/*
 Author : Ruel
 Problem : Baekjoon 5972번 택배 배송
 Problem address : https://www.acmicpc.net/problem/5972
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5972_택배배송;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Route {
    int end;
    int cost;

    public Route(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static List<List<Route>> routes;

    public static void main(String[] args) throws IOException {
        // n개의 헛간, m개의 길이 주어진다.
        // 1번 헛간에서 출발하여 n번 헛간에 도달하려 한다.
        // 각 도로에는 소들이 있는데, 들리는 도로마다 모든 소에게 여물을 줘야한다.
        // 목적지까지 도달하는데 필요한 최소한의 여물은?
        //
        // dijkstra 문제
        // 1번에서 시작해서 n에 도달하는데까지 다익스트라를 통해 최소 비용을 구하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 주어진 입력
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // n개의 헛간에 대한 도로들의 정보를 인접 리스트로 구현.
        routes = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            routes.add(new ArrayList<>());

        // 도로의 제한이 없으므로 양방향.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            routes.get(a).add(new Route(b, c));
            routes.get(b).add(new Route(a, c));
        }

        // 각 헛간에 도달하는데 필요한 최소 여물의 수.
        int[] minDistance = new int[n + 1];
        // 큰 값으로 초기화.
        Arrays.fill(minDistance, Integer.MAX_VALUE);
        // 시작지점은 0
        minDistance[1] = 0;

        // 지점 별로 도달하는 여물의 수가 적은 순으로 살펴본다.
        PriorityQueue<Route> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(r -> r.cost));
        priorityQueue.offer(new Route(1, 0));
        // 방문 체크.
        boolean[] visited = new boolean[n + 1];
        while (!priorityQueue.isEmpty()) {
            // currnet의 순서.
            Route current = priorityQueue.poll();
            // 기록된 여물의 수보다 current가 더 많은 여물이 필요하다고 기록되어있다면
            // 이미 이전에 계산을 한 경우. 건너 뛴다.
            if (current.cost > minDistance[current.end])
                continue;

            // current에 연결된 모든 도로들을 살펴본다.
            for (Route r : routes.get(current.end)) {
                
                // 아직 방문체크가 안되어있고, 해당 도로를 통해 r.end로 도달하는 것이
                // 이전에 기록된 최소 여물 수보다 더 적은 여물이 필요하다면
                if (!visited[r.end] && minDistance[r.end] > minDistance[current.end] + r.cost) {
                    // 최소 여물의 수 갱신
                    minDistance[r.end] = minDistance[current.end] + r.cost;
                    // 새로운 값으로 우선순위큐 삽입.
                    priorityQueue.offer(new Route(r.end, minDistance[r.end]));
                }
            }
            // current에서 모든 계산이 끝났으므로 방문 체크.
            visited[current.end] = true;
        }

        // n지점에 도달하는 최소 비용 출력.
        System.out.println(minDistance[n]);
    }
}