/*
 Author : Ruel
 Problem : Baekjoon 13707번 합분해 2
 Problem address : https://www.acmicpc.net/problem/13707
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13707_합분해2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        // 0 부터 n까지의 정수 k개를 더해서 n이 되는 경우의 수를 구한다.
        // 1 + 2 / 2 + 1은 서로 다른 경우.
        // 한 개의 수를 여러번 쓸 수 있다.
        //
        // DP 문제
        // dp[만들어야하는 수][사용한 수의 개수]
        // dp[1][3] = 3 ((1 0 0), (0 1 0), (0 0 1))
        // dp[2][2] = 3 ((2 0), (1 1), (0 2))
        // dp[2][3] = 6 ((2 0 0), (1 1 0), (1 0 1), (0 2 0), (0 1 1), (0 0 2))
        // dp[i][j] = dp[i-1][j] + dp[i][j-1]
        // dp[i][j]는 i를 j칸에 나누는 경우와 같다
        // 이 경우는 i-1이란 값을 j칸에 나눈 경우에, 첫 칸에 1을 더해 i를 만드는 경우의 수
        // + i 값을 j-1칸에 나눈 경우에 첫 칸에 0이 있는 한 칸을 더하는 경우의 수와 같다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 값 n, k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // n이라는 값을 k칸에 나누는 경우의 수를 dp로 구한다.
        int[][] dp = new int[n + 1][k + 1];
        // 1값을 i칸에 나누는 경우의 수는 i개
        for (int i = 1; i < dp[1].length; i++)
            dp[1][i] = i;
        
        // dp[만드는 수][나누는 칸]
        // dp[i-1][j] -> 첫 칸에 1을 더해 i를 만드는 경우
        // dp[i][j-1] -> 첫칸에 0이라는 칸을 하나 추가해 j칸을 만드는 경우
        for (int i = 2; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++)
                dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % LIMIT;
        }
        
        // 답을 출력
        System.out.println(dp[n][k]);
    }
}