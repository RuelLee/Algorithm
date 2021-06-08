package 피보나치수2;

import java.util.Scanner;

public class Main {
    static long[] dp;

    public static void main(String[] args) {
        // 피보나치 수와 같다.
        // 단 숫자 범위가 커지므로 long 값으로 계산하자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        dp = new long[n + 1];
        dp[1] = 1;
        for (int i = 2; i < dp.length; i++)
            dp[i] = dp[i - 1] + dp[i - 2];

        System.out.println(dp[n]);
    }
}