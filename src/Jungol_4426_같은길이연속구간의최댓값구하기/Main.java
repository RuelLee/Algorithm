/*
 Author : Ruel
 Problem : Jungol 4426번 같은 길이 연속구간의 최댓값 구하기
 Problem address : https://jungol.co.kr/problem/4426
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4426_같은길이연속구간의최댓값구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 수열이 주어진다.
        // 길이 w의 모든 연속 구간에 대해 최댓값을 출력하라
        //
        // 슬라이딩 윈도우, 우선순위큐 문제
        // 우선순위큐에 순서대로 범위 내 수들의 idx를 담으며,
        // 최댓값의 범위에 벗어난 수가 나온다면 제거하길 반복한다.
        // 그러면서 그대로 범위를 밀어나가며 최댓값을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길의 n의 수열과 범위 w
        int n = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        // 수열
        int[] array = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());

        // 범위 내 최댓값을 우선순위큐로 구한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(array[o2], array[o1]));
        for (int i = 0; i < w - 1; i++)
            priorityQueue.offer(i);

        StringBuilder sb = new StringBuilder();
        for (int i = w - 1; i < n; i++) {
            // 현재 주소 추가
            priorityQueue.offer(i);
            // 범위를 벗어난 수가 최댓값이라면 제거
            while (i - priorityQueue.peek() >= w)
                priorityQueue.poll();

            // 답 기록
            sb.append(array[priorityQueue.peek()]).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}