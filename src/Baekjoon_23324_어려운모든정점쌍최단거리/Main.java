/*
 Author : Ruel
 Problem : Baekjoon 23324번 어려운 모든 정점 쌍 최단 거리
 Problem address : https://www.acmicpc.net/problem/23324
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23324_어려운모든정점쌍최단거리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 정점, m개의 양방향 간선으로 이루어진 단순 연결 그래프가 주어진다.
        // k번째 간선 딱 하나만 1의 가중치가 있다.
        // 1 <= i < j <= n을 만족하는 모든 (i, j) 쌍에 대해 정점 간의 최단거리를 전부 더한 값을 출력하라
        //
        // 분리 집합 문제
        // k번째 간선을 제외하고 나머지 모든 간선을 연결한다.
        // 그 후, k번째 간선의 한 정점과 직간접적으로 연결된 정점들과
        // 다른 한 정점과 직간접적으로 연결된 정점의 개수를 곱해주면 된다.
        // 단순 연결 그래프이므로, k번째 간선이 두 그룹을 연결하는 유일한 간선이 아닐 수도 있다.
        // 곱이 int 범위를 벗어날 수 있음에 주의

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, m개의 간선, k번째 간선의 가중치만 1
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 분리 집합을 위한 배열 초기화
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];

        // k번째 간선을 기준으로 두 그룹으로 나눈다.
        int[] teams = new int[2];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // k번째 간선일 경우, 팀을 나누기만 하고
            // 정점을 연결하진 않는다.
            if (i == k - 1) {
                teams[0] = a;
                teams[1] = b;
                continue;
            }

            // k번째 간선이 아니면, 두 정점을 연결한다.
            if (findParent(a) != findParent(b))
                union(a, b);
        }

        // 각 그룹에 속한 정점의 개수를 센다.
        int[] counts = new int[2];
        // a팀과 b팀
        int pa = findParent(teams[0]);
        int pb = findParent(teams[1]);
        // 두 그룹을 연결하는 간선이 k번째 간선이 유일할 경우
        if (pa != pb) {
            for (int i = 1; i < parents.length; i++) {
                // a 그룹에 속한 정점일 경우, 해당 개수 증가
                if (findParent(i) == pa)
                    counts[0]++;
                // b 그룹에 속한 정점일 경우, 해당 개수 증가
                else if (findParent(i) == pb)
                    counts[1]++;
            }
        }
        // 두 그룹에 속한 정점들을 서로 곱한 값이
        // 1<= i < j <= n에 해당하는 모든 (i, j)에 대한 경로의 가중치 합
        System.out.println((long) counts[0] * counts[1]);
    }

    // a와 b가 속한 두 그룹을 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a), pb = findParent(b);
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