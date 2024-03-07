/*
 Author : Ruel
 Problem : Baekjoon 30893번 트리 게임
 Problem address : https://www.acmicpc.net/problem/30893
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30893_트리게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> child;

    public static void main(String[] args) throws IOException {
        // 트리 형태가 주어진다.
        // s 위치에 말이 놓여있는데 이를 두 명이 번갈아가며 움직인다.
        // 한 번 방문했던 정점으로는 이동할 수 없으며, 말을 더 이상 움직일 수 없다면 게임이 종료된다.
        // 한번이라도 e를 경유했다면 선공의 승리, 그렇지 않다면 후공의 승리이다.
        // 두 사람이 서로 최선의 전략으로 게임을 진행할 때
        // 선후공 중 어떤 것을 잡는 것이 유리한가?
        //
        // DFS, 게임이론 문제
        // 더 이상 자식 노드가 없는 경우, 후공이 승리
        // e 노드를 만난 경우, 선공의 승리임을 유의하며 문제를 푼다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드, 시작 지점 s, 선공이 반드시 이기는 지점 e
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        // 자식 노드를 추가한다.
        child = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            child.add(new LinkedList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            child.get(u).add(v);
            child.get(v).add(u);
        }
        
        // 부모 노드와 혼재되어있으므로
        // 각 리스트에서 부모 노드는 제거
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : child.get(current)) {
                queue.offer(next);
                child.get(next).remove(Integer.valueOf(current));
            }
        }
        
        // 처음이 선공이 유리하다면 0,
        // 후공이 유리하다면 1
        int[] win = new int[n + 1];
        Arrays.fill(win, -1);
        findAnswer(s, 0, e, win);
        // s지점에서 선공이 유리하다면 First 출력, 그렇지 않다면 Second 출력
        System.out.println(win[s] == 0 ? "First" : "Second");
    }
    
    // 백트래킹, 메모이제이션
    // 각 node에 방문할 때, 선공이 도달하는지, 후공이 도달하는지도 중요
    static int findAnswer(int node, int turn, int e, int[] win) {
        // 이미 계산한 결과가 있다면 반환
        if (win[node] >= 0)
            return win[node];

        // 만약 e 지점이라면 선공이 이기게 된다.
        if (node == e)
            return win[node] = 0;
        // 만약 node에서 더 이상 진행할 수 없다면 후공이 이긴다.
        if (child.get(node).isEmpty())
            return win[node] = 1;

        // node에서 turn이 이길 수 있는 방법이 있는지 찾는다.
        win[node] = (turn + 1) % 2;
        // 자식 노드들
        for (int next : child.get(node)) {
            // 자식 노드들에 다음 (turn + 1) % 2 차례로 게임을 넘겼을 때
            // turn이 이기는 경우가 있는지 찾는다.
            // 그렇다면 node는 turn이 이길 수 있다.
            if (findAnswer(next, (turn + 1) % 2, e, win) == turn) {
                win[node] = turn;
                break;
            }
        }
        // 결과값 반환.
        return win[node];
    }
}