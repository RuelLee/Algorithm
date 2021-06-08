package 피보나치수;

import java.util.Scanner;

public class Main {
    static int[] dp;

    public static void main(String[] args) {
        // 재귀를 통해 구하게 된다면, 연산 수가 너무 많다.
        // DP를 사용해야하는데, n이 주어질 때, 0부터 n까지 이전 두 숫자를 더해가며 채우는 방법과
        // 재귀를 사용하되 피보나치 값을 배열에 모두 저장해두고, 한번 구했던 피보나치 값은 바로 참조함으로 연산을 줄일 수 있다.
        // 첫번째 방법은 한번만 값을 구할 때 유리하고, 두번째는 한번 저장해둔 피보나치 값을을 지속적으로 참조할 때 유리하다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        dp = new int[46];
        dp[1] = dp[2] = 1;
        System.out.println(fibonacci(n));
    }

    static int fibonacci(int r) {
        if (dp[r] == 0)
            dp[r] = fibonacci(r - 1) + fibonacci(r - 2);
        return dp[r];
    }
}