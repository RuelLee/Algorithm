/*
 Author : Ruel
 Problem : Baekjoon 1854번 K번째 최단경로 찾기
 Problem address : https://www.acmicpc.net/problem/1854
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1854_K번째최단경로찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int to;
    int distance;

    public Road(int to, int distance) {
        this.to = to;
        this.distance = distance;
    }
}

class State {
    int city;
    int distance;

    public State(int city, int distance) {
        this.city = city;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시, m개의 도로가 주어진다
        // 각 도시를 k번째의 최단 경로로 방문하려고 한다
        // 해당 도시를 k번째로 방문하는 것이 불가능하다면 -1, 가능하다면 그 때 거리를 출력하라
        //
        // 문제를 이해하기에 k번째 최단 경로로써 기록되려면, 해당 도시를 처음 방문해야한다고 생각했다
        // 만약 a -> b -> a 같이 a를 이미 방문했지만 b를 거쳐서 a를 다시 돌아온 경우, 이게 a에 대한 최단 경로 중 하나이냐에 대한 생각이었다
        // 나는 아니다라는 생각을 갖고 풀었지만, 문제의 의도로는 상관이 없는 모양이다.
        // 이렇게 되면, 다익스트라 알고리즘을 돌리되, 해당 도시에 대한 최단 경로를 최대힙 우선순위큐로 관리해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            roads.get(Integer.parseInt(st.nextToken())).add(new Road(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        List<PriorityQueue<Integer>> minDistance = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            minDistance.add(new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1)));      // 최대힙

        // 다익스트라 알고리즘
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparing(o -> o.distance));
        priorityQueue.offer(new State(1, 0));
        minDistance.get(1).add(0);
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();

            // current.city에 연결된 도로들
            for (Road next : roads.get(current.city)) {
                // 연결된 도시의 최단 거리가 k개 미만이거나
                // 이번에 방문하는 경우가 현재 기록된 k번째 방문보다 더 짧은 거리를 갖고 있다면
                if (minDistance.get(next.to).size() < k ||
                        (!minDistance.get(next.to).isEmpty() && minDistance.get(next.to).peek() > current.distance + next.distance)) {
                    // 해당 거리를 최대힙 우선순위큐에 추가.
                    minDistance.get(next.to).add(current.distance + next.distance);
                    // 다음 번에 방문하도록 우선순위큐에 추가
                    priorityQueue.offer(new State(next.to, current.distance + next.distance));
                }
                // 최대힙의 개수가 k개보다 많다면, k개로 줄여준다.
                while (minDistance.get(next.to).size() > k)
                    minDistance.get(next.to).poll();
            }
        }

        StringBuilder sb = new StringBuilder();
        // 1번 도시부터, 각 도시의 k번째 방문 거리를 기록한다. k개가 되지 않는다면 -1을 출력.
        for (int i = 1; i < minDistance.size(); i++)
            sb.append(minDistance.get(i).size() < k ? -1 : minDistance.get(i).poll()).append("\n");
        System.out.print(sb);
    }
}