/*
 Author : Ruel
 Problem : Baekjoon 32656번 트리의 루트를 찾아라
 Problem address : https://www.acmicpc.net/problem/32656
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32656_트리의루트를찾아라;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 노트, n-1개의 간선으로 이루어진 트리가 주어진다.
        // a와 b의 최소공통조상이 x일 때,
        // 가능한 트리의 루트 후보 개수를 출력하라
        //
        // 그래프 탐색 문제
        // a와 b의 최소 공통조상이 x이므로
        // x의 서브트리 중 a 혹은 b가 속한 서브 트리의 노드들은 루트 노드가 될 수 없다.
        // x의 자식 노드들을 별개로 탐색하며, 각 서브트리에 속한 노드의 개수를 구하고, 
        // a 혹은 b가 포함되었는지 여부를 계산하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드
        int n = Integer.parseInt(br.readLine());
        
        // n-1개의 간선 입력
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            connections.get(u).add(v);
            connections.get(v).add(u);
        }

        // LCA(a, b) = x
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        
        // 방문 체크
        boolean[] visited = new boolean[n + 1];
        // x엔 미리 방문 체크하여, x로 도달할 수 없게끔 한다.
        visited[x] = true;
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        int answer = n;
        // x의 자식 노드들
        for (int child : connections.get(x)) {
            // 서브 트리에 속한 노드의 개수
            int count = 0;
            queue.offer(child);
            boolean cannotBeRoot = false;
            // 각 노드들 방문하며 노드의 개수를 세고
            // a 혹은 b가 포함되는지 확인한다.
            while (!queue.isEmpty()) {
                int current = queue.poll();
                visited[current] = true;
                count++;
                if (current == a || current == b)
                    cannotBeRoot = true;

                for (int next : connections.get(current)) {
                    if (!visited[next])
                        queue.offer(next);
                }
            }

            // a 혹은 b가 속했다면
            // 해당 서브 트리의 노드 개수를 정답에서 제외시킨다.
            if (cannotBeRoot)
                answer -= count;
        }
        // 답 출력
        System.out.println(answer);
    }
}