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
        // n * m 크기의 격자가 주어진다.
        // 각 칸에는 수가 주어진다.
        // 1행의 아무 칸에서 시작하여, n행 칸으로 도달한다.
        // 이 때 1행에서의 점수는 0점이고, 칸에서 칸으로 이동할 때, 두 칸의 곱만큼을 점수로 얻는다.
        // 또한 이동할 때의 맨해튼 거리는 d 이하로만 이동할 수 있다.
        //
        // dp 문제
        // dp를 통해 각 칸에 도달하는 최대 점수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n행 m열 거리 제한 d
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        
        // 각 칸의 수
        int[][] map = new int[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp
        // 음수가 답이 될 수 있으므로 각 칸에 가장 작은 음수값들로 채워넣는다.
        int[][] dp = new int[n][m];
        for (int[] dd : dp)
            Arrays.fill(dd, Integer.MIN_VALUE);
        // 1행에서 출발 점수는 0점
        Arrays.fill(dp[0], 0);
        // (i, j) 칸에서
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // (i + rowJump, col) 칸으로 이동한다.
                // rowJump는 1 ~ d까지
                for (int rowJump = 1; rowJump <= d && i + rowJump < dp.length; rowJump++) {
                    // col은 Math.max(j - (d - rowJump), 0) 에서 ~  Math.min(j + (d - rowJump)), m -1)까지 살펴본다.
                    for (int col = Math.max(j - (d - rowJump), 0); Math.abs(j - col) + rowJump <= d && col < dp[i + rowJump].length; col++)
                        dp[i + rowJump][col] = Math.max(dp[i + rowJump][col], dp[i][j] + map[i][j] * map[i + rowJump][col]);
                }
            }
        }

        // n행에서 얻을 수 있는 가장 큰 값을 찾아 출력한다.
        System.out.println(Arrays.stream(dp[n - 1]).max().getAsInt());
    }
}