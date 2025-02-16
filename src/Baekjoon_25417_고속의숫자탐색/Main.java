/*
 Author : Ruel
 Problem : Baekjoon 25417번 고속의 숫자 탐색
 Problem address : https://www.acmicpc.net/problem/25417
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25417_고속의숫자탐색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 5 * 5 크기의 격자가 주어진다.
        // 각 칸에는 -1, 0, 1, 7의 숫자가 적혀있다.
        // -1은 벽이고, 0은 이동할 수 있는 빈 칸, 1은 목적지, 7은 달릴 때, 멈춰야만 하는 칸이다.
        // 한 명의 학생이 (r, c)의 위치에 있다.
        // 학생은 상 하 좌 우 한 방향으로 한 칸 걸어서 이동할 수 있다.
        // 학생은 맵 밖으로 벗어나거나 -1을 만나기 직전 혹은 7번 칸을 만날 때까지 달릴 수 있다.
        // 걷거나 달리는 건 하나의 행동으로 취급된다.
        // 1번 칸으로 가는 최소 행동의 수는?
        //
        // BFS 문제
        // BFS로 각 칸을 한 칸씩 사방 탐색 혹은 조건에 맞는 달리기로 탐색하는 경우를 모두 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각 격자에 적힌 수
        int[][] map = new int[5][5];
        StringTokenizer st;
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 학생의 위치
        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(r * 5 + c);
        // 각 칸에 도달하는 최소 행동
        int[][] minActions = new int[5][5];
        for (int[] ma : minActions)
            Arrays.fill(ma, Integer.MAX_VALUE);
        minActions[r][c] = 0;
        while (!queue.isEmpty()) {
            // 현재 위치
            int current = queue.poll();
            int row = current / 5;
            int col = current % 5;

            for (int d = 0; d < 4; d++) {
                // 다음 위치
                int nextR = row + dr[d];
                int nextC = col + dc[d];
                
                // 다음 위치가 격자를 벗어나지 않고, 벽이 아닌 경우
                if (checkArea(nextR, nextC) && map[nextR][nextC] != -1) {
                    // 최소 행동 횟수를 비교해보고, 최솟값을 갱신하는지 확인.
                    if (minActions[nextR][nextC] > minActions[row][col] + 1) {
                        minActions[nextR][nextC] = minActions[row][col] + 1;
                        queue.offer(nextR * 5 + nextC);
                    }

                    // 한 칸 이동한 방향 그대로 달리는 경우
                    // 7을 만나거나, 벽이거나, 격자를 벗어나기 직전까지 이동한다.
                    while (map[nextR][nextC] != 7 && checkArea(nextR + dr[d], nextC + dc[d]) &&
                            map[nextR + dr[d]][nextC + dc[d]] != -1) {
                        nextR += dr[d];
                        nextC += dc[d];
                    }
                    // 해당 값의 행동 최솟값을 갱신하는지 확인.
                    if (minActions[nextR][nextC] > minActions[row][col] + 1) {
                        minActions[nextR][nextC] = minActions[row][col] + 1;
                        queue.offer(nextR * 5 + nextC);
                    }
                }
            }
        }
        
        // 목적지를 찾아, 최소 행동 횟수를 출력한다.
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1) {
                    System.out.println(minActions[i][j] == Integer.MAX_VALUE ? -1 : minActions[i][j]);
                    break;
                }
            }
        }
    }
    
    // 격자 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < 5 && c >= 0 && c < 5;
    }
}