/*
 Author : Ruel
 Problem : Baekjoon 16940번 BFS 스페셜 저지
 Problem address : https://www.acmicpc.net/problem/16940
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16940_BFS스페셜저지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정점과 n - 1개의 간선으로 이루어진 양방향 그래프가 주어진다
        // 그리고 BFS 방문 순서가 주어졌을 때 올바른 방문인지 확인하라
        //
        // BFS 문제
        // BFS 알고리즘은 너비우선으로 탐색하기 때문에 깊이가 큰 노드는 깊이가 작은 노드보다 절대 앞에 나올 수 없다.
        // BFS 탐색이기 때문에 양방향 그래프라 했지만 사실상 트리라 다름 없다.
        // 그리고 노드 a를 노드 b보다 먼저 방문했다면
        // a의 자식노드들 또한 b의 자식 노드들 보다 우선적으로 탐색해야한다.
        // 위 점을 유의하며 문제를 해결한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 노드의 개수
        int n = Integer.parseInt(br.readLine());
        // 간선들
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            edges.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges.get(a).add(b);
            edges.get(b).add(a);
        }

        // 각 노드들의 깊이를 기록한다.
        int[] ranks = new int[n + 1];
        // 부모 노드가 무엇인지 나타낸다.
        int[] parents = new int[n + 1];
        Arrays.fill(ranks, Integer.MAX_VALUE);
        ranks[1] = 0;
        // BFS 탐색
        Queue<Integer> queue = new LinkedList<>();
        // 시작은 1번 노드
        queue.offer(1);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 간선들을 살펴보며 아직 방문하지 않은 노드라면
            // current의 자식 노드라 볼 수 있다.
            for (int next : edges.get(current)) {
                if (ranks[next] == Integer.MAX_VALUE) {
                    // 부모 노드의 깊이 + 1
                    ranks[next] = ranks[current] + 1;
                    // 부모 노드 표시
                    parents[next] = current;
                    // 큐에 next 추가
                    queue.offer(next);
                }
            }
        }
        
        // 문제에서 주어지는 bfs 순서
        int[] bfsOrders = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 현재 깊이를 표시
        int currentRank = 0;
        // 노드가 등장한 순서 표시
        int[] orders = new int[n + 1];
        // 현재 순서
        int order = 0;
        // 등장한 노드의 부모들 중 가장 늦은 순서의 값
        int maxParentOrder = 0;
        boolean possible = true;
        for (int node : bfsOrders) {
            // 현재 진행한 깊이보다 더 작은 깊이의 노드가 나왔거나
            // 현재 노드의 부모 노드의 순서가 maxParentOrder보다 더 작은 값이 나왔다면
            // 해당 순서는 잘못된 순서.
            // possible을 false로 만들어주고 종료.
            if (ranks[node] < currentRank || maxParentOrder > orders[parents[node]]) {
                possible = false;
                break;
            }

            // 가능한 경우라면
            // 현재 노드의 순서를 기록하고
            orders[node] = order++;
            // 진행 중인 깊이가 최대값을 갱신했는지 확인하고
            // (다음 깊이로 진행한 경우 표시하기 위함)
            currentRank = Math.max(currentRank, ranks[node]);
            // 부모 노드의 순서 또한 최대값을 갱신했는지 확인한다
            // (더 일찍 등장한 부모 노드의 자식 노드들이, 더 늦게 등장한 부모 노드의 자식 노드들보다 늦게 등장하는지 체크)
            maxParentOrder = Math.max(maxParentOrder, orders[parents[node]]);
        }

        // possible의 값에 따라 가능한 경우 1,
        // 불가능한 경우 0을 출력.
        System.out.println(possible ? 1 : 0);
    }
}