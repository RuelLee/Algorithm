/*
 Author : Ruel
 Problem : Baekjoon 25428번 분필 도둑
 Problem address : https://www.acmicpc.net/problem/25428
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25428_분필도둑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks, minChalks, members;

    public static void main(String[] args) throws IOException {
        // n개의 교실에 구비된 분필의 수가 주어진다.
        // n-1개의 복도가 준비하고, 두 교실을 연결한다.
        // 분필을 훔치는 과정은 다음과 같다.
        // 서로 직간접적으로 연결된 교실을 골라, 모든 교실에서 동일한 수의 분필을 훔친다.
        // 구비된 분필보다 많은 수를 훔칠 수는 없다.
        // 훔칠 수 있는 가장 많은 수의 분필은?
        //
        // 분리 집합, 정렬 문제
        // 모든 교실에서 동일한 개수의 분필을 훔치기 때문에
        // 직간접적으로 연결된 교실 중, 가장 분필이 적은 교실의 분필 수 * 고른 교실의 수
        // 가 답이 된다.
        // 따라서 교실 연결 상태를, 두 교실 중, 더 적은 분필에 대해 내림차순으로 정렬하여
        // 새로 고르는 교실의 분필 수가 가장 많은 순으로 살펴본다.
        // 그리하여, 점점 고르는 교실의 수를 늘려가며, 답을 살펴본다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 분필 수를 입력 받으며 
        // 교실을 하나만 선택했을 때의 답도 고려한다.
        long answer = 0;
        // 각 교실의 비치된 분필의 수
        // 추후, 연결된 교실들 중 최소 분필의 수로 사용.
        minChalks = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < minChalks.length; i++)
            answer = Math.max(answer, minChalks[i] = Integer.parseInt(st.nextToken()));

        // 교실의 연결 상태
        int[][] connections = new int[n - 1][2];
        for (int i = 0; i < connections.length; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // u 교실의 분필 수가 더 작거나 같은 경우
            if (minChalks[u] <= minChalks[v]) {
                connections[i][0] = u;
                connections[i][1] = v;
            } else {         // v가 더 많은 경우
                connections[i][0] = v;
                connections[i][1] = u;
            }
        }
        // 더 작은 쪽의 분필 수에 대해 내림차순 정렬
        Arrays.sort(connections, (o1, o2) -> Integer.compare(minChalks[o2[0]], minChalks[o1[0]]));

        // 분리 집합을 위한 초기화
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        // 연결된 교실의 수
        members = new int[n + 1];
        Arrays.fill(members, 1);

        // 교실 연결을 순서대로 살펴본다.
        for (int[] c : connections) {
            // 서로 다른 집합이라면
            if (findParent(c[0]) != findParent(c[1])) {
                // 하나의 집합으로 묶고
                union(c[0], c[1]);
                // 훔칠 수 있는 분필의 수가 최대값을 갱신하는지 확인.
                answer = Math.max(answer, (long) members[findParent(c[0])] * minChalks[findParent(c[0])]);
            }
        }
        // 답 출력
        System.out.println(answer);
    }

    static void union(int a, int b) {
        // a가 연결된 교실들의 대표
        int pa = findParent(a);
        // b가 연결된 교실들의 대표
        int pb = findParent(b);

        // ranks에 따른 연산 최소화
        // 연결된 교실의 수, 최소 분필의 수, 집합의 대표 값 갱신.
        if (ranks[pa] >= ranks[pb]) {
            members[pa] += members[pb];
            minChalks[pa] = Math.min(minChalks[pa], minChalks[pb]);
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            members[pb] += members[pa];
            minChalks[pb] = Math.min(minChalks[pa], minChalks[pb]);
            parents[pa] = pb;
        }
    }

    // n이 연결된 교실들의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}