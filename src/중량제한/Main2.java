/*
 Author : Ruel
 Problem : Baekjoon 1939번 중량제한
 Problem address : https://www.acmicpc.net/problem/1939
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 중량제한;

import java.util.*;

class Route {
    int start;
    int end;
    int weight;

    public Route(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}

public class Main2 {
    static final int MAX = 1_000_000_000;
    static int[] ranks;
    static int[] parents;

    public static void main(String[] args) {
        // Kruskal 알고리즘으로 간선을 선택한 후, 그 간선대로 목표 지점을 따라갔을 때의 무게가 최대 무게.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        init(n);

        PriorityQueue<Route> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.weight, o1.weight));
        for (int i = 0; i < m; i++)
            priorityQueue.offer(new Route(sc.nextInt(), sc.nextInt(), sc.nextInt()));

        List<List<Route>> routes = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            routes.add(new ArrayList<>());

        while (!priorityQueue.isEmpty()) {      // 최대 무게가 높은 다리부터 선택
            Route current = priorityQueue.poll();
            if (findParents(current.start) != findParents(current.end)) {       // 두 지점이 연결이 안되었다면
                union(current.start, current.end);      // 연결해주고,
                routes.get(current.start).add(new Route(current.start, current.end, current.weight));       // 해당 다리를 기억해두자.
                routes.get(current.end).add(new Route(current.end, current.start, current.weight));
            }
        }
        int start = sc.nextInt();
        int end = sc.nextInt();
        int[] maxWeight = new int[n + 1];
        maxWeight[start] = MAX;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);     // 시작 지점에서 시작
        boolean[] visited = new boolean[n + 1];
        while (!queue.isEmpty()) {
            int current = queue.poll();
            visited[current] = true;
            if (current == end)     // 목표 지점에 도달했다면 끝
                break;

            for (Route next : routes.get(current)) {        // Kruskal 알고리즘으로 선택된 다리들을 따라간다.
                if (visited[next.end])
                    continue;
                maxWeight[next.end] = Math.min(maxWeight[next.start], next.weight);     // 최대 무게가 갱신된다면 갱신해주고
                queue.offer(next.end);      // 다음 지점으로 이동
            }
        }
        System.out.println(maxWeight[end]);     // 목표 지점에 기록된 무게가 최대 무게.
    }

    static void init(int n) {
        ranks = new int[n + 1];
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
    }

    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }

    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[pa] > ranks[pb])
            parents[pb] = pa;
        else {
            parents[pa] = pb;
            if (ranks[pa] == ranks[pb])
                ranks[pb]++;
        }
    }
}