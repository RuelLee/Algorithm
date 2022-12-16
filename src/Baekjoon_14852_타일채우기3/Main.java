/*
 Author : Ruel
 Problem : Baekjoon 14852번 타일 채우기 3
 Problem address : https://www.acmicpc.net/problem/14852
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14852_타일채우기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    // 2차원 배열로 가능한 경우를 수로 나타낸다
    // cases[i][j] = i인 상태에서 j인 상태를 만드는 방법.
    static int[][] cases = {
            {2, 1, 1, 1},
            {1, 0, 1, 0},
            {1, 1, 0, 0},
            {1, 0, 0, 0}};

    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 2 x n 크기의 벽을 2*1, 1*2, 1*1 타일로 채우는 경우의 수를 구해보자.
        //
        // DP문제
        // 벽을 채울 때 채워지는 상황은 총 4가지이다.
        // n번째 벽이 두 칸 모두 비워져있어, 1*1 타일 2개 or 2*1 타일로 채우는 경우.
        // n번째 벽이 윗 칸만 채워져있어, 1*1 타일로 마저 채우는 경우
        // n번째 벽이 아랫칸만 채워져있어, 1*1 타일로 마저 채우는 경우
        // n-1번째 벽에서 1*2 타일을 두 개 사용하여 n번째 벽이 모두 차있는 경우.
        // 위 상황들을 고려하여 dp를 세운다.
        // dp[i][0] = 모두 비워져있는 경우, dp[i][1] = 윗 칸만 차 있는 경우,
        // dp[i][2] = 아랫칸만 차 있는 경우, dp[i][3] = 모든 칸이 차 있는 경우
        // 그리고 현재 상황들을 고려하여 다음 상황으로 가능한 경우를 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 벽이 n까지 주어진다고 n개의 공간을 할당할 필요는 없다.
        // 항상 직전 상황에 대해서만 값이 필요하므로
        // 2개의 공간으로 번갈아가며 계산한다.
        long[][] dp = new long[2][4];
        // 처음 모두 비어있는 경우 1가지.
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            // 다음 칸에 대한 계산값들을 비워둔다.
            Arrays.fill(dp[(i + 1) % 2], 0);
            for (int j = 0; j < dp[i % 2].length; j++) {
                // 현재 i번째 칸이 j인 상태.
                for (int k = 0; k < cases[j].length; k++) {
                    // i번째 칸이 j인 상태에서
                    // i+1번째 칸을 k로 만드는 경우의 수를 계산한다.
                    dp[(i + 1) % 2][k] += dp[i % 2][j] * cases[j][k];
                    dp[(i + 1) % 2][k] %= LIMIT;
                }
            }
        }
        // 0번째 칸부터 계산했으므로
        // n번째 칸이 모두 비워져있는 경우(n-1칸이 모두 차있는 경우)의 수를 출력한다.
        System.out.println(dp[n % 2][0]);
    }
}