/*
 Author : Ruel
 Problem : Baekjoon 20007번 떡 돌리기
 Problem address : https://www.acmicpc.net/problem/20007
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20007_떡돌리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int distance;

    public Road(int end, int distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 집이 주어진다.
        // 그 중 성현이는 한 집에 살고 있으며 다른 집들에 이사 기념으로 떡을 돌리고자한다.
        // 집들 사이에는 m개의 도로가 있으며, 성현이는 하루에 x보다 먼 거리를 걷지 않고자 한다.
        // 떡은 가장 가까운 집부터 우선적으로 돌린다.
        // 성현이의 집은 y일 때, 최대 며칠이 걸려야 모든 집에 떡을 돌릴 수 있는가?
        //
        // 다익스트라 문제
        // 먼저 다익스트라 알고리즘을 통해 각 집에 방문하는 최소 거리를 모두 구한다.
        // 그 후, 거리가 가까운 순서대로 직접 떡을 돌리며, 며칠이 걸리는지 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 상수 값들
        // 집의 개수 n, 도로의 개수 m, 하루에 걷는 거리 제한 x, 주인공의 집 y
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        
        // 인접 리스트로 도로 정리
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, c));
            roads.get(b).add(new Road(a, c));
        }
        
        // 다익스트라
        // 각 집에 이르는 최소 거리
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        // 주인공의 집
        distances[y] = 0;
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.distance));
        priorityQueue.offer(new Road(y, 0));
        boolean[] visited = new boolean[n];
        while (!priorityQueue.isEmpty()) {
            // 현재 살펴볼 집
            Road current = priorityQueue.poll();
            // 이미 계산했다면 건너뛴다.
            if (visited[current.end])
                continue;

            // current에서 다음으로 방문할 수 있는 집들을 살펴본다.
            for (Road next : roads.get(current.end)) {
                // next로 가는 최소 거리를 갱신한다면
                // 거리값을 갱신하고, 우선순위큐에 담아 다음 번에 방문한다.
                if (distances[next.end] > distances[current.end] + next.distance) {
                    distances[next.end] = distances[current.end] + next.distance;
                    priorityQueue.offer(new Road(next.end, distances[next.end]));
                }
            }
            // 계산이 끝났으므로 방문 체크
            visited[current.end] = true;
        }
        
        // 떡을 돌리러 간다.
        // 왕복 거리 합이 x를 넘어서는 안된다.
        // 가까운 순서대로 방문하기 위해 정렬
        Arrays.sort(distances);
        
        // 만약 거리가 가장 먼 집이, 초기값 그대로인 경우(도로가 없는 경우)
        // 혹은 왕복 거리가 x를 넘어 방문할 수 없는 경우 -1을 출력 
        if (distances[n - 1] == Integer.MAX_VALUE || distances[n - 1] * 2 > x)
            System.out.println(-1);
        else {
            // 그 외 방문이 가능한 경우
            // 방무한 집의 수
            int visitedCount = 0;
            // 방문 여부
            visited = new boolean[n];
            // 소요 시일
            int days = 0;
            // 모든 집을 방문할 때까지
            while (visitedCount < n - 1) {
                // 현재 왕복한 거리 합
                int distanceSum = 0;
                for (int i = 1; i < distances.length; i++) {
                    // 방문했다면 건너뛰고
                    if (visited[i])
                        continue;
                    // 그렇지 않다면, i번 거리를 왕복하더라도 x를 넘지 않는지 확인한다.
                    // 그렇다면 방문하고 distanceSum에 해당 왕복 거리를 더함.
                    else if (distanceSum + distances[i] * 2 <= x) {
                        visited[i] = true;
                        distanceSum += distances[i] * 2;
                        visitedCount++;
                    } else      // 이 경우는 더 이상 방문할 수 없는 경우
                        break;  // 정렬이 되어있어 왕복거리가 더 늘어나기 때문. 반복문 종료.
                }
                // 소요 시일 증가.
                days++;
            }
            // 최종 소요 시일 출력.
            System.out.println(days);
        }
    }
}