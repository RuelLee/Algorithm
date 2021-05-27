package 행렬테두리회전하기;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    static int[][] board;

    public static void main(String[] args) {
        // query 로 좌측상단의 점과, 우측하단의 점이 주어졌을 때,
        // 이에 해당하는 행렬을 시계방향으로 하나씩 회전시킨다.
        // 이 때 이동하는 값들 중 최소값을 찾아 행렬로 리턴하자.
        int rows = 100;
        int columns = 97;
        int[][] queries = {{1, 1, 100, 97}};
        setBoard(rows, columns);

        List<Integer> answer = new ArrayList<>();

        for (int[] query : queries)
            answer.add(rotate(query[0] - 1, query[1] - 1, query[2] - 1, query[3] - 1));

        System.out.println(Arrays.toString(answer.stream().mapToInt(i -> i).toArray()));
    }

    static int rotate(int r1, int c1, int r2, int c2) {
        int min = Integer.MAX_VALUE;

        int row = r1;
        int col = c1 + 1;
        int current;
        int pre = board[r1][c1];
        while (col < c2) {      // r1, c1 으로부터 r1, c2 까지
            current = board[row][col];
            min = Math.min(min, current);
            board[row][col++] = pre;
            pre = current;
        }
        while (row < r2) {      // r1, c2 으로부터 r2, c2까지
            current = board[row][col];
            min = Math.min(min, current);
            board[row++][col] = pre;
            pre = current;
        }
        while (col > c1) {      // r2, c2 으로부터, r2, c1까지
            current = board[row][col];
            min = Math.min(min, current);
            board[row][col--] = pre;
            pre = current;
        }
        while (row > r1) {      // r2, c1 으로부터 r1, c1까지
            current = board[row][col];
            min = Math.min(min, current);
            board[row--][col] = pre;
            pre = current;
        }
        min = Math.min(min, board[row][col]);       // 마지막 값 또한 최소값 비교하고, 바꿔주자.
        board[row][col] = pre;
        return min;
    }

    static void setBoard(int row, int col) {        // 주어지는 값으로 board 이차원 배열을 세팅한다.
        board = new int[row][col];
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = ++count;
        }
    }
}