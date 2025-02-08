/*
 Author : Ruel
 Problem : Baekjoon 28131번 K-지폐
 Problem address : https://www.acmicpc.net/problem/28131
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28131_K지폐;

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
        // n개의 도시, m개의 단방향 도로가 주어진다.
        // s도시에서 출발하여 t도시에 가고자 하며, 이 때 비용을 최소화하고자 한다.
        // 그런데 지수는 k원 지폐를 좋아하여, k원 지폐만 들고다니며, 총 비용이 k원으로 나누어떨어지길 원한다.
        //
        // 최단 경로, dijkstra 문제
        // s에서 t로 가는 최단 경로를 구하되, 총 비용의 합이 k원으로 나누어떨어져야한다.
        // 따라서, 각 도시에 도달하는 경우의 비용을 k로 나눈 나머지에 따라 별도로 구한다.
        // 그 후, t도시에 도달할 때, 나머지가 0인 경우의 값을 출력해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 도로, k원 지폐
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 출발 도시 s, 도착 도시 t
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 도로들
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        
        // 도로 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            roads.get(u).add(new Road(v, w));
        }
        
        // 각 도시에 도달하는 최소 비용을 k의 나머지 별로 구분
        int[][] minCosts = new int[n + 1][k];
        for (int[] mc : minCosts)
            Arrays.fill(mc, Integer.MAX_VALUE);
        minCosts[s][0] = 0;
        
        // dijkstra
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        priorityQueue.offer(new Road(s, 0));
        while (!priorityQueue.isEmpty()) {
            // 현재 위치
            Road current = priorityQueue.poll();
            if (minCosts[current.end][current.cost % k] < current.cost)
                continue;
            
            // 다음 도시
            for (Road next : roads.get(current.end)) {
                // 총 비용
                int total = current.cost + next.cost;
                // 다음 도시에 도달하는 총 비용이, 해당하는 나머지 만큼의 비용들 중 최소일 경우
                if (minCosts[next.end][total % k] > total) {
                    // 값 갱신 후, 우선순위큐에 추가
                    minCosts[next.end][total % k] = total;
                    priorityQueue.offer(new Road(next.end, total));
                }
            }
        }
        
        // t도시에 나머지 0으로 도달하는 최소 비용 출력
        // 초기값 그대로인 경우에는 불가능한 경우이므로 IMPOSSIBLE 출력
        System.out.println(minCosts[t][0] == Integer.MAX_VALUE ? "IMPOSSIBLE" : minCosts[t][0]);
    }
}