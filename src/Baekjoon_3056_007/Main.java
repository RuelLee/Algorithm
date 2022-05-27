/*
 Author : Ruel
 Problem : Baekjoon 3056번 007
 Problem address : https://www.acmicpc.net/problem/3056
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3056_007;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 미션(n은 최대 20)과 각 미션에 대한 n명의 요원의 성공 확률이 주어진다
        // 모든 미션의 성공률을 가장 높게 각 미션을 배분할 때의 확률은?
        //
        // 비트마스킹을 활용한 DP문제
        // n이 최대 20이므로, int 범위 내에서 처리할 수 있다
        // 각 미션을 각 요원에게 배치해가며 가장 높은 확률을 갖는 값을 dp에 기록해두자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 각 요원의 미션 당 성공 확률
        int[][] successPercents = new int[n][];
        for (int i = 0; i < successPercents.length; i++)
            successPercents[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // i행에는 0 ~ i번의 미션이 할당됨을 의미하고
        // 열에는 미션이 할당된 요원들을 비트마스킹으로 나타낸다.
        // 0번 미션은 각 요원의 성공확률을 그대로 가져오면 된다.
        double[][] dp = new double[n][1 << n];
        for (int i = 0; i < n; i++)
            dp[0][1 << i] = (double) successPercents[0][i] / 100;

        // 0번 미션부터 n-1번 미션까지
        for (int i = 0; i < n - 1; i++) {
            // 비트마스킹을 확인해나간다.
            for (int j = 0; j < dp[i].length; j++) {
                // dp에 저장된 값이 없다면 해당하는 경우는 없는 경우.
                // 빠르게 건너뛰자.
                if (dp[i][j] == 0)
                    continue;

                // 그렇지 않고 저장된 값이 있다면
                // 아직 미션이 할당되지 않은 요원을 찾아야한다.
                for (int k = 0; k < n; k++) {
                    int bit = 1 << k;
                    // j와 bit를 &연산했을 때 0이라면, k요원은 미션을 아직 할당받지 않았다.
                    // 따라서 k번 요원에 j번 미션을 할당하는 경우를 살펴보고 그 때의 최대 성공률이 갱신되는지 확인하자.
                    if ((j & bit) == 0)
                        dp[i + 1][j | bit] = Math.max(dp[i + 1][j | bit], dp[i][j] * ((double) successPercents[i + 1][k] / 100));
                }
            }
        }
        // n개의 미션이 모두 할당되고, 모든 요원들에게도 미션이 할당된 dp의 값을 출력한다.
        System.out.println(dp[n - 1][(1 << n) - 1] * 100);
    }
}