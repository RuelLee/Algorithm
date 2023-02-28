/*
 Author : Ruel
 Problem : Baekjoon 11985번 오렌지 출하
 Problem address : https://www.acmicpc.net/problem/11985
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11985_오렌지출하;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 오렌지들을 포장하려 한다.
        // 오렌지들은 연속하여 최대 m까지 동시에 포장할 수 있다.
        // 상자에 오렌지들 담는 비용은 k + s *(a - b)이며
        // k는 상수이며, s는 담는 오렌지의 개수, a는 가장 큰 오렌지의 크기, b는 가장 작은 오렌지의 크기이다.
        // 전체 오렌지들을 포장하는 비용을 최소로 하고자할 때, 그 비용은?
        //
        // DP 문제
        // dp[i]를 i번째 오렌지를 담을 때까지의 최소 비용이라 정의하고 계산한다.
        // n이 최대 2만, m이 최대 1000이므로 (i - m + 1)번 오렌지부터 i번 오렌지까지 새로운 포장으로 담을 때
        // 포장 비용을 계산해서 비교해나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 오렌지의 크기가 최대 10억으로 주어지므로 long 타입으로 DP를 세운다.
        long[] dp = new long[n];
        Arrays.fill(dp, Long.MAX_VALUE);
        
        // 각 오렌지의 크기
        int[] oranges = new int[n];
        for (int i = 0; i < oranges.length; i++)
            oranges[i] = Integer.parseInt(br.readLine());

        // i번까지의 오렌지를 포장할 때의 최소 비용 계산.
        for (int i = 0; i < dp.length; i++) {
            // 현재 살펴보는 범위에서의 오렌지들의
            // 최대, 최소 크기
            int min, max;
            min = max = oranges[i];

            // j ~ i번 오렌지를 하나로 포장할 때를 계산한다.
            for (int j = i; j >= 0 && i - j < m; j--) {
                // 최대, 최소 크기 갱신.
                min = Math.min(min, oranges[j]);
                max = Math.max(max, oranges[j]);

                // 이 때의 비용은 k + 개수  * (max - min)이 되고
                // 만약 j가 0이라면 하나의 포장이므로 해당 비용만 계산하면 되고
                // 1이상이라면 (j - 1)까지의 포장 비용 + j ~ i까지의 포장 비용으로 계산한다.
                long cost = k + (long) (i - j + 1) * (max - min) + (j > 0 ? dp[j - 1] : 0);
                // 최소 비용을 갱신하는지 확인.
                dp[i] = Math.min(dp[i], cost);
            }
        }
        
        // n번까지 계산했을 때의 최소 비용 출력
        System.out.println(dp[n - 1]);
    }
}