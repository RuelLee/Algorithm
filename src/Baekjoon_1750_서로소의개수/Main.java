/*
 Author : Ruel
 Problem : Baekjoon 1750번 서로소의 개수
 Problem address : https://www.acmicpc.net/problem/1750
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1750_서로소의개수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 10_000_003;

    public static void main(String[] args) throws IOException {
        // 어떤 수열 s가 주어진다.
        // 한 개 이상의 수를 선택하여 최대공약수가 1이 되는 것의 개수를 구하라
        //
        // DP, 유클리드 호제법 문제
        // dp[idx][최대공약수] = 경우의 수로 dp를 세운다.
        // idx까지의 수들을 선택하여 최대공약수가 되는 경우의 수를 dp로 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 수열의 크기 n
        int n = Integer.parseInt(br.readLine());
        int[] nums = new int[n];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(br.readLine());
            max = Math.max(max, nums[i]);
        }
        
        // dp
        long[][] dp = new long[n][max + 1];
        // 각 i번째 수 하나만 선택했을 때의 초기값
        for (int i = 0; i < nums.length; i++)
            dp[i][nums[i]]++;

        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                // dp[i][j]가 되는 경우의 수가 0인 경우 건너뜀
                if (dp[i][j] == 0)
                    continue;

                // i+1번째 수를 선택하지 않는다면
                // dp[i][j]의 값이 그대로 dp[i+1][j]로 넘어간다.
                dp[i + 1][j] += dp[i][j];
                dp[i + 1][j] %= LIMIT;
                // i + 1 번째 수를 선택한다면 j와 nums[i+1]의 최대공약수의 위치에
                // 현재 dp[i][j]의 값을 더해준다.
                dp[i + 1][getGCD(j, nums[i + 1])] += dp[i][j];
                dp[i+1][getGCD(j, nums[i+1])] %= LIMIT;
            }
        }
        // n개의 수를 모두 살펴봤을 때,
        // 최대 공약수가 1인 경우의 수를 출력
        System.out.println(dp[n - 1][1]);
    }
    
    // 유클리드 호제법
    static int getGCD(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}