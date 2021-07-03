/*
 Author : Ruel
 Problem : Baekjoon 16954번 움직이는 미로 탈출
 Problem address : https://www.acmicpc.net/problem/16954
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 움직이는미로탈출;

import java.util.*;

class Point {
    int r;
    int c;
    int turn;

    public Point(int r, int c, int turn) {
        this.r = r;
        this.c = c;
        this.turn = turn;
    }
}

public class Main {
    static int[] dr = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) {
        // 한 턴이 지날 때마다, 돌이 한칸씩 아래로 떨어진다.
        // 사람은 인접한 칸이나 대각선으로 이동할 수 있다.
        // 맨 왼쪽 아래에서 출발하여 맨 오른쪽 위로 도착할 수 있는지 여부를 판단하여라.
        // 틀렸던 케이스 중 하나가 사람이 가만히 있는 경우도 고려해야한다.
        // 돌이 떨어지기 때문에 가만히 있는 것도 상황이 바뀐다.
        
        Scanner sc = new Scanner(System.in);

        char[][] board = new char[8][];
        for (int i = 0; i < board.length; i++)
            board[i] = sc.nextLine().toCharArray();

        // 굳이 우선순위큐를 쓰지 않더라도 낮은 turn 이 우선적으로 실행될 것이다.
        PriorityQueue<Point> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.turn, o2.turn));
        priorityQueue.add(new Point(7, 0, 0));

        boolean finished = false;   // 도착했는지 여부 표시.
        int turn = 0;   // turn 이 바뀌었는지 표시.
        while (!priorityQueue.isEmpty()) {
            Point current = priorityQueue.poll();
            if (current.r == 0 && current.c == 7) { // 최종 목적지에 도착
                finished = true;
                break;
            }
            if (current.turn != turn) { // 다음 턴으로 넘어갔다면
                turn++;     // 현재 턴을 높여주고
                nextTurn(board);    // 돌 이동
            }

            for (int d = 0; d < dr.length; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 8방 + 제자리
                // 다음 위치와, 다음 위치의 위에 돌이 있으면 안된다.
                if (checkArea(nextR, nextC, board) && board[nextR][nextC] == '.' && ((checkArea(nextR - 1, nextC, board) && board[nextR - 1][nextC] == '.') || nextR == 0))
                    priorityQueue.add(new Point(nextR, nextC, current.turn + 1));
            }
        }
        System.out.println(finished ? 1 : 0);
    }

    static void nextTurn(char[][] board) {
        for (int i = board.length - 1; i > 0; i--)
            board[i] = board[i - 1];
        board[0] = new char[]{'.', '.', '.', '.', '.', '.', '.', '.'};
    }

    static boolean checkArea(int r, int c, char[][] board) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }
}