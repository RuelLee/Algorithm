/*
 Author : Ruel
 Problem : Baekjoon 13016번 내 왼손에는 흑염룡이 잠들어 있다
 Problem address : https://www.acmicpc.net/problem/13016
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13016_내왼손에는흑염룡이잠들어있다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<int[]>> roads;

    public static void main(String[] args) throws IOException {
        // n개의 국가가 n-1개의 도로로 연결되어있다. 모든 양 국가 간에는 경로가 존재한다.
        // 각 도로마다 연결된 양 국가와 거리가 주어진다.
        // 각 국가에서 가장 먼 국가까지의 거리를 모두 출력하라
        //
        // 트리의 지름, dijkstra 문제
        // 일단 n-1개의 연결로 모든 국가가 연결되어있다는 점에서 트리 형태이다.
        // 어떤 노드에서든 가장 먼 곳은 트리의 지름의 두 노드 중 하나이다.
        // 따라서 먼저 트리의 지름을 구하고
        // 트리의 지름에서부터 다른 국가까지의 거리를 구한다.
        // 그리고 각 국가마다 트리의 지름인 두 노드로부터 거리 중 큰 값을 취해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 국가
        int n = Integer.parseInt(br.readLine());

        roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        
        // 도로 정보
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int length = Integer.parseInt(st.nextToken());

            roads.get(from).add(new int[]{to, length});
            roads.get(to).add(new int[]{from, length});
        }
        
        // 임의의 점으로 가장 먼 노드. 트리의 지름 중 한 노드 a
        int a = dijkstra(1)[0];
        // a부터 각 노드까지의 거리를 계산.
        int[] distancesFromA = dijkstra(a);
        // a부터 가장 먼 노드인 b부터 각 노드까지의 거리를 계산
        int[] distancesFromB = dijkstra(distancesFromA[0]);
        
        // 모든 국가에 대해 트리의 지름에 해당하는 두 노드까지의 거리 중 더 큰 값을 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++)
            sb.append(Math.max(distancesFromA[i], distancesFromB[i])).append("\n");
        // 답 출력
        System.out.print(sb);
    }
    
    // start부터 dijkstra로 각 노드에 이르는 최소 거리를 계산
    static int[] dijkstra(int start) {
        // 거리
        int[] distances = new int[roads.size()];
        Arrays.fill(distances, Integer.MAX_VALUE);
        // 시작점 초기값
        distances[start] = 0;

        // dijkstra
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        priorityQueue.offer(new int[]{start, 0});
        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();
            if (current[1] > distances[current[0]])
                continue;

            for (int[] next : roads.get(current[0])) {
                if (distances[next[0]] > current[1] + next[1]) {
                    distances[next[0]] = current[1] + next[1];
                    priorityQueue.offer(new int[]{next[0], distances[next[0]]});
                }
            }
        }

        // 0번 노드는 존재하지 않으므로, start으로부터 가장 먼 노드의 idx로 채우자.
        distances[0] = start;
        for (int i = 1; i < distances.length; i++) {
            if (distances[i] > distances[distances[0]])
                distances[0] = i;
        }
        return distances;
    }
}