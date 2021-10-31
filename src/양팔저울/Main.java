/*
 Author : Ruel
 Problem : Baekjoon 2629번 양팔저울
 Problem address : https://www.acmicpc.net/problem/2629
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 양팔저울;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // DP 문제!
        // 최대 30개의 추가 주어지므로, 일일이 모든 경우에 대해 생각해서는
        // 총 2의 30승의 가지수가 나온다.
        // 1 ~ n번까지의 추를 사용했을 때 만들 수 있는 가지수를 DP를 활용해서 차근차근 풀어가자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        boolean[][] dp = new boolean[n + 1][40001];     // 총 무게는 비교 하는 무게는 4만까지라고 한다.
        dp[0][0] = true;        // 0g 무게는 잴 수 있다 가정.
        for (int i = 1; i < dp.length; i++) {
            int num = sc.nextInt();     // 이번에 들어온 추
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i - 1][j]) {     // i-1 개의 추를 사용한 경우에 j 무게를 재는게 가능하다면
                    dp[i][j] = true;        // i개의 추를 사용한 경우에도 가능.
                    if (j + num < dp[i].length)     // j의 무게를 재는게 가능하다면 j + num의 무게를 재는 것도 가능
                        dp[i][j + num] = true;

                    if (j > num)        // j가 num보다 크다면 j - num의 무게를 재는 것도 가능하다.
                        dp[i][j - num] = true;
                    else            // num이 더 크다면 num - j 의 무게를 재는 것이 가능하다.
                        dp[i][num - j] = true;
                }
            }
        }

        // 완성된 DP의 n+1번째 row에는 n개의 추를 모두 혹은 일부만 사용했을 때 측정가능한 무게들이 담겨있다.
        int m = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            if (dp[dp.length - 1][sc.nextInt()])
                sb.append("Y ");
            else
                sb.append("N ");
        }
        System.out.println(sb);
    }
}