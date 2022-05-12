/*
 Author : Ruel
 Problem : Baekjoon 11405번 책 구매하기
 Problem address : https://www.acmicpc.net/problem/11405
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11405_책구매하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[][] capacities;
    static int[][] flows;
    static int[][] deliveryCosts;
    static final int MAX = 100 * 100 * 1000 + 1;

    public static void main(String[] args) throws IOException {
        // n명의 사람 m개의 서점이 주어진다
        // 각 사람이 사려는 책의 수가 주어진다
        // 각 서점이 갖고 있는 책의 수가 주어진다
        // 각 서점마다 각 사람에게 책을 팔 때 드는 배송비가 다르며, 이 값들이 주어진다
        // 전체 인원이 원하는 만큼의 책을 서점을 통해 샀을 때, 배송비의 총합을 최소로 만들고자 한다.
        // 이 최소 배송비의 합은?
        //
        // 최대 유량이라는 점은 느낌이 바로 오지만 최소 비용을 어떻게 해야하는가에 대한 느낌이 잘 오지 않는다
        // 일반적으로 최대 유량은 DFS(Ford Fulkerson)나 BFS(Edmond Karp) 방식으로 루트를 찾고 이를 flow에 반영한다
        // 하지만 비용이라는 문제에 들어간다면, 음의 방향에 대해 최소비용을 찾아야하기 때문에
        // 음의 방향도 찾을 수 있는 Bellman Ford 방법 혹은 이를 좀 더 최적화한 SPFA 알고리즘을 통해 흐름을 찾아 답을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 전체 노드들의 크기.
        int nodeSize = 1 + n + m + 1;
        // 소스는 0번.
        int source = 0;
        // sink는 1 + n + m번.
        int sink = 1 + n + m;

        capacities = new int[nodeSize][nodeSize];
        flows = new int[nodeSize][nodeSize];

        // 사람이 사려는 책의 수.
        // soruce에서 사람으로 보내는 용량으로 설정해준다.
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            capacities[source][i + 1] = Integer.parseInt(st.nextToken());
        // 서점이 갖고 있는 책의 수
        // 서점에서 sink로 보내는 용량으로 설정해준다.
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++)
            capacities[i + 1 + n][sink] = Integer.parseInt(st.nextToken());
        // 사람들은 각 서점에 대해 자신이 구매하는 수량 내에서 서점마다 분할해서 구매가 가능하다
        // 어차피 흘러들어오는 양에서 제한이 되었기 때문에, 각 서점에 구매하는 책의 수는 최대값으로 설정해주자.
        for (int i = 1; i < 1 + n; i++) {
            for (int j = 1 + n; j < sink; j++)
                capacities[i][j] = 101;
        }

        // 서점에서 각 사람에게 책을 배송할 때 드는 배송비.
        deliveryCosts = new int[nodeSize][nodeSize];
        for (int store = 0; store < m; store++) {
            st = new StringTokenizer(br.readLine());
            // 역방향으로 해당 사람의 책의 구매를 취소하고 다른 서점에서 책을 구매하는 경우도 생긴다
            // 따라서 역방향 값을 음수의 가격으로 설정해준다.
            for (int person = 0; person < n; person++)
                deliveryCosts[1 + n + store][person + 1] = -(deliveryCosts[person + 1][1 + n + store] = Integer.parseInt(st.nextToken()));
        }

        while (true) {
            // flow의 흐름을 기록해준다.
            int[] preLocation = new int[nodeSize];
            // 각 지점에 도달하는 최소 비용
            int[] minCosts = new int[nodeSize];
            // 초기값은 MAX
            Arrays.fill(minCosts, MAX);
            // sink는 0부터 시작하자.
            minCosts[source] = 0;
            // 이번에 흘러가는 유량을 노드별로 지나가며 생각해주자.
            int[] maxFlows = new int[nodeSize];
            // source에서는 최대값.
            maxFlows[source] = MAX;

            Queue<Integer> queue = new LinkedList<>();
            queue.offer(source);
            while (!queue.isEmpty()) {
                int current = queue.poll();

                // 현재 지점에서 다음 지점으로 흘려보내는 경우를 생각하자.
                for (int next = 1; next <= sink; next++) {
                    // current -> next로 흘려보내는 용량이 남아있거나 음의 유량이며
                    // next로 도달하는 최소 비용이 갱신되는 경우.
                    if (capacities[current][next] - flows[current][next] > 0 &&
                            minCosts[next] > minCosts[current] + deliveryCosts[current][next]) {
                        // 최소 비용 갱신.
                        minCosts[next] = minCosts[current] + deliveryCosts[current][next];
                        // 경로 기록.
                        preLocation[next] = current;
                        // 유량 기록.
                        maxFlows[next] = Math.min(maxFlows[current], capacities[current][next] - flows[current][next]);
                        // 큐에 삽입.
                        queue.remove(next);
                        queue.offer(next);
                    }
                }
            }
            // SPFA 알고리즘을 돌린 후에 sink로 도달한 유량이 0이라면
            // 더 이상 흘려보낼 유량이 없다. 종료.
            if (maxFlows[sink] == 0)
                break;

            // 그렇지 않다면
            // preLocation 기록을 따라 유량을 기록해준다.
            int loc = sink;
            while (loc != source) {
                flows[preLocation[loc]][loc] += maxFlows[sink];
                flows[loc][preLocation[loc]] -= maxFlows[sink];
                loc = preLocation[loc];
            }
        }

        // 최종적으로는 flows를 살펴보며, 사람들이 어느 서점에서 몇권씩 구매했는지 따져보고, 해당 가격을 곱해 총 배송비를 구한다.
        int sum = 0;
        for (int i = 1; i < 1 + n; i++) {
            for (int j = 1 + n; j < sink; j++)
                sum += flows[i][j] * deliveryCosts[i][j];
        }
        System.out.println(sum);
    }
}