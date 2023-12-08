/*
 Author : Ruel
 Problem : Baekjoon 9505번 엔터프라이즈호 탈출
 Problem address : https://www.acmicpc.net/problem/9505
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9505_엔터프라이즈호탈출;

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
        // w * h 크기의 맵이 주어진다.
        // 각 위치에는 상대 전투기가 E를 제외한 A ~ Z까지 주어지고
        // 시작위치는 E로 주어진다.
        // 그리고 각 상대 전투기를 쓰러뜨리는 시간이 주어진다.
        // 최외곽으로 이동하는데 걸리는 최소 시간을 구하라
        //
        // dijkstra
        // 기본적으로 큐를 통한 너비우선탐색을 하되
        // 각 칸에 전투기를 쓰러뜨리는데 시간이 소요되므로 더 적은 시간이 소모된
        // 칸을 우선적으로 탐색한다. 다익스트라 알고리즘과 닮아있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // k개의 적 전투기 종류
            int k = Integer.parseInt(st.nextToken());
            // 너비
            int w = Integer.parseInt(st.nextToken());
            // 높이
            int h = Integer.parseInt(st.nextToken());

            // 상대 전투기를 쓰러뜨리는 시간을 기록한다.
            int[] times = new int[26];
            for (int i = 0; i < k; i++) {
                st = new StringTokenizer(br.readLine());
                char species = st.nextToken().charAt(0);
                int time = Integer.parseInt(st.nextToken());
                times[species - 'A'] = time;
            }
            
            // 전체 맵 정보
            char[][] map = new char[h][];
            int start = -1;
            for (int i = 0; i < map.length; i++) {
                map[i] = br.readLine().toCharArray();
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 'E')
                        start = i * w + j;
                }
            }
            
            // 각 칸에 도달하는 최소 시간
            int[][] minTimes = new int[h][w];
            for (int[] mt : minTimes)
                Arrays.fill(mt, Integer.MAX_VALUE);
            // 시작 위치
            minTimes[start / w][start % w] = 0;
            // 우선순위큐를 통해 탐색 시간이 적은 칸부터 우선적으로 탐색한다.
            PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.time));
            priorityQueue.offer(new State(start / w, start % w, 0));
            while (!priorityQueue.isEmpty()) {
                // 우선순위큐에서 뽑은 다음 탐색 대상
                State current = priorityQueue.poll();
                // 이미 더 적은 시간으로 탐색을 했다면 건너뛴다.
                if (minTimes[current.r][current.c] < current.time)
                    continue;
                
                // 4방향 탐색
                for (int d = 0; d < 4; d++) {
                    int nextR = current.r + dr[d];
                    int nextC = current.c + dc[d];
                    
                    // 범위를 벗어나지 않고, 최소 도달 시간을 갱신한다면
                    // 값을 갱신하고 우선순위큐에 추가한다.
                    if (checkArea(nextR, nextC, map) && minTimes[nextR][nextC] > minTimes[current.r][current.c] + times[map[nextR][nextC] - 'A']) {
                        minTimes[nextR][nextC] = minTimes[current.r][current.c] + times[map[nextR][nextC] - 'A'];
                        priorityQueue.offer(new State(nextR, nextC, minTimes[nextR][nextC]));
                    }
                }
            }

            // 최외곽을 돌며 최소 시간을 찾는다.
            int minTime = Integer.MAX_VALUE;
            for (int i = 0; i < minTimes[0].length; i++)
                minTime = Math.min(minTime, Math.min(minTimes[0][i], minTimes[h - 1][i]));
            for (int i = 0; i < minTimes.length; i++)
                minTime = Math.min(minTime, Math.min(minTimes[i][0], minTimes[i][w - 1]));
            // 최소 시간 기록
            sb.append(minTime).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}