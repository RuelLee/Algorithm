/*
 Author : Ruel
 Problem : Baekjoon 27498번 연애 혁명
 Problem address : https://www.acmicpc.net/problem/27498
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27498_연애혁명;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // 사랑 관계 중 이미 성사된 관계는 포기하도록 하지 않는다.
        // k각 관계를 이루지 않도록 한다. Ai-1과 Ai 사이에 사랑 관계가 존재하고,
        // A마지막과 A1의 사랑 관계가 존재하는 것을 의미한다.
        // 포기하도록 만들 수 있는 경우가 여러 가지일 경우, 포기하도록 애정도의 합을 최소화한다.
        //
        // 최소 스패닝 트리 문제
        // 먼저, 이미 성사된 관계에 대해서는 같은 집합으로 묶는다.
        // 그 후, 사랑 관계들을 애정도 합에 대해 내림차순 정렬한 뒤,
        // 서로 다른 집합에 속한 관계라면 한 집합으로 묶고, 같은 집합이라면 묶어서는 안되므로
        // 이를 끊는 관계로 생각하여 애정도 합에 더한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사람, m개의 관계
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        ranks = new int[n + 1];
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;

        int[][] relationships = new int[m][];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(relationships[o2][2], relationships[o1][2]));
        for (int i = 0; i < relationships.length; i++) {
            relationships[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // 이미 성사된 관계는 한 집합으로 묶는다.
            if (relationships[i][3] == 1)
                union(relationships[i][0], relationships[i][1]);
            // 그렇지 않을 경우엔 최대힙 우선순위큐에 담아 정렬한다.
            else
                priorityQueue.offer(i);
        }

        int sum = 0;
        // 아직 연결되지 않은 관계들을 차례대로 살펴본다.
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();

            // 두 인원이 다른 집합이라면 한 집합으로 묶고
            if (findParent(relationships[current][0]) != findParent(relationships[current][1]))
                union(relationships[current][0], relationships[current][1]);
            // 같은 집합이라면 끊는 비용으로 더한다.
            else
                sum += relationships[current][2];
        }

        // 계산 값 출력
        System.out.println(sum);
    }

    // a와 b를 한 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pb] = pa;
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}