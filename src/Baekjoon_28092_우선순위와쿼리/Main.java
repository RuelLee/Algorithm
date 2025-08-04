/*
 Author : Ruel
 Problem : Baekjoon 28092번 우선순위와 쿼리
 Problem address : https://www.acmicpc.net/problem/28092
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28092_우선순위와쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Tree {
    int nodes;
    int minNode;

    public Tree(int nodes, int minNode) {
        this.nodes = nodes;
        this.minNode = minNode;
    }
}

public class Main {
    static int[][] parents;

    public static void main(String[] args) throws IOException {
        // n개의 노드가 있다.
        // q개의 쿼리를 처리하라
        // 1 u v : u와 v를 잇는 간선을 추가한다.
        // 2 : 존재하는 트리 형태들 중에 노드의 개수가 가장 많은 트리의 가장 작은 노드 번호를 출력한다.
        // 노드 개수가 가장 많은 트리가 복수 존재하는 경우, 가장 작은 정점 번호를 출력한다.
        // 그리고 트리의 간선과 정점을 모두 삭제한다.
        //
        // 분리 집합, 우선순위큐 문제
        // 분리 집합을 통해 1번 쿼리는 u와 v를 한 집합으로 묶되, 이미 같은 집합이라면 트리 형태가 아니게 되므로 없앤다.
        // 하지만 없애기는 쉽지 않으므로, 사용하지 않는 0번 노드에 모두 묶어, 0번 노드와 연결된 노드들은 무시하기로 한다.
        // 2번 쿼리는 우선순위큐를 통해, 정점의 개수가 가장 많은 트리, 여러개라면 노드의 최소 번호가 가장 작은 트리가 우선적으로 나오도록
        // 정렬 조건을 주어 처리한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드 , q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // parents[i][0] = i번 노드가 속한 집합의 대표
        // parents[i][1] = i번 노드가 속한 집합의 노드 개수
        // parents[i][2] = i번 노드가 속한 집합의 최소 노드 번호
        parents = new int[n + 1][3];
        for (int i = 1; i < parents.length; i++) {
            parents[i][0] = parents[i][2] = i;
            parents[i][1] = 1;
        }
        
        // 노드 개수가 많은 트리가 우선적으로, 여러개라면 노드 최소 번호가 작은 트리가 우선
        PriorityQueue<Tree> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.nodes == o2.nodes)
                return Integer.compare(o1.minNode, o2.minNode);
            return Integer.compare(o2.nodes, o1.nodes);
        });
        // 단일 노드도 트리이므로, 모두 우선순위큐에 추가
        for (int i = 1; i <= n; i++)
            priorityQueue.offer(new Tree(1, i));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 1번 쿼리 처리
            if (Integer.parseInt(st.nextToken()) == 1) {
                // u, v
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                // 두 노드가 속한 집합이 다른 경우
                if (findParent(u) != findParent(v)) {
                    // 하나의 집합으로 묶고, 현재 트리 상태를 우선순위큐에 추가
                    union(u, v);
                    priorityQueue.offer(new Tree(parents[findParent(u)][1], parents[findParent(u)][2]));
                } else      // 이미 같은 집합인 경우, 트리 형태가 아니게 되므로 0번 노드와 묶어버린다.
                    union(u, 0);
            } else {
                // 0번 노드와 묶인 트리가 아니게 될 때까지 우선순위큐에서 제거
                while (!priorityQueue.isEmpty() && parents[findParent(priorityQueue.peek().minNode)][2] == 0)
                    priorityQueue.poll();
                // 현재 가장 노드의 수가 많고, 최소 번호가 작은 트리
                Tree current = priorityQueue.poll();
                // 기록
                sb.append(current.minNode).append("\n");
                // 후 0번 노드와 묶어 제거
                union(current.minNode, 0);
            }
        }
        // 답 출력
        System.out.print(sb);
    }

    // a와 b를 하나의 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);
        
        // 노드의 개수 비교하여 더 적은 쪽을 큰 쪽에 속하게 한다.
        // 최소 노드의 번호도 관리
        if (parents[pa][1] >= parents[pb][1]) {
            parents[pb][0] = pa;
            parents[pa][1] += parents[pb][1];
            parents[pa][2] = Math.min(parents[pa][2], parents[pb][2]);
        } else {
            parents[pa][0] = pb;
            parents[pb][1] += parents[pa][1];
            parents[pb][2] = Math.min(parents[pa][2], parents[pb][2]);
        }
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n][0] == n)
            return n;
        return parents[n][0] = findParent(parents[n][0]);
    }
}