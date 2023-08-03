/*
 Author : Ruel
 Problem : Baekjoon 17130번 토끼가 정보섬에 올라온 이유
 Problem address : https://www.acmicpc.net/problem/17130
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17130_토끼가정보섬에올라온이유;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {0, 1, -1};
    static int[] dc = {1, 1, 1};

    public static void main(String[] args) throws IOException {
        // 정보섬은 n행, m열의 격자로 나타낼 수 있으며
        // 토끼는 →, ↘, ↗ 방향으로만 이동할 수 있다고 한다.
        // R은 토끼의 위치, #은 벽, C는 당근, O는 탈출구를 의미한다.
        // 탈출구에 도달하더라도 바로 나가지 않고, 다른 탈출구를 통해 나가는 것도 가능하다.
        // 가장 많은 당근을 주워 탈출하고자 할 때, 그 개수는?
        // 탈출하는 것이 불가능하다면 -1을 출력한다.
        //
        // DP 문제
        // 일관되게 오른쪽으로만 이동하되, 행만 조금씩 변동이 생긴다.
        // 따라서 BFS를 통해 탐색하더라도, 당근의 상태를 고려하지 않더라도 반복이 생기지 않는다.
        // 혹은 0열의 0행 ~ n-1행, ... , (n -1)열의 0행 ~ n-1행을 순차적으로 살펴봐도 괜찮다.
        // 답안이 0인 경우도 생기는데 당근을 하나도 줍지 못했지만 탈출할 수 있는 경우이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 정보섬의 정보
        char[][] map = new char[n][];
        int start = -1;
        for (int i = 0; i < map.length; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < map[i].length; j++) {
                // 시작 위치
                if (map[i][j] == 'R')
                    start = i * m + j;
            }
        }
        
        // 각 지점에서 도달하는데 최대로 얻을 수 있는 당근의 개수
        int[][] dp = new int[n][m];
        // 초기값은 -1
        // 탈출구에 도달하는 경우와 도달하지 않는 경우를 구분하기 위해
        // -1로 초기화했다.
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // 시작점의 당근 개수는 0
        dp[start / m][start % m] = 0;

        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / m;
            int col = current % m;

            for (int d = 0; d < 3; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC, map)) {
                    // 다음 지점이 당근일 경우, 개수 +1
                    if (map[nextR][nextC] == 'C') {
                        if (dp[nextR][nextC] < dp[row][col] + 1) {
                            dp[nextR][nextC] = dp[row][col] + 1;
                            queue.offer(nextR * m + nextC);
                        }
                    } else if (map[nextR][nextC] == '#')        // 벽일 경우는 건너뜀
                        continue;
                    else if (dp[nextR][nextC] < dp[row][col]) {
                        // 다음 지점의 값이
                        // 현재 지점에서 다음 지점으로 가는 경로가 유리
                        dp[nextR][nextC] = dp[row][col];
                        queue.offer(nextR * m + nextC);
                    }
                }
            }
        }

        int max = -1;
        // 탈출구에서의 값들 중 가장 큰 값을 찾는다.
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'O')
                    max = Math.max(max, dp[i][j]);
            }
        }
        // 답안 출력
        System.out.println(max);
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}