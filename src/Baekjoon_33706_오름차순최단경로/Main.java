/*
 Author : Ruel
 Problem : Baekjoon 33706번 오름차순 최단 경로
 Problem address : https://www.acmicpc.net/problem/33706
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_33706_오름차순최단경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정점, m개의 간선으로 이루어진 무방향 그래프가 주어진다.
        // 간선의 비용이 아직 정해지지 않았는데
        // 다음과 같은 조건을 만족해야한다.
        // 간선의 비용은 양의 정수이다.
        // 모든 (i, j)(i < j)쌍에 대해 1 -> i의 비용이, 1 -> j의 비용보다 작아야한다.
        // 그렇게 간선들의 비용을 정할 수 있다면 YES, 불가능하다면 NO를 출력한다.
        //
        // BFS 문제
        // 간단한 그래프 탐색 문제
        // 모든 (i, j)쌍에 대해 (1 -> i) < (1 -> j)를 만족해야한다.
        // 1부터 BFS 탐색을 하되, 값이 높은 정점에서 값이 낮은 정점으로 이동하는 간선은 사용하지 않는다고 보는게 좋다.
        // 만약 역방향으로 진행하는 간선으로 최단거리를 계산할 경우, 조건을 만족하지 않게 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, m개의 간선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            edges.add(new ArrayList<>());

        // 간선의 입력 자체를
        // 항상 작은 쪽에서 큰 쪽으로 가는 경우만 입력 받는다.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges.get(Math.min(a, b)).add(Math.max(a, b));
        }
        
        // 방문 체크
        boolean[] visited = new boolean[n + 1];
        visited[1] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        // 방문 정점의 개수
        int cnt = 1;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 다음 미방문 정점을 방문한다.
            for (int next : edges.get(current)) {
                if (!visited[next]) {
                    visited[next] = true;
                    cnt++;
                    queue.offer(next);
                }
            }
        }
        // 모든 정점을 방문했다면 YES, 그렇지 않은 경우 NO 출력
        System.out.println(cnt == n ? "YES" : "NO");
    }
}