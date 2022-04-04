/*
 Author : Ruel
 Problem : Baekjoon 13907번 세금
 Problem address : https://www.acmicpc.net/problem/13907
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13907_세금;

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

class State {
    int city;
    int via;

    public State(int city, int via) {
        this.city = city;
        this.via = via;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시를 연결하는 m개의 도로, 시작점 s, 도착점 d가 주어진다.
        // 그리고 k번에 모든 도로의 비용을 점차적으로 증가시킨다고 한다.
        // 도로 비용을 증가시키기 전 s -> d에 도달하는 최소 비용과
        // 각 증가시킨 후의 최소 비용을 출력하라
        //
        // 다익스트라이긴한데, 이전에 거쳐온 도로의 개수에 따라 비용이 변한다
        // 따라서 다익스트라로 돌리긴 하되, 거쳐온 도로의 개수만큼으로 분리해서 계산해야한다
        // 그리고 최종적으로 구해지는 거쳐온 도로에 따른 최소 비용을 토대로 각 비용을 증가시키면서 최소 비용을 찾는다
        //
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        List<List<Road>> roads = new ArrayList<>();     // 도로들을 입력 받음.
        List<HashMap<Integer, Integer>> minCosts = new ArrayList<>();       // 거쳐온 도로에 따른 최소 거리를 해쉬맵으로 저장하자.
        for (int i = 0; i < n + 1; i++) {
            roads.add(new ArrayList<>());
            minCosts.add(new HashMap<>());
        }

        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, w));
            roads.get(b).add(new Road(a, w));
        }

        // 다익스트라
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparing(o -> minCosts.get(o.city).get(o.via)));
        priorityQueue.offer(new State(s, 0));       // 시작점
        minCosts.get(s).put(0, 0);      // 시작점의 최소 비용
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();       // 현재 상태

            for (Road r : roads.get(current.city)) {        // 현재 도시에 연결된 도로들
                boolean shorterRouteFoundAlready = false;
                for (int via : minCosts.get(r.end).keySet()) {     // r.end 도시로 도달하는 방법 중 이미 더 적은 도로와 더 적은 비용으로 도달하는게 가능하다면
                    if (via <= current.via + 1 && minCosts.get(r.end).get(via) <= minCosts.get(current.city).get(current.via) + r.cost) {
                        shorterRouteFoundAlready = true;
                        break;
                    }
                }
                // 건너 뜀
                if (shorterRouteFoundAlready)
                    continue;

                // 그렇지 않다면, r.end에 방문한다
                // 거쳐온 도로의 수는 current.via + 1, 이 때의 비용은 current.city에 current.via로 도달하는 비용 + r도로를 이용하는 비용
                minCosts.get(r.end).put(current.via + 1, minCosts.get(current.city).get(current.via) + r.cost);
                // 그리고 다음 도시를 우선순위 큐에 삽입.
                priorityQueue.offer(new State(r.end, current.via + 1));
            }
        }

        StringBuilder sb = new StringBuilder();
        // d에 도달하는 방법들 중 가장 비용이 싼 것 출력.
        sb.append(minCosts.get(d).values().stream().mapToInt(i -> i).min().getAsInt()).append("\n");
        for (int i = 0; i < k; i++) {
            // 각 도로마다 p만큼 비용이 증가
            int p = Integer.parseInt(br.readLine());

            // d에 도달하는 방법들을 살펴보며
            // 경유한 도로의 수 * p만큼 비용을 증가시킨다.
            for (int via : minCosts.get(d).keySet())
                minCosts.get(d).put(via, minCosts.get(d).get(via) + p * via);
            // 그 중에 비용이 가장 적은 것 출력.
            sb.append(minCosts.get(d).values().stream().mapToInt(value -> value).min().getAsInt()).append("\n");
        }
        System.out.println(sb);
    }
}