/*
 Author : Ruel
 Problem : Baekjoon 17979번 What’s Mine is Mine
 Problem address : https://www.acmicpc.net/problem/17979
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17979_WhatsMineisMine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // m종류의 광물이 존재하고, 특정 시간대에 n번, 광물이 등장한다.
        // 광물에는 정해진 가격이 있고,
        // 출현하는 시간 * 정해진 가격으로 이익이 발생한다.
        // 출현 정보는 출현 시각 s, 종료 시각 e, 광물의 타입 t로 주어지낟.
        // 한 시간대에 등장하는 광물을 중간만 채굴하다, 다른 광물을 채굴한 순 없으며
        // 하나의 시간대에 등장한 광물을 모두 채굴해야한다.
        // 얻을 수 있는 최대 이익은?
        //
        // dp, 정렬 문제
        // 일단 광물의 출현 정보를 종료 시각, 종료 시각이 같다면 등장 시각에 따른 오름차순 정렬한다.
        // 그 후, 출현 정보를 살펴보며, 해당 시각까지 얻을 수 있는 최대 이익을 구해나가면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // m개의 광물, n개의 출현 정보
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        
        // 광물들의 단위 시간 당 이익
        int[] prices = new int[m + 1];
        for (int i = 1; i < prices.length; i++)
            prices[i] = Integer.parseInt(br.readLine());
        
        // 출현 정보
        int[][] appearances = new int[n][3];
        for (int i = 0; i < appearances.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < appearances[i].length; j++)
                appearances[i][j] = Integer.parseInt(st.nextToken());
        }
        // 종료 시각에 따라, 종료 시각이 같다면, 등장 시각에 따라 오름차순 정렬
        Arrays.sort(appearances, (o1, o2) -> {
            if (o1[1] == o2[1])
                return Integer.compare(o1[0], o2[0]);
            return Integer.compare(o1[1], o2[1]);
        });
        
        // dp[i] = i 시각까지 얻을 수 있는 최대 이익
        int[] dp = new int[appearances[n - 1][1] + 1];
        // 출현 정보를 모두 살펴본다.
        for (int[] appearance : appearances) {
            // 해당 출현 광물에 대한 이익
            int income = (appearance[1] - appearance[0]) * prices[appearance[2]];
            // 현 광물의 출현 시각 이전에 얻을 최대 이익 + 현 광물의 이익이
            // 현 광물의 종료 시각까지 얻을 수 있는 이익보다 크다면
            if (dp[appearance[1]] < dp[appearance[0]] + income) {
                // 해당 값으로 교체
                dp[appearance[1]] = dp[appearance[0]] + income;
                // 종료 시각 이후의 최대 이익값들에 반영
                for (int i = appearance[1] + 1; i < dp.length; i++)
                    dp[i] = Math.max(dp[i], dp[i - 1]);
            }
        }

        // 전체 종료 시각까지 얻을 수 있는 최대 이익 출력
        System.out.println(dp[dp.length - 1]);
    }
}