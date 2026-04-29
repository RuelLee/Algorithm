/*
 Author : Ruel
 Problem : Jungol 1068번 광부들의식사(Miners)
 Problem address : https://jungol.co.kr/problem/1068
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1068_광부들의식사Miners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 광산이 주어진과 매 시각 주어지는 음식이 n개 주어진다.
        // M고기 F생선 B빵으로 주어진다.
        // 음식을 먹은 광산의 광부들은 이전과 그 전전에 먹은 음식의 종류에 따라
        // 1가지 종류일 경우 1을, 2가지 종류일 경우 2를, 3가지 종류일 경우 3의 광석을 채굴한다.
        // 최대로 채굴할 수 있는 광석의 양을 구하라
        //
        // DP, 비트마스킹 문제
        // dp를 통해 dp[턴][첫번째 광산에 들어간 음식의 비트][두번째 광산에 들어간 음식의 비트] = 최대 채굴 광석의 양
        // 으로 정의한다.
        // 음식의 종류가 3가지이므로, 3가지 + 안 먹었을 때, 4가지 상태를 표현한다. 4진법을 활용한다.
        // 이전과 전전 상태를 표현하면 되므로 각각 16의 공간이 필요하다.
        // 메모리를 절약하기 위해 턴은 2개의 공간으로 번갈아가며 사용한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 매 시각 주어지는 음식
        int n = Integer.parseInt(br.readLine());
        char[] meals = br.readLine().toCharArray();

        // dp
        int[][][] dp = new int[2][16][16];
        // 초기화
        clear(dp[0]);
        dp[0][0][0] = 0;

        for (int i = 0; i < meals.length; i++) {
            // 다음 상태 공간 초기화
            clear(dp[(i + 1) % 2]);
            int meal = -1;
            // 이번에 들어오는 음식 선별
            switch (meals[i]) {
                case 'M' -> meal = 1;
                case 'F' -> meal = 2;
                case 'B' -> meal = 3;
            }

            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    // 불가능한 상태일 경우 건너뜀.
                    if (dp[i % 2][j][k] == Integer.MIN_VALUE)
                        continue;

                    // 첫번째 광산에 음식을 주는 경우
                    dp[(i + 1) % 2][(j * 4 + meal) % 16][k] = Math.max(dp[(i + 1) % 2][(j * 4 + meal) % 16][k],
                            dp[i % 2][j][k] + calcVariety(j * 4 + meal));
                    // 두번째 광산에 음식을 주는 경우
                    dp[(i + 1) % 2][j][(k * 4 + meal) % 16] = Math.max(dp[(i + 1) % 2][j][(k * 4 + meal) % 16],
                            dp[i % 2][j][k] + calcVariety(k * 4 + meal));
                }
            }
        }

        // 최종 상태에서 최대 채굴량을 구한다.
        int max = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++)
                max = Math.max(max, dp[n % 2][i][j]);
        }
        System.out.println(max);
    }

    // dp 초기화
    static void clear(int[][] array) {
        for (int[] a : array)
            Arrays.fill(a, Integer.MIN_VALUE);
    }

    // 비트를 보고 음식의 종류를 판별
    static int calcVariety(int n) {
        boolean[] variety = new boolean[4];
        while (n > 0) {
            variety[n % 4] = true;
            n /= 4;
        }
        int cnt = 0;
        for (int i = 1; i < variety.length; i++)
            cnt += variety[i] ? 1 : 0;
        return cnt;
    }
}