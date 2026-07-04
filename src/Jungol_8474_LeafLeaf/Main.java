/*
 Author : Ruel
 Problem : Jungol 8474번 Leaf Leaf
 Problem address : https://jungol.co.kr/problem/8474
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8474_LeafLeaf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connections;

    public static void main(String[] args) throws IOException {
        // n개의 노드를 가진 트리가 주어진다.
        // 단말 노드에서 다른 단말 노드까지 이르는 거리 중 최소값을 찾고 그 값고 두 단말노드를 출력하라
        // 그러한 경우가 여러 개라면 아무 거나 하나 출력한다
        //
        // 그래프 탐색 문제
        // 모든 단말 노드를 큐에 넣고, 탐색을 시작한다.
        // 탐색할 때, 방문하는 노드는, 탐색이 시작된 단말 노드와 해당 단말 노드로부터의 거리를 기록해나간다.
        // 그러다 한 노드에서 두 단말 노드의 탐색이 겹치는 경우. 그 때의 거리와 탐색이 시작된 두 단말노드를 기록한다.
        // 이미 다른 단말 노드의 탐색이 진행된 노드는 다시 탐색하지 않는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드
        int n = Integer.parseInt(br.readLine());
        connections = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            connections.add(new ArrayList<>());

        // 트리 간선 정보
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            connections.get(x).add(y);
            connections.get(y).add(x);
        }

        // 단말 노드들을 큐에 담는다.
        Queue<Integer> queue = new LinkedList<>();
        // 가장 가까운 단말 노드의 번호와 거리
        int[][] distances = new int[n + 1][2];
        for (int i = 1; i <= n; i++) {
            if (connections.get(i).size() == 1) {
                queue.add(i);
                distances[i][0] = i;
                distances[i][1] = 0;
            }
        }

        // BFS
        int[] answer = new int[3];
        answer[0] = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // current와 연결도니 노드들 중
            for (int next : connections.get(current)) {
                // 아직 미탐색인 경우
                if (distances[next][0] == 0) {
                    // 탐색 기록을 기록하고 큐에 추가
                    distances[next][0] = distances[current][0];
                    distances[next][1] = distances[current][1] + 1;
                    queue.offer(next);
                } else if (distances[next][0] != distances[current][0] && answer[0] > distances[next][1] + distances[current][1] + 1) {
                    // 탐색이 이미 됐고, 거리 합이 최소인 경우
                    // 답 기록
                    answer[0] = distances[next][1] + distances[current][1] + 1;
                    answer[1] = distances[next][0];
                    answer[2] = distances[current][0];
                    // 이미 다른 단말노드로부터 탐색이 진행된 노드이기 때문에, 추가적으로 탐색을 하지는 않는다.
                }
            }
        }
        // 답 출력
        System.out.println(answer[0] + "\n" + answer[1] + " " + answer[2]);
    }
}