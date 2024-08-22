/*
 Author : Ruel
 Problem : Baekjoon 30014번 준영이의 사랑
 Problem address : https://www.acmicpc.net/problem/30014
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30014_준영이의사랑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 진주로 이루어진 진주 목걸이를 만들고자 한다.
        // i번째 진주의 가치를 Ai라 할 때, 목걸이의 가치는
        // A1 * A2 + ... + Ai * Ai+1 + An-1 * An 이라고 한다.
        // 최대 가치의 목걸이를 만들고자할 떄 그 가치가와
        // 진주의 배치를 출력하라
        //
        // 그리디, 데크 문제
        // 가장 큰 가치를 갖는 진주 주변에 우선적으로 다음 가치를 갖는 진주를 배치해야한다.
        // 목걸이는 끝과 끝이 서로 맞닿아있으므로,
        // 진주를 배치할 때, 앞이나 뒤 양쪽 모두 배치할 수 있으므로 데크를 사용한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 우선순위큐를 통해 n개의 진주를 가치 내림순으로 살펴본다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            priorityQueue.offer(Integer.parseInt(st.nextToken()));

        // 데크로 목걸이 모양대로 배치
        Deque<Integer> deque = new LinkedList<>();
        // 첫 진주
        deque.offerLast(priorityQueue.poll());
        long answer = 0;
        while (!priorityQueue.isEmpty()) {
            // 데크의 앞과 뒤를 살펴보고 더 큰 쪽에
            // 다음 가치를 갖는 진주를 배치한다.
            // 앞쪽에 배치
            if (deque.peekFirst() >= deque.peekLast()) {
                // 해당 진주를 배치할 때 추가되는 가치 계산
                answer += deque.peekFirst() * priorityQueue.peek();
                deque.offerFirst(priorityQueue.poll());
            } else {        // 뒤쪽에 배치
                answer += deque.peekLast() * priorityQueue.peek();
                deque.offerLast(priorityQueue.poll());
            }
        }
        // 마지막 진주와 첫번째 진주가 갖는 가치 계산.
        answer += deque.peekLast() * deque.peekFirst();

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 전체 가치
        sb.append(answer).append("\n");
        // 진주 배열 기록
        while (!deque.isEmpty())
            sb.append(deque.pollFirst()).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력
        System.out.println(sb);
    }
}