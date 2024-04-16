/*
 Author : Ruel
 Problem : Baekjoon 9764번 서로 다른 자연수의 합
 Problem address : https://www.acmicpc.net/problem/9764
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9764_서로다른자연수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 100999;

    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 수 n을 서로 다른 자연수의 합으로 표현할 때
        // 몇가지의 수로 표현할 수 있는지 구하라.
        //
        // DP 문제
        // dp[i][j] = 수 i를 만드는데 1 ~ j까지의 수를 사용하여 만든 경우의 수
        // 로 정의하고 문제를 푼다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());

        // dp 정의
        int[][] dp = new int[2001][];
        // i의 수를 만드는데 i보다 큰 수를 사용할 필요는 없다.
        for (int i = 0; i < dp.length; i++)
            dp[i] = new int[i + 1];
        
        // 초기값
        dp[0][0] = 1;
        // i를 만드는데
        for (int i = 1; i < dp.length; i++) {
            // j까지의 수를 사용한 경우의 수
            // j-1까지의 수를 사용한 경우의 수 = dp[i][j - 1]
            // dp[i-j]는 최대 i-j열까지만 가지므로
            // 수 j를 사용한 경우의 수 = dp[i - j][j-1과 i-j중 작은 값]
            // 두 값을 더해 모듈러 연산.
            for (int j = 1; j <= i; j++)
                dp[i][j] = (dp[i][j - 1] + dp[i - j][Math.min(i - j, j - 1)]) % LIMIT;
        }

        StringBuilder sb = new StringBuilder();
        // t개의 테스트케이스 처리
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            sb.append(dp[n][n]).append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }
}