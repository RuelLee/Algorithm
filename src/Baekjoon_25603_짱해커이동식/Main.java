/*
 Author : Ruel
 Problem : Baekjoon 25603번 짱해커 이동식
 Problem address : https://www.acmicpc.net/problem/25603
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25603_짱해커이동식;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 의뢰들의 비용이 주어진다.
        // 임의의 연속한 k개의 의뢰 중 최소 한가지는 무조건 받아야한다.
        // 의뢰를 수행할 때, 가장 비싼 의뢰의 최소값을 구하라
        //
        // 슬라이딩 윈도우, 우선순위큐 문제
        // 임의의 연속한 k개의 의뢰 중 하나를 무조건 수행해야하므로
        // 모든 구간에 대해 연속한 k개의 의뢰중 하나를 무조건 수행해야한다.
        // 따라서 슬라이딩 윈도우를 통해 모든 구간을 살펴보며
        // 해당 구간에가 가장 가격이 싼 의뢰를 계산하며
        // 그 값이 가장 비싼 경우를 찾으면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 의뢰, 연속한 k개의 의뢰들 중 하나를 반드시 수행해야한다.
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 가격들
        int[] costs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 구간 내에서 가장 싼 의뢰를 찾아줄 우선순위큐
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> costs[o]));
        // 0 ~ k-2까지의 의뢰를 담아두고
        for (int i = 0; i < k - 1; i++)
            priorityQueue.offer(i);

        int min = 1;
        // i - k + 1 ~ i 구간을 살펴본다.
        for (int i = k - 1; i < costs.length; i++) {
            // i값 추가.
            priorityQueue.offer(i);
            // 우선순위큐의 최소값이 범위 내에 포함되지 않을 경우 제외한다.
            while (!priorityQueue.isEmpty() && priorityQueue.peek() <= i - k)
                priorityQueue.poll();

            // 해당 구간에서의 최소값이 우선순위큐 최상단에 있다.
            // 해당 값과 여태 지나온 최소 의뢰비를 비교하여 더 큰 값을 남겨둔다.
            min = Math.max(min, costs[priorityQueue.peek()]);
        }
        // 답 출력
        System.out.println(min);
    }
}