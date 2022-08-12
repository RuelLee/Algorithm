/*
 Author : Ruel
 Problem : Baekjoon 18223번 민준이와 마산 그리고 건우
 Problem address : https://www.acmicpc.net/problem/18223
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18223_민준이와마산그리고건우;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge {
    int n;
    int distance;

    public Edge(int n, int distance) {
        this.n = n;
        this.distance = distance;
    }
}

public class Main {
    static int v, e, p;
    static List<List<Edge>> roads;

    public static void main(String[] args) throws IOException {
        // v개의 정점, e개의 간선, 친구의 위치 p가 주어진다
        // 1부터 시작하여 -> v로 최단 거리로 가는 도중 p를 거친다면, 친구를 태워가고,
        // 그렇지 않다면 태우지 않는다. 단, 여러 최단 거리 중 p를 거치는 경로가 있다면 반드시 태워야한다.
        // 친구를 태우는 경우에는 SAVE HIM, 그렇지 않은 경우에는 GOOD BYE를 출력하자.
        //
        // Dijkstra 문제.
        // 1 -> p -> v 의 최단 경로와 1 -> v의 최단 경로를 각각 구해 두 값이 같은지 다른지 비교해주자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken()) - 1;

        // 인접리스트
        roads = new ArrayList<>(v);
        for (int i = 0; i < v; i++)
            roads.add(new ArrayList<>());
        // 간선들에 대한 입력 처리
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Edge(b, c));
            roads.get(b).add(new Edge(a, c));
        }

        // p를 거쳐갈 경우 최단 거리
        // 0 ~ p까지, p ~ v - 1까지의 거리를 더해준다.
        int distanceViaP = dijkstra(0, p) + dijkstra(p, v - 1);
        // p를 고려하지 않을 경우 최단 거리.
        int minDistance = dijkstra(0, v - 1);

        // 두 값이 같다면 SAVE HIM, 그렇지 않다면 GOOD BYE 출력.
        System.out.println(distanceViaP == minDistance ? "SAVE HIM" : "GOOD BYE");
    }

    // start ~ end까지의 최단 거리를 구한다.
    static int dijkstra(int start, int end) {
        // start에서 각 정점에 이르는 최단 거리.
        int[] minDistances = new int[v];
        // 값 초기화.
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        minDistances[start] = 0;
        // 방문 체크.
        boolean[] visited = new boolean[v];
        // 각 정점에 이르는 거리가 짧은 순으로 방문한다.
        // 최소힙 우선순위큐를 활용.
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.distance));
        priorityQueue.offer(new Edge(start, 0));
        // 우선순위큐가 빌 때까지.
        while (!priorityQueue.isEmpty()) {
            // 가장 가까운 정점을 꺼낸다.
            Edge current = priorityQueue.poll();
            // 그 정점이 목적지라면 그대로 종료.
            if (current.n == end)
                break;
            // 정점이 기록된 최단 거리보다 큰 값을 갖고 있다면,
            // 최단 거리일 때 이미 처리된 정점이므로 건너뛴다.
            else if (minDistances[current.n] < current.distance)
                continue;

            // current -> next로 가는 경로에 대해 계산한다.
            for (Edge next : roads.get(current.n)) {
                // next가 아직 방문하지 않았고
                // 이전 최단거리보다 current -> next로 가는 경우가 더 짧다면
                if (!visited[next.n] && minDistances[next.n] > current.distance + next.distance) {
                    // 최단 거리 갱신.
                    minDistances[next.n] = current.distance + next.distance;
                    // 해당 거리로 우선순위큐 삽입
                    priorityQueue.offer(new Edge(next.n, minDistances[next.n]));
                }
            }
            // current에 대해선 방문 체크.
            visited[current.n] = true;
        }
        // 최종적으로 minDistances[end]에 기록된 값이 start -> end까지의 최단거리
        // 해당 값 반환.
        return minDistances[end];
    }
}