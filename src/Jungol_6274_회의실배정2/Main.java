/*
 Author : Ruel
 Problem : Jungol 6274번 회의실 배정 2
 Problem address : https://jungol.co.kr/problem/6274
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_6274_회의실배정2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 미팅의 시작 시간과 종료 시간이 주어진다.
        // 모든 미팅을 원활하게 진행하기 위해서 최소 몇 개의 회의실이 필요한가?
        //
        // 정렬, 우선순위큐 문제
        // 시작 시간 순으로 미팅을 정렬한다. 그리고 종료 시간에 따라 오름차순으로 정렬되는 우선순위큐를 준비한다.
        // 미팅을 순서대로 살펴보며, 해당 미팅보다 일찍 끝나는 미팅을 우선순위큐에서 제거한다.
        // 그리고 해당 미팅을 우선순위큐에 담으면, 현재 미팅의 시작 시간에 동시에 진행되는 미팅의 수가 된다.
        // 동시에 진행되는 미팅의 최대 개수를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 미팅
        int n = Integer.parseInt(br.readLine());
        int[][] meetings = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            meetings[i][0] = Integer.parseInt(st.nextToken());
            meetings[i][1] = Integer.parseInt(st.nextToken());
        }
        // 시작 시간 오름차순으로 정렬
        Arrays.sort(meetings, Comparator.comparingInt(o -> o[0]));

        // 동시에 진행되는 미팅의 최대 수
        int max = 0;
        // 종료 시간에 따라 오름차순 정렬되는 우선순위큐
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> meetings[o][1]));
        for (int i = 0; i < meetings.length; i++) {
            // i번째 미팅보다 일찍 끝나는 미팅들을 제거
            while (!priorityQueue.isEmpty() && meetings[priorityQueue.peek()][1] <= meetings[i][0])
                priorityQueue.poll();

            // i번째 미팅 추가
            priorityQueue.offer(i);
            // 현재 진행되는 미팅의 수가 최대 개수인지 체크
            max = Math.max(max, priorityQueue.size());
        }
        // 답 출력
        System.out.println(max);
    }
}