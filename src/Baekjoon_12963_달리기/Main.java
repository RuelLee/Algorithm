/*
 Author : Ruel
 Problem : Baekjoon 12963번 달리기
 Problem address : https://www.acmicpc.net/problem/12963
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12963_달리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n개의 교차로, m개의 양방향 도로가 주어진다.
        // i번 도로는 최대 3^i명의 인원만 통행할 수 있다고 한다.
        // 0번 교차로에서 n-1번 교차로로 이동할 수 있는 최대 인원의 수는?
        //
        // 분리 집합 문제
        // 처음 딱 보고는 최대 유량 문제네 하고 풀려고 했으나
        // 값의 범위가 너무 크고, 0번 교차로부터 차례대로 살펴볼 경우,
        // mod된 값 때문에 예를 들어, 1_000_000_007 + 1인 도로를 지나고, 1_000_000_007 - 1인 도로를 지나 n-1에 도달한다면
        // 처음에 mod 된 값이 1이고, 두번째 역시 통과하더라도 1 밖에 되지 않으므로, 값이 1로 계산되버린다.
        // 하지만 실제로는 1_000_000_007 - 1명이 지나갈 수 있다.
        // 분리 집합으로 푸는 문제였다.
        // 먼저 i < j인 수에 대해 3^0 + ... + 3^i < 3^j 이다.
        // j가 i보다 큰 경우, j 미만의 도로의 모든 통행량을 합쳐도 j번 도로보다 작다. 이 점이 중요하다.
        // 통행량이 큰 도로부터 살펴보면, 연결된 경로에서 가장 작은 통행량을 갖는 도로를 찾을 수 있기 때문.
        // 따라서 값이 큰 도로부터 살펴보며, 해당 도로의 양쪽의 대표를 찾는다.
        // 해당 집합들이 0번 교차로와 n-1번 교차로가 포함되어있다면, 이번 도로를 통해 0번 교차로와 n-1번 교차로가 연결된다.
        // 도로의 통행량이 많은 순서부터 살펴보고 있으므로, 현재 경로의 최대 통행 인원은 현재 살펴보는 도로의 통행량이다.
        // 따라서 현재 도로의 통행량을 더해준다.
        // 다만 더하기만 할 뿐, 한 집합으로 묶지는 않아서 다른 경로를 통해 0번 교차로와 n-1번 교차로가 연결되는 경우도 계산한다.
        // 이와 같이 차례대로 살펴보며, 해당 도로를 통해 0번 교차로와 n-1번 교차로가 연결되면 해당 도로의 통행량을 누적시키되, 한 집합으로 묶지는 않는다.
        // 해당 도로를 연결하더라도 0번 교차로와 n-1번 교차로가 연결되지 않는다면 두 교차로를 한 집합으로 묶는 과정을 반복해나간다.
                
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 교차로, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 도로 정보
        int[][] roads = new int[m][3];
        // 각 도로의 통행량을 미리 구해둔다.
        int traffic = 1;
        for (int i = 0; i < roads.length; i++) {
            // 도로를 연결하는 양 교차로
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                roads[i][j] = Integer.parseInt(st.nextToken());
            // 해당 도로의 통행량
            roads[i][2] = traffic;
            traffic = (int) ((traffic * 3L) % LIMIT);
        }
        
        // 분리 집합를 위한 배열 초기화
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];
        
        // 누적 통행량
        int answer = 0;
        for (int i = roads.length - 1; i >= 0; i--) {
            // 도로의 양 교차로가 속한 집합의 대표들을 찾는다.
            int pa = findParent(roads[i][0]);
            int pb = findParent(roads[i][1]);

            int parent0 = findParent(0);
            int parentN1 = findParent(n - 1);
            // 양 교차로의 집합에 0번 교차로와 n-1번 교차로가 속한 경우
            // 이번 도로를 통해 0번 교차로와 n-1번 교차로 연결된다.
            // 따라서 이번 도로의 통행량을 누적시킨다.
            // 다만 양 교차로를 한 집합으로 묶지는 않아, 더 낮은 번호의 도로를 통해 연결되는 경우도 계산할 수 있도록 한다.
            if ((parent0 == pa && parentN1 == pb) ||
                    (parent0 == pb && parentN1 == pa)) {
                answer += roads[i][2];
                answer %= LIMIT;
            } else      // 연결되지 않는다면 한 집합으로 묶는다.
                union(pa, pb);
        }
        // 최종 답 출력
        System.out.println(answer);
    }

    // a가 속한 집합과 b가 속한 집합을 한 집합으로 묶는다.
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