/*
 Author : Ruel
 Problem : Baekjoon 1988번 낮잠 시간
 Problem address : https://www.acmicpc.net/problem/1988
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1988_낮잠시간;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 시간을 n개의 구간으로 나누어 그중 b개의 구간동안 낮잠을 자려고 한다.
        // 각 시각마다 피로회복도가 다르며 그 값이 주어진다.
        // 낮잠을 자기 시작한 첫 한시간 동안은 잠에 들기 위한 준비시간으로 피로회복이 되지 않는다.
        // 낮잠은 굳이 연속한 구간으로 잘 필요는 없다.
        // 얻을 수 있는 최대 피로회복도는?
        //
        // DP 문제
        // 연속한 구간들 중 첫 구간에서는 피로회복이 되지 않음에 유의하며 DP를 세우고 값을 채운다.
        // dp[구간][현재잔구간의수][이번구간을잤는지여부] = 최대피로회복도
        // 로 세워서, 이번 구간에 잤는지 여부를 구분하여, 첫구간인지 여부를 파악한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 구간, b개의 자려는 구간
        int n = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 피로회복도
        int[] recoveries = new int[n];
        for (int i = 0; i < recoveries.length; i++)
            recoveries[i] = Integer.parseInt(br.readLine());

        int[][][] dp = new int[n][b + 1][2];
        // 최대 피로 회복도
        int answer = Integer.MIN_VALUE;
        
        // 첫번째 구간은 어떤 경우에도 값이 생길 수는 없다.
        // 자는데 준비하는 시간이 필요하기 때문
        for (int i = 1; i < dp.length - 1; i++) {
            // i번째 구간까지 두 번 잤지만, i번째 구간에서 자지 않는 경우는
            // i 이전 구간에서 이미 2번 잔 경우.
            dp[i][2][0] = Math.max(dp[i][2][0],
                    Math.max(dp[i - 1][2][0], dp[i - 1][2][1]));
            // i번째 구간이 두번째로 자는 구간인 경우
            // 비연속적이라면 값은 0이므로 고려하지 않아도 되며
            // 연속할 경우, i번째 구간 회복도만큼이 값이 된다.
            dp[i][2][1] = recoveries[i];

            // dp[i] 값을 바탕으로 dp[i+1]의 값을 채운다.
            for (int j = 2; j < dp[i].length; j++) {
                // i+1번째 구간까지 j번 잠을 자고서, i+1번째 구간에서는 자지 않는 경우
                // i번째 구간까지 j번 잠을 잔 경우. i번째 구간에서 잤는지 여부는 중요 x
                dp[i + 1][j][0] = Math.max(dp[i + 1][j][0],
                        Math.max(dp[i][j][0], dp[i][j][1]));

                // i+1번째 구간까지 j번 잠을 자고서, i+1번째 구간에서도 자는 경우
                // i번째 구간까지 j번 잠을 자고서, i번째 구간에선 잠을 자지 않았던 경우
                // -> i+1번째 구간이 다시 잠이 들기 시작하는 첫번째 구간이므로 피로회복 x
                // i번째 구간까지 j번 잠을 자고서, i번째 구간에서 잠을 잤던 경우
                // -> 연속하여 잠을 자는 경우이므로 i+1번째 피로회복량만큼 누적
                if (j + 1 <= b)
                    dp[i + 1][j + 1][1] = Math.max(dp[i + 1][j + 1][1],
                            Math.max(dp[i][j][0], dp[i][j][1] + recoveries[i + 1]));
            }
            // i+1번째 구간까지 b번 잔 경우
            // 최대 피로회복도를 계산
            answer = Math.max(answer,
                    Math.max(dp[i + 1][b][0], dp[i + 1][b][1]));
        }
        
        // 답 출력
        System.out.println(answer);
    }
}