/*
 Author : Ruel
 Problem : Baekjoon 17485번 진우의 달 여행 (Large)
 Problem address : https://www.acmicpc.net/problem/17485
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17485_진우의달여행_Large;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {1, 1, 1};
    static int[] dc = {-1, 0, 1};

    public static void main(String[] args) throws IOException {
        // n, m크기의 공간이 주어진다.
        // 아래로 진행할 때는 ↙↓↘ 방향으로만 진행할 수 있으며, 각 칸에 도달하기 위한 연료 소모량이 주어진다.
        // 현재 칸으로 도착하기 위해 진행했던 방향으로 연속해서 다음 칸을 이동할 수 없다.
        // 가장 윗 줄에서 출발하여 가장 아랫줄에 도착하려할 때 소모하는 최소 연료 소모량은?
        //
        // DP문제
        // 각 칸에 도달하는 최소 연료 소모량을 계산해나가되,
        // 방향에 대한 제한이 있으므로 도착한 방향에 따라 구분하여 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력 처리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 칸에 도착하는데 필요한 연료소모량
        int[][] space = new int[n][m];
        for (int i = 0; i < space.length; i++)
            space[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 칸에 도달하는 최소 전체 연료 소모량
        int[][][] dp = new int[n][m][3];
        // 큰 값으로 초기화
        for (int[][] dd : dp) {
            for (int[] d : dd)
                Arrays.fill(d, Integer.MAX_VALUE);
        }
        // 맨 윗줄에는 해당 칸에 도달하는데 필요한 연료 값으로 초기화.
        for (int i = 0; i < dp[0].length; i++)
            Arrays.fill(dp[0][i], space[0][i]);

        // i번째 row
        for (int i = 0; i < dp.length; i++) {
            // j번째 col
            for (int j = 0; j < dp[i].length; j++) {
                // i, j에 도달한 방향.
                for (int k = 0; k < dp[i][j].length; k++) {
                    // 만약 해당 값이 큰 값이라면(= 도달하지 못했다면)
                    // 건너 뛴다.
                    if (dp[i][j][k] == Integer.MAX_VALUE)
                        continue;

                    // ↙↓↘ 세가지 방향 고려
                    for (int d = 0; d < 3; d++) {
                        // 같은 방향으로 연속하여 이동이 불가하므로
                        // 같은 방향일 경우 건너 뛴다.
                        if (k == d)
                            continue;

                        // d방향으로 이동할 때 다음 칸
                        int nextR = i + dr[d];
                        int nextC = j + dc[d];
                        // 다음 칸이 범위를 벗어나지 않으며
                        // 해당칸에 도달하는 전체 연료 소모량이 최소값을 갱신한다면
                        // 값 갱신.
                        if (checkArea(nextR, nextC, space) && dp[nextR][nextC][d] > dp[i][j][k] + space[nextR][nextC])
                            dp[nextR][nextC][d] = dp[i][j][k] + space[nextR][nextC];
                    }
                }
            }
        }

        // 가장 마지막 줄에 어느 방향이든 최소 연료로 도달한 경우, 해당 전체 연료 소모량을 찾는다.
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < dp[n - 1].length; i++) {
            for (int j = 0; j < dp[n - 1][i].length; j++)
                answer = Math.min(answer, dp[n - 1][i][j]);
        }
        // 답안 출력.
        System.out.println(answer);
    }

    // 범위 체크
    static boolean checkArea(int r, int c, int[][] space) {
        return r >= 0 && r < space.length && c >= 0 && c < space[r].length;
    }
}