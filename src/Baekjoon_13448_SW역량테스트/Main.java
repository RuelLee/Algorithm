/*
 Author : Ruel
 Problem : Baekjoon 13448번 SW 역량 테스트
 Problem address : https://www.acmicpc.net/problem/13448
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13448_SW역량테스트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 문제가 주어진다.
        // 각 문제를 풀었을 때, 푸는 시각에 따라 다른 점수를 받게된다.
        // 시각 t인 시점에 푼 경우, Mi - t * Pi 점을 받는다.
        // 총 T분 동안 진행될 때, 얻을 수 있는 최대 점수는?
        //
        // 정렬, 배낭 문제
        // 배낭 문제로 풀되, 푸는 시각에 따라 문제에서 얻을 수 있는 점수가 달라지기에
        // 푸는 문제를 어떤 순서로 살펴볼 지가 중요한 문제
        // i번 문제를 풀고 j번 문제를 푸는 경우
        // Mi - (Ri) * Pi + Mj - (Ri + Rj) * Pj
        // j번 문제를 풀고, i번 문제를 푸는 경우
        // Mj - (Rj)* Pj + Mi - (Ri + Rj) * Pi
        // 양 변에 Mi + Mj - Rj * Pj - Ri * Pi를 빼주면
        // -Ri * Pj
        // -Rj * Pi
        // 위의 기준으로 정렬하여, 배낭 문제로 풀면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 문제, 총 시간 T
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 문제들
        long[][] problems = new long[n][3];
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                problems[j][i] = Integer.parseInt(st.nextToken());
        }
        // 기준에 따라 정렬
        Arrays.sort(problems, (o1, o2) -> Long.compare(o1[2] * o2[1], o2[2] * o1[1]));

        // 배낭 문제
        long[] dp = new long[t + 1];
        long max = 0;
        for (long[] p : problems) {
            for (int i = t - (int) p[2]; i >= 0; i--) {
                // 문제를 푸는 시각과 점수
                int solveTime = (int) (i + p[2]);
                long score = p[0] - solveTime * p[1];
                max = Math.max(max, dp[solveTime] = Math.max(dp[solveTime], dp[i] + score));
            }
        }
        // 얻은 최대 점수 출력
        System.out.println(max);
    }
}