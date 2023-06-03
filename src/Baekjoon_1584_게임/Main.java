/*
 Author : Ruel
 Problem : Baekjoon 1584번 게임
 Problem address : https://www.acmicpc.net/problem/1584
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1584_게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // (0, 0)에서 (500, 500)에 가고자 한다.
        // 각 구역은 안전, 위험, 죽음 지역이 있으며
        // 안전 구역에서는 생명을 잃지 않으며
        // 위험 구역에 진입할 때는 생명을 하나 잃는다.
        // 그리고 죽음 구역은 진입할 수 없다.
        // n개의 위험 지역과 m개의 죽음 지역에 대한
        // 끝 두 점이 x1, y1, x2, y2 형태로 주어진다.
        // 목적지에 도달하는데 소모하는 최소 생명의 양은?
        //
        // 0-1 너비 우선 탐색
        // 너비 우선 탐색이되 가중치가 있는 탐색이다.
        // 안전 구역으로 진행할 때는 가중치가 0이고, 위험지역에는 1이다.
        // 따라서 큐를 사용하지 않고, 우선순위큐를 활용하여
        // 더 적은 생명을 소모하는 탐색을 우선적으로 탐색하도록 한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 지도
        int[][] map = new int[501][501];
        
        // 위험 구역
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            fillMap(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 1, map);
        }
        // 죽음 구역
        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            fillMap(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 2, map);
        }

        // 각 구역에 도달하는 최소 생명을 계산한다.
        int[][] dp = new int[501][501];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[0][0] = 0;

        // 우선순위큐를 활용하여 더 적은 생명을 소모한 구역을 우선적으로 탐색한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> dp[value / 501][value % 501]));
        priorityQueue.offer(0);
        while (!priorityQueue.isEmpty()) {
            // 현재 위치
            int idx = priorityQueue.poll();
            int row = idx / 501;
            int col = idx % 501;
            
            // 현재 위치로부터 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 범위를 벗어나지 않고, 죽음 구역이 아니며
                // nextR, nextC에 도달하는 최소 생명 값을 갱신한다면
                // 값을 추가하고 우선순위큐에 추가
                if (checkArea(nextR, nextC, map) && map[nextR][nextC] != 2 &&
                        dp[nextR][nextC] > dp[row][col] + (map[nextR][nextC] == 1 ? 1 : 0)) {
                    dp[nextR][nextC] = dp[row][col] + (map[nextR][nextC] == 1 ? 1 : 0);
                    priorityQueue.offer(nextR * 501 + nextC);
                }
            }
        }
        
        // (500, 500)에 도달하는 최소 소모 생명을 출력한다.
        // 만약 초기값 그대로라면 도달하는 경로가 없는 경우이므로 -1 출력
        System.out.println(dp[500][500] == Integer.MAX_VALUE ? -1 : dp[500][500]);
    }
    
    // 범위 체크
    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }

    // x1, y1, x2, y2를 두 끝점으로 갖는 구역을 value 값으로 채운다.
    static void fillMap(int x1, int y1, int x2, int y2, int value, int[][] map) {
        for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
            for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++)
                map[i][j] = value;
        }
    }
}