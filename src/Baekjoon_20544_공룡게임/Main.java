/*
 Author : Ruel
 Problem : Baekjoon 20544번 공룡게임
 Problem address : https://www.acmicpc.net/problem/20544
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20544_공룡게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n개의 길이의 길에서 높이가 1 혹은 2인 선인장들이 있다.
        // 공룡은 선인장 뛰어넘어 n + 1 위치가 도달하려한다.
        // 시작 위치에는 선인장이 존재하지 않는다.
        // 공룡은 최대 2개의 인접한 선인장을 뛰어넘을 수 있으며, 두 선인장의 높이 합이 4이상인 경우 뛰어넘을 수 없다.
        // 적어도 1번 높이가 2인 선인장을 뛰어넘어야한다.
        // 가능한 선인장 배치 경우의 수는?
        //
        // DP 문제
        // dp에 기록해야할 조건은
        // 1. 현재 위치
        // 2. 2인 선인장을 뛰어넘었는가
        // 3. -2 위치의 선인장 높이
        // 4. -1 위치의 선인장 높이
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 4차원 배열을 세운다.
        // dp[위치][높이2인선인장뛰어넘었는가][-2위치의선인장높이][-1위치의선인장높이]
        long[][][][] dp = new long[n][2][3][3];
        // 처음 시작
        dp[0][0][0][0] = 1;

        // 위치
        for (int i = 0; i < dp.length - 1; i++) {
            // 길이가 2인 선인장을 뛰어넘었는가
            for (int j = 0; j < dp[i].length; j++) {
                // -2 위치의 선인장 높이
                for (int first = 0; first < dp[i][j].length; first++) {
                    // -1 위치의 선인장 높이
                    for (int second = 0; second < dp[i][j][first].length; second++) {
                        dp[i][j][first][second] %= LIMIT;

                        // i+1 위치에 선인장은 항상 없을 수 있다.
                        dp[i + 1][j][second][0] += dp[i][j][first][second];

                        // i+1 위치에 높이가 1인 선인장을 배치하려면
                        // 두번째 전 혹은 직전 위치에 선인장이 존재해서는 안된다.
                        if (first == 0 || second == 0)
                            dp[i + 1][j][second][1] += dp[i][j][first][second];

                        // i+1 위치에 높이가 2인 선인장을 배치하려면
                        // 두번째 전에 선인장이 없고, 직전에 0 혹은 1인 선인장이 있는 경우와
                        // 직전에 선인장이 없던 경우.
                        if ((first == 0 && second < 2) || second == 0)
                            dp[i + 1][1][second][2] += dp[i][j][first][second];
                    }
                }
            }
        }

        long sum = 0;
        // n위치에서 한번 이상 높이가 2인 선인장을 뛰어넘은 경우들의 합을 구한다.
        for (int i = 0; i < dp[n - 1][1].length; i++) {
            for (int j = 0; j < dp[n - 1][1][i].length; j++)
                sum += dp[n - 1][1][i][j];
        }
        sum %= LIMIT;
        // 답안 출력
        System.out.println(sum);
    }
}