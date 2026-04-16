/*
 Author : Ruel
 Problem : Jungol 1108번 페이지 전환
 Problem address : https://jungol.co.kr/problem/1108
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1108_페이지전환;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connections;
    static int[] minDistance;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        // e개의 단방향 간선이 주어진다.
        // 각 정점은 페이지이며, 각 페이지에서 다른 페이지로 가는 모든 클릭 횟수의 평균을 구한다.
        //
        // dijkstra 문제
        // 모든 정점에서 출발하여, 모든 정점으로 도달하는 경우의 수를 계산해야하므로 얼핏 플로이드 와셜로 보이지만
        // 500 * 500 * 500이 1억을 넘어가므로 조금 아슬아슬.
        // 따라서 dijkstra를 모든 정점에서 출발하도록 한다.
        // e의 개수가 적으므로 dijkstra가 금방 끝난다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 간선의 개수
        int e = Integer.parseInt(br.readLine());
        connections = new ArrayList<>();
        for (int i = 0; i < 500; i++)
            connections.add(new ArrayList<>());
        // 각 페이지가 출현했는지 여부 및 총 개수를 확인한다.
        HashSet<Integer> hashSet = new HashSet<>();
        StringTokenizer st;
        // 간선 입력
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            hashSet.add(a);
            hashSet.add(b);
        }

        // 각 정점에서 다른 정점에 이르는 클릭 횟수를 체크할 공간
        minDistance = new int[500];
        // 방문 체크 공간
        visited = new boolean[500];

        int sum = 0;
        // 각 정점마다 다른 모든 정점에 이르는 클릭 횟수의 합을 구한다.
        for (int start : hashSet)
            sum += dijkstraCountAll(start);

        // 총 개수를 모든 경우의 수(정점의 개수 * (정점의 개수 -1))로 나눠 평균을 구한다.
        double answer = sum / (double) hashSet.size() / (hashSet.size() - 1);
        // 답을 형식에 맞게 출력
        System.out.printf("%.3f%n", answer);
    }

    // dijkstra를 통해 start에서 다른 정점에 이르는 클릭 횟수의 총합을 구한다.
    static int dijkstraCountAll(int start) {
        // 배열 초기화
        Arrays.fill(minDistance, Integer.MAX_VALUE);
        minDistance[start] = 0;
        Arrays.fill(visited, false);

        // 간선의 가중치가 전부 1이므로 딱히 우선순위큐를 큐를 쓰지 않아도 된다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> minDistance[o]));
        priorityQueue.offer(start);
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();

            // 다음 간선.
            for (int next : connections.get(current)) {
                // 미방문이고, 최소 클릭 횟수를 갱신하는 경우.
                if (!visited[next] && minDistance[next] > minDistance[current] + 1) {
                    // 값 갱신 및 큐에 추가
                    minDistance[next] = minDistance[current] + 1;
                    priorityQueue.offer(next);
                }
            }
            // 방문 체크
            visited[current] = true;
        }

        // 다른 정점에 이르는 클릭 횟수를 모두 더해
        int sum = 0;
        for (int md : minDistance) {
            if (md != Integer.MAX_VALUE)
                sum += md;
        }
        // 반환한다.
        return sum;
    }
}