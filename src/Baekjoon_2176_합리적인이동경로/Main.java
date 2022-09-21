/*
 Author : Ruel
 Problem : Baekjoon 2176번 합리적인 이동경로
 Problem address : https://www.acmicpc.net/problem/2176
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2176_합리적인이동경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int distance;

    public Road(int end, int distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        // 1 -> 2로 이동하려고 한다.
        // 이동할 때 2에 점점 가까워지는 경우, 이를 합리적인 이동경로라고 한다. 이는 최단 경로가 아닐 수 있다.
        // 그래프가 주어질 때 합리적인 이동 경로의 개수를 구하라.
        //
        // 다익스트라 문제.
        // 2에서 시작해서 각 정점의 거리를 모두 구한다.
        // 그리고 다시 1에서부터 시작하며, 2까지의 거리가 점점 줄어드는 경로의 개수만 세어나간다.
        // 물론 일일이 세다간 개수가 너무 많으므로 DP를 이용한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 정점과 간선의 개수.
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 입력 처리.
        List<List<Road>> roads = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, c));
            roads.get(b).add(new Road(a, c));
        }

        // Dijkstra를 이용해 2에서부터 각 정점의 거리를 구한다.
        // 각 정점까지의 거리.
        int[] minDistance = new int[n + 1];
        // 최대값으로 초기화.
        Arrays.fill(minDistance, Integer.MAX_VALUE);
        // 시작점 2의 거리는 0.
        minDistance[2] = 0;
        // 방문 체크.
        boolean[] visited = new boolean[n + 1];
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(r -> r.distance));
        priorityQueue.offer(new Road(2, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 이미 계산이 끝난 정점이라면 건너뜀.
            if (current.distance > minDistance[current.end])
                continue;

            // current에서 연결이 된 정점들을 살펴본다.
            for (Road next : roads.get(current.end)) {
                // 아직 방문 전이며, 최소 거리가 current -> next를 통해 갱신된다면
                if (!visited[next.end] && minDistance[next.end] > current.distance + next.distance) {
                    // 값 갱신.
                    minDistance[next.end] = current.distance + next.distance;
                    // 우선 순위 큐에 해당 거리로 삽입.
                    priorityQueue.offer(new Road(next.end, minDistance[next.end]));
                }
            }
            // 방문 체크.
            visited[current.end] = true;
        }
        // 각 2 -> 각 정점까지의 계산 끝.

        // 각 정점에 합리적인 이동경로의 개수를 센다.
        int[] routes = new int[n + 1];
        // 1에서 출발하므로, 1에서의 경로의 개수 1
        routes[1] = 1;
        // 거리가 먼 순으로 살펴본다.
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(minDistance[o2], minDistance[o1]));
        // 1에서 출발.
        pq.offer(1);
        // 방문 체크
        visited = new boolean[n + 1];
        while (!pq.isEmpty()) {
            int current = pq.poll();
            // 방문한 적이 있다면 건너뜀.
            if (visited[current])
                continue;

            // current에 연결된 정점들을 살펴본다.
            for (Road next : roads.get(current)) {
                // 2에서부터의 거리가 current보다 next가 더 가깝다면 합리적인 이동 경로.
                if (minDistance[next.end] < minDistance[current]) {
                    // current -> next로 가는 경로가 합리적인 이동경로이므로
                    // next에 도달하는 가짓수에 current에 도달한 가짓수를 더해준다.
                    routes[next.end] += routes[current];
                    // pq에 삽입.
                    pq.offer(next.end);
                }
            }
            // 방문 체크
            visited[current] = true;
        }
        // 최종적으로 2에 도달하는 합리적인 이동경로 가짓수를 출력한다.
        System.out.println(routes[2]);
    }
}