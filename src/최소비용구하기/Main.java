/*
 Author : Ruel
 Problem : Baekjoon 1916번 최소비용 구하기
 Problem address : https://www.acmicpc.net/problem/1916
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 최소비용구하기;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Stop {
    int destination;
    int cost;

    public Stop(int destination, int cost) {
        this.destination = destination;
        this.cost = cost;
    }
}

public class Main {
    static List<List<Stop>> route;
    static final int MAX = 1000 * 100_000;

    public static void main(String[] args) {
        // 오랜만에 푼 다익스트라 기본 문제
        // 오래만이라 기억을 더듬더듬.. 그래도 모두 기억해낼 수 있다!
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        init(n);

        for (int i = 0; i < m; i++)
            route.get(sc.nextInt()).add(new Stop(sc.nextInt(), sc.nextInt()));

        int start = sc.nextInt();
        int end = sc.nextInt();

        Stop[] stops = new Stop[n + 1];     // 각 지점의 최소 비용 저장 공간
        for (int i = 0; i < stops.length; i++)
            stops[i] = new Stop(i, MAX);

        stops[start].cost = 0;
        PriorityQueue<Stop> priorityQueue = new PriorityQueue<>(((o1, o2) -> Integer.compare(o1.cost, o2.cost)));
        priorityQueue.offer(stops[start]);
        boolean[] visited = new boolean[n + 1];
        while (!priorityQueue.isEmpty()) {
            Stop current = priorityQueue.poll();
            if (visited[current.destination])       // 이미 계산했다면 패쓰
                continue;

            for (Stop next : route.get(current.destination)) {      // current에서 갈 수 있는 next들 중에
                // next에서 다른 지점으로 출발하는 경우를 아직 계산하지 않았고, next로의 도달하는데 최소 비용이 갱신된다면
                if (!visited[next.destination] && stops[next.destination].cost > current.cost + next.cost) {
                    stops[next.destination].cost = current.cost + next.cost;        // 갱신해주고
                    priorityQueue.offer(stops[next.destination]);       // next를 우선순위큐에 삽입.
                }
            }
            visited[current.destination] = true;        // current는 방문 표시.
        }
        System.out.println(stops[end].cost);        // end 지점의 도달 비용 출력.
    }

    static void init(int n) {
        route = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            route.add(new ArrayList<>());
    }
}