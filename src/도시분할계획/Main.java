/*
 Author : Ruel
 Problem : Baekjoon 1647번 도시 분할 계획
 Problem address : https://www.acmicpc.net/problem/1647
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 도시분할계획;

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
    static List<List<Road>> route;
    static boolean[] visited;

    public static void main(String[] args) {
        // 오래만에 풀어본 최소 스패닝 트리 문제
        // kruskal에서 사용하는 union-find는 종종 사용했으므로, prim 알고리즘으로 풀어보았다
        // 임의의 한 점을 시작점으로 채용
        // prim 알고리즘은 임의 한점으로부터 시작하여, 각 지점에 도달하는 최소비용이 갱신되는지 여부를 확인하는 알고리즘
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        init(n, m, sc);

        // 현재 연결되어있고, 아직 해당 지점에서 출발하는 경우를 계산하지 않은 점들 중에 최소 비용인 점에서 시작한다.
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        priorityQueue.offer(new Road(1, 0));
        int[] costs = new int[n + 1];
        Arrays.fill(costs, 1001);       // 각 지점에 도달하는 비용은 1001로 초기화해준다.
        costs[1] = 0;

        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();        // 이번에 계산할 출발점

            for (Road next : route.get(current.end)) {      // 연결된 도로 next
                if (!visited[next.end] && costs[next.end] > next.cost) {        // next 에 도착점을 아직 출발점으로 한 계산이 진행되지 않았고, 해당 지점으로의 도로 비용이 최소로 갱신된다면
                    costs[next.end] = next.cost;        // 최소비용을 갱신하고
                    priorityQueue.offer(next);      // 다음 지점을 출발점으로 시작할 수 있도록 우선순위큐에 담아준다.
                }
            }
            visited[current.end] = true;        // current에서 출발하는 경우를 모두 계산했으므로 방문처리.
        }
        int max = 0;
        int sum = 0;
        for (int i = 1; i < costs.length; i++) {        // costs에 저장된 모든 도로들의 비용을 합한 후
            sum += costs[i];
            max = Math.max(costs[i], max);
        }
        System.out.println(sum - max);      // 가장 큰 비용을 갖는 도로를 끊어 분할된 두 도시로 만들고, 이 때의 도로 비용을 출력.
    }

    static void init(int n, int m, Scanner sc) {
        route = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            route.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int cost = sc.nextInt();

            route.get(a).add(new Road(b, cost));
            route.get(b).add(new Road(a, cost));
        }
        visited = new boolean[n + 1];
    }
}