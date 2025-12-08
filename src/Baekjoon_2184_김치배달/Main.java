/*
 Author : Ruel
 Problem : Baekjoon 2184번 김치 배달
 Problem address : https://www.acmicpc.net/problem/2184
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2184_김치배달;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n포기의 김치는 n개의 도시에 배달하고자 한다.
        // 도시들은 x축 위에 있으며, 각 위치와 처음 시작 위치 l이 주어진다.
        // 각 김치는 시간이 1 지날 때마다, 1만큼 쉰다.
        // 각 도시에서 배달되는 시점의 김치의 쉼 정도의 합이 최소가 되도록 하고자한다.
        // 합의 값은?
        //
        // DP 문제
        // dp[현재 배달한 왼쪽 끝 도시][현재 배달한 오른쪽 끝 도시][현재 왼쪽 끝인가 오른쪽 끝인가] = 계산된 김치의 쉼 정도 합
        // dp값에 현재 배달한 김치의 쉼 정도만 고려하면 안되고
        // 어차피 모든 김치들의 쉼 정도 합이기 때문에
        // 현재 이 김치는 배달하면, 나머지 김치들도 현재 김치를 배달하는데 걸리는 시간만큼 쉬게 된다.
        // 값을 계산할 때, 아직 배달되지 않은 전체 김치의 쉼 정도 합을 기준으로 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n포기의 김치와 n개의 도시, 현재 위치 l
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        // 각 도시의 위치
        int[] locs = new int[n];
        for (int i = 0; i < locs.length; i++)
            locs[i] = Integer.parseInt(br.readLine());
        Arrays.sort(locs);

        // dp[현재 배달한 왼쪽 끝 도시][현재 배달한 오른쪽 끝 도시][현재 왼쪽 끝인가 오른쪽 끝인가] = 계산된 김치의 쉼 정도 합
        long[][][] dp = new long[n][n][2];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++)
                Arrays.fill(dp[i][j], Long.MAX_VALUE);
        }

        // 첫 도시 방문
        for (int i = 0; i < n; i++)
            dp[i][i][0] = (long) n * Math.abs(l - locs[i]);

        // 김치를 배달한 좌우 도시의 거리가 가까운 순부터 계산
        for (int length = 0; length < n; length++) {
            // 왼쪽 끝
            for (int left = 0; left + length < n; left++) {
                // 오른쪽 끝
                int right = left + length;
                for (int c = 0; c < 2; c++) {
                    if (dp[left][right][c] == Long.MAX_VALUE)
                        continue;

                    // 현재 위치
                    int current = c == 0 ? left : right;
                    // 왼쪽에 있는 도시를 방문하는 경우
                    if (left > 0)
                        dp[left - 1][right][0] = Math.min(dp[left - 1][right][0], dp[left][right][c] + (long) (locs[current] - locs[left - 1]) * (n - (length + 1)));
                    // 오른쪽에 있는 도시를 방문하는 경우
                    if (right + 1 < n)
                        dp[left][right + 1][1] = Math.min(dp[left][right + 1][1], dp[left][right][c] + (long) (locs[right + 1] - locs[current]) * (n - (length + 1)));
                }
            }
        }
        // 모든 도시에 배달 후, 왼쪽 끝이든 오른쪽 끝이든 더 적은 값을 출력
        System.out.println(Math.min(dp[0][n - 1][0], dp[0][n - 1][1]));
    }
}