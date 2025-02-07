/*
 Author : Ruel
 Problem : Baekjoon 31230번 모비스터디
 Problem address : https://www.acmicpc.net/problem/31230
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31230_모비스터디;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    long cost;

    public Road(int end, long cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 민겸이와 시은이가 스터디를 진행한다.
        // 총 n개의 도시와 m개의 도로가 주어지며, 민겸이는 A도시, 시은이는 B도시에 있다.
        // 스터디는 두 도시를 최단 거리로 있는 경로 내에 있는 한 도시에서 진행한다.
        // 스터디를 진행할 수 있는 도시의 개수와 각각을 모두 출력하라
        //
        // 다익스트라
        // 최단 경로 문제이다. dijkstra 알고리즘을 사용한다.
        // 대신 이전 경로 추적을 해야하고, 같은 거리를 갖는 최단 경로가 두 개 이상 될 수 있으므로
        // 이전 경로 추적 또한 복수로 진행해야할 수 있음을 유의하자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 경로, 민겸이의 도시 A, 시은이의 도시 B
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        
        // 도로 정보
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, c));
            roads.get(b).add(new Road(a, c));
        }
        
        // 민겸이의 도시에서 출발하여, 
        // 각 도시를 방문하는데 드는 최소 비용
        long[] costs = new long[n + 1];
        // 최소 비용으로 해당 도시를 방문하기 직전 방문한 도시들
        List<List<Integer>> preCities = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            preCities.add(new ArrayList<>());
        // 방문 체크
        boolean[] visited = new boolean[n + 1];
        Arrays.fill(costs, Long.MAX_VALUE);
        costs[A] = 0;
        // dijkstra
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.cost));
        priorityQueue.offer(new Road(A, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 시은이의 도시에 도착했다면 종료
            if (current.end == B)
                break;
            // 이미 방문했다면 건너뛴다.
            else if (visited[current.end])
                continue;

            // current.end에서 방문 가능한 도시 next
            for (Road next : roads.get(current.end)) {
                // next의 최소 비용이 갱신된다면
                if (costs[next.end] > costs[current.end] + next.cost) {
                    // 값 갱신
                    costs[next.end] = costs[current.end] + next.cost;
                    // next의 이전 도시 목록 클리어 후, current.end 추가
                    preCities.get(next.end).clear();
                    preCities.get(next.end).add(current.end);
                    // 우선순위큐에 next 추가
                    priorityQueue.offer(new Road(next.end, costs[next.end]));
                } else if (costs[next.end] == costs[current.end] + next.cost)   
                    // next에 방문하는 최소 비용과 같은 비용으로 방문할 수 있을 경우
                    // 이전 방문 도시 목록에만 추가
                    preCities.get(next.end).add(current.end);
            }
            // 방문 체크
            visited[current.end] = true;
        }

        // B 도시부터 이전 도시 목록들 가지고서 A까지 추적해나간다.
        Arrays.fill(visited, false);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(B);
        int count = 0;
        while (!queue.isEmpty()) {
            // 현재 도시
            int current = queue.poll();
            if (visited[current])
                continue;
            // 스터디 진행 가능 도시 개수 증가
            count++;
            // 방문 체크
            visited[current] = true;
            // current에 방문하기 직전에 방문한 도시를 queue에 추가
            for (int next : preCities.get(current))
                queue.offer(next);
        }
        
        StringBuilder sb = new StringBuilder();
        // 스터디 진행 가능 도시의 개수
        sb.append(count).append("\n");
        // 방문 배열을 통해, 스터디 진행 가능 도시들을 답안에 작성
        for (int i = 1; i < visited.length; i++) {
            if (visited[i])
                sb.append(i).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력
        System.out.println(sb);
    }
}