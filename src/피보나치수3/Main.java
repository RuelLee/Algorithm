package 피보나치수3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 입력이 100경까지 매우매우 크다.
        // 출력이 백만으로 나눈 나머지값이다.
        // 주로 정확한 값을 원하는게 아니라 나머지값을 원하는 경우,
        // 일정 주기로 값이 반복되는 경우가 많다.
        // 피사노 주기 ->
        // 피보나치 수열의 경우, 10^n의 나눈 나머지 값은 15 * 10 ^(n-1) 주기로 반복된다고 한다

        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();
        int divide = 1000000;
        int[] dp = new int[15 * divide / 10];
        dp[1] = 1;
        int target = (int) (n % dp.length);

        for (int i = 2; i < target + 1; i++)
            dp[i] = (dp[i - 1] + dp[i - 2]) % divide;

        System.out.println(dp[target]);
    }
}