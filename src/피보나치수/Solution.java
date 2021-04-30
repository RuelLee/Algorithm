package 피보나치수;

public class Solution {
    public static void main(String[] args) {
        // 피보나치 수 -> 재귀로 풀게되면 너무 많은 함수호출이 생기며 stack overflow 위험.
        // 값도 저장해 두지 않는다면 연산마저 오래 걸림.
        // dp로 처리하는게 가장 깔끔

        int n = 5;
        int[] dp = new int[n + 1];

        if (n > 0)
            dp[1] = 1;

        for (int i = 2; i < dp.length; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
            dp[i] %= 1234567;
        }
        System.out.println(dp[n]);
    }
}