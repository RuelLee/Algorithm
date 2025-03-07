/*
 Author : Ruel
 Problem : Baekjoon 1176번 섞기
 Problem address : https://www.acmicpc.net/problem/1176
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1176_섞기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명 학생들의 키가 주어진다.
        // 인접한 학생들의 키를 k 초과로 배치하고자 할 때, 가능한 경우의 수는?
        //
        // dp, bitmask 문제
        // n이 최대 16으로 주어지길래, 비스마스킹으로 표현할 수 있다.
        // 따라서 dp[비트마스크][마지막학생] = 경우의 수
        // 로 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, 인접한 학생들의 키 차이가 k 초과
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        // 학생들의 키
        int[] heights = new int[n];
        for (int i = 0; i < heights.length; i++)
            heights[i] = Integer.parseInt(br.readLine());
        
        // dp[비트마스크][마지막학생] = 경우의 수
        long[][] dp = new long[1 << n][n];
        Queue<Integer> queue = new LinkedList<>();
        // 큐에 해당 경우의 담겨있는지 체크
        boolean[][] enqueued = new boolean[dp.length][n];
        
        // 첫 학생을 세우는 경우
        for (int i = 0; i < heights.length; i++) {
            dp[1 << i][i] = 1;
            queue.offer((1 << i) * n + i);
            enqueued[1 << i][i] = true;
        }
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 현재 비트마스크
            int bitmask = current / n;
            // 마지막 학생 번호
            int lastIdx = current % n;

            // 다음에 세울 학생을 찾는다.
            for (int i = 0; i < heights.length; i++) {
                // i번째 학생이 아직 줄을 서지 않았고
                // 마지막 학생과의 키 차이가 k 초과인 경우
                if ((bitmask & (1 << i)) == 0 && Math.abs(heights[lastIdx] - heights[i]) > k) {
                    int nextBits = bitmask | (1 << i);
                    // dp에 경우의 수 추가
                    dp[nextBits][i] += dp[bitmask][lastIdx];
                    // 큐에 안 담겨있다면
                    if (!enqueued[nextBits][i]) {
                        // 해당 경우를 큐에 담고, 큐에 담았다고 체크
                        queue.offer(nextBits * n + i);
                        enqueued[nextBits][i] = true;
                    }
                }
            }
        }
        
        // 마지막 학생과 상관없이 
        // 모든 학생을 줄 세우는 경우의 수를 모두 더함
        long sum = 0;
        for (int i = 0; i < n; i++)
            sum += dp[dp.length - 1][i];
        // 답 출력
        System.out.println(sum);
    }
}