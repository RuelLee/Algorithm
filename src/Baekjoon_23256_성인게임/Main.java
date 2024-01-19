/*
 Author : Ruel
 Problem : Baekjoon 23256번 성인 게임
 Problem address : https://www.acmicpc.net/problem/23256
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23256_성인게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 칼의 칼날은 2 x 2 크기의 정사각형 n개가 한 칸씩 맞물려 연결되는 형태다
        // 크기가 3인 칼날
        //   ◇  ◇  ◇
        // ◇  ◇  ◇  ◇
        //   ◇  ◇  ◇
        // 1 x 2 내지 1 x 1 의 광석을 붙여 칼날을 만든다.
        // t개의 길이 n 칼날을 각각 만드는 방법을 1_000_000_0007로 나눈 나머지를 출력하라
        //
        // DP 문제
        // 길이 n의 칼날을 만들 때, 마지막 칸이 차있는지 혹은 비어있는지를 구분하여 계산했다.
        // 마지막 칸이 비어있다면 어차피 1 x 1짜리 광석으로 채워서 칼날을 만들 수 있기 때문.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());

        // 최대 길이 100만까지. 한번에 모두 구한다.
        long[][] dp = new long[1_000_001][2];
        // 길이가 0인 칼날의 가짓수 1가지.
        dp[0][0] = 1;
        for (int i = 0; i < dp.length - 1; i++) {
            // 길이 i + 1의 칼날의 마지막 칸을 비우는 경우
            // 길이 i인 칼날이 비어있는 경우에서
            // 위쪽이나 아래쪽으로 1 * 2 광석을 배치하고 나머지를 1 x 1로 채우는 경우
            // + 위 아래를 1 x 1광석으로 배치하는 경우
            // 와 마지막 칸이 차있는 경우에서 위 아래를 1 * 1 광석으로 채우는 경우.
            dp[i + 1][0] += dp[i][0] * 3 + dp[i][1];
            dp[i + 1][0] %= LIMIT;

            // 길이 i + 1의 칼날이 마지막 칸이 채우는 경우.
            // 마지막칸이 비어있는 상태에서 1 * 2 길이의 광석 2개로 채우는 경우 2가지 +
            // 1 * 2 길이 광석 1개와 1 * 1 광석 2개로 채우는 경우
            // 와 마지막 칸이 차있는 상태에서
            // 1 * 2 광석과 1 * 1 광석 한 개씩으로 채우는 경우.
            dp[i + 1][1] += dp[i][0] * 4 + dp[i][1] * 2;
            dp[i + 1][1] %= LIMIT;
        }
        
        // 답안 기록
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            int n = Integer.parseInt(br.readLine());
            // 답은 마지막 칸이 비어있는 경우와 차 있는 경우의 합으로 기록한다.
            // 마지막 칸이 비어있을 경우, 1 * 1 광석을 채워넣으면 되기 때문.
            sb.append((dp[n][0] + dp[n][1]) % LIMIT).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}