/*
 Author : Ruel
 Problem : Baekjoon 2616번 소형기관차
 Problem address : https://www.acmicpc.net/problem/2616
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2616_소형기관차;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 객차들에 타고 있는 승객의 수가 주어진다
        // 이를 3개의 소형기관차가 나누어 최대 k개의 연속한 객차들씩 운송하려고 한다
        // 가장 많은 승객들을 운송하려할 때, 운송할 수 있는 승객들의 수는?
        //
        // DP문제
        // dp를 세우되, dp[소형기관차의개수][커버하는객차의번호]로 세우자
        // dp[n][m]일 경우, n개의 소형기차로 m번 객차까지 고려했을 때 최대 운송할 수 있는 승객들의 수라는 의미이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] passengerCars = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 누적합을 통해 승객의 수 계산을 줄여주자.
        int[] pSum = new int[n + 1];
        for (int i = 1; i < pSum.length; i++)
            pSum[i] = pSum[i - 1] + passengerCars[i - 1];

        int k = Integer.parseInt(br.readLine());

        // 기관차의 수는 최대3. 열차의 수는 n
        // 따라서 0번 열과 0번 행을 비워주기 위해 하나씩 더해 dp를 선언한다.
        int[][] dp = new int[4][n + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                // (j - k + 1)(or 1)번 객차부터 ~ j번 객차까지 승객의 수
                int passenger = (j - k < 0) ? pSum[j] : (pSum[j] - pSum[j - k]);
                // j - 1번 객차까지만 고려한 승객의 수와
                // (i - 1)개의 기관차가 (j - k + 1)번까지 커버하고
                // i번째 기관차가 (j - k + 1) ~ j번 객차를 운송할 때의 승객 수를 비교하여 더 큰 값을 dp[i][j]에 저장한다.
                dp[i][j] = Math.max(dp[i][j - 1], ((j - k < 0) ? 0 : dp[i - 1][j - k]) + passenger);
            }
        }

        // 최종적으로 기관차 3대가, n번 객차까지 모두 커버할 때의 값이
        // 최대 운송할 수 있는 승객의 수.
        System.out.println(dp[3][n]);
    }
}