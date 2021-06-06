package 미로탐색;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Point {
    int r;
    int c;
    int length;

    public Point(int r, int c, int length) {
        this.r = r;
        this.c = c;
        this.length = length;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 시뮬레이션 문제.
        // 길이 있는 곳은 1, 없는 곳은 0
        // 0,0부터 시작하여 n-1, m-1에 도착하는데 걸리는 최소 길이를 구하라.
        // DFS 로 탐색할 경우 범위에 따라 stack overflow 가 발생할 수 있으니 BFS로 풀자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();
        char[][] board = new char[n][m];
        for (int i = 0; i < n; i++)
            board[i] = sc.nextLine().toCharArray();

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(0, 0, 1));
        boolean[][] check = new boolean[n][m];
        check[0][0] = true;

        int minLength = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.r == n - 1 && current.c == m - 1) {
                minLength = Math.min(minLength, current.length);
                continue;
            }
            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];
                if (checkArea(nextR, nextC, board) && board[nextR][nextC] == '1' && !check[nextR][nextC]) {
                    check[nextR][nextC] = true;
                    queue.add(new Point(nextR, nextC, current.length + 1));
                }
            }
        }
        System.out.println(minLength);
    }

    static boolean checkArea(int r, int c, char[][] board) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }
}