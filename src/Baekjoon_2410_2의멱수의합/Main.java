/*
 Author : Ruel
 Problem : Baekjoon 2410번 2의 멱수의 합
 Problem address : https://www.acmicpc.net/problem/2410
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2410_2의멱수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        // 자연수 n을 2의 멱수의 합으로 나타내는 경우의 수를 구하고자 한다.
        // 2의 멱수는 2^k로 표현되는 자연수를 의미한다.
        // 3의 경우
        // 1 + 1 + 1
        // 1 + 2
        // 2가지 경우로 표현할 수 있다.
        //
        // DP
        // 1부터 2의 멱수를 순서대로 살펴보며 만들 수 있는 경우의 수를 계산한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 2의 멱수의 합으로 표현하고자 하는 수
        int n = Integer.parseInt(br.readLine());
        
        // dp
        int[] dp = new int[n + 1];
        // 초기값.
        // 0은 1가지
        dp[0] = 1;
        // i가 2배가 되며 2의 멱수들을 순서대로 계산한다.
        for (int i = 1; i <= n; i *= 2) {
            // i + j를 만드는 방법은
            // j를 만든 후에 i를 더해 i + j를 만드는 방법이다.
            // 이는 i를 만드는 방법의 수와 같다.
            for (int j = 0; j + i < dp.length; j++) {
                dp[j + i] += dp[j];
                dp[j + i] %= LIMIT;
            }
        }
        // 최종적으로 n을 2의 멱수의 합으로 표현하는 방법의 수를 출력한다.
        System.out.println(dp[n]);
    }
}