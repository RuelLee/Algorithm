/*
 Author : Ruel
 Problem : Baekjoon 19582번 200년간 폐관수련했더니 PS 최강자가 된 건에 대하여
 Problem address : https://www.acmicpc.net/problem/19582
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19582_200년간폐관수련했더니PS최강자가된건에대하여;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 대회가 순서대로 열린다.
        // 각 대회는 xi pi 값이 주어지며
        // xi는 참가 자격으로 이전 대회들에서 얻은 상금의 총액이 xi이하여야 한다.
        // pi는 해당 대회를 우승할 경우 얻는 상금이다.
        // 최대 한 개의 대회만 참가하지 않으며, 나머지 대회에 대해 모두 참가하여 우승할 수 있는가?
        //
        // DP 문제
        // 상금을 많이 얻는 건 목적이 아니고, n-1개 혹은 n개의 대회에 참가하는 것이 목적이다.
        // 따라서 상금은 최소로 얻을 수록 유리하다.
        // dp[대회][스킵여부] = 최저 상금
        // 으로 dp를 세우고, 마지막 대회까지 살펴봤을 때, 참가할 수 있는지를 따져보면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 대회
        int n = Integer.parseInt(br.readLine());

        int[][] contests = new int[n + 1][2];
        StringTokenizer st;
        for (int i = 1; i < contests.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                contests[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // dp[대회][스킵여부] = 최저 상금
        long[][] dp = new long[n + 1][2];
        // 값 초기화
        for (long[] d : dp)
            Arrays.fill(d, Long.MAX_VALUE);
        dp[0][0] = dp[0][1] = 0;
        for (int i = 0; i < dp.length - 1; i++) {
            // i번째 대회까지 스킵을 하지 않았고, i+1번째 대회도 참가하는 경우
            if (dp[i][0] <= contests[i + 1][0])
                dp[i + 1][0] = Math.min(dp[i + 1][0], dp[i][0] + contests[i + 1][1]);
            // i+1번째 대회를 건너뛰는 경우
            if (dp[i][0] < Long.MAX_VALUE)
                dp[i + 1][1] = Math.min(dp[i + 1][1], dp[i][0]);
            // i번째 대회까지 스킵을 하지 않았고, i+1번째 대회는 스킵하는 경우
            if (dp[i][1] <= contests[i + 1][0])
                dp[i + 1][1] = Math.min(dp[i + 1][1], dp[i][1] + contests[i + 1][1]);
        }
        
        // dp[n][0], dp[n][1] 중 하나라도 초기값이 아닌 값이 있다면 모든 대회에 참가 가능한 경우
        // 둘 다 초기값이라면 불가능한 경우.
        // 답 출력
        System.out.println(Math.min(dp[n][0], dp[n][1]) < Long.MAX_VALUE ? "Kkeo-eok" : "Zzz");
    }
}