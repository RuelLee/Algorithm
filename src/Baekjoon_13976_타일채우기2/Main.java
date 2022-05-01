/*
 Author : Ruel
 Problem : Baekjoon 13976번 타일 채우기 2
 Problem address : https://www.acmicpc.net/problem/13976
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13976_타일채우기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static final int LIMIT = 1_000_000_007;
    static HashMap<Long, long[][]> memo;


    public static void main(String[] args) throws IOException {
        // 3 * n (최대 1,000,000,000,000,000,000) 길이의 벽을 1 * 2 or 2 * 1 벽돌로 채우려고 한다
        // 채울 수 있는 가지수를 구하여라
        //
        // N이 충분히 작을 때는 DP를 활용하여 구할 수 있지만
        // 위 경우에는 1천조로 n의 크기가 매우 크기 때문에 DP를 사용할 수는 없다
        // 경우의 수의 규칙성을 찾아 점화식을 세우고 이를 통해 행렬의 제곱을 분할 정복을 하여 풀어야한다
        // 첨부된 note.png 파일과 같이 규칙을 찾을 수 있고
        // 이를 행렬의 제곱을 통해 풀자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());

        memo = new HashMap<>();
        memo.put(0L, new long[][]{{1, 0}, {1, 0}});
        memo.put(1L, new long[][]{{4, -1}, {1, 0}});

        // 홀수일 경우에는 채우는 것이 불가능하다. 0출력
        if (n % 2 == 1)
            System.out.println(0);
        else {      // 짝수일 경우에는 분할 정복을 통해 풀어주자.
            long[][] matrix = getMatrixPow(n / 2 - 1);
            System.out.println((matrix[0][0] * 3 + matrix[0][1]) % LIMIT);
        }
    }

    static long[][] getMatrixPow(long n) {
        // 메모이제이션 활용
        // n의 값이 계산된 적이 없다면
        if (!memo.containsKey(n)) {
            if (n % 2 == 0)     // 짝수라면 n/2, n/2로 나누고
                memo.put(n, matrixMultiple(getMatrixPow(n / 2), getMatrixPow(n / 2)));
            else        // 홀수라면 (n-1)/2, (n-1)/2, 1 세 개로 나누어 곱해주자.
                memo.put(n, matrixMultiple(matrixMultiple(getMatrixPow(n / 2), getMatrixPow(n / 2)), getMatrixPow(1)));
        }
        // 이미 계산한 적이 있거나, 방금 계산한 행렬의 n제곱을 리턴.
        return memo.get(n);
    }

    // 행렬의 곱
    static long[][] matrixMultiple(long[][] a, long[][] b) {
        long[][] result = new long[a.length][b[0].length];

        for (int row = 0; row < a.length; row++) {
            for (int col = 0; col < b[0].length; col++) {
                for (int c = 0; c < a[row].length; c++) {
                    result[row][col] += (a[row][c] * b[c][col]);
                    result[row][col] = (result[row][col] + LIMIT) % LIMIT;
                }
            }
        }
        return result;
    }
}