/*
 Author : Ruel
 Problem : Baekjoon 13075번 Fibonacci Sequence
 Problem address : https://www.acmicpc.net/problem/13075
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13075_FibonacciSequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static final int LIMIT = 1_000_000_000;
    static HashMap<Long, long[][]> hashMap;
    static long[][] factor = {{1, 1}, {1, 0}};
    static long[][] f1f0 = {{1}, {0}};

    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스에 대해
        // x번째 피보나치수를 10^9으로 나눈 나머지값을 출력하라
        //
        // 분할 정복 문제
        // x가 최대 2^48으로 매우 크게 주어지므로
        // 행렬의 곱셈으로 표현한다.
        // (1 1)^(n-1)     (f1)  =   (fn)
        // (1 0)       *   (f0)      (fn-1)
        // (1 1)
        // (1 0)의 n-1 제곱을 구해, 마지막으로 f1f0랑 곱해주면 fn을 구할 수 있다.
        // 이 때 제곱을 구할 때
        // n-1이 짝수라면 (n-1) / 2제곱과 (n-1)/2 제곱을 곱해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // factor의 제곱을 해쉬맵에 젖아
        hashMap = new HashMap<>();
        hashMap.put(1L, factor);
        for (int testCase = 0; testCase < t; testCase++) {
            long x = Long.parseLong(br.readLine());
            // x가 1이라면 그냥 1 기록
            if (x == 1)
                sb.append(1).append("\n");
            // 그 외의 경우 factor의 x-1 제곱을 구해 f1f0을 곱한 뒤, fn을 계산해 기록
            else
                sb.append(matrixMultiple(f1f0, pow(x - 1))[0][0]).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // factor의 n제곱을 구한다.
    static long[][] pow(long n) {
        if (!hashMap.containsKey(n)) {
            // 짝수일 경우
            // n/2 제곱 * n/2 제곱
            if (n % 2 == 0)
                hashMap.put(n, matrixMultiple(pow(n / 2), pow(n / 2)));
            // 홀수일 경우, 추가로 factor를 한 번 더 곱해줌
            else
                hashMap.put(n, matrixMultiple(factor, matrixMultiple(pow(n / 2), pow(n / 2))));
        }
        // 결과값 반환
        return hashMap.get(n);
    }

    // 행렬의 곱
    static long[][] matrixMultiple(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[i].length; k++)
                    c[i][j] += a[i][k] * b[k][j];
                c[i][j] %= LIMIT;
            }
        }
        return c;
    }
}