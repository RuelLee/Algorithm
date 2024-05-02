/*
 Author : Ruel
 Problem : Baekjoon 15989번 1, 2, 3 더하기 4
 Problem address : https://www.acmicpc.net/problem/15989
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15989_123더하기4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 정수 n에 대해, 1, 2, 3의 합으로 표현하는 방법의 개수를 계산하라
        // 합을 이루는 수의 순서만 다른 경우는 같은 경우로 센다.
        //
        // DP 문제
        // 작은 수부터 합으로 만들 수 있는 방법의 수를 DP로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스의 수
        int t = Integer.parseInt(br.readLine());
        
        // DP
        // 해당하는 수를 합으로 만들어내는 방법의 수
        int[] dp = new int[10_001];
        // 0을 만드는 방법은 하나도 안 더하는 한가지 방법
        dp[0] = 1;
        // 1부터 3까지 합으로 더할 수를 순서대로 살펴보며
        for (int i = 1; i < 4; i++) {
            // j + i를 만드는 방법은
            // j에서 i를 더해 만드는 방법
            // 따라서 dp[i + j]를 만드는 방법 중 몇가지는
            // dp[j]를 이루는 각각의 경우의 수도 포함된다.
            for (int j = 0; j + i < dp.length; j++)
                dp[i + j] += dp[j];
        }

        // dp를 통해 가능한 값의 범위에 대해 모두 답을 찾았다.
        // 해당 답을 찾아 출력만 해주면 된다.
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            int n = Integer.parseInt(br.readLine());
            sb.append(dp[n]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}