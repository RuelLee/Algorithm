/*
 Author : Ruel
 Problem : Baekjoon 1563번 개근상
 Problem address : https://www.acmicpc.net/problem/1563
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1563_개근상;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static final int LIMIT = 1_000_000;

    public static void main(String[] args) throws IOException {
        // 출결 상태는 출석, 지각, 결석이 있다.
        // 학기 중 지각을 두 번 이상했거나, 결석을 세 번 연속으로 한 경우, 개근상을 받을 수 없다.
        // 한 학기가 n일이라고 할 때, 개근상을 받을 수 있는 출결 정보의 개수는?
        // 값이 클 수 있으므로 1,000,000으로 나눈 나머지를 출력한다.
        //
        // DP 문제
        // DP를 통해, 현재 지각한 횟수와 연속하여 결석한 정보를 갖고서 3가지 출경 상태에 대해 계산해 나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // dp[출결일][지각][연속결석]
        int[][][] dp = new int[n + 1][2][3];
        dp[0][0][0] = 1;
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    // 맨 처음 현재 값을 LIMIT으로 모듈러 연산해 값을 줄여준다.
                    dp[i][j][k] %= LIMIT;
                    
                    // 정상적으로 출석한 경우
                    // 연속 결석의 수는 초기화된다.
                    dp[i + 1][j][0] += dp[i][j][k];
                    
                    // 지각한 경우
                    // 이미 한번 지각한 경우는 개근상을 받을 수 없으므로 해당 경우는 제외.
                    // 마찬가지로 연속 결석의 수는 초기화 
                    if (j != 1)
                        dp[i + 1][j + 1][0] += dp[i][j][k];
                    
                    // 결석한 경우
                    // 3번 연속 결석한 경우에는 개근상을 받을 수 없으므로
                    // 2번 연속 지각한 경우는 제외
                    // 다시 결석한 경우이므로 연속 결석의 수는 하나 증가
                    if (k != 2)
                        dp[i + 1][j][k + 1] += dp[i][j][k];
                }
            }
        }

        // 최종적으로 n일에 기록된 모든 상태에 대한 경우의 수를 더해주고
        // 1,000,000으로 모듈러 연산한 값을 출력한다.
        int sum = 0;
        for (int[] d : dp[n])
            sum += Arrays.stream(d).sum();
        sum %= LIMIT;
        System.out.println(sum);
    }
}