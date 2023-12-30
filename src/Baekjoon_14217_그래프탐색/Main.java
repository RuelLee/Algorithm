/*
 Author : Ruel
 Problem : Baekjoon 14217번 그래프 탐색
 Problem address : https://www.acmicpc.net/problem/14217
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14217_그래프탐색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시, m개의 도로가 주어진다.
        // 그 후, 도로 정비 계획을 통해, 도로를 새로 만들거나 없앤다.
        // 도로를 정비할 때마다 수도(1번 도시)에서 각 도시에 이를 때까지
        // 방문해야하는 도시의 수를 출력하라
        //
        // 그래프 탐색, BFS 문제
        // 단순한 BFS 탐색 문제.
        // 도로가 추가되거나 제가될 때마다 반복해주기만 하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 도로로 연결된 두 도시
        boolean[][] connected = new boolean[n + 1][n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connected[a][b] = connected[b][a] = true;
        }

        // 도로 정비 횟수.
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // b, c도로를 a가 1일 때는 연결, 2일 때는 제거한다.
            connected[b][c] = connected[c][b] = (a == 1);
            // 각 도시에 이르는 최소 도시 방문 수
            int[] distances = distancesFromCapital(connected);
            // 1번 도시부터 작성.
            // 만약 큰 값(Integer.MAX_VALUE)이 들어있다면 방문할 수 없는 경우. -1 기록
            for (int j = 1; j < distances.length; j++)
                sb.append(distances[j] == Integer.MAX_VALUE ? -1 : distances[j]).append(" ");
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // BFS를 통해 각 도시에 이르는 최소 도시 방문 횟수를 계산한다.
    static int[] distancesFromCapital(boolean[][] connected) {
        // 각 도시에 이르는 방문 횟수
        int[] distances = new int[connected.length];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[1] = 0;
        // BFS 탐색. 1에서 시작
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int i = 1; i < connected[current].length; i++) {
                // 방문할 수 있으며, 최소 방문 횟수를 갱신한다면 큐에 추가.
                if (connected[current][i] && distances[i] > distances[current] + 1) {
                    distances[i] = distances[current] + 1;
                    queue.offer(i);
                }
            }
        }
        // 계산한 각 도시들의 최소 방문 횟수 반환.
        return distances;
    }
}