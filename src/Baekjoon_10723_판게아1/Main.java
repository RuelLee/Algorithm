/*
 Author : Ruel
 Problem : Baekjoon 10723번 판게아 1
 Problem address : https://www.acmicpc.net/problem/10723
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10723_판게아1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge {
    int a;
    int b;
    int c;

    public Edge(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 도시가 주어지고 이들을 잇는 도로 n-1개가 주어진다.
        // m개의 턴마다 새로운 도로가 주어지고
        // 모든 도시가 서로 직간접적으로 연결되어있도록 하며, 도로의 길이 합을 최소로 하고자 한다.
        // 그 때의 도로 길이 합을 xor한 값을 출력하라
        //
        // 최소 스패닝 트리 문제
        // 처음 주어지는 형태를 제외하곤 최소 스패닝 트리 형태를 유지하며 도로 길이 합을 구하라는 문제이다.
        // kruskal 알고리즘을 통해 풀었다.
        // 길이가 최소인 도로를 오름차순으로 살펴보고, 두 도시가 직간접적으로 연결되어있는지를 체크
        // 연결되어있지 않다면 해당 도로를 건설하고, 연결되어있다면 건너뛴다.
        // 그런 식으로 최소 스패닝 트리를 만드는 작업을 계속 반복한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // n개의 도시, m개의 턴
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            // union find를 위한 parents와 ranks
            parents = new int[n + 1];
            ranks = new int[n + 1];
            reset();

            // 최초의 도로들
            PriorityQueue<Edge> next = new PriorityQueue<>(Comparator.comparingInt(o -> o.c));
            for (int i = 1; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                next.offer(new Edge(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
            }

            PriorityQueue<Edge> priorityQueue;
            // 도로의 길이 합을 xor하여 출력할 answer
            long answer = 0;
            for (int i = 0; i < m; i++) {
                // 이번에 뽑아 낼 우선순위큐
                priorityQueue = next;
                // 선택한 도로들을 저장하여 다음에 사용할 next 우선선위큐
                next = new PriorityQueue<>(Comparator.comparingInt(o -> o.c));
                // parent, ranks 초기화
                reset();
                
                // 도로 길이 합
                long sum = 0;
                // 추가되는 도로
                st = new StringTokenizer(br.readLine());
                priorityQueue.offer(new Edge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
                // 최소 스패닝 트리
                while (!priorityQueue.isEmpty()) {
                    Edge current = priorityQueue.poll();
                    // 이미 직간접적으로 연결된 도시라면 건너 뜀.
                    if (findParent(current.a) == findParent(current.b))
                        continue;
                    
                    // 아니라면 두 도시 사이에 도로 건설
                    union(current.a, current.b);
                    // 도로 합 누적
                    sum += current.c;
                    // next에 current 도로 추가
                    next.offer(current);
                }
                // 최종적으로 얻은 도로 합에
                // answer를 xor 연산
                answer ^= sum;
            }
            // m턴 경과
            // 이번 테스트케이스 얻은 답 기록
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // a와 b 도시를 같은 집합으로 묶는다(=직간접적으로 연결되어있다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        // 연산 감소를 위한 rank 처리
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
    
    // parents, ranks 초기화
    static void reset() {
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
        Arrays.fill(ranks, 0);
    }
}