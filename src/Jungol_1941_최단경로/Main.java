/*
 Author : Ruel
 Problem : Jungol 1941번 최단경로
 Problem address : https://jungol.co.kr/problem/1941
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1941_최단경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정점, m개의 단방향 간선이 주어진다.
        // 간선은 a b c 형태로 주어지며, 비용이 c인 a에서 b로 가는 경로가 존재한다는 뜻이다.
        // 1번 정점에서 n번 정점으로 가는 최소 비용은?
        //
        // dijkstra 문제
        // 간단한 다익스트라 문제
        // 충실히 구현해면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, m개의 간선
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<List<int[]>> roads = new ArrayList<>();
        for (int i = 0; i <= N; i++)
            roads.add(new ArrayList<>());

        // 간선 정보 입력
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            roads.get(a).add(new int[]{b, c});
        }

        // 1번 정점에서 각 정점에 도달하는 최소 비용
        int[] costs = new int[N + 1];
        Arrays.fill(costs, Integer.MAX_VALUE);
        // 1번 정점은 비용이 0
        costs[1] = 0;
        // 방문 체크
        boolean[] visited = new boolean[N + 1];
        // 우선순위큐로 현재 이동 가능한 정점들에서 다음 이동 가능한 정점들 중 최소 비용인 곳을 찾는다.
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        // 시작점
        pq.offer(new int[]{1, 0});
        while (!pq.isEmpty()) {
            // 현재 위치와 비용
            int[] current = pq.poll();
            // 이미 방문했다면 건너뜀
            if (visited[current[0]])
                continue;

            // current[0]에서 이동 가능한 미연결 정점들을 찾는다.
            for (int[] next : roads.get(current[0])) {
                // 최소 비용을 갱신하는 경우
                // 비용 갱신 후, 우선순위큐에 추가
                if (costs[next[0]] > current[1] + next[1]) {
                    costs[next[0]] = current[1] + next[1];
                    pq.offer(new int[]{next[0], costs[next[0]]});
                }
            }
            // 방문 체크
            visited[current[0]] = true;
        }
        // n번 정점에 이르는 최소 비용 출력
        System.out.println(costs[N]);
    }
}