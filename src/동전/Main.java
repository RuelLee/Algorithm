/*
 Author : Ruel
 Problem : Baekjoon 9084번 동전
 Problem address : https://www.acmicpc.net/problem/9084
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 동전;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] coins;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        // 동전의 종류가 주어지고, 원하는 금액이 주어질 때
        // 해당 동전으로 원하는 금액을 만들 수 있는 가지 수를 구하는 문제
        // 배낭 문제! DP를 활용하여 풀어주자
        // 가로줄은 0원 ~ 목표원
        // 세로줄은 각 동전을 의미한다. 그리고 그 때 값은 해당 금액을 만들 수 있는 가지수이다.
        // 각 가로줄의 첫번째 칸은 1로 초기세팅을 해주자(0원을 만들 수 있는 가지수는 아무것도 안 쓰는 1가지
        // 각 동전은 coins[]에 저장한다
        // n번째 동전을 사용하여 m원을 만드는 방법은
        // n-1번째까지의 동전만 사용하여 m원을 만드는 방법 dp[n-1][m]과
        // n번째 동전을 고려하여 만든 m - coins[n]원에 coins[n]동전을 하나 더해 m원을 만드는 방법 dp[n][m-coins[n]]이다.
        // 두 가지수를 합쳐주면, m원을 만들 수 있는 모든 가지수가 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            coins = new int[n + 1];     // dp를 고려하여 1번 자리부터 동전을 저장한다
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i < coins.length; i++)
                coins[i] = Integer.parseInt(st.nextToken());

            int m = Integer.parseInt(br.readLine());        // 목표 금액
            dp = new int[coins.length][m + 1];      // 가로줄은 0원 동전, coins[1]동전, ... , coins[n] 동전 / 세로줄은 0원, 1원, ..., m원
            for (int i = 0; i < dp.length; i++)     // 0원을 만들 수 있는 방법은 동전을 하나도 사용하지 않는 1가지
                dp[i][0] = 1;

            for (int i = 1; i < dp.length; i++) {
                for (int j = 1; j < dp[i].length; j++) {
                    dp[i][j] = dp[i - 1][j];        // j원이 되는 방법은 coins[i]를 하나도 사용하지 않은 dp[i -1][j]가지
                    if (j >= coins[i])      // 만약 j가 coins[i]보다 같거나 큰 값이라면, j - coins[i]원을 만들고, 그 후에 coins[i]원을 더하는 방법의 수인 dp[i][j - coins[i]]를 더해준다.
                        dp[i][j] += dp[i][j - coins[i]];
                }
            }
            // 최종적으로 모든 동전을 사용하여 만드는 방법의 가지수는 dp[동전의 가지수][m원]이다.
            sb.append(dp[dp.length - 1][m]).append("\n");
        }
        System.out.println(sb);
    }
}