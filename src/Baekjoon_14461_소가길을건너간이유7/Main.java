/*
 Author : Ruel
 Problem : Baekjoon 14461번 소가 길을 건너간 이유 7
 Problem address : https://www.acmicpc.net/problem/14461
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14461_소가길을건너간이유7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int r;
    int c;
    int turn;
    int time;

    public Seek(int r, int c, int turn, int time) {
        this.r = r;
        this.c = c;
        this.turn = turn;
        this.time = time;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int n;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 목초지가 주어지고, 각 목초지를 건너갈 때는 t 시간이 소요된다.
        // 그리고 목초지를 세번 가로지를 때마다 해당 목초지에 있는 풀을 뜯으며, 각 격자마다 할당된 시간이 소모된다.
        // 서쪽 끝에 있는 목초지에서 남동쪽 끝으로 가고자할 때
        // 소모되는 최소 시간은?
        //
        // dijkstra 문제
        // 최단 경로를 구하되, 세번째 이동을 할 때마다 추가적인 시간이 소모되는 형식이다.
        // 문제에 나온대로 잘 구현하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 격자, 격자를 가로지를 때 드는 시간 t
        n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 각 격자에 할당된 풀을 뜯는 시간
        int[][] map = new int[n][n];
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // dp[row][col][turn] = 최소 시간
        int[][][] dp = new int[n][n][3];
        for (int[][] row : dp) {
            for (int[] col : row)
                Arrays.fill(col, Integer.MAX_VALUE);
        }
        dp[0][0][0] = 0;
        
        // 우선순위큐를 통해, 시간이 짧은 순서대로 탐색
        PriorityQueue<Seek> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.time));
        priorityQueue.offer(new Seek(0, 0, 0, 0));
        while (!priorityQueue.isEmpty()) {
            Seek current = priorityQueue.poll();
            // 이미 더 적은 시간으로 탐색된 격자라면 건너뛴다.
            if (dp[current.r][current.c][current.turn] < current.time)
                continue;
            
            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC)) {
                    // 턴이 아직 2보다 작고, 다음 칸으로 이동할 때, 다음 칸에 도달하는 최소시간을 갱신하는 경우
                    if (current.turn < 2 && dp[nextR][nextC][current.turn + 1] > current.time + t) {
                        dp[nextR][nextC][current.turn + 1] = current.time + t;
                        priorityQueue.offer(new Seek(nextR, nextC, current.turn + 1, current.time + t));
                    } else if (current.turn == 2 && dp[nextR][nextC][0] > current.time + t + map[nextR][nextC]) {
                        // 딱 두번째 턴이라 다음 턴에 풀을 뜯어야하는 경우
                        dp[nextR][nextC][0] = current.time + t + map[nextR][nextC];
                        priorityQueue.offer(new Seek(nextR, nextC, 0, current.time + t + map[nextR][nextC]));
                    }
                }
            }
        }

        // 마지막 칸에 도달한 턴이 어느 값이던, 가장 작은 시간을 출력한다.
        System.out.println(Math.min(dp[n - 1][n - 1][0], Math.min(dp[n - 1][n - 1][1], dp[n - 1][n - 1][2])));
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}