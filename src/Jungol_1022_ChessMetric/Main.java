/*
 Author : Ruel
 Problem : Jungol 1022번 Chess Metric
 Problem address : https://jungol.co.kr/problem/1022
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1022_ChessMetric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[] dr = {-1, -1, -1, -1, -1, 1, 1, 1, 1, 1, -2, 0, 2, -2, 0, 2};
    static int[] dc = {-2, -1, 0, 1, 2, -2, -1, 0, 1, 2, -1, -1, -1, 1, 1, 1};

    public static void main(String[] args) throws IOException {
        // 킹과 나이트가 이동가능한 칸들을 모두 이동 가능한 말이 있다.
        // n * n 크기의 체스판에서
        // 시작 위치의 x, y와 도착 위치의 x, y 그리고 움직임 횟수 numMoves가 주어진다.
        // 시작 위치의 말을 numMoves회 움직여서 도착 위치로 옮기는 경우의 수를 구하라
        //
        // dp 문제
        // dp[움직임횟수][x][y] = 경우의 수
        // 로 놓고 dp를 채워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 체스판
        n = Integer.parseInt(st.nextToken());
        // 시작 위치
        int sx = Integer.parseInt(st.nextToken());
        int sy = Integer.parseInt(st.nextToken());
        // 도착 위치
        int ex = Integer.parseInt(st.nextToken());
        int ey = Integer.parseInt(st.nextToken());
        // 움직임 횟수
        int numMoves = Integer.parseInt(st.nextToken());

        int[][][] map = new int[numMoves + 1][n][n];
        // 처음 위치에서 1가지
        map[0][sx][sy] = 1;
        for (int m = 0; m < map.length - 1; m++) {
            for (int r = 0; r < map[m].length; r++) {
                for (int c = 0; c < map[m][r].length; c++) {
                    // 경우의 수가 0인 경우 건너뜀.
                    if (map[m][r][c] == 0)
                        continue;

                    // 이동 가능한 모든 칸에 대해
                    for (int d = 0; d < dr.length; d++) {
                        int nextR = r + dr[d];
                        int nextC = c + dc[d];

                        // 이동 가능하다면 경우의 수 누적
                        if (checkArea(nextR, nextC))
                            map[m + 1][nextR][nextC] += map[m][r][c];
                    }
                }
            }
        }
        // 도착 위치에 numMoves회 움직여 도착한 경우의 수 출력
        System.out.println(map[numMoves][ex][ey]);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}