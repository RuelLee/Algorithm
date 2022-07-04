/*
 Author : Ruel
 Problem : Baekjoon 16398번 행성 연결
 Problem address : https://www.acmicpc.net/problem/16398
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16398_행성연결;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Flow {
    int a;
    int b;
    int cost;

    public Flow(int a, int b, int cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }
}

public class Main2 {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n개의 행성과 각 행성 간의 연결 비용이 인접 행렬로 주어진다
        // 모든 행성을 잇는 최소 비용을 구하라
        //
        // 최소 스패닝 트리 문제
        // Kruskal 알고리즘
        // 모든 간선을 비용에 대해 오름차순으로 살펴보며
        // 간선으로 연결된 도시들이 서로 다른 집합일 경우
        // 해당 간선을 선택하고 하나의 집합으로 묶는 연산을 반복한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        init(n);

        int[][] adjMatrix = new int[n][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 모든 간선을 우선순위큐에 담는다.
        PriorityQueue<Flow> priorityQueue = new PriorityQueue<>(Comparator.comparing(flow -> flow.cost));
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].length; j++) {
                if (i == j)
                    continue;

                priorityQueue.offer(new Flow(i, j, adjMatrix[i][j]));
            }
        }

        long sum = 0;
        while (!priorityQueue.isEmpty()) {
            Flow current = priorityQueue.poll();

            // 이번 간선의 양 도시가 같은 집합이 아니라면
            // 해당 간선을 선택하고 비용을 더한다.
            if (findParents(current.a) != findParents(current.b)) {
                union(current.a, current.b);
                sum += current.cost;
            }
        }
        // 최종 비용을 출력한다.
        System.out.println(sum);
    }

    // 집합을 묶는 연산.
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

    // 초기화
    static void init(int n) {
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];
    }

    // 집합의 대표를 찾는 연산.
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }
}