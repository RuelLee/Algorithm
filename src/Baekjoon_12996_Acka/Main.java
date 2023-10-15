/*
 Author : Ruel
 Problem : Baekjoon 12996번 Acka
 Problem address : https://www.acmicpc.net/problem/12996
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12996_Acka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 앨범에 n곡의 곡이 수록된다.
        // 각 곡은 dotorya, kesakiyo, hongjun7 중 최소 한 명이 불러야한다.
        // 즉 각 곡은 한 명 혹은 두 명 또는 세 명 모두 함께 부를 수도 있다.
        // 각 사람이 앨범에서 부르는 노래의 개수가 주어질 때
        // 만들 수 있는 앨범 종류의 가짓수는?
        //
        // DP 문제
        // 노래의 순서, 각 사람들이 부를 수있는 남은 곡의 수가 필요하므로
        // 4차원 배열로 풀 수 있다.
        // dp[노래][d][k][h]로 세운다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 앨범에 수록되는 노래의 수
        int s = Integer.parseInt(st.nextToken());
        // 첫번째 사람이 부르는 노래의 수
        int d = Integer.parseInt(st.nextToken());
        // 두번째 사람이 부르는 노래의 수
        int k = Integer.parseInt(st.nextToken());
        // 세번째 사람이 부르느 노래의 수
        int h = Integer.parseInt(st.nextToken());
        
        // 4차원 배열
        int[][][][] dp = new int[s + 1][d + 1][k + 1][h + 1];
        // 0번째 곡에 아무도 노래를 부르지 않은 경우의 수 1
        dp[0][0][0][0] = 1;
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int l = 0; l < dp[i][j].length; l++) {
                    for (int m = 0; m < dp[i][j][l].length; m++) {
                        if (dp[i][j][l][m] == 0)
                            continue;
                        
                        // i번째 노래를 한 명이 부르는 경우
                        // 첫번째 사람이 아직 노래를 다 부르지 않은 경우
                        if (j < d)
                            dp[i + 1][j + 1][l][m] = (dp[i + 1][j + 1][l][m] + dp[i][j][l][m]) % LIMIT;
                        // 두번째 ..
                        if (l < k)
                            dp[i + 1][j][l + 1][m] = (dp[i + 1][j][l + 1][m] + dp[i][j][l][m]) % LIMIT;
                        // 세번째 ..
                        if (m < h)
                            dp[i + 1][j][l][m + 1] = (dp[i + 1][j][l][m + 1] + dp[i][j][l][m]) % LIMIT;
                        
                        // i번째 노래를 두 명이 부르는 경우
                        // 첫번째, 두번째 사람이 함께 노래를 부르는 경우
                        if (j < d && l < k)
                            dp[i + 1][j + 1][l + 1][m] = (dp[i + 1][j + 1][l + 1][m] + dp[i][j][l][m]) % LIMIT;
                        // 두번째, 세번째
                        if (l < k && m < h)
                            dp[i + 1][j][l + 1][m + 1] = (dp[i + 1][j][l + 1][m + 1] + dp[i][j][l][m]) % LIMIT;
                        // 세번째, 첫번째
                        if (m < h && j < d)
                            dp[i + 1][j + 1][l][m + 1] = (dp[i + 1][j + 1][l][m + 1] + dp[i][j][l][m]) % LIMIT;
                        
                        // 세 사람이 함께 부르는 경우
                        if (j < d && l < k && m < h)
                            dp[i + 1][j + 1][l + 1][m + 1] = (dp[i + 1][j + 1][l + 1][m + 1] + dp[i][j][l][m]) % LIMIT;
                    }
                }
            }
        }

        // 마지막 노래까지 모든 사람이 자신에게 할당한 수의 노래를 모두 불렀어야하므로
        // dp[s][d][k][h]의 값을 출력한다.
        System.out.println(dp[s][d][k][h]);
    }
}