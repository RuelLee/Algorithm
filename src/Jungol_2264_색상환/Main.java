/*
 Author : Ruel
 Problem : Jungol 2264번 색상환
 Problem address : https://jungol.co.kr/problem/2264
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2264_색상환;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static final int LIMIT = 1_000_000_003;

    public static void main(String[] args) throws IOException {
        // n개의 색상환이 주어진다.
        // 이 중 이웃하지 않은 k개의 색을 뽑는 경우의 수를 1,000,000,003으로 나눈 나머지를 출력하라
        //
        // DP 문제
        // dp[색생환의 순서][현재 고른 색의 수][현재 색의 선택 여부] = 경우의 수로 따져가며 dp를 채운다.
        // 단, 첫번째와 마지막이 이어져있으므로 첫번째 색을 고른다면 마지막 색을 골라선 안되고
        // 첫번째 색을 선택하지 않는다면 마지막 색을 선택해도 된다.
        // 따라서 첫번째 색을 선택하지 않는 경우에 대해 dp를 채워 경우의 수를 구하고
        // 첫번째 색을 선택한 경우에 대해 dp를 채워 두 경우를 합친다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 색 중 k개를 고른다.
        int n = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());

        // 첫번째 색을 선택하지 않는 경우
        // 이전 상태만 필요하므로 dp[2]로 선택해 교차 사용한다.
        long[][][] dp = new long[2][k + 1][2];
        dp[0][0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                // 다음 색을 선택하지 않는다면
                // 현재 색을 선택했던 선택하지 않았던 상관이 없다.
                dp[(i + 1) % 2][j][0] = (dp[i % 2][j][0] + dp[i % 2][j][1]) % LIMIT;
                // 다음 색을 선택한다면 이번에는 색을 선택하지 않았어야한다.
                if (j + 1 <= k)
                    dp[(i + 1) % 2][j + 1][1] = dp[i % 2][j][0];
            }
        }
        // 첫 색을 선택하지 않았으므로, 마지막 색을 선택하든 선택하지 않던 k개의 색을 고른 경우를 구한다.
        long ans = dp[(n - 1) % 2][k][0] + dp[(n - 1) % 2][k][1];

        // dp 초기화
        for (long[][] d : dp) {
            for (long[] dd : d)
                Arrays.fill(dd, 0);
        }

        // 첫번째 색을 선택하는 경우
        dp[0][1][1] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[(i + 1) % 2][j][0] = (dp[i % 2][j][0] + dp[i % 2][j][1]) % LIMIT;
                if (j + 1 <= k)
                    dp[(i + 1) % 2][j + 1][1] = dp[i % 2][j][0];
            }
        }

        // 첫번째 색을 선택했으므로 마지막 색을 선택하지 않은 채 k개의 색이 뽑힌 경우를 누적한다.
        ans = (ans + dp[(n - 1) % 2][k][0]) % LIMIT;
        // 답 출력
        System.out.println(ans);
    }
}