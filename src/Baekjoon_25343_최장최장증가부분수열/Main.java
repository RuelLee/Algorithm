/*
 Author : Ruel
 Problem : Baekjoon 25343번 최장 최장 증가 부분 수열
 Problem address : https://www.acmicpc.net/problem/25343
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25343_최장최장증가부분수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어직고, 각 칸에는 1 ~ 10_000의 값이 들어있다.
        // 장 왼쪽 위 칸에서 가장 오른쪽 아래 칸까지 최단 경로로 이동하며
        // 가면서 얻는 값들을 순서대로 모아 증가하는 부분 수열의 길이가 가장 긴 형태로 만들어 주고자 한다.
        // 만들 수 있는 가장 긴 길이는?
        //
        // DP 문제
        // dp[i][j] = 현 위치의 수를 마지막 값으로 하는 최장 증가 수열의 길이
        // 로 하고 계산한다.
        // 따라서 dp[i][j]의 값을 구하려면 (0, 0) ~ (i, j) 값들 중 (i, j)보다 작은 경우에는
        // 해당 값 + 1로 길이를 만들 수 있고, 같은 경우에는 그대로 가져올 수 있다. 더 큰 경우는 불가능하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 배열
        int n = Integer.parseInt(br.readLine());
        // 각 칸의 값
        int[][] map = new int[n][n];
        StringTokenizer st;
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // dp[i][j] = 현 위치의 수를 마지막 값으로 하는 최장 증가 수열의 길이
        // 모두 자기 자신부터 시작하므로 1값을 채워야하지만, 0이 1이라고 생각하고 계산하자.
        int[][] dp = new int[n][n];
        // 얻는 최장 증가 수열의 길이들 중에서도 가장 큰 값
        int max = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // (i, j)의 값을 계산하기 위해
                for (int l = 0; l <= i; l++) {
                    for (int m = 0; m <= j; m++) {
                        // (l, m)과 비교한다.
                        // (i, j)값이 더 큰 경우 길이는 +1 할 수 있고
                        if (map[l][m] < map[i][j])
                            dp[i][j] = Math.max(dp[i][j], dp[l][m] + 1);
                        else if (map[l][m] == dp[i][j])     // 같은 경우는 같은 길이만큼을 가져올 수 있다.
                            dp[i][j] = Math.max(dp[i][j], dp[l][m]);
                        // 작은 경우는 불가능

                        // 최장 증가 수열의 길이들 중 가장 큰 값인지 값을 확인한다.
                        max = Math.max(max, dp[i][j]);
                    }
                }
            }
        }
        // 모든 칸들의 값들 중 가장 큰 값을 출력
        // 모든 칸이 처음 자기 자신만 있을 때의 길이 1을 0으로 보고 시작했으므로
        // max + 1 값이 답
        System.out.println(max + 1);
    }
}