/*
 Author : Ruel
 Problem : Baekjoon 11406번 책 구매하기 2
 Problem address : https://www.acmicpc.net/problem/11406
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11406_책구매하기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] capacities;
    static int[][] flows;
    static int source;
    static int sink;


    public static void main(String[] args) throws IOException {
        // n명 사람, m개의 서점이 주어진다
        // n명의 사람이 구하려는 책의 개수, m개의 서점이 갖고 있는 책의 수
        // 그리고 각 서점마다 각 사람에게 팔 수 있는 책의 개수가 주어진다.
        // 구매할 수 있는 책의 최대값은 몇 권이 구하라.
        //
        // 최대 유량 문제
        // 차분하게 조건들을 고려하며 용량과 흐름을 계산해주자!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        capacities = new int[n + m + 2][n + m + 2];
        flows = new int[n + m + 2][n + m + 2];

        source = 0;
        sink = n + m + 1;

        // 사람이 구매할 책의 수.
        // source -> 사람의 용량으로 지정해주자.
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            capacities[source][i + 1] = Integer.parseInt(st.nextToken());

        // 서점이 팔 수 있는 최대 책의 수.
        // 서점 -> sink의 용량으로 지정해주자.
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++)
            capacities[n + 1 + i][sink] = Integer.parseInt(st.nextToken());

        // 각 서점이 사람들에게 팔 수 있는 책의 수
        // 사람 -> 서점의 용량으로 지정해주자.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                capacities[j + 1][n + 1 + i] = Integer.parseInt(st.nextToken());
        }

        // Ford Fulkerson 알고리즘(DFS)로 흐름을 흘려보내며
        // 더 이상 추가 유량이 발생하지 않을 때까지 흘려보낸다.
        while (true) {
            if (fordFulkerson(0, Integer.MAX_VALUE, new boolean[n + m + 2]) == 0)
                break;
        }

        System.out.println(Arrays.stream(flows[0]).sum());
    }

    static int fordFulkerson(int n, int flow, boolean[] visited) {
        // sink에 도착했다면 해당 흐름을 리턴해주자.
        if (n == sink)
            return flow;

        visited[n] = true;      // 방문 체크
        int remainFlow = flow;      // 잔여 흐름.
        for (int i = 1; i < capacities[n].length; i++) {
            // 아직 용량의 여유가 있거나 음의 유량이 있어 역방향의 흐름이 고려될 수 있다면
            if (capacities[n][i] - flows[n][i] > 0 && !visited[i]) {
                // 재귀적으로 fordFulkerson 메소드를 불러 흐름을 흘려보낸다.
                // 이 때의 유량은 remainFlow와 capacities[n][i] - flows[n][i] 중 더 작은 값.
                // 그리고 새로운 흐름이 발생한다면
                int newFlow = fordFulkerson(i, Math.min(remainFlow, capacities[n][i] - flows[n][i]), visited);
                // n -> i로의 흐름에 추가하고
                flows[n][i] += newFlow;
                // i -> n로의 음의 유량도 추가한다.
                flows[i][n] -= newFlow;
                // 잔여 유량에서는 새로운 유량만큼을 빼준다.
                remainFlow -= newFlow;
            }
        }

        // 처음 유량에서 잔여 유량을 뺀 만큼이 새로운 유량이다.
        return flow - remainFlow;
    }
}