/*
 Author : Ruel
 Problem : Baekjoon 5558번 チーズ (Cheese)
 Problem address : https://www.acmicpc.net/problem/5558
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5558_Cheese;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static char[][] map;
    static int[][] distances;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // h * w 크기의 맵이 주어진다.
        // 1 ~ n까지의 치즈가 한개씩 맵에 놓여있다.
        // S에서 시작하여 오름차순으로 모든 치즈를 먹는데 걸리는 시간은?
        // 상하좌우로 1칸을 단위 시간 1 마다 이동할 수 있다.
        // 장애물은 X로 주어진다. 장애물을 통과할 수 없다. 치즈는 먹지 않고 통과할 수 있다.
        //
        // BFS 문제
        // 치즈는 통과할 수 있으므로, 그냥 순서대로 S -> 1 -> 2 -> ... -> n까지
        // BFS를 통해 최소 이동 시간을 구해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 h * w 크기의 맵, 1 ~ n의 치즈
        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        map = new char[h][];
        // 시작 위치
        int curR = 0, curC = 0;
        for (int i = 0; i < h; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    curR = i;
                    curC = j;
                }
            }
        }

        distances = new int[h][w];
        // 1 ~ n까지의 거리를 누적
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            int[] result = calcDistance(curR, curC, i);
            sum += result[0];
            curR = result[1];
            curC = result[2];
        }
        // 답 출력
        System.out.println(sum);
    }

    // 현재 위치에서 num까지의 최소 거리를 BFS로 구한다.
    static int[] calcDistance(int curR, int curC, int num) {
        // 각 위치에 이르는 최소 거리
        for (int[] d : distances)
            Arrays.fill(d, Integer.MAX_VALUE);
        distances[curR][curC] = 0;

        // BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{curR, curC});
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            // 네 방향으로 이동
            for (int d = 0; d < 4; d++) {
                int nextR = cur[0] + dr[d];
                int nextC = cur[1] + dc[d];

                // 맵 범위를 안 벗어나고, 장애물이 아니며
                // 최소거리를 갱신하는 경우에
                if (checkArea(nextR, nextC) && map[nextR][nextC] != 'X' && distances[nextR][nextC] > distances[cur[0]][cur[1]] + 1) {
                    // 거리 기록
                    distances[nextR][nextC] = distances[cur[0]][cur[1]] + 1;
                    // 만약 목적지인 경우 해당 거리와 좌표 반환
                    if (map[nextR][nextC] - '0' == num)
                        return new int[]{distances[nextR][nextC], nextR, nextC};
                    // 큐에 추가
                    queue.offer(new int[]{nextR, nextC});
                }
            }
        }
        // 조건에 항상 다음 치즈로 이동이 가능한 경우만 주어진다했으므로
        // 아래를 반환하는 경우는 없을 것임.
        return new int[]{0};
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}
