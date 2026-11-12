/*
 Author : Ruel
 Problem : Jungol 2893번 제리의 치즈먹기 (Cheese)
 Problem address : https://jungol.co.kr/problem/2893
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2893_제리의치즈먹기_Cheese;

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
    static int h, w;

    public static void main(String[] args) throws IOException {
        // h * w 크기의 격자칸이 주어지고, 1 ~ n까지의 치즈가 놓여있다.
        // 각 칸에는 . 빈 공간, X 이동할 수 없는 공간, 치즈로 주어진다.
        // 치즈는 먹지 않고 통과할 수 있다.
        // 1부터 순서대로 치즈를 먹을 때, 모든 치즈를 먹는 최소 시간은?
        //
        // BFS 문제
        // minDst[마지막으로먹은치즈][row][col] = 최소 시간
        // 으로 정해, 각 위치를 마지막으로 먹은 치즈에 따라 구분하여 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // h * w 크기의 격자, 1 ~ n의 치즈
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 맵
        char[][] map = new char[h][];
        for (int i = 0; i < h; i++)
            map[i] = br.readLine().toCharArray();

        // 각 격자에 도달하는 최소 시간
        int[][][] minDst = new int[n + 1][h][w];
        for (int[][] m : minDst) {
            for (int[] d : m)
                Arrays.fill(d, Integer.MAX_VALUE);
        }
        // 시작지 탐색
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < h && queue.isEmpty(); i++) {
            for (int j = 0; j < w && queue.isEmpty(); j++) {
                if (map[i][j] == 'S') {
                    queue.offer(new int[]{i, j, 0});
                    minDst[0][i][j] = 0;
                }
            }
        }

        // 마지막으로 먹은 치즈
        int lastAte = 0;
        // 답
        int ans = -1;
        // 마지막으로 먹은 치즈가 n 미만이고, 답을 아직 못 찾은 경우
        while (!queue.isEmpty() && lastAte < n && ans == -1) {
            int[] cur = queue.poll();
            // 현재 먹은 치즈가 cur이 가르키는 치즈보다 더 크다면
            // 건너뛰어도 됨
            if (cur[2] < lastAte)
                continue;

            // 사방 탐색
            for (int d = 0; d < 4; d++) {
                int nextR = cur[0] + dr[d];
                int nextC = cur[1] + dc[d];

                // 범위를 안 벗어나고, 이동 가능하며
                if (checkArea(nextR, nextC) && map[nextR][nextC] != 'X') {
                    // 마지막으로 먹은 치즈보다 1개 더 큰 치즈인 경우
                    if (map[nextR][nextC] == (lastAte + '0' + 1) &&
                            minDst[cur[2] + 1][nextR][nextC] > minDst[cur[2]][cur[0]][cur[1]] + 1) {
                        // 마지막 치즈인 경우
                        if (lastAte == n - 1)
                            ans = minDst[cur[2]][cur[0]][cur[1]] + 1;
                        // 그 외의 경우
                        minDst[cur[2] + 1][nextR][nextC] = minDst[cur[2]][cur[0]][cur[1]] + 1;
                        queue.offer(new int[]{nextR, nextC, ++lastAte});
                        break;
                    } else if (minDst[cur[2]][nextR][nextC] > minDst[cur[2]][cur[0]][cur[1]] + 1) {     // 빈 공간이며 탐색을 하는 경우
                        minDst[cur[2]][nextR][nextC] = minDst[cur[2]][cur[0]][cur[1]] + 1;
                        queue.offer(new int[]{nextR, nextC, cur[2]});
                    }
                }
            }

        }
        // 답 출력
        System.out.println(ans);
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }
}