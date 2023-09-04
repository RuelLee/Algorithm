/*
 Author : Ruel
 Problem : Baekjoon 12896번 스크루지 민호
 Problem address : https://www.acmicpc.net/problem/12896
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12896_스크루지민호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Probe {
    int n;
    int distance;

    public Probe(int n, int distance) {
        this.n = n;
        this.distance = distance;
    }
}

public class Main {
    static List<List<Integer>> connections;

    public static void main(String[] args) throws IOException {
        // n개의 도시가 n-1개의 도로를 이용해 모든 도시들 사이에는 단 한 개의 경로만 존재하도록 연결되어있다.
        // n-1개의 도로에 대해 연결된 두 도시가 주어진다.
        // 하나의 소방서를, 가장 먼 도시까지 출동하는 거리가 최소인 도시에 세우려고 한다.
        // 이 때 가장 먼 도시까지 출동하는 거리는?
        //
        // 그래프 탐색 문제
        // 조건을 살펴보면 도시들의 연결 상태는 트리 형태이다.
        // 트리에서 가장 먼 두 노드는
        // 임의의 한 노드에서 가장 먼 노드 A를 구하고
        // A로부터 가장 먼 노드 B를 구하면
        // A와 B가 트리 내에서 가장 먼 두 노드이다.
        // 소방서는 최적 위치에 설치하므로, A와 B 사이의 경로에 위치한 중간 지점 도시에 설치하게 된다.
        // 따라서 가장 먼 두 노드의 거리를 구하고, 그 거리의 반이
        // 소방서에서 출동하는데 가장 먼 거리이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 도시
        int n = Integer.parseInt(br.readLine());

        // n - 1개의 도로
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            connections.get(b).add(a);
        }

        // 1에서 가장 먼 도시 A
        Probe pointA = findFarthestFromN(1);
        // A에서 가장 먼 도시 B
        Probe pointB = findFarthestFromN(pointA.n);

        // A와 B 도시 사이의 경로에서
        // 중간 위치에 소방서를 설치하고, 해당 위치에서
        // A나 B로 출동하는 거리 중 먼 거리가 가장 먼 거리가 된다.
        System.out.println((pointB.distance + 1) / 2);
    }
    
    // n으로부터 가장 먼 노드를 구한다.
    static Probe findFarthestFromN(int n) {
        // n의 위치에서부터 BFS 탐색을 한다.
        Queue<Probe> queue = new LinkedList<>();
        queue.offer(new Probe(n, 0));
        Probe answer = null;
        // 방문 표시
        boolean[] visited = new boolean[connections.size()];
        visited[n] = true;
        while (!queue.isEmpty()) {
            Probe current = queue.poll();
            
            // 최대 거리 노드 값이 아직 없거나
            // current가 answer보다 더 멀다면 값 갱신
            if (answer == null || answer.distance < current.distance)
                answer = current;

            // current.n에 연결된 도시들을 살펴본다.
            for (int next : connections.get(current.n)) {
                // 방문했던 노드라면(= 이전 노드라면) 건너뛴다.
                if (visited[next])
                    continue;

                // 큐에 추가.
                queue.offer(new Probe(next, current.distance + 1));
                // 방문 표시
                visited[next] = true;
            }
        }
        // 최종적으로 찾은 n에서 가장 먼 노드에 대한 결과값 반환.
        return answer;
    }
}