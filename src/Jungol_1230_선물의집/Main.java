/*
 Author : Ruel
 Problem : Jungol 1230번 선물의 집
 Problem address : https://jungol.co.kr/problem/1230
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1230_선물의집;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 미로가 주어진다.
        // 길은 0, 벽은 1, 선물은 2로 주어진다.
        // (0, 0)에서 시작하여 (n-1, n-1)이 출구이다.
        // 가는 동안 한 번 지난 길과 벽은 지나지 못한다.
        // 탈출하는 동안 마주친 선물은 모두 가져간다할 때, 얻을 수 있는 선물의 최대 개수는?
        //
        // DFS, 브루트포스 문제
        // n이 10으로 그리 크지 않다.
        // 따라서 모든 경우에 대해 DFS로 탐색하며 방문 체크를 하고 선물의 개수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 미로
        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // dfs 결과
        // 첫 위치에 선물이 있다면 해당 사항도 반영
        int answer = dfs(0, 0, new boolean[n][n], map[0][0] == 2 ? 1 : 0);
        // 답 출력
        System.out.println(answer);
    }

    // dfs
    static int dfs(int r, int c, boolean[][] visited, int totalGift) {
        if (r == n - 1 && c == n - 1)
            return totalGift;

        // 방문 체크
        visited[r][c] = true;
        // 해당 경우에서 파상되는 경우들 중 최대 선물의 개수
        int max = 0;
        for (int d = 0; d < 4; d++) {
            // 사방탐색
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            // 다음 위치가 뱀을 벗어나지 않았고, 미방문이며, 벽이 아닌 경우
            // 선물의 위치일 땐 totalGift+1 값을, 0일 경우 totalGift를 인자로 전달
            if (checkArea(nextR, nextC) && !visited[nextR][nextC] && map[nextR][nextC] != 1)
                max = Math.max(max, dfs(nextR, nextC, visited, totalGift + (map[nextR][nextC] == 2 ? 1 : 0)));
        }
        // 방문 체크 해제
        visited[r][c] = false;
        // 계산한 최대 선물 습득 개수 반환
        return max;
    }

    // 범위 체크
    static boolean checkArea(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }
}