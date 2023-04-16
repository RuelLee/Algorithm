/*
 Author : Ruel
 Problem : Baekjoon 13141번 Ignition
 Problem address : https://www.acmicpc.net/problem/13141
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13141_Ignition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge {
    int start;
    int end;
    int distance;

    public Edge(int start, int end, int distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정점과 m개의 간선이 주어진다.
        // 어느 정점에 불을 붙여 모든 간선을 최단 시간에 태우려고 한다.
        // 불을 1초에 1만큼씩 진행된다할 때
        // 모든 간선을 태울 수 있는 최소 시간은?
        //
        // 플로이드-워셜, 브루트포스 문제
        // 먼저 플로이드-워셜을 통해 각 정점끼리의 최단 거리를 구한다.
        // 브루트 포스와 BFS를 이용하여 처음 불을 붙이는 정점에 따른 각 정점에 불이 도달하는 시간을 계산한다.
        // 그 후 모든 간선들을 살펴보며 해당 간선이 모두 타는데 걸리는 시간을 각각 구해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점과 m개의 간선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 간선의 정보를 저장할 인접행렬
        int[][] adjMatrix = new int[n][n];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);

        // 각 간선들을 한번씩 살펴보기 위해 리스트에도 저장한다.
        List<Edge> list = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) - 1;
            int e = Integer.parseInt(st.nextToken()) - 1;
            int l = Integer.parseInt(st.nextToken());

            // 인접행렬과 리스트에 저장
            adjMatrix[s][e] = adjMatrix[e][s] = Math.min(adjMatrix[s][e], l);
            list.add(new Edge(s, e, l));
        }

        // 플로이드 워셜
        for (int via = 0; via < adjMatrix.length; via++) {
            for (int start = 0; start < adjMatrix.length; start++) {
                if (start == via || adjMatrix[start][via] == Integer.MAX_VALUE)
                    continue;
                for (int end = 0; end < adjMatrix.length; end++) {
                    if (end == start || end == via || adjMatrix[end][start] == Integer.MAX_VALUE || adjMatrix[end][via] == Integer.MAX_VALUE)
                        continue;

                    if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }

        // 답안으로 제출할 모든 간선이 타는데 소요된 최소 시간
        double minTime = Double.MAX_VALUE;
        // 1 ~ n번 정점에 처음 불을 붙였을 때
        for (int i = 0; i < n; i++) {
            // 각 정점에 불이 도달하는 최소 시간을 BFS를 통해 계산한다.
            int[] minTimes = new int[n];
            Arrays.fill(minTimes, Integer.MAX_VALUE);
            minTimes[i] = 0;
            PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.distance));
            priorityQueue.offer(new Edge(0, i, 0));
            boolean[] visited = new boolean[n];
            while (!priorityQueue.isEmpty()) {
                Edge current = priorityQueue.poll();
                if (visited[current.end])
                    continue;

                for (int j = 0; j < adjMatrix[current.end].length; j++) {
                    if (j == current.end || adjMatrix[current.end][j] == Integer.MAX_VALUE)
                        continue;

                    if (!visited[current.end] && minTimes[j] > minTimes[current.end] + adjMatrix[current.end][j]) {
                        minTimes[j] = minTimes[current.end] + adjMatrix[current.end][j];
                        priorityQueue.offer(new Edge(current.end, j, minTimes[j]));
                    }
                }
                visited[current.end] = true;
            }
            // 각 정점에 불이 도달하는 최소 시간 계산 끝

            // 간선들 중 가장 타는데 오래 걸린 시간
            double maxTime = 0;
            // 계산된 결과를 토대로 각 간선들이 타는데 걸린 시간을 계산한다.
            for (Edge e : list) {
                // 양쪽 정점을 비교하여, 두 정점의 불이 붙는 시간의 차이만큼은
                // 길이가 1씩 줄어들며, 늦은 쪽의 정점에 불이 붙은 시간부터는 2씩 줄어든다.
                double time = Math.max(((double) e.distance - Math.abs(minTimes[e.end] - minTimes[e.start])) / 2, 0) + Math.max(minTimes[e.start], minTimes[e.end]);
                // 간선들 중 가장 늦은 시간에 타는지 확인한다.
                maxTime = Math.max(maxTime, time);
            }

            // 각 정점에 불을 붙였을 때, 모든 간선이 타는 시간들 중에서 최소 시간인지 확인한다.
            minTime = Math.min(minTime, maxTime);
        }
        // 답안 출력.
        System.out.println(minTime);
    }
}