/*
 Author : Ruel
 Problem : Baekjoon 14657번 준오는 최종인재야!!
 Problem address : https://www.acmicpc.net/problem/14657
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14657_준오는최종인재야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Problem {
    int next;
    int time;

    public Problem(int next, int time) {
        this.next = next;
        this.time = time;
    }
}

public class Main {
    static List<List<Problem>> connections;

    public static void main(String[] args) throws IOException {
        // n개의 문제가 존재하며 문제 간의 n-1개의 연결관계가 존재한다.
        // 모든 문제는 서로 직간접적으로 연결되어있다.
        // 연결관계는 양방향이며, a라는 문제를 풀었을 때, b라는 문제를 푸는 시간을 나타낸다.
        // 첫 문제는 푸는데 0시간이 걸린다.
        // 하루에 문제를 푸는 시간 t가 주어진다.
        // 만약 t가 4이고, 4문제를 푸는데 0 + 3 + 3 + 3 = 9시간이 걸린다면, 4 + 4 + 1로 사흘이 걸린다.
        // 가장 많은 수의 문제를 가장 적은 날 동안 풀고자할 때
        // 최소 날짜의 수는?
        //
        // 트리의 지름 문제
        // 트리의 지름을 통해 가장 한 쌍의 노드, 그러한 쌍이 여러개라면 시간이 가장 적게 걸리는 노드를 찾는다.
        // 그 후, 걸리는 시간을, t를 통해 최소 소요일 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 문제, 하루에 문제를 푸는 시간 t
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 문제들의 연결 관계
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            connections.get(a).add(new Problem(b, c));
            connections.get(b).add(new Problem(a, c));
        }

        // 1번 노드에서 가장 먼 노드 A
        int[] pointA = findFarthestNode(1);
        // A에서 가장 먼 노드 B, A < - > B는 서로 가장 먼 노드이다.
        int[] pointB = findFarthestNode(pointA[0]);

        // A에서 B에 도달하는 최소 시간과 t를 고려해 최소 소요일을 구한다.
        System.out.println(pointB[1] / t + (pointB[1] % t == 0 ? 0 : 1));
    }

    // n에서 가장 먼 노드의 번호와 시간을 구한다.
    static int[] findFarthestNode(int n) {
        // 거쳐가는 노드의 수
        int[] ranks = new int[connections.size()];
        ranks[n] = 0;
        // 시간의 합
        int[] timeSum = new int[connections.size()];
        boolean[] visited = new boolean[connections.size()];

        // BFS를 통해 구한다.
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n);
        visited[n] = true;
        // n에서 가장 먼 노드의 번호
        int maxIdx = n;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (Problem next : connections.get(current)) {
                // 미방문이고, 거쳐가는 노드의 수가 증가한다면
                if (!visited[next.next] && ranks[next.next] < ranks[current] + 1) {
                    // 거쳐간 노드의 수 기록
                    ranks[next.next] = ranks[current] + 1;
                    // 시간 기록
                    timeSum[next.next] = timeSum[current] + next.time;
                    // 방문 체크
                    visited[next.next] = true;
                    // 큐 추가
                    queue.offer(next.next);
                    // 가장 먼 노드이거나
                    // maxIdx와 같은 거리이지만 더 적은 시간이 소요된다면
                    // maxIdx를 next.next로 변경.
                    if (ranks[next.next] > ranks[maxIdx] ||
                            (ranks[next.next] == ranks[maxIdx] && timeSum[next.next] < timeSum[maxIdx]))
                        maxIdx = next.next;
                }
            }
        }

        // 최종적으로 찾은 노드의 번호와 소요 시간을 반환한다.
        return new int[]{maxIdx, timeSum[maxIdx]};
    }
}