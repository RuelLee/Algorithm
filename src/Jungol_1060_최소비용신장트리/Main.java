/*
 Author : Ruel
 Problem : Jungol 1060번 최소비용신장트리
 Problem address : https://jungol.co.kr/problem/1060
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1060_최소비용신장트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 학원이 주어진다.
        // n * n 크기로 각 학원을 연결하는 비용이 주어진다.
        // 모든 학원들을 직간접적으로 연결하는 최소 비용은?
        //
        // 최소 신장 트리 문제
        // kruskal 혹은 prim 방식으로 각 정점을 최소 비용으로 연결해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 정점
        int n = Integer.parseInt(br.readLine());

        // 분리 집합을 위한 배열 초기화
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];

        // kruskal
        StringTokenizer st;
        // 모든 간선을 우선순위큐에 담는다.
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // i와 j를 잇는 비용을 우선순위큐에 담는다.
            for (int j = 0; j < i; j++)
                pq.offer(new int[]{i, j, Integer.parseInt(st.nextToken())});
            for (int j = i; j < n; j++)
                st.nextToken();
        }

        int sum = 0;
        // 간선들을 꺼내며, 두 정점이 직간접적으로 연결되지 않았을 경우
        // 두 정점을 해당 간선으로 직접 연결한다.
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            if (findParent(current[0]) == findParent(current[1]))
                continue;

            union(current[0], current[1]);
            sum += current[2];
        }
        // 답 출력
        System.out.println(sum);
    }

    // a와 b를 한 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = parents[a];
        int pb = parents[b];

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}