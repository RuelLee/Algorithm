package 피보나치수;

import java.util.Scanner;

public class Main {
    static int[] memo;

    public static void main(String[] args) {
        // 재귀를 통해 구하게 된다면, 연산 수가 너무 많다.
        // DP를 사용하여 0부터 n까지 값을 선형적으로 계산하는 방법이 있고,
        // memoization 을 활용하여, 값을 구한 적 있다면 구했던 값을 참조하는 식으로 연산을 줄여 구할 수도 있다.
        // 여기선 메모이제이션을 활용하여 풀어보자.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        memo = new int[46];
        memo[1] = memo[2] = 1;
        System.out.println(fibonacci(n));
    }

    static int fibonacci(int r) {
        if (memo[r] == 0)
            memo[r] = fibonacci(r - 1) + fibonacci(r - 2);
        return memo[r];
    }
}