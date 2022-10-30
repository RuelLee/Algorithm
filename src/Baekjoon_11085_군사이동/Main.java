/*
 Author : Ruel
 Problem : Baekjoon 11085번 군사 이동
 Problem address : https://www.acmicpc.net/problem/11085
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11085_군사이동;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // p개의 지점과 w개의 도로, 시작점 c, 도착점 v가 주어진다.
        // 각 도로에는 너비가 있으며, 시작점에서 도착점까지 거치는 도로들 중
        // 최소 너비가 가장 넓도록 경로를 짜고 싶다.
        // 이 때 경로에서 최소 너비를 갖는 도로의 너비는?
        //
        // 그래프 탐색 문제. 유사 다익스트라..?
        // 시작점에서 도로들을 선택하며 다음 지점에 도달하는데 최대 너비 도로들을 선택하고 해당 값을 기록해준다
        // 이러한 연산을 도착지점까지 반복하며 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int p = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int c = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());

        // 간선 정보.
        int[][] adjMatrix = new int[p][p];
        for (int i = 0; i < w; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int width = Integer.parseInt(st.nextToken());

            adjMatrix[start][end] = adjMatrix[end][start] = Math.max(adjMatrix[start][end], width);
        }

        // 각 지점에 도달하는 최대 너비 경로에서 최소 도로의 너비
        int[] maxWidths = new int[p];
        // 시작점 초기값
        maxWidths[c] = Integer.MAX_VALUE;
        // 우선순위큐를 통해 각 지점에 도달하는 도달하는 경로들 중 최소 도로 너비가 가장 큰 것을 우선적으로 계산한다.
        // 낮은 값으로 인한 중복 계산을 줄일 수 있다.
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2[1], o1[1]));
        priorityQueue.offer(new int[]{c, Integer.MAX_VALUE});
        while (!priorityQueue.isEmpty()) {
            // 현재 지점
            int current = priorityQueue.peek()[0];
            // current에 도달하는 최대 너비 경로에서 도로의 최소 너비.
            int width = priorityQueue.poll()[1];
            // 더 큰 값으로 이미 계산된 경우 건너 뛴다.
            if (maxWidths[current] > width)
                continue;

            // current에서 연결된 지점들 중 currnet -> next의 도로로
            for (int i = 0; i < adjMatrix[current].length; i++) {
                if (current == i || adjMatrix[current][i] == 0)
                    continue;

                // 도로의 최소 너비값이 갱신되는 경우
                // 값 갱신 후, 우선순위큐에 삽입.
                if (maxWidths[i] < Math.min(width, adjMatrix[current][i])) {
                    maxWidths[i] = Math.min(width, adjMatrix[current][i]);
                    priorityQueue.offer(new int[]{i, maxWidths[i]});
                }
            }
        }
        // v 지점에 도달하는 최대 너비 경로에서 도로의 최소 너비를 출력.
        System.out.println(maxWidths[v]);
    }
}