/*
 Author : Ruel
 Problem : Baekjoon 22865번 가장 먼 곳
 Problem address : https://www.acmicpc.net/problem/22865
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*

package Baekjoon_22865_가장먼곳;

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
    static List<List<Road>> roads;

    public static void main(String[] args) throws IOException {
        // 가장 먼 곳
        // n개의 땅이 주어진다.
        // 세 명의 친구들의 현재 사는 위치가 주어진다.
        // m개의 도로로 각 땅은 연결되어있다. 도로의 길이는 각각 주어진다.
        // 세 친구들이 같은 집에서 같이 자취를 한다.
        // 각 친구들은 최대한 원래 집에서 떨어진 곳에서 살고자한다.
        // 원래 집에서 가장 거리가 가까운 친구의 집까지가 새로운 집의 거리가 되고
        // 이걸 최대화하고자 한다.
        //
        // dijkstra 문제
        // 각 친구들의 집에서 모든 점까지 이르는 최대거리를 각각 구하고
        // 각 지점에서 세 친구들의 원래 집으로부터의 거리들 중 가장 작은 값을 살펴보고
        // 그 중 가장 큰 값을 답으로 남겨둔다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 땅
        int n = Integer.parseInt(br.readLine());
        
        // 세 친구의 원래 집 위치
        int[] homes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 도로 입력
        roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            roads.get(d).add(new Road(e, l));
            roads.get(e).add(new Road(d, l));
        }
        
        // 각 지점에 이르는 거리
        int[][] distances = new int[3][];
        for (int i = 0; i < distances.length; i++)
            distances[i] = dijkstra(homes[i]);

        // 답안
        int answer = 0;
        // 최소 거리의 최대값을 찾는다.
        int max = 0;
        for (int i = 1; i < n + 1; i++) {
            // 최소 거리
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < distances.length; j++)
                min = Math.min(min, distances[j][i]);

            if (min > max) {
                max = min;
                answer = i;
            }
        }

        System.out.println(answer);
    }
    
    // 다익스트라
    // 출발 지점을 입력받으면 배열 형태로 다른 지점에 도달하는 시간을 계산한다.
    static int[] dijkstra(int start) {
        int[] distances = new int[roads.size()];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;
        boolean[] visited = new boolean[roads.size()];

        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));
        priorityQueue.offer(new Road(start, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            if (current.distance > distances[current.end])
                continue;

            for (Road next : roads.get(current.end)) {
                if (!visited[next.end] && distances[next.end] > distances[current.end] + next.distance) {
                    distances[next.end] = distances[current.end] + next.distance;
                    priorityQueue.offer(new Road(next.end, distances[next.end]));
                }
            }
            visited[current.end] = true;
        }
        return distances;
    }
}