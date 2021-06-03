package 경주로건설;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class State {   // 현재 위치와 도달 비용, 그리고 현재 도로가 수직인지 수평인지 나타내자.
    int r;
    int c;
    int cost;
    boolean horizontal;

    public State(int r, int c, int cost, boolean horizontal) {
        this.r = r;
        this.c = c;
        this.cost = cost;
        this.horizontal = horizontal;
    }
}

public class Solution {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 장애물이 있는 곳은 1, 빈 곳은 0의 값으로 board 가 주어지고, 직선 100, 곡선 500의 비용이 들 때, 좌상단에서 우하단까지 최소비용을 구하라.
        // 각 구간에 도달할 수 있는 최소비용을 cost 로 나타내자.

        int[][] board = {{0, 0, 1, 0}, {0, 0, 0, 0}, {0, 1, 0, 1}, {1, 0, 0, 0}};
        int[][] cost = new int[board.length][board[0].length];
        for (int[] a : cost)
            Arrays.fill(a, Integer.MAX_VALUE);
        cost[0][0] = 0;

        Queue<State> queue = new LinkedList<>();
        queue.add(new State(0, 0, 0, true));
        queue.add(new State(0, 0, 0, false));

        while (!queue.isEmpty()) {
            State current = queue.poll();

            for (int d = 0; d < 4; d++) {   // 4방을 체크하며
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC, board) && board[nextR][nextC] == 0) {   // 범위 내이고, 장애물이 있지 않다면
                    if (current.horizontal) {   // 현재가 수평 상태라면
                        if (d % 2 == 1) {   // d가 홀수 -> 다음 위치도 현재의 수평 위치. 이미 놓여있는 도로의 비용과 비교하여 작다면 갱신
                            if (cost[nextR][nextC] < current.cost + 100)    // 이미 있는 도로의 비용이 더 작다면 더 이상 해당 도로부터 파생되는 도로는 필요하지 않다. queue 에 추가하지 말자.
                                continue;
                            else {
                                cost[nextR][nextC] = current.cost + 100;
                                queue.add(new State(nextR, nextC, cost[nextR][nextC], true));       // 이번 도로가 최소비용으로 갱신됐으므로 현재 도로로부터 파생되는 도로는 필요.
                            }
                        } else {    // d가 짝수 -> 다음 위치는 수평. 따라서 곡선 비용인 500과 도로비용인 100이 같이 들어 600이 된다.
                            if (cost[nextR][nextC] < current.cost + 600)
                                continue;
                            else {
                                cost[nextR][nextC] = current.cost + 600;
                                queue.add(new State(nextR, nextC, cost[nextR][nextC], false));
                            }
                        }
                    } else {    // 현재 수직 위치
                        if (d % 2 == 1) {   // 다음 도로가 현재 지점으로부터 수평위치, 코너 + 도로
                            if (cost[nextR][nextC] < current.cost + 600)
                                continue;
                            else {
                                cost[nextR][nextC] = current.cost + 600;
                                queue.add(new State(nextR, nextC, cost[nextR][nextC], true));
                            }
                        } else {    // 다음 도로가 현재 지점으로부터 수직. 따라서 + 도로
                            if (cost[nextR][nextC] < current.cost + 100)
                                continue;
                            else {
                                cost[nextR][nextC] = current.cost + 100;
                                queue.add(new State(nextR, nextC, cost[nextR][nextC], false));
                            }
                        }
                    }
                }
            }
        }
        System.out.println(cost[cost.length - 1][cost[0].length - 1]);
    }
    static boolean checkArea(int r, int c, int[][] board) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }
}