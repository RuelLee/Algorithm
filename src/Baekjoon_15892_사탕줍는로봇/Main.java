/*
 Author : Ruel
 Problem : Baekjoon 15892번 사탕 줍는 로봇
 Problem address : https://www.acmicpc.net/problem/15892
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15892_사탕줍는로봇;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] capacities;
    static int[][] flows;
    static int source, sink;

    public static void main(String[] args) throws IOException {
        // n개의 방이 주어지고, 이를 연결하는 m개의 복도가 주어진다
        // 각 복도에는 몇 개의 사탕이 놓여있고, 로봇은 복도를 지나갈 때마다 하나의 사탕을 줍는다.
        // 사탕이 없는 복도는 지나갈 수 없다고 할 때, 1번 방에서 n번 방에 도달할 수 있는 로봇의 최대 갯수는 몇 개인가?
        //
        // 최대 유량 문제
        // 각 복도에 놓은 사탕을 용량
        // 로봇이 해당 복도를 지나가는 횟수를 흐름으로 보자.
        // 그리고 n번 방에 도달하는 최대 흐름을 구하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 시작은 1번방
        source = 1;
        // 끝은 n번방
        sink = n;
        capacities = new int[n + 1][n + 1];
        flows = new int[n + 1][n + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 복도에 방향은 없으므로, 역방향 또한 각각 더해주자.
            capacities[a][b] += c;
            capacities[b][a] += c;
        }

        int sum = 0;
        while (true) {
            // 1번 방부터 새로운 흐름을 흘려보내 n번 방에 도달하는 흐름을 구하자.
            int newFlow = fordFulkerson(source, Integer.MAX_VALUE, new boolean[n + 1]);
            // 새로운 흐름이 없다면 그만.
            if (newFlow == 0)
                break;
            // 있다면 sum에 새로운 흐름을 더해주고 다시 반복하자.
            sum += newFlow;
        }
        System.out.println(sum);
    }

    // DFS로 최대 유량을 찾는 알고리즘을 포드 풀커슨이라고 부른다.
    static int fordFulkerson(int loc, int flow, boolean[] visited) {
        // sink에 도달했다면 도달한 흐름 그대로 반환.
        if (loc == sink)
            return flow;

        // 방문 체크.
        visited[loc] = true;
        // 잔여 흐름.
        int remains = flow;
        for (int next = 0; next < capacities[loc].length; next++) {
            // next가 방문하지 않았고, 용량과 역방향 흐름을 고려했을 때 여유가 있다면
            if (!visited[next] && capacities[loc][next] - flows[loc][next] > 0) {
                // 메소드를 재귀적으로 호출해 새로운 흐름을 계산한다.
                int newFlow = fordFulkerson(next, Math.min(capacities[loc][next] - flows[loc][next], remains), visited);
                // 새로운 흐름이 있다면
                if (newFlow > 0) {
                    // 잔여 흐름에서 해당 흐름을 빼주고
                    remains -= newFlow;
                    // flows에 새로운 유량을 더해준다.
                    flows[loc][next] += newFlow;
                    // 역방향 유량도 추가해준다.
                    flows[next][loc] -= newFlow;
                }
            }
        }
        // 최종적으로 생긴 새로운 흐름을 반환한다.
        return flow - remains;
    }
}