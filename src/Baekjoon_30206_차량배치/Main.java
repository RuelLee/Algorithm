/*
 Author : Ruel
 Problem : Baekjoon 30206번 차량 배치
 Problem address : https://www.acmicpc.net/problem/30206
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30206_차량배치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n개의 지점, m개의 도로가 주어진다.
        // 각 지점에는 차량을 배치할 수 있고, 동시에 1번 지점으로 이동하여
        // 1번 지점에서 시가행진을 시작한다.
        // 각 차량들은 같은 지점에 두 대 이상의 차량이 배치되어서는 안되고
        // 이는 이동하는 도중에 적용된다.
        // 각 차량을 지점에 배치하는 경우의 수는?
        //
        // BFS 문제
        // 언제나 각 지점에 하나의 차량만 있기 위해서는
        // 그래프를 트리로 보고, 깊이를 따져 각 깊이에 한 대의 차만 배치해야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 지점, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 도로의 연결 상태
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            connections.get(b).add(a);
        }
        
        // 각 지점의 깊이
        int[] depths = new int[n + 1];
        // 1의 깊이는 1
        depths[1] = 1;
        // BFS를 통해 각 지점에 이르는 깊이를 찾는다.
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        // 가장 깊은 깊이
        int max = 1;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // current에서 이동 가능한 지점들을 살펴본다.
            for (int next : connections.get(current)) {
                // 미방문 노드일 경우
                if (depths[next] == 0) {
                    // current -> next를 통해 이동하는 것이 최소 깊이가 되고
                    // 이를 계산하고 큐에 추가
                    depths[next] = depths[current] + 1;
                    queue.offer(next);
                    // 최대 깊이 계산
                    max = Math.max(max, depths[next]);
                }
            }
        }

        // 각 깊이에 있는 노드의 개수를 센다.
        int[] counts = new int[max + 1];
        for (int depth : depths)
            counts[depth]++;

        long answer = 1;
        // 각 깊에 해당하는 지점들+ 1(배치하지 않는 경우)가 가능하다
        // 모든 경우를 곱해주고
        for (int i = 1; i < counts.length; i++) {
            answer *= counts[i] + 1;
            answer %= LIMIT;
        }

        // 최종적인 값에 모두 안 배치하는 경우 1을 빼준 값이 답
        System.out.println(answer - 1);
    }
}