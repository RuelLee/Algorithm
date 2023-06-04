/*
 Author : Ruel
 Problem : Baekjoon 1823번 수확
 Problem address : https://www.acmicpc.net/problem/1823
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1823_수확;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 벼를 수확하려고 한다.
        // 각 벼는 양쪽의 가장 바깥쪽 벼들만 수확할 수 있으며
        // 각 벼의 가치 * 수확하는 순서에 따른 이익을 얻을 수 있다고 한다.
        // 벼를 수확하며 얻는 최대 이익은?
        //
        // DP 문제
        // DP를 통해 남아있는 상태에 따른 최대 이익을 차근차근 구해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 각 벼를 수확할 때의 가치
        int[] rices = new int[n];
        for (int i = 0; i < rices.length; i++)
            rices[i] = Integer.parseInt(br.readLine());
        
        // dp[i][j] 현재 남은 벼가 i ~ j 까지의 벼가 남았을 때
        // 이미 수확한 벼들의 최대 가치
        int[][] dp = new int[n][n];
        // 왼쪽 끝부터, 오른쪽 끝까지의 차이
        for (int diff = n - 1; diff > 0; diff--) {
            // 범위가 diff인 모든 상태를 살펴본다.
            for (int left = 0; left + diff < n; left++) {
                // left ~ diff의 벼가 남았을 때 얻은 최대 이익을 dp[left][left + diff]라 할 때
                // left나 diff를 수확하는 경우 얻는 이익을 계산하고 그 결과값과 이미 계산된 결과값을 비교하여
                // 더 큰 값을 남겨준다.
                // left를 수확하는 경우
                dp[left + 1][left + diff] = Math.max(dp[left + 1][left + diff], dp[left][left + diff] + rices[left] * (n - diff));
                // leff + diff를 수확하는 경우
                dp[left][left + diff - 1] = Math.max(dp[left][left + diff - 1], dp[left][left + diff] + rices[left + diff] * (n - diff));
            }
        }

        // 각 벼가 하나 남은 상태에서, 해당 벼를 수확할 때 얻는 이익을 구해
        // 최대 이익을 구한다.
        int max = 0;
        for (int i = 0; i < rices.length; i++)
            max = Math.max(max, dp[i][i] + rices[i] * n);
        
        // 결과값 출력
        System.out.println(max);
    }
}