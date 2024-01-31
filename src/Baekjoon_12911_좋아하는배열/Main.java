/*
 Author : Ruel
 Problem : Baekjoon 12911번 좋아하는 배열
 Problem address : https://www.acmicpc.net/problem/12911
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12911_좋아하는배열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 1. 배열의 길이는 n이다.
        // 2. 배열의 채워져있는 수는 1보다 같거나 크고 k보다 같거나 작다.
        // 3. 연속한 수가 A, B일 때, A <= B 또는 A % B != 0 을 만족해야 한다.
        // n과 k가 주어질 때, 그러한 배열의 개수는 몇 개인지 출력하라
        //
        // 누적합, DP 문제
        // dp를 통해 dp[배열의 길이][끝나는 수] = 경우의 수로 계산한다.
        // 3번 조건을 보면 이후에 나오는 수는 이전에 나온 수보다 크거나
        // 작다면 앞의 수의 약수여서는 안된다.
        // 따라서 앞에 나온 모든 수, 경우의 수 누적합에서 자신의 배수들의 경우의 수만 제해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n, 수의 범위 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] dp = new int[n + 1][k + 1];
        // 길이가 1일 때, 각 수의 경우의 수는 1
        Arrays.fill(dp[1], 1);
        int[] psums = new int[n + 1];
        // 길이가 1일 때, 경우의 수 누적합은 k
        psums[1] = k;

        // 길이 i
        for (int i = 2; i < dp.length; i++) {
            // 1로 끝나는 경우는 1이 연속한 경우 한 가지밖에 없다.
            dp[i][1] = 1;
            // 해당 경우의 수 누적합에 추가.
            psums[i] = 1;

            // 끝나는 수가 2일 때부터는 계산.
            for (int j = 2; j < dp[i].length; j++) {
                // 먼저 길이 i - 1의 모든 경우의 수로 시작.
                dp[i][j] = psums[i - 1];
                // j의 배수들의 경우의 수를 빼준다.
                for (int m = 2; j * m < dp[i - 1].length; m++) {
                    dp[i][j] -= dp[i - 1][j * m];
                    dp[i][j] += LIMIT;
                    dp[i][j] %= LIMIT;
                }
                // 길이 i의 누적합에 dp[i][j] 값 추가.
                psums[i] += dp[i][j];
                psums[i] %= LIMIT;
            }
        }
        // 길이 n인 경우의 모든 경우의 수 출력
        System.out.println(psums[n]);
    }
}