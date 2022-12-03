/*
 Author : Ruel
 Problem : Baekjoon 5582번 공통 부분 문자열
 Problem address : https://www.acmicpc.net/problem/5582
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5582_공통부분문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 문자열이 주어졌을 때, 두 문자열에 모두 포함된 가장 긴 공통 부분 문자열의 길이를 출력하라
        //
        // DP 문제
        // 문자열의 길이가 최대 4000으로 주어진다
        // 일일이 계산해서는 O(n^3)으로 시간이 오래 소요된다.
        // DP를 통해 이전 결과를 활용하는 방법으로 계산한다.
        // A문자열의 i번째, B문자열의 j번째 문자가 같다면
        // i
        // A의 i - 1번째, B의 j - 1번째 문자열에 DP값이 저장되어있는지 확인하고
        // 해당 길이에 + 1한 값을 DP[i][j]에 저장한다.
        // 최종적으로 가장 큰 dp값이 가장 긴 공통 문자열의 길이.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String a = br.readLine();
        String b = br.readLine();

        // dp값 중 최대값을 max에 기록한다.
        int max = 0;
        // 세로는 a문자열, 가로는 b문자열

        int[][] dp = new int[a.length()][b.length()];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // a의 i번째 문자와 b의 j번째 문자가 같다면
                if (a.charAt(i) == b.charAt(j)) {
                    // 두 문자열 모두 첫 문자가 아니라면
                    // dp[i - 1][j - 1]에 기록된 길이에 + 1한 값을
                    // dp[i][j]에 저장하고
                    if (i - 1 >= 0 && j - 1 >= 0)
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                        // 두 문자 중 하나라도 처음이라면 1을 기록한다.
                    else
                        dp[i][j] = 1;
                    // 현재 길이가 max를 갱신하는지 확인.
                    max = Math.max(max, dp[i][j]);
                }
            }
        }

        // max값 출력.
        System.out.println(max);
    }
}