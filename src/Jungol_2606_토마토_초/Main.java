/*
 Author : Ruel
 Problem : Jungol 2606번 토마토(초)
 Problem address : https://jungol.co.kr/problem/2606
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2606_토마토_초;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {1, -1, 0, 0, 0, 0};
    static int[] dc = {0, 0, 1, -1, 0, 0};
    static int[] dl = {0, 0, 0, 0, 1, -1};
    static int m, n, h;

    public static void main(String[] args) throws IOException {
        // 가로 m, 세로 n, 높이 h의 상자가 주어진다.
        // 각 칸마다 익지 않은 토마토 0, 익은 토마토 1, 빈 공간 -1로 주어진다.
        // 익은 토마토는 하루 동안 상 하 좌 우 위 아래의 익지 않은 토마토를 익힌다.
        // 모든 토마토가 익는데 걸리는 시간은?
        // 모든 토마토가 익을 수 없다면 -1을 출력한다.
        //
        // 그래프, BFS
        // 평면에서 탐색하듯 3차원 공간에서도 BFS 탐색을 하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 가로, 세로, 높이
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        // 각 토마토 정보
        int[][][] tomatoes = new int[h][n][m];
        // 각 칸의 익는 날짜
        int[][][] days = new int[h][n][m];

        Queue<int[]> queue = new LinkedList<>();
        // 빈 공간의 수
        int blank = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(days[i][j], Integer.MAX_VALUE);
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < m; k++) {
                    tomatoes[i][j][k] = Integer.parseInt(st.nextToken());
                    // 익은 토마토인 경우
                    if (tomatoes[i][j][k] == 1) {
                        queue.offer(new int[]{i, j, k});
                        // 해당 토마토가 익는데 걸리는 시간은 0
                        days[i][j][k] = 0;
                    } else if (tomatoes[i][j][k] == -1)     // 빈 공간인 경우
                        blank++;
                }
            }
        }

        // 마지막 토마토가 익는데 걸리는 시간
        int maxDay = 0;
        // 익은 토마토의 수
        int cnt = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            // 익은 토마토의 수 증가
            cnt++;
            // 마지막 토마토가 익는데 걸리는 시간 반영
            maxDay = Math.max(maxDay, days[cur[0]][cur[1]][cur[2]]);

            for (int d = 0; d < 6; d++) {
                // 상하좌우위아래로 탐색한다.
                int nextL = cur[0] + dl[d];
                int nextR = cur[1] + dr[d];
                int nextC = cur[2] + dc[d];

                // 범위를 벗어나지 않고, 익지 않은 토마토이며, 해당 칸의 토마토가 익는데 걸리는 최소 시간인 경우
                if (checkArea(nextL, nextR, nextC) && tomatoes[nextL][nextR][nextC] == 0 && days[nextL][nextR][nextC] > days[cur[0]][cur[1]][cur[2]] + 1) {
                    // 날짜 표시 및 큐에 추가
                    days[nextL][nextR][nextC] = days[cur[0]][cur[1]][cur[2]] + 1;
                    queue.offer(new int[]{nextL, nextR, nextC});
                }
            }
        }
        // 모든 칸의 토마토가 익지 못한 경우
        if (m * n * h - blank > cnt)
            System.out.println(-1);
        else        // 익었다면 최대 일 출력
            System.out.println(maxDay);
    }

    // 범위 체크
    static boolean checkArea(int l, int r, int c) {
        return l >= 0 && l < h && r >= 0 && r < n && c >= 0 && c < m;
    }
}