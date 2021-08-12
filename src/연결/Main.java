/*
 Author : Ruel
 Problem : Baekjoon 5022번 연결
 Problem address : https://www.acmicpc.net/problem/5022
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 연결;

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
    static int answer;
    static boolean[][] blocked;
    static int[][] minCost;
    static Point[] points;

    public static void main(String[] args) {
        // 시뮬레이션 문제
        // 두 쌍의 지점에 전선을 두개 연결한다
        // 두 전선을 연결하는 최소 경로
        // 먼저 BFS로 첫번째 전선을 잇고 그 경로를 기록해둔다
        // 그 후 두번째 전선을 이어본다
        // 반대로 두번째 전선을 먼저 이어본다
        // 그 후 첫번째 전선을 잇는다
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt() + 1;
        int m = sc.nextInt() + 1;

        points = new Point[4];
        for (int i = 0; i < 4; i++)
            points[i] = new Point(sc.nextInt(), sc.nextInt());
        answer = Integer.MAX_VALUE;

        // 첫번째 전선을 먼저 잇는 경우
        blocked = new boolean[n][m];
        blocked[points[2].r][points[2].c] = blocked[points[3].r][points[3].c] = true;
        minCost = findMinCostWays(points[0], points[1], n, m);
        findBlockedWay(points[1].r, points[1].c, minCost[points[1].r][points[1].c], points[1], points[2], points[3]);

        // 두번째 전선을 먼저 잇는 경우.
        blocked = new boolean[n][m];
        blocked[points[0].r][points[0].c] = blocked[points[1].r][points[1].c] = true;
        minCost = findMinCostWays(points[2], points[3], n, m);
        findBlockedWay(points[3].r, points[3].c, minCost[points[3].r][points[3].c], points[3], points[0], points[1]);
        System.out.println(answer == Integer.MAX_VALUE ? "IMPOSSIBLE" : answer);

    }

    static void findBlockedWay(int r, int c, int distance, Point ended, Point start, Point end) {   // 최소거리기록을 바탕으로 전선을 찾고, 다른 전서을 이어 두 결과의 최소값을 남김.
        blocked[r][c] = true;
        if (distance == 0) {
            blocked[start.r][start.c] = blocked[end.r][end.c] = false;
            int[][] secondMinCost = findMinCostWays(start, end, blocked.length, blocked[0].length);
            if (minCost[ended.r][ended.c] == 100 * 100 || secondMinCost[end.r][end.c] == 100 * 100)
                return;
            answer = Math.min(answer, minCost[ended.r][ended.c] + secondMinCost[end.r][end.c]);
            return;
        }

        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            if (checkArea(nextR, nextC, minCost) && minCost[nextR][nextC] == distance - 1)
                findBlockedWay(nextR, nextC, --distance, ended, start, end);
        }
        blocked[r][c] = false;
    }

    static int[][] findMinCostWays(Point start, Point end, int n, int m) {      // start 에서 end로 가는 최소거리기록
        int[][] minCost = new int[n][m];
        for (int[] mc : minCost)
            Arrays.fill(mc, 100 * 100);
        minCost[start.r][start.c] = 0;
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.r == end.r && current.c == end.c)
                break;

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC, minCost) && !blocked[nextR][nextC] && minCost[current.r][current.c] + 1 < minCost[nextR][nextC]) {
                    minCost[nextR][nextC] = minCost[current.r][current.c] + 1;
                    queue.add(new Point(nextR, nextC));
                }
            }
        }
        return minCost;
    }

    static boolean checkArea(int r, int c, int[][] minCost) {
        return r >= 0 && r < minCost.length && c >= 0 && c < minCost[r].length;
    }
}