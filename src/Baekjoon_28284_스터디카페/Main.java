/*
 Author : Ruel
 Problem : Baekjoon 28284번 스터디 카페
 Problem address : https://www.acmicpc.net/problem/28284
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28284_스터디카페;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 좌석을 가진 스터디 카페가 있다.
        // 해당 좌석의 이용 요금은 좌석마다 다르며, 각각의 요금이 주어진다.
        // m개의 이용 내역이 주어진다.
        // 어떤 이용자가 Si일부터 Ei일 까지 스터디 카페를 이용했다는 내용이다.
        // 좌석은 날마다 바뀔 수 있다.
        // 이용 기록을 통해
        // 얻을 수 있는 최소 수익과 최대 수익을 구하라
        //
        // 누적합, 스위핑 문제
        // 누적합을 통해 같은 날, i명이 스터디 카페를 이용했을 때, 얻을 수 있는 일일 최소, 최대 수익을 누적합으로 구해둔다.
        // 그리고 이용내역을 살펴나가며, 전체 기간의 최소, 최대 수익을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 좌석, m개의 이용 내역
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 요금
        int[] costs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 오름차순 정렬
        Arrays.sort(costs);
        // 일일 이용객에 따른 얻을 수 있는 최소, 최대 수익
        long[] minSums = new long[n + 1];
        long[] maxSums = new long[n + 1];
        for (int i = 0; i < n; i++) {
            minSums[i + 1] = minSums[i] + costs[i];
            maxSums[i + 1] = maxSums[i] + costs[n - 1 - i];
        }

        // m개의 기록
        int[][] records = new int[m][];
        for (int i = 0; i < records.length; i++)
            records[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 시작일을 기준으로 오름차순 정렬
        Arrays.sort(records, Comparator.comparingInt(o -> o[0]));
        
        // 정산이 끝난 날짜
        int calcedDay = 0;
        // 최소 수익과 최대 수익
        long minSum = 0;
        long maxSum = 0;
        // 우선순위큐를 통해 이용객의 수 관리
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // 이용 내역을 차례대로 살펴본다.
        for (int[] r : records) {
            // 현재 이용 내역의 시작일보다 종료일이 빠른 이용객들에 대해서는 정산을 한다.
            while (!priorityQueue.isEmpty() && priorityQueue.peek() < r[0]) {
                // 우선순위큐의 최상단에 있는 이용객의 종료일까지는
                // 우선순위큐의 크기만큼의 이용객들이 이용한다.
                // 해당 이용객의 수와 일자를 이용하여 최소, 최대 수익 계산
                minSum += minSums[priorityQueue.size()] * (priorityQueue.peek() - calcedDay);
                maxSum += maxSums[priorityQueue.size()] * (priorityQueue.peek() - calcedDay);
                calcedDay = priorityQueue.poll();

                // 종료일이 같은 이용객들은 그냥 꺼낸다.
                while (!priorityQueue.isEmpty() && priorityQueue.peek() == calcedDay)
                    priorityQueue.poll();
            }

            // 현재 이용 내역의 시작일 이전에 대해서는 정산을 실시한다.
            minSum += minSums[priorityQueue.size()] * (r[0] - 1 - calcedDay);
            maxSum += maxSums[priorityQueue.size()] * (r[0] - 1 - calcedDay);
            calcedDay = r[0] - 1;
            // 현재 이용내역을 우선순위큐에 추가.
            priorityQueue.offer(r[1]);
        }
        // 마지막으로 우선순위큐에 남은 이용객들을
        // 종료일이 이른 순서대로 정산을 시작한다.
        while (!priorityQueue.isEmpty()) {
            // 현재 우선순위큐 최상단에 있는 이용객의 종료일까지
            // 우선순위큐의 크기만큼의 이용객들이 사용한다.
            minSum += minSums[priorityQueue.size()] * (priorityQueue.peek() - calcedDay);
            maxSum += maxSums[priorityQueue.size()] * (priorityQueue.peek() - calcedDay);
            calcedDay = priorityQueue.poll();
        }
        // 기간 동안 얻을 수 있는 최소, 최대 이익 출력
        System.out.println(minSum + " " + maxSum);
    }
}