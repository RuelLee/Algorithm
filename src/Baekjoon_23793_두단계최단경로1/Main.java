/*
 Author : Ruel
 Problem : Baekjoon 23793번 두 단계 최단 경로 1
 Problem address : https://www.acmicpc.net/problem/23793
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23793_두단계최단경로1;

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
    static List<List<Road>> connections;

    public static void main(String[] args) throws IOException {
        // n개의 정점, m개의 간선이 주어진다.
        // 간선은 방향이 있으며, 가중치가 주어진다.
        // x 지점에서 출발하여 z지점을 가는데
        // y 지점을 거쳐서 가는 경우의 최소 경로의 길이와
        // y 지점을 거치지 않고 가는 경우의 최소 경로를 찾으라.
        //
        // dijkstra 문제
        // 다익스트라 알고리즘을 통해 x -> y, y -> z의 최소 경로를 찾고
        // y 지점으로 가는 경우는 제외한 x -> z의 최소 경로를 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, m개의 간선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 간선들
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            
            // 방향성 그래프이므로, u -> v로만 연결
            connections.get(u).add(new Road(v, w));
        }

        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int z = Integer.parseInt(st.nextToken());
        
        // x -> y
        int xToY = dijkstra(x, y, 0);
        // y -> z
        int yToZ = dijkstra(y, z, 0);
        // y를 거치지 않고, x -> z
        int xToZExceptY = dijkstra(x, z, y);

        StringBuilder sb = new StringBuilder();
        // x -> y -> z
        // 경로가 존재하는지 확인하고, 존재하다면 그 두 길이의 합 기록
        sb.append((xToY == Integer.MAX_VALUE || yToZ == Integer.MAX_VALUE) ? -1 : xToY + yToZ).append("\n");
        // 마찬가지로 y를 거치지 않고 x에서 z로 갈 수 있는지 확인하고 값 기록
        sb.append(xToZExceptY == Integer.MAX_VALUE ? -1 : xToZExceptY);
        // 답안 출력
        System.out.println(sb);
    }
    
    // except를 거치지 않고 start에서 end로 가는 최소 경로의 길이 반환
    static int dijkstra(int start, int end, int except) {
        // 각 지점에 이르는 최소 길이
        int[] minDistances = new int[connections.size()];
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        // 출발점은 0
        minDistances[start] = 0;
        // 방문 체크
        boolean[] visited = new boolean[connections.size()];

        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(r -> r.distance));
        // 출발점
        priorityQueue.offer(new Road(start, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 도착 지점에 도달했다면 종료
            if (current.end == end)
                break;
            // 이미 방문한 적이 있다면 건너뛰기
            else if (visited[current.end])
                continue;
            
            // current에서 next로 가는 경로 탐색
            for (Road next : connections.get(current.end)) {
                // next를 방문하지 않았고, next가 except도 아니며
                // 최소 경로 길이를 갱신한다면
                if (!visited[next.end] && next.end != except &&
                        minDistances[next.end] > minDistances[current.end] + next.distance) {
                    // 경로 길이 반영 후, 우선 순위 큐에 추가
                    minDistances[next.end] = minDistances[current.end] + next.distance;
                    priorityQueue.offer(new Road(next.end, minDistances[next.end]));
                }
            }
            // 방문 체크
            visited[current.end] = true;
        }
        // end에 이르는 거리 반환
        return minDistances[end];
    }
}