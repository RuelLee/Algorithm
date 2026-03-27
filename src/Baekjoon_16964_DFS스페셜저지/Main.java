/*
 Author : Ruel
 Problem : Baekjoon 16964번 DFS 스페셜 저지
 Problem address : https://www.acmicpc.net/problem/16964
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16964_DFS스페셜저지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<HashSet<Integer>> connections;
    static int[] order;

    public static void main(String[] args) throws IOException {
        // n개의 정점과 n-1개의 연결 정보가 주어진다. 트리 형태이다.
        // 루트 노드는 1이며, DFS 방문을 한 결과가 주어진다.
        // 올바르게 DFS 방문을 한 것인지 판별하라
        //
        // DFS 문제
        // 주어진 방문 순서를 보고 DFS 방문인지 판별하는 문제
        // 실제로 DFS 방문을 하면서, 해당하는 정점들을 만나는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 연결 정보
        connections = new ArrayList<>();
        // 해쉬셋으로 처리하여 O(1)로 연결 여부 판단.
        for (int i = 0; i <= n; i++)
            connections.add(new HashSet<>());

        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            connections.get(x).add(y);
            connections.get(y).add(x);
        }

        // 방문 순서
        order = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            order[i] = Integer.parseInt(st.nextToken());

        // 첫번째 노드는 1이어야하며, 마지막 순서까지 모두 방문했다면 1
        // 그 외의 경우 0 출력
        System.out.println(order[0] == 1 && isDFS(0, new boolean[n + 1]) == n ? 1 : 0);
    }

    static int isDFS(int idx, boolean[] visited) {
        // 마지막 까지 방문한 경우, 해당 번호를 반환
        if (idx == order.length)
            return idx;

        // 방문 체크
        visited[order[idx]] = true;
        // 다음 방문 순서
        int next = idx + 1;
        // 아직 방문할 노드가 남아있고, 미방문이며,
        // order[current]와 order[next]가 연결되어있는 경우
        while (next < order.length && !visited[order[next]] && connections.get(order[idx]).contains(order[next]))
            next = isDFS(next, visited);        // next로 방문하여 진행한 경우, 다음 노드의 순서를 받아와 next에 갱신

        // 현재 노드에서 방문 가능한 모든 노드를 방문.
        // 다음 방문해야할 순서를 반환.
        return next;
    }
}