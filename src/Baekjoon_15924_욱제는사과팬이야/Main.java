/*
 Author : Ruel
 Problem : Baekjoon 15924번 욱제는 사과팬이야!!
 Problem address : https://www.acmicpc.net/problem/15924
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15924_욱제는사과팬이야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_009;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 지도가 주어진다.
        // 각 칸엔 알파벳이 적혀있는데,
        // E -> 오른쪽으로 한칸
        // S -> 아래로 한칸
        // B -> 오른쪽 혹은 아래쪽으로 한칸
        // 이동한다는 것을 의미한다.
        // (n, m)으로 이동할 수 있는 모든 경우의 수를 구하라
        // 
        // DP 문제
        // 각 칸에 이를 수 있는 모든 경우의 수를 DP를 통해 구한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 지도
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 각 칸에 이르는 방법
        int[][] dp = new int[n][m];
        // 처음 부터 각 칸에 위치하는 경우, 1가지씩
        for (int[] d : dp)
            Arrays.fill(d, 1);
        
        // 각 칸의 알파벳이
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    // E라면 오른쪽 한칸
                    case 'E' -> right(i, j, dp);
                    // S라면 아래로 한칸
                    case 'S' -> down(i, j, dp);
                    // B라면 두 가지 모두 가능
                    case 'B' -> {
                        right(i, j, dp);
                        down(i, j, dp);
                    }
                }
            }
        }
        
        // (n-1, m-1) (0, 0에서 시작했으므로)에 도달하는 경우의 수 출력
        System.out.println(dp[n - 1][m - 1]);
    }

    // 오른쪽으로 한 칸 이동할 수 있다면
    // 오른쪽으로 한 칸에 이동하는 방법의 수는
    // 현재 칸에 도달하는 수만큼 늘어난다.
    static void right(int r, int c, int[][] dp) {
        if (c + 1 < dp[r].length) {
            dp[r][c + 1] += dp[r][c];
            dp[r][c + 1] %= LIMIT;
        }
    }

    // 아래로 한 칸 이동시킬 수 있다면
    // 아래로 한 칸에 이르는 방법의 수는
    // 현재 칸에 이르는 수만큼 늘어난다.
    static void down(int r, int c, int[][] dp) {
        if (r + 1 < dp.length) {
            dp[r + 1][c] += dp[r][c];
            dp[r + 1][c] %= LIMIT;
        }
    }
}