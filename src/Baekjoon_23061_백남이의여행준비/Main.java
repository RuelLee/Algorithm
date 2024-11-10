/*
 Author : Ruel
 Problem : Baekjoon 23061번 백남이의 여행 준비
 Problem address : https://www.acmicpc.net/problem/23061
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23061_백남이의여행준비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 물건, m개의 가방이 주어진다.
        // 물건은 각각 무게 w와 가치 v를 가지며
        // 가방은 각각 담을 수 있는 무게 k가 주어진다.
        // 가방의 효용성은 (담을 수 있는 가치 / 견딜 수 있는 최대 무게)로 정해진다.
        // 효용성이 가장 높은 가방을 찾아라
        //
        // 배낭 문제
        // 최대 배낭의 크기를 찾아, 해당 값을 dp의 크기로 만들고
        // 배낭 문제로 푼다.
        // 그 후, 순서대로 배낭 별로 효용성을 구해 가장 높은 효용성을 갖는 가방을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 물건, m개의 가방
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 물건들의 무게와 가치
        int[][] stuffs = new int[n][2];
        for (int i = 0; i < stuffs.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < stuffs[i].length; j++)
                stuffs[i][j] = Integer.parseInt(st.nextToken());
        }

        // 가장 큰 가방의 크기를 찾는다
        int maxBag = 0;
        // 각 가방에 담을 수 있는 무게
        int[] bags = new int[m];
        for (int i = 0; i < bags.length; i++)
            maxBag = Math.max(maxBag, bags[i] = Integer.parseInt(br.readLine()));

        // dp로 가장 큰 가방의 크기까지 계산.
        // 배낭 문제
        int[] dp = new int[maxBag + 1];
        for (int i = 0; i < stuffs.length; i++) {
            for (int j = dp.length - 1; j - stuffs[i][0] >= 0; j--)
                dp[j] = Math.max(dp[j], dp[j - stuffs[i][0]] + stuffs[i][1]);
        }

        // 가방을 순회하며, 효용성을 계산하고
        // 가장 높은 효용성을 갖는 가방을 찾는다.
        double max = 0;
        int idx = 0;
        for (int i = 0; i < bags.length; i++) {
            if (dp[bags[i]] == 0)
                continue;

            double efficiency = (double) dp[bags[i]] / bags[i];
            if (max < efficiency) {
                idx = i;
                max = efficiency;
            }
        }
        // 답 출력
        System.out.println(idx + 1);
    }
}