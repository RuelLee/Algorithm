/*
 Author : Ruel
 Problem : Baekjoon 7044번 Bad Cowtractors
 Problem address : https://www.acmicpc.net/problem/7044
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7044_BadCowtractors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 헛간, m개의 연결 가능한 도로가 주어진다.
        // 건설업자는 모든 헛간을 서로 간에 이동가능하도록 연결하되,
        // 순환이 발생하지 않고, 비용이 최대가 되게끔 하고자 한다.
        // 최종 연결된 형태는 트리 형태이다.
        // 그 때의 비용은? 불가능하다면 -1을 출력한다.
        //
        // 최소 스패팅 트리(최대...?) 문제
        // 흔히 알려진 최소 스패닝 트리 문제.
        // kruskal 혹은 prim 알고리즘으로 풀이 가능.
        // 단 간선은 비용이 큰 순서대로 내림차순으로 본다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 헛간, m개의 연결 가능한 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] connections = new int[m][3];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++)
                connections[i][j] = Integer.parseInt(st.nextToken());
        }
        // 비용에 대해 내림차순으로 정렬
        Arrays.sort(connections, (o1, o2) -> Integer.compare(o2[2], o1[2]));

        // kruskal로 풀기 위한 배열들 초기화
        ranks = new int[n + 1];
        parents = new int[n + 1];
        for (int i = 1; i < n + 1; i++)
            parents[i] = i;

        int sum = 0;
        // 도로들을 비용이 비싼 순으로 살펴보며
        // 양쪽 헛간이 다른 집합에 속한다면, 연결하고, 비용을 누적
        for (int[] c : connections) {
            if (findParent(c[0]) == findParent(c[1]))
                continue;

            union(c[0], c[1]);
            sum += c[2];
        }

        // 최종적으로 모든 헛간이 연결되어 하나의 집합이 되었는지 확인
        for (int i = 2; i <= n; i++) {
            if (findParent(1) != findParent(i)) {
                sum = -1;
                break;
            }
        }
        System.out.println(sum);
    }

    // a와 b를 하나의 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

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