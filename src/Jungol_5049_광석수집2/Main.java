/*
 Author : Ruel
 Problem : Jungol 5049번 광석수집 2
 Problem address : https://jungol.co.kr/problem/5049
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5049_광석수집2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자로 이루어진 광산이 주어진다.
        // (1, 1)에서 시작하여 (n, m)으로 가고자 한다.
        // 각 칸은
        // 0 : 빈 칸, 1 : 돌로 막힌 칸, 2 : 황금을 하나 주어갈 수 있는 칸으로 주어진다.
        // 오른쪽과 아래로만 이동할 수 있다할 때, 도착지에서 얻을 수 있는 최대 황금의 수는?
        //
        // dp 문제
        // dp를 통해 시작점에서 도착점까지 가며 얻을 수 있는 황금의 수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 광산의 형태
        int[][] mine = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                mine[i][j] = Integer.parseInt(st.nextToken());
        }

        // 각 칸에서 얻을 수 있는 최대 황금의 수
        int[][] dp = new int[n][m];
        // 초기화
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // 첫번째 칸
        dp[0][0] = (mine[0][0] == 1 ? -1 : (mine[0][0] == 2 ? 1 : 0));

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                // 이동 불가 칸이라면 건너뜀
                if (dp[i][j] == -1)
                    continue;

                // 아래로 이동하는 경우
                if (i + 1 < n && mine[i + 1][j] != 1)
                    dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + (mine[i + 1][j] == 2 ? 1 : 0));
                // 오른쪽으로 이동하는 경우
                if (j + 1 < m && mine[i][j + 1] != 1)
                    dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] + (mine[i][j + 1] == 2 ? 1 : 0));
            }
        }
        // 최종 위치에서의 값 출력
        System.out.println(dp[n - 1][m - 1] == -1 ? 0 : dp[n - 1][m - 1]);
    }
}