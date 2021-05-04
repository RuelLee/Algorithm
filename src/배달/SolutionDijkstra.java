package 배달;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Path {
    int to;
    int cost;

    public Path(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

public class SolutionDijkstra {
    public static void main(String[] args) {
        // Dijkstra로 풀어보자

        int N = 6;
        int[][] road = {{1, 2, 1}, {1, 3, 2}, {2, 3, 2}, {3, 4, 3}, {3, 5, 2}, {3, 5, 3}, {5, 6, 1}};
        int K = 4;

        // 경로를 list 형태로 만들어 담아두자.
        // path[i]는 (i+1)로부터 갈 수 있는 경로들의 list
        List<Path>[] pathLists = new List[N];
        for (int i = 0; i < pathLists.length; i++)
            pathLists[i] = new ArrayList<>();

        for (int[] rd : road) {
            pathLists[rd[0] - 1].add(new Path(rd[1] - 1, rd[2]));
            pathLists[rd[1] - 1].add(new Path(rd[0] - 1, rd[2]));
        }

        // 0부터의 거리를 저장해둘 Path 배열.
        // 0부터 0까지의 거리는 0이므로 0값을 넣어주고, 다른 경로들의 탐색 전이므로 Integer.MAX_VALUE 값으로 초기화시켜주자.
        // 차후 이를 PriorityQueue에 담아, 현재의 도달비용이 최소인 곳을 하나씩 방문하며, 그 곳으로부터 갈 수 있는 곳을 체크하자.
        Path[] distance = new Path[N];
        PriorityQueue<Path> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        boolean[] check = new boolean[N];

        for (int i = 0; i < distance.length; i++) {
            if (i == 0)
                distance[i] = new Path(i, 0);
            else
                distance[i] = new Path(i, Integer.MAX_VALUE);

            priorityQueue.add(distance[i]);
        }

        while (!priorityQueue.isEmpty()) {
            Path cur = priorityQueue.poll();    // 현재 도달 비용이 최소인 곳을 뽑아

            for (Path next : pathLists[cur.to]) {       // 그곳으로부터 도달할 수 있는 곳을 체크하자.
                if (!check[next.to] && distance[next.to].cost > distance[cur.to].cost + next.cost) {    // 아직 가지 않은 곳이며, 비용이 더 적어질 수 있다면
                    distance[next.to].cost = distance[cur.to].cost + next.cost;     // 비용을 갱신하고

                    priorityQueue.remove(distance[next.to]);    // PriorityQueue에 다시 담아, 정렬해주자.
                    priorityQueue.add(distance[next.to]);
                }
            }
            check[cur.to] = true;   // 현재 도달 비용이 최소인 곳에서부터 갈 수 있는 곳을 모두 체크했다. true값으로 남겨주자.
        }
        int count = 0;
        for (Path p : distance) {
            if (p.cost <= K)
                count++;
        }
        System.out.println(count);
    }
}