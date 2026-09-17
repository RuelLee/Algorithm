/*
 Author : Ruel
 Problem : Jungol 5691번 RGB거리
 Problem address : https://jungol.co.kr/problem/5691
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5691_RGB거리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int BIG = 1_000_000;

    public static void main(String[] args) throws IOException {
        // n개의 집이 일렬로 늘어서있다.
        // 각 집을 R, G, B 색깔로 칠하는 비용이 각각 주어진다.
        // 이웃한 두 집 그리고 마지막과 첫 집의 색이 서로 다르게 칠하고자할 때, 최소 비용은?
        //
        // DP 문제
        // dp[i][j] = i번째 집을 j색으로 칠할 때의 최소 비용으로 정하고 값을 구한다.
        // 대신, 첫 집과 마지막 집을 서로 다른 색으로 칠해야하므로,
        // 첫 집을 R, G, B 각각 색으로 칠했을 때를 각각 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 집
        int n = Integer.parseInt(br.readLine());
        // 각 집을 칠하는 비용
        int[][] costs = new int[n][3];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++)
                costs[i][j] = Integer.parseInt(st.nextToken());
        }

        int ans = Integer.MAX_VALUE;
        // dp
        int[][] dp = new int[n][3];
        // 첫 집을 i색으로 칠한다.
        for (int i = 0; i < 3; i++) {
            Arrays.fill(dp[0], BIG);
            Arrays.fill(dp[1], BIG);
            dp[0][i] = costs[0][i];

            // 두번째 ~ n번째 집까지 색을 칠한다.
            for (int j = 1; j < dp.length; j++) {
                for (int k = 0; k < dp[j].length; k++)
                    dp[j][k] = Math.min(dp[j - 1][(k + 1) % 3], dp[j - 1][(k + 2) % 3]) + costs[j][k];
            }

            // 첫번째 집과 마지막 집을 다른 색으로 칠한 최소 비용을 반영한다.
            ans = Math.min(ans, Math.min(dp[n - 1][(i + 1) % 3], dp[n - 1][(i + 2) % 3]));
        }
        // 답 출력
        System.out.println(ans);
    }
}