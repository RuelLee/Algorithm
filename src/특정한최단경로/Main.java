/*
 Author : Ruel
 Problem : Baekjoon 1504번 특정한 최단 경로
 Problem address : https://www.acmicpc.net/problem/1504
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 특정한최단경로;

import java.util.*;

class Point {
    int num;
    int cost;

    public Point(int num, int cost) {
        this.num = num;
        this.cost = cost;
    }
}

public class Main {
    static List<List<Point>> costs;
    static final int MAX = 200_000 * 1000 + 1;

    public static void main(String[] args) {
        // 오랜만에 풀어본 다익스트라 문제
        // 한번 이해를 해두었더니 기억에서 꺼내 다시 푸는데 오래 걸리진 않았다.
        // 1 -> n 으로 가는데, v1과 v2를 경유하는 최소 거리를 구하는 문제이다
        // v1과 v2를 경유해가는 방법은  1 -> v1 -> v2 -> n or 1 -> v2 -> v1 -> n
        // 두 가지 방법이므로, 1 -> v1, 1 -> v2, v1 -> v2, v1 -> n, v2 -> n 5가지 경우를 구해 값을 합쳐 각각의 값을 구해주면 된다!
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int e = sc.nextInt();

        costs = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            costs.add(new ArrayList<>());

        for (int i = 0; i < e; i++) {           // 주어지는 간선은 양방향이다!
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            costs.get(a).add(new Point(b, c));
            costs.get(b).add(new Point(a, c));
        }

        int v1 = sc.nextInt();
        int v2 = sc.nextInt();

        int oneToV1 = dijkstra(1, v1);
        int v1ToV2 = dijkstra(v1, v2);
        int v2ToN = dijkstra(v2, n);
        int V1V2;
        if (oneToV1 == MAX || v1ToV2 == MAX || v2ToN == MAX)        // 경로 중 하나라도 못 지나가는 곳이 있다면 v1v2에 MAX 할당.
            V1V2 = MAX;
        else
            V1V2 = oneToV1 + v1ToV2 + v2ToN;

        int oneToV2 = dijkstra(1, v2);
        int V1ToN = dijkstra(v1, n);
        int V2V1;
        if (oneToV2 == MAX || v1ToV2 == MAX || V1ToN == MAX)        // 마찬가지로 경로 중 못 지나가는 곳이 있다면 MAX 할당.
            V2V1 = MAX;
        else
            V2V1 = oneToV2 + v1ToV2 + V1ToN;

        int min = Math.min(V1V2, V2V1);
        System.out.println(min == MAX ? -1 : min);      // 최소 값이 MAX가 아니라면 min 값이 최소비용!

    }

    static int dijkstra(int start, int end) {
        PriorityQueue<Point> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));        // 각 정점에 방문하는 비용이 낮은 순서대로 처리
        boolean[] visited = new boolean[costs.size()];
        visited[start] = true;
        int[] totalCosts = new int[costs.size()];
        Arrays.fill(totalCosts, MAX);
        totalCosts[start] = 0;
        priorityQueue.offer(new Point(start, 0));

        while (!priorityQueue.isEmpty()) {
            Point current = priorityQueue.poll();
            if (current.num == end)     // 도착지점에 도달했다면 종료.
                return current.cost;

            if (current.cost == MAX)        // 만약 더 이상 진행할 수 있는 경로가 없을 경우에도 종료.
                break;

            for (Point next : costs.get(current.num)) {     // current 에서 갈수 있는 정점들 중
                if (!visited[next.num] && totalCosts[next.num] > totalCosts[current.num] + next.cost) {     // 아직 처리를 안했고, 비용이 갱신된다면
                    totalCosts[next.num] = totalCosts[current.num] + next.cost;     // 최소 비용 갱신 후
                    priorityQueue.offer(new Point(next.num, totalCosts[next.num]));         // 우선순위큐에 삽입.
                }
            }
            visited[current.num] = true;        // 처리를 마쳤다면 현재 정점을 방문 표시.
        }
        return MAX;     // 여기까지 왔다면 도착 지점에 도달할 방법이 없는 경우. MAX 값을 리턴해주자.
    }
}