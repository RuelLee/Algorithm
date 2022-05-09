/*
 Author : Ruel
 Problem : Baekjoon 6497번 전력난
 Problem address : https://www.acmicpc.net/problem/6497
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6497_전력난;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Route {
    int end;
    int length;

    public Route(int end, int length) {
        this.end = end;
        this.length = length;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // m개의 집들이 n개의 길로 서로 연결이 되어있다
        // 이중 몇 개의 가로등을 꺼, 전력 소모를 줄이고 싶다
        // 사람들은 가로등이 켜진 길로만 움직일 수 있으므로, 모든 집들에 대해 가로등이 켜진 길로 이동할 수 있어야한다.
        // 조건을 만족하도록 가로등을 끄는 경우, 가로등이 꺼진 길의 길이 합은?
        //
        // 모든 집들 간에 서로 이동할 수 있는 경로가 있으면서 최소한의 연결로만 유지 하고 싶다 -> 최소 스패닝 트리
        // 최소 스패닝 트리를 만드는 알고리즘으로는 유명한 Kruskal 알고리즘과 Prim 알고리즘이 있다
        // kruskal은 분리집합을 이용한 알고리즘이고,
        // Prim은 연결된 집들에서 선택할 수 있는 연결되지 않은 집들로 향하는 도로들 중 가장 비용이 적은 것을 계속 선택해나가는 알고리즘이다.
        // 프림 알고리즘으로 풀어보았다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (true) {
            int m = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            if (m == 0 && n == 0)
                break;

            List<List<Route>> routes = new ArrayList<>();
            for (int i = 0; i < m; i++)
                routes.add(new ArrayList<>());
            // 차이를 구하기 위해 전체 도로의 길이를 구한다.
            int lengthSum = 0;
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int z = Integer.parseInt(st.nextToken());

                routes.get(x).add(new Route(y, z));
                routes.get(y).add(new Route(x, z));
                lengthSum += z;
            }

            // 각 집들을 연결시키는데 드는 최소 비용을 저장한다
            int[] costs = new int[m];
            Arrays.fill(costs, Integer.MAX_VALUE);
            // 0번 집은 연결되어있다고 보고, 0비용을 준다.
            costs[0] = 0;
            boolean[] connected = new boolean[m];

            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> costs[o]));
            priorityQueue.offer(0);
            while (!priorityQueue.isEmpty()) {
                int current = priorityQueue.poll();
                // 이미 더 적은 비용으로 계산한 적이 있다면 건너 뛴다.
                if (connected[current])
                    continue;

                // current는 연결되어있는 집이다.
                // 해당 집에서 갈 수 있는 도로들 중
                for (Route r : routes.get(current)) {
                    // 아직 연결되지 않은 집이고, 연결 비용이 최소로 갱신되는 경우에
                    if (!connected[r.end] && costs[r.end] > r.length) {
                        // 해당 연결 비용을 기록하고
                        costs[r.end] = r.length;
                        // 우선순위큐에 넣어 다음 번에 체크하도록 한다.
                        priorityQueue.offer(r.end);
                    }
                }
                // current 집에서 연결할 수 있는 도로들을 계산했으므로 방문 표시.
                connected[current] = true;
            }

            // 다른 집과 연결하는 최소 비용들이 costs에 기록되어있다
            // 전체 합을 구해주자. 이는 최소 신장 트리의 길이 합이다.
            int mstLength = Arrays.stream(costs).sum();
            // 이를 전체 도로 길이 합에서 빼면 절약할 수 있는 도로의 최대 길이가 된다.
            sb.append(lengthSum - mstLength).append("\n");
            st = new StringTokenizer(br.readLine());
        }
        System.out.print(sb);
    }
}