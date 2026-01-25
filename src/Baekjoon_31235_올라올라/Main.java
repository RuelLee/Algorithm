/*
 Author : Ruel
 Problem : Baekjoon 31235번 올라올라
 Problem address : https://www.acmicpc.net/problem/31235
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31235_올라올라;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 수열 a가 주어진다.
        // 이를 통해 새로운 수열 b를 만드는데
        // bi = max(ai , ... , ai+ k -1)로 각 원소를 정의한다.
        // 이 때 b의 수열이 감소하지 않도록 하고자한다.
        // 예를 들어 a가 = {3, 1, 4, 2, 5}인데
        // k = 1인 경우, {3, 1, 4, 2, 5}가 되어 감소하는 구간이 생기고
        // k = 2인 경우 {3, 4, 4, 5}가 되어 감소하는 구간이 생기지 않는다.
        // 조건에 만족하는 가장 작은 k를 구하라
        //
        // 그리디 문제
        // 첫 원소부터 k 범위 내에 가장 큰 원소를 찾으며, 이전 원소의 최댓값보다 같거나 큰지 살펴본다.
        // 만족하지 않는다면, k의 값을 더 큰 값을 찾을 때까지 지속적으로 늘린다.
        // 그러다 결국 발견하지 못한다면, 현재 i가 b 수열에 포함되지 않도록
        // k의 값을 n - 1 - i + 2로 늘려버리고 반복문을 종료한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 수열 a
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = Integer.parseInt(st.nextToken());

        // 이전 최대 원소의 값
        int preMax = arr[0];
        // 길이
        int length = 1;
        // 범위 내의 최댓값을 우선순위큐로 관리
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(((o1, o2) -> Integer.compare(arr[o2], arr[o1])));
        priorityQueue.offer(0);
        for (int i = 1; i + length - 1 < n; i++) {
            // 값 추가
            if (i + length - 1 < n)
                priorityQueue.offer(i + length - 1);
            // i보다 작은 idx의 값이 우선순위큐의 최상단에 있따면 제거
            while (!priorityQueue.isEmpty() && priorityQueue.peek() < i)
                priorityQueue.poll();
            // 만약 이전 최대 원소 값보다 우선순위큐의 최상단 값이 더 작다면 k의 범위를 늘린다.
            while (arr[priorityQueue.peek()] < preMax && i + length < n)
                priorityQueue.offer(i + ++length - 1);

            // 만약 끝까지 늘렸음에도 만족하지 않는다면
            // 현재 i가 수열 b에 포함되지 않도록 k의 값을 늘리고 반복문을 종료한다.
            if (arr[priorityQueue.peek()] < preMax) {
                length = n - 1 - i + 2;
                break;
            }

            // 만족하는 k를 찾았다면 이전 원소의 최대값을 갱신한다.
            preMax = arr[priorityQueue.peek()];
        }
        // 답 출력
        System.out.println(length);
    }
}