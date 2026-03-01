/*
 Author : Ruel
 Problem : Baekjoon 1480번 보석 모으기
 Problem address : https://www.acmicpc.net/problem/1480
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1480_보석모으기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 보석, m개의 가방, 각 가방의 한도 c가 주어진다.
        // n개의 보석들의 무게가 주어진다.
        // 보석들을 가장 많이 가방에 담고자할 때, 그 개수는?
        //
        // DP, 비트마스킹 문제
        // dp[현재 가방][담긴 보석의 bit][현재 무게] = 보석의 개수로 세우고
        // 현재 상태에서 다음 가방으로 넘기는 경우, 다른 보석을 담는 경우들을 고려한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 보석, m개의 가방, 각 가방의 한도 c
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 보석들의 무게
        int[] gems = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            gems[i] = Integer.parseInt(st.nextToken());

        // dp[현재 가방][담긴 보석의 bit][현재 무게] = 보석의 개수
        int[][][] dp = new int[m][1 << n][c + 1];
        int max = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 0});
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            // 현재 상태가 최대 보석의 수인지 값 계산
            max = Math.max(max, dp[cur[0]][cur[1]][cur[2]]);

            // 현재 상태 그대로 다음 가방으로 넘어가는 경우.
            // 무게는 초기화
            if (cur[0] + 1 < m && dp[cur[0] + 1][cur[1]][0] < dp[cur[0]][cur[1]][cur[2]]) {
                dp[cur[0] + 1][cur[1]][0] = dp[cur[0]][cur[1]][cur[2]];
                queue.offer(new int[]{cur[0] + 1, cur[1], 0});
            }

            // 담을 수 있는 다른 보석이 있는지 보는 경우
            for (int i = 0; i < gems.length; i++) {
                // 이미 담았거나, 잔여 무게가 부족한 경우 건너뜀.
                if ((cur[1] & (1 << i)) != 0 || cur[2] + gems[i] > c)
                    continue;

                // 다음 상태 bit
                int nextBit = cur[1] | (1 << i);
                // 무게
                int nextWeight = cur[2] + gems[i];
                // 다음 상태, 무게에서 최대 보석의 수를 갱신한다면
                // 값 갱신 후 큐에 추가
                if (dp[cur[0]][nextBit][nextWeight] < dp[cur[0]][cur[1]][cur[2]] + 1) {
                    dp[cur[0]][nextBit][nextWeight] = dp[cur[0]][cur[1]][cur[2]] + 1;
                    queue.offer(new int[]{cur[0], nextBit, nextWeight});
                }
            }
        }
        // 답 출력
        System.out.println(max);
    }
}