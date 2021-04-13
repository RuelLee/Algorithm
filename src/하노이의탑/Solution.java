package 하노이의탑;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Move {
    int x;
    int y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Move{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

public class Solution {
    public static void main(String[] args) {
        int n = 2;

        Queue<Move> moveQueue = new LinkedList<>();

        movePlane(n, moveQueue, 1, 3, 2);

        int[][] answer = new int[moveQueue.size()][2];

        for (int[] move : answer) {
            Move currentMove = moveQueue.poll();
            move[0] = currentMove.x;
            move[1] = currentMove.y;
        }

        for(int[] move : answer)
            System.out.println(Arrays.toString(move));
    }

    static void movePlane(int n, Queue<Move> queue, int from, int to, int buffer) {     // n개의 원판을 buffer를 사용해서 from에서 to로 옮긴다.
        if (n == 1) {
            queue.add(new Move(from, to));  // 원판이 하나만 있을 때는 그 원판을 from에서 to로 그냥 옮기면 된다.
            return;
        }

        // n개의 원판을 from에서 to로 옮기는 방법은
        movePlane(n - 1, queue, from, buffer, to);  // n-1개의 원판을 일단 buffer쪽으로 옮겨두고,
        queue.add(new Move(from, to));                  // 가장 큰 마지막 원판을 to로 옮긴 후,
        movePlane(n - 1, queue, buffer, to, from);  // buffer에 있는 n-1개의 원판을 to로 옮기면 끝!
    }
}