/*
 Author : Ruel
 Problem : Baekjoon 1865번 웜홀
 Problem address : https://www.acmicpc.net/problem/1865
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1865_웜홀;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] adjMatrix;
    static int MAX = 501 * 10000;

    public static void main(String[] args) throws IOException {
        // n개의 지점과 m개의 도로, w개의 웜홀이 주어진다
        // m개의 도로는 s시작지점, e도착지점, t소요시간으로 주어지고 양방향으로 이동이 가능하다
        // w개의 웜홀은 s시작지점, e도착지점, t감소하는시간으로 주어지고 단방향 이동이 가능하다.(웜홀로 이동할 경우, 시간이 t만큼 되돌아간다)
        // 어느 시작 지점에서 다시 시작 지점으로 돌아왔을 때, 처음 시작 시간보다 더 과거로 돌아가는 경우가 있는지 알고 싶다
        // 그러는 경우가 있는 경우, YES 아니라면 NO를 출력한다.
        //
        // 음의 가중치가 있는 최소 거리 찾기 -> bellman-ford 알고리즘
        // 이보다 시간의 이점을 좀 더 챙겨갈 수 있는 SPFA이 있다고 해서 해당 알고리즘으로 풀어보았다
        // 벨만 포드일 경우, 모든 경로를 n번 체크하지만
        // SPFA의 경우, 시작점을 정하고 해당 지점으로부터 연결되어있는 도로들을 따라가며 최소 시간을 갱신해나간다
        // 만약 음의 가중치를 갖고 무한히 감소하는 사이클이 생길 경우, 어느 한 지점을 n회 이상 방문할 것이기 때문에,
        // 각 지점의 방문 횟수를 따로 기록하고 n이 될 경우 종료시킨다.
        // 느낌 상으로는 벨만 포드 알고리즘과 다익스트라를 섞은 듯한 느낌이 난다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int te = 0; te < testCase; te++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            adjMatrix = new int[n + 1][n + 1];
            for (int[] am : adjMatrix)
                Arrays.fill(am, MAX);

            // 도로에 대한 입력.
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                int t = Integer.parseInt(st.nextToken());
                adjMatrix[s][e] = (adjMatrix[s][e] > t ? (adjMatrix[e][s] = t) : adjMatrix[s][e]);
            }
            // 웜홀에 대한 입력.
            for (int i = 0; i < w; i++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                int t = -Integer.parseInt(st.nextToken());
                adjMatrix[s][e] = Math.min(adjMatrix[s][e], t);
            }

            // 방문 횟수 체크
            int[] visited = new int[n + 1];
            // 최소 시간 체크
            int[] minDistance = new int[n + 1];
            Arrays.fill(minDistance, MAX);
            // 음의 사이클 존재 여부 체크
            boolean possible = false;
            // 서로 연결되지 않은 사이클들이 있을 수 있다.
            // 따라서 모든 지점에 대해서 시작점으로 설정하고 사이클 여부를 확인을 해봐야한다.
            // 1번부터 차례대로 시작지점으로 설정하되, 이전에 방문한 적이 있다면, 음의 사이클이 발생하지 않은 지점이므로 건너뛰어도 된다.
            for (int i = 1; i < n + 1; i++) {
                // 음의 사이클을 찾은 경우.
                // 연산 종료
                if(possible)
                    break;
                // 다른 지점에서 i로 오는 게 가능한 경우.
                // 건너 뛴다.
                if (visited[i] > 0)
                    continue;

                Queue<Integer> queue = new LinkedList<>();
                // i지점을 시작 지점으로
                minDistance[i] = 0;
                queue.offer(i);
                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    // 방문 횟수 +1
                    // n번 이상 방문했다면 음의 사이클이 존재.
                    // possible에 true로 남겨주고 종료.
                    if (++visited[current] > n) {
                        possible = true;
                        break;
                    }

                    // 그렇지 않다면, 연결된 도로들을 살펴보며
                    // 최소 시간이 갱신되는지 확인한다.
                    for (int j = 1; j < adjMatrix[current].length; j++) {
                        if (minDistance[j] > minDistance[current] + adjMatrix[current][j]) {
                            minDistance[j] = minDistance[current] + adjMatrix[current][j];
                            queue.remove(j);
                            queue.offer(j);
                        }
                    }
                }
            }
            sb.append(possible ? "YES" : "NO").append("\n");
        }
        System.out.print(sb);
    }
}