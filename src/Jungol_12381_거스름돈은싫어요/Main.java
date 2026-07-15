/*
 Author : Ruel
 Problem : Jungol 12381번 거스름돈은 싫어요
 Problem address : https://jungol.co.kr/problem/12381
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12381_거스름돈은싫어요;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 동전이 주어진다.
        // i번째 동전까지만 사용할 수 있을 때, 만들 수 없는 최소 금액을 n줄에 걸쳐 출력하라
        //
        // 우선순위큐 문제
        // s원까지 모두 만들 수 있는 상태에서, s+1원 이하의 동전이 들어온다면
        // s + s + 1원까지 모든 금액을 만들 수 있다.
        // s + 2원의 동전이 들어온다면 s + 1원은 만들수 없으므로 최소 금액은 s+1원 그대로이다.
        // 따라서 턴마다 새로운 동전을 우선순위큐에 담고
        // 우선순위큐의 최상단 동전이 s + 1원 이하인지 여부를 판단해 커버되는 범위를 확장시켜 나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 동전
        int n = Integer.parseInt(br.readLine());
        // 우선순위큐
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 처음엔 커버되는 범위가 없다.
        long covered = 0;
        for (int i = 0; i < n; i++) {
            // i번째 동전
            priorityQueue.offer(Long.parseLong(st.nextToken()));
            // 우선순위큐에 담긴 동전이 covered + 1 이하인 경우
            // covered의 범위를 확장시킨다.
            while (!priorityQueue.isEmpty() && priorityQueue.peek() <= covered + 1)
                covered += priorityQueue.poll();

            // 만들 수 없는 최소 금액 기록
            sb.append(covered + 1).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}