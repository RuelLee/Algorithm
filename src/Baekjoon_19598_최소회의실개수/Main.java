/*
 Author : Ruel
 Problem : Baekjoon 19598번 최소 회의실 개수
 Problem address : https://www.acmicpc.net/problem/19598
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19598_최소회의실개수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 회의에 대해 시작, 종료 시간이 주어진다.
        // 모든 미팅을 진행하는데 있어 필요한 회의실 개수는?
        //
        // 그리디 문제
        // 회의 시작 시간 순으로 정렬하여 하나씩 탐색해나간다.
        // 진행중인 회의를 종료 시간 순으로 우선순위큐에 담는다.
        // 하나씩 회의를 살펴보며, 해당 회의가 시작되기 전에 종료된 회의들은 빼가며
        // 동시에 진행되는 회의의 최대 개수를 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 회의 정보.
        int[][] meetings = new int[n][2];
        for (int i = 0; i < meetings.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            meetings[i][0] = Integer.parseInt(st.nextToken());
            meetings[i][1] = Integer.parseInt(st.nextToken());
        }
        // 회의 시작 시간 순으로 정렬.
        Arrays.sort(meetings, Comparator.comparing(m -> m[0]));

        // 필요한 최소 회의실의 개수.
        int minRooms = 0;
        // 최소힙 우선순위큐로 종료 시간 순으로 살필 큐.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(m -> meetings[m][1]));
        for (int i = 0; i < meetings.length; i++) {
            // i번째 회의가 시작하기 전에 끝난 회의들을 우선순위큐에서 빼준다.
            while (!priorityQueue.isEmpty() && meetings[i][0] >= meetings[priorityQueue.peek()][1])
                priorityQueue.poll();
            // 이번 회의를 큐에 삽입하고
            priorityQueue.offer(i);
            // 우선순위큐의 크기(= 진행중인 회의)의 최대값이 갱신되었는지 확인한다.
            minRooms = Math.max(minRooms, priorityQueue.size());
        }
        // 최종적으로 가장 컸던 우선순위큐의 크기(= 동시에 진행된 최대 회의의 개수)를 출력한다.
        System.out.println(minRooms);
    }
}