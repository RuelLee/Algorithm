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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new char[r][];
        for (int i = 0; i < r; i++)
            map[i] = br.readLine().toCharArray();

        int[][] fires = new int[r][c];
        for (int[] f : fires)
            Arrays.fill(f, Integer.MAX_VALUE);

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (map[i][j] == '*') {
                    fires[i][j] = 0;
                    queue.offer(i * c + j);
                }
            }
        }

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

        int[][] hero = new int[r][c];
        for (int[] h : hero)
            Arrays.fill(h, Integer.MAX_VALUE);

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
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / c;
            int col = current % c;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC) && map[nextR][nextC] != 'X' && map[nextR][nextC] != '*' &&
                        Math.min(fires[nextR][nextC], hero[nextR][nextC]) > hero[row][col] + 1) {
                    hero[nextR][nextC] = hero[row][col] + 1;
                    queue.offer(nextR * c + nextC);
                }
            }
        }
        System.out.println(hero[home / c][home % c] == Integer.MAX_VALUE ? "impossible" : hero[home / c][home % c]);
    }

    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}