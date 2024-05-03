/*
 Author : Ruel
 Problem : Baekjoon 14505번 팰린드롬 개수 구하기 (Small)
 Problem address : https://www.acmicpc.net/problem/14505
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14505_팰린드롬개수구하기_Small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 30을 넘지 않는 문자열 s가 주어진다.
        // 이 문자열의 부분수열 중 팰린드롬이 되는 부분 수열의 개수를 계산하라
        //
        // DP 문제
        // 문자열이라 주어져서 착각했던 점이 연속한 부분 수열일거라 생각했던 점이다.
        // 그냥 부분 수열이므로 서로 떨어진 문자들로 이루어진 부분 수열이라도 상관없다.
        // dp[i][j] = i ~ j번째 문자들로 만들 수 있는 팰린드롬의 총 개수로 정의한다.
        // i == j일 경우는 1가지 이며
        // i + 1 == j 인 경우에는 i번째와 j번째 문자가 다른 경우는 2가지, 같은 경우에는 3가지이다.
        // 크기가 3이상은 부분 수열에 대해서는
        // dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1]로 찾는다.
        // 만약 i번째와 j번째 문자가 서로 같다면, dp[i+1][j-1] + 1 개만큼의 팰린드롬이 더 생기므로
        // 해당 개수를 더해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 문자열 s
        String s = br.readLine();
        
        // dp[i][j] = i ~ j번째 문자열로 만들 수 있는 팰린드롬의 수
        int[][] dp = new int[s.length()][s.length()];
        // 길이가 1일 때
        for (int i = 0; i < s.length(); i++)
            dp[i][i] = 1;
        // 길이가 2일 때
        for (int i = 0; i < s.length() - 1; i++)
            dp[i][i + 1] = (s.charAt(i) == s.charAt(i + 1) ? 3 : 2);
        
        // 길이가 3 이상인 경우
        for (int diff = 2; diff < s.length(); diff++) {
            for (int i = 0; i + diff < s.length(); i++) {
                // i ~ diff의 부분 문자열로 만들 수 있는 팰린드롬의 수는
                // i+1 ~ diff 까지와 i ~ diff -1 까지로 만들 수 있는 팰린드롬의 수에서
                // 겹치는 i+1 ~ diff-1 부분의 수를 뺀 경우와 같다.
                dp[i][i + diff] = dp[i + 1][i + diff] + dp[i][i + diff - 1] - dp[i + 1][i + diff - 1];
                // 만약 i번째 문자와 i+diff 번째 문자가 같다면
                // 두 문자 사이에 주어지는 팰린드롬의 수만큼 더 증가하고
                // i와 i+diff번째 문자 두 개로만 만드는 팰린드롬 1 경우도 추가된다.
                if (s.charAt(i) == s.charAt(i + diff))
                    dp[i][i + diff] += (dp[i + 1][i + diff - 1] + 1);
            }
        }
        // 찾은 팰린드롬의 총 개수 출력
        System.out.println(dp[0][s.length() - 1]);
    }
}