/*
 Author : Ruel
 Problem : Baekjoon 10978번 기숙사 재배정
 Problem address : https://www.acmicpc.net/problem/10978
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10978_기숙사재배정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생이 각각의 기숙사에 배정되어있다.
        // 다음 학기가 되어 다시 기숙사를 배정하려한다.
        // 학생들은 이사하기 싫어, 원래 기숙사를 다시 사용하고 싶어하지만
        // 학생복지팀에서는 어떤 학생에게도 기숙사 재배정을 해주지 않으려고 한다.
        // 그러한 경우의 수는?
        //
        // DP, 비트마스킹, 조합 문제
        // 비트마스킹을 통해 현재 기숙사 배정에 대한 가짓수를 표현하고
        // 순서대로 0번 학생부터 n - 1번 학생까지 기숙사를 배정해나간다.
        // 물론 번호와 동일한 기숙사에 배저하지 않는다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 최대 20명이므로 20개의 기숙사 상태를 표시할 DP 공간
        long[] dp = new long[1 << 20];
        // 초기값
        dp[0] = 1;
        // 큐에 들어있는지 여부
        boolean[] enqueued = new boolean[1 << 20];
        
        Queue<Integer> queue = new LinkedList<>();
        // 아무도 배정되지 않은 상태
        queue.offer(0);
        enqueued[0] = true;
        Queue<Integer> nextQueue = new LinkedList<>();
        // 0번 학생부터 차례대로 배정한다.
        for (int i = 0; i < 20; i++) {
            while (!queue.isEmpty()) {
                // 현재 기숙사 배정 상태
                int current = queue.poll();

                // i번 학생에게 j번 기숙사를 배정하는 것이 가능한지 살펴본다.
                for (int j = 0; j < 20; j++) {
                    // i, j는 서로 다르고
                    // j번 기숙사가 아직 배정되지 않았다면
                    if (i != j && ((1 << j) & current) == 0) {
                        // j번 기숙사에 학생이 배정된 상태
                        int bitmask = (1 << j) | current;
                        // 에 현재 상태의 경우의 수를 더해준다.
                        dp[bitmask] += dp[current];

                        // 만약 큐에 bitmask 상태가 있다면 추가하지 않고
                        // 없다면 추가하고, enqueued 배열에 true 표시
                        // 여기서 nextQueue에 추가함으로써 i번 학생을 다시 배정하는 것이 아닌
                        // i + 1번 학생의 기숙사 배정할 때 사용한다.
                        if (!enqueued[bitmask]) {
                            nextQueue.offer(bitmask);
                            enqueued[bitmask] = true;
                        }
                    }
                }
            }
            // i번 학생 배정이 끝났다.
            // i + 1번 학생의 기숙사 배정을 위해 nextQueue를 queue로 옮겨준다.
            queue = nextQueue;
            nextQueue = new LinkedList<>();
        }
        
        // 테스트케이스 수
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // n명의 학생을 모두 배정한 경우의 수는
            // n개의 비트가 모두 1로 채워진 상태이다.
            // 해당 상태의 값을 기록한다.
            int n = Integer.parseInt(br.readLine());
            sb.append(dp[(1 << n) - 1]).append("\n");
        }
        // 전체 답안 출력.
        System.out.println(sb);
    }
}