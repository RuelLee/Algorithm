/*
 Author : Ruel
 Problem : Baekjoon 24501번 blobaww
 Problem address : https://www.acmicpc.net/problem/24501
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24501_blobaww;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1000000000 + 7;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 칸에 E, S, M 중 하나의 문자가 들어있다.
        // (x1, y1), (x2, y2), (x3, y3) (x1 <= x2 <= x3), (y1 <= y2 <= y3)의 세 문자를 골라
        // 해당 문자들이 순서대로 E S M인 경우를 찾고자 한다.
        // 모든 경우의 수를 구하라
        //
        // DP, 누적합 문제
        // dp[i][j][k] = (i, j)까지 세 개읨 문자 중, k+1개를 찾은 개수로 정하고 dp를 채워나간다.
        // 이 때, 이차원 누적합이 필요하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 칸의 문자
        String[] map = new String[n];
        for (int i = 1; i < map.length; i++)
            map[i] = br.readLine();

        long[][][] dp = new long[n + 1][m + 1][3];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++)
                    // 누적합 처리
                    // 값이 커, 나머지 연산을 취하므로, 음수가 되는 경우를 보정해준다.
                    dp[i][j][k] = (dp[i - 1][j][k] + dp[i][j - 1][k] - dp[i - 1][j - 1][k] + LIMIT) % LIMIT;
                
                switch (map[i].charAt(j)) {
                    // E인 경우 dp[i][j][0]의 값을 하나만 증가
                    case 'E' -> dp[i][j][0] = (dp[i][j][0] + 1) % LIMIT;
                    // S인 경우, (i, j)보다 더 적거나 같은 (x, y) 좌표에서 찾을 수 있는 E의 값만큼 dp[i][j][1]의 값 증가
                    case 'S' -> dp[i][j][1] = (dp[i][j][1] + dp[i][j][0]) % LIMIT;
                    // M인 경우, S와 마찬가지.
                    case 'M' -> dp[i][j][2] = (dp[i][j][2] + dp[i][j][1]) % LIMIT;
                }
            }
        }
        // 답 출력
        System.out.println(dp[n][m][2]);
    }
}