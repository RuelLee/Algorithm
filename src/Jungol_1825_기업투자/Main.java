/*
 Author : Ruel
 Problem : Jungol 1825번 기업투자
 Problem address : https://jungol.co.kr/problem/1825
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1825_기업투자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n의 자금을 가지고 m개의 기업에 투자하려고 한다.
        // 각 기업에 1 ~ n까지의 금액을 투자했을 때의 이익이 주어진다.
        // 자금을 투자하여 얻을 수 있는 최대 이익은 얼마인가?
        //
        // DP 문제
        // DP를 통해, 순차적으로 기업의 모든 투자액을 살펴보며
        // dp[금액][기업] = 투자한 금액, 단 dp[금액][m] = 이익으로 정하고 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 자본 n, 기업의 수 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 기업에 대한 투자 당 이익
        int[][] invests = new int[m][n + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int amount = Integer.parseInt(st.nextToken());
            for (int j = 0; j < m; j++)
                invests[j][amount] = Integer.parseInt(st.nextToken());
        }

        // 현재 살펴보는 기업에 투자 안했을 때의 값과 투자한 결과 두 경우가 별개로 필요하다.
        // 따라서 두개의 dp공간을 번갈아가며 사용한다.
        // dp[현재 기업에 투자 여부에 따른 상태][금액][기업] = 투자 금액과 이익
        int[][][] dp = new int[2][n + 1][m + 1];
        for (int i = 0; i < m; i++) {
            // i번 기업에 투자하기 전
            int cur = i % 2;
            // 투자 후의 결과
            int next = (i + 1) % 2;
            // 총 투자 금액이 j원인데
            for (int j = 1; j <= n; j++) {
                // i번 기업에 투자한 금액이 k원인 경우
                for (int k = 0; k <= j; k++) {
                    // 그 결과가 최대 이익을 갱신한다면
                    if (dp[next][j][m] < dp[cur][j - k][m] + invests[i][k]) {
                        // dp값 갱신
                        for (int l = 0; l < m; l++)
                            dp[next][j][l] = dp[cur][j - k][l];
                        dp[next][j][m] = dp[cur][j - k][m] + invests[i][k];
                        dp[next][j][i] = k;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        // m번 기업까지 모두 살펴본 뒤 결과가 저장된 공간
        int result = m % 2;
        // 최대 이익
        sb.append(dp[result][n][m]).append("\n");
        // 각 기업 별 투자 금액 기록
        sb.append(dp[result][n][0]);
        for (int i = 1; i < m; i++)
            sb.append(" ").append(dp[result][n][i]);
        // 답 출력
        System.out.println(sb);
    }
}