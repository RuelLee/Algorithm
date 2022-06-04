/*
 Author : Ruel
 Problem : Baekjoon 11437번 LCA
 Problem address : https://www.acmicpc.net/problem/11437
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11437_LCA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> child;
    static int[] depth;
    static int[][] sparseArray;

    public static void main(String[] args) throws IOException {
        // n개의 정점으로 이루어진 트리가 주어진다. 루트는 1번.
        // 두 노드의 쌍이 주어질 때 가장 가까운 공통 조상이 몇 번인지 출력하라.
        //
        // 최소 공통 조상 문제
        // n이 최대 5만개, 쿼리가 최대 1만개 주어지므로, 희소 배열을 사용한다.
        // 희소 배열을 최소 공통 조상에 이용하면
        // 자신의 2^n번째 조상들을 희소배열에 저장한다.
        // 그 후 두 노드가 주어지면, 희소배열을 통해 두 노드의 깊이를 빠르게 맞춰주고
        // 그 후 같은 조상을 발견할 때까지 거슬러 올라간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        child = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            child.get(a).add(b);
            child.get(b).add(a);
        }

        // n개의 노드의 깊이를 저장.
        depth = new int[n + 1];
        // 1번 노드는 1
        depth[1] = 1;
        // 각 노드의 조상들을 저장할 희소 배열 공간.
        sparseArray = new int[n + 1][(int) Math.ceil(Math.log(n) / Math.log(2))];
        // 큐를 통해 차근차근 방문하며 희소배열을 채워나간다.
        Queue<Integer> queue = new LinkedList<>();
        // 1번부터 시작.
        queue.offer(1);
        // 방문 체크 배열
        boolean[] visited = new boolean[n + 1];
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // current 방문 체크
            visited[current] = true;
            // 그리고 자신의 깊이와 채워진 희소배열을 토대로 자신의 희소배열을 채워나간다.
            for (int i = 1; i < Math.ceil(Math.log(depth[current]) / Math.log(2)); i++)
                sparseArray[current][i] = sparseArray[sparseArray[current][i - 1]][i - 1];

            // currnet의 자식 노드들 탐색.
            for (int next : child.get(current)) {
                // 방문했다면(=조상노드라면) 건너뛴다.
                if (visited[next])
                    continue;

                // next의 2^0번 조상(=부모노드)는 currnet
                sparseArray[next][0] = current;
                // 깊이는 depth[current] + 1
                depth[next] = depth[current] + 1;
                // 큐에 next를 담아 다음번에 탐색한다.
                queue.offer(next);
            }
        }

        // m개의 쿼리.
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            sb.append(findLCA(a, b)).append("\n");
        }
        System.out.print(sb);
    }

    // 두 노드의 최소 공통 조상 찾기.
    static int findLCA(int a, int b) {
        // 두 노드의 깊이가 서로 다르다면
        while (depth[a] != depth[b]) {
            // 차이에 log2를 취해 희소배열을 통해 최대 몇 개의 조상을 건너뛸 수 있는지 계산한다.
            int diff = (int) (Math.log(Math.abs(depth[a] - depth[b])) / Math.log(2));
            // 그리고 해당만큼 깊이가 더 깊은 쪽을 건너뛰어준다.
            if (depth[a] > depth[b])
                a = sparseArray[a][diff];
            else
                b = sparseArray[b][diff];
        }

        // 깊이는 같아진 상태
        // 이제 하나씩 거슬러 올라가며 같은 조상인지 확인한다.
        while (a != b) {
            a = sparseArray[a][0];
            b = sparseArray[b][0];
        }
        // a == b인 시점에 위 while문이 끝나고, a(= b) 값이 최소 공통 조상.
        return a;
    }
}