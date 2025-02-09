/*
 Author : Ruel
 Problem : Baekjoon 28472번 Minimax Tree
 Problem address : https://www.acmicpc.net/problem/28472
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28472_MinimaxTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] nodes, ranks, parents;
    static List<List<Integer>> child;

    public static void main(String[] args) throws IOException {
        // Minimax 알고리즘은 체스나 바둑, 틱택토 같이 상대방과 번갈아가면서 하는 게임에서 사용하는 알고리즘이다.
        // 이 알고리즘에서 사용되는 트리는
        // 노드에 저장되는 값이 리프 노드가 아니라면 자식 노드들의 최댓값, 최솟값이 번갈아가면서 저장된다.
        // 예를 들어, 루트 노드에는 자식 노드들 중 최댓값이 저장되고
        // 깊이가 1일 경우에는 최솟값, 2일 경우에는 최댓값... 이 번갈아가면서 등장한다.
        // 루트 노드와, 부모 자식 간의 연결, 리프 노드들의 값은 주어진다고 할 때
        // q개의 노드에 대해, 노드의 값을 출력하라

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드, 루트 노드 r
        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        
        // 부모 자식 간의 연결
        child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            child.get(u).add(v);
            child.get(v).add(u);
        }
        // 깊이, 부모 노드, 노드의 값
        ranks = new int[n + 1];
        ranks[r] = 1;
        parents = new int[n + 1];
        nodes = new int[n + 1];
        // bfs로, 깊이와 부모 노드를 찾고, 노드 값을 계산하기 쉽게 초기화
        bfs(r);

        // 리프 노드
        int l = Integer.parseInt(br.readLine());
        // 우선순위큐에 담아, 깊이가 깊은 노드부터 탐색
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(ranks[o2], ranks[o1]));
        boolean[] enqueued = new boolean[n + 1];
        for (int i = 0; i < l; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            // 노드의 값
            nodes[k] = t;
            // 우선순위큐에 추가
            priorityQueue.offer(k);
            enqueued[k] = true;
        }

        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            // 만약 자신이 짝수번째 깊이라면
            // 부모 노드는 자식들의 최댓값을 갖는다.
            // 부모 노드에 자신의 값 반영
            if (ranks[current] % 2 == 0)
                nodes[parents[current]] = Math.max(nodes[parents[current]], nodes[current]);
            else        // 자신이 홀수번째라면, 부모 노드는 최솟값을...
                nodes[parents[current]] = Math.min(nodes[parents[current]], nodes[current]);

            // 부모 노드가 아직 큐에 담긴 적이 없다면 담는다.
            if (!enqueued[parents[current]]) {
                priorityQueue.offer(parents[current]);
                enqueued[parents[current]] = true;
            }
        }
        
        // q개의 쿼리 답변
        StringBuilder sb = new StringBuilder();
        int q = Integer.parseInt(br.readLine());
        for (int i = 0; i < q; i++)
            sb.append(nodes[Integer.parseInt(br.readLine())]).append("\n");
        System.out.print(sb);
    }
    
    // bfs 탐색
    static void bfs(int node) {
        // node의 길이에 따라
        // 자신이 홀수번째라면 최댓값을 계산해야하므로 0으로 초기화
        // 짝수번째라면 최솟값을 계산해야하므로 큰 값으로 초기화
        nodes[node] = ranks[node] % 2 == 1 ? 0 : Integer.MAX_VALUE;
        for (int next : child.get(node)) {
            // 아직 깊이가 0일 경우, 부모 노드가 아닌 자식 노드
            if (ranks[next] == 0) {
                // 자식 노드이므로 자신보다 깊이가 1증가.
                ranks[next] = ranks[node] + 1;
                // 부모 노드 표시
                parents[next] = node;
                // 재귀 탐색
                bfs(next);
            }
        }
    }
}