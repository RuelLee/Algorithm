/*
 Author : Ruel
 Problem : Baekjoon 14226번 이모티콘
 Problem address : https://www.acmicpc.net/problem/14226
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14226_이모티콘;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 화면에 1개의 이모티콘이 입력되어있다.
        // 1. 화면에 있는 이모티콘을 모두 복사해서 클립보드에 저장한다.
        // 2. 클립보드에 있는 모든 이모티콘을 화면에 붙여넣기 한다.
        // 3. 화면에 있는 이모티콘 중 하나를 삭제한다.
        // 세 개의 행동을 통해 s개의 이모티콘으로 만드려고 한다.
        // 각 행동에는 1초가 소요된다고 할 때, 최소 소요 시간은?
        //
        // BFS와 DP를 활용한 문제.
        // 이모티콘이 입력되어있을 때, 해당 이모티콘을 하나 지우는 경우
        // 복사해서 (1번, 2번, ...) 붙여넣기하는 경우.
        // 모두 계산하며 n개의 이모티콘을 입력하는데 드는 최소시간을 구해나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int s = Integer.parseInt(br.readLine());

        // s보다 큰 값에서 3번 명령을 통해 줄여오는 경우가 있을 수 있다.
        // s보다 큰 값으로 DP의 크기를 설정해주자.
        int[] dp = new int[(s + 1) * 2];
        // 큰 값으로 초기화
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 1개는 기본으로 입력되어있다.
        dp[1] = 0;
        // 우선순위큐를 통해 소요 시간이 적은 것들부터 우선적으로 방문하자.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparing(v -> dp[v]));
        priorityQueue.offer(1);
        while (!priorityQueue.isEmpty()) {
            // 현재 화면에 입력된 이모티콘의 개수
            int current = priorityQueue.poll();
            // s개라면 목표 도달 종료.
            if (current == s)
                break;

            // current에서 한 개를 지우는 경우.
            if (current > 1 && dp[current - 1] > dp[current] + 1) {
                dp[current - 1] = dp[current] + 1;
                priorityQueue.remove(current - 1);
                priorityQueue.offer(current - 1);
            }

            // current에서 복사를 한 뒤, 여러 차례 붙여넣기 하는 경우.
            for (int i = 2; i * current < dp.length; i++) {
                // i * current개의 이모티콘이 화면에 있으려면,
                // current 개수만큼 복사한 뒤, i - 1번 만큼 붙여넣기를 해야한다.
                // 추가 소요 시간 1 + (i - 1) = i
                if (dp[i * current] > dp[current] + i) {
                    dp[i * current] = dp[current] + i;
                    priorityQueue.remove(i * current);
                    priorityQueue.offer(i * current);
                }
            }
        }
        System.out.println(dp[s]);
    }
}