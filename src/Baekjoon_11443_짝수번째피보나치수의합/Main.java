/*
 Author : Ruel
 Problem : Baekjoon 11443번
 Problem address : https://www.acmicpc.net/problem/11443
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11443_짝수번째피보나치수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static long[][] factor = {{1, 1}, {1, 0}};
    static final int LIMIT = 1_000_000_007;
    static HashMap<Long, long[][]> memo;

    public static void main(String[] args) throws IOException {
        // n까지의 피보나치 수 중 짝수번째 수의 합을 구하라
        //
        // 분할정복, 메모이제이션
        // 일단 피보나치수를 나열해보면
        // 0 1 1 2 3 5 8 13 21 34 55 89 ...
        // 이 되고 짝수번째 수들의 합을 순서대로 나열하면
        // 1 4 12 33 88 ...
        // 마지막 짝수번째 수의 다음 피보나치수에서 1 뺀 값과 같다는 걸 확인할 수 있다.
        // 따라서 n이 주어진다면, n에 속하는 짝수 중 가장 큰 수를 찾고,
        // 그 수의 다음 피보나치 수에서 1을 뺀 값을 출력하면 된다.
        // n이 매우 큰 범위까지 주어지므로 long타입과 분할 정복을 통한 거듭제곱으로 해결한다.
        // (An  )   (1 1)   (An-1)   (1 1) ^ (n-1)   (A1)
        // (An-1) = (1 0) * (An-2) = (1 0)         * (A0)
        // 행렬의 거듭제곱으로 An을 빠르게 구할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());
        memo = new HashMap<>();
        // 0과 1에 factor를 넣어둔다.
        // 1제곱은 factor가 맞고,
        // n이 1이 주어질 경우, 0값을 찾게되는데
        // 이 때의 답 또한 0이므로 factor 행렬이 들어있더라도 올바른 값이 출력된다.
        memo.put(0L, factor);
        memo.put(1L, factor);

        // n보다 작거나 같은 가장 큰 짝수를 찾고, 해당 수보다 1 큰 피보나치 수의
        // 필요한 제곱수는 (n / 2) * 2 제곱이다.
        // 따라서 해당 제곱값을 찾고
        // [0][0] 위치의 값에서 -1한 값을 출력한다.
        long[][] answer = getPow((n / 2) * 2 + 1 - 1);
        System.out.println(answer[0][0] - 1);
    }

    // 메모이제이션을 통해 n값이 있는지 확인하고
    // 있다면 반환, 없다면 계산하여 반환한다.
    static long[][] getPow(long n) {
        // n값이 없다면
        if (!memo.containsKey(n)) {
            // 짝수 일 경우 (n/2) * (n/2)를 통해 n제곱을 구한다.
            if (n % 2 == 0)
                memo.put(n, matrixMultiple(getPow(n / 2), getPow(n / 2)));
            else        // 홀수일 경우, 1 * (n/2) * (n/2)를 통해 구한다.
                memo.put(n, matrixMultiple(factor, matrixMultiple(getPow(n / 2), getPow(n / 2))));
        }
        // 결과값 반환
        return memo.get(n);
    }

    // 두 행렬이 주어졌을 때 곱셈을 계산한다.
    static long[][] matrixMultiple(long[][] a, long[][] b) {
        long[][] answer = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[i].length; k++)
                    answer[i][j] += a[i][k] * b[k][j];
                answer[i][j] %= LIMIT;
            }
        }
        return answer;
    }
}