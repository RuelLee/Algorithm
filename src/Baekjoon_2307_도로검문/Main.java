/*
 Author : Ruel
 Problem : Baekjoon 2307번 도로검문
 Problem address : https://www.acmicpc.net/problem/2307
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2307_도로검문;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Point {
    int n;
    int time;

    public Point(int n, int time) {
        this.n = n;
        this.time = time;
    }
}

public class Main {
    static int[][] adjMatrix;
    static int[] pi;

    public static void main(String[] args) throws IOException {
        // n개의 도시, m개의 도로가 주어진다
        // 범죄용의자가 1번에서 출발하여 n번 지점으로 최소 시간으로 도망치려한다.
        // 경찰은 도로들 중 하나를 검문하며 범죄용의자는 검문 도로를 지나갈 수 없으며 따라서 도주 시간이 지연된다.
        // 경찰은 이 도주 시간을 최대한 지연시키고자 한다.
        // 최대 지연 시간 혹은 도로를 검문함으로써 범죄용의자가 탈주하지 못한다면 -1을 출력한다.
        //
        // Dijkstra 문제
        // 먼저, 1 -> n의 최단 루트를 뽑고, 해당 루트에 있는 도로들을 하나씩 폐쇄해가며 다시 다익스트라를 돌려본다.
        // 그러면서 도착 시간의 차이를 통해 지연 시간을 구해나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 인접 배열
        adjMatrix = new int[n][n];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int t = Integer.parseInt(st.nextToken());

            adjMatrix[a][b] = adjMatrix[b][a] = Math.min(adjMatrix[a][b], t);
        }
        // 최단 루트를 뽑을 파이 배열.
        pi = new int[n];
        Arrays.fill(pi, -1);
        // 검문이 없을 때의 최소 도주 시간.
        int minTime = dijkstra(n - 1);
        // 그 때의 루트
        int[] piArray = pi.clone();

        // 최대 지연 시간 계산.
        int maxDelay = 0;
        // 도착 지점으로부터 하나씩 거슬러 올라간다.
        int loc = n - 1;
        // loc이 출발 지점이 될 때까지.
        while (piArray[loc] != -1) {
            // 해당 도로의 소요시간 저장.
            int temp = adjMatrix[piArray[loc]][loc];
            // 도로 폐쇄.
            adjMatrix[piArray[loc]][loc] = adjMatrix[loc][piArray[loc]] = Integer.MAX_VALUE;

            // 1 -> n 까지의 최소 시간 다시 계산.
            int currentMinTime = dijkstra(n - 1);
            // 만약 도착하는 방법이 없어진다면
            // maxDelay에 -1값을 저장하고 반복문 종료.
            if (currentMinTime == Integer.MAX_VALUE) {
                maxDelay = -1;
                break;
            }

            // 최대 지연값 갱신.
            maxDelay = Math.max(maxDelay, currentMinTime - minTime);
            // piArray[loc] < - > loc 도로 복원.
            adjMatrix[piArray[loc]][loc] = adjMatrix[loc][piArray[loc]] = temp;
            // loc을 이전 지점으로 변경.
            loc = piArray[loc];
        }
        // 최대 지연 시간 출력.
        System.out.println(maxDelay);
    }

    static int dijkstra(int end) {
        // 각 지점에 이르는 최소 시간.
        int[] minTimes = new int[adjMatrix.length];
        // 최대값으로 초기화.
        Arrays.fill(minTimes, Integer.MAX_VALUE);
        // 시작 지점의 최소 시간은 0.
        minTimes[0] = 0;

        // 방문 체크.
        boolean[] visited = new boolean[adjMatrix.length];

        // 해당 지점에 이르는 시간이 이른 순으로 체크.
        PriorityQueue<Point> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.time));
        priorityQueue.offer(new Point(0, 0));
        while (!priorityQueue.isEmpty()) {
            // 현재 지점.
            Point current = priorityQueue.poll();
            // 만약 이전에 계산이 된 지점이라면 건너뜀.
            if (current.time > minTimes[current.n])
                continue;

            // current에서 갈 수 있는 다른 지점들 계산.
            for (int i = 0; i < adjMatrix[current.n].length; i++) {
                // 방문 체크가 안 됐고, 도로가 있으며,
                // 최소 시간이 갱신된다면.
                if (!visited[i] && adjMatrix[current.n][i] != Integer.MAX_VALUE &&
                        minTimes[i] > minTimes[current.n] + adjMatrix[current.n][i]) {
                    // 최소 시간 갱신.
                    minTimes[i] = minTimes[current.n] + adjMatrix[current.n][i];
                    // 루트 저장.
                    pi[i] = current.n;
                    // 갱신된 시간으로 해당 지점을 우선순위큐에 담음.
                    priorityQueue.offer(new Point(i, minTimes[i]));
                }
            }
            // current에 대한 계산이 모두 끝났으므로 방문 체크.
            visited[current.n] = true;
        }
        // end에 도달하는 최소 시간 반환.
        return minTimes[end];
    }
}