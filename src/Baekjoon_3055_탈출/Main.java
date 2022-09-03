/*
 Author : Ruel
 Problem : Baekjoon 3055번 탈출
 Problem address : https://www.acmicpc.net/problem/3055
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3055_탈출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 세로 r, 가로 c의 맵이 주어지고
        // 빈 곳은 '.', 물은 '*', 돌은 'X', 비버굴은 'D', 고슴도치는 'S'로 표시되어있다.
        // 물은 매 턴마다 상하좌우의 빈 곳으로 퍼져나가며,
        // 고슴도치는 돌과 물을 건너지 못하며 비버 굴에 도달하고자한다.
        // 비버 굴에 도달하는 최소 시간 혹은 도달하지 못한다면 KAKTUS를 출력한다.
        //
        // 너비우선탐색 문제
        // 먼저 각 지점에 물이 퍼져나가는 시간을 구하고
        // 이 시간보다 고슴도치가 해당 지점에 이르게 도달할 수 있는지 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // 입력으로 주어지는 맵
        char[][] map = new char[r][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // 각 지점에 물이 도달하는 최소시간.
        int[][] minTimeToFlood = new int[r][c];
        for (int[] mttf : minTimeToFlood)
            Arrays.fill(mttf, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '*') {
                    minTimeToFlood[i][j] = 0;
                    queue.offer(i * c + j);
                }
            }
        }

        // 처음 물이 있는 곳부터 매 턴 물을 상하좌우로 퍼뜨린다.
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / c;
            int col = current % c;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 맵 안에 있고, 빈 곳이며, (row, col)에서 (nextR, nextC)로 퍼뜨리는게
                // 이전에 기록된 경우보다 더 이른 경우.
                if (checkArea(nextR, nextC, map) && map[nextR][nextC] == '.' &&
                        minTimeToFlood[nextR][nextC] > minTimeToFlood[row][col] + 1) {
                    minTimeToFlood[nextR][nextC] = minTimeToFlood[row][col] + 1;
                    queue.offer(nextR * c + nextC);
                }
            }
        }

        // 고슴도치가 해당 지점에 도달하는 최소시간.
        int[][] minTimeToReach = new int[r][c];
        for (int[] mttr : minTimeToReach)
            Arrays.fill(mttr, Integer.MAX_VALUE);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    minTimeToReach[i][j] = 0;
                    queue.offer(i * c + j);
                }
            }
        }

        // 비버굴에 도달하는 최소 시간.
        int answer = Integer.MAX_VALUE;
        // 고슴도치도 각 지점에 도달할 수 있는 최소 시간을 구한다.
        // 물과 다른 점은 물보다 이른 시간에 도달해야한다는 조건이 추가된다.
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / c;
            int col = current % c;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 맵 안에 있고, 돌이 아니며
                // 고슴도치가 이동하는 최소시간이며, 물이 도달하는 시간보다 이른 시간일 때.
                if (checkArea(nextR, nextC, map) && map[nextR][nextC] != 'X' &&
                        minTimeToReach[nextR][nextC] > minTimeToReach[row][col] + 1 &&
                        minTimeToFlood[nextR][nextC] > minTimeToReach[row][col] + 1) {
                    minTimeToReach[nextR][nextC] = minTimeToReach[row][col] + 1;
                    queue.offer(nextR * c + nextC);
                    if (map[nextR][nextC] == 'D')
                        answer = minTimeToReach[nextR][nextC];
                }
            }
        }
        // answer이 초기값이라면 불가능한 경우. KAKTUS 출력
        // 값이 있다면 해당 값 출력.
        System.out.println(answer == Integer.MAX_VALUE ? "KAKTUS" : answer);
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}