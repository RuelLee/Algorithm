/*
 Author : Ruel
 Problem : Jungol 1220번 최장 공통 부분서열
 Problem address : https://jungol.co.kr/problem/1220
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1220_최장공통부분서열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 개의 문자열이 입력될 때,
        // 각 문자열에서 임의의 문자열을 제거해 서로 같은 문자열을 만들고자할 때, 그 최대 길이는?
        //
        // dp 문제
        // dp[첫번째문자열의순서][두번째문자열의순서] = 같은 문자열의 최대 길이

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 두 개의 문자열
        String[] words = new String[2];
        for (int i = 0; i < words.length; i++)
            words[i] = br.readLine();

        // 각각의 문자열 길이로 2차원 dp를 생성
        int[][] dp = new int[words[0].length() + 1][words[1].length() + 1];
        // 각 문자열을 살펴보며
        for (int i = 1; i <= words[0].length(); i++) {
            // 첫번째 문자열의 i번째 문자
            for (int j = 1; j <= words[1].length(); j++) {
                // 두번째 문자열의 j번째 문자 순서

                // 이전 dp의 값을 가져오고
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                // 이번 순서의 문자가 같다면, dp[i-1][j-1] + 1 값과 현재 값을 비교한다.
                if (words[0].charAt(i - 1) == words[1].charAt(j - 1))
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
            }
        }
        // 모든 작업이 끝났을 때의 값 출력
        System.out.println(dp[words[0].length()][words[1].length()]);
    }
}