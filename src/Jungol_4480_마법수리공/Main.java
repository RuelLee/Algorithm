/*
 Author : Ruel
 Problem : Jungol 4480번 마법 수리공
 Problem address : https://jungol.co.kr/problem/4480
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4480_마법수리공;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수리가게에 n명이 서있으며, i번째 손님이 가진 물건의 가치는 ci이다.
        // 해당 물건을 수리하면 ci만큼 마법력을 소모하며, ci만큼 돈을 받는다.
        // 처음 마법력은 k만큼 갖고 있고, 물건을 수리하지 않고 그 시간 동안 명상을 하면 m만큼 마법력을 높일 수 있다.
        // 모든 손님을 처리했을 때, 얻을 수 있는 최대 이익은?
        //
        // DP문제
        // dp[turn][마법력] = 최대 이익 으로 놓고 계산한다.
        // 이전 결과만 필요하므로 dp[2]로 선언하고 두 개의 배열을 번갈아가며 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 손님, 명상 시 마법력 회복량 m, 처음의 마법력 k
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 물건들
        int[] stuffs = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            stuffs[i] = Integer.parseInt(st.nextToken());

        // 두 개의 배열을 번갈아가며 계산
        int[][] dp = new int[2][k + n * m + 1];
        for (int i = 0; i < dp.length; i++)
            Arrays.fill(dp[i], -1);
        // 초기값
        dp[0][k] = 0;
        for (int i = 0; i < n; i++) {
            // 현재 위치, 다음 위치
            int cur = i % 2;
            int next = (i + 1) % 2;
            for (int j = 0; j < dp[i % 2].length; j++) {
                // -1인 경우, 불가능한 경우이므로 건너뜀
                if (dp[cur][j] == -1)
                    continue;

                // 명상하는 경우
                dp[next][j + m] = Math.max(dp[next][j + m], dp[cur][j]);
                // 수리하는 경우
                if (j - stuffs[i] >= 0)
                    dp[next][j - stuffs[i]] = Math.max(dp[next][j - stuffs[i]], dp[cur][j] + stuffs[i]);
            }
        }

        // 가장 높은 이익을 찾아
        int ans = 0;
        for (int i = 0; i < dp[n % 2].length; i++)
            ans = Math.max(ans, dp[n % 2][i]);
        // 출력
        System.out.println(ans);
    }
}