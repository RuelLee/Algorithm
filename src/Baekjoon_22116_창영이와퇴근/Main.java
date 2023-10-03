/*
 Author : Ruel
 Problem : Baekjoon 22116번 창영이와 퇴근
 Problem address : https://www.acmicpc.net/problem/22116
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22116_창영이와퇴근;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class State {
    int r;
    int c;
    int maxDiff;

    public State(int r, int c, int maxDiff) {
        this.r = r;
        this.c = c;
        this.maxDiff = maxDiff;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어진다.
        // 각 격자에는 높이가 있으며, (1, 1)에서 (n, n)으로 이동하고자한다.
        // 각 격자를 이동할 때는 높이 차에 따른 경사가 존재한다.
        // 목적지까지 도달하는데 지나는 경사들의 최대값을 최소화하고자 한다.
        // 그 때 지나는 경사의 최대값은?
        //
        // dijkstra..? 문제
        // (1, 1)부터 각 좌표에 이르는 경사를 계산하며
        // 지나온 경로의 경사도가 최소인 격자를 우선적으로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        int n = Integer.parseInt(br.readLine());
        
        // 각 위치의 높이
        int[][] map = new int[n][n];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 지나온 경로에서 최대 경사
        int[][] maxDiffs = new int[n][n];
        // 초기화
        for (int[] md : maxDiffs)
            Arrays.fill(md, Integer.MAX_VALUE);
        maxDiffs[0][0] = 0;
        // 우선순위큐로 경사가 낮은 순서를 우선적으로 계산한다.
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.maxDiff));
        priorityQueue.offer(new State(0, 0, 0));
        // 큐가 빌 때까지
        while (!priorityQueue.isEmpty()) {
            // 현재 위치
            State current = priorityQueue.poll();
            // 만약 기록된 경로의 최대 경사가 더 적은 값이라면
            // 앞선 차례에서 계산된 경우이기 때문에 건너뛴다.
            if (current.maxDiff > maxDiffs[current.r][current.c])
                continue;

            for (int d = 0; d < 4; d++) {
                // 다음 위치
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];
                
                // 맵을 벗어나는지 체크
                if (checkArea(nextR, nextC, map)) {
                    // 현재 지나온 최대 경사와 current -> next의 경사를 비교해
                    // 더 큰 경사값을 기록
                    int diff = Math.max(maxDiffs[current.r][current.c], Math.abs(map[nextR][nextC] - map[current.r][current.c]));
                    // next에 이르는 최소 경사라면
                    if (maxDiffs[nextR][nextC] > diff) {
                        // 값 기록
                        maxDiffs[nextR][nextC] = diff;
                        // 큐 추가
                        priorityQueue.offer(new State(nextR, nextC, diff));
                    }
                }
            }
        }

        // (n, n)에 이르는 최소 경사 출력
        System.out.println(maxDiffs[n - 1][n - 1]);
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}