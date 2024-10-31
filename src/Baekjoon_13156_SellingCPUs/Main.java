/*
 Author : Ruel
 Problem : Baekjoon 13156번 Selling CPUs
 Problem address : https://www.acmicpc.net/problem/13156
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13156_SellingCPUs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // c개의 cpu를 갖고 있고, m개의 구입처가 있다.
        // 각 구입처에 따라 1 ~ c개의 cpu를 한번에 매입하는 가격이 주어진다.
        // 구입처마다 한번씩만 판매가 가능하다고 할 때
        // 얻을 수 있는 최대 이익은?
        //
        // 배낭문제
        // dp[i] = i개 이하의 cpu를 팔았을 때, 얻을 수 있는 최대 수익
        // 이라 정하고
        // 내림차순으로 살펴가며, 구입처에 cpu를 팔았을 때 얻을 수 있는 최대 수익을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // c개의 cpu, m개의 구입처
        int c = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 구입처의 구입 가격
        int[][] merchants = new int[m][c];
        for (int i = 0; i < merchants.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < merchants[i].length; j++)
                merchants[i][j] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[c + 1];
        // i번째 구입처
        for (int i = 0; i < merchants.length; i++) {
            // 현재 j개이하의 cpu를 판 상태이고
            for (int j = c - 1; j >= 0; j--) {
                // 추가로 k개의 cpu를 i번째 구입처에 팔 때
                // 얻을 수 있는 최대 이익
                for (int k = 1; j + k < dp.length; k++)
                    dp[j + k] = Math.max(dp[j + k], dp[j] + merchants[i][k - 1]);
            }
        }
        // 총 c개 이하의 cpu를 팔았을 때
        // 얻을 수 있는 최대 이익
        System.out.println(dp[c]);
    }
}