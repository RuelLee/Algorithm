/*
 Author : Ruel
 Problem : Baekjoon 14728번 벼락치기
 Problem address : https://www.acmicpc.net/problem/14728
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14728_벼락치기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 단원, 공부할 수 있는 시간 t가 주어진다
        // 그리고 n개의 단원에 대해 소요 공부 시간과 문제 배점이 주어진다
        // 시험에서 얻을 수 있는 최대 점수는?
        //
        // 간단한 배낭 문제.
        // t가 1만까지, n이 100개까지 주어지므로,
        // 시간에 대해 dp를 만들고, 해당 시간 동안 얻을 수 있는 최대 점수를 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 해당 단원에 소요되는 시간, 문제 배점
        int[][] chapters = new int[n][2];
        for (int i = 0; i < chapters.length; i++) {
            st = new StringTokenizer(br.readLine());
            chapters[i][0] = Integer.parseInt(st.nextToken());
            chapters[i][1] = Integer.parseInt(st.nextToken());
        }

        // 단원 별로 DP를 세우지 말고, 시간으로만 만든다.
        int[] dp = new int[t + 1];
        // 똑같은 단원에 대해 중복해서 계산될 수 있으므로, 시간에 대해 내림차순으로 계산해나간다.
        // 먼저 모든 단원에 대해
        for (int[] chapter : chapters) {
            // 시간에 대해서는 역순으로
            for (int j = dp.length - 1; j - chapter[0] >= 0; j--)
                // 현재 dp[j]에 기록된 값은 i단원에 대해 고려하지 않았을 때, 얻을 수 있는 최대 점수이다.
                // 따라서 기존의 dp[j] 값과, (j - i단원의 공부 시간)의 점수 최대값 + i단원의 점수 중 더 큰 값을 비교해 저장한다.
                dp[j] = Math.max(dp[j], dp[j - chapter[0]] + chapter[1]);
        }
        // 최종적으로 dp[t]에 얻을 수 있는 최대 점수가 저장된다.
        // 해당 점수 출력.
        System.out.println(dp[dp.length - 1]);
    }
}