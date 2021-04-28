package 게임맵최단거리;

import java.util.LinkedList;
import java.util.Queue;

class Move {
    int r;
    int c;
    int turn;

    public Move(int r, int c, int turn) {
        this.r = r;
        this.c = c;
        this.turn = turn;
    }
}

public class Solution {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 시뮬레이션 문제.
        // DFS로 풀 경우 stack overflow 가 발생할 수 있으므로 BFS로 풀자.
        // 0,0에서 시작. 4방향을 살펴보며 길이 있다면 queue 담고, 해당 경로를 중복해서 가지 않기 위해 0으로 길을 지워주자.
        // 목적지(maps.length, maps[r].length)에 도달했을 경우( 이동 횟수를 비교해, 최소 이동값을 갱신해나가자)
        // 초기에 설정해둔 최소 이동값(Integer.MAX_VALUE)에서 변하지 않았다면 경로 x
        // 변했다면 그 값이 최소 이동값

        int[][] maps = {{1, 0, 1, 1, 1}, {1, 0, 1, 0, 1}, {1, 0, 1, 1, 1}, {1, 1, 1, 0, 1}, {0, 0, 0, 0, 1}};

        Queue<Move> queue = new LinkedList<>();

        queue.add(new Move(0, 0, 1));
        maps[0][0] = 0;
        int minDistance = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Move curMove = queue.poll();
            if (curMove.r == maps.length - 1 && curMove.c == maps[curMove.r].length - 1) {  // 목적지에 도달한 경우
                minDistance = Math.min(curMove.turn, minDistance);  // minDistance 값 갱신
                continue;
            }

            for (int i = 0; i < 4; i++) {   // 4방향을 탐색.
                int nextR = curMove.r + dr[i];
                int nextC = curMove.c + dc[i];

                if (checkArea(nextR, nextC, maps) && maps[nextR][nextC] == 1) {     // 길이 있다면 해당 위치로 이동. 이동 횟수 1증가. 해당위치의 길은 중복방지를 위해 0으로 지워주자.
                    queue.add(new Move(nextR, nextC, curMove.turn + 1));
                    maps[nextR][nextC] = 0;
                }
            }
        }
        System.out.println(minDistance == Integer.MAX_VALUE ? -1 : minDistance);
    }

    static boolean checkArea(int r, int c, int[][] maps) {
        if (r < 0 || c < 0 || r >= maps.length || c >= maps[r].length)
            return false;
        return true;
    }
}