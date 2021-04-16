package 멀리뛰기;

public class Solution {
    public static void main(String[] args) {
        // 피보나치 수열과 관련된 DP 문제.
        // 1칸 또는 2칸을 뛸 수 있으므로,
        // n번째 칸에 도달하는 방법은 n-1칸에서 1칸을 뛰거나, n-2칸에서 2칸을 뛰는 방법들의 합.
        int n = 4;

        long[] dp = new long[n];

        dp[0] = 1;
        if (n > 1)
            dp[1] = 1;

        for (int i = 2; i < dp.length; i++)
            dp[i] = (dp[i - 2] + dp[i - 1]) % 1234567;

        System.out.println(dp[n - 1]);
    }
}