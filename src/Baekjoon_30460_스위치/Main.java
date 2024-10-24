/*
 Author : Ruel
 Problem : Baekjoon 30460번 스위치
 Problem address : https://www.acmicpc.net/problem/30460
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30460_스위치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n초 동안 게임을 진행한다.
        // 각 초마다 얻을 수 있는 점수가 주어진다.
        // 스위치가 있는데 t초에 스위치를 누르면, t, t+1, t+2초 간 얻는 점수를 2배로 만들 수 있으며
        // 스위치는 t+3초 부터 다시 누를 수 있다.
        // 게임을 진행하는 동안 얻을 수 있는 최대 점수는?
        //
        // DP 문제
        // dp[시간] = 최대 점수로 지정하고 dp를 채워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 게임 진행 시간 n
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 초에 얻는 점수
        int[] scores = new int[n + 1];
        for (int i = 1; i < scores.length; i++)
            scores[i] = Integer.parseInt(st.nextToken());
        
        // 음의 큰 값으로 값 세팅
        // 0초에는 0점
        long[] dp = new long[n + 1];
        Arrays.fill(dp, Long.MIN_VALUE);
        dp[0] = 0;
        // 스위치를 얻는 값을 갖고다니며 계산.
        int sum = (scores[1] + scores[2]) * 2;
        for (int i = 0; i < dp.length - 1; i++) {
            // i+1초에 스위치를 누르지 않는 경우
            // scores[i] 점을 얻는다.
            dp[i + 1] = Math.max(dp[i + 1], dp[i] + scores[i + 1]);

            // i+1초에 스위치를 누르는 경우
            // i+1, i+2, i+3 간의 점수가 2개가 된다.
            // sum에 있는 scores[i]값을 빼주고, scores[i+3] 값을 추가시킨다.
            sum -= scores[i] * 2;
            // 만약 i+3이 값을 벗어나는지 확인.
            // 벗어난다면 2배가 되는 점수는 없지만, 버튼은 누를 수 있음에 주의
            sum += (i + 3 < dp.length ? scores[i + 3] * 2 : 0);
            // 해당 값은 i+1이 아니라, i+3에 기록해야한다.
            // 만약 i+3이 n을 벗어난다면, n에 기록한다.
            int idx = Math.min(i + 3, dp.length - 1);
            dp[idx] = Math.max(dp[idx], dp[i] + sum);
        }
        // 얻을 수 있는 최대 점수 출력
        System.out.println(dp[n]);
    }
}