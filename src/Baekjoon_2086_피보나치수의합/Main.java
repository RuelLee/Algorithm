/*
 Author : Ruel
 Problem : Baekjoon 2086번 피보나치 수의 합
 Problem address : https://www.acmicpc.net/problem/2086
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2086_피보나치수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static long[][] factor = {{1, 1}, {1, 0}};
    static long[][] first = {{1}, {0}};
    static HashMap<Long, long[][]> memo;
    static int limit = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        // 피보나치 수열의 a ~ b항 까지의 합을 구하는 프로그램을 작성하라
        // 답이 커질 수 있으므로, 마지막 아홉자리만 구한다.
        // 즉 1,000,000,000으로 나눈 나머지를 구한다.
        //
        // 분할제곱을 이용한 행렬의 제곱 문제
        // 피보나치 수열의 합의 경우 n번째 항까지의 합은 n + 2번째 항 - 1 값과 같다.
        // 따라서 f(b + 2) - 1 - (f(a + 1) - 1)  = f(b + 2) - f(a + 1)이다.
        // ( f(n)  )    ( 1 1 )^(n - 1)   ( f(1) )
        // ( f(n-1)) =  ( 1 0 )         * ( f(0) )
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        memo = new HashMap<>();
        memo.put(1L, factor);

        // 답을 출력한다.
        int answer = (getFIbo(b + 2) - getFIbo(a + 1) + limit) % limit;
        System.out.println(answer);
    }

    // 피보나치 수를 구한다.
    static int getFIbo(long n) {
        // f(1), f(2)는 1
        if (n <= 2)
            return 1;

        // n >=3 일 경우, factor를 n - 1 제곱한 값과 first를 행렬 곱을 행하여
        // 1행 1렬이 피보나치 수이다.
        return (int) matrixMultiple(getPow(n - 1), first)[0][0];
    }

    // factor의 n제곱을 구한다.
    static long[][] getPow(long n) {
        // 계산한 적이 있다면 바로 참조하여 반환한다.
        if (!memo.containsKey(n)) {
            // 짝수라면 factor의 (n/2) 제곱 끼리 곱해 factor의 n제곱을 구한다.
            if ((n & 1) == 0)
                memo.put(n, matrixMultiple(getPow(n / 2), getPow(n / 2)));
            // 홀수라면 factor의 (n / 2)제곱 끼리 곱한 후, 다시 factor를 한번 더 곱한 값을 구한다.
            else
                memo.put(n, matrixMultiple(getPow(n / 2), matrixMultiple(getPow(n / 2), getPow(1))));
        }
        // 답을 반환한다.
        return memo.get(n);
    }

    // 행렬의 곱을 구한다.
    static long[][] matrixMultiple(long[][] a, long[][] b) {
        long[][] answer = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[i].length; k++) {
                    answer[i][j] += a[i][k] * b[k][j];
                    answer[i][j] %= limit;
                }
            }
        }
        return answer;
    }
}