/*
 Author : Ruel
 Problem : Jungol 2109번 꿀꿀이 축제
 Problem address : https://jungol.co.kr/problem/2109
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2109_꿀꿀이축제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 마을에 꿀꿀이들이 한마리씩 살고 있다.
        // 단방향 도로를 통해 x마을에 모였다가 각자의 마을로 돌아가고자 한다.
        // 이동 시간이 가장 오래 걸리는 경우를 구하라
        //
        // 최단 거리, dijkstra 문제
        // 도로를 순방향으로 x에서 각각의 마을로 가는 거리들을 구한다.
        // 도로를 역방향으로 바꿔 다시 계산하면, 이는 각 마을에서 x로 오는 거리가 된다.
        // 두 경우를 합쳐 최대 소요 시간을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 마을, m개의 도로, 모이는 마을 x
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        // x마을로 가는 경우. 도로를 역방향으로 넣는다.
        List<List<int[]>> toX = new ArrayList<>();
        // x마을에서 출발하는 경우. 정방향으로 넣는다.
        List<List<int[]>> fromX = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            toX.add(new ArrayList<>());
            fromX.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            fromX.get(s).add(new int[]{e, t});
            toX.get(e).add(new int[]{s, t});
        }

        // x마을까지 가는 이동 시간
        int[] distancesToX = dijkstra(n, x, toX);
        // x마을에서 되돌아가는데 걸리는 이동 시간
        int[] distancesFromX = dijkstra(n, x, fromX);

        // 답
        int ans = 0;
        for (int i = 1; i <= n; i++)
            ans = Math.max(ans, distancesToX[i] + distancesFromX[i]);
        System.out.println(ans);
    }

    // 다익스트라
    // n개의 마을, x에서 출발하는 경우. 도로들은 roads
    static int[] dijkstra(int n, int x, List<List<int[]>> roads) {
        // 각 마을에 이르는 시간
        int[] distances = new int[n + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        // 시작 지점
        distances[x] = 0;
        // 우선순위큐로 x에서부터 시작
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> distances[o]));
        priorityQueue.offer(x);
        // 방문 체크
        boolean[] visited = new boolean[n + 1];
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            if (visited[current])
                continue;

            for (int[] next : roads.get(current)) {
                if (!visited[next[0]] && distances[next[0]] > distances[current] + next[1]) {
                    distances[next[0]] = distances[current] + next[1];
                    priorityQueue.offer(next[0]);
                }
            }
        }
        // 계산된 거리 반환
        return distances;
    }
}