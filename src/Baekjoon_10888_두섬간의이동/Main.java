/*
 Author : Ruel
 Problem : Baekjoon 10888번 두 섬간의 이동
 Problem address : https://www.acmicpc.net/problem/10888
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10888_두섬간의이동;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] parents, ranks, members;

    public static void main(String[] args) throws IOException {
        // n개의 섬이 주어진다.
        // n-1 턴 동안 i와 i+1의 섬을 다리로 잇는다.
        // 섬을 이을 때마다
        // 두 섬 간의 왕래가 가능한 섬들 (i, j) (i < j) 쌍의 개수
        // 와 (i, j)가 왕래가 가능할 때, i -> j 로 가는데 이용하는 다리의 최소 개수 합
        // 을 출력한다.
        //
        // 분리 집합 문제
        // 먼저, 다리가 i와 i+1의 섬을 잇기 때문에
        // 결과적으로 이어진 섬들은 일렬로 이어진다.
        // 따라서 이어진 섬들을 집합으로 묶고, 속한 섬들의 개수에 따라 두 질문의 대답을 구할 수 있다.
        // 일렬로만 이어지기 때문에, 속한 섬의 개수가 같다면, 답 또한 같다.
        // 따라서 속한 섬의 개수에 따른 답을 모두 구해두고
        // 값이 바뀔 때마다 해당 답을 참고하여 출력할 값을 수정해간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 섬
        int n = Integer.parseInt(br.readLine());
        
        // 분리 집합을 위한 초기화
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        members = new int[n + 1];
        Arrays.fill(members, 1);
        
        // pairs[i] = i개의 섬이 이어져있을 때, 왕래 가능한 섬들 쌍의 개수
        long[] pairs = new long[n + 1];
        pairs[2] = 1;
        for (int i = 3; i < parents.length; i++)
            pairs[i] = pairs[i - 1] + i - 1;

        // bridges[i] = i개의 섬이 이어져있을 때, 각 섬들 간의 이동 시에 필요한 최소 경우 다리의 합
        long[] bridges = new long[n + 1];
        bridges[2] = 1;
        for (int i = 3; i < bridges.length; i++)
            bridges[i] = bridges[i - 1] + (long) (i - 1) * (1 + i - 1) / 2;

        // 매 턴 쿼리 처리
        long[] answer = new long[2];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n - 1; i++) {
            // bridge와 bridge + 1 섬을 잇는다.
            int bridge = Integer.parseInt(br.readLine());

            // 먼저 기존 답안에서
            // bridge와 birdge + 1이 속한 그룹에 대해 반영되어있는 값들을 빼준다.
            for (int j = bridge; j < bridge + 2; j++) {
                answer[0] -= pairs[members[findParent(j)]];
                answer[1] -= bridges[members[findParent(j)]];
            }

            // 두 그룹을 하나로 묶고
            union(bridge, bridge + 1);
            // 새로 생긴 그룹에 대한 값을 답에 반영한다.
            answer[0] += pairs[members[findParent(bridge)]];
            answer[1] += bridges[members[findParent(bridge)]];
            // 해당 답 기록
            sb.append(answer[0]).append(" ").append(answer[1]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // a와 b가 속한 그룹을 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            members[pa] += members[pb];
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            parents[pa] = pb;
            members[pb] += members[pa];
        }
    }

    // n이 속한 그룹의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}