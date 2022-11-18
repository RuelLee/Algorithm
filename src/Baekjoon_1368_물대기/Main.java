/*
 Author : Ruel
 Problem : Baekjoon 1368번 물대기
 Problem address : https://www.acmicpc.net/problem/1368
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1368_물대기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Cost {
    int start;
    int end;
    int cost;

    public Cost(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n개의 논이 있다.
        // 논에 물을 대는 방법은 직접 우물을 파는 방법과 다른 논에서 물을 끌어오는 방법이 있다.
        // 각 논에 우물을 파는 비용과, 다른 논에서 물을 끌어오는 비용이 주어질 때
        // 최소 비용으로 모든 우물에 물을 대는 비용을 구하라
        //
        // 최소 신장 트리 문제.
        // 인데, 모든 정점을 하나로 연결하는 것이 아니라
        // 우물을 파는 곳을 기점으로 여러개의 신장 트리가 생길수 있다.
        // 이 점 유의하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        init(n);

        // 최소 힙 우선순위큐
        PriorityQueue<Cost> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        // i번 논에서 직접 우물을 팔 때 비용
        for (int i = 0; i < n; i++) {
            int wellCost = Integer.parseInt(br.readLine());
            priorityQueue.offer(new Cost(i, i, wellCost));
        }
        
        // i, j번 논을 연결할 때, 연결 비용
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < i; j++)
                priorityQueue.offer(new Cost(i, j, Integer.parseInt(st.nextToken())));
        }

        // 물이 공급되었는지 표시
        boolean[] watered = new boolean[n];
        // 전체 비용
        int sum = 0;
        while (!priorityQueue.isEmpty()) {
            Cost current = priorityQueue.poll();
            int pStart = findParent(current.start);
            int pEnd = findParent(current.end);

            // 우물을 직접 파는 비용일 경우.
            if (current.start == current.end) {
                // 해당 논에 물이 공급되지 않고 있는데
                // 현재 논에서 우물을 파는 것이 가장 싼 비용인 경우.
                // 직접 우물을 파고 물 공급 표시.
                if (!watered[pStart]) {
                    sum += current.cost;
                    watered[pStart] = true;
                }
            } else if (pStart != pEnd) {
                // 논끼리 연결하는 비용

                // 양쪽 다 이미 물이 공급되고 있을 경우
                // 굳이 두 논을 연결할 필요 없다.
                if (watered[pStart] && watered[pEnd])
                    continue;

                // 둘 중 하나의 논이라도 공급이 안되고 있다면
                // 두 논을 연결한다.
                union(pStart, pEnd);
                // 비용 추가.
                sum += current.cost;
                // 한 쪽의 논에 물이 공급되고 있다면
                // 두 논을 연결했으므로 해당 집합에도 물이 공급된다.
                if (watered[pStart] || watered[pEnd])
                    watered[findParent(pStart)] = true;
            }
        }
        System.out.println(sum);
    }

    // 두 개의 논을 하나의 집합으로 합침.
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

    // 해당 논이 속한 집합의 대표를 반환.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }

    // 초기화.
    static void init(int n) {
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];
    }
}