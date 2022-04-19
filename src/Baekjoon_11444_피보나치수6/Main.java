/*
 Author : Ruel
 Problem : Baekjoon 11444번 피보나치 수 6
 Problem address : https://www.acmicpc.net/problem/11444
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11444_피보나치수6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    static HashMap<Long, long[][]> memo;
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n번째 피보나치 수를 구하라
        //
        // n 값이 최대 100경까지 주어진다
        // 일일이 계산해서는 절대 안된다
        // 점화식을 통해 행렬의 곱셈으로 표현하고, 이를 분할정복을 통해 계산하자
        // (An  )   (1 1)   (An-1)   (1 1)^n-2   (A2)
        // (An-1) = (1 0) * (An-2) = (1 0)     * (A1)
        // 처럼 나타낼 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long n = Long.parseLong(br.readLine());
        memo = new HashMap<>();
        memo.put(1L, new long[][]{{1, 1}, {1, 0}});

        System.out.println(getFibonacci(n));
    }

    static int getFibonacci(long n) {
        // n이 3보다 작을 때는 피보나치 수가 1이므로 1을 돌려준다.
        if (n < 3)
            return 1;

        // 3보다 같거나 크면, 행렬의 곱셈을 통해 분할정복으로 구해준다.
        long[][] matrix = getMultiple(n - 2);
        // 1행의 1열, 2열의 합이 피보나치수!
        return (int) Arrays.stream(matrix[0]).sum() % LIMIT;
    }

    static long[][] getMultiple(long n) {
        // n 값을 계산한 적이 없다면
        if (!memo.containsKey(n)) {
            // n 값이 짝수라면, n/2 제곱 * n/2 제곱으로 구한다.
            if (n % 2 == 0)
                memo.put(n, matrixMultiple(getMultiple(n / 2), getMultiple(n / 2)));
            // n 값이 홀수라면, (n-1)/2 제곱 * (n-1)*2 제곱 * 1 으로 나타낸다
            else
                memo.put(n, matrixMultiple(matrixMultiple(getMultiple(n / 2), getMultiple(n / 2)), getMultiple(1)));
        }
        // n 제곱에 해당하는 행렬을 가져온다.
        return memo.get(n);
    }

    static long[][] matrixMultiple(long[][] a, long[][] b) {
        // 행렬 a와 행렬 b를 곱한다
        long[][] answer = new long[a.length][b[0].length];
        for (int row = 0; row < a.length; row++) {      // a행렬의 행과
            for (int col = 0; col < b[0].length; col++) {       // b행렬의 열
                for (int i = 0; i < a[row].length; i++) {       // 각 원소들을 곱해 더해준다.
                   answer[row][col] += a[row][i] * b[i][col];
                   // 값이 10억7을 넘을 경우를 대비하여 나머지 연산을 한다.
                    answer[row][col] %= LIMIT;
                }
            }
        }
        return answer;
    }
}