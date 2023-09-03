/*
 Author : Ruel
 Problem : Baekjoon 22115번 창영이와 커피
 Problem address : https://www.acmicpc.net/problem/22115
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22115_창영이와커피;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 커피가 각각 하나씩 주어진다.
        // 각 커피에 든 카페인이 주어지고, 정확히 k만큼의 카페인을 섭취하려한다.
        // 마셔야하는 커피의 최소 수는?
        //
        // 배낭 문제
        // 각 커피가 하나만 있음에 유의하고
        // DP를 통해 각 해당하는 카페인에 도달하는 최소 커피의 수를 차곡차곡 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 커피, 섭취하려는 카페인의 양 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 커피
        int[] coffees = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 0 ~ k만큼의 카페인을 섭취하는 최소 커피의 수를 계산한다.
        int[] dp = new int[k + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // 필요없는 계산을 피하기 위해
        // 현재 계산된 도달 가능한 최대 카페인을 기록한다.
        int maxCaffeine = 0;
        // 모든 커피에 대해
        for (int coffee : coffees) {
            // 현재 계산된 가장 많은 양의 카페인 양에
            // coffee를 더하는 경우부터, 0까지 게산한다.
            for (int i = Math.min(maxCaffeine, k - coffee); i >= 0; i--) {
                // 만약 i에 도달하는 방법이 없는 경우에는 초기값이 기록되어있다.
                // 위 경우 건너뛴다.
                if (dp[i] == Integer.MAX_VALUE)
                    continue;

                // i상태에서 coffee를 마실 경우
                // 카페인 섭취는 i + coffee가 되고
                // 마신 커피의 수는 dp[i] + 1이 된다.
                dp[i + coffee] = Math.min(dp[i + coffee], dp[i] + 1);
                // 가장 많이 섭취한 카페인의 양을 기록한다.
                maxCaffeine = Math.max(maxCaffeine, i + coffee);
            }
        }

        // 정확히 k만큼의 카페인을 섭취하는 것이 가능했다면
        // 초기값이 아닌 다른 값이 저장되어있다.
        // 그 경우 해당 값을 출력하고, 초기값일 경우 -1을 출력한다.
        System.out.println(dp[k] == Integer.MAX_VALUE ? -1 : dp[k]);
    }
}