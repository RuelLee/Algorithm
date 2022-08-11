/*
 Author : Ruel
 Problem : Baekjoon 2251번 물통
 Problem address : https://www.acmicpc.net/problem/2251
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2251_물통;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class State {
    int a;
    int b;
    int c;

    public State(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 부피가 a, b, c인 물통이 주어지고, c 물통엔 물이 가득차 있다.
        // 이 물들을 서로 쏟아부을 수 있는데, 이 때 a 물통이 비어있을 때, c 물통에 담겨있을 수 있는 물의 양을 모두 구하라.
        // 답은 오름차순 정렬한다.
        //
        // 완전 탐색 문제.
        // DFS, BFS가 있고, BFS로 문제를 해결했다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 각각 물통의 부피.
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 방문 체크
        boolean[][][] visited = new boolean[c + 1][c + 1][c + 1];
        // 처음에는 c물통만 가득 차 있다.
        // 방문 표시.
        visited[0][0][c] = true;
        // 답은 우선순위큐로 오름차순 정렬하자.
        PriorityQueue<Integer> answer = new PriorityQueue<>();
        // 초기 상태인 c도 답안 중 하나.
        answer.offer(c);
        // 큐를 통해 BFS 탐색한다.
        Queue<State> queue = new LinkedList<>();
        // 처음 상태엔 a, b, 물통은 비어있고, c 물통만 가득차 있는 상태.
        queue.offer(new State(0, 0, c));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            // 만약 a 물통에 물이 차 있다면
            if (current.a > 0) {
                // a 물통에서 b 물통으로 물을 쏟는 경우.
                // 현재 a물통의 물과 b의 남은 용량 중 작은 값을 선택.
                int water = Math.min(current.a, b - current.b);
                // 아직 계산한 적이 없는 물통들의 양이라면
                if (!visited[current.a - water][current.b + water][current.c]) {
                    // 큐에 추가.
                    queue.offer(new State(current.a - water, current.b + water, current.c));
                    // 방문 체크
                    visited[current.a - water][current.b + water][current.c] = true;
                    // 만약 위 행동으로 a물통의 물이 비고, c물통 물의 양이 아직 answer에 기록되지 않은 물의 양이라면 해당 답안 추가.
                    if(current.a - water == 0 && !answer.contains(current.c))
                        answer.offer(current.c);
                }
                // a 물통에서 c 물통으로 물을 쏟는 경우.
                water = Math.min(current.a, c - current.c);
                if (!visited[current.a - water][current.b][current.c + water]) {
                    queue.offer(new State(current.a - water, current.b, current.c + water));
                    visited[current.a - water][current.b][current.c + water] = true;
                    if (current.a - water == 0 && !answer.contains(current.c + water))
                        answer.offer(current.c + water);
                }
            }
            // b 물통에 물이 있을 때.
            if (current.b > 0) {
                // b물통에서 a물통으로 물을 쏟는 경우.
                int water = Math.min(current.b, a - current.a);
                if (!visited[current.a + water][current.b - water][current.c]) {
                    queue.offer(new State(current.a + water, current.b - water, current.c));
                    visited[current.a + water][current.b - water][current.c] = true;
                }
                // b물통에서 c물통으로 물을 쏟는 경우.
                water = Math.min(current.b, c - current.c);
                if (!visited[current.a][current.b - water][current.c + water]) {
                    queue.offer(new State(current.a, current.b - water, current.c + water));
                    visited[current.a][current.b - water][current.c + water] = true;
                    if (current.a == 0 && !answer.contains(current.c + water))
                        answer.offer(current.c + water);
                }
            }

            // c 물통에 물이 있을 때
            if (current.c > 0) {
                // c물통에서 a물통으로 물을 쏟는 경우.
                int water = Math.min(current.c, a - current.a);
                if (!visited[current.a + water][current.b][current.c - water]) {
                    queue.offer(new State(current.a + water, current.b, current.c - water));
                    visited[current.a + water][current.b][current.c - water] = true;
                }
                // c 물통에서 b물통으로 물을 쏟는 경우.
                water = Math.min(current.c, b - current.b);
                if (!visited[current.a][current.b + water][current.c - water]) {
                    queue.offer(new State(current.a, current.b + water, current.c - water));
                    visited[current.a][current.b + water][current.c - water] = true;
                    if (current.a == 0 && !answer.contains(current.c - water))
                        answer.offer(current.c - water);

                }
            }
        }
        // 모든 과정이 끝난 후, 우선순위큐에 담긴 답들을 차례대로 출력한다.
        StringBuilder sb = new StringBuilder();
        while (!answer.isEmpty())
            sb.append(answer.poll()).append(" ");
        System.out.println(sb);
    }
}