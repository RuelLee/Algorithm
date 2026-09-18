/*
 Author : Ruel
 Problem : Jungol 8506번 백신
 Problem address : https://jungol.co.kr/problem/8506
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8506_백신;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 격자로 표현된 조직이 있다.
        // 각 조직에는 1 ~ 1000 값이 부여된다.
        // 한 칸에 백신을 놓으면, 백신은 동일한 형질이 있는 상하좌우의 인접한 칸들로 퍼져나간 뒤, 특정한 동일 값으로 변경된다.
        // 두 개의 사진이 주어질 때, 두 사진이 실험 전후가 될 수 있는지 판별하라
        //
        // BFS 문제
        // 두 사진의 서로 값이 다른 부분을 찾고, BFS로 동일한 형질에 대해 모든 값을 같게 변경시킨다.
        // 그 후, 두 사진을 비교하여 같은지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 두 사진
        int[][][] pictures = new int[2][n][m];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < m; k++)
                    pictures[i][j][k] = Integer.parseInt(st.nextToken());
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean changed = false;
        for (int i = 0; i < n && !changed; i++) {
            for (int j = 0; j < m && !changed; j++) {
                // 값이 다르다면
                if (pictures[0][i][j] != pictures[1][i][j]) {
                    // 원래 값을 저장해둔 뒤
                    int num = pictures[0][i][j];
                    // BFS로 인접한 동일 형질들을 다 picture[1]의 값으로 바꾼다.
                    queue.offer(new int[]{i, j});
                    pictures[0][i][j] = pictures[1][i][j];
                    while (!queue.isEmpty()) {
                        int[] cur = queue.poll();
                        for (int d = 0; d < 4; d++) {
                            int nextR = cur[0] + dr[d];
                            int nextC = cur[1] + dc[d];

                            if (checkArea(nextR, nextC) && pictures[0][nextR][nextC] == num) {
                                queue.offer(new int[]{nextR, nextC});
                                pictures[0][nextR][nextC] = pictures[1][i][j];
                            }
                        }
                    }
                    changed = true;
                }
            }
        }

        // 두 사진이 같은지 비교
        boolean possible = true;
        for (int i = 0; i < n && possible; i++) {
            for (int j = 0; j < m && possible; j++) {
                if (pictures[0][i][j] != pictures[1][i][j])
                    possible = false;
            }
        }
        // 답 출력
        System.out.println(possible ? "YES" : "NO");
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}