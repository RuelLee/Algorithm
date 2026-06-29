/*
 Author : Ruel
 Problem : Jungol 1099번 연대표
 Problem address : https://jungol.co.kr/problem/1099
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1099_연대표;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 사건들의 시작과 종료의 시간대가 주어진다.
        // 동시에 이루어진 사건들을 하나의 행에 열을 추가하여 나열한다고 할 때
        // 최대 필요한 열의 수는?
        //
        // 정렬, 스위핑, 우선순위큐 문제
        // 동시간에 얼마나 많은 사건이 동시에 일어났는지를 체크하면 된다.
        // 시작 시간대로 사건들을 정렬하여 우선순위큐에 종료 시간대로 정렬하여 담는다.
        // 사건들을 살펴보며, 현재 사건보다 이른 시간에 종료된 사건들은 우선순위큐에서 제거해나가며 담고
        // 탐색을 하는 동안 우선순위큐의 최대 크기를 구하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 사건들
        int[][] cases = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < cases.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cases[i].length; j++)
                cases[i][j] = StringToInt(st.nextToken());
        }
        // 시작 시간대로 정렬
        Arrays.sort(cases, Comparator.comparingInt(o -> o[0]));

        // 종료 시간대에 대해 오름차순 우선순위큐
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> cases[o][1]));
        int max = 0;
        for (int i = 0; i < cases.length; i++) {
            // 우선순위큐에서 i번째 사건보다 이른 시간에 끝난 사건들을 모두 제거
            while (!priorityQueue.isEmpty() && cases[priorityQueue.peek()][1] <= cases[i][0])
                priorityQueue.poll();
            // i번을 담고
            priorityQueue.offer(i);
            // 우선순위큐의 최대 크기 계산
            max = Math.max(max, priorityQueue.size());
        }
        // 답 출력
        System.out.println(max);
    }

    // String 형태로 주어지지만 그대로 수의 형태로 받아도
    // 시간대의 대소 크기 관계가 변하지 않는다.
    static int StringToInt(String str) {
        String[] strings = str.split("\\.");
        return Integer.parseInt(strings[0]) * 10000 +
                Integer.parseInt(strings[1]) * 100 +
                Integer.parseInt(strings[2]);
    }
}