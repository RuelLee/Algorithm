/*
 Author : Ruel
 Problem : Baekjoon 11408번 열혈강호 5
 Problem address : https://www.acmicpc.net/problem/11408
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11408_열혈강호5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 직원과 m개의 작업이 있고, m개의 작업에 대해 각 직원의 비용이 주어진다
        // 직원은 각각 최대 한 개의 작업을 맡을 수 있다.
        // 최소 비용으로 최대한 많은 작업을 처리하려고 한다
        // 이 때의 작업의 수와 비용을 출력하라.
        //
        // 최소 비용 최대 유량 문제
        // SPFA 알고리즘을 통해 음의 유량을 고려하여 최소 비용을 통해 source에서 sink로 도달하는 방법을 찾는다.
        // 음의 유량을 고려하기 때문에, 이전 작업을 취소하고 새로운 작업을 반영하는 경우도 계산된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 전체 노드의 크기
        int nodeSize = n + m + 2;
        // 소스는 0번
        int source = 0;
        // sink는 마지막 번호.
        int sink = n + m + 1;

        // 용량.
        int[][] capacities = new int[nodeSize][nodeSize];
        // 각 일에 따른 직원의 소요 비용.
        int[][] workCosts = new int[nodeSize][nodeSize];
        // 각 직원이 맡을 수 있는 작업은 최대 1개
        for (int i = 1; i < n + 1; i++)
            capacities[source][i] = 1;
        // 각 작업마다 완료될 수 있는 경우는 1번.
        for (int i = n + 1; i < sink; i++)
            capacities[i][sink] = 1;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 각 직원이 할 수 있는 작업의 개수
            int workAble = Integer.parseInt(st.nextToken());
            for (int j = 0; j < workAble; j++) {
                // 작업의 번호.
                int work = Integer.parseInt(st.nextToken()) + n;
                // 비용
                int cost = Integer.parseInt(st.nextToken());
                
                // i + 1번 직원이 맡을 수 있는 작업 work의 개수는 1개
                capacities[i + 1][work] = 1;
                // 그 때의 비용
                workCosts[i + 1][work] = cost;
                // 역방향도 고려될 수 있어야한다.
                // 음의 비용.
                workCosts[work][i + 1] = -cost;
            }
        }

        // 실제의 흐름.
        int[][] flows = new int[nodeSize][nodeSize];
        int costSum = 0;
        while (true) {
            // 각 지점에 도달하는 최소 비용
            int[] minCosts = new int[nodeSize];
            // Integer.MAX_VALUE로 초기화
            Arrays.fill(minCosts, Integer.MAX_VALUE);
            // source에서 비용은 0.
            minCosts[0] = 0;
            // 이전 경로를 기록할 배열.
            int[] preLocations = new int[nodeSize];
            // 큐에 source를 넣어준다.
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(source);
            // 중복을 막기 위해 큐의 현재 상태를 저장하는 boolean 배열
            boolean[] enqueued = new boolean[nodeSize];
            enqueued[source] = true;
            while (!queue.isEmpty()) {
                // 현재 위치 current
                int current = queue.poll();
                // 큐에서 꺼냄 체크.
                enqueued[current] = false;

                for (int next = 1; next <= sink; next++) {
                    // 다음 지점이 음의 유량까지 고려하여, 용량의 여유가 있고,
                    // 최소 비용을 갱신하게 된다면
                    if (capacities[current][next] - flows[current][next] > 0 &&
                            minCosts[next] > minCosts[current] + workCosts[current][next]) {
                        // 최소 비용 갱신
                        minCosts[next] = minCosts[current] + workCosts[current][next];
                        // 이전 지점 기록
                        preLocations[next] = current;

                        // next가 큐에 들어있지 않다면
                        if (!enqueued[next]) {
                            // 큐에 넣음 체크.
                            enqueued[next] = true;
                            // 큐에 넣음
                            queue.offer(next);
                        }
                    }
                }
            }
            // 큐가 빌 때까지 연산을 했음에도, sink에 도달하는 경우가 없었다면
            // 더 이상 flow를 흘릴 수 없는 경우. 종료해준다.
            if (preLocations[sink] == 0)
                break;

            // sink로 도달하는 경우가 있다면
            // sink로 도달하는 최소비용을 costSum에 더해준다.
            costSum += minCosts[sink];
            // sink에서부터 시작하여
            int loc = sink;
            // source가 될 때까지
            while (loc != source) {
                // flow를 기록해준다.
                flows[preLocations[loc]][loc] += 1;
                // 음의 유량도 기록
                flows[loc][preLocations[loc]] -= 1;
                // 이전 지점으로 이동.
                loc = preLocations[loc];
            }
        }

        // 전체 맡은 일의 개수는 0번에서 직원으로 흘린 유량의 합과 같다.
        System.out.println(Arrays.stream(flows[0]).sum());
        // 이 때의 비용은 costSum
        System.out.println(costSum);
    }
}