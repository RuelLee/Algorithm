/*
 Author : Ruel
 Problem : Baekjoon 1106번 호텔
 Problem address : https://www.acmicpc.net/problem/1106
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1106_호텔;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 호텔에서 고객을 최소 c명 늘리고 싶다.
        // 그리고 n개의 도시에 대해 홍보 비용과 그로 인해 늘어나는 고객의 수가 주어진다
        // 한 도시에 홍보를 반복적으로 할 수 있으며, 그 효과도 같다고 할 때
        // 최소 c명의 고객을 늘리기 위해 사용해야하는 최소 홍보 비용은 얼마인가? (늘어나는 고객의 수는 100보다 같거나 작은 자연수)
        //
        // DP를 활용한 배낭 문제
        // 하나 주의할 점은, 최소 c명이지, c명을 초과하더라도 비용이 더 적다면 그 값을 출력해야한다
        // 따라서 dp를 선언할 때, 크기를 c명이 아니라 c + 100 으로 선언하여 c를 초과할 때의 비용도 살펴보자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int c = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 도시의 홍보 비용과 효과
        int[][] cities = new int[n][2];
        for (int i = 0; i < cities.length; i++)
            cities[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 인원 수에 따른 최소 비용을 계산할 DP
        // 먼저 큰 값으로 초기화해두고, 0명일 때는 0원이다.
        int[] dp = new int[c + 100];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        // 각 도시에 따른 홍보를 각각 계산한다.
        for (int[] city : cities) {
            // 비용과 효과
            int cost = city[0];
            int newCustomer = city[1];

            // 최소 newCustomer 만큼의 고객이 새로 생긴다.
            // 만약 j - newCustomer의 계산된 값이 없다면 건너뛰어야한다.
            // j명의 고객을 모집하는 비용이 기존에 계산된 값보다
            // j - newCustomer명 고객에서 이번 홍보를 통해 cost 가격에 newCustomer명만큼 늘리는 것이 더 싸다면
            // 해당 값으로 바꿔준다.
            for (int j = newCustomer; j < dp.length; j++) {
                if (dp[j - newCustomer] != Integer.MAX_VALUE &&
                        dp[j - newCustomer] + cost < dp[j])
                    dp[j] = dp[j - newCustomer] + cost;
            }
        }

        // c부터 ~ dp의 끝까지 가격을 살펴보며 최소값을 찾아 출력한다.
        int minCost = Integer.MAX_VALUE;
        for (int i = c; i < dp.length; i++)
            minCost = Math.min(minCost, dp[i]);
        System.out.println(minCost);
    }
}