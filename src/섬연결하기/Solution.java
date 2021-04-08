package 섬연결하기;

import java.util.PriorityQueue;

class Edge {
    int start;
    int end;
    int cost;

    public Edge(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
}

public class Solution {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) {
        // 노드를 모두 연결 -> kruskal or prim 알고리즘
        // 이번엔 kruskal 알고리즘으로 구현해보자.
        int n = 6;
        int costs[][] = {{0, 1, 5}, {0, 3, 2}, {0, 4, 3}, {1, 4, 1}, {3, 4, 10}, {1, 2, 2}, {2, 5, 3}, {4, 5, 4}};

        parents = new int[n];
        ranks = new int[n];

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));     // priorityQueue로 비용이 최소인 간선부터 확인하자.

        for (int[] edge : costs)
            priorityQueue.add(new Edge(edge[0], edge[1], edge[2]));

        makeSet();
        int totalCost = 0;
        while (!priorityQueue.isEmpty()) {  //priorityQueue에서 최소비용인 간선부터 꺼내서,
            Edge edge = priorityQueue.poll();

            if (findSet(edge.start) != findSet(edge.end)) { // 서로 다른 부모를 갖고 있다면 union으로 합쳐준다!
                union(edge.start, edge.end);
                totalCost += edge.cost;
            }
        }
        System.out.println(totalCost);
    }

    static void makeSet() {
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
    }

    static int findSet(int n) {     // 최상위 부모 노드 찾기.
        if (parents[n] == n)
            return n;
        parents[n] = findSet(parents[n]);   // 자식 노드의 최상위 부모노드를 찾을 때, 자신보다 부모노드의 부모값도 최상위 부모 노드값으로 바꿔주면, 다음 번 findSet 연산에서 중복 연산을 줄일 수 있다.
        return parents[n];
    }

    static void union(int n, int m) {
        int pn = findSet(n);
        int pm = findSet(m);

        if (ranks[pn] > ranks[pm])
            parents[pm] = pn;
        else {
            parents[pn] = pm;
            if (ranks[pn] == ranks[pm])
                ranks[pm]++;
        }
    }
}