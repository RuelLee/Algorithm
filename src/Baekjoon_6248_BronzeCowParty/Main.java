/*
 Author : Ruel
 Problem : Baekjoon 6248번 Bronze Cow Party
 Problem address : https://www.acmicpc.net/problem/6248
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6248_BronzeCowParty;

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
        // n개의 농장에 한마리씩 소가 있으며 각 농장은 m개의 도로로 서로 이어져있다.
        // x번 농장에서 파티가 열린다.
        // x번 농장에서 소들은 모였다가, 선물을 두고 와서 자기 농장에 돌아간 뒤, 파티 농장에 다시 모인다.
        // 이 때 지연되는 파티 시간은?
        //
        // Dijkstra
        // 다익스트라 알고리즘을 통해
        // x번 농장에서 각 농장에 이르는 최소 시간을 구한 뒤
        // 가장 큰 값 * 2를 해 지연되는 시간을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 농장, m개의 도로, 파티가 열리는 x번 농장
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        
        // 도로들
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, t));
            roads.get(b).add(new Road(a, t));
        }
        
        // x번 농장에서 각 농장에 이르는 최소 거리
        int[] minDistances = new int[n + 1];
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        minDistances[x] = 0;
        // 다익스트라
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));
        priorityQueue.offer(new Road(x, 0));
        // 방문 체크
        boolean[] visited = new boolean[n + 1];
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 이미 계산된 노드라면 건너뜀
            if (minDistances[current.end] < current.distance)
                continue;
            
            // current에서 이동 가능한 다른 농장들을 살펴봄
            for (Road next : roads.get(current.end)) {
                // 미방문이고, 최소 거리를 갱신하는 경우
                if (!visited[next.end] && minDistances[next.end] > current.distance + next.distance) {
                    minDistances[next.end] = current.distance + next.distance;
                    priorityQueue.offer(new Road(next.end, minDistances[next.end]));
                }
            }
            // current 방문 체크
            visited[current.end] = true;
        }

        // 가장 시간이 오래 걸리는 농장까지 이동 시간 * 2
        // = 파티 지연 시간
        int max = 0;
        for (int i = 1; i < minDistances.length; i++)
            max = Math.max(max, minDistances[i]);
        System.out.println(max * 2);
    }
}