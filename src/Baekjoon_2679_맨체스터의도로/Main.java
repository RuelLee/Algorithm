/*
 Author : Ruel
 Problem : Baekjoon 2679번 맨체스터의 도로
 Problem address : https://www.acmicpc.net/problem/2679
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2679_맨체스터의도로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int b;
    static int[][] capacities, flows;

    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 각 테스트케이스마다 n개의 정점, e개의 도로, 시작점 a, 목적지 b가 주어진다.
        // 도로는 단방향이며 시작 정점 u, 도착 정점 v, 1시간 동안의 도로 제한 w가 주어진다.
        // a에서 b로 이동하는데, 전체 이동 가능한 차의 개수와 단일 경로의 최대 이동 가능 차 개수의 비율을 출력하라
        //
        // 최대 유량, bfs 문제
        // 최대 유량을 구하며, 단일 경로의 최대 흐름과, 누적 흐름의 비율을 출력하면 되는 문제
        // 단일 경로 최대 이동 대수는 bfs를 통해 이동할 수 있는 차의 최대 개수를 구하면 된다.

        // 최대 유량을 위한 초기화
        // t개의 테스트케이스가 주어지므로, 최대 크기로 선언해두고, 초기화하며 재사용하자
        //
        capacities = new int[1000][1000];
        flows = new int[1000][1000];
        boolean[] visited = new boolean[1000];
        int[] maxFlows = new int[1000];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // n개의 정점, e개의 도로, 출발지 a, 도착지 b
            int n = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            // capacities와 flows 초기화
            for (int i = 0; i < n; i++) {
                Arrays.fill(capacities[i], 0, n, 0);
                Arrays.fill(flows[i], 0, n, 0);
            }

            // 단방향 도로 입력
            // 도로의 용량과 관계
            for (int i = 0; i < e; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                capacities[u][v] = w;
            }

            // 전체 흐름
            int flowSum = 0;
            while (true) {
                // boolean 배열 초기화
                Arrays.fill(visited, 0, n, false);
                // 이번 경로에서 추가로 발생한 새 흐름
                int newFlow = findFlow(a, Integer.MAX_VALUE, visited);
                // 이 없다면 반복문 종료
                if (newFlow == 0)
                    break;
                // 있다면, 전체 흐름에 누적 및 단일 경로 최대 흐름 값을 갱신하는지 확인
                flowSum += newFlow;
            }
            
            // BFS, 단일 경로 최대 이동 가능 차의 개수를 구한다.
            Arrays.fill(maxFlows, 0, n, 0);
            // 처음 위치 큰 값
            maxFlows[a] = Integer.MAX_VALUE;
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{a, Integer.MAX_VALUE});
            while (!queue.isEmpty()) {
                // 현재 위치
                int[] current = queue.poll();
                // 도착지에 도달했거나, current[0]에 기록된 값이 current[1]보다 크거나
                // 현재까지 찾은 단일 경로 최대 이동 가능 차의 개수가 current[1]보다 큰 경우는 건너뜀.
                if (current[0] == b || maxFlows[current[0]] > current[1] ||
                        maxFlows[b] > current[1])
                    continue;

                // current[0] -> i로 최대한 많은 차가 이동하는 경우를 따짐.
                for (int i = 0; i < n; i++) {
                    int toNext = Math.min(current[1], capacities[current[0]][i]);
                    // 그것이 i에 이동 가능한 최대 차의 개수를 갱신한다면 값 갱신 후, 큐에 추가
                    if (toNext > maxFlows[i]) {
                        maxFlows[i] = toNext;
                        queue.offer(new int[]{i, toNext});
                    }
                }
            }
            // 최종적으로 얻은, 전체 통행량을 고려한 a -> 최대 이동 가능 차의 개수와
            // 단일 경로에서 이동 가능한 최대 차의 개수를 나눈 값을 기록
            sb.append(String.format("%.3f", (double) flowSum / maxFlows[b])).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 새로운 흐름을 찾는다.
    static int findFlow(int idx, int flow, boolean[] visited) {
        // 도착지에 도달한 흐름만큼 반환
        if (idx == b)
            return flow;

        // 방문 체크
        visited[idx] = true;
        int flowSum = 0;
        // idx에서 next로 새로운 흐름이 만들어지는지 확인
        for (int next = 0; next < capacities.length && flow > 0; next++) {
            if (!visited[next] && capacities[idx][next] - flows[idx][next] > 0) {
                // 만들어진 새 흐름
                int newFlow = findFlow(next, Math.min(flow, capacities[idx][next] - flows[idx][next]), visited);
                // 만큼 flows에 기록
                flows[idx][next] += newFlow;
                flows[next][idx] -= newFlow;
                // idx에서 나가는 흐름에 누적
                flowSum += newFlow;
                // idx에 들어온 흐름에서 차감
                flow -= newFlow;
            }
        }
        // 만들어진 흐름의 총량을 반환
        return flowSum;
    }
}