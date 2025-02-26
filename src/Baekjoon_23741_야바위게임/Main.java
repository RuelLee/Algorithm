/*
 Author : Ruel
 Problem : Baekjoon 23741번 야바위 게임
 Problem address : https://www.acmicpc.net/problem/23741
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23741_야바위게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정점과 m개의 간선이 주어진다.
        // 준석이는 x번 정점에 공을 숨겨두고선, 연결된 정점으로 y번 이동시켜 공을 숨긴다.
        // 공이 있을 가능성이 있는 정점의 위치를 오름차순으로 출력하라
        //
        // BFS
        // 처음 간선으로부터 y번 탐색하여 최종적으로 있을 수 있는 정점들을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, m개의 간선, 처음 공이 있는 정점 x, y번 이동
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        // 정점 간 연결 정보
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

        // 두 개의 큐로 번갈아가면서 사용.
        List<Queue<Integer>> queues = new ArrayList<>();
        for (int i = 0; i < 2; i++)
            queues.add(new LinkedList<>());

        queues.get(0).offer(x);
        // 이번 턴에 해당 정점을 선택했는지 표시
        boolean[] selected = new boolean[n + 1];
        // BFS 탐색을 y번 반복한다.
        for (int i = 0; i < y; i++) {
            Arrays.fill(selected, false);
            // 큐가 비어있지 않다면
            while (!queues.get(i % 2).isEmpty()) {
                // 해당 정점을 꺼냄
                int current = queues.get(i % 2).poll();

                // 연결된 정점으로 이동.
                for (int next : connections.get(current)) {
                    if (!selected[next]) {
                        selected[next] = true;
                        queues.get((i + 1) % 2).offer(next);
                    }
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        // 큐가 비어있지 않다면
        if (!queues.get(y % 2).isEmpty()) {
            // 우선 순위 큐에 해당 정점들을 담아 오름차순 정렬
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            while (!queues.get(y % 2).isEmpty())
                priorityQueue.offer(queues.get(y % 2).poll());
            // 답안 작성
            sb.append(priorityQueue.poll());
            while (!priorityQueue.isEmpty())
                sb.append(" ").append(priorityQueue.poll());
        } else      // 큐가 비어있다면 불가능한 경우이므로 -1을 기록
            sb.append(-1);
        // 답안 출력
        System.out.println(sb);
    }
}