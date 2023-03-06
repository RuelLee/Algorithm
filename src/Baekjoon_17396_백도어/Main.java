/*
 Author : Ruel
 Problem : Baekjoon 17396번 백도어
 Problem address : https://www.acmicpc.net/problem/17396
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17396_백도어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    long distance;

    public Road(int end, long distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 지점과 m개의 도로가 주어진다.
        // 그리고 각 지점의 상황이 0 또는 1로 주어진다.
        // 0번 지점부터 n-1 지점까지 이동하려하고, 각 지점에 와드, 미니언, 포탑이 있을 경우 1로 값이 주어지며 지나칠 수 없다.
        // n-1 지점에 도달하는데 드는 최소 시간은?
        //
        // dijkstra 문제
        // 각 지점에 상황에 대한 정보가 추가되었지만 크게 영향을 끼칠 정도는 아니다.
        // 각 지점에 도달하는 것은 계산한되, 지점의 상황이 1일 경우, 그 지점에서 더 이상 진행하진 않고 멈춘다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 처리
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 지점의 상황
        int[] visible = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 주어지는 도로들
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, t));
            roads.get(b).add(new Road(a, t));
        }
        
        // 각 지점에 이르는 최소 거리
        long[] minDistances = new long[n];
        // 초기값 세팅
        Arrays.fill(minDistances, Long.MAX_VALUE);
        minDistances[0] = 0;
        // 방문 체크
        boolean[] visited = new boolean[n];
        // 우선순위큐를 사용하여 각 지점에 이르는 거리가 짧은 순으로 살펴본다.
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(value -> value.distance));
        // 0 지점에 출발
        priorityQueue.offer(new Road(0, 0));
        while (!priorityQueue.isEmpty()) {
            // 현재 위치를 꺼내
            Road current = priorityQueue.poll();
            // 이미 방문했거나 지점 상황이 1이라면 건너뛴다.
            if (visited[current.end] || visible[current.end] == 1)
                continue;

            // 그렇지 않다면 갈 수 있는 다음 지점들을 살펴보며
            // 최소 거리가 갱신된다면 값을 갱신하고, 우선순위큐에 다음 지점을 추가한다.
            for (Road next : roads.get(current.end)) {
                if (minDistances[next.end] > current.distance + next.distance) {
                    minDistances[next.end] = current.distance + next.distance;
                    priorityQueue.offer(new Road(next.end, minDistances[next.end]));
                }
            }
            // 방문 체크
            visited[current.end] = true;
        }

        // n -1 지점에 도달하는 최소 거리가 초기값이라면 도착이 불가능한 경우. -1 출력
        // 초기값이 아니라면 그 최소 거리를 출력한다.
        System.out.println(minDistances[n - 1] == Long.MAX_VALUE ? -1 : minDistances[n - 1]);
    }
}