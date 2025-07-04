/*
 Author : Ruel
 Problem : Baekjoon 23880번 Walking Home
 Problem address : https://www.acmicpc.net/problem/23880
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23880_WalkingHome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {1, 0};
    static int[] dc = {0, 1};

    public static void main(String[] args) throws IOException {
        // n * n크기의 목초지가 주어진다.
        // 목초지는 . 빈칸 혹은 H 장애물이 주어진다.
        // (1, 1)에서 출발하여 (n, n)으로 가는데
        // x 혹은 y가 증가하는 방향으로만 이동한다.
        // 장애물이 있는 곳은 지나갈 수 없으며
        // 최대 k번 이동 방향을 바꿀수 있다.
        // 목적지에 도달하는 경우의 수는?
        //
        // DP 문제
        // dp[x][y][방향][방향변경횟수] = 경우의 수
        // 로 dp를 채워나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // n * n 크기의 목초지, 방향 변경 제한 횟수 k
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            // 맵 상태
            char[][] map = new char[n][];
            for (int i = 0; i < map.length; i++)
                map[i] = br.readLine().toCharArray();

            // dp[x][y][방향][방향변경횟수] = 경우의 수
            int[][][][] dp = new int[n][n][2][k + 1];
            // 첫 이동만 직접 계산.
            // (1, 0)과 (0, 1)이 빈 칸인 경우
            if (map[1][0] == '.')
                dp[1][0][0][0] = 1;
            if (map[0][1] == '.')
                dp[0][1][1][0] = 1;
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < dp[i].length; j++) {
                    for (int d = 0; d < dp[i][j].length; d++) {
                        // d 방향대로 진행하는 경우.
                        // 맵을 벗어나선 안되고, 빈칸이어야 한다.
                        if (i + dr[d] < n && j + dc[d] < n && map[i + dr[d]][j + dc[d]] == '.') {
                            // 변경 횟수가 증가하진 않는다.
                            for (int e = 0; e < dp[i][j][d].length; e++)
                                dp[i + dr[d]][j + dc[d]][d][e] += dp[i][j][d][e];
                        }
                        
                        // 방향을 변경하는 경우
                        // 맵을 벗어나선 안되고, 빈 칸이어야한다.
                        if (i + dr[(d + 1) % 2] < n && j + dc[(d + 1) % 2] < n && map[i + dr[(d + 1) % 2]][j + dc[(d + 1) % 2]] == '.') {
                            // 방향을 변경하므로 횟수가 아직 k에 못 미친 경우에만 가능하다.
                            for (int e = 0; e < k; e++)
                                dp[i + dr[(d + 1) % 2]][j + dc[(d + 1) % 2]][(d + 1) % 2][e + 1] += dp[i][j][d][e];
                        }
                    }
                }
            }

            // 최종 지점에서, 방향과 방향 변경 횟수에 상관없이 모두 합을 구하 답을 기록한다.
            int answer = 0;
            for (int i = 0; i <= k; i++)
                answer += dp[n - 1][n - 1][0][i] + dp[n - 1][n - 1][1][i];
            sb.append(answer).append("\n");
        }
        // 전체 테스트 케이스 답 출력
        System.out.print(sb);
    }
}