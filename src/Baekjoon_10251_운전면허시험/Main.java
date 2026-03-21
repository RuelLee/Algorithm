/*
 Author : Ruel
 Problem : Baekjoon 10251번 운전 면허 시험
 Problem address : https://www.acmicpc.net/problem/10251
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10251_운전면허시험;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][][][] dp;
    static int[][] toRight, toBottom;
    static int n, m, maxTurn;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자가 주어진다.
        // 가장 왼쪽 위 끝에서 가장 오른쪽 아래 끝으로 이동하고자 한다.
        // 이 때 자동차에는 g만큼의 연료가 들어있으며, 각 한 칸을 이동하는데 l만큼의 시간이 소모된다.
        // 진행 방향은 오른쪽 혹은 아래로만 이동이 가능하며, 진행 방향을 바꾸면 1만큼의 시간이 소모된다.
        // 각 칸마다 오른쪽과 아래로 이동할 때 소모되는 연료량이 주어진다.
        // g이하의 연료를 소모하며 목적지에 도달하고자할 때, 최소 소요 시간은?
        //
        // DP 문제
        // 오른쪽과 아래로만 이동할 수 있기 때문에 도착지까지
        // 도달시간은 (n - 1) + (m - 1) + 방향 전환 횟수이다.
        // 따라서 dp를
        // dp[row][col][방향전환횟수][현재방향] = 최소 사용 연료
        // 로 세우고 DP를 채워나간다.
        // 최종 도착지에 도달한 경우들 중 사용 연료가 g이하를 만족시키는 값들 중
        // 방향 전환이 최소인 값을 찾아 소요 시간을 구한다.

        dp = new int[100][100][201][2];
        toRight = new int[100][100];
        toBottom = new int[100][100];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트 케이스 수
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 가로, 세로 줄의 수
            m = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());
            // 한 번 이동에 걸리는 시간과 초기 연료
            int l = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            // 오른쪽으로 이동할 때의 연료 소모량
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n - 1; j++)
                    toRight[i][j] = Integer.parseInt(st.nextToken());
            }
            // 아래로 이동할 때의 연료 소모량
            for (int i = 0; i < m - 1; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++)
                    toBottom[i][j] = Integer.parseInt(st.nextToken());
            }

            // 최대 방향 전환 횟수
            // 가로와 세로 중 긴 쪽 방향으로 진행을 시작하여
            // 짧은 길이 쪽으로 방향 전환 후, 다시 긴 쪽으로 전환하는 행동을
            // 짧은 길이만큼 반복하는게 최대 횟수
            maxTurn = Math.min(n, m) * 2;
            // DP
            clearDP();
            fillDP();
            int answer = Integer.MAX_VALUE;
            // 방향 전환 수에 상관 없이
            for (int i = 0; i <= maxTurn; i++) {
                // 최종 도달 방향에 상관 없이
                for (int j = 0; j < 2; j++) {
                    // 소모 연료량이 g이하인 값들 중 최소 방향 전환 횟수를 구한다.
                    if (dp[m - 1][n - 1][i][j] <= g)
                        answer = Math.min(answer, i);
                }
            }

            // 초기값이라면 -1 출력
            // 그 외의 경우, ((n - 1) + (m - 1)) * l + 방향 전환 횟수 = 소요 시간 값을 출력
            sb.append(answer == Integer.MAX_VALUE ? -1 : (n + m - 2) * l + answer).append("\n");
        }
        System.out.print(sb);
    }

    static void fillDP() {
        // dp
        // 모든 행
        for (int i = 0; i < m; i++) {
            // 모든 열
            for (int j = 0; j < n; j++) {
                // 최대 방향 전환 횟수만큼
                for (int k = 0; k <= maxTurn; k++) {
                    // 가로 진행 값이 초기값이 아닌 경우.
                    if (dp[i][j][k][0] != Integer.MAX_VALUE) {
                        // 가로로 이동 가능한 경우
                        if (j + 1 < n)
                            dp[i][j + 1][k][0] = Math.min(dp[i][j + 1][k][0], dp[i][j][k][0] + toRight[i][j]);
                        // 세로로 이동 가능한 경우
                        if (i + 1 < m && k + 1 <= maxTurn)
                            dp[i + 1][j][k + 1][1] = Math.min(dp[i + 1][j][k + 1][1], dp[i][j][k][0] + toBottom[i][j]);
                    }

                    // 세로 진행 값이 초기값이 아닌 경우.
                    if (dp[i][j][k][1] != Integer.MAX_VALUE) {
                        if (j + 1 < n && k + 1 <= maxTurn)
                            dp[i][j + 1][k + 1][0] = Math.min(dp[i][j + 1][k + 1][0], dp[i][j][k][1] + toRight[i][j]);
                        if (i + 1 < m)
                            dp[i + 1][j][k][1] = Math.min(dp[i + 1][j][k][1], dp[i][j][k][1] + toBottom[i][j]);
                    }
                }
            }
        }
    }

    // dp 공간 초기화
    static void clearDP() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k <= maxTurn; k++) {
                    for (int l = 0; l < 2; l++)
                        dp[i][j][k][l] = Integer.MAX_VALUE;
                }
            }
        }
        dp[0][0][0][0] = dp[0][0][0][1] = 0;
    }
}