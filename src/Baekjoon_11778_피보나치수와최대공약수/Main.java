/*
 Author : Ruel
 Problem : Baekjoon 11778번 피보나치 수와 최대공약수
 Problem address : https://www.acmicpc.net/problem/11778
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11778_피보나치수와최대공약수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;
    static HashMap<Long, long[][]> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // 0번째 피보나치 수는 0, 1번째 피보나치 수는 1이다.
        // n번째 피보나치 수와 m번째 피보나치 수의 최대 공약수는?
        //
        // 분할 정복, 유클리드 호제법 문제
        // n번째 피보나치 수를 f(n)이라고 할 때
        // f(GCD(n, m)) = GCD(f(n), f(m)) 이 성립한다고 한다.
        // 따라서 n과 m의 최대공약수를 구한 뒤, 해당 번째의 피보나치 수를 구하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 입력 n, m
        long n = Long.parseLong(st.nextToken());
        long m = Long.parseLong(st.nextToken());

        map.put(0L, new long[][]{{1, 0}, {0, 0}});
        map.put(1L, new long[][]{{1, 1}, {1, 0}});
        long[][] fibo = new long[][]{{1}, {0}};

        // n과 m의 최대공약수를 구해, 해당 번째의 피보나치 수를 출력
        System.out.println(getPOW(getGCD(n, m) - 1)[0][0]);
    }

    // 분할 정복
    // {{1, 1}, {1, 0}}의 n제곱을 구한다.
    static long[][] getPOW(long n) {
        if (!map.containsKey(n)) {
            // 짝수라면 기본 행렬의 n / 2제곱을 서로 곱함.
            if (n % 2 == 0)
                map.put(n, matrixMultiple(getPOW(n / 2), getPOW(n / 2)));
            else    // 홀수일 경우, 추가로 한번 더 곱해줌
                map.put(n, matrixMultiple(getPOW(1), matrixMultiple(getPOW(n / 2), getPOW(n / 2))));
        }
        // 값 반환
        return map.get(n);
    }

    // 행렬의 곱
    static long[][] matrixMultiple(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[i].length; k++)
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % LIMIT;
            }
        }
        return c;
    }

    // 두 수의 최대공약수
    // 유클리드 호제법
    static long getGCD(long a, long b) {
        long max = Math.max(a, b);
        long min = Math.min(a, b);

        while (min > 0) {
            long temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}