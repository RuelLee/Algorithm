/*
 Author : Ruel
 Problem : Baekjoon 30413번 양 한 마리... 양 A마리... 양 A제곱마리...
 Problem address : https://www.acmicpc.net/problem/30413
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30413_양한마리양A마리양A제곱마리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;
    static long[][] factor;
    static long[][] a1;
    static HashMap<Long, long[][]> memo;

    public static void main(String[] args) throws IOException {
        // 춘배는 누워서 양을 센다.
        // 처음에는 한마리, 두번째는 a마리, 세번째는 a^2 마리...
        // 이렇게 제곱으로 늘어나는 양을 센다.
        // b번째까지 센 양의 마릿수의 합을 구하면?
        //
        // 등비 수열의 합, 분할 정복
        // 등비 수열의 합 문제로, 분할 정복으로 풀 수 있다.
        // An을 n번째 양까지의 합이라고 한다면
        // (An)    (a 1)   (An-1)   (a 1) ^ (n-1)   (A1)
        // (1 )  = (0 1) * ( 1  ) = (0 1)         * (1 )
        // 로 나타낼 수 있으며
        // (a 1)
        // (0 1)을 분할 정복을 통해 제곱들을 구하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // a, b
        int a = Integer.parseInt(st.nextToken());
        long b = Long.parseLong(st.nextToken());
        // 제곱해야하는 행렬
        factor = new long[][]{{a, 1}, {0, 1}};
        // a1
        a1 = new long[][]{{1}, {1}};
        memo = new HashMap<>();
        memo.put(1L, factor);

        // 만약 b가 1이라면 답은 그냥 1
        if (b == 1)
            System.out.println(1);
        else        // 그 외의 경우 분할정복을 통해 An을 구한다.
            System.out.println(matrixMultiple(pow(b - 1), a1)[0][0]);
    }

    // factor의 n제곱을 구한다.
    static long[][] pow(long n) {
        // 기록된 결과가 없는 경우
        if (!memo.containsKey(n)) {
            // 짝수인 경우
            if (n % 2 == 0)
                memo.put(n, matrixMultiple(pow(n / 2), pow(n / 2)));
            else        // 홀수인 경우
                memo.put(n, matrixMultiple(matrixMultiple(pow(n / 2), pow(n / 2)), factor));
        }
        // 계산된 결과 반환.
        return memo.get(n);
    }
    
    // 행렬의 곱
    static long[][] matrixMultiple(long[][] a, long[][] b) {
        long[][] answer = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                for (int k = 0; k < a[i].length; k++)
                    answer[i][j] += a[i][k] * b[k][j];
                answer[i][j] %= LIMIT;
            }
        }
        return answer;
    }
}