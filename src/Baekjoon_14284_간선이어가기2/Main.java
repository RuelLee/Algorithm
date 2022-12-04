/*
 Author : Ruel
 Problem : Baekjoon 14284번 간선 이어가기 2
 Problem address : https://www.acmicpc.net/problem/14284
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14284_간선이어가기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge {
    int end;
    int cost;

    public Edge(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static List<List<Edge>> edges;

    public static void main(String[] args) throws IOException {
        // n개의 정점과 아직 이어지지 않은 간선 m개가 주어질 때
        // s -> e까지 직간접적으로 잇는 최소 비용을 출력하라.
        //
        // 간단한 dijkstra 문제
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 정점, m개의 간선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 인접리스트.
        edges = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            edges.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges.get(a).add(new Edge(b, c));
            edges.get(b).add(new Edge(a, c));
        }

        // 출발점과 도착점
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        // 각 정점에 도달하는 최소 비용.
        int[] minCosts = new int[n + 1];
        Arrays.fill(minCosts, Integer.MAX_VALUE);
        // 시작점은 0
        minCosts[s] = 0;
        // 우선순위큐로 비용이 적은 정점들을 우선적으로 방문한다.
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparing(o -> o.cost));
        priorityQueue.offer(new Edge(s, 0));
        // 방문체크
        boolean[] visited = new boolean[n + 1];
        while (!priorityQueue.isEmpty()) {
            Edge current = priorityQueue.poll();
            // 이전 방문 정점이라면 건너뛴다.
            if (visited[current.end])
                continue;

            // current에서 방문할 수 있는 정점들을 확인한다.
            for (Edge next : edges.get(current.end)) {
                // next를 아직 방문하지 않았고, 도달하는 비용이 이전 기록된 것보다
                // s -> current -> next가 더 적은 비용일 경우.
                if (!visited[next.end] && minCosts[next.end] > minCosts[current.end] + next.cost) {
                    // 비용 갱신
                    minCosts[next.end] = minCosts[current.end] + next.cost;
                    // 해당 값으로 우선순위큐에 삽입.
                    priorityQueue.offer(new Edge(next.end, minCosts[next.end]));
                }
            }
            // 방문 체크.
            visited[current.end] = true;
        }

        // s -> e로 도달하는 비용을 출력.
        System.out.println(minCosts[e]);
    }
}