/*
 Author : Ruel
 Problem : Baekjoon 4650번 Jungle Roads
 Problem address : https://www.acmicpc.net/problem/4650
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4650_JungleRoads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Road {
    int start;
    int end;
    int cost;

    public Road(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 마을과 각 마을에서 이어진 도로와 유지 비용이 주어진다.
        // 모든 마을들을 연결하되, 도로 유지 비용을 최소화하고자 한다.
        // 그 때의 유지 비용을 출력하라
        //
        // 최소 스패닝 트리 문제
        // 각 도시들과 도로들로 최소로 연결되는 비용을 구하고자함이므로
        // 크루스칼이나 프림 알고리즘을 사용하는 최소 신장 트리 문제이다.
        // 크루스칼 알고리즘을 통해 풀고자 한다면
        // 도로들을 유지 비용에 대해 오름차순 정렬하고
        // 각 연결되는 두 도시가 같은 집합 내에 있다면 도로가 필요없고
        // 서로 다른 집합에 있다면 도로를 연결하여 같은 집합으로 묶는다.
        // 위 과정을 모든 도시들이 같은 집합에 속하게 될 때까지 진행한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String input = br.readLine();
        while (!input.equals("0")) {
            // n개의 도시
            int n = Integer.parseInt(input);
            // 분리 집합을 위한 parents와 ranks 초기화
            parents = new int[n];
            for (int i = 1; i < parents.length; i++)
                parents[i] = i;
            ranks = new int[n];

            // 도로들을 유지 비용에 대한 오름차순으로 살펴보기 위해
            // 최소 힙 우선순위큐를 사용한다.
            PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
            for (int i = 0; i < n - 1; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int start = st.nextToken().charAt(0) - 'A';
                int num = Integer.parseInt(st.nextToken());
                for (int j = 0; j < num; j++) {
                    int end = st.nextToken().charAt(0) - 'A';
                    int cost = Integer.parseInt(st.nextToken());
                    priorityQueue.offer(new Road(start, end, cost));
                }
            }

            int sum = 0;
            // 도로들에 대해 유지 비용이 적은 순으로 살펴보며
            while (!priorityQueue.isEmpty()) {
                Road current = priorityQueue.poll();
                if (findParent(current.start) == findParent(current.end))
                    continue;

                // 두 도시가 서로 다른 집합일 때만 
                // 두 도시를 한 집합으로 묶고, 해당 도로를 선택한다.
                union(current.start, current.end);
                sum += current.cost;
            }
            // 구해진 총 도로 유지 비용 기록
            sb.append(sum).append("\n");
            input = br.readLine();
        }
        // 전체 답안 출력.
        System.out.print(sb);
    }

    // 두 집합을 하나의 집합으로 묶는다.
    static void union(int a, int b) {
        // 각 도시가 속한 대표를 구해
        int pa = findParent(a);
        int pb = findParent(b);

        // 두 대표의 ranks를 비교하여
        // 더 작은 쪽이 더 큰 쪽에 속하도록 한다.
        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}