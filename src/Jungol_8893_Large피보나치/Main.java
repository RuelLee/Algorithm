/*
 Author : Ruel
 Problem : Jungol 8893번 Large 피보나치
 Problem address : https://jungol.co.kr/problem/8893
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8893_Large피보나치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static long[][] factor = {{1, 1}, {1, 0}};
    static final int LIMIT = 1_000_000_000 + 7;
    static HashMap<Long, long[][]> hashMap;

    public static void main(String[] args) throws IOException {
        // n이 주어질 때, n번째 피보나치 수를 10^9 + 7로 나눈 나머지를 출력하라
        //
        // 분할 정복 문제
        // 피보나치는 행렬의 제곱으로 구할 수 있다.
        // (fn+1, fn)     (1 1)^n
        // (fn, fn-1)  =  (1 0)

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        hashMap = new HashMap<>();
        // 기본 행렬
        hashMap.put(1L, factor);
        long input = Long.parseLong(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (input != -1) {
            // 0인 경우는 0, 그 외의 경우 해당하는 피보나치수의 나머지를 기록
            sb.append(input == 0 ? 0 : getFibo(input)[0][1]).append("\n");
            // 다음 입력
            input = Long.parseLong(br.readLine());
        }
        // 답 출력
        System.out.print(sb);
    }

    // n번째 피보나치수의 나머지를 구한다.
    static long[][] getFibo(long n) {
        // 계산된 결과가 없는 경우
        if (!hashMap.containsKey(n)) {
            // 짝수인 경우
            // 단위 행렬의 (n / 2) 제곱끼리 곱해 구한다
            if (n % 2 == 0)
                hashMap.put(n, matrixMultiple(getFibo(n / 2), getFibo(n / 2)));
            else        // 홀수 인 경우, 단위 행렬의 곱에 다시 단위 행렬을 곱해 구한다.
                hashMap.put(n, matrixMultiple(matrixMultiple(getFibo(n / 2), getFibo(n / 2)), getFibo(1)));
        }
        // 답 반환
        return hashMap.get(n);
    }

    // 행렬의 곱
    static long[][] matrixMultiple(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];

        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                for (int k = 0; k < a[i].length; k++)
                    c[i][j] += a[i][k] * b[k][j];
                // 값을 구한 뒤 나머지 처리
                c[i][j] %= LIMIT;
            }
        }
        return c;
    }
}