package 피보나치수3;

import java.util.HashMap;
import java.util.Scanner;

public class Main2 {
    static HashMap<Long, int[][]> memo;

    public static void main(String[] args) {
        // 이 문제는 피보나치 수열을 제곱으로 나태낼 수도 있다는 점으로 인해
        // 제곱의 분할정복을 통한 방법으로도 해결할 수 있다.
        // An+2 = An+1 + An, A0 = 0,  A1 = 1
        // ( An+1 )    ( 1 1 )     ( An   )
        // ( An   ) =  ( 1 0 )  *  ( An-1 )
        // ( An+1 )    ( 1 1 )^n   ( A1   )
        // ( An   ) =  ( 1 0 )  *  ( A0   )
        // 피보나치 수를 행렬의 제곱으로 구할 수 있게 되었다.
        // 짝수일 때는 A^2n = A^n * A^n 으로 바꿀 수 있고,
        // 홀수 일 때는 A^2n+1 = A^n * A^n * A로 바꿀 수 있다.

        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();

        memo = new HashMap<>();
        memo.put(0L, new int[][]{{0, 0}, {0, 0}});
        memo.put(1L, new int[][]{{1, 1}, {1, 0}});

        System.out.println(getFibonacci(n)[1][0]);
    }

    static int[][] getFibonacci(long n) {
        if (memo.containsKey(n))
            return memo.get(n);

        if (n % 2 == 0)
            memo.put(n, multiple(getFibonacci(n / 2), getFibonacci(n / 2)));
        else
            memo.put(n, multiple(multiple(getFibonacci((n - 1) / 2), getFibonacci((n - 1) / 2)), getFibonacci(1)));

        return memo.get(n);
    }

    static int[][] multiple(int[][] a, int[][] b) {
        int[][] answer = new int[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    answer[i][j] += ((long) a[i][k] * b[k][j]) % 1000000;
                }
                answer[i][j] %= 1000000;
            }
        }
        return answer;
    }
}