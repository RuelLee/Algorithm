/*
 Author : Ruel
 Problem : Baekjoon 15912번 우주선 만들기
 Problem address : https://www.acmicpc.net/problem/15912
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15912_우주선만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 우주선을 만드는데 총 n개의 부품이 필요하다.
        // 각 부품에는 무게 w와 에너지 e가 주어지며
        // 가격은 w * e로 정해진다.
        // 상점에 특이한 판매 방식이 있는데
        // i ~ j번까지의 부품에 대해
        // 가장 무거운 부품의 무게 * 가장 많은 에너지 값으로
        // i ~ j번 부품을 한번에 팔기도 한다.
        // 부품에는 순서가 있으며, 뒷 순서의 부품을 앞 순서의 부품보다 먼저 살 수 없다.
        // 모든 부품을 사는데 필요한 최소 비용은?
        //
        // DP 문제
        // dp[i] = i번째 부품까지는 사는데 필요한 최소 비용으로 정하고 문제를 푼다.
        // i번째 부품까지 사는 방법은
        // dp[i-1] + wi * ei
        // dp[i-2] + max(wi, wi-1) * max(ei, ei-1)
        // .. 
        // max(wi ~ w0) * max(ei ~ e0)로 계산할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 부품의 무게와 에너지
        int n = Integer.parseInt(br.readLine());
        int[] w = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] e = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp
        long[] dp = new long[n];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = (long) w[0] * e[0];
        for (int i = 1; i < dp.length; i++) {
            // i번째 부품까지 구매하는 최소 비용을 구한다.
            int maxW = 0;
            int maxE = 0;
            // 0 ~ mid 부품까지는 계산된 dp를 참고하며
            // mid+1 ~ i번째 부품은 한번에 구매할 때 가격을 비교한다.
            for (int mid = i - 1; mid >= 0; mid--) {
                // mid +1 ~ i번째 부품까지의 최대 무게와 에너지
                maxW = Math.max(maxW, w[mid + 1]);
                maxE = Math.max(maxE, e[mid + 1]);
                
                // mid까지는 dp 값 사용
                // mid + 1 ~ i번째 부품은 한꺼번에 구매
                dp[i] = Math.min(dp[i], dp[mid] + (long) maxW * maxE);
            }
            // 0 ~ i번 부품까지 모두 한꺼번에 구매하는 경우.
            maxW = Math.max(maxW, w[0]);
            maxE = Math.max(maxE, e[0]);
            dp[i] = Math.min(dp[i], (long) maxW * maxE);
        }
        // 모든 부품을 구매하는 최소 비용 출력
        System.out.println(dp[n - 1]);
    }
}