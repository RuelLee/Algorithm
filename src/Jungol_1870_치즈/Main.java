/*
 Author : Ruel
 Problem : Jungol 1870번 치즈
 Problem address : https://jungol.co.kr/problem/1870
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1870_치즈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 격자 판 위에 치즈가 놓여있다.
        // 항상 가장자리에는 치즈가 놓여있지 않으며, 4면 중 두 면 이상 외부 공기와 노출된 치즈는 1시간 이내에 녹는다.
        // 모든 치즈가 녹는 시간을 구하라
        //
        // BFS, 시뮬레이션 문제
        // 노출된 공기로 탐색을 하며, 인접한 치즈에 인접한 외부 공기 면의 수를 체크한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 치즈 상태
        int[][] map = new int[n + 2][m + 2];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= m; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 녹인 치즈가 있는 경우 계속 진행
        boolean melt = true;
        // 턴
        int turn = 0;
        // 인접한 외부 공기 면의 개수
        int[][] nearByAir = new int[n + 2][m + 2];
        // 방문 체크
        boolean[][] visited = new boolean[n + 2][m + 2];
        Queue<int[]> queue = new LinkedList<>();
        while (melt) {
            // 변수 초기화
            melt = false;
            for (boolean[] v : visited)
                Arrays.fill(v, false);
            for (int[] ne : nearByAir)
                Arrays.fill(ne, 0);

            // BFS
            queue.offer(new int[]{0, 0});
            // 외부 공기
            visited[0][0] = true;
            while (!queue.isEmpty()) {
                int[] current = queue.poll();

                for (int d = 0; d < 4; d++) {
                    int nextR = current[0] + dr[d];
                    int nextC = current[1] + dc[d];

                    // 인접한 칸 체크
                    if (checkArea(nextR, nextC) && !visited[nextR][nextC]) {
                        // 외부 공기인 경우, 큐에 추가 후 방문
                        if (map[nextR][nextC] == 0) {
                            queue.offer(new int[]{nextR, nextC});
                            visited[nextR][nextC] = true;
                        } else      // 치즈인 경우, 인접한 외부 공기 면의 수 증가
                            nearByAir[nextR][nextC]++;
                    }
                }
            }

            // 칸을 돌며, 2면 이상 외부와 맞닿은 치즈를 녹임
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (map[i][j] == 1 && nearByAir[i][j] > 1) {
                        melt = true;
                        map[i][j] = 0;
                    }
                }
            }
            // 녹인 치즈가 있을 경우 턴 증가
            if (melt)
                turn++;
        }
        // 걸린 시간 출력
        System.out.println(turn);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n + 2 && c >= 0 && c < m + 2;
    }
}