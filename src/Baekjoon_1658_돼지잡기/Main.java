/*
 Author : Ruel
 Problem : Baekjoon 1658번 돼지 잡기
 Problem address : https://www.acmicpc.net/problem/1658
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1658_돼지잡기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[][] capacities;
    static int[][] flows;
    static int source = 0;
    static int sink;

    public static void main(String[] args) throws IOException {
        // m개의 돼지 우리, n명의 손님이 있다.
        // 각 손님은 m개의 돼지 우리 중 몇 개의 우리에 키를 갖고 있다.
        // 손님은 자신이 키를 갖고 있는 돼지 우리들을 열어, 자신이 원하는 만큼의 돼지를 구매하고 다시 문을 잠근다.
        // 이 때, 열린 우리들 사이의 돼지의 수를 재분배할 수 있다.
        // 가장 많은 돼지들을 파는 마릿수는 얼마인가?
        //
        // 최대 유량 문제
        // 인데 돼지들을 열린 우리들 사이에 재분배할 수 있다는 점이 생각해볼 포인트다.
        // 다시 말해 자신과 같은 키를 갖고 있는 사람에 다른 우리에서 해당 우리로 돼지들을 옮겨달라고 부탁하는 것이 가능하다.
        // 혹은 같은 키를 갖고 있는 사람에게 대리 구매를 요청할 수 있다고 볼 수도 있다.
        // 따라서 키에 따라 키의 소유자들을 해두고, 해당 소유자에게도 간선을 이어 대리 구매를 한다고 생각하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 우리의 수
        int m = Integer.parseInt(st.nextToken());
        // 손님의 수
        int n = Integer.parseInt(st.nextToken());
        sink = n + m + 1;

        // 최대 유량을 위한 용량과 흐름 행렬
        capacities = new int[n + m + 2][n + m + 2];
        flows = new int[n + m + 2][n + m + 2];

        // 각 우리에 있는 돼지의 마릿수.
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++)
            capacities[n + (i + 1)][sink] = Integer.parseInt(st.nextToken());

        // 키 소지자들
        List<List<Integer>> keyHolders = new ArrayList<>(n + m + 2);
        for (int i = 0; i < n + m + 2; i++)
            keyHolders.add(new ArrayList<>());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // i번째 손님은 a개의 키를 갖고 있고
            int a = Integer.parseInt(st.nextToken());
            for (int j = 0; j < a; j++) {
                // 갖고 있는 키.
                int key = Integer.parseInt(st.nextToken()) + n;
                // i + 1 손님과 key 우리와 간선을 연결해준다.
                capacities[i + 1][key] = Integer.MAX_VALUE;
                // 이전의 손님들 중 key를 갖고 있었던 손님들에게 대리구매를 요청할 수 있다.
                // 이전 손님들에게도 간선을 연결해준다.
                for (int keyHolder : keyHolders.get(key))
                    capacities[i + 1][keyHolder] = Integer.MAX_VALUE;
                // 그리고 자신도 keyHolder에 등록한다.
                keyHolders.get(key).add(i + 1);
            }
            // 자신이 총 구매할 돼지의 마릿수.
            capacities[source][i + 1] = Integer.parseInt(st.nextToken());
        }

        int sum = 0;
        // 더 이상 새로운 흐름이 발생하지 않을 때까지 반복한다.
        while (true) {
            int newFlow = waterFlow(source, Integer.MAX_VALUE, new boolean[n + m + 2]);
            // 새로운 흐름(= 새로운 돼지 구매)이 발생하지 않는다면 종료.
            if (newFlow == 0)
                break;
            // 발생한다면 sum에 누적시켜준다.
            sum += newFlow;
        }

        // 최대 팔 수 있는 돼지의 마릿수.
        System.out.println(sum);
    }

    // 최대 유량.
    static int waterFlow(int edge, int flow, boolean[] visited) {
        // sink에 도달했다면 해당 flow만큼이 구매 가능한 돼지의 마릿수.
        // 해당 수를 반환한다.
        if (edge == sink)
            return flow;

        // 방문 체크.
        visited[edge] = true;
        // 들어온 흐름은 flow
        // 나간 흐름들을 제외한 잔여 흐름들을 기록해둔다.
        int remainFlow = flow;
        for (int i = 0; i < capacities[edge].length; i++) {
            // 미방문이고, 잔여 흐름이 있으며, 용량과 음의 흐름을 고려했을 때, 흐름을 발생시킬 수 있다면
            if (!visited[i] && remainFlow > 0 && capacities[edge][i] - flows[edge][i] > 0) {
                // edge에서 i로 새로운 흐름을 보낸다.
                // 이 때의 흐름의 최대량은 raminFlow와 용량과 음의 흐름을 고려한 값 중 더 작은 값.
                int newFlow = waterFlow(i, Math.min(remainFlow, capacities[edge][i] - flows[edge][i]), visited);
                // 발생한 새로운 흐름 만큼을 잔여 흐름에서 빼주고
                remainFlow -= newFlow;
                // edge -> i의 흐름과
                flows[edge][i] += newFlow;
                // i -> edge로의 음의 흐름에 기록해준다.
                flows[i][edge] -= newFlow;
            }
        }
        // 최종적으로 edge에서 다른 곳으로 보낸 흐름은
        // 처음 들어왔던 flow에서 남은 흐름 remainFlow를 빼준 값.
        // 해당 값을 반환한다.
        return flow - remainFlow;
    }
}