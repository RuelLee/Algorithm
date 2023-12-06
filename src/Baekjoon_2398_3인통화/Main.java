/*
 Author : Ruel
 Problem : Baekjoon 2398번 3인통화
 Problem address : https://www.acmicpc.net/problem/2398
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2398_3인통화;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge {
    int end;
    int cost;

    public Edge(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 3인 통화 서비스가 있다.
        // 각 통화자들은 스위치에 연결하여 전화망에 접속한다.
        // n개의 스위치, m개의 연결, 그리고 각 연결 정보
        // 그리고 세 명의 시작 스위치 위치가 주어진다.
        // 세 명이 연결되는데 최소 비용으로 통화하고자한다면 그 비용은?
        //
        // 다익스트라 문제
        // 한 점에서 다른 두 점으로 가는 최소 비용을 생각하면 힘들어진다.
        // 세 명이 위치한 스위치로부터 다른 스위치까지의 비용을 모두 구하고
        // 세 명이 한 스위치로 모였을 때, 그 비용이 최소인 곳을 찾으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 전체 n개의 스위치
        int n = Integer.parseInt(st.nextToken());
        // m개의 연결
        int m = Integer.parseInt(st.nextToken());
        // 연결 정보
        List<List<Edge>> edges = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            edges.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges.get(a).add(new Edge(b, cost));
            edges.get(b).add(new Edge(a, cost));
        }
        // 세 명의 위치
        int[] clients = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 각 사람 별로 다른 스위치까지의 접속 비용
        int[][] distances = new int[3][n + 1];
        // 해당 스위치가 연결되기 위해 거치는 이전 스위치 정보
        int[][] pre = new int[3][n + 1];
        for (int i = 0; i < distances.length; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
            distances[i][clients[i]] = 0;
        }

        // 총 3번의 다익스트라를 돌린다.
        for (int i = 0; i < clients.length; i++) {
            // 방문 체크
            boolean[] visited = new boolean[n + 1];
            PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.cost));
            // 각 사람의 위치에서 시작.
            priorityQueue.offer(new Edge(clients[i], 0));
            // 다익스트라
            while (!priorityQueue.isEmpty()) {
                Edge current = priorityQueue.poll();
                if (visited[current.end])
                    continue;

                for (Edge next : edges.get(current.end)) {
                    if (!visited[next.end] && distances[i][next.end] > distances[i][current.end] + next.cost) {
                        distances[i][next.end] = distances[i][current.end] + next.cost;
                        pre[i][next.end] = current.end;
                        priorityQueue.offer(new Edge(next.end, distances[i][next.end]));
                    }
                }
                visited[current.end] = true;
            }
        }
        // 3번의 다익스트라 종료
        
        // 세 명이 모두 모이는 최소 비용 스위치 위치 탐색
        int idx = 1;
        for (int i = 2; i < n + 1; i++) {
            if (distances[0][i] + distances[1][i] + distances[2][i]
                    < distances[0][idx] + distances[1][idx] + distances[2][idx])
                idx = i;
        }
        
        // 사용한 경로를 boolean 배열에 체크하고 연결의 개수 계산.
        boolean[][] used = new boolean[n + 1][n + 1];
        int count = 0;
        for (int i = 0; i < pre.length; i++) {
            int loc = idx;
            // 시작 위치(= 비용이 0)인 곳까지 탐색
            while (distances[i][loc] != 0) {
                if (!used[loc][pre[i][loc]]) {
                    used[loc][pre[i][loc]] = used[pre[i][loc]][loc] = true;
                    count++;
                }
                loc = pre[i][loc];
            }
        }


        StringBuilder sb = new StringBuilder();
        // 최소 비용과 그 때의 연결 개수
        sb.append(distances[0][idx] + distances[1][idx] + distances[2][idx]).append(" ").append(count).append("\n");
        // 그리고 사용된 연결들을 기록
        for (int i = 1; i < used.length; i++) {
            for (int j = i + 1; j < used[i].length; j++) {
                if (used[i][j])
                    sb.append(i).append(" ").append(j).append("\n");
            }
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}