/*
 Author : Ruel
 Problem : Baekjoon 2367번 파티
 Problem address : https://www.acmicpc.net/problem/2367
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2367_파티;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] capacity;
    static int[][] flows;

    public static void main(String[] args) throws IOException {
        // n명이 모여 파티를 진행한다
        // 각 사람이 최대 k개의 음식을 준비하며, 음식의 종류는 d가지이다.
        // 각 사람은 한 종류의 음식은 하나만 준비할 수 있다.
        // 각 요리마다 가져올 수 있는 개수가 제한되어있으며, 각 인원마다 준비할 수 있는 요리의 종류도 주어진다.
        // 파티에 준비되는 최대한 많은 음식의 개수는?
        //
        // 최대 유량 문제
        // 각 인원마다 k만큼의 flow를 흘려보내, 요리에 도달하도록 하며, 각 요리에서 sink로 흘러갈 수 있는 flow는 요리의 제한만큼으로 설정한다
        // 그리고 sink로 흘러가는 최대 유량을 구하면 된다!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        // 0번은 source, n + d + 1은 sink
        capacity = new int[n + d + 2][n + d + 2];
        flows = new int[n + d + 2][n + d + 2];
        // 각 인원이 준비할 수 있는 음식의 개수는 최대 k개
        for (int i = 1; i < n + 1; i++)
            capacity[0][i] = k;
        // 각 요리마다 준비될 수 있는 최대 개수 제한.
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < d + 1; i++)
            capacity[n + i][n + d + 1] = Integer.parseInt(st.nextToken());

        // 각 사람과 만들 수 있는 음식을 연결해준다.
        // 한 사람은 한 종류의 음식을 하나만 만들 수 있으므로 이 때 값은 1
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int canMake = Integer.parseInt(st.nextToken());
            for (int j = 0; j < canMake; j++)
                capacity[i + 1][n + Integer.parseInt(st.nextToken())] = 1;
        }

        // source로 부터 더 이상 새로운 flow가 발생하지 않을 때까지 계속 흘려보낸다.
        int sum = 0;
        while (true) {
            int newFlow = fordFulkerson(0, Integer.MAX_VALUE, new boolean[n + d + 2]);
            if (newFlow == 0)
                break;
            sum += newFlow;
        }
        // 이 때까지의 새 flow 들의 합이 답.
        System.out.println(sum);
    }

    // Ford Fulkerson 알고리즘. DFS를 통해 새로운 flow를 설정해준다.
    static int fordFulkerson(int n, int flow, boolean[] visited) {
        if (n == capacity.length - 1)       // sink에 도달했다면
            return flow;        // flow 자체를 리턴.
        visited[n] = true;      // 방문 체크

        // 원래 흘러온 유량을 기록해두고,
        int originFlow = flow;
        for (int i = 0; i < capacity[n].length; i++) {
            // n과 연결된 곳들 중 아직 방문하지 않았고, 흘려보낼 유량의 여유가 있을 경우.
            if (!visited[i] && capacity[n][i] - flows[n][i] > 0) {
                // 함수를 다시 재귀적으로 호출하여 flow를 흘려보낸다
                // 이 때의 흘려보낼 수 있는 유량은 다른 곳에 흘려보내고 남은 flow와 여유량 중 작은 값이다.
                int newFlow = fordFulkerson(i, Math.min(flow, capacity[n][i] - flows[n][i]), visited);
                // 이렇게 흘려보낼 수 있는 newFlow를 현재 flow에서 제해주고,
                flow -= newFlow;
                // n -> i 유량을 표시해준다.
                flows[n][i] += newFlow;
                // i -> n의 음의 유량도 표시.
                flows[i][n] -= newFlow;
            }
        }
        // 새롭게 흘러간 유량은 원래 originFlow에서 남은 flow만큼을 뺀 값이다.
        return originFlow - flow;
    }
}