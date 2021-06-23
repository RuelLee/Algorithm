package 최단경로;

import java.util.*;

class Route {
    int num;
    int cost;

    public Route(int num, int cost) {
        this.num = num;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) {
        // 간단한 다익스트라 문제.
        // 다익스트라에만 충실하면 된다.
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt();
        int E = sc.nextInt();
        int K = sc.nextInt();

        List<Route>[] lists = new List[V + 1];      // i에서 출발하는 경로들을 저장할 리스트.
        for (int i = 1; i < lists.length; i++)
            lists[i] = new ArrayList<>();

        for (int i = 0; i < E; i++)
            lists[sc.nextInt()].add(new Route(sc.nextInt(), sc.nextInt()));

        boolean[] check = new boolean[V + 1];       // 각 정점에서 출발 여부를 표시할 boolean 배열
        Route[] minCost = new Route[V + 1];         // 각 정점의 최소비용을 저장할 배열.
        PriorityQueue<Route> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        for (int i = 1; i < minCost.length; i++) {
            if (i == K)
                minCost[i] = new Route(i, 0);
            else
                minCost[i] = new Route(i, Integer.MAX_VALUE);
            priorityQueue.add(minCost[i]);
        }

        while (!priorityQueue.isEmpty()) {      // 현재 도달하는 비용이 가장 적은 정점부터 하나씩 뽑아가며
            Route current = priorityQueue.poll();
            if (current.cost == Integer.MAX_VALUE)      // 현재 뽑은 정점이 아직 연결이 안되었다면, 이후 정점들도 연결이 안되어있다. 스탑.
                break;

            for (Route r : lists[current.num]) {        // r.num으로 도달하는 최소비용이, current.num을 거쳐 갈 경우, 최소비용이 갱신된다면
                if (!check[r.num] && minCost[r.num].cost > current.cost + r.cost) {
                    minCost[r.num].cost = current.cost + r.cost;        // 값 갱신
                    priorityQueue.remove(minCost[r.num]);           // 우선순위큐에서 위치 재배치.
                    priorityQueue.add(minCost[r.num]);
                }
            }
            check[current.num] = true;      // current 에서 출발 가능한 경로 모두 탐색했으니 check.
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < minCost.length; i++)
            stringBuilder.append(minCost[i].cost == Integer.MAX_VALUE ? "INF" : minCost[i].cost).append("\n");
        System.out.println(stringBuilder);
    }
}