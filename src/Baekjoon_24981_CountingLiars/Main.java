/*
 Author : Ruel
 Problem : Baekjoon 24981번 Counting Liars
 Problem address : https://www.acmicpc.net/problem/24981
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24981_CountingLiars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static final int MAX = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        // n마리의 소가 주어진다.
        // 각 소는 x축 위에 숨을 수 있는 공간을 다음과 같이 말한다.
        // L p : p보다 같거나 작은 곳에 숨을 수 있다.
        // G p : p보다 크거나 같은 곳에 숨을 수 있다.
        // 몇 마리의 소는 거짓말을 하고 있어, 숨을 수 있는 곳이 없을 수 있다.
        // 거짓말을 하고 있는 최소 수의 소를 구하라.
        //
        // 정렬, 스위핑 문제
        // 각 소들을 범위에 대해 얘기하고 있다.
        // 따라서 범위에 대해 겹치는 교집합의 최대 크기를 구하고
        // n에서 그 값을 뺀 값이 정답

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n 마리의 소
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        
        // 각 소가 말하는 범위 기록
        int[][] areas = new int[n][2];
        for (int i = 0; i < areas.length; i++) {
            st = new StringTokenizer(br.readLine());
            // G p 인 경우
            if (st.nextToken().charAt(0) == 'G') {
                areas[i][0] = Integer.parseInt(st.nextToken());
                areas[i][1] = MAX;
            } else      // L p인 경우
                areas[i][1] = Integer.parseInt(st.nextToken());
        }
        // 정렬
        Arrays.sort(areas, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });

        int max = 0;
        // 우선순위큐로 현재 교집합인 소들의 수를 구함.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < areas.length; i++) {
            // 현재 소의 시작 범위보다 작은 범위를 말해 교집합이 생기지 않는 범위 제거
            while (!priorityQueue.isEmpty() && priorityQueue.peek() < areas[i][0])
                priorityQueue.poll();
            // 현재 소 추가
            priorityQueue.offer(areas[i][1]);
            // 현재 소가 추가된 교집합의 크기의 최댓값 갱신
            max = Math.max(max, priorityQueue.size());
        }
        // max는 교집합에 속한 소가 가장 많은 수.
        // n에서 해당 수를 뺀 값이 답.
        System.out.println(n - max);
    }
}