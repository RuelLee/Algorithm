/*
 Author : Ruel
 Problem : Jungol 3382번 팀워크
 Problem address : https://jungol.co.kr/problem/3382
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3382_팀워크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 훈련병이 주어진다.
        // 최대 k명의 연속한 훈련병을 묶어 한 팀으로 만들 수 있다.
        // 한 팀은 가장 점수가 높은 훈련병이 나머지들을 가르쳐줘, 자신과 같은 사격 점수를 맞게 해준다.
        // 모든 훈련병의 사격 점수 합이 최대가 되게끔 하고자할 때, 그 값은?
        //
        // DP 문제
        // dp[i] = i번까지 훈련병들을 팀으로 구성했을 때의 최대 점수로 한다.
        // dp[i]는 i번째 훈련병으로부터 최대 i - k + 1번째 훈련병까지 한 팀으로 묶었을 때, 얻을 수 있는 최대 점수로 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 훈련병, 한 팀의 최대 인원 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 각 훈련병의 점수
        int[] scores = new int[n];
        for (int i = 0; i < n; i++)
            scores[i] = Integer.parseInt(br.readLine());

        // dp[i] = i번째 훈련병을 팀으로 구성했을 때의 최대 점수 합
        int[] dp = new int[n];
        // dp[0]은 0번 한 명만 팀으로 이루었을 때 밖에 없음.
        dp[0] = scores[0];
        for (int i = 1; i < n; i++) {
            // i-1번까지의 점수 + i번 훈련병 한 명이 팀일 떄의 점수 합
            dp[i] = dp[i - 1] + scores[i];
            // 최대 i - k + 1번 훈련병까지
            int limit = Math.max(0, i - k + 1);
            // i번 훈련병이 속한 팀의 단일 최대 점수
            int max = scores[i];
            // j ~ i번 훈련병이 한 팀일 때의 점수
            for (int j = i - 1; j >= limit; j--) {
                // 팀 내 최대 점수
                max = Math.max(max, scores[j]);
                // 이 때의 최대 점수 합
                dp[i] = Math.max(dp[i], max * (i - j + 1) + (j - 1 >= 0 ? dp[j - 1] : 0));
            }
        }
        // n-1번까지 모든 경우가 끝났을 때, 최대 점수 합 출력
        System.out.println(dp[n - 1]);
    }
}