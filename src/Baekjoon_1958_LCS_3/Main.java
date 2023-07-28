/*
 Author : Ruel
 Problem : Baekjoon 1958번 LCS 3
 Problem address : https://www.acmicpc.net/problem/1958
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1958_LCS_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 3개의 문자열이 주어질 때, LCS를 구하라
        //
        // DP 문제
        // 보통 대개 LCS를 구할 때, 두 개의 문자열을 가지고서만 구했다.
        // 하지만 위 경우는 3개의 문자열이 주어지는데
        // 먼저 두 문자열을 가지고서 LCS를 구하고, 차후에 3번째를 비교하는 것이 아니라
        // 3차원 DP를 세워 한번에 풀면 되는 문제였다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 3개의 문자열
        String[] inputs = new String[3];
        for (int i = 0; i < inputs.length; i++)
            inputs[i] = br.readLine();

        // 3차원 DP
        int[][][] dp = new int[inputs[0].length() + 1][inputs[1].length() + 1][inputs[2].length() + 1];
        int max = 0;
        // 첫번째 문자열의 문자 위치
        for (int i = 1; i < dp.length; i++) {
            // 두번째 문자열의 문자 위치
            for (int j = 1; j < dp[i].length; j++) {
                // 3번째 문자열의 문자 위차
                for (int k = 1; k < dp[i][j].length; k++) {
                    // 최장 공통 부분 수열을 구하는 문제이므로
                    // 연속한 문자가 아니더라도 상관이 없다.
                    // 따라서 현재 값의 기본 세팅은 이전에 계산해둔 값들 중 큰 값을 가져온다.
                    // 이전의 계산해둔 값이란, i, j, k 중 하나씩 1 작은 값인 값들 중 큰 값을 가져온다.
                    dp[i][j][k] = Math.max(dp[i][j][k], Math.max(dp[i][j][k - 1],
                            Math.max(dp[i][j - 1][k], dp[i - 1][j][k])));

                    // 만약 현재 i, j, k에서 세 문자가 일치한다면
                    // 현재 값과 dp[i-1][j-1][k-1] +1 값 중 비교하여 더 큰 값을 저장한다.
                    if (inputs[0].charAt(i - 1) == inputs[1].charAt(j - 1) &&
                            inputs[0].charAt(i - 1) == inputs[2].charAt(k - 1))
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k - 1] + 1);
                }
            }
        }
        // 최종 위치(= 모든 문자열의 모든 문자를 다 살펴본 후)의 값을 출력한다.
        System.out.println(dp[inputs[0].length()][inputs[1].length()][inputs[2].length()]);
    }
}