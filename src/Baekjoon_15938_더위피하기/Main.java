/*
 Author : Ruel
 Problem : Baekjoon 15938번 더위 피하기
 Problem address : https://www.acmicpc.net/problem/15938
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15938_더위피하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int LIMIT = 1_000_000_000 + 7;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int t, SIZE;

    public static void main(String[] args) throws IOException {
        // 현재 좌표 (xs, ys)가 주어지고, 더위를 버틸 수 있는 시간 t(1 <= t <= 200)가 주어진다.
        // 집의 좌표 (xh, yh)가 주어진다.
        // 장애물의 수 n과 장애물의 좌표들이 주어진다.
        // 모든 좌표는 -10만 ~ 10만까지의 범위를 갖는다.
        // 장애물을 피해, 집에 도달하는 경우의 수를 구하라
        //
        // DP 문제
        // 범위가 너무 커서, dp로 불가능할 것 같지만, t가 200 이내라서
        // 사실상 시작 지점부터 -t ~ +t까지의 범위 내에 집이 존재하는 경우 해당 범위만 보면 된다.
        // 따라서 DP로 가능!

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 시작 위치
        int xs = Integer.parseInt(st.nextToken());
        int ys = Integer.parseInt(st.nextToken());
        // 더위를 버틸 수 있는 시간
        t = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        // 집의 위치
        int xh = Integer.parseInt(st.nextToken()) - xs + t;
        int yh = Integer.parseInt(st.nextToken()) - ys + t;

        // 최대한 이동할 수 있는 범위
        SIZE = 2 * t + 1;
        // 장애물의 위치
        boolean[][] obstacles = new boolean[SIZE][SIZE];
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - xs + t;
            int y = Integer.parseInt(st.nextToken()) - ys + t;

            // 장애물 또한 범위 내에 속하는 것만 표시
            if (checkArea(x, y))
                obstacles[x][y] = true;
        }

        // 집이 범위 밖이라면 도달 불가능
        if (!checkArea(xh, yh))
            System.out.println(0);
        else {
            // 가능한 경우
            // dp
            int[][][] dp = new int[2][SIZE][SIZE];
            // 현재 위치
            dp[0][t][t] = 1;
            // 누적 경우의 수
            int sum = dp[0][xh][yh];

            // 시간 k
            for (int k = 0; k < t; k++) {
                // dp[0]과 dp[1]을 번갈아가면서 사용할 것이므로
                // 다음에 사용할 공간을 초기화
                for (int i = t - k - 1; i <= t + k + 1; i++)
                    Arrays.fill(dp[(k + 1) % 2][i], t - k - 1, t + k + 2, 0);

                // 현재 idx
                int current = k % 2;
                // 다음 idx
                int next = (k + 1) % 2;
                for (int i = t - k; i <= t + k; i++) {
                    for (int j = t - k; j <= t + k; j++) {
                        // (i, j)가 도착지라면 더 이상 이동 x
                        if (i == xh && j == yh)
                            continue;

                        // 현재 위치에 도달하는 경우가 있고
                        if (dp[current][i][j] != 0) {
                            for (int d = 0; d < 4; d++) {
                                int nextR = i + dr[d];
                                int nextC = j + dc[d];

                                // 다음 위치가 범위 안이고, 장애물이 존재하지 않는다면
                                if (checkArea(nextR, nextC) && !obstacles[nextR][nextC]) {
                                    // 경우의 수 누적
                                    dp[next][nextR][nextC] += dp[k % 2][i][j];
                                    // 및 mod 처리
                                    dp[next][nextR][nextC] %= LIMIT;
                                }
                            }
                        }
                    }
                }
                // next에 집에 도달한 경우의를 sum에 누적
                sum += dp[next][xh][yh];
                // sum mod 처리
                sum %= LIMIT;
            }
            // 답 출력
            System.out.println(sum);
        }
    }

    // 범위 체크
    static boolean checkArea(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }
}