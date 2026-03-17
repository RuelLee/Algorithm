/*
 Author : Ruel
 Problem : Baekjoon 11056번 두 부분 문자열
 Problem address : https://www.acmicpc.net/problem/11056
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11056_두부분문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 문자열 A와 B가 주어질 때, A와 B를 부분문자열로 갖는 가장 짧은 문자열 S의 길이를 구하라
        //
        // DP 문제
        // A와 B의 모든 문자가 S에 순서에 맞게 포함되어야한다.
        // 따라서 dp를 통해
        // dp[A문자열의 순서][B문자열의 순서] = S의 길이로 정하여
        // 각 A와 B의 해당 순서의 문자까지 포함하는 S의 최소 길이를 구한다.
        // 각 순서에서 A 혹은 B에 해당하는 문자를 하나 추가할 수 있고
        // 두 순서의 문자가 서로 같다면 길이는 하나만 증가시키고 각 순서를 동시에 증가시킬 수 있다.
        // 최종적으로 마지막 위치에 적힌 길이가 최소 길이
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각 문자열
        String a = br.readLine();
        String b = br.readLine();
        
        // dp[현재까지 본 A의 문자 순서][현재까지 본 B 문자의 순서] = S의 최소 길이
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        // 초기화
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[0][0] = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // A의 i번째 문자를 S에 추가하는 경우
                if (i + 1 < dp.length)
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);
                // B의 j번째 문자를 S에 추가하는 경우
                if (j + 1 < dp[i].length)
                    dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1);

                // A의 i번째, B의 j번째 문자가 같아 동시에 순서를 증가시키며
                // 길이는 1만 증가시키는 경우
                if (i + 1 < dp.length && j + 1 < dp[i].length &&
                        a.charAt(i) == b.charAt(j))
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j] + 1);
            }
        }
        // 답 출력
        System.out.println(dp[a.length()][b.length()]);
    }
}