/*
 Author : Ruel
 Problem : Baekjoon 1833번 고속철도 설계하기
 Problem address : https://www.acmicpc.net/problem/1833
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1833_고속철도설계하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 도시가 주어진다.
        // 도시 사이에 고속철도를 설치하려하는데 임의의 도시에서 다른 임의의 도시로 항상 이동할 수 있게 하려 한다.
        // 몇 개의 도시 사이에는 이미 고속철도가 설치되어있다.
        // 추가적으로 드는 건설 비용을 최소화하고자 할 때
        // 이미 설치된 비용을 포함한 모든 건설 비용과 새로 추가된 철도의 수
        // 그리고 새로 설치된 철도의 두 도시번호를 출력하라
        //
        // 최소 스패닝 트리
        // 이미 설치된 철도가 있다는 점을 유의하며
        // 최소 스패닝 트리를 만든다.
        // 분리 집합을 활용하는 Kruskal 알고리즘을 적용하였다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 인접 행렬
        int[][] adjMatrix = new int[n][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = Arrays.stream(br.readLine().trim().replaceAll("\\s+", " ").split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 분리 집합을 위한 초기 세팅
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        
        // 총 설치 비용
        int c = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> adjMatrix[o / n][o % n]));
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = i + 1; j < adjMatrix[i].length; j++) {
                // 0 이라면 건너뛰고
                if (adjMatrix[i][j] == 0)
                    continue;
                // 이미 설치된 철도라면
                // 비용을 미리 더해주고, 설치 비용을 0으로 한다.
                else if (adjMatrix[i][j] < 0) {
                    c -= adjMatrix[i][j];
                    adjMatrix[i][j] = 0;
                }
                // 그 후 우선순위큐에 담는다.
                priorityQueue.offer(i * n + j);
            }
        }
        
        // 새로 설치된 철도들을 담아 나중에 답안을 작성한다.
        Queue<int[]> newConstruct = new LinkedList<>();
        while (!priorityQueue.isEmpty()) {
            // 설치 비용이 최소인 두 도시를 우선순위큐에서 꺼낸다.
            int current = priorityQueue.poll();
            int start = current / n;
            int end = current % n;
            
            // 두 도시가 이미 철도로 연결되었다면 건너뛰고
            if (findParent(start) == findParent(end))
                continue;

            // 그렇지 않을 경우, 설치
            c += adjMatrix[start][end];
            // 새로 설치되는 철도라면 큐에 담아 답안을 작성할 때 활용한다.
            if (adjMatrix[start][end] > 0)
                newConstruct.offer(new int[]{start + 1, end + 1});
            // 두 도시를 하나의 집합을 묶는다.
            union(start, end);
        }

        StringBuilder sb = new StringBuilder();
        // 총 설치 비용과 새로 건설되는 철도의 수 
        sb.append(c).append(" ").append(newConstruct.size()).append("\n");
        // 새로 건설된 철도의 두 도시를 기록
        while (!newConstruct.isEmpty())
            sb.append(newConstruct.peek()[0]).append(" ").append(newConstruct.poll()[1]).append("\n");
        
        // 전체 답안 출력
        System.out.print(sb);
    }

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

    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}