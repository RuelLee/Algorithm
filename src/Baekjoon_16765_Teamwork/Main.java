/*
 Author : Ruel
 Problem : Baekjoon 16765번 Teamwork
 Problem address : https://www.acmicpc.net/problem/16765
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16765_Teamwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 소가 주어진다.
        // 각 소는 기술 레벨이 주어진다.
        // 연속한 최대 k마리의 소를 한 팀으로 묶을 수 있고, 팀 내의 소들은 서로의 기술을 익혀
        // 가장 높은 기술의 소와 같은 기술을 갖게 된다.
        // 모든 소들의 기술 레벨 합을 최대로 하고자할 때, 그 값은?
        //
        // dp 문제
        // dp[i] = i번째 소까지의 최대 기술 레벨 합으로 정의하고 푼다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 소와 최대 팀원 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 소의 기술 레벨
        int[] cows = new int[n + 1];
        for (int i = 1; i < cows.length; i++)
            cows[i] = Integer.parseInt(br.readLine());

        // dp[i] = i번째 소까지의 최대 기술 레벨 합
        int[] dp = new int[n + 1];
        for (int i = 1; i < dp.length; i++) {
            // i번째 소 혼자서 팀을 이루는 경우.
            dp[i] = Math.max(dp[i], dp[i - 1] + cows[i]);
            
            // i ~ i+j번째 소까지 한 팀을 이루는 경우.
            int max = cows[i];
            for (int j = 1; j < k && i + j < dp.length; j++) {
                max = Math.max(max, cows[i + j]);
                dp[i + j] = Math.max(dp[i + j], dp[i - 1] + max * (j + 1));
            }
        }
        // 답 출력
        System.out.println(dp[dp.length - 1]);
    }
}