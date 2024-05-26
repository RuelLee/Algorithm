/*
 Author : Ruel
 Problem : Baekjoon 25636번 소방차
 Problem address : https://www.acmicpc.net/problem/25636
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25636_소방차;

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
        // n개의 교차로와 m개의 도로가 주어진다.
        // 각 교차로에서는 소방차에 물을 충전할 수 있다.
        // s 교차로에서 출발하여, t 교차로까지 최소 경로 중 가장 물을 많이 충전할 수 있는 경로로 진행한다.
        // t 교차로까지의 최소 거리와 최대 물 충전량을 출력하라
        // t 교차로에 도달할 수 없다면 -1을 출력한다.
        //
        // 다익스트라 문제
        // dijkstra 알고리즘을 통해 t 교차로까지 최소 거리를 구하며
        // 한 교차로에 도달하는 최소 경로가 여러개가 발견될 경우
        // 충전할 수 있는 물의 양을 해당 경로들 중 최대값으로 한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 교차로와 물 충전량
        int n = Integer.parseInt(br.readLine());
        int[] waters = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // m개의 도로
        int m = Integer.parseInt(br.readLine());
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int w = Integer.parseInt(st.nextToken());

            roads.get(u).add(new Road(v, w));
            roads.get(v).add(new Road(u, w));
        }
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 출발지 s와 도착지 t
        int s = Integer.parseInt(st.nextToken()) - 1;
        int t = Integer.parseInt(st.nextToken()) - 1;
        
        // 각 교차로에 이르는 최소 거리
        long[] minDistances = new long[n];
        Arrays.fill(minDistances, Long.MAX_VALUE);
        minDistances[s] = 0;
        // 각 교차로에 도달할 때, 최대 물 충전량
        long[] maxWaters = new long[n];
        maxWaters[s] = waters[s];
        
        // 방문 체크
        boolean[] visited = new boolean[n];
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.distance));
        priorityQueue.offer(new Road(s, 0));
        while (!priorityQueue.isEmpty()) {
            // 현재 교차로
            Road current = priorityQueue.poll();
            // 이미 계산했다면 건너뛴다.
            if (visited[current.end])
                continue;

            for (Road next : roads.get(current.end)) {
                // current.end -> next.end로 가는 경우.
                // 거리
                long distance = current.distance + next.distance;
                // 최소 거리를 갱신할 경우
                if (minDistances[next.end] > distance) {
                    // 거리 갱신
                    minDistances[next.end] = distance;
                    // 이전 경로보다 더 짧은 경로로 발견되었으므로 물의 양과 상관 없이 갱신.
                    maxWaters[next.end] = maxWaters[current.end] + waters[next.end];
                    // 우선순위큐 추가
                    priorityQueue.offer(new Road(next.end, distance));
                } else if (minDistances[next.end] == distance)      // 같은 경로라면, 최대 물 충전량 계산.
                    maxWaters[next.end] = Math.max(maxWaters[next.end], maxWaters[current.end] + waters[next.end]);
            }
            // 방문 체크
            visited[current.end] = true;
        }
        // t 교차로에 도달할 수 없는 경우
        if (minDistances[t] == Long.MAX_VALUE)
            System.out.println(-1);
        else        // 도달할 수 있는 경우, 그 거리와 물 충전량 출력
            System.out.println(minDistances[t] + " " + maxWaters[t]);
    }
}