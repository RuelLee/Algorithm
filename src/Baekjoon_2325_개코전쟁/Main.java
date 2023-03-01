/*
 Author : Ruel
 Problem : Baekjoon 2325번 개코전쟁
 Problem address : https://www.acmicpc.net/problem/2325
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2325_개코전쟁;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class State {
    int loc;
    int distance;

    public State(int loc, int distance) {
        this.loc = loc;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 1번 지점에서 n번 지점으로 최소 거리로 이동하려고 한다.
        // 이 이동을 방해하려는 사람이 있어, 도로 중 하나를 파괴한다.
        // 이 때 도로를 파괴할 경우, 1부터 n까지의 최소 거리가 최대가 되게끔 하고자할 때, 최소 거리는?
        //
        // dijkstra 문제
        // 먼저 dijkstra로 1에서 시작하여 n에 도달하는 최소 경로를 계산한다.
        // 그 후, 기록된 최소 경로에서 도로를 하나씩 폐쇄하며 최소 거리를 계산해나간다.
        // 그 중 가장 값이 큰 최소 거리를 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 정점, m개의 도로
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 인접 행렬
        int[][] adjMatrix = new int[n + 1][n + 1];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            adjMatrix[x][y] = adjMatrix[y][x] = z;
        }
        
        // 1 -> n에 도달하는 최소 경로를 dijkstra로 계산
        // 최소 경로로 현 위치에 도달할 때, 이전 정점의 위치
        int[] preLoc = new int[n + 1];
        // 각 정점에 도달하는 최소 거리
        int[] minDistances = new int[n + 1];
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        minDistances[1] = 0;
        boolean[] visited = new boolean[n + 1];
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(state -> state.distance));
        priorityQueue.offer(new State(1, 0));
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            if (visited[current.loc])
                continue;
            else if (current.loc == n)
                break;
            
            // current.loc -> next로 가는 경로가 존재하고,
            // next로 도달하는 최소 거리를 갱신한다면
            for (int next = 1; next < adjMatrix[current.loc].length; next++) {
                if (adjMatrix[current.loc][next] != Integer.MAX_VALUE &&
                        !visited[next] && minDistances[next] > current.distance + adjMatrix[current.loc][next]) {
                    // 최소 거리 갱신
                    minDistances[next] = current.distance + adjMatrix[current.loc][next];
                    // 우선순위큐 추가
                    priorityQueue.offer(new State(next, minDistances[next]));
                    // 경로 기록 
                    preLoc[next] = current.loc;
                }
            }
            // 방문 체크
            visited[current.loc] = true;
        }

        // 최소 경로에서 도로를 하나씩 폐쇄해가며 1 -> n의 최소 거리를 계산한다.
        int maxDistance = 0;
        int current = n;
        // n부터 시작 지점에 도달할 때까지
        while (preLoc[current] != 0) {
            // preloc[current] -> current의 경로를 폐쇄
            // 해당 도로의 거리 기록.
            int temp = adjMatrix[preLoc[current]][current];
            // 도로 폐쇄
            adjMatrix[preLoc[current]][current] = adjMatrix[current][preLoc[current]] = Integer.MAX_VALUE;
            // 최소 거리 계산 및 이전에 기록한 최소 거리보다 더 먼지 확인.
            maxDistance = Math.max(maxDistance, dijkstra(n, adjMatrix));
            // 도로 복구
            adjMatrix[preLoc[current]][current] = adjMatrix[current][preLoc[current]] = temp;
            // preloc[current] -> current의 도로를 폐쇄했을 때 결과를 살펴봤으므로
            // preloc[preloc[current]] -> preloc[current]의 도로를 폐쇄했을 때 결과를 보기 위해
            // current를 preloc[current]로 변경
            current = preLoc[current];
        }
        // 각 도로를 폐쇄했을 때 최소 거리들 중 최대 값을 찾아 출력한다.
        System.out.println(maxDistance);
    }
    
    // start -> end로의 최소 경로를 dijkstra로 계산하고 거리를 반환하는 메소드
    static int dijkstra(int end, int[][] adjMatrix) {
        boolean[] visited = new boolean[adjMatrix.length];
        int[] minDistances = new int[adjMatrix.length];
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        minDistances[1] = 0;
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(state -> state.distance));
        priorityQueue.offer(new State(1, 0));
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            if (visited[current.loc])
                continue;
            else if (current.loc == end)
                return current.distance;

            for (int next = 1; next < adjMatrix[current.loc].length; next++) {
                if (adjMatrix[current.loc][next] != Integer.MAX_VALUE &&
                        !visited[next] && minDistances[next] > current.distance + adjMatrix[current.loc][next]) {
                    minDistances[next] = current.distance + adjMatrix[current.loc][next];
                    priorityQueue.offer(new State(next, minDistances[next]));
                }
            }
            visited[current.loc] = true;
        }
        return minDistances[end];
    }
}