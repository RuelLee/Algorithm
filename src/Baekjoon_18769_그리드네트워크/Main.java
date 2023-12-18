/*
 Author : Ruel
 Problem : Baekjoon 18769번 그리드 네트워크
 Problem address : https://www.acmicpc.net/problem/18769
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18769_그리드네트워크;

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
        // r * c 격자 모양으로 연결된 시스템이 존재한다.
        // 각 연결마다 비용이 주어지며
        // 모든 서버들을 최소 비용으로 직간접적으로 연결하고자 한다.
        // 그 때 그 비용은?
        //
        // 최소 스패닝 트리 문제
        // 간단하게 좌표를 하나의 인덱스로 치환하여
        // prim 혹은 kruskal 알고리즘을 돌려주자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스
        int t = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 격자의 크기
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            
            // 모든 연결들
            List<List<Edge>> edges = new ArrayList<>();
            for (int i = 0; i < r * c; i++)
                edges.add(new ArrayList<>());

            for (int i = 0; i < r; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < c - 1; j++) {
                    // 인접한 좌우 서버의 연결 비용
                    int cost = Integer.parseInt(st.nextToken());
                    edges.get(i * c + j).add(new Edge(i * c + j + 1, cost));
                    edges.get(i * c + j + 1).add(new Edge(i * c + j, cost));
                }
            }
            for (int i = 0; i < r - 1; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < c; j++) {
                    // 인접한 상하 서버의 연결 비용
                    int cost = Integer.parseInt(st.nextToken());
                    edges.get(i * c + j).add(new Edge((i + 1) * c + j, cost));
                    edges.get((i + 1) * c + j).add(new Edge(i * c + j, cost));
                }
            }
            
            boolean[] connected = new boolean[r * c];
            // 0번은 처음부터 연결되어있다 보고 
            connected[0] = true;
            // 0번에서 연결 가능한 다른 서버들을 추가
            PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparing(o -> o.cost));
            for (Edge e : edges.get(0))
                priorityQueue.offer(e);

            int sum = 0;
            while (!priorityQueue.isEmpty()) {
                Edge current = priorityQueue.poll();
                // 꺼낸 서버가 이미 연결이 되어있다면 건너뛴다.
                if (connected[current.end])
                    continue;
                
                // 그렇지 않다면 연결하고
                connected[current.end] = true;
                // 해당 비용 추가.
                sum += current.cost;
                // current.end로부터 아직 연결되지 않았지만 연결 가능한 서버들을 추가.
                for (Edge next : edges.get(current.end)) {
                    if (!connected[next.end])
                        priorityQueue.offer(next);
                }
            }
            // 통신망 구축 비용 기록
            sb.append(sum).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}