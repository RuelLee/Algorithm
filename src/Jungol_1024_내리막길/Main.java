/*
 Author : Ruel
 Problem : Jungol 1024번 내리막 길
 Problem address : https://jungol.co.kr/problem/1024
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1024_내리막길;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 가로 n, 세로 m 길이의 격자가 주어진다.
        // 각 격자 칸마다 높이가 정해져있으며, 가장 왼쪽, 가장 위 칸에서 시작하여
        // 가장 오른쪽, 가장 아래쪽 칸으로 가고자 한다.
        // 언제나 높이가 더 낮은 곳으로만 이동이 가능하다 할 때
        // 목적지까지 가는 경우의 수를 구하라
        //
        // 최단 경로
        // 단순 반복문으로 풀고자한다면 할 수 없는 문제
        // 먼저 높이가 낮아지는 경로로만 이동할 수 있으므로, 우선순위큐에 높이별 내림차순으로 살펴본다.
        // 그러면서, 한 칸에 모이는 경우의 수들을 한번에 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 격자판의 크기
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        // 각 격자 칸의 높이
        int[][] map = new int[m][n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 경우의 수 계산
        int[][] dp = new int[m][n];
        // 시작 위치
        dp[0][0] = 1;
        // 우선순위큐로 높이를 내림차순으로 탐색
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(map[o2 / n][o2 % n], map[o1 / n][o1 % n]));
        pq.offer(0);
        while (!pq.isEmpty()) {
            int current = pq.poll();
            // 현재 위치
            int row = current / n;
            int col = current % n;

            for (int d = 0; d < 4; d++) {
                // 다음 위치
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 맵을 안 벗어나며, 높이가 더 낮아질 때만
                if (checkArea(nextR, nextC) && map[nextR][nextC] < map[row][col]) {
                    // 이미 경우의 수가 0이 아닌, 다른 칸에서 오는 경우가 있는 경우는
                    // 이미 우선순위큐에 해당 위치가 담겨있을 것이므로 추가로 담지 않는다.
                    // 0인 경우만 담는다.
                    if (dp[nextR][nextC] == 0)
                        pq.offer(nextR * n + nextC);
                    // 경우의 수 누적
                    dp[nextR][nextC] += dp[row][col];
                }
            }
        }
        // 최종 목적지까지 이르는 경우의 수 출력
        System.out.println(dp[m - 1][n - 1]);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < m && c >= 0 && c < n;
    }
}