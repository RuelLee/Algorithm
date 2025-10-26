/*
 Author : Ruel
 Problem : Baekjoon 18153번 Coloring Contention
 Problem address : https://www.acmicpc.net/problem/18153
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18153_ColoringContention;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 노드, m개의 간선으로 이루어진 무방향 그래프가 주어진다.
        // 앨리스는 간선에 빨강 혹은 파랑으로 칠할 수 있다.
        // 이전에 거친 간선과 다른 색의 간선을 거치면 색상 변화라고 한다.
        // 밥은 1번 노드에서 출발하여 n번 노드로 가는데, 거치는 간선의 색상 변화를 최소화하고 싶다.
        // 앨리스는 이를 최대화하고자한다.
        // 이 때의 색상 변화를 몇 번 거치는가?
        //
        // BFS 문제
        // 문제 설명이 복잡하지만 따지고보면
        // 몇 번의 간선을 통해 1번 노드에서 n번 노드에 갈 수 있느냐? 와 거의 같은 문제
        // 매 간선을 거칠 때마다 앨리스는 다른 색상으로 만들고자 할 것이다.
        // 따라서 밥은 그냥 간선 자체를 최소한으로 거쳐 n 노드에 도달해야한다.
        // 간단한 BFS다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드, m개의 간선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            connections.get(b).add(a);
        }
        
        // 각 노드에 이르는 최소 간선의 개수
        int[] minDistance = new int[n + 1];
        Arrays.fill(minDistance, Integer.MAX_VALUE);
        // 시작 노드는 0개
        minDistance[1] = 0;
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            // next에 이르는데 minDistance[current] + 1개로 가는 것이 최선이라면
            // 해당 값으로 갱신하고 큐에 추가
            for (int next : connections.get(current)) {
                if (minDistance[next] > minDistance[current] + 1) {
                    minDistance[next] = minDistance[current] + 1;
                    queue.offer(next);
                }
            }
        }
        // 색상 변화이므로 첫 간선은 색상 변화로 치지 않는다.
        // 따라서 거친 간선의 개수 -1이 답
        System.out.println(minDistance[n] - 1);
    }
}