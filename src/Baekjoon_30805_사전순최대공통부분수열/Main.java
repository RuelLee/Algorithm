/*
 Author : Ruel
 Problem : Baekjoon 30805번 사전 순 최대 공통 부분 수열
 Problem address : https://www.acmicpc.net/problem/30805
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30805_사전순최대공통부분수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 부분 수열이라함은, 수열의 순서는 유지한 채, 일부의 수로 다시 만든 수열을 의미한다.
        // 두 수열 A, B가 주어질 때,
        // 두 수열의 공통된 부분 수열 중 가장 사전 순으로 나중인 것을 구하라
        //
        // 그리디 문제
        // 두 수열을 첫 수부터 살펴보며, 공통된 가장 큰 수를 찾아나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 수열 A
        int n = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] a = new int[n];
        // 우선순위큐를 통해, 가장 큰 수의 idx를 우선적으로 찾는다.
        // 그러한 수가 여러개라면 idx가 이른 순으로 찾는다.
        PriorityQueue<Integer> aPriorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (a[o1] == a[o2])
                return Integer.compare(o1, o2);
            return Integer.compare(a[o2], a[o1]);
        });
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(st.nextToken());
            aPriorityQueue.offer(i);
        }

        // 수열 B
        int m = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] b = new int[m];
        PriorityQueue<Integer> bPriorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (b[o1] == b[o2])
                return Integer.compare(o1, o2);
            return Integer.compare(b[o2], b[o1]);
        });
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.parseInt(st.nextToken());
            bPriorityQueue.offer(i);
        }

        // 공통된 부분 수열의 수들을 담아둘 큐
        Queue<Integer> queue = new LinkedList<>();
        // 해당 idx 이상의 idx에서 수를 찾는다.
        int[] idxes = new int[2];
        // idx가 아직 수열의 범위를 넘어서지 않았고, 우선순위큐에 수들이 담겨있다면
        while (idxes[0] < a.length && idxes[1] < b.length &&
                !aPriorityQueue.isEmpty() && !bPriorityQueue.isEmpty()) {
            // 우선순위큐에서 범위를 벗어난 idx들 제거
            while (!aPriorityQueue.isEmpty() && aPriorityQueue.peek() < idxes[0])
                aPriorityQueue.poll();
            while (!bPriorityQueue.isEmpty() && bPriorityQueue.peek() < idxes[1])
                bPriorityQueue.poll();

            // 두 우선순위큐가 비어있지 않고
            if (!aPriorityQueue.isEmpty() && !bPriorityQueue.isEmpty()) {
                // a우선순위큐가 가르키는 값이 b우선순위큐보다 더 크다면
                // a우선순위큐의 값 제거
                if (a[aPriorityQueue.peek()] > b[bPriorityQueue.peek()])
                    aPriorityQueue.poll();
                    // 반대일 경우 b우선순위큐의 값 제거
                else if (a[aPriorityQueue.peek()] < b[bPriorityQueue.peek()])
                    bPriorityQueue.poll();
                else {      // 값이 서로 일치하는 경우
                    // queue에 해당 값 기록
                    queue.offer(a[aPriorityQueue.peek()]);
                    // 후, 각각의 우선순위큐에서 값 제거하며
                    // idx에 표시
                    idxes[0] = aPriorityQueue.poll();
                    idxes[1] = bPriorityQueue.poll();
                }
            }
        }

        // 구한 queue를 바탕으로 답안 작성
        StringBuilder sb = new StringBuilder();
        sb.append(queue.size()).append("\n");
        if (!queue.isEmpty()) {
            sb.append(queue.poll());
            while (!queue.isEmpty())
                sb.append(" ").append(queue.poll());
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}