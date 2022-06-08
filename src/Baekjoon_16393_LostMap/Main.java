/*
 Author : Ruel
 Problem : Baekjoon 16393번 Lost Map
 Problem address : https://www.acmicpc.net/problem/16393
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16393_LostMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge {
    int a;
    int b;
    int cost;

    public Edge(int a, int b, int cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }
}

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n개의 도시가 주어진다.
        // 도시 간 연결 도로를 건설하는데 비용이 많이 들기 때문에,
        // 각 마을이 다른 마을에 도달할 수 있는 최소한의 도로를 건설하고자 한다.
        // 이 때 건설되는 도로들을 출력하라.
        //
        // 최소 스패닝 트리 문제
        // 대표적으로 Kruskal 알고리즘과 Prim 알고리즘이 있다.
        // 분리 집합을 이용하는 Kruskal을 이용해보았다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        init(n);

        // 특이하게 인접 행렬 자체가 주어진다
        // 양방향 도로로 동일한 값이 두 번 주어지므로, 왼쪽 위에서 오른쪽 아래로 선을 그어
        // 나뉘는 두 부분 중 아랫부분의 값만 취하였다.
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < i; j++)
                priorityQueue.offer(new Edge(j + 1, i + 1, Integer.parseInt(st.nextToken())));
        }

        // 연결된 도로들을 저장해둔다.
        List<Edge> connected = new ArrayList<>(n - 1);
        // 우선순위큐에 연결 비용이 적은 도로들이 최소힙으로 저장되어있다.
        // 차례대로 꺼내가며
        while (!priorityQueue.isEmpty()) {
            Edge current = priorityQueue.poll();
            // 두 도시 간에 이미 성립된 도로가 있다면(= 같은 집합에 속한다면)
            // 건너뛴다.
            if (findParents(current.a) == findParents(current.b))
                continue;

            // 그렇지 않다면, current 도로를 선택하여 두 도시를 연결하고, union 연산을 해준다.
            union(current.a, current.b);
            // connected에 해당 도로 추가.
            connected.add(current);
        }

        // 최종적으로 connected에 연결된 모든 도로들을 꺼내며 출력해준다.
        StringBuilder sb = new StringBuilder();
        for (Edge e : connected)
            sb.append(e.a).append(" ").append(e.b).append("\n");
        System.out.print(sb);
    }
    
    // 분리 집합에 사용할 parents 배열과 ranks 배열 초기화
    static void init(int n) {
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
    }
    
    // 집합의 대표를 찾는 메소드
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        // 경로 단축
        return parents[n] = findParents(parents[n]);
    }

    // 같은 집합으로 묶는 union 연산.
    static void union(int a, int b) {
        // 각 a와 b의 대표를 각각 찾는다.
        int pa = findParents(a);
        int pb = findParents(b);

        // 그 후 ranks를 활용해 더 적은 rank를 갖는 집합을
        // 더 큰 rank를 갖는 집합에 속하게 한다.(연산 최소화)
        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }
}