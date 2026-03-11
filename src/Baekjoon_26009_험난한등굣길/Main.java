/*
 Author : Ruel
 Problem : Baekjoon 26009번 험난한 등굣길
 Problem address : https://www.acmicpc.net/problem/26009
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26009_험난한등굣길;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자가 주어진다.
        // k개의 교통정체가 주어진다.
        // r c d로 주어지며 (r, c)로부터 맨해튼 거리가 d이하인 곳들은 정체가 일어난다는 뜻이다.
        // (1, 1)에서 시작하여 (n, m)에 도달하고자 한다.
        // 시작점과 도착점은 교통 정체이지 않다.
        // 교통 정체를 피해 도착점까지 도달하는 최소 거리는?
        //
        // BFS 문제
        // 교통 정체가 최대 3000개, d가 최대 3000으로 주어지므로 모든 경우 모든 칸을 칠한다면 너무 많은 칸에 대해 계산해야한다.
        // 사실 교통 정체가 일어난다면, 해당 외곽선을 통해 넘나드는 것 자체가 금지된다.
        // 따라서 교통정체를 가장 외곽선만 표시해준다하더라도 넘나드는 것이 금지되기 때문에 외곽선만 처리하는 방법으로 한다.
        // 그 후에 최소 거리를 구하는 것은 BFS로 가능하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 맵
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int k = Integer.parseInt(br.readLine());
        // 교통 정체
        boolean[][] obstacles = new boolean[n][m];
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());

            // 최소 row부터 최대 row까지
            for (int row = Math.max(0, r - d); row <= Math.min(n - 1, r + d); row++) {
                // row에 반영된 차이의 크기
                int diff = Math.abs(r - row);
                // 남은 크기만큼 col에서 차이가 생길 수 있다.
                if (checkArea(row, c - (d - diff)))
                    obstacles[row][c - (d - diff)] = true;
                if (checkArea(row, c + (d - diff)))
                    obstacles[row][c + (d - diff)] = true;
            }
        }

        // BFS를 통해 시작점에서 도착점까지의 최소 거리를 구한다.
        int[][] minDistances = new int[n][m];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            for (int delta = 0; delta < 4; delta++) {
                int nextR = current[0] + dr[delta];
                int nextC = current[1] + dc[delta];

                if (checkArea(nextR, nextC) && !obstacles[nextR][nextC] &&
                        (minDistances[nextR][nextC] == 0 || minDistances[nextR][nextC] > minDistances[current[0]][current[1]] + 1)) {
                    minDistances[nextR][nextC] = minDistances[current[0]][current[1]] + 1;
                    queue.offer(new int[]{nextR, nextC});
                }
            }
        }
        // 도착점이 0이라면 불가능한 경우
        // 0이 아니라면 해당 거리를 출력
        System.out.println(minDistances[n - 1][m - 1] == 0 ? "NO" : "YES\n" + minDistances[n - 1][m - 1]);
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}