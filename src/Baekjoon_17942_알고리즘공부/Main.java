/*
 Author : Ruel
 Problem : Baekjoon 17942번 알고리즘 공부
 Problem address : https://www.acmicpc.net/problem/17942
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17942_알고리즘공부;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Problem {
    int idx;
    int difficulty;

    public Problem(int idx, int difficulty) {
        this.idx = idx;
        this.difficulty = difficulty;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 알고리즘과 배고자하는 알고리즘의 개수 m이 주어진다.
        // 각 알고리즘을 배울 때, ki만큼의 공부량이 필요하다.
        // 그런데 어떤 알고리즘끼리는 서로 연관성이 있어
        // a 알고리즘을 공부한다면 b 알고리즘의 공부량이 d 만큼 감소한다.
        // 또한 알고리즘의 공부량은 합산되는 것이 아니라 누적된다.
        // 예를 들어 a, b알고리즘의 요구 공부량이 15, 19라면
        // 두 알고리즘을 공부하는데 필요한 공부량은 19다.
        //
        // 우선순위큐 문제
        // 우선선위큐를 통해 요구 공부량이 낮은 알고리즘을 우선순으로 배우며
        // 연관된 알고리즘들의 요구 공부량을 낮추며 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 알고리즘들, 공부하고자 하는 알고리즘의 수 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 알고리즘을 공부하는 필요한 공부량
        int[] algorithms = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // r개의 연관 관계
        int r = Integer.parseInt(br.readLine());
        List<List<Problem>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());

            connections.get(a).add(new Problem(b, d));
        }

        // 우선순위큐에 처음 상태의 모든 알고리즘들을 담는다.
        PriorityQueue<Problem> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.difficulty));
        for (int i = 0; i < algorithms.length; i++)
            priorityQueue.offer(new Problem(i, algorithms[i]));
        
        // 현재 공부량
        int max = 0;
        // 공부한 알고리즘의 수
        int count = 0;
        // 공부한 알고리즘 체크
        boolean[] learned = new boolean[n];
        // 우선순위큐가 비지 않고, m개의 알고리즘을 아직 공부하지 않았다면
        while (!priorityQueue.isEmpty() && count < m) {
            // 우선순위큐에 담겨있는 가장 요구 공부량이 낮은 알고리즘 문제를 꺼냄
            Problem current = priorityQueue.poll();
            // 이미 공부했다면 건너뛴다.
            if (learned[current.idx])
                continue;

            // 아직 공부하지 않은 경우
            // 개수 증가
            count++;
            // 요구 공부량 갱신 및 체크
            max = Math.max(max, current.difficulty);
            learned[current.idx] = true;

            // 연관된 알고리즘의 공부량을 낮춘다.
            for (Problem p : connections.get(current.idx)) {
                algorithms[p.idx] -= p.difficulty;
                priorityQueue.offer(new Problem(p.idx, algorithms[p.idx]));
            }
        }
        
        // m개의 알고리즘을 공부하는데 필요한 최소 공부량 출력
        System.out.println(max);
    }
}