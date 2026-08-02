/*
 Author : Ruel
 Problem : Jungol 2000번 동전교환
 Problem address : https://jungol.co.kr/problem/2000
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2000_동전교환;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 동전의 종류가 n개 주어지고 각 금액이 주어진다.
        // w원을 최소 개수의 동전으로 내주려고한다면 몇 개의 동전이 필요한가
        //
        // dp 문제
        // dp를 통해 w원까지 만들 수 있는 최소 동전의 개수를 센다.
        // dp[i] = j, i원을 만드는데 필요한 동전의 최소 개수 j

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n 종류의 동전
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 동전들
        int[] coins = new int[n];
        for (int i = 0; i < coins.length; i++)
            coins[i] = Integer.parseInt(st.nextToken());

        // w원
        int w = Integer.parseInt(br.readLine());
        int[] dp = new int[w + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 0원을 만들 때는 0개의 동전이 필요함
        dp[0] = 0;
        for (int i = 0; i < dp.length; i++) {
            // 만드는 방법이 없는 경우, 건너뜀
            if (dp[i] == Integer.MAX_VALUE)
                continue;

            // 현재 금액에 동전을 추가했을 때
            for (int coin : coins) {
                if (i + coin < dp.length)
                    dp[i + coin] = Math.min(dp[i + coin], dp[i] + 1);
            }
        }
        // 답 출력
        System.out.println(dp[w] == Integer.MAX_VALUE ? "impossible" : dp[w]);
    }
}