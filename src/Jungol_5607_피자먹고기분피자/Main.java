/*
 Author : Ruel
 Problem : Jungol 5607번 피자먹고 기분 피자
 Problem address : https://jungol.co.kr/problem/5607
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5607_피자먹고기분피자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 피자가 n 종류, 소지금 m이 주어진다.
        // 각 피자마다 가격과 맛이 주어진다.
        // 최소 k이상의 맛의 합을 갖도록 서로 다른 피자를 주문하는데 필요한 최소 금액은 얼마인가
        //
        // 배낭 문제
        // dp[가격] = 맛의 합으로 놓고 배낭 문제 풀듯 푼다.
        // 그리고 맛의 합이 k이상인 최저 가격을 찾아 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 소지금 m, 원하는 맛의 합 k, 피자의 종류 n
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(br.readLine());

        int[] dp = new int[m + 1];
        int[] pizza = new int[2];
        for (int i = 0; i < n; i++) {
            // 피자 입력
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < pizza.length; j++)
                pizza[j] = Integer.parseInt(st.nextToken());

            // 배낭
            for (int j = m - pizza[0]; j >= 0; j--)
                dp[j + pizza[0]] = Math.max(dp[j + pizza[0]], dp[j] + pizza[1]);
        }

        // k이상인 최저 가격을 찾아
        int ans = -1;
        for (int i = 0; i <= m; i++) {
            if (dp[i] >= k) {
                ans = i;
                break;
            }
        }
        // 출력
        System.out.println(ans == -1 ? ":(" : ans);
    }
}