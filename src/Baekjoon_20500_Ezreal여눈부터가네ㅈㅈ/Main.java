/*
 Author : Ruel
 Problem : Baekjoon 20500번 Ezreal 여눈부터 가네 ㅈㅈ
 Problem address : https://www.acmicpc.net/problem/20500
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20500_Ezreal여눈부터가네ㅈㅈ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n자리의 1과 5로만 이루어진 수 중 15의 배수를 출력하라
        //
        // DP 문제
        // dp[자릿수][15로 나눈 나머지] = 개수로 dp를 정하고 문제를 푼다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n자리의 수
        int n = Integer.parseInt(br.readLine());
        
        // dp[자릿수][15로 나눈 나머지] = 개수
        int[][] dp = new int[n + 1][15];
        // 0자리의 수는 없지만 계산 편의상.
        dp[0][0] = 1;
        for (int i = 0; i < dp.length - 1; i++) {
            // i자리의 수들 중
            for (int j = 0; j < dp[i].length; j++) {
                // 15로 나눈 나머지가 j인 수
                // 그러한 수가 0개라면 건너뛴다.
                if (dp[i][j] == 0)
                    continue;

                // 그러한 수 뒤에 1을 붙인 경우
                // 나머지는 10배 후, 1을 더한 값의 나머지와 같다.
                dp[i + 1][(j * 10 + 1) % 15] += dp[i][j];
                dp[i + 1][(j * 10 + 1) % 15] %= LIMIT;
                // 그러한 수 뒤에 5를 붙인 경우
                dp[i + 1][(j * 10 + 5) % 15] += dp[i][j];
                dp[i + 1][(j * 10 + 5) % 15] %= LIMIT;
            }
        }
        // n자리의 수 중 15로 나눈 나머지가 0인 수(= 15의 배수인 수)의 개수
        System.out.println(dp[n][0]);
    }
}