/*
 Author : Ruel
 Problem : Jungol 2666번 배낭채우기3
 Problem address : https://jungol.co.kr/problem/2666
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2666_배낭채우기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 보석과 k개의 배낭이 주어진다.
        // 보석은 무게와 가치가 주어진다.
        // 각 배낭에는 하나의 보석만 담을 수 있고, 담을 수 있는 최대 무게 Wt가 주어진다.
        // 배낭들에 담을 수 있는 모든 보석의 가치 합은?
        //
        // 정렬, 우선순위큐 문제
        // 보석과 가방을 무게에 따라 오름차순 정렬한다.
        // 그런 후에 가방을 순서대로 살펴보며, 보석 가치에 따른 최대 힙 우선순위큐에 현재 가능한 무게까지만 담아나간다.
        // 그러며, 가방에 담을 수 있는 최대 가치의 보석을 담아간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 보석, k개의 가방
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 보석
        int[][] gems = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            gems[i][0] = Integer.parseInt(st.nextToken());
            gems[i][1] = Integer.parseInt(st.nextToken());
        }
        // 무게에 따라 오름차순 정렬
        Arrays.sort(gems, Comparator.comparingInt(o -> o[0]));

        // 가방
        int[] bags = new int[k];
        for (int i = 0; i < k; i++)
            bags[i] = Integer.parseInt(br.readLine());
        // 담을 수 있는 무게에 따라 오름차순 정렬
        Arrays.sort(bags);

        // 보석 가치 합
        long sum = 0;
        // 현재 담을 수 있는 보석의 가치를 내림차순으로 정렬되는 우선순위큐에 담는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        // 현재 살펴봐야하는 보석의 위치
        int idx = 0;
        // 가방을 차례대로 살펴본다.
        for (int i = 0; i < bags.length; i++) {
            // 현재 가방에 담을 수 있는 무게까지 우선순위큐에 담는다.
            while (idx < gems.length && gems[idx][0] <= bags[i])
                priorityQueue.offer(gems[idx++][1]);

            // 우선순위큐가 비어있지 않은 경우, 해당 보석을 가방에 담는다.
            if (!priorityQueue.isEmpty())
                sum += priorityQueue.poll();
        }
        // 답 출력
        System.out.println(sum);
    }
}