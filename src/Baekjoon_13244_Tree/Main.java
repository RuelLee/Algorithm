/*
 Author : Ruel
 Problem : Baekjoon 13244번 Tree
 Problem address : https://www.acmicpc.net/problem/13244
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13244_Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;
    static HashSet<Integer> hashSet;

    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 테스트케이스마다 n개의 정점, m개의 간선이 주어진다.
        // 이 때, 주어진 그래프가 트리인지, 그냥 그래프인지 판별하라
        //
        // 분리 집합 문제
        // 주어진 간선들을 모두 이었을 때, 서로 다른 집합의 정점들과만 묶이고
        // 하나의 집합으로 합쳐진다면 이는 트리이다.
        // 하지만 이미 같은 집합인 정점을 연결하는 간선이 존재하거나
        // 여러 개의 집합으로 나뉘어져있다면 이는 그냥 그래프이다.

        // 분리 집합을 위한 배열
        parents = new int[1001];
        ranks = new int[1001];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        hashSet = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int testCase = 0; testCase < t; testCase++) {
            // 정점의 개수 n
            int n = Integer.parseInt(br.readLine());
            init(n);

            // 간선의 개수 m
            int m = Integer.parseInt(br.readLine());
            boolean isTree = true;
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                // 간선, a와 b를 연결한다.
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                // 두 정점이 서로 다른 집합에 속해있다면
                // 하나의 집합으로 묶는다.
                if (findParent(a) != findParent(b))
                    union(a, b);
                else        // 이미 같은 집합이라면 단순 그래프
                    isTree = false;
            }

            // 1 ~ n까지의 집합의 대표들을 해쉬셋에 담는다.
            for (int i = 1; i <= n; i++)
                hashSet.add(findParent(i));
            // 만약 두 개 이상의 그룹으로 나뉜다면 단순 그래프
            if (hashSet.size() >= 2)
                isTree = false;

            // 답 기록
            sb.append(isTree ? "tree" : "graph").append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // a와 b를 하나의 집합으로 묶는다.
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

    // 초기화
    static void init(int n) {
        for (int i = 1; i <= n; i++) {
            parents[i] = i;
            ranks[i] = 0;
        }
        hashSet.clear();
    }
}