/*
 Author : Ruel
 Problem : Jungol 1350번 최대신장트리
 Problem address : https://jungol.co.kr/problem/1350
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1350_최대신장트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정점, m개의 간선이 주어진다.
        // 모든 정점을 직간접적으로 n-1개의 간선으로 잇되, 가중치 합을 최대로 하고 싶다.
        // 이 때, 가중치의 합은?
        //
        // 최소 스패닝 트리
        // 문제 인데, 가중치의 값만 최소가 아닌 최대로 바꾸면 된다.
        // union find를 활용하는 kruskal,
        // 연결된 정점들로부터 모든 간선들 중 연결되지 않은 정점을 연결하는 최대 비용 간선을 선택해나가는 prim
        // 알고리즘 두 개를 사용할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, m개의 간선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 간선들
        List<List<int[]>> edges = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            edges.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges.get(a).add(new int[]{b, c});
            edges.get(b).add(new int[]{a, c});
        }

        // 연결된 간선들
        boolean[] connected = new boolean[n + 1];
        // 가중치의 합
        int sum = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o2[1], o1[1]));
        // 1번 정점에서부터 시작한다.
        for (int[] e : edges.get(1))
            pq.offer(e);
        connected[1] = true;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            // 이미 연결된 정점이라면 건너뜀.
            if (connected[cur[0]])
                continue;

            // 아니라면 연결 후
            connected[cur[0]] = true;
            sum += cur[1];
            // 해당 정점에서 연결 가능한 간선들을 추가
            for (int[] e : edges.get(cur[0])) {
                if (!connected[e[0]])
                    pq.offer(e);
            }
        }
        // 가중치의 합 출력
        System.out.println(sum);
    }
}