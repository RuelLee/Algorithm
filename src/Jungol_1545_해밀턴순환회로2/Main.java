/*
 Author : Ruel
 Problem : Jungol 1545번 해밀턴 순환회로2
 Problem address : https://jungol.co.kr/problem/1545
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1545_해밀턴순환회로2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 지점이 주어지고, 각 지점에서 다른 지점에 이르는 비용이 주어진다.
        // 1번 지점에서 시작하여, 모든 지점을 돌고, 다시 1번 지점에 돌아온다고 할 때, 최소 비용을 구하라
        //
        // 비트마스킹, dp 문제
        // dp[비트마스킹][마지막지점] = 비용 으로 dp를 채워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 지점
        int n = Integer.parseInt(br.readLine().trim());
        // 각 지점에 이르는 비용
        int[][] adjMatrix = new int[n][n];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                adjMatrix[i][j] = Integer.parseInt(st.nextToken());
        }

        // dp[비트마스킹][마지막지점] = 비용
        int[][] dp = new int[1 << n][n];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 처음 위치
        dp[1][0] = 0;

        // 비트 상태 i
        for (int i = 1; i < dp.length; i++) {
            // 현재 위치 j
            for (int j = 0; j < dp[i].length; j++) {
                // 상태가 불가능한 경우 건너뜀
                if (dp[i][j] == Integer.MAX_VALUE)
                    continue;

                // 다음 위치 k
                for (int k = 0; k < n; k++) {
                    // 다음 위치가 이미 방문한 위치거나 j -> k로 갈 수 없는 경우
                    if ((i & (1 << k)) != 0 || adjMatrix[j][k] == 0)
                        continue;

                    // i 상태에서 j -> k로 갈 때의 비용을 구한다.
                    int bitmask = i | (1 << k);
                    dp[bitmask][k] = Math.min(dp[bitmask][k], dp[i][j] + adjMatrix[j][k]);
                }
            }
        }

        // 모든 지점을 방문한 뒤, 다시 1번 지점으로 돌아오는 비용을 구한다.
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            // 불가능한 상태이거나 다시 1번 지점으로 돌아오는 것이 불가능하다면 건너뜀
            if (dp[dp.length - 1][i] == Integer.MAX_VALUE || adjMatrix[i][0] == 0)
                continue;

            // 이 때의 비용을 반영
            ans = Math.min(ans, dp[dp.length - 1][i] + adjMatrix[i][0]);
        }
        // 답 출력
        System.out.println(ans);
    }
}