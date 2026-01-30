/*
 Author : Ruel
 Problem : Baekjoon 12852번 1로 만들기 2
 Problem address : https://www.acmicpc.net/problem/12852
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12852_1로만들기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 100만 이하의 자연수 n에 대해
        // 1. n이 3으로 나누어 떨어지면 3으로 나눈다.
        // 2. n이 2로 나누어 떨어지면 2로 나눈다.
        // 3. 1을 뺀다.
        // 세 가지 연산을 최소한으로 사용하여 1로 만들고자 한다.
        // 그 때 사용한 연산의 수와 거쳐간 수를 출력하라
        //
        // dp, 백트래킹 문제
        // n으로부터 줄어들 경우, 경우의 수가 여러가지이므로 어떤 갈래가 최적의 수인지 알지 못한다.
        // 반대로 1부터 3배, 2배, +1을 해나가면서 해당 수까지 도달하는 최소 연산의 수를 구하고
        // 이전 수를 기록해둔다면 쉽게 구할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 n
        int n = Integer.parseInt(br.readLine());
        // 각 수까지 도달하는 최소 연산의 수
        int[] dp = new int[n + 1];
        // 해당 수에 도달하는 최소 연산 중 직전 수
        int[] pre = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[1] = 0;
        // 1부터 n 직전까지
        for (int i = 1; i < n; i++) {
            // 3배하는 경우
            if (i * 3 <= n && dp[i * 3] > dp[i] + 1) {
                dp[i * 3] = dp[i] + 1;
                pre[i * 3] = i;
            }
            // 2배 하는 경우
            if (i * 2 <= n && dp[i * 2] > dp[i] + 1) {
                dp[i * 2] = dp[i] + 1;
                pre[i * 2] = i;
            }
            // +1 하는 경우
            if (i + 1 <= n && dp[i + 1] > dp[i] + 1) {
                dp[i + 1] = dp[i] + 1;
                pre[i + 1] = i;
            }
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 최소 연산의 횟수
        sb.append(dp[n]).append("\n");
        // 마지막 수부터 역추적하며 답안 작성
        sb.append(n);
        while (pre[n] != 0)
            sb.append(" ").append(n = pre[n]);
        // 답 출력
        System.out.println(sb);
    }
}