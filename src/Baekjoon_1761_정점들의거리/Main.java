/*
 Author : Ruel
 Problem : Baekjoon 1761번 정점들의 거리
 Problem address : https://www.acmicpc.net/problem/1761
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1761_정점들의거리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Link {
    int end;
    int cost;

    public Link(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static List<List<Link>> connections;
    static int[][] sparseArray;
    static int[] ranks;
    static int[] distance;

    public static void main(String[] args) throws IOException {
        // n개의 정점으로 이루어진 트리가 주어진다.
        // n-1개의 간선과 그 비용이 주어진다
        // 그 후 m개의 줄에 두 정점이 주어질 때, 두 정점 사이의 거리에 대한 비용을 출력하라.
        //
        // 최소공통조상에 대한 문제
        // 정점이 많거나 트리의 깊이가 크다면 연산이 많아진다
        // 따라서 희소 배열을 통해 이를 줄여보자
        // 희소배열은 2^i에 해당하는 조상을 배열로 기억해두는 것이다
        // 이를 이용하여 두 정점이 주어졌을 때, 두 정점의 깊이 차이를 희소 배열을 통해 적은 횟수로 깊이를 맞춰줄 수 있다
        // 따라서 두 정점이 주어진다면
        // 루트로부터 a까지, 루트로부터 b까지의 거리에서 2 * 루트로부터 최소 공통 조상까지의 거리를 빼주면 답이 된다!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        StringTokenizer st;
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            connections.get(a).add(new Link(b, cost));
            connections.get(b).add(new Link(a, cost));
        }

        // n개의 정점이 주어진다면 최대 깊이는 n이 될 수 있다
        // 희소배열은 n의 제곱으로 채우므로, log2를 취해 값을 가져오자.
        sparseArray = new int[n + 1][(int) Math.ceil(Math.log(n) / Math.log(2))];
        // 각 정점의 깊이
        ranks = new int[n + 1];
        // 루트로부터의 거리
        distance = new int[n + 1];
        fillSparseArray(1, 1, new boolean[n + 1]);

        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // root ~ a + root ~ b - 2 * roo ~ 최소 공통 조상
            // = a ~ 최소 공통 조상 ~ b
            sb.append(distance[a] + distance[b] - 2 * distance[findSameAncestor(a, b)]).append("\n");
        }
        System.out.println(sb);
    }

    static int findSameAncestor(int a, int b) {     // 최소 공통 조상을 찾는다.
        // 깊이가 서로 다르다면
        while (ranks[a] != ranks[b]) {
            // 두 차이에 log2값을 취해 가능한 많은 차이를 줄여준다.
            int diff = (int) (Math.log(Math.abs(ranks[a] - ranks[b])) / Math.log(2));
            if (ranks[a] < ranks[b])
                b = sparseArray[b][diff];
            else
                a = sparseArray[a][diff];
        }

        // 같은 깊이가 됐지만 서로 다른 값일 수 있다.
        // 이제 a와 b가 같은 값(최소 공통 조상)이 될 때까지 거슬러 올라간다.
        while (a != b) {
            a = sparseArray[a][0];
            b = sparseArray[b][0];
        }
        return a;
    }

    static void fillSparseArray(int n, int rank, boolean[] visited) {
        visited[n] = true;      // 방문 체크
        ranks[n] = rank;        // 깊이 체크

        // 현재 깊이 따라서 몇번째 희소배열까지 값을 채울 지 알 수 있다
        // rank가 n이라면 이를 log 취한 후 올림한 값까지 희소 배열을 채워나가면 된다.
        for (int i = 1; i < (Math.ceil(Math.log(rank) / Math.log(2))); i++)
            sparseArray[n][i] = sparseArray[sparseArray[n][i - 1]][i - 1];

        // 자식 노드 방문
        for (Link child : connections.get(n)) {
            if (!visited[child.end]) {      // 자식 노드라면(= 이미 방문한 조상 노드가 아니라면)
                sparseArray[child.end][0] = n;      // 자식 노드의 부모 노드로 n 표시
                distance[child.end] = distance[n] + child.cost;     // n ~ child 까지의 간선의 비용만큼 더 거리가 늘어난다.
                // 그리고 child에 대해 fillSparseArray를 불러준다. 깊이는 하나 증가.
                fillSparseArray(child.end, rank + 1, visited);
            }
        }
    }
}