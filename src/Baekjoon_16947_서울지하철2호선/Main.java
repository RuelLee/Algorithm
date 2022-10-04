/*
 Author : Ruel
 Problem : Baekjoon 16947번 서울 지하철 2호선
 Problem address : https://www.acmicpc.net/problem/16947
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16947_서울지하철2호선;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connections;
    static int[] distances;

    public static void main(String[] args) throws IOException {
        // 서울 지하철 2호선은 순환선과 순환선의 역과 연결된 지선이 있다.
        // 각 역이 순환선으로부터 떨어진 거리를 출력한다.
        //
        // 그래프 탐색 문제
        // 먼저 노드들을 방문하며 사이클이 생기는지 여부를 체크해야한다.
        // 사이클이 생긴 노드들은 순환선이며, 순환선을 구하는 것이 끝났다면
        // 그 이후로 순환선으로부터 떨어진 거리들을 탐색했다.
        // 사이클을 찾는데 스택을 이용하여 SCC와 유사한 방법으로 풀어보았다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 역의 개수
        int n = Integer.parseInt(br.readLine());
        connections = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());

        // 역들 간의 연결 정보
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            connections.get(b).add(a);
        }

        // 순환선으로부터의 거리
        distances = new int[n + 1];
        // 초기값으로는 큰 값
        Arrays.fill(distances, Integer.MAX_VALUE);

        // 1번 노드부터 방문한다.
        dfs(1, 0, new boolean[n + 1], new Stack<>());

        // 순환선인 역들을 큐에 담는다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < distances.length; i++) {
            if (distances[i] == 0)
                queue.offer(i);
        }
        // 역들을 꺼내가며, 순환선으로부터 거리가 갱신되는 다음 역만 큐에 다시 집어넣으며
        // 순환선으로부터의 거리를 계산한다.
        while (!queue.isEmpty()) {
            // 현재 역
            int current = queue.poll();

            // current와 연결된 역들을 살펴본다.
            for (int next : connections.get(current)) {
                // 다음 역이 순환선으로부터의 거리가 갱신된다면
                if (distances[next] > distances[current] + 1) {
                    // 거리 갱신 및 큐 추가.
                    distances[next] = distances[current] + 1;
                    queue.offer(next);
                }
            }
        }

        // 모든 역의 순환선으로부터의 거리를 출력한다.
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < distances.length; i++)
            sb.append(distances[i]).append(" ");
        System.out.println(sb);
    }

    // dfs로 순환하는 사이클을 찾는다.
    static void dfs(int n, int pre, boolean[] visited, Stack<Integer> stack) {
        // 이전에 방문한 적이 있는 역이라면
        if (visited[n]) {
            // 순환하는 사이클을 찾은 것이다.
            // 스택에 담겨진 역들 중에서 n이 나올 때까지 거리값을 0으로 만들어준다.
            while (!stack.isEmpty() && stack.peek() != n)
                distances[stack.pop()] = 0;
            // n역도 사이클에 포함되므로 거리는 0
            distances[stack.peek()] = 0;
            // 종료.
            return;
        }
        
        // 방문한 적이 없는 역이라면 방문 체크
        visited[n] = true;
        // 스택에 현재 역 추가.
        stack.push(n);
        
        // n에서 갈 수 있는 다음 역들
        for (int next : connections.get(n)) {
            // 직전에 출발해온 역이 아니고, 이미 순환선으로 판명된 역도 아니라면
            // dfs를 재귀적으로 부른다.
            if (next != pre && distances[next] != 0)
                dfs(next, n, visited, stack);
        }
        // 다음 역을 방문이 끝나고 돌아왔는데, stack 최상단에 n이 있다면 순환선이 아니거나
        // 순환선 중 가장 먼저 방문한 역이다.
        // stack에서 제거해준다.
        if (stack.peek() == n)
            stack.pop();
    }
}