/*
 Author : Ruel
 Problem : Baekjoon 11440번 피보나치 수의 제곱의 합
 Problem address : https://www.acmicpc.net/problem/11440
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11440_피보나치수의제곱의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static final int LIMIT = 1_000_000_007;
    static HashMap<Long, long[][]> pows;

    public static void main(String[] args) throws IOException {
        // 0번째 피보나치 수는 0이고, 1번째 피보나치 수는 1이다.
        // n번째 피보나치수까지의 제곱의 합을 구하라
        // 값이 크므로 1,000,000,007로 나눈 나머지를 구한다.
        //
        // 분할 정복 문제
        // 정확한 이유는 모르겠지만 , 피보나치 수를 쭉 적어보면
        // 0 1 1 2 3 5 8 13 21 34, ...
        // 제곱을 적으면
        // 0 1 1 4 9 25 64 169 441 1156
        // 제곱의 합을 적으면
        // 0 1 2 6 15 40 104 273 814 1970
        // 제곱의 합을 다시 적으면
        // 0 1 2 (9 - 3 * 1) (25 - 5 * 2) (64 - 8 * 3) (169 - 13 * 5) (441 - 21 * 8)\
        // 과 같이 된다.
        // f(x)를 x번째 피보나치 수의 합이라고 하고, Ax를 x번째 피보나치수라 할 때
        // f(x) = (Ax+1)^ 2 - Ax+1 * Ax-1이 성립한다.
        // 따라서 분할 정복을 통해 Ax+1과 Ax-1을 구해 위 식에 넣으면 답을 구할 수 있다.
        // (An)     ( 1 1 )             (A1)
        // (An-1) = ( 1 0 )의 n-1제곱 * (A0) 으로 구할 수 있다.
        // 단위 행렬의 n-1 제곱을 분할 정복으로 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 구해야하는 n번째 피보나치 수
        long n = Long.parseLong(br.readLine());

        pows = new HashMap<>();
        pows.put(-1L, new long[][]{{0, 1}, {0, 0}});
        pows.put(0L, new long[][]{{1, 0}, {0, 1}});
        pows.put(1L, new long[][]{{1, 1,}, {1, 0}});
        // 1번째, 0번째 피보나치 수
        long[][] a1a0 = new long[][]{{1}, {0}};

        // n+1번째 피보나치 수
        long anplus1 = multiple(a1a0, getPow(n))[0][0];
        // n-1번째 피보나치 수
        long anminus1 = multiple(a1a0, getPow(n - 2))[0][0];
        // 답
        long answer = (anplus1 * (anplus1 - anminus1 + LIMIT)) % LIMIT;
        System.out.println(answer);
    }

    // 단위 행렬의 n제곱
    static long[][] getPow(long n) {
        // 값이 없는 경우
        if (!pows.containsKey(n)) {
            // 짝수 일 땐, (n/2)제곱의 행렬을 서로 곱해 구해준다.
            if (n % 2 == 0)
                pows.put(n, multiple(getPow(n / 2), getPow(n / 2)));
            else        // 홀수일 땐, (n / 2)제곱의 행렬을 곱한 뒤 단위 행렬을 한 번 곱해준다.
                pows.put(n, multiple(getPow(1), multiple(getPow(n / 2), getPow(n / 2))));
        }
        return pows.get(n);
    }

    // 행렬의 곱
    static long[][] multiple(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[i].length; k++)
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % LIMIT;
            }
        }
        return c;
    }
}