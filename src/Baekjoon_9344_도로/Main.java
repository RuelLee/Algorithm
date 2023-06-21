/*
 Author : Ruel
 Problem : Baekjoon 9344번 도로
 Problem address : https://www.acmicpc.net/problem/9344
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9344_도로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
    public static void main(String[] args) throws IOException {
        // n개의 도시와 건설할 수 있는 m개의 도로가 주어진다.
        // 최소한의 도로를 건설하여 모든 도시를 서로 이동가능하게끔 하고자한다.
        // 이 때 p <-> q 도시 간의 도로가 직접적으로 연결이되는지 확인하라
        //
        // 최소 스패닝 트리 문제
        // 프림 알고리즘을 통해 풀었다.
        // 프림 알고리즘을 통해 연결되어있는 도시에서 연결되어있지 않은 도시들 중 연결할 때
        // 최소 비용인 도시를 찾아 연결해 나가는 작업을 반복한다.
        // 그러면서 p <-> q 간의 도로를 연결하는지 확인한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 테스트 케이스
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // n개의 도시
            int n = Integer.parseInt(st.nextToken());
            // m개의 도로
            int m = Integer.parseInt(st.nextToken());
            // 직접적인 도로가 연결되는지 확인할 두 도시 p, q
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            
            // 도로 입력 처리
            List<List<Road>> roads = new ArrayList<>();
            for (int i = 0; i < n + 1; i++)
                roads.add(new ArrayList<>());
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());

                roads.get(u).add(new Road(u, v, w));
                roads.get(v).add(new Road(v, u, w));
            }
            
            // 연결 여부
            boolean[] connected = new boolean[n + 1];
            // 각 도시의 연결 최소 비용
            int[] minCosts = new int[n + 1];
            // 값 초기화
            Arrays.fill(minCosts, Integer.MAX_VALUE);
            minCosts[1] = 0;

            // 우선순위큐를 통해 연결 비용이 적은 도시부터 연결한다.
            PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.cost));
            // 1번 도시에서 다른 도시들을 연결하기 시작.
            priorityQueue.offer(new Road(1, 1, 0));
            // p <-> q 직접적 도로 연결 여부
            boolean answer = false;
            while (!priorityQueue.isEmpty()) {
                // 현재 도시
                Road current = priorityQueue.poll();
                // 이미 연결되어있다면 건너뛴다.
                if (connected[current.end])
                    continue;

                // p <-> q간의 도로를 연결한다면 답이 나온 경우이므로
                // answer에 true 값을 주고 더 이상의 도시 연결을 그만한다.
                if ((current.start == p && current.end == q) ||
                        (current.start == q && current.end == p)) {
                    answer = true;
                    break;
                }

                // current.end에서 연결할 수 있는 도시들을 살펴본다.
                for (Road next : roads.get(current.end)) {
                    // 아직 연결되지 않은 도시이고, current <-> next 간의 연결 비용이
                    // 다른 도시에서 next로 연결하는 비용보다 더 적다면
                    if (!connected[next.end] && minCosts[next.end] > next.cost) {
                        // 값 갱신
                        minCosts[next.end] = next.cost;
                        // 우선순위 큐 추가
                        priorityQueue.offer(new Road(current.end, next.end, next.cost));
                    }
                }
                // current에서 연결할 수 있는 다른 도시들을 모두 살펴보았다.
                // current는 연결된 도시로 표시.
                connected[current.end] = true;
            }
            // answer 값을 보며, 현재 테스트케이스에서의 답 기록
            sb.append(answer ? "YES" : "NO").append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}
