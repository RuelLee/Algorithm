/*
 Author : Ruel
 Problem : Jungol 1108번 Sumsets 2
 Problem address : https://jungol.co.kr/problem/1108
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1093_Sumsets2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 숫자 n이 주어진다.
        // n을 2의 제곱 수의 합으로 표현하고자할 때. 그 개수를 구하라
        // 3인 경우
        // 1 + 1 + 1
        // 1  + 2로 2가지다.
        //
        // DP 문제
        // 점화식을 세워보면
        // i가 홀수일 경우
        // f(i) = f(i-1)
        // i-1을 만드는 모든 경우에 +1을하여 만들면 된다.
        // i가 짝수일 경우
        // f(i) = f(i-1) + f(i / 2)
        // 마찬가지로 i-1에 1을 더해 i를 만들수도 있고
        // i / 2을 만드는 경우의 모든 값들에 대해 2를 곱한 경우, 모든 수를 짝수로 통해 표현하는 경우도 가능하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 n
        int n = Integer.parseInt(br.readLine());
        int[] dp = new int[n + 1];
        // 초기값 1
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            // i가 홀수인 경우 i-1의 값을 가져오고
            // i가 짝수인 경우, i-1 값과 i/2의 값을 가져온다.
            dp[i] = (i % 2 == 0) ? dp[i - 1] + dp[i / 2] : dp[i - 1];
            dp[i] %= LIMIT;
        }
        // 답 출력
        System.out.println(dp[n]);
    }
}