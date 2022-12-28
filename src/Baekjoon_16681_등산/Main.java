/*
 Author : Ruel
 Problem : Baekjoon 16681번 등산
 Problem address : https://www.acmicpc.net/problem/16681
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16681_등산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Route {
    int end;
    long distance;

    public Route(int end, long distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 주인공은 집에서 어느 지점을 거쳐 학교로 가려고 한다.
        // 해당 지점까지는 오르막길, 지점부터 학교가지는 내리막길로 가고자한다.
        // 주인공은 높은 지점을 거칠수록 많은 성취감을 얻는다. 하지만 많이 이동하면 체력을 많이 소모한다.
        // 높이당 E의 성취감을 얻고, 거리 당 E의 체력을 소모할 때
        // 성취감 - 소모한 체력의 값이 가장 높은 지점을 들려갈 경우, 해당 값을 출력하라
        // N개의 지점, M개의 도로, 높이당 얻는 성취감 E, 거리당 소모 체력 E
        //
        // 다익스트라 문제
        // 지점이 최대 10만개, 거리가 최대 10만으로 주어지므로, int 범위를 넘을 수있음을 유의하자.
        // 높이에 대한 조건이 있다. 길이 있다하더라도 항상 갈 수 있는 길은 아니다.
        // 집에서 각 지점까지 이르는 최소 길이들과
        // 그리고 역으로 학교에서 각 지점에 이르는 최소 길이들을 구한다.
        // 위 값을 바탕으로 집에서 각 지점을 거쳐 학교에 이르는 최소 거리를 통해 
        // 구하고자 하는 값을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력 처리
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        
        // 각 지점의 높이
        int[] heights = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 입력되는 도로들
        List<List<Route>> routes = new ArrayList<>(N);
        for (int i = 0; i < N; i++)
            routes.add(new ArrayList<>());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int n = Integer.parseInt(st.nextToken());

            routes.get(a).add(new Route(b, n));
            routes.get(b).add(new Route(a, n));
        }

        // 다익스트라를 통해 집에서 지점에 이르는 거리
        long[] fromHome = dijkstra(0, routes, heights);
        // 와 학교에서 지점에 이르는 거리를 구한다.
        long[] fromUni = dijkstra(N - 1, routes, heights);

        // 각 지점에서의 성취감 - 소모 체력의 값을 구하고
        // 해당 값의 최대값을 찾는다.
        long answer = Long.MIN_VALUE;
        for (int i = 1; i < N - 1; i++) {
            if (fromHome[i] != Long.MAX_VALUE && fromUni[i] != Long.MAX_VALUE)
                answer = Math.max(answer, (long) heights[i] * E - (fromHome[i] + fromUni[i]) * D);
        }

        // 초기값 그대로라면 답이 없는 경우. Impossible 출력
        // 있다면 해당 값 출력.
        System.out.println(answer != Long.MIN_VALUE ? answer : "Impossible");
    }
    
    // 다익스트라 메소드
    // 시작 지점과 도로들 그리고 각 지점의 높이 정보를 바탕으로 다익스트라 알고리즘을 돌린다.
    static long[] dijkstra(int start, List<List<Route>> routes, int[] heights) {
        // start 부터 각 지점에 이르는 최소 거리.
        long[] minDistances = new long[routes.size()];
        Arrays.fill(minDistances, Long.MAX_VALUE);
        minDistances[start] = 0;

        // start에서 시작해서 이르는 거리가 짧은 순으로 살펴본다.
        PriorityQueue<Route> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(value -> value.distance));
        priorityQueue.offer(new Route(start, 0));
        // 방문 체크
        boolean[] visited = new boolean[routes.size()];
        while (!priorityQueue.isEmpty()) {
            Route current = priorityQueue.poll();
            if (visited[current.end])
                continue;

            // current에서 next로 이동이 가능한지 살펴본다.
            for (Route next : routes.get(current.end)) {
                // current의 높이보다 next의 높이가 항상 더 높아야하고
                // 최소 거리가 갱신된다면
                if (heights[current.end] < heights[next.end] &&
                        minDistances[next.end] > minDistances[current.end] + next.distance) {
                    minDistances[next.end] = minDistances[current.end] + next.distance;
                    priorityQueue.offer(new Route(next.end, minDistances[next.end]));
                }
            }
            visited[current.end] = true;
        }
        // 결과 반환
        return minDistances;
    }
}