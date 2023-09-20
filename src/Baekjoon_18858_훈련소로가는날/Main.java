/*
 Author : Ruel
 Problem : Baekjoon 18858번 훈련소로 가는 날
 Problem address : https://www.acmicpc.net/problem/18858
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18858_훈련소로가는날;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 998_244_353;

    public static void main(String[] args) throws IOException {
        // 어떤 수열에서 '산'은
        // Ai-1 < Ai > Ai+1
        // 을 만족할 때 산이라고 한다.
        // 길이 n, 1이상 m이하의 정수로 이루어진 수열 중
        // 산이 없는 수열의 개수를 998,244,353으로 나눈 나머지를 출력
        //
        // DP 문제
        // DP로 이 문제를 풀고자하면
        // 위치 / 수 / 증감 상태
        // 위 3가지 상태에 대한 정보가 필요하다.
        // 따라서 3차원 배열로 DP를 세우면 문제 해결이 가능하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n, 1이상 m이하의 수열들
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // dp[길이][수][증감상태]
        // 증감 상태는 0 -> 증가, 1 -> 유지, 2 -> 감소
        long[][][] dp = new long[n][m][3];
        // 처음 수에 대해는 증감상태는 존재하지 않는다.
        // 따라서 기본이 되는 유지에 1로 초기화한다.
        for (int i = 0; i < dp[0].length; i++)
            dp[0][i][1] = 1;
        
        // 길이
        for (int i = 1; i < dp.length; i++) {
            // 현재 수
            for (int j = 0; j < dp[i].length; j++) {
                // i번째 수(j)가 i-1번째 수보다 큰 경우
                // 0 ~ j-1까지의 수(j보다 작은 수)에 대해
                // 증감 상태에 상관없이 산이 생기지 않는다.
                // 해당 하는 모든 수의 증감사태를 더해준다.
                for (int k = 0; k < j; k++)
                    dp[i][j][0] += dp[i - 1][k][0] + dp[i - 1][k][1] + dp[i - 1][k][2];
                // 모듈러 연산
                dp[i][j][0] %= LIMIT;

                // i번째 수가 i-1번째 수와 같은 경우
                // 마찬가지로 모든 증감 상태에서 산이 생기지 않는다.
                dp[i][j][1] += dp[i - 1][j][0] + dp[i - 1][j][1] + dp[i - 1][j][2];
                dp[i][j][1] %= LIMIT;

                // i번째 수가 i-1번째 수보다 작은 경우
                // 만약 i-1번째 수가 증가 상태였다면 산이 생기므로 해당 경우는 세서는 안된다.
                // 증가 상태가 아닌 1, 2번 상태만 더해준다.
                for (int k = j + 1; k < dp[i - 1].length; k++)
                    dp[i][j][2] += dp[i - 1][k][1] + dp[i - 1][k][2];
                dp[i][j][2] %= LIMIT;
            }
        }
        
        // 길이가 n이며 m이하의 자연수로 이루어진 수열 중 산이 없는 수열의 개수
        // 마지막 수가 어떤 수든, 무슨 증감상태이든 상관이 없으므로
        // 해당 하는 모든 경우의 수를 더해준다.
        long sum = 0;
        for (int i = 0; i < dp[n - 1].length; i++) {
            for (int j = 0; j < dp[n - 1][i].length; j++)
                sum += dp[n - 1][i][j];
        }
        // 모듈러 연산을 한다.
        sum %= LIMIT;
        
        // 정답 출력
        System.out.println(sum);
    }
}