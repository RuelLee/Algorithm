/*
 Author : Ruel
 Problem : Baekjoon 27134번 Subset Sums
 Problem address : https://www.acmicpc.net/problem/27134
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27134_SubsetSums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n이 주어진다.
        // 1 ~ n까지의 합을 두 개의 같은 합으로 나누는 경우의 수를 구하라
        //
        // dp, 배낭 문제
        // 먼저 모든 수의 합이 홀수인지를 확인한다.
        // 홀수라면 두 그룹으로 나누는 것이 불가능하기 때문에 답은 0
        // 짝수인 경우, 두 그룹으로 나눌 수 있으므로
        // 모든 수에 배낭 문제처럼 각 수로 만들 수 있는 경우의 수를 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 1 ~ n까지의 수
        int n = Integer.parseInt(br.readLine());

        int sum = n * (n + 1) / 2;
        // 모든 수의 합이 홀수라면 답은 0
        if (sum % 2 == 1)
            System.out.println(0);
        else {
            // 두 그룹으로 나누므로, 각 그룹의 합은 sum / 2
            // 배낭
            long[] dp = new long[sum / 2 + 1];
            dp[0] = 1;
            for (int i = 1; i <= n; i++) {
                for (int j = dp.length - 1 - i; j >= 0; j--) {
                    if (dp[j] == 0)
                        continue;

                    dp[j + i] += dp[j];
                }
            }
            // sum / 2를 만드는 경우의 수를 구했으나
            // 실제로는 두 그룹으로 나누는 경우이므로 답을 / 2한 값으로 출력
            System.out.println(dp[dp.length - 1] / 2);
        }
    }
}