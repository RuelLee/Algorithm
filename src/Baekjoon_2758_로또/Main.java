/*
 Author : Ruel
 Problem : Baekjoon 2758번 로또
 Problem address : https://www.acmicpc.net/problem/2758
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2758_로또;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // T개의 테스트케이스가 주어진다.
        // m개의 숫자 중 n개의 숫자를 고르려고 한다.
        // 단 이전에 골랐던 수보다 적어도 2배가 되는 수를 고른다.
        // 예를 들어, n = 4, m  = 10일 때, 1,2,4,8, 1,2,4,9, 1,2,4,10, 1,2,5,10 과 같이 고를 수 있다.
        // 만들 수 있는 가짓수는?
        //
        // DP문제
        // 이차원 배열을 통해
        // dp[i][j] = 마지막 수가 i일 때, j개를 고르는 방법의 가짓수를 계산하여 저장한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // m이 최대 2000, n은 최대 10까지 주어진다.
        long[][] dp = new long[2001][11];
        dp[0][0] = 1;
        // 각 수를 한 개만 고르는 가짓수 1가지.
        for (int i = 1; i < dp.length; i++)
            dp[i][1] = 1;

        // 1부터 2000까지의 수들을 살펴보며
        for (int i = 1; i < dp.length; i++) {
            // 각 수까지 j개를 선택했을 때의 경우의 수가
            for (int j = 1; j < dp[i].length - 1; j++) {
                // 0이라면, j + 1도 살펴볼 필요 x
                if (dp[i][j] == 0)
                    break;

                // 경우의 수가 존재한다면
                // i보다 2배 이상인 수들 k에 대해서
                // 해당 경우 + k라는 수를 덧붙여
                // k까지 j + 1장의 카드를 선택하는 경우의 수가 된다.
                // dp[k][j+1]에 dp[i][j]를 더해준다.
                for (int k = i * 2; k < dp.length; k++)
                    dp[k][j + 1] += dp[i][j];
            }
        }
        // 모든 경우의 대해 계산이 끝났다.

        // 테스트케이스에 해당하는 DP값을 찾고 출력만 해주면 된다.
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < testCase; i++) {
            // 주어지는 n, m
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            // dp[m][n]에 계산된 결과는 마지막 수가 m일 때이므로
            // m보다 작은 경우도 답에 포함시켜야한다.
            // 해당하는 값들을 모두 더해준 뒤 출력.
            long sum = 0;
            while (dp[m][n] > 0)
                sum += dp[m--][n];
            sb.append(sum).append("\n");
        }
        // 전체 답안 출력.
        System.out.print(sb);
    }
}