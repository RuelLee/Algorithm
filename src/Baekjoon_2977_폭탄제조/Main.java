/*
 Author : Ruel
 Problem : Baekjoon 2977번 폭탄제조
 Problem address : https://www.acmicpc.net/problem/2977
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2977_폭탄제조;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 하나의 폭탄을 만드는데는 n 종류의 부품이 필요하다
        // 각 부품에 대한 정보들이 
        // X Y Sm Pm Sv Pv 형태로 주어진다.
        // X = 필요한 부품의 수, Y = 창고에 이미 있는 재고
        // Sm = 소형 패키지의 부품 수, Pm = 소형 패키지 가격
        // Sv = 대형 패키지의 부품 수, Pv = 대형 패키지 가격
        // m원을 갖고 있을 때, 만들 수 있는 폭탄의 최대 개수는?
        //
        // 이분 탐색 + 배낭 문제
        // 이분 탐색을 통해 만들 수 있는 폭탄의 개수의 범위를 좁혀가며
        // 소형과 대형 패키지 중 어느 것을 몇개씩 살지는 배낭 문제를 통해 푼다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 폭탄, 소지금 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 부품의 종류
        int[][] parts = new int[n][];
        for (int i = 0; i < parts.length; i++)
            parts[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 이분 탐색
        int start = 0;
        int end = 100000;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (possible(mid, m, parts))
                start = mid + 1;
            else
                end = mid - 1;
        }
        // 만들 수 있는 폭탄의 수 출력
        System.out.println(end);
    }

    // num개의 폭탄을 만들 때
    // 최소금으로 부품들을 구매할 경우, m원을 넘는지 확인한다.
    static boolean possible(int num, int m, int[][] parts) {
        int sum = 0;
        // 각 파츠 별로
        for (int[] part : parts) {
            // 필요한 부품의 수
            int need = part[0] * num - part[1];

            // 배낭 문제
            // 총 구매하는 부품의 수가 need개를 넘을 수도 있다.
            // 하지만 그 개수는 need + 대형 패키지 부품의 수 보다는 작다.
            int[] dp = new int[need + 1 + part[4]];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;
            
            // 소형 패키지
            for (int i = 0; i < need; i++) {
                if (dp[i] == Integer.MAX_VALUE)
                    continue;
                dp[i + part[2]] = Math.min(dp[i + part[2]], dp[i] + part[3]);
            }
            // 대형 패키지
            for (int i = 0; i < need; i++) {
                if (dp[i] == Integer.MAX_VALUE)
                    continue;
                dp[i + part[4]] = Math.min(dp[i + part[4]], dp[i] + part[5]);
            }

            // need ~ need + 대형 패키지의 수 -1까지의 범위에서
            // 최소값을 찾는다.
            int cost = Integer.MAX_VALUE;
            for (int i = need; i < dp.length; i++)
                cost = Math.min(cost, dp[i]);
            // 총 비용에 추가
            sum += cost;
            // 만약 sum이 m을 넘어버렸다면 반복문 종료
            if (sum > m)
                break;
        }
        // 가격의 총합이 m이하라면 가능
        // 그렇지 않다면 불가능을 반환.
        return sum <= m;
    }
}