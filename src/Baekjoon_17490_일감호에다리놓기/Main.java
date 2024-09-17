/*
 Author : Ruel
 Problem : Baekjoon 17490번 일감호에 다리 놓기
 Problem address : https://www.acmicpc.net/problem/17490
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17490_일감호에다리놓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Edge {
    int a;
    int b;
    int stones;

    public Edge(int a, int b, int stones) {
        this.a = a;
        this.b = b;
        this.stones = stones;
    }
}

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // 호수를 둘레로 n개 건물이 세워져있다. i번 건물은 i-1번과 i+1번 건물과 이웃해있으며
        // i번 건물과 n번 건물 또한 서로 이웃한다.
        // 이웃한 건물들 사이에는 기본적으로 도로가 있으나, m개의 구간에 대해서 공사중이다.
        // 가운데 중앙에 징검다리를 놓아 이동할 수 있고, 각 건물에서 중앙까지 징검다리를 연결하는데 필요한 돌의 수가 주어진다.
        // 한 건물에서 모든 다른 건물로 이동이 가능케하고자 할 때
        // k개 이하의 돌을 사용해서 가능한지를 계산하라.
        //
        // 최소 스패닝 트리
        // MST 문제. 여러 개의 집합으로 분리되어 시작할 수 있으므로 kruskal 알고리즘을 사용한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 건물, 폐쇄된 m개의 도로, 주어진 돌의 수 k
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());
        
        // 분리 집합을 위한 parents, ranks 배열
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];

        // 간선들을 비용에 따라 살펴본다.
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.stones));
        // 호수 중앙까지 연결하는데 드는 비용
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int s = Integer.parseInt(st.nextToken());
            priorityQueue.offer(new Edge(0, i + 1, s));
        }
        
        // 이웃한 건물과 도로가 폐쇄되었는지 여부
        boolean[] disconnected = new boolean[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (Math.min(a, b) != 1)
                disconnected[Math.min(a, b)] = true;
            else if (Math.max(a, b) == n)
                disconnected[n] = true;
            else
                disconnected[1] = true;
        }

        // 도로가 폐쇄된 경우를 제외하고
        // 이웃한 건물과 한 집합으로 묶는다.
        for (int i = 1; i < disconnected.length - 1; i++) {
            if (!disconnected[i] && findParents(i) != findParents(i + 1))
                union(i, i + 1);
        }
        if (!disconnected[n])
            union(n, 1);

        boolean possible = true;
        // 만약 m이 1이하라면 호수 중앙으로 징검다리를 놓을 필요가 없다.
        // 기존 도로로만으로도 이동할 수 있기 때문
        // 따라서 m이 1 초과인 경우에만 살펴본다.
        if (m > 1) {
            long sum = 0;
            // 모든 건물을 한 집합으로 묶는데 필요한 돌의 개수를 계산한다.
            while (!priorityQueue.isEmpty()) {
                Edge current = priorityQueue.poll();
                if (findParents(current.a) != findParents(current.b)) {
                    union(current.a, current.b);
                    sum += current.stones;
                }
            }
            
            // 만약 k보다 많은 수의 돌이 필요하다면 불가능한 경우
            // false 기록
            if (sum > k)
                possible = false;
        }
        // 답 출력
        System.out.println(possible ? "YES" : "NO");
    }

    // a가 속한 집합과 b가 속한 집합을 하나의 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }
}