/*
 Author : Ruel
 Problem : Baekjoon 16132번 그룹 나누기 (Subset)
 Problem address : https://www.acmicpc.net/problem/16132
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16132_그룹나누기_Subset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1부터 n까지의 수들이 주어진다.
        // 수들을 두 그룹으로 나누되, 합이 같게끔하고자 한다.
        // 가능한 경우의 수를 구하라
        //
        // DP 문제
        // n이 최대 50으로 크지 않으므로 dp를 통해 구할 수 있다.
        // dp[선택한 가장 큰 수][합] = 경우의 수
        // 또한 만약 n이 3의 경우
        // 1 2 / 3, 3 / 1 2의 같은 경우가 모두 세어지므로
        // 2로 나눈 값을 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 1 ~ n
        int n = Integer.parseInt(br.readLine());
        
        // dp[선택한 가장 큰 수][합] = 경우의 수
        long[][] dp = new long[n + 1][(n * (n + 1) / 2) + 1];
        // 수를 하나도 선택하지 않은 경우의 수 1가지
        dp[0][0] = 1;
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // j합에 도달하는 경우가 없다면 건너뛴다.
                if (dp[i][j] == 0)
                    continue;

                // i+1를 상대팀에 넣어 합을 증가시키지 않거나
                dp[i + 1][j] += dp[i][j];
                // 우리팀에 넣어 합을 증가시키는 경우 모두 계산.
                dp[i + 1][j + i + 1] += dp[i][j];
            }
        }

        // 만약 1 ~ n까지의 합이 짝수 아니어서 정확히 같은 값으로 나눌 수 없다면
        // 0을 출력하고
        // 가능하다면 해당하는 값을 찾아, 2를 나눈 값을 출력한다.
        System.out.println((n * (n + 1) / 2) % 2 == 0 ? dp[n][n * (n + 1) / 4] / 2 : 0);
    }
}
