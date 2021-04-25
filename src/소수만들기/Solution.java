package 소수만들기;

import java.util.Arrays;

public class Solution {
    static boolean[] dp;
    static int count;

    public static void main(String[] args) {
        int[] nums = {1, 2, 7, 6, 4};

        dp = new boolean[1000 + 999 + 998 + 1];
        count = 0;
        Arrays.fill(dp, true);

        for (int i = 2; i < dp.length; i++) {   // 소수는 dp로 미리 구해두자.
            if (dp[i]) {
                for (int j = 2; i * j < dp.length; j++)
                    dp[i * j] = false;
            }
        }
        selectThreeNum(nums, 0, 0, -1);
        System.out.println(count);
    }

    static void selectThreeNum(int[] nums, int sum, int depth, int preSelectedIdx) {
        // 백트래킹을 이용하여 숫자들의 조합의 경우를 뽑아내자.
        if (depth == 3) {
            if (dp[sum])    // 구해진 숫자의 합이 소수일 때 count 증가.
                count++;
            return;
        }

        for (int i = preSelectedIdx + 1; i < nums.length; i++)
            selectThreeNum(nums, sum + nums[i], depth + 1, i);
    }
}