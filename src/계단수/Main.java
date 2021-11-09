/*
 Author : Ruel
 Problem : Baekjoon 1562 계단 수
 Problem address : https://www.acmicpc.net/problem/1562
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 계단수;

import java.util.Scanner;

public class Main {
    static long[][][] dp;
    static final int remainder = 1_000_000_000;

    public static void main(String[] args) {
        // DP와 비트마스킹을 이용한 문제!
        // 이웃한 자리와 1차이가 나는 숫자들을 계단 수라고 한다(ex 123, 121, 45656 ....)
        // 자리의 개수 n이 주어질 때, 0부터 9까지 숫자를 모두 사용한 게단 수는 모두 몇개인지 구하여라
        // 답은 10억의 나머지 값으로 한다
        // 답이 10억의 나머지인 것을 보면 당연히 완전탐색을 사용하면 절대 풀지 못한다
        // 각 자리에 숫자 별로 총 사용된 숫자의 종류까지 표시하는 방법의 DP로 풀어야한다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        dp = new long[n][10][1024];     // n번째 자리, 현재 자리의 숫자, 사용된 숫자의 종류를 나타내는 비트마스크
        for (int i = 1; i < dp[0].length; i++)      // 첫자리엔 0을 제외한 다른 숫자들이 올 수 있는 경우가 하나씩 있다.
            dp[0][i][1 << i] = 1;

        for (int i = 0; i < dp.length - 1; i++) {       // 자리별로 하나씩 늘려가며
            for (int j = 0; j < dp[i].length; j++) {    // 모든 숫자
                for (int k = 0; k < dp[i][j].length; k++) {
                    if (dp[i][j][k] != 0) {         // 비트마스크 중 그 값이 존재하는 경우에만
                        if (j < 9) {        // 현재 숫자가 9보다 작다면
                            // 자신보다 큰 숫자를 선택하는 경우가 가능하다
                            // i+1번째에는 자신보다 1 큰 수를 선택하고, 현재 비트마스킹된 k값에, j+1 값 또한 추가시켜 넘겨준다
                            dp[i + 1][j + 1][k | 1 << (j + 1)] += dp[i][j][k];
                            dp[i + 1][j + 1][k | 1 << (j + 1)] %= remainder;
                        }
                        if (j > 0) {        // 현재 숫자가 0보다 크다면
                            // 자신보다 작은 숫자를 선택하는 경우가 가능하다
                            // i+1번째에는 자신보다 1 작은 수를 선택하고, 현재 비트마스킹된 k값에 j-1 값 또한 추가시켜 넘겨준다.
                            dp[i + 1][j - 1][k | 1 << (j - 1)] += dp[i][j][k];
                            dp[i + 1][j - 1][k | 1 << (j - 1)] %= remainder;
                        }
                    }
                }
            }
        }
        long sum = 0;
        // 값들 중 우리가 원하는 건 0~9까지 모두 사용된 계단수이다.
        // 0~9중 어느 숫자로 끝났던, 비트마스킹된 값이 111111111(2) 즉 (2^10) - 1인 값들을 모두 더해준다.
        for (int i = 0; i < 10; i++) {
            sum += dp[n - 1][i][1023];
            sum %= remainder;
        }
        System.out.println(sum);
    }
}