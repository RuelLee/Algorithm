/*
 Author : Ruel
 Problem : Baekjoon 2310번 어드벤처 게임
 Problem address : https://www.acmicpc.net/problem/2310
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2310_어드벤처게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1번 방에서 시작하여 n번 방으로 가고자 한다.
        // 각 방은 E 비어있거나, L 레프리콘이 소지금을 지정 금액까지 채워주거나, T 트롤이 지정 금액만큼 통행룔르 받는다.
        // 각 방의 상태와 연결된 다음 방에 대한 정보들이 주어질 때
        // 모험가는 n번 방에 도달할 수 있는가?
        //
        // 그래프 탐색 문제
        // 1번 방에서 시작하여 n번 방까지 소지금을 0이상으로 유지하며 도달할 수 있는지
        // DFS 혹은 BFS를 통해 탐색한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        // 여러 테스트케이스가 주어진다.
        while (true) {
            // 방이 0개라면 종료
            int n = Integer.parseInt(br.readLine());
            if (n == 0)
                break;
            
            // 방들의 연결 상태
            List<List<Integer>> connections = new ArrayList<>();
            for (int i = 0; i < n + 1; i++)
                connections.add(new ArrayList<>());
            // 1번 방 또한 레프리콘 혹은 트롤이 있을 수 있다.
            // 따라서 0번 방에서 시작하여 1번방으로 거쳐가도록 한다.
            connections.get(0).add(1);
            // 각 방의 상태
            int[] rooms = new int[n + 1];
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                // 트롤 일 경우, * -1
                // 빈 방이거나 레프리콘일 경우, 입력된 값을 그대로 넣는다.
                char type = st.nextToken().charAt(0);
                int value = Integer.parseInt(st.nextToken());
                rooms[i + 1] = (type == 'T' ? value * -1 : value);

                // 다음 방 연결 상태
                while (true) {
                    int next = Integer.parseInt(st.nextToken());
                    if (next == 0)
                        break;
                    connections.get(i + 1).add(next);
                }
            }
            
            // 각 방에 도달하는 최대 소지금
            int[] maxCash = new int[n + 1];
            // 0이여도 통과가 가능하므로
            // -1로 초기화
            Arrays.fill(maxCash, -1);
            // 처음엔 소지금 0
            maxCash[0] = 0;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(0);
            while (!queue.isEmpty()) {
                int current = queue.poll();

                // 다음 방을 살펴본다.
                for (int next : connections.get(current)) {
                    // 다음 방이 트롤일 경우, 현재 소지금에서 통행료만큼을 지불해야하고
                    // 레프리콘일 경우, 현재 소지금과 레프리콘이 채워주는 금액을 확인하고 더 많은 금액이 남는다.
                    int nextCash = (rooms[next] < 0 ? maxCash[current] + rooms[next] : Math.max(maxCash[current], rooms[next]));
                    // 만약 다음 방에 도달하는 소지금이 최대값으로 갱신된다면
                    if (maxCash[next] < nextCash) {
                        // 값 갱신 후, 큐 추가
                        maxCash[next] = nextCash;
                        queue.offer(next);
                    }
                }
            }
            // n번 방에 소지금 0원 이상으로 도달하는 것이 가능한지 살펴보고
            // 답안 기록
            sb.append(maxCash[n] < 0 ? "No" : "Yes").append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}