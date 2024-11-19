/*
 Author : Ruel
 Problem : Baekjoon 7868번 해밍 수열
 Problem address : https://www.acmicpc.net/problem/7868
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7868_해밍수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 세 소수 p1, p2, p3를 이용하여, 해밍 수열 H(p1, p2, p3)를 정의할 수 있다.
        // 예를 들어, H(2, 3, 5) = 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, 16, 18, 20, 24, 25, 27, ... 이고, 5번째 수는 6이다.
        // 첫째줄에 p1, p2, p3, i가 주어질 때
        // H(p1, p2, p3)의 i번째 수를 출력하라
        //
        // 브루트 포스 문제
        // 문제에서 네 수와 출력하는 수가 10^18보다 작다는 조건이 주어진다.
        // 10^18은 2^60보다 약간 작은 수로
        // 세 소수의 승들의 합이 60을 넘을 수는 없다.
        // 상당히 여유있게, 각각의 승을 60까지로 보더라도, i는 최대 60^3 약 21만으로
        // 직접 계산하기에 무리가 없는 수준이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 세 소수 p
        int[] p = new int[3];
        for (int i = 0; i < p.length; i++)
            p[i] = Integer.parseInt(st.nextToken());
        // 순서 i
        int i = Integer.parseInt(st.nextToken());

        // 우선순위큐를 통해, 작은 수부터 탐색한다.
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        // 0번째 수는 1
        priorityQueue.offer(1L);
        HashSet<Long> hashSet = new HashSet<>();
        hashSet.add(1L);
        int count = 0;
        // i번 반복하여, i번째 수가 우선순위큐의 최상단에 오게끔한다.
        while (count < i) {
            long current = priorityQueue.poll();

            // current에 세 소수를 곱해 새로운 수를 만든다.
            for (int k : p) {
                long next = current * k;

                // 만약 오버플로우가 발생하거나, 이전에 추가한 적이 있는 수라면 건너뛴다.
                if (next / k != current ||
                        hashSet.contains(next))
                    continue;

                // 해쉬셋과 우선순위큐에 추가
                hashSet.add(next);
                priorityQueue.offer(next);
            }
            // count 증가
            count++;
        }
        // 답 출력
        System.out.println(priorityQueue.peek());
    }
}