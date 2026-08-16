/*
 Author : Ruel
 Problem : Jungol 8699번 무등산 등반
 Problem address : https://jungol.co.kr/problem/8699
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8699_무등산등반;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자가 주어진다.
        // 각 칸의 높이가 주어진다.
        // (x, y)에서 시작해 정상으로 가고자 한다.
        // 같은 높이의 칸으로 이동한다면 1만큼
        // 더 높은 곳으로 이동한다면 1 높이 차만큼 a만큼씩, 낮은 곳으로 이동한다면 높이 차만큼 b씩 시간을 소모한다.
        // 높이 차가 c 초과라면 이동할 수 없다.
        // 정상에 도달하는 최소 시간은?
        //
        // 그래프 탐색, BFS
        // 그래프 탐색을 하되, 높이 따른 조건이 추가됐다.
        // 해당 높이 조건만 잘 고려하여 탐색해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 시작 위치
        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken()) - 1;
        int y = Integer.parseInt(st.nextToken()) - 1;

        // 높은 곳으로 이동할 때의 단위 소모 시간 a, 낮은 곳으로 이동할 때의 단위 소모 시간 b
        // 최대 이동 가능 높이 차 c
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 각 칸의 높이
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 각 칸에 도달하는 최소 시간
        int[][] dp = new int[n][m];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 출발 위치
        dp[x][y] = 0;
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(x * m + y);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / m;
            int col = current % m;

            // 인접 4방향
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 맵 범위를 벗어나지 않으며
                if (checkArea(nextR, nextC)) {
                    int diff = map[nextR][nextC] - map[row][col];
                    // 높이 차가 c 초과면 건너뜀
                    if (Math.abs(diff) > c)
                        continue;

                    // 높이 차이에 따른 소모 시간 계산
                    int cost = diff == 0 ? 1 : (diff > 0 ? diff * a : -diff * b);
                    // 최소 시간으로 다음 지역을 방문하는 경우
                    // 값 갱신 후, 큐에 추가
                    if (dp[nextR][nextC] > dp[row][col] + cost) {
                        dp[nextR][nextC] = dp[row][col] + cost;
                        queue.offer(nextR * m + nextC);
                    }
                }
            }
        }

        // 정상의 위치를 찾는다.
        int summit = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] > map[summit / m][summit % m])
                    summit = i * m + j;
            }
        }

        // 초기값이라면 정상에 도달하는 것이 불가능한 경우
        // 그 외의 경우 도착 시간을 출력
        System.out.println(dp[summit / m][summit % m] == Integer.MAX_VALUE ? -1 : dp[summit / m][summit % m]);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}