/*
 Author : Ruel
 Problem : Baekjoon 11084번 나이트의 염탐
 Problem address : https://www.acmicpc.net/problem/11084
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11084_나이트의염탐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int r, c;
    static int[] dr = {-1, -2, -2, -1, 1, 2, 2, 1};
    static int[] dc = {-2, -1, 1, 2, 2, 1, -1, -2};
    static int LIMIT = 1_000_000_009;

    public static void main(String[] args) throws IOException {
        // r * c 체스판 위, (1, 1)과 (r, c)에 두 나라가 있다.
        // (1, 1) 나라에서 (r, c) 나라로 나이트를 보냈다.
        // 갈 수 있는 최단 경로의 이동 횟수와, 그러한 경우의 수가 몇 가지인지 출력하라
        // 불가능하다면 None을 출력한다.
        //
        // BFS
        // 각 칸에 도달하는 최소 이동 횟수와 경우의 수를 기록해나간다.
        // 최소 이동 횟수가 갱신되는 경우에는 경우의 수도 갱신
        // 최소 이동 횟수가 같은 경우에는 경우의 수는 누적시킨다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 체스판
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        
        // dp[i][j][0] = (i, j)에 도달하는 최소 이동 횟수
        // dp[i][j][1] = 그러한 경우의 수
        long[][][] dp = new long[r][c][2];
        // 값 초기화
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j][0] = Integer.MAX_VALUE;
                dp[i][j][1] = 0;
            }
        }
        dp[0][0][0] = 0;
        dp[0][0][1] = 1;
        
        // (1, 1)에서 출발
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 현재 위치
            int row = current / c;
            int col = current % c;

            for (int d = 0; d < dr.length; d++) {
                // 나이트로서 이동 가능한 위치
                int nextR = row + dr[d];
                int nextC = col + dc[d];
                
                // 맵의 범위를 벗어나지 않으며
                if (checkArea(nextR, nextC)) {
                    // 최소 이동 횟수를 갱신하는 경우
                    // 최소 이동 횟수와 경우의 수 갱신
                    if (dp[nextR][nextC][0] > dp[row][col][0] + 1) {
                        dp[nextR][nextC][0] = dp[row][col][0] + 1;
                        dp[nextR][nextC][1] = dp[row][col][1];
                        queue.offer(nextR * c + nextC);
                    } else if (dp[nextR][nextC][0] == dp[row][col][0] + 1)      // 최소 이동 횟수가 같은 경우, 경우의 수 누적
                        dp[nextR][nextC][1] = (dp[nextR][nextC][1] + dp[row][col][1]) % LIMIT;
                }
            }
        }
        // (r, c) 나라에 도달할 수 없다면 None
        // 가능하다면 최소 이동 횟수와 경우의 수 출력
        System.out.println(dp[r - 1][c - 1][0] == Integer.MAX_VALUE ? "None" : dp[r - 1][c - 1][0] + " " + dp[r - 1][c - 1][1]);
    }

    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}