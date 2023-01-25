/*
 Author : Ruel
 Problem : Baekjoon 13265번 색칠하기
 Problem address : https://www.acmicpc.net/problem/13265
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13265_색칠하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 여러 동그라미와 두 동그라미를 연결하는 직선들이 주어진다.
        // 2개의 색을 가지고서 연결된 두 동그라미는 서로 다른 색을 칠하고자할 때
        // 가능 여부를 나타내라
        //
        // 그래프 탐색 문제
        // 두 동그라미 사이의 연결 상태를 따져 두 색을 번갈아가면서 칠해나간다
        // 그러다 두 동그라미에 같은 색이 할당되는 경우가 생긴다면 종료.
        // 해당 경우가 발생하지 않는다면 가능한 경우.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // 주어지는 동그라미와 연결 선
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            // 동그라미들이 서로 연결되어있음을 표시
            List<List<Integer>> links = new ArrayList<>(n + 1);
            for (int i = 0; i < n + 1; i++)
                links.add(new ArrayList<>());
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                links.get(a).add(b);
                links.get(b).add(a);
            }
            
            // 방문 체크
            boolean[] visited = new boolean[n + 1];
            // 색 표시
            int[] colors = new int[n + 1];
            // 연결된 동그라미들을 다른 색으로 칠할 수 있는지 여부
            boolean possible = true;
            for (int i = 1; i < visited.length; i++) {
                // 아직 방문하지 않은 동그라미를 만나면
                if (!visited[i]) {
                    // 큐에 해당 동그라미를 담고
                    Queue<Integer> queue = new LinkedList<>();
                    queue.offer(i);
                    // 방문 체크
                    visited[i] = true;
                    // BFS를 통해 탐색한다.
                    while (!queue.isEmpty()) {
                        // 현재 동그라미
                        int current = queue.poll();
                        // 와 연결된 다른 동그라미들 탐색
                        for (int next : links.get(current)) {
                            // 아직 방문하지 않은 동그라미라면
                            if (!visited[next]) {
                                // current와 다른 색을 칠하고
                                colors[next] = (colors[current] + 1) % 2;
                                // 큐 추가
                                queue.offer(next);
                                // 다시 또 큐에 담지 않기 위해 방문 체큰
                                visited[next] = true;
                            } else if (colors[next] == colors[current]) {       // 방문을 했는데, current와 같은 색인 경우
                                // 불가능한 경우이므로 possible에 false 값 대입.
                                possible = false;
                                // 종료
                                break;
                            }
                        }
                        // 불가능한 경우 BFS 탐색 종료
                        if (!possible)
                            break;
                    }
                }
                // 불가능한 경우에는 더 이상 동그라미를 살펴보는 것도 종료
                if (!possible)
                    break;
            }
            // 최종적으로 possible이 true 값 그대로 남았다면 가능한 경우.
            // 그렇지 않고 중간에 false로 바뀌었다면 불가능한 경우.
            // 결과값 출력.
            sb.append(possible ? "possible" : "impossible").append("\n");
        }
        // 전체 결과값 출력.
        System.out.print(sb);
    }
}