/*
 Author : Ruel
 Problem : Jungol 4377번 공통부분문자열[longest common substring]
 Problem address : https://jungol.co.kr/problem/4377
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4377_공통부분문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 문자열에 포함된 가장 긴 공통 부분 문자열의 길이를 구하라
        //
        // DP 문제
        // dp[첫번째 문자열의 위치][두번째 문자열의 위치] = 최대 길이
        // 로 정하고, 문자열을 반복문으로 살펴가며
        // i, j 위치의 문자가 일치할 경우, dp[i + 1][j + 1] = dp[i][j] + 1을 해나가며 최대 길이를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 두 문자열
        String str1 = br.readLine();
        String str2 = br.readLine();

        // dp
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        int max = 0;
        // 반복문으로 살펴간다.
        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                // 두 문자가 같은 경우
                // dp[i + 1][j + 1] = dp[i][j] + 1 하며 최댓값을 계산
                if (str1.charAt(i) == str2.charAt(j))
                    max = Math.max(max, dp[i + 1][j + 1] = dp[i][j] + 1);
            }
        }
        // 답 출력
        System.out.println(max);
    }
}