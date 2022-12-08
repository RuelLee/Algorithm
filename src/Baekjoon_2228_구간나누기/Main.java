/*
 Author : Ruel
 Problem : Baekjoon 2228번 구간 나누기
 Problem address : https://www.acmicpc.net/problem/2228
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2228_구간나누기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수열이 주어진다
        // m개의 구간으로 나누려고 한다. 각 구간은
        // 1. 하나 이상의 연속된 수로 이루어져있으며
        // 2. 구간끼리 서로 겹치거나 인접하지 않는다.
        // 3. 정확히 m개의 구간이 있어야한다.
        // 각 구간들의 전체 수의 합을 최대로 하고자할 때 그 값은?
        //
        // DP문제
        // dp[i][j]를 통해, i번째 수를 포함하여, j개의 구간으로 나누었을 때 합의 최대값으로 정의한다.
        // 해당 값은
        // dp[i-1][j]에 i번째 수를 더하거나
        // = 이미 j개의 구간으로 나뉘어져있고, 마지막 구간에 i번째 수를 포함시키는 경우
        // dp[0][j-1] ~ dp[i-2][j-1] 값들 중 가장 큰 값에 i번째 수를 더한 값과 같다.
        // = i 이전 수들로 j - 1개의 구간을 만들고, i번째 수로 j번째 구간을 시작하는 경우.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // dp[i][j-1] = i번째 수를 포함하여 j개의 구간으로 나눌 때, 최대합.
        int[][] dp = new int[n][m];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MIN_VALUE);

        // 첫번째 수에 대한 초기값.
        dp[0][0] = Integer.parseInt(br.readLine());
        // m개의 구간으로 나누었을 때의 최대값
        // m개의 구간은 마지막 수까지 가지 않더라도 생길 수 있다.
        // m개의 구간이 나뉘어졌다면, 최대값인지 확인하고 갱신하자.
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < dp.length; i++) {
            int num = Integer.parseInt(br.readLine());
            // 구간이 1개인 경우는 앞 구간에 현재의 수를 붙이는 방법
            // 과 앞의 수들은 버리고 현재의 수부터 첫번째 구간을 시작하는 방법이 있다.
            dp[i][0] = Math.max(dp[i - 1][0] + num, num);

            // 2 ~ m개의 구간을 만든다.
            for (int j = 1; j < dp[i].length; j++) {
                // 이미 j + 1개의 구간이 만들어져있다면, 해당 구간에 현재 수를 덧붙이는 방법.
                if (dp[i - 1][j] != Integer.MIN_VALUE)
                    dp[i][j] = dp[i - 1][j] + num;

                // 0 ~ i - 2번째 dp값을 방문하면서
                // j - 1개의 구간이 만들어져있는지 확인한다.
                for (int k = 0; k < i - 1; k++) {
                    // 그리고 해당 값이 있다면 현재 수로 j번째 구간을 시작한다.
                    // 이미 계산된 위의 이미 생성된 j개의 구간에 i번째 수를 덧붙이는 경우의 최대합과
                    // 비교하여 큰 값을 남겨둔다.
                    if (dp[k][j - 1] != Integer.MIN_VALUE)
                        dp[i][j] = Math.max(dp[i][j], dp[k][j - 1] + num);
                }
            }
            // 생성된 i번째 수까지 고려된 m개의 구간합이 가장 큰 값이 확인하고 갱신한다.
            max = Math.max(max, dp[i][m - 1]);
        }
        // 발견된 m개의 구간합 중 가장 큰 값을 출력한다.
        System.out.println(max);
    }
}