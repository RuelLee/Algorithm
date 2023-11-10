/*
 Author : Ruel
 Problem : Baekjoon 15488번 나이트가 체스판을 벗어나지 않을 확률
 Problem address : https://www.acmicpc.net/problem/15488
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15488_나이트가체스판을벗어나지않을확률;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {2, 1, -1, -2, -2, -1, 1, 2};
    static int[] dc = {1, 2, 2, 1, -1, -2, -2, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 체스판이 있고
        // (x, y)에 나이트가 있다.
        // 이 때 나이트가 k번 이동하여 체스판을 나가지 않을 확률은?
        //
        // DP 문제
        // 위치와 이동 횟수가 필요하므로 3차원 DP로 표현한다.
        // dp[m][r][c] = m번 이동했을 때, (r, c)에 나이트가 있을 확률로 정의한다.
        // 나이트가 이동할 수 있는 위치는 8방향이므로 한 방향으로 이동할 확률은 현재 위치에서
        // 확률 / 8이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n * n 크기의 보드
        int n = Integer.parseInt(st.nextToken());
        // x, y 위치
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // 이동 횟수 k
        int k = Integer.parseInt(st.nextToken());
        
        // 각 이동 횟수에 따른 위치할 확률
        double[][][] dp = new double[k + 1][n][n];
        // 첫 위치
        dp[0][x - 1][y - 1] = 1;
        // 이동 횟수가 0일 때부터,
        // k - 1까지
        for (int i = 0; i < dp.length - 1; i++) {
            // 모든 좌표를 돌며 확인
            for (int r = 0; r < dp[i].length; r++) {
                for (int c = 0; c < dp[i][r].length; c++) {
                    // r, c에서 이동할 수 있는 8가지 경우의 수 계산
                    for (int d = 0; d < 8; d++) {
                        // 다음 위치
                        int nextR = r + dr[d];
                        int nextC = c + dc[d];
                        
                        // 맵을 벗어나지 않는다면
                        // 해당 이동 확률 계산
                        if (checkArea(nextR, nextC, n))
                            dp[i + 1][nextR][nextC] += dp[i][r][c] / 8;
                    }
                }
            }
        }

        // k번 이동 후,
        // 각 위치에 있는 확률의 합을 구하면
        // 보드에 위치할 확률.
        double sum = 0;
        for (int i = 0; i < dp[k].length; i++) {
            for (int j = 0; j < dp[k][i].length; j++)
                sum += dp[k][i][j];
        }

        // 답안 출력
        System.out.println(sum);
    }
    
    // 범위 체크
    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}