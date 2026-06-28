/*
 Author : Ruel
 Problem : Jungol 3510번 광고 뿌리기
 Problem address : https://jungol.co.kr/problem/3510
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3510_광고뿌리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connections;

    public static void main(String[] args) throws IOException {
        // n개의 노드와 학원의 위치s가 주어진다. 노드들을 트리 형태로 연결되어있고, n-1개의 연결이 주어진다.
        // 학원에서 모든 노드에 전단지를 돌리고 돌아오려고 한다.
        // 힘이 좋기 때문에, 거리가 d이하로 떨어진 노드는 직접 방문하지 않고 전단지를 던져 돌릴수 있다.
        // 모든 노드에 전단지를 돌리기 위해 이동해야하는 거리는?
        //
        // 트리, BFS, 우선순위큐 문제
        // 먼저, s를 루트 노드로 보고, 깊이를 계산해나간다.
        // 그리고 우선순위큐를 통해, 깊이가 깊은 노드부터, 자신보다 깊이가 낮고 인접한 노드에 대해
        // 자신보다 거리를 +1하여 단말 노드로부터의 거리를 모든 노드에 대해 계산한다.
        // 그리고, s를 제외한 단말 노드로부터 거리가 d이상인 모든 노드를 방문하고 돌아와야하므로
        // 그 개수를 세어 x2 해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드, 학원의 위치 s, 전단지를 던질 수 있는 거리 d
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        // 노드들의 연결 상태
        connections = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            connections.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            connections.get(x).add(y);
            connections.get(y).add(x);
        }

        // s를 루트 노드로 보고, 각 노드의 깊이를 계산한다.
        int[] depths = new int[n + 1];
        Arrays.fill(depths, Integer.MAX_VALUE);
        depths[s] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(depths[o2], depths[o1]));
        while (!queue.isEmpty()) {
            int current = queue.poll();
            pq.offer(current);

            for (int next : connections.get(current)) {
                if (depths[next] == Integer.MAX_VALUE) {
                    depths[next] = depths[current] + 1;
                    queue.offer(next);
                }
            }
        }

        // 깊이가 깊은 순서대로 단말 노드로부터의 거리를 계산한다.
        int[] distancesFromTerminal = new int[n + 1];
        while (!pq.isEmpty()) {
            int current = pq.poll();

            // 인접한 노드 중, 자신보다 깊이가 낮은 경우
            // 자신의 거리 + 1한 값이 next의 최대거리가 되는지 확인.
            for (int next : connections.get(current)) {
                if (depths[next] < depths[current])
                    distancesFromTerminal[next] = Math.max(distancesFromTerminal[next], distancesFromTerminal[current] + 1);
            }
        }

        // s를 제외한 단말 노드로부터 거리가 d이상인 모든 노드의 수를 센다.
        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            if (distancesFromTerminal[i] >= d)
                cnt++;
        }
        if (distancesFromTerminal[s] >= d)
            cnt--;

        // x2한 값이 답
        System.out.println(cnt * 2);
    }
}