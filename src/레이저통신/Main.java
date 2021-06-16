package 레이저통신;

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

    public static void main(String[] args) {
        // C에서 레이저를 쏴, 또 다른 C 지점에 닿는 최소한의 거울의 갯수를 구하라
        // C에서 시작해서, 각 지점에 가로, 세로로 도달할 수 있는 최소한의 거울 갯수를 minMirror 배열로 나타내자.
        Scanner sc = new Scanner(System.in);

        int W = sc.nextInt();
        int H = sc.nextInt();
        sc.nextLine();

        char[][] board = new char[H][W];        // 입력으로 주어질 지도.
        for (int i = 0; i < H; i++)
            board[i] = sc.nextLine().toCharArray();

        int[][][] minMirror = new int[H][W][2];     // 각 지점에 닿는 최소한의 거울 갯수를 저장할 배열. minMirror[r][c][0]은 세로, minMirror[r][c][1]은 가로.
        for (int[][] a : minMirror) {
            for (int[] b : a)
                Arrays.fill(b, 10000);          // 각 값은 10000 으로 초기화
        }
        Queue<Point> queue = new LinkedList<>();
        boolean found = false;
        for (int i = 0; i < board.length; i++) {
            if (found)
                break;
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'C') {       // 출발 지점을 찾아, 값을 바꾸고, Queue에 넣자.
                    queue.add(new Point(i, j));
                    board[i][j] = 'S';
                    found = true;
                    break;
                }
            }
        }

        minMirror[queue.peek().r][queue.peek().c][0] = 0;       // start 지점에 필요한 거울의 개수는 0개
        minMirror[queue.peek().r][queue.peek().c][1] = 0;
        int minAnswer = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (board[current.r][current.c] == 'C') {   // 현재 지점이 종료지점이라면 최소값 갱신을 해주자.
                minAnswer = Math.min(minAnswer, Math.min(minMirror[current.r][current.c][0], minMirror[current.r][current.c][1]));
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC, board) && (board[nextR][nextC] == '.' || board[nextR][nextC] == 'C')) {         // 막혀 있는 길이 아니라면
                    if (d % 2 == 0) {     // 현재 지점과 다음 지점은 세로로 위치.
                        // 이전 지점의 가로에서 거울을 타고 다음 지점의 세로로 오거나,
                        // 이전 지점의 세로에서 직진으로 다음 지점의 세로로 올 수 있다. 이들 중 최소값을 가져오자.
                        int minValue = Math.min(minMirror[current.r][current.c][0], minMirror[current.r][current.c][1] + 1);
                        if (minMirror[nextR][nextC][0] > minValue) {
                            minMirror[nextR][nextC][0] = minValue;
                            queue.add(new Point(nextR, nextC));
                        }
                    } else {            // 현재 지점과 다음 지점은 가로로 위치.
                        // 이전 지점의 가로에서 직진으로 다음 지점의 가로로 오거나,
                        // 이전 지점의 세로에서 거울을 타고 다음 지점의 가로로 올 수 있다.
                        int minValue = Math.min(minMirror[current.r][current.c][0] + 1, minMirror[current.r][current.c][1]);
                        if (minMirror[nextR][nextC][1] > minValue) {
                            minMirror[nextR][nextC][1] = minValue;
                            queue.add(new Point(nextR, nextC));
                        }
                    }
                }
            }
        }
        System.out.println(minAnswer);
    }

    static boolean checkArea(int r, int c, char[][] board) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }
}