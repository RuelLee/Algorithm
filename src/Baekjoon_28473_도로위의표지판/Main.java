/*
 Author : Ruel
 Problem : Baekjoon 28473번 도로 위의 표지판
 Problem address : https://www.acmicpc.net/problem/28473
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28473_도로위의표지판;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 마을과 m개의 도로가 주어진다.
        // 1번 마을에서 출발하여 모든 마을을 방문하고자 한다.
        // 각 도로에는 표지판과 통행료가 있다. 표지판에는 1 ~ 9까지의 수가 적혀있고, 통행료는 최초 통행 시 1회만 지불하면, 그 후는 무료이다.
        // 중복되지 않은 도로를 지날 때마다, 표지판에 있는 수를 휴대폰에 적는다. 커서의 위치는 마음대로 조절할 수 있다.
        // 적은 수가 최소가 되면서, 그 중 통행료가 최소인 경우를 출력하라
        // 불가능하다면 -1을 출력한다.
        //
        // 최소 스패닝 트리 문제
        // 오랜만이다. Kruskal과 Prim 알고리즘이 있다.
        // Kruskal은 분리집합을 이용하여, 비용이 최소인 간선부터 살펴보는 것이고
        // Prim은 임의의 지점에서 연결 가능한 간선들 중 최소인 비용부터 살펴보는 것이다.
        // kruskal로 풀었고, 먼저 표지판의 수가 최소인 순서대로, 같다면 비용이 최소인 순서대로 정렬하여 처리했다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 분리집합을 위한 초기화
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        
        // 표지판 오름차순, 같다면, 비용 오름차순
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1[2] == o2[2])
                return Integer.compare(o1[3], o2[3]);
            return Integer.compare(o1[2], o2[2]);
        });
        // 도로를 모두 우선순위큐에 추가
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            priorityQueue.offer(new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
        }
        
        // 표지판의 수를 몇 번 지나쳤는지 계산
        int[] counts = new int[10];
        // 총 지나친 표지판의 수
        int countSum = 0;
        // 요금 합
        long sum = 0;
        // 모든 간선을 꺼내며
        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();
            // 이미 연결된 도시들이라면 건너뜀.
            if (findParent(current[0]) == findParent(current[1]))
                continue;

            // 그 외의 경우, 해당 도로를 선택하여 두 도시를 연결하고
            union(current[0], current[1]);
            // 표지판 개수 증가
            counts[current[2]]++;
            countSum++;
            // 요금 증가
            sum += current[3];
        }

        StringBuilder sb = new StringBuilder();
        // 지나친 표지판의 수가 n-1개라면
        // 모든 도시를 최소 간선으로 연결한 것.
        if (countSum == n - 1) {
            // 표지판의 수들로 만들 수 있는 최소 수는
            // 당연히 가장 작은 수가 앞으로 오는 것이다.
            // 순서대로 개수만큼 기록
            for (int i = 1; i < counts.length; i++) {
                for (int j = 0; j < counts[i]; j++)
                    sb.append(i);
            }
            // 요금도 기록
            sb.append(" ").append(sum);
        } else      // 불가능한 경우는 -1
            sb.append(-1);
        // 답 출력
        System.out.println(sb);
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