/*
 Author : Ruel
 Problem : Baekjoon 22856번 트리 순회
 Problem address : https://www.acmicpc.net/problem/22856
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22856_트리순회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] nodes;

    public static void main(String[] args) throws IOException {
        // n개의 노드가 있는 트리가 주어진다.
        // 루트 노드는 1번이며, 루트 노드에서 시작한다.
        // 1. 왼쪽 자식 노드가 존재하며 방문하지 않았다면, 왼쪽 자식 노드로 이동한다.
        // 2. 그렇지 않고 오른쪽 자식 노드가 존재하며 방문하지 않았다면, 오른쪽 자식 노드로 이동한다.
        // 3. 현재 노드가 유사 중위 순회의 끝이라면 순회를 종료한다.
        // 4. 그렇지 않고, 부모 노드가 존재한다면 부모 노드로 이동한다.
        // 5. 유사 중위 순회를 종료할 때까지 1 ~ 4를 반복한다.
        // 총 이동 횟수를 출력한다.
        //
        // 트리, BFS 문제
        // 이동 횟수를 현재까지 확정된 이동 횟수와 부모 노드로 돌아가는 이동 횟수를 구분하여 계산한다.
        // 부모 노드로 돌아가는 경우, 해당 이동 횟수가 유사 중위 순회의 마지막이라 반영되지 않을 가능성이 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드
        int n = Integer.parseInt(br.readLine());
        nodes = new int[n + 1][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            for (int j = 0; j < nodes[i].length; j++)
                nodes[node][j] = Integer.parseInt(st.nextToken());
        }
        
        // 이동 횟수
        int[] moves = new int[2];
        bfs(1, moves);
        // 확정된 이동횟수 moves[0]만 출력
        System.out.println(moves[0]);
    }
    
    // bfs 탐색
    static void bfs(int node, int[] moves) {
        // 왼쪽 자식 노드가 있는 경우
        if (nodes[node][0] != -1) {
            // 자식 노드로 이동.
            moves[0]++;
            bfs(nodes[node][0], moves);
        }
        // 유사 중위 순회이므로 왼쪽 자식 노드로 간 경우
        // 반드시 해당 노드로 돌아와야한다.
        // 이 때의 이동 횟수 반영.
        moves[0] += moves[1];
        moves[1] = 0;

        // 오른쪽 자식 노드가 있는 경우.
        if (nodes[node][1] != -1) {
            // 해당 노드로 이동.
            moves[0]++;
            bfs(nodes[node][1], moves);
        }
        // 오른쪽 자식 노드를 방문하고서, 상위 노드로 이동하는 경우는
        // 이미 유사 중위 순회의 끝을 방문했다면 반영되지 않을 가능성도 있다.
        // 해당 이동은 moves[1]에 누적.
        moves[1]++;
    }
}