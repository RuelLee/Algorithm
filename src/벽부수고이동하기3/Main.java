/*
 Author : Ruel
 Problem : Baekjoon 16933번 벽 부수고 이동하기 3
 Problem address : https://www.acmicpc.net/problem/16933
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 벽부수고이동하기3;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

class Pos {
    int r;
    int c;
    int distance;
    int brokenWall;
    boolean daytime;

    public Pos(int r, int c, int distance, int brokenWall, boolean daytime) {
        this.r = r;
        this.c = c;
        this.distance = distance;
        this.brokenWall = brokenWall;
        this.daytime = daytime;
    }
}

public class Main {
    static int[][] map;
    static int[][][] minDistance;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // n, m 사이즈 맵에서 k번 벽을 뚫을 수 있는데, 낮에만 뚫을 수 있다.
        // 현 위치에서 낮을 기다릴 수 있지만 이럴 때는 이동거리가 하나 늘어난 걸로 생각한다.
        // 0, 0 -> n-1, m-1로 가는데 걸리는 최소이동거리는?
        // 각 위치에 도달하는 최소이동거리를 기록해야하는데, 벽을 뚫을 갯수에 따라 따로 저장해야한다
        // -> 벽을 많이 뚫더라도 더 적은 이동거리를 갖고 있으며, k를 넘지 않을 경우 유효하기 때문.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        map = new int[n][m];
        sc.nextLine();
        for (int i = 0; i < map.length; i++) {
            String row = sc.nextLine();
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(String.valueOf(row.charAt(j)));
        }
        minDistance = new int[n][m][k + 1];
        for (int[][] minDIst : minDistance) {
            for (int[] md : minDIst)
                Arrays.fill(md, Integer.MAX_VALUE);
        }

        PriorityQueue<Pos> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.distance, o2.distance));
        priorityQueue.add(new Pos(0, 0, 1, 0, true));
        int answer = Integer.MAX_VALUE;
        while (!priorityQueue.isEmpty()) {
            Pos current = priorityQueue.poll();

            if (current.r == n - 1 && current.c == m - 1) {     // 목적지에 도달했다면 최솟값을 갱신해주고 종료.
                answer = current.distance;
                break;
            }

            if (!current.daytime)       // 만약 현재가 밤이라면, 옆이 벽이라 못 뚫을 수 있으므로 그대로 낮으로 바꿔주는 경우를 추가한다.
                priorityQueue.add(new Pos(current.r, current.c, current.distance + 1, current.brokenWall, true));

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC)) {
                    if (map[nextR][nextC] == 0 && minDistance[nextR][nextC][current.brokenWall] > current.distance + 1) {   // 옆이 벽이 아니고, 최소이동거리를 갱신할 수 있다면 이동!
                        priorityQueue.add(new Pos(nextR, nextC, current.distance + 1, current.brokenWall, !current.daytime));
                        minDistance[nextR][nextC][current.brokenWall] = current.distance + 1;
                    } else if (map[nextR][nextC] == 1 && current.daytime && current.brokenWall < k && minDistance[nextR][nextC][current.brokenWall + 1] > current.distance + 1) {
                        // 옆이 벽이지만 현재가 낮이고, 벽을 뚫은 회수가 k를 넘지 않았을 경우 이동!
                        priorityQueue.add(new Pos(nextR, nextC, current.distance + 1, current.brokenWall + 1, false));
                        minDistance[nextR][nextC][current.brokenWall + 1] = current.distance + 1;
                    }
                }
            }
        }
        // answer가 갱신됐다면 이동이 가능한 경우, 그렇지 않다면 불가능하므로 -1을 출력.
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}