/*
 Author : Ruel
 Problem : Baekjoon 33926번 인덕이와 보드게임
 Problem address : https://www.acmicpc.net/problem/33926
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_33926_인덕이와보드게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 보드가 주어진다.
        // 각 칸은 수와 색이 주어진다. (1, 1)은 하얀색이다.
        // 공을 굴리며 (1, 1)에서 (n, m)으로 가고자 한다.
        // 공은 각 칸의 수를 더하며, 해당 칸에서 벗어날 때, 흰색이라면 그대로, 검정색이라면 부호 반전이 일어난다.
        // 도착지에 도달하는 최대 점수는?
        //
        // dp 문제
        // dp문제인데, 검정칸을 갈 때마다 값이 반전이 되므로
        // 각 칸에 도달하는 최솟값과 최댓값 2개를 구한다.
        // 다음 칸이 검정색일 경우, 부호가 반전되어 최솟값과 최댓값이 바뀐다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 보드
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 칸의 수
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 각 칸의 색
        int[][] colors = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                colors[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 초기값
        long[][][] dp = new long[n][m][2];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j][0] = Long.MAX_VALUE;
                dp[i][j][1] = Long.MIN_VALUE;
            }
        }
        dp[0][0][0] = dp[0][0][1] = map[0][0];
        
        // 모든 칸을 순회하며
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 다음 행이 있는 경우
                if (i + 1 < dp.length) {
                    // 최솟값
                    dp[i + 1][j][0] = Math.min(dp[i + 1][j][0],
                            Math.min((dp[i][j][0] + map[i + 1][j]) * (colors[i + 1][j] == 1 ? -1 : 1),
                                    (dp[i][j][1] + map[i + 1][j]) * (colors[i + 1][j] == 1 ? -1 : 1)));
                    // 최댓값
                    dp[i + 1][j][1] = Math.max(dp[i + 1][j][1],
                            Math.max((dp[i][j][0] + map[i + 1][j]) * (colors[i + 1][j] == 1 ? -1 : 1),
                                    (dp[i][j][1] + map[i + 1][j]) * (colors[i + 1][j] == 1 ? -1 : 1)));
                }
                
                // 다음 열이 있는 경우
                if (j + 1 < dp[i].length) {
                    // 최솟값
                    dp[i][j + 1][0] = Math.min(dp[i][j + 1][0],
                            Math.min((dp[i][j][0] + map[i][j + 1]) * (colors[i][j + 1] == 1 ? -1 : 1),
                                    (dp[i][j][1] + map[i][j + 1]) * (colors[i][j + 1] == 1 ? -1 : 1)));
                    // 최댓값
                    dp[i][j + 1][1] = Math.max(dp[i][j + 1][1],
                            Math.max((dp[i][j][0] + map[i][j + 1]) * (colors[i][j + 1] == 1 ? -1 : 1),
                                    (dp[i][j][1] + map[i][j + 1]) * (colors[i][j + 1] == 1 ? -1 : 1)));
                }
            }
        }
        // (n, m)에서의 최댓값 출력
        System.out.println(dp[n - 1][m - 1][1]);
    }
}