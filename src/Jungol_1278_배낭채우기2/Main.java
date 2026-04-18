/*
 Author : Ruel
 Problem : Jungol 1278번 배낭채우기 2
 Problem address : https://jungol.co.kr/problem/1278
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1278_배낭채우기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 보석을 용량이 w인 배낭에 최대 가치로 채우려고 한다.
        // 각 보석은 무게 w와 가치 p가 주어진다.
        // 용량 내의 무게로 최대 가치들의 보석을 담고자한다면
        // 그 때의 가치의 합은 얼마인가?
        //
        // 배낭 문제
        // 간단한 배낭 문제
        // dp[용량] = 최대 가치의 합으로 정하고
        // 보석을 순차적으로 살펴보며, 값을 채운다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 보석, 배낭의 용량 w
        int n = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        // 각 보석
        int[][] gems = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                gems[i][j] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[w + 1];
        // 순차적으로 모든 보석을
        for (int[] gem : gems) {
            // 무게의 역순으로 dp를 채운다.
            // 정순으로 할 경우, 한 보석을 여러번 담는 오류가 생긴다.
            for (int j = dp.length - 1 - gem[0]; j >= 0; j--)
                dp[j + gem[0]] = Math.max(dp[j + gem[0]], dp[j] + gem[1]);
        }

        // 답 출력
        System.out.println(dp[w]);
    }
}