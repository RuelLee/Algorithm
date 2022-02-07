/*
 Author : Ruel
 Problem : Baekjoon 18870번 좌표 압축
 Problem address : https://www.acmicpc.net/problem/18870
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18870_좌표압축;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 다른 문제를 풀기 위해 풀어본 문제
        // 좌표들의 순서가 중요하고, 그 크기 자체가 중요하진 않을 때의 압축법
        // 정렬해서 순서만 기록.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] nums = new int[n];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> nums[o]));
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
            priorityQueue.offer(i);
        }

        int preValue = nums[priorityQueue.peek()];      // 이전값 기록
        nums[priorityQueue.poll()] = 0;     // 첫번째는 0부터 시작.
        int count = 1;      // 현재 순서 기록
        while (!priorityQueue.isEmpty()) {
            if (preValue == nums[priorityQueue.peek()])     // 이전 값과 같다면
                nums[priorityQueue.poll()] = count - 1;     // 같은 순위 기록
            else {      // 다르다면
                preValue = nums[priorityQueue.peek()];      // 현재값 기록
                nums[priorityQueue.poll()] = count++;       // 순위 기록하고 count 증가
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i : nums)
            sb.append(i).append(" ");
        System.out.println(sb);
    }
}