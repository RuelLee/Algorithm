/*
 Author : Ruel
 Problem : Baekjoon 2225번 합분해
 Problem address : https://www.acmicpc.net/problem/2225
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2225_합분해;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final long LIMIT = 1_000_000_000L;

    public static void main(String[] args) throws IOException {
        // 0부터 n까지의 정수 k개를 더해, 그 합이 n이 되는 경우의 수를 구하라
        // 답을 1,000,000,000으로 나눈 나머지를 출력한다.
        //
        // 간단한 DP 문제
        // n과 k의 조건이 200이하이므로
        // dp[더한 정수의 개수][합] = 경우의 수 으로 DP를 세우고 값을 채워나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // dp[더한 정수의 개수][합]
        long[][] dp = new long[k + 1][n + 1];
        // 아무 수도 더하지 않았을 때, 합이 0인 경우의 수 1
        dp[0][0] = 1;

        // 더하는 수를 하나씩 늘려나가며, k - 1개의 수를 더했을 때까지 살펴본다.
        for (int i = 0; i < dp.length - 1; i++) {
            // 합은 n까지 살펴본다.
            for (int j = 0; j < dp[i].length; j++) {
                // 만약 j값에 도달하는 경우가 없다면 건너뛴다.
                if (dp[i][j] == 0)
                    continue;

                // 값이 존재한다면, 해당 경우에 a를 더하는 경우를 계산한다.
                // a는 0부터 a와 j를 더한 값이 n을 넘지 않을 때까지.
                // 더하는 것이기 때문에 n을 넘는 경우는 고려하지 않는다.
                for (int a = 0; a + j <= n; a++) {
                    // i + 1개의 수를 더해 a + j가 되는 경우의 수에
                    // i개의 수를 더해 j인 상태에 +a를 하는 경우의 수를 더해준다.
                    dp[i + 1][a + j] += dp[i][j];
                    // 모듈러 연산값을 최종적으로 출력하므로 10억으로 모듈러 연산해준다.
                    dp[i + 1][a + j] %= LIMIT;
                }
            }
        }
        // 최종적으로 k개의 수를 더해 n이 된 경우의 수를 출력한다.
        System.out.println(dp[k][n]);
    }
}