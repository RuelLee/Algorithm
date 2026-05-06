/*
 Author : Ruel
 Problem : Jungol 1082번 화염에서탈출
 Problem address : https://jungol.co.kr/problem/1082
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1082_화염에서탙출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int r, c;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static char[][] map;

    public static void main(String[] args) throws IOException {
        // r * c 크기의 맵이 주어진다.
        // X 바위, . 빈칸, * 불, S 시작 위치, D 도착 점으로 주어진다.
        // 불을 1초마다 상하좌우로 번져나가며 바위는 태우지 못한다.
        // 시작 위치에서 매초 1칸을 움직일 수 있으며 바위와 불을 넘지 못한다.
        // 무사히 도착점에 도달할 수 있는가? 그렇다면 얼마나 걸리는지 불가능한다면 impossible을 출력한다
        //
        // bfs 문제
        // 먼저 불을 퍼뜨린 후, 불보다 이른 시간에 도착한 경우에만 해당 지역을 지날 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 격자
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 지도 상태
        map = new char[r][];
        for (int i = 0; i < r; i++)
            map[i] = br.readLine().toCharArray();

        // 불
        int[][] fires = new int[r][c];
        for (int[] f : fires)
            Arrays.fill(f, Integer.MAX_VALUE);

        // 불의 위치를 큐에 담는다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (map[i][j] == '*') {
                    fires[i][j] = 0;
                    queue.offer(i * c + j);
                }
            }
        }

        // bfs로 불을 퍼뜨린다.
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / c;
            int col = current % c;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC) && map[nextR][nextC] != 'X' && map[nextR][nextC] == '.' &&
                        fires[nextR][nextC] > fires[row][col] + 1) {
                    fires[nextR][nextC] = fires[row][col] + 1;
                    queue.offer(nextR * c + nextC);
                }
            }
        }

        // 플레이어가 다른 칸에 이동하는 최소 시각
        int[][] hero = new int[r][c];
        for (int[] h : hero)
            Arrays.fill(h, Integer.MAX_VALUE);

        // 집 위치와 시작 위치
        int home = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (map[i][j] == 'S') {
                    hero[i][j] = 0;
                    queue.offer(i * c + j);
                } else if (map[i][j] == 'D')
                    home = i * c + j;
            }
        }
        // bfs로 플레이어를 이동시킨다.
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / c;
            int col = current % c;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 불보다 일찍 도달할 수 있어야한다.
                if (checkArea(nextR, nextC) && map[nextR][nextC] != 'X' && map[nextR][nextC] != '*' &&
                        Math.min(fires[nextR][nextC], hero[nextR][nextC]) > hero[row][col] + 1) {
                    hero[nextR][nextC] = hero[row][col] + 1;
                    queue.offer(nextR * c + nextC);
                }
            }
        }
        // 초기값인 경우 impossible, 그 외에는 도착 시각을 출력한다.
        System.out.println(hero[home / c][home % c] == Integer.MAX_VALUE ? "impossible" : hero[home / c][home % c]);
    }

    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}