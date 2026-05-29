/*
 Author : Ruel
 Problem : Jungol 1515번 구슬 찾기
 Problem address : https://jungol.co.kr/problem/1515
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1515_구슬찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 구슬 n개의 무게 관계가 m개 주어진다.
        // 주어진 관계를 바탕으로 절대 중간 무게가 될 수 없는 구슬의 개수를 구하라
        //
        // 그래프 탐색 문제
        // BFS를 통해 자신보다 가벼운 구슬 개수, 무거운 구슬의 개수를 각각 구한다.
        // 그 개수가 (n + 1) / 2개보다 같거나 크다면 그 구슬은 절대 중간 순위가 될 수 없다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 구슬, m개의 상관관계
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 관계 정리
        List<List<List<Integer>>> connections = new ArrayList<>();
        connections.add(new ArrayList<>());
        connections.add(new ArrayList<>());
        for (int i = 0; i <= n; i++) {
            connections.get(0).add(new ArrayList<>());
            connections.get(1).add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            connections.get(0).get(x).add(y);
            connections.get(1).get(y).add(x);
        }

        // BFS를 통해 계산한다.
        // 정답에 해당하는 구슬의 개수
        int answer = 0;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        // 모든 구슬에 대해
        for (int i = 1; i <= n; i++) {
            boolean found = false;
            // 각 구슬의 가벼운 관계, 무거운 관계를 각각 계산
            for (int j = 0; j < connections.size() && !found; j++) {
                queue.clear();
                Arrays.fill(visited, false);
                queue.offer(i);
                // i번 구슬부터 시작하여
                // 자신보다 무겁거나 가벼운 구슬의 개수를 모두 구한다.
                visited[i] = true;
                int cnt = 1;
                while (!queue.isEmpty()) {
                    int current = queue.poll();

                    for (int next : connections.get(j).get(current)) {
                        if (!visited[next]) {
                            visited[next] = true;
                            cnt++;
                            queue.offer(next);
                        }
                    }
                }
                // 그 개수가 절반 이상이라면 절대 중간 순위의 구슬이 될 수 없다.
                if (cnt > (n + 1) / 2)
                    found = true;
            }
            if (found)
                answer++;
        }
        // 답 출력
        System.out.println(answer);
    }
}