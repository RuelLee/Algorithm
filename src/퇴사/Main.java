/*
 Author : Ruel
 Problem : Baekjoon 14501번 퇴사
 Problem address : https://www.acmicpc.net/problem/14501
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 퇴사;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] array = new int[n][2];
        for (int i = 0; i < array.length; i++) {
            array[i][0] = sc.nextInt();
            array[i][1] = sc.nextInt();
        }

        int[][] dp = new int[n + 1][n];

        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (array[i][0] > j)
                    dp[i][j] = dp[i - 1][j];

                dp[i][j] = dp[i - 1][j] < dp[i - 1][j - array[i][0]] + array[i][1] ? dp[i - 1][j - array[i][0]] + array[i][1] : dp[i - 1][j];
            }
        }
    }
}