/*
 Author : Ruel
 Problem : Baekjoon 13398번 연속합 2
 Problem address : https://www.acmicpc.net/problem/13398
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13398_연속합2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정수로 이루어진 임의의 수열이 주어진다.
        // 이 때 연속한 수들의 합을 최대로 하고자 한다.
        // 연속한 수 들 중 하나의 수를 제거할 수 있다고 한다.
        // 10, -4, 3, 1, 5, 6, -35, 12, 21, -1이 주어진다면
        // 하나의 수를 제거하지 않았을 때는 12 + 21 = 33이고
        // 하나의 수를 제거한다면 10-4+3+1+5+6+12+21 로 54가 된다.
        //
        // DP 문제
        // dp를 통해 차근차근 수들을 살펴보며, 하나의 수를 제거했을 때와 제거하지 않았을 때의
        // 연속된 수열의 최대합을 구해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp[i] i까지의 연속된 수열의 합 중 최대값
        // dp[i][0] 수를 제거하지 않았을 때
        // dp[i][1] 수를 하나 제거했을 때
        int[][] dp = new int[n][2];
        dp[0][0] = nums[0];
        int max = nums[0];
        // 첫번째 값은 초기값으로 세팅 후
        // 두번째 값부터 살펴본다.
        for (int i = 1; i < dp.length; i++) {
            // 수를 제거하지 않는다면 연속된 수열의 합은
            // 이전 dp[i-1][0]에 현재 값을 더하는 경우
            // 혹은 이전 값들은 모두 버리고 현재 nums[i]로 부터 새로 시작하는 경우가 있다.
            // 둘 중 큰 값을 기록
            dp[i][0] = Math.max(dp[i - 1][0] + nums[i], nums[i]);

            // 하나의 수를 제거하는 경우
            // 값이 제거되지 않은 연속한 수열(dp[i-1][0])에서 현재값(nums[i])을 제거하여 연속한 수열에 포함시키는 경우
            // 혹은 이미 하나의 값이 제거된 값(dp[i-1][1])에 현재값을 포함시키는 경우
            dp[i][1] = Math.max(dp[i - 1][0], dp[i - 1][1] + nums[i]);

            // 제거했든 하지않았던 현재 두 값 중 큰 값을
            // max와 비교하여 더 큰 값을 남겨둔다.
            max = Math.max(max, Math.max(dp[i][0], dp[i][1]));
        }

        // 최종적으로 구한 최대값을 출력한다.
        System.out.println(max);
    }
}