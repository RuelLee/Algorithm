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

        for (int i = 2; i < dp.length; i++) {
            if (dp[i]) {
                for (int j = 2; i * j < dp.length; j++)
                    dp[i * j] = false;
            }
        }
        selectThreeNum(nums, 0, 0, -1);
        System.out.println(count);
    }

    static void selectThreeNum(int[] nums, int sum, int depth, int preSelectedIdx) {
        if (depth == 3) {
            if (dp[sum])
                count++;
            return;
        }

        for (int i = preSelectedIdx + 1; i < nums.length; i++)
            selectThreeNum(nums, sum + nums[i], depth + 1, i);
    }
}