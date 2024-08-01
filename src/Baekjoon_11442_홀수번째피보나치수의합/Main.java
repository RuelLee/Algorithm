/*
 Author : Ruel
 Problem : Baekjoon 11442번 홀수번째 피보나치 수의 합
 Problem address : https://www.acmicpc.net/problem/11442
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11442_홀수번째피보나치수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static long[][] factor = {{1, 1,}, {1, 0}};
    static long[][] a1a0 = {{1}, {0}};
    static HashMap<Long, long[][]> memo;
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 1 ~ n번째 피보나치수들 중 홀수번째 수들의 합을 구하라
        //
        // 행렬의 거듭제곱 문제
        // 직접 피보나치 수들을 나열해보면
        // 1 1 2 3 5 8 13 21 34 55 ..
        // 여기서 홀수번째들의 합을 적어보면
        // 1 3 8 21 55 ..
        // 짝수번째 피보나치 수와 같다.
        // 따라서 n이 주어질 때, n이 홀수라면 n+1번째 피보나치수를 구하면 되고
        // n이 짝수라면 n번째 작수를 구하면 된다.
        // n이 1,000,000,000,000,000,000까지로 매우 크게 주어지므로
        // 행렬의 거듭제곱을 통해 구한다.
        // (An   )   (1 1)   (An-1)    (1 1)^(n-1)   (A1)
        // (An-1 ) = (1 0) * (An-2)  = (1 0)       * (A0)
        // 으로 나타낼수 있고, 0번째 피보나치 수는 0이라 할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());
        memo = new HashMap<>();
        // 행렬의 1제곱 값은 초기값으로 넣어준다.
        // (1 1)
        // (1 0)
        memo.put(1L, factor);
        
        // 짝수일 경우 n-1, 홀수일 경우 n제곱을 해, a1a0와 곱해준다.
        long[][] answer = multiple(getFactorPow(n % 2 == 0 ? n - 1 : n), a1a0);
        // 답에 해당하는 값 출력
        System.out.println(answer[0][0]);
    }
    
    // 행렬의 분할 제곱
    static long[][] getFactorPow(long n) {
        // 값이 없을 경우, 짝홀수를 나눠 계산하여 저장
        if (!memo.containsKey(n)) {
            if (n % 2 == 0)
                memo.put(n, multiple(getFactorPow(n / 2), getFactorPow(n / 2)));
            else
                memo.put(n, multiple(multiple(getFactorPow(n / 2), getFactorPow(n / 2)), factor));
        }
        // 저장된 값 출력
        return memo.get(n);
    }
    
    // 행렬의 곱
    static long[][] multiple(long[][] a, long[][] b) {
        long[][] answer = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < answer[i].length; j++) {
                for (int k = 0; k < a[i].length; k++)
                    answer[i][j] += a[i][k] * b[k][j];
                answer[i][j] %= LIMIT;
            }
        }
        return answer;
    }
}