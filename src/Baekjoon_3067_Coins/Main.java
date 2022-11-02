/*
 Author : Ruel
 Problem : Baekjoon 3067번 Coins
 Problem address : https://www.acmicpc.net/problem/3067
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3067_Coins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스
        // 각각의 테스트케이스는 n개의 동전의 종류와 만들고자 하는 금액 m이 주어진다
        // 이 때 m원을 만드는 방법의 가짓수는?
        //
        // DP문제
        // 동전을 순차적으로 살펴보며
        // coin원 동전을 사용하여 j원을 만드는 방법은
        // j - coin 원에서 coin원을 사용하여 j원을 만드는 방법이 있다.
        // 이런식으로 전 결과의 가짓수를 참고하여 DP를 채워나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 테스트케이스
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // n개의 동전 종류
            int n = Integer.parseInt(br.readLine());
            // 동전들
            int[] coins = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // 목표 금액
            int m = Integer.parseInt(br.readLine());

            // DP
            int[] dp = new int[m + 1];
            // 0원을 만드는 방법은 아무 동전도 사용하지 않는 경우 한가지.
            dp[0] = 1;
            // 각 동전들을 살펴보며
            for (int coin : coins) {
                // j원을 만드는 방법은 j - coin 원에서 coin원을 더해 j원을 만드는 방법이 있다.
                // 따라서 j - coin 원을 만드는 방법의 수만큼이 j원을 만드는 방법의 수에 더해진다.
                for (int i = coin; i < dp.length; i++)
                    dp[i] += dp[i - coin];
            }
            // 최종적으로 m원을 만드는 경우의 수를 출력.
            sb.append(dp[m]).append("\n");
        }
        // 전체 결과 출력.
        System.out.print(sb);
    }
}