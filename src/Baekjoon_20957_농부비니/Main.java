/*
 Author : Ruel
 Problem : Baekjoon 20957번 농부 비니
 Problem address : https://www.acmicpc.net/problem/20957
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20957_농부비니;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n이 주어졌을 때, n자리 양의 정수 중 모든 숫자의 합과 곱이 7의 배수인 것의 개수를 구하라
        // 값이 클 수 있으므로 10^9 + 7로 나눈 나머지를 출력한다.
        //
        // DP 문제
        // dp[자릿수][합의나머지][7의배수여부]로 dp를 세우고 채운다.
        // 합이 7의 배수가 되는지 여부는 합의 mod 7한 결과값으로 줄여 생각할 수 있고
        // 7의 배수 여부는 한번이라도 0이나 7이 포함된 적이 있는지를 고려하면 알 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[][][] dp = new int[10001][7][2];
        // 1 자릿수일 경우엔 직접 계산.
        // 1 ~ 9까지
        for (int i = 1; i < 10; i++)
            dp[1][i % 7][i % 7 == 0 ? 1 : 0]++;
        
        // 이후는 반복문 처리
        for (int i = 1; i < dp.length - 1; i++) {
            // 합의 나머지가 j이고
            for (int j = 0; j < dp[i].length; j++) {
                // 7의 배수라면 1, 7의 배수가 아니라면 0
                for (int k = 0; k < dp[i][j].length; k++) {
                    // 경우의 수가 0인 경우 불가능한 경우이므로 건너뛴다.
                    if (dp[i][j][k] == 0)
                        continue;

                    // 뒤에 숫자 l을 덧붙여, i+1 자릿수를 만들었을 때
                    // 합의 나머지는 (j + l) % 7이 되고
                    // 7의 배수 여부는 k가 1이었거나, l이 0 혹은 7인 경우, 7의 배수가 된다.
                    // 해당 경우를 계산.
                    for (int l = 0; l < 10; l++)
                        dp[i + 1][(j + l) % 7][k == 1 || l % 7 == 0 ? 1 : 0] = (dp[i + 1][(j + l) % 7][k == 1 || l % 7 == 0 ? 1 : 0] + dp[i][j][k]) % LIMIT;
                }
            }
        }

        // t개의 테스트 케이스에 대해 답안 작성
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            // n자리일 때, 합의 7 나머지가 0이고, 7의 배수인 것의 개수를 기록
            sb.append(dp[n][0][1]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}