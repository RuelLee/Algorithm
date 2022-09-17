/*
 Author : Ruel
 Problem : Baekjoon 15486번 퇴사 2
 Problem address : https://www.acmicpc.net/problem/15486
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15486_퇴사2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n일 뒤에 퇴사를 한다.
        // n일 동안 가능한 상담들이 주어진다.
        // 상담은 상담 기간과 상담 금액으로 주어진다.
        // 퇴사 전에 가장 많은 상담 금액을 얻고자 한다면 얼마를 얻을 수 있는가?
        //
        // DP문제
        // dp[day]로 day날까지 얻을 수 있는 최대 상담금액이라고 세우고
        // 순차적으로 상담들을 고려하며 DP를 채워나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 상담들
        int[][] works = new int[n][2];
        for (int i = 0; i < works.length; i++)
            works[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // n일까지 일하므로, n + 1일에 최종적으로 얻을 수 있는 금액을 구하자.
        int[] dp = new int[n + 1];

        // 0일에 시작하는 첫번째 상담이 n + 1일 전에 끝난다면
        // 해당 하는 dp에 상담 금액을 채우자.
        if (works[0][0] < dp.length)
            dp[works[0][0]] = works[0][1];
        // 두번째 상담부터 순차적으로 상담들을 살펴본다.
        for (int i = 1; i < works.length; i++) {
            // i - 2일까지 상담을 마치고 얻은 금액 dp[i - 1]과
            // i - 1일까지 상담을 마치고 얻은 금액 dp[i]를 비교해 큰 값을 남겨두자
            // 어차피 두 가지 경우 모두 오늘부터 상담을 받을 수 있기 때문이다.
            dp[i] = Math.max(dp[i], dp[i - 1]);
            // 오늘부터 가능한 상담이 퇴사일을 넘지 않는다면
            // 오늘 가능한 상담이 마치는 날짜 + 1일에 dp[오늘] + 상담 금액과 기존에 저장되어있던 금액을 비교하여
            // 더 큰 값을 남겨둔다.
            if (i + works[i][0] < dp.length)
                dp[i + works[i][0]] = Math.max(dp[i + works[i][0]], dp[i] + works[i][1]);
        }

        // 모든 상담을 살펴보는 것이 끝났다면, 정확히 n일에 끝나는 경우는 dp[n]에 값이 기록되지만
        // 그 전에 상담이 끝난 경우에는 dp[n - 1]에 기록되어있을 것이다.
        // 두 가지 모두 가능한 경우이므로 둘 중에 큰 값을 출력한다.
        System.out.println(Math.max(dp[dp.length - 2], dp[dp.length - 1]));
    }
}