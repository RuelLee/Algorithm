/*
 Author : Ruel
 Problem : Baekjoon 28129번 2022 APC가 어려웠다고요?
 Problem address : https://www.acmicpc.net/problem/28129
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28129_2022APC가어려웠다고요;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_000 + 7;

    public static void main(String[] args) throws IOException {
        // n개의 문제가 주어진다.
        // 각 문제의 난이도 di는 주어지는 두 수 ai <= di <= bi를 만족한다.
        // 인접한 문제의 난이도 차이는 k 이하를 만족한다.
        // n개의 문제들의 난이도를 결정하는 경우의 수를 출력하라
        // 10^9 + 7로 나눈 나머지 값을 출력한다.
        //
        // DP, 누적합 문제
        // dp[문제번호][난이도] = 경우의 수
        // 해당 문제의 난이도 별로 가능한 경우의 수를 구하되, 누적합을 통해서 연산을 줄이는 방법을 사용하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 문제, 인접한 문제들의 난이도 최대 차이 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 가능한 문제 난이도 범위
        int[][] problems = new int[n][2];
        for (int i = 0; i < problems.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < problems[i].length; j++)
                problems[i][j] = Integer.parseInt(st.nextToken());
        }

        long[][] dp = new long[n][3001];
        // 첫번째 문제를 가능한 난이도에 출제하는 경우의 수는 각각 하나씩.
        for (int j = problems[0][0]; j <= problems[0][1]; j++)
            dp[0][j]++;
        // 누적합 처리
        for (int i = 1; i < dp[0].length; i++)
            dp[0][i] += dp[0][i - 1];

        for (int i = 1; i < problems.length; i++) {
            // i번째는 problems[i][0] ~ problems[i][1]까지의 난이도로 설정 가능.
            // 이 범위를 만족하는 j의 경우
            // 가능한 경우의 수는
            // 이전에 설정된 문제의 난이도가
            // j - k ~ j + k까지 가능.
            // 위 경우를 누적합을 통해 계산.
            // dp[i-1][j+k] - dp[i-1][j-k-1]이나, 이 값이 나머지 연산을 취했기 때문에 음수가 될 수 있으므로
            // 보정을 해준다.
            for (int j = problems[i][0]; j <= problems[i][1]; j++)
                dp[i][j] = (dp[i - 1][Math.min(j + k, 3000)] - dp[i - 1][Math.max(0, j - k - 1)] + LIMIT) % LIMIT;
            
            // 누적합 처리
            for (int j = 1; j < dp[i].length; j++)
                dp[i][j] = (dp[i][j] + dp[i][j - 1]) % LIMIT;
        }

        // n번째 문제의 가능한 모든 경우의 수를 출력.
        System.out.println(dp[n - 1][3000]);
    }
}