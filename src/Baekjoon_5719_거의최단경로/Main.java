/*
 Author : Ruel
 Problem : Baekjoon 5719번 거의 최단 경로
 Problem address : https://www.acmicpc.net/problem/5719
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5719_거의최단경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {
    static int[][] adjMatrix;
    static List<List<Integer>> pi;

    public static void main(String[] args) throws IOException {
        // n개의 장소, m개의 도로, 시작점 s, 도착점 d가 주어진다
        // s에서 d로 가는데 최소 거리 경로에 포함되는 도로들은 이용하지 않고서 d점에 도달하려한다
        // 이 때 경로의 길이를 구하라.
        // 단순하게 다익스트라를 한번 돌려 최소 거리 경로를 구하고, 해당 도로들을 폐쇄하고 다시 다익스트라를 돌릴려고 했다
        // 하지만 최소 거리로 도착점에 도달하는 경로들이 여러개 있을 수가 있고, 또한 이러한 경로들이 이용하는 도로들이 중복될 수도 있다
        // 이러한 점들을 고려해서, pi를 배열로 이용하는 것이 아니라, 리스트로 해당 지점에 최단 거리로 도달하는 여러 개의 경로를 저장하는 방법으로 했다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (true) {      // 테스트케이스는 여러개
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());       // 장소의 개수
            int m = Integer.parseInt(st.nextToken());       // 도로의 개수
            if (n == 0 && m == 0)
                break;

            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());       // 시작점
            int d = Integer.parseInt(st.nextToken());       // 도착점

            adjMatrix = new int[n][n];
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                adjMatrix[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = Integer.parseInt(st.nextToken());
            }
            pi = new ArrayList<>();     // 어떤 지점에 최소 거리로 도달하려면, pi 리스트 안에 있는 지점들 중 하나에서 해당 지점으로 오면 된다.
            for (int i = 0; i < n; i++)
                pi.add(new ArrayList<>());

            if (dijkstra(n, s, d) == Integer.MAX_VALUE)     // s에서 d로 도달하는 경로 자체가 없다면 -1 출력
                sb.append(-1).append("\n");
            else {      // 도달하는 경로가 있다면
                Queue<Integer> queue = new LinkedList<>();
                boolean[] visited = new boolean[n];
                queue.offer(d);     // d지점부터 역으로 추적해나간다.
                while (!queue.isEmpty()) {
                    int current = queue.poll();     // current에 최소 거리로 도달하기 위해서는
                    for (int pre : pi.get(current)) {       // pre -> current 경로를 이용하면 된다.
                        adjMatrix[pre][current] = 0;        // 해당 경로를 폐쇄하고,
                        if (visited[pre])       // 다른 경로들에서 pre지점으로 돌아가는 경우를 이미 계산했다면 건너뛴다.
                            continue;
                        queue.offer(pre);       // 그렇지 않다면 다음엔 pre지점에서 위와 같은 연산을 할 것이다.
                        visited[pre] = true;        // pre에 대해서 방문 체크
                    }
                }
                // 위 순서가 끝났다면 최소 거리 경로들에 대한 도로들의 폐쇄가 끝났다.
                // 다시 한번 다익스트라를 돌려주자.
                int ans = dijkstra(n, s, d);
                // ans가 초기값이라면 경로가 없는 경우. -1 출력
                // 그렇지 않다면 ans 값을 출력해주자.
                sb.append(ans == Integer.MAX_VALUE ? -1 : ans).append("\n");
            }

        }
        System.out.println(sb);
    }

    static int dijkstra(int n, int s, int d) {      // 다익스트라 알고리즘
        int[] distances = new int[n];       // 각 지점에 도달하는 최소 거리.
        boolean[] visited = new boolean[n];     // 방문 체크
        Arrays.fill(distances, Integer.MAX_VALUE);      // 최소 거리는 최대값으로 세팅
        distances[s] = 0;       // 시작점의 거리는 0
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> distances[o]));     // 각 지점에 도달하는 거리가 적은 순으로 살펴본다.
        priorityQueue.offer(s);     // 시작점 삽입.
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();     // current에서 도로들을 살펴본다.
            if (current == d)       // current가 도착점이라면 그대로 종료.
                break;

            for (int i = 0; i < adjMatrix[current].length; i++) {
                // i점에 아직 방문한 적이 없고, current - > i로의 경로가 있고, 최소 거리가 같거나 더 작은 값으로 갱신된다면
                if (!visited[i] && adjMatrix[current][i] != 0 && distances[i] >= distances[current] + adjMatrix[current][i]) {
                    if (distances[i] > distances[current] + adjMatrix[current][i])      // 최소 거리가 갱신되는 상황이라면
                        pi.get(i).clear();      // pi.get(i) 리스트를 비워주자.
                    pi.get(i).add(current);     // i에 최소 거리로 도달하는 이전 지점을 current로 추가해주자.
                    distances[i] = distances[current] + adjMatrix[current][i];      // i점에 최소 거리 값 갱신.
                    priorityQueue.remove(i);        // 우선순위큐에서 i값을 지우고 다시 넣음으로 혹시 이전에 값이 들어가 순서가 정해져있는 상황에 대해서 보정해주도록 하자.
                    priorityQueue.offer(i);
                }
            }
            // 방문 체크
            visited[current] = true;
        }
        // 최종적으로 d에 도달하는 최소 거리를 리턴한다.
        return distances[d];
    }
}