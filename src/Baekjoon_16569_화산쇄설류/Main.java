/*
 Author : Ruel
 Problem : Baekjoon 16569번 화산쇄설류
 Problem address : https://www.acmicpc.net/problem/16569
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16569_화산쇄설류;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class State {
    int r;
    int c;
    int time;

    public State(int r, int c, int time) {
        this.r = r;
        this.c = c;
        this.time = time;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // m행, n열의 공간의 고도가 주어진다.
        // 주인공의 시작 위치는 x, y
        // v개의 화산의 위치와 폭발 시간이 주어진다.
        // 화산이 폭발하면 t 시간 뒤, 맨해튼 거리가 t이하인 곳은 화산쇄설류로 덮이게 된다.
        // 화산과 화산쇄설류를 피해 가장 높은 곳으로 대피하고자할 때
        // 그 높이와 시간은?
        //
        // 그래프 탐색 문제
        // 먼저 화산의 정보를 토대로 각 위치가 화산쇄설류로 덮이는 최소 시간을 구한다.
        // 그 후 주인공이 움직이되, 화산이 아니며, 화산쇄설류가 덮이기 전에 해당 장소로 도착할 수 있는 경우만 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // m, n 크기의 지도, v개의 화산
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        // 주인공의 시작 위치
        int x = Integer.parseInt(st.nextToken()) - 1;
        int y = Integer.parseInt(st.nextToken()) - 1;
        
        // 각 위치의 고도
        int[][] map = new int[m][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 화산이 폭발하는 시간이 항상 0이 아니므로
        // 우선순위큐를 통해 해당 지역이 화산쇄설류에 덮이는 시간을 기준으로 오름차순 계산한다.
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.time));
        int[][] volcanoTimes = new int[m][n];
        boolean[][] volcano = new boolean[m][n];
        for (int[] vt : volcanoTimes)
            Arrays.fill(vt, Integer.MAX_VALUE);
        for (int i = 0; i < v; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int t = Integer.parseInt(st.nextToken());
            volcanoTimes[r][c] = t;
            volcano[r][c] = true;
            priorityQueue.offer(new State(r, c, t));
        }

        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            // 이미 계산되었다면 건너뜀
            if (current.time > volcanoTimes[current.r][current.c])
                continue;

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];
                
                // 맵을 벗어나지 않으며
                // 이번 위치에서 화산쇄셜류가 퍼져나가는 것이 최소 시간이라면
                if (nextR >= 0 && nextR < m && nextC >= 0 && nextC < n &&
                        volcanoTimes[nextR][nextC] > current.time + 1) {
                    // 최소시간 기록
                    volcanoTimes[nextR][nextC] = current.time + 1;
                    // 큐 추가
                    priorityQueue.offer(new State(nextR, nextC, volcanoTimes[nextR][nextC]));
                }
            }
        }

        // 주인공이 대피를 시작한다.
        int[][] escapeTimes = new int[m][n];
        for (int[] et : escapeTimes)
            Arrays.fill(et, Integer.MAX_VALUE);
        escapeTimes[x][y] = 0;

        int maxHeightR = x;
        int maxHeightC = y;
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(x, y, 0));
        while (!queue.isEmpty()) {
            State current = queue.poll();

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 맵을 벗어나지 않으며, 화산의 위치에 가선 안되고
                // 해당 칸에 도착하는 시간이 최소여야하며
                // 화산쇄설류가 도착하기보다 이른 시간에 도착해야만 한다.
                if (nextR >= 0 && nextR < m && nextC >= 0 && nextC < n && !volcano[nextR][nextC] &&
                        escapeTimes[nextR][nextC] > current.time + 1 &&
                        current.time + 1 < volcanoTimes[nextR][nextC]) {
                    // 위 조건을 만족할 경우, 해당 시간 기록
                    escapeTimes[nextR][nextC] = current.time + 1;
                    queue.offer(new State(nextR, nextC, escapeTimes[nextR][nextC]));
                    // 현재 위치가 최고 고도를 기록하는지 확인.
                    if (map[nextR][nextC] > map[maxHeightR][maxHeightC]) {
                        maxHeightR = nextR;
                        maxHeightC = nextC;
                    }
                }
            }
        }
        // 도달할 수 있는 가장 높은 위치의 고도와 시간을 출력
        System.out.println(map[maxHeightR][maxHeightC] + " " + escapeTimes[maxHeightR][maxHeightC]);
    }
}