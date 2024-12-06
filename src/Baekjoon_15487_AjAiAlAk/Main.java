/*
 Author : Ruel
 Problem : Baekjoon 15487번 A[j]-A[i]+A[l]-A[k]
 Problem address : https://www.acmicpc.net/problem/15487
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15487_AjAiAlAk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기가 n인 배열 A가 주어졌을 때
        // i < j < k < l을 만족하는 (i, j, k, l) 중
        // A[j]-A[i]+A[l]-A[k]의 최댓값을 구하라
        //
        // dp 문제
        // dp[살펴본배열의원소수][현재완성된식의단계] = 값으로 정의한다.
        // dp[i][0] = -A[i]의 최대값
        // dp[i][1] = A[j] -A[i]의 최대값
        // dp[i][2] = A[j] -A[i] -A[k]의 최대값
        // dp[i][3] = A[j] -A[i] +A[l] -A[k]의 최대값으로 정의하고 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기가 n인 배열 A
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] nums = new int[n];
        for (int i = 0; i < nums.length; i++)
            nums[i] = Integer.parseInt(st.nextToken());
        
        // dp는 이전 단계의 값만 참고하므로
        // dp[살펴본 배열의 원소 수의 홀짝][현재 완성된 식의 단계] = 최대값으로 살펴본다.
        int[][] dp = new int[2][4];
        // 값의 범위가 100보다 작거나 같은 자연수이므로
        // 최소의 답이 -1_999_998이 될 수 있다.
        // 따라서 -200만으로 초기화
        Arrays.fill(dp[0], -2_000_000);
        // 처음 하나의 수만 주어졌을 땐, -a[i]의 최대값은 배열의 첫번째 원소 값 
        dp[0][0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < dp[i % 2].length; j++) {
                // -A[i]의 최대값
                dp[i % 2][0] = Math.min(dp[(i + 1) % 2][0], nums[i]);
                // A[j] -A[i]의 최대값
                dp[i % 2][1] = Math.max(dp[(i + 1) % 2][1], nums[i] - dp[(i + 1) % 2][0]);
                // A[j] -A[i] -A[k]의 최대값
                dp[i % 2][2] = Math.max(dp[(i + 1) % 2][2], dp[(i + 1) % 2][1] - nums[i]);
                // A[j] -A[i] +A[l] -A[k]의 최대값
                dp[i % 2][3] = Math.max(dp[(i + 1) % 2][3], dp[(i + 1) % 2][2] + nums[i]);
            }
        }
        // 결과 출력
        System.out.println(dp[(n + 1) % 2][3]);
    }
}