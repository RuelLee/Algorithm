/*
 Author : Ruel
 Problem : Baekjoon 2259번 두더지 잡기
 Problem address : https://www.acmicpc.net/problem/2259
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2259_두더지잡기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        int[][] moles = new int[n + 1][];
        moles[0] = new int[3];
        for (int i = 1; i < moles.length; i++)
            moles[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(moles, Comparator.comparingInt(o -> o[2]));

        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;
        int max = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j] != Integer.MIN_VALUE &&
                        moles[i][2] - moles[j][2] >= calcDistance(j, i, moles) / s) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    max = Math.max(max, dp[i]);
                }
            }
        }
        System.out.println(max);
    }

    static double calcDistance(int start, int end, int[][] moles) {
        return Math.sqrt(Math.pow((double) moles[start][0] - moles[end][0], 2) +
                Math.pow((double) moles[start][1] - moles[end][1], 2));
    }
}