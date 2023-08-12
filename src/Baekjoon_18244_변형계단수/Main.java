/*
 Author : Ruel
 Problem : Baekjoon 18244번 변형 계단 수
 Problem address : https://www.acmicpc.net/problem/18244
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18244_변형계단수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 인접한 모든 자릿수의 차이가 1이 나는 수를 계단 수라고 한다.
        // 여기에 하나의 룰을 추가하여
        // 인접한 자릿수는 연속으로 3번 이상 증가, 감소할 수 없다
        // 위 조건을 만족하는 수를 변형 계단수라고 한다.
        // n이 주어질 때 가능한 변형 계단 수의 개수는?
        // 변형 계단 수는 0으로 시작이 가능하다.
        //
        // DP 문제
        // 3차원 DP를 통하여
        // dp[i][j][k] = 길이가 i이며, 끝자리 수는 j이고,
        // k는 연속하여 증가하거나 감소한 횟수를 나타낸다.(연속 증가 -> 1, 2, 연속 감소 -> 3, 4)

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 변형 계단 수의 길이 n
        int n = Integer.parseInt(br.readLine());

        // dp
        long[][][] dp = new long[n + 1][10][5];
        // 길이가 1일 경우, 모든 수는 변형 계단 수의 시작이 될 수 있다.
        // 따라서 값으로 1을 채워준다.
        for (int i = 0; i < dp[1].length; i++)
            dp[1][i][0] = 1;
        
        // 길이가 i인 변형 계단 수
        for (int i = 2; i < dp.length; i++) {
            // 끝나는 수가 j인.
            for (int j = 0; j < dp[i].length; j++) {
                // 증가 상태이려면  j - 1이 0보단 크거나 같아야한다.
                if (j - 1 >= 0) {
                    // 연속 증가가 1회인 경우
                    dp[i][j][1] += dp[i - 1][j - 1][0];
                    // 연속 감소를 끊고 증가로 바뀌는 경우.
                    for (int k = 3; k <= 4; k++)
                        dp[i][j][1] += dp[i - 1][j - 1][k];
                    dp[i][j][1] %= LIMIT;

                    // 연속 증가가 2회인 경우.
                    dp[i][j][2] += dp[i - 1][j - 1][1];
                    dp[i][j][2] %= LIMIT;
                }

                // 감소 상태이려면 j + 1이 값의 범위를 벗어나선 안된다.
                if (j + 1 < 10) {
                    // 첫 감소 상태로 진입하려면
                    // 증감소 상태가 없는 0이거나, 증가 상태를 끊고 감소 상태가 되는 경우
                    for (int k = 0; k <= 2; k++)
                        dp[i][j][3] += dp[i - 1][j + 1][k];
                    dp[i][j][3] %= LIMIT;

                    // 감소 상태가 2회째인 경우
                    dp[i][j][4] += dp[i - 1][j + 1][3];
                    dp[i][j][4] %= LIMIT;
                }
            }
        }

        // 길이가 n인 모든 변형 계단 수의 상태를 더하여 출력한다.
        long sum = 0;
        for (long[] d : dp[n])
            sum += Arrays.stream(d).sum();
        sum %= LIMIT;
        System.out.println(sum);
    }
}