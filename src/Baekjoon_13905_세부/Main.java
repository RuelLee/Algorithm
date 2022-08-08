/*
 Author : Ruel
 Problem : Baekjoon 13905번 세부
 Problem address : https://www.acmicpc.net/problem/13905
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13905_세부;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Bridge {
    int start;
    int end;
    int weightLimit;

    public Bridge(int start, int end, int weightLimit) {
        this.start = start;
        this.end = end;
        this.weightLimit = weightLimit;
    }
}

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n개의 섬이 주어지며, 이 섬들을 잇는 m개의 다리가 주어진다
        // 다리는 두 섬과 무게 제한이 주어진다
        // s에서 출발하여 e로 도달하려할 때, 최대 들고 갈 수 있는 무게가 얼마인가?
        //
        // 크루스칼 문제
        // 다익스트라인가..? 라고 생각했는데
        // 섬이 최대 10만개, 다리가 최대 30만개 주어지므로 가능하다면 한번에 찾을 수 있어야한다
        // kruskal 알고리즘으로 무게 제한이 큰 다리부터 연결하기 시작한다.
        // 그러다 s와 e가 같은 그룹으로 묶이는 시점의 다리 제한이 s에서 e로 옮길 수 있는 최대 중량이 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        init(n);

        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken()) - 1;
        int e = Integer.parseInt(st.nextToken()) - 1;

        // 다리의 중량 내림차순. 최대힙
        PriorityQueue<Bridge> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.weightLimit, o1.weightLimit));
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int h1 = Integer.parseInt(st.nextToken()) - 1;
            int h2 = Integer.parseInt(st.nextToken()) - 1;
            int k = Integer.parseInt(st.nextToken());

            priorityQueue.offer(new Bridge(h1, h2, k));
        }

        // s -> e 가 연결되지 않을 수 있으므로 초기값은 0
        int answer = 0;
        // 다리들을 중량 제한 내림차순으로 살펴본다.
        while (!priorityQueue.isEmpty()) {
            Bridge current = priorityQueue.poll();
            // 이미 다리의 양쪽이 연결이 되어있다면 건너뛴다.
            if (findParents(current.start) == findParents(current.end))
                continue;

            // 그렇지 않다면 다리를 연결하고
            union(current.start, current.end);
            // s와 e가 같은 그룹으로 묶였는지 확인한다.
            // 그렇다면 현재 다리 중량 제한이 옮길 수 있는 최대 무게가 된다.
            if (findParents(s) == findParents(e)) {
                answer = current.weightLimit;
                break;
            }
        }
        System.out.println(answer);
    }

    // a와 b를 한 그룹으로 묶는다.
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

    // n이 속한 그룹의 대표자를 찾는다.
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }

    static void init(int n) {
        parents = new int[n];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];
    }
}