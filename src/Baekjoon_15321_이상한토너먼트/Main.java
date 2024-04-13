/*
 Author : Ruel
 Problem : Baekjoon 15321번 이상한 토너먼트
 Problem address : https://www.acmicpc.net/problem/15321
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15321_이상한토너먼트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 참가자들 코딩력이 순서대로 주어진다.
        // 참가자들은 이웃한 참가자와 대진하여 코딩력이 더 높은 사람이 승리한다.
        // 하지만 그 때 관중들이 느끼는 지루함은 두 참가자들의 코딩력 차로 결정된다.
        // 모든 대진의 지루함 합을 최소화하고자 할 때, 그 합은?
        //
        // DP 문제
        // dp[i][j] = i번부터, j번 참가자들이 대진했을 때, 지루함 합의 최소값
        // 으로 정의하고 문제를 푼다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 참가자들의 코딩력
        int n = Integer.parseInt(br.readLine());
        int[] powers = new int[n];
        for (int i = 0; i < powers.length; i++)
            powers[i] = Integer.parseInt(br.readLine());
        
        // dp[i][j] = i ~ j번 참가자들이 대진했을 때, 지루함 합의 최소값
        int[][] dp = new int[n][n];
        // max[i][j] = i ~ j번 참가자들 중 가장 높은 코딩력
        int[][] max = new int[n][n];
        // dp 및 max 초기화
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            dp[i][i] = 0;
            max[i][i] = powers[i];
        }

        // 탐색하고자 하는 i와 j의 차이
        for (int diff = 1; diff < dp.length; diff++) {
            // 가장 왼쪽 참가자 left
            // 가장 오른쪽 참가자는 left + diff
            for (int left = 0; left + diff < dp.length; left++) {
                // 그 때의 left ~ left + diff 참가자들 중 최대 코딩력
                max[left][left + diff] = Math.max(max[left][left + diff - 1], max[left + diff][left + diff]);
                
                // dp[left][left + diff]는
                // mid가 left ~ left + diff -1 까지의 모든 값에 대해
                // dp[left][mid] + dp[mid +1][left + diff] + (max[left][mid]와 max[mid+1][left+diff]의 차)
                // 한 결과값
                for (int mid = left; mid < left + diff; mid++) {
                    dp[left][left + diff] = Math.min(dp[left][left + diff],
                            dp[left][mid] + dp[mid + 1][left + diff] + Math.abs(max[left][mid] - max[mid + 1][left + diff]));
                }
            }
        }
        
        // 모든 참가자들에 대해 계산한 결과값을 출력
        System.out.println(dp[0][n - 1]);
    }
}