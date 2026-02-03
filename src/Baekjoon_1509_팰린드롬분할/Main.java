/*
 Author : Ruel
 Problem : Baekjoon 1509번 팰린드롬 분할
 Problem address : https://www.acmicpc.net/problem/
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1509_팰린드롬분할;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어떤 문자열을 팰린드롬으로 분할하려한다.
        //  예를 들어, ABACABA를 팰린드롬으로 분할하면, {A, B, A, C, A, B, A}, {A, BACAB, A}, {ABA, C, ABA}, {ABACABA}등이 있다.
        // 분할의 최소 개수를 출력하라
        //
        // DP 문제
        // dp를 통해, 어느 부분부터 어디까지가 팰린드롬인지 체크한다.
        // 그리고 다시 dp를 통해
        // counts[i] = i까지를 팰린드롬으로 분할했을 때 최소 개수로 정의하고 값을 채운다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어진 문자열
        String word = br.readLine();

        // 팰린드롬 여부 체크
        boolean[][] dp = new boolean[word.length()][word.length()];
        // 단일 글자는 팰린드롬
        for (int i = 0; i < word.length(); i++)
            dp[i][i] = true;
        // 두 글자가 일치한다면 팰린드롬
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) == word.charAt(i + 1))
                dp[i][i + 1] = true;
        }

        // 3 이상의 문자열 길이에 대해 팰린드롬 체크
        for (int length = 3; length <= word.length(); length++) {
            for (int i = 0; i + length - 1 < word.length(); i++) {
                if (dp[i + 1][i + length - 2] && word.charAt(i) == word.charAt(i + length - 1))
                    dp[i][i + length - 1] = true;
            }
        }

        // counts[i] = i까지를 팰린드롬들로 나눴을 때, 그 개수
        int[] counts = new int[word.length()];
        Arrays.fill(counts, Integer.MAX_VALUE);
        // 첫 단일 글자의 팰린드롬 1개
        counts[0] = 1;
        // 이후 체크
        for (int i = 1; i < counts.length; i++) {
            // 만약 첫 문자부터 i까지가 하나의 팰린드롬인 경우
            // 1로 채우고 건너뛰기
            if (dp[0][i]) {
                counts[i] = 1;
                continue;
            }

            // 그 외의 경우
            // j+1 ~ i까지가 팰린드롬인 경우
            // counts[i]의 최솟값이 counts[j] + 1인지 확인
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j + 1][i])
                    counts[i] = Math.min(counts[i], counts[j] + 1);
            }
        }
        // 답 출력
        System.out.println(counts[word.length() - 1]);
    }
}