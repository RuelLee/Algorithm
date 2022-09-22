/*
 Author : Ruel
 Problem : Baekjoon 2294번 동전 2
 Problem address : https://www.acmicpc.net/problem/2294
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2294_동전2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n가지 종류의 동전을 사용하여 그 가치의 합이 k가 되도록 하고 싶다.
        // 각 동전의 갯수는 무한하다할 때, 가장 적은 동전의 수로 k원을 만드는 경우, 동전의 개수는?
        //
        // DP문제
        // 각 동전 별로 살펴보며, 특정 금액을 만들 때 소요되는 동전의 개수를 따져나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 주어진 동전의 종류.
        int[] coins = new int[n];
        for (int i = 0; i < coins.length; i++)
            coins[i] = Integer.parseInt(br.readLine());

        // k원까지 dp를 세운다.
        int[] dp = new int[k + 1];
        // 큰 값으로 초기화.
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 0원을 만드는 경우, 필요 동전의 수 0개.
        dp[0] = 0;

        // 모든 동전들을 하나씩 살펴보며
        for (int coin : coins) {
            // DP를 채운다.
            // j원의 시작은 동전 1개의 값.
            // 그 이하 값은 coin으로 만들지 못한다.
            for (int j = coin; j < dp.length; j++) {
                // j - coin 값이 초기값이 아닌 경우(= 다른 동전들로 j - coin값을 만든 경우)
                // j - coin을 만드는 필요한 동전 + 1개(coin)을 한 동전의 수가 현재 기록된 j원을 만드는 동전의 수보다 적을 경우.
                // 해당 값으로 j원을 만드는데 필요한 동전의 개수를 갱신한다.
                if (dp[j - coin] != Integer.MAX_VALUE)
                    dp[j] = Math.min(dp[j], dp[j - coin] + 1);
            }
        }

        // 모든 동전을 살펴보았을 때, k원을 만드는 동전의 개수가 초기값이라면
        // 불가능한 경우이므로 -1 출력.
        // 아니라면 해당 동전의 개수 출력.
        System.out.println(dp[k] == Integer.MAX_VALUE ? -1 : dp[k]);
    }
}