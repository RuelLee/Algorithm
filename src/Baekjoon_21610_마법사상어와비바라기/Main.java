/*
 Author : Ruel
 Problem : Baekjoon 21610번 마법사 상어와 비바라기
 Problem address : https://www.acmicpc.net/problem/21610
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21610_마법사상어와비바라기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {0, 0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dc = {0, -1, -1, 0, 1, 1, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어지고, 각 격자에는 물을 담을 수 있는 공간이 있다.
        // 가장 먼저, (n, 1), (n, 2), (n-1, 1), (n-1, 2)에는 비구름이 있다.
        // 방향은 총 8개의 방향이 있으며, 8개의 정수로 표현한다. 1부터 순서대로 ←, ↖, ↑, ↗, →, ↘, ↓, ↙ 이다.
        // 구름이 이동할 때는 열의 끝과 끝, 행의 끝과 끝이 이어져있다.
        // 다시 말해, 1행과 n행, 1열과 n열은 연결되어있다.
        // 각 턴마다 d와 s가 주어지며, 해당 값을 토대로 다음을 행동을 한다.
        // 1. 모든 구름이 d 방향으로 s칸 이동한다.
        // 2. 각 구름에서 비가 내려 물의 양이 1씩 증가한다.
        // 3. 구름이 사라진다.
        // 4. 2에서 물이 증가한 곳에 대해, 인접한 대각선 위치한 곳에 물이 존재한다면 해당 개수만큼 물이 증가한다.
        //    단, 여기서 대각선 위치는 열과 열이 맞닿아 있지 않다. (n, 2)에서 인접한 대각선은 (n-1, 1), (n-1, 3) 뿐이다.
        // 5. 물이 2이상 인 곳에 대해 구름이 생성되고 물이 2 감소한다. 단 구름이 생성되는 지역은 직전에 구름이 사라진 칸이 아니어야 한다.
        // m번의 턴이 지난 후 모든 물의 양의 합을 구하라
        //
        // 시뮬레이션 문제
        // 문제에 서술된 대로 구현하면 되는 문제.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 격자, m개의 턴
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 처음 물의 양
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                a[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 처음의 구름
        Queue<Integer> clouds = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++)
                clouds.offer((n - 2 + i) * n + j);
        }
        
        // 구름이 사라진 지역 = 이번 턴에 구름이 생성되지 않을 지역
        boolean[][] notEvaporated = new boolean[n][n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // d방향으로 s만큼 구름이 이동
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());

            for (int j = 0; j < clouds.size(); j++) {
                // 현재 구름의 idx
                int current = clouds.poll();
                // 을 토대로 구름의 이동 위치.
                int nextR = (current / n + dr[d] * s + n * 50) % n;
                int nextC = (current % n + dc[d] * s + n * 50) % n;

                // 구름이 이동한 위치에서 구름이 제거될 것이고
                // 이번 턴에 구름이 생성되지 않는다.
                notEvaporated[nextR][nextC] = true;
                // 비가 내려 물이 증가.
                a[nextR][nextC]++;
                clouds.offer(nextR * n + nextC);
            }

            // 비가 내린 곳의 대각선을 살펴본다.
            while (!clouds.isEmpty()) {
                int row = clouds.peek() / n;
                int col = clouds.poll() % n;

                for (int j = 2; j < 9; j += 2) {
                    int nearR = row + dr[j];
                    int nearC = col + dc[j];

                    // 대각선 위치가 맵을 벗어나지 않고, 물이 존재한다면
                    // 각 칸의 개수 만큼 a[row][col]의 물이 1씩 증가.
                    if (checkArea(nearR, nearC, n) && a[nearR][nearC] > 0)
                        a[row][col]++;
                }
            }
            
            // 전체 칸을 살펴보며
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    // 구름이 사라진 곳이라면 다음 턴을 위해 true을 지워줌
                    if (notEvaporated[j][k])
                        notEvaporated[j][k] = false;
                    else if (a[j][k] >= 2) {    // 아니고, 물이 2 이상 있을 경우, 구름 생성
                        a[j][k] -= 2;
                        clouds.offer(j * n + k);
                    }
                }
            }
        }
        
        // 모든 턴이 끝나고 총 물의 양을 계산
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                sum += a[i][j];
        }
        System.out.println(sum);
    }

    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}