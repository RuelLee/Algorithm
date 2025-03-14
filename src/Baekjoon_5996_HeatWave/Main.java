/*
 Author : Ruel
 Problem : Baekjoon 5996번 Heat Wave
 Problem address : https://www.acmicpc.net/problem/5996
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5996_HeatWave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int cost;

    public Road(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 마을과 c개의 양방향 도로가 주어진다.
        // 각 마을(시작 마을과 도착 마을을 제외한)은 최소 2개의 다른 도시와 연결되어있다.
        // ts 마을에서 출발하여 te 마을에 도착한다할 때, 최소 비용은?
        //
        // 다익스트라
        // dijkstra 알고리즘 돌려주면 되는 간단한 문제.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // t개의 도시, c개의 도로
        int t = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        // 시작 도시 ts, 도착 도시 te
        int ts = Integer.parseInt(st.nextToken());
        int te = Integer.parseInt(st.nextToken());
        
        // 간선 정보 입력
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i <= t; i++)
            roads.add(new ArrayList<>());

        for (int i = 0; i < c; i++) {
            st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            int ci = Integer.parseInt(st.nextToken());

            roads.get(r1).add(new Road(r2, ci));
            roads.get(r2).add(new Road(r1, ci));
        }

        // dijkstra
        // 각 마을에 도달하는 최소 비용
        int[] minCosts = new int[t + 1];
        Arrays.fill(minCosts, Integer.MAX_VALUE);
        minCosts[ts] = 0;
        // 방문 여부
        boolean[] visited = new boolean[t + 1];
        // 비용이 도달 비용이 싼 도시부터 탐색
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        priorityQueue.offer(new Road(ts, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 도착 도시에 도달한 경우 종료
            if (current.end == te)
                break;
            // 이미 방문한 도시라면 건너뜀.
            else if (visited[current.end])
                continue;
            
            // current.end에서 도달할 수 있는 다른 도시들을 체크
            for (Road next : roads.get(current.end)) {
                if (!visited[next.end] && minCosts[next.end] > current.cost + next.cost) {
                    minCosts[next.end] = current.cost + next.cost;
                    priorityQueue.offer(new Road(next.end, minCosts[next.end]));
                }
            }
            // 방문 표시
            visited[current.end] = true;
        }
        // te에 도달하는 최소 비용 출력
        System.out.println(minCosts[te]);
    }
}