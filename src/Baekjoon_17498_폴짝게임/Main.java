/*
 Author : Ruel
 Problem : Baekjoon 17498번 폴짝 게임
 Problem address : https://www.acmicpc.net/problem/17498
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17498_폴짝게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int[][] dp = new int[n][m];
        for (int[] dd : dp)
            Arrays.fill(dd, Integer.MIN_VALUE);
        Arrays.fill(dp[0], 0);
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int rowJump = 1; rowJump <= d && i + rowJump < dp.length; rowJump++) {
                    for (int col = Math.max(j - (d - rowJump), 0); Math.abs(j - col) + rowJump <= d && col < dp[i + rowJump].length; col++)
                        dp[i + rowJump][col] = Math.max(dp[i + rowJump][col], dp[i][j] + map[i][j] * map[i + rowJump][col]);
                }
            }
        }

        System.out.println(Arrays.stream(dp[n - 1]).max().getAsInt());
    }
}