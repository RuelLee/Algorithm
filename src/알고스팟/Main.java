/*
 Author : Ruel
 Problem : Baekjoon 1261번 알고스팟
 Problem address : https://www.acmicpc.net/problem/1261
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 알고스팟;

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
    static final int MAX = 100 * 100;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 간단한 시뮬레이션 문제
        // 1은 벽, 0은 뚫린 공간이라고 할 때, 가장 왼쪽 위의 점에서 가장 오른쪽 아래의 점까지 도착하는데
        // 부숴야하는 최소 벽의 개수를 구하는 문제
        // BFS로 풀어주었다
        //
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = sc.nextInt();

        String[] map = new String[n];
        sc.nextLine();
        for (int i = 0; i < n; i++)
            map[i] = sc.nextLine();

        int[][] minWallBroken = new int[n][m];      // 각 지점에 도착하는데 필요한 부수는 벽의 개수
        for (int[] mwb : minWallBroken)
            Arrays.fill(mwb, MAX);
        minWallBroken[0][0] = 0;        // 시작점은 0으로 세팅.

        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(0, 0));
        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC, n, m)) {    // 맵을 안 벗어나며
                    // 다음 위치가 0이고, 현재 벽을 부순 개수 그대로 이동할 때, 최소 부수는 벽의 개수가 갱신된다면
                    if (map[nextR].charAt(nextC) == '0' && minWallBroken[nextR][nextC] > minWallBroken[current.r][current.c]) {
                        minWallBroken[nextR][nextC] = minWallBroken[current.r][current.c];
                        queue.offer(new Point(nextR, nextC));
                    }
                    // 다음 위치가 1이고, 현재 위치에서 하나의 벽을 부순 개수가, 다음 지점의 최소 개수를 갱신한다면
                    else if (map[nextR].charAt(nextC) == '1' && minWallBroken[nextR][nextC] > minWallBroken[current.r][current.c] + 1) {
                        minWallBroken[nextR][nextC] = minWallBroken[current.r][current.c] + 1;
                        queue.offer(new Point(nextR, nextC));
                    }
                }
            }
        }
        // 최우측하단의 부수는 벽의 최소 개수를 출력한다.
        System.out.println(minWallBroken[minWallBroken.length - 1][minWallBroken[minWallBroken.length - 1].length - 1]);
    }

    static boolean checkArea(int r, int c, int n, int m) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}