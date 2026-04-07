/*
 Author : Ruel
 Problem : Baekjoon 21922번 학부 연구생 민상
 Problem address : https://www.acmicpc.net/problem/21922
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21922_학부연구생민상;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    // 각 물건에 부딪칠 때, 방향의 전환을 이차원 배열로 표시
    static int[][] conversion = new int[][]{
            {},
            {0, 3, 2, 1},
            {2, 1, 0, 3},
            {1, 0, 3, 2},
            {3, 2, 1, 0}};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 연구실이 주어진다.
        // 연구실은 빈 칸 혹은 물건, 그리고 에어컨으로 구성되어있다.
        // 에어컨에서는 상하좌우 네 방향으로 바람이 나오고,
        // 바람은 계속 직진하여 물건과 부딪치면 방향을 바꾼다.
        // 물건 1은 ▥ 모양으로 상하 바람은 통과하되, 좌우 바람은 반대로 돌아간다.
        // 물건 2는 ▤ 모양으로 좌우 바람은 통과하되 상하 바람은 반대로 돌아간다.
        // 물건 3은 ▨은 좌우바람은 물건과 부딪쳐 하상으로 바뀌고, 상하바람은 우좌로 바람이 바뀌낟.
        // 물건 4는 ▧은 좌우바람은 상하, 상하바람은 좌우 방향으로 바뀐다.
        // 에어컨은 9로 주어진다.
        // 에어컨 바람이 지나가는 칸의 수는?
        //
        // BFS, 그래프 탐색 문제
        // 일정한 방향으로 탐색하되, 물건을 만날 때마다 방향이 바뀔 수 있다.
        // 각 칸에 어떤 방향으로 도달하느냐도 영향을 미치므로 방향 또한 방문 체크에 표시되어야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 물건과 에어컨의 위치
        int[][] map = new int[n][m];
        // 방문 체크
        boolean[][][] visited = new boolean[n][m][4];
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                // 에어컨인 경우
                if ((map[i][j] = Integer.parseInt(st.nextToken())) == 9) {
                    for (int d = 0; d < 4; d++) {
                        queue.offer(new int[]{i, j, d});
                        visited[i][j][d] = true;
                    }
                }
            }
        }

        // BFS
        while (!queue.isEmpty()) {
            // 현재 r, c, d
            int[] current = queue.poll();

            int direction = 0;
            // 빈 칸인 경우 방향 유지
            if (map[current[0]][current[1]] % 9 == 0)
                direction = current[2];
            else        // 물건이 있는 경우 방향 전환
                direction = conversion[map[current[0]][current[1]]][current[2]];
            int nextR = current[0] + dr[direction];
            int nextC = current[1] + dc[direction];

            // 다음 칸이 범위 내에 있고, 해당 상태로 처음 방문인지 체크
            if (checkArea(nextR, nextC) && !visited[nextR][nextC][direction]) {
                queue.offer(new int[]{nextR, nextC, direction});
                visited[nextR][nextC][direction] = true;
            }
        }

        // 바람 방향에 상관없이 바람이 지나가는 칸을 모두 계산
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                boolean candidate = false;
                for (int k = 0; k < 4; k++)
                    candidate |= visited[i][j][k];
                if (candidate)
                    cnt++;
            }
        }
        // 답 출력
        System.out.println(cnt);
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}