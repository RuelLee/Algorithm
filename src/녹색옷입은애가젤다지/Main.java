/*
 Author : Ruel
 Problem : Baekjoon 4485번 녹색 옷 입은 애가 젤다지?
 Problem address : https://www.acmicpc.net/problem/4485
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 녹색옷입은애가젤다지;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Point {
    int r;
    int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] map;

    public static void main(String[] args) {
        // 간단한 구현 문제
        // 가로 세로의 길이 n이 주어지고, 각 칸의 값들이 주어진다
        // 0, 0에서 n-1, n-1로 이동하는데 최소비용으로 이동해야한다
        // BFS
        Scanner sc = new Scanner(System.in);

        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; ; testCase++) {
            int n = sc.nextInt();
            if (n == 0)
                break;

            map = new int[n][n];
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++)
                    map[i][j] = sc.nextInt();
            }

            // 각 칸에 최소비용이 갱신될 때만 이동한다.
            int[][] minCost = new int[n][n];
            for (int[] mc : minCost)
                Arrays.fill(mc, Integer.MAX_VALUE);     // Integer.MAX_VALUE로 초기화.
            minCost[0][0] = map[0][0];  // 첫 칸은 map[0][0]값 그대로.

            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(0, 0));
            while (!queue.isEmpty()) {
                Point current = queue.poll();

                for (int d = 0; d < 4; d++) {       // 사방 탐색
                    int nextR = current.r + dr[d];
                    int nextC = current.c + dc[d];

                    // 맵 범위를 벗어나지 않고, 최소 비용을 갱신할 때
                    if (checkArea(nextR, nextC) && minCost[nextR][nextC] > minCost[current.r][current.c] + map[nextR][nextC]) {
                        minCost[nextR][nextC] = minCost[current.r][current.c] + map[nextR][nextC];
                        queue.offer(new Point(nextR, nextC));
                    }
                }
            }
            // 모든 탐색이 끝났다.
            // minCost의 n-1, n-1의 값을 가져온다.
            sb.append("Problem ").append(testCase + 1).append(": ").append(minCost[map.length - 1][map[map.length - 1].length - 1]).append("\n");
        }
        System.out.println(sb);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}