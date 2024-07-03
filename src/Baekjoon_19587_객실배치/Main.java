/*
 Author : Ruel
 Problem : Baekjoon 19587번 객실 배치
 Problem address : https://www.acmicpc.net/problem/19587
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19587_객실배치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static final int LIMIT = 1_000_000_000 + 7;
    static long[][] factor = {{1, 1, 1,}, {1, 0, 1}, {1, 1, 0}};
    static long[][] zero = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
    static HashMap<Long, long[][]> memo;

    public static void main(String[] args) throws IOException {
        // 한 층에 2개의 객실, n개의 층을 가진 호텔이 주어진다.
        // 사회적 거리두기를 위해
        // 한 층에는 하나의 객실만 배정하고 또한
        // 위 아래로 인접한 객실을 배정해서는 안된다.
        // 객실에 투숙객들을 배정하는 경우의 수는?
        //
        // 분할 정복 거듭제곱
        // 먼저, 1층에 배정하는 경우의 수는 1호에만, 2호에만, 아무도 배정하지 않는 경우 각각 1가지씩 총 3가지다
        // 다음 2층에 배정하는 경우에는, 
        // 1호에 배정하는 경우는 1층에 2호 혹은 아무도 배정하지 않은 경우
        // 2호에 배정하는 경우는 2층에 1호 혹은 아무도 배정하지 않은 경우
        // 아무도 배정하지 않는 경우는 1층의 모든 경우이다
        // 따라서 아무도 배정하지 않는 경우를 A, 1호에 배정하는 경우를 B, 2호에 배정하는 경우를 C라 할 때
        // An = An-1 + Bn-1 + Cn-1
        // Bn = An-1 + Cn-1
        // Cn = An-1 + Bn-1
        // 로 정의하고 점화식을 세울 수 있다.
        // 따라서
        // (An)   (1, 1, 1)^(n -1)   (A1)
        // (Bn) = (1, 0, 1)        * (B1)
        // (Cn)   (1, 1, 0)          (C1)
        // 로 정의하고 행렬의 거듭제곱을 통해 해결할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 층
        long n = Long.parseLong(br.readLine());

        memo = new HashMap<>();
        // 0일 때는 (A1, B1, C1)의 상태를 그대로 반환하기 위해
        // zero를 넣어두고
        // 1에는 거듭제곱이 되는 행렬을 넣는다.
        memo.put(0L, zero);
        memo.put(1L, factor);
        
        // (An, Bn, Cn)을 구하기 위해선 factor의 n-1제곱과 (A1, B1, C1)의 곱이 필요하다.
        long[][] answer = matrixMultiple(getPow(n - 1), new long[][]{{1}, {1}, {1}});
        // 세 수를 모두 더한 값이 구하고자 하는
        // 모든 경우의 수
        long sum = 0;
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length; j++)
                sum += answer[i][j];
            sum %= LIMIT;
        }
        // 답 출력
        System.out.println(sum);
    }

    // factor의 n제곱을 구한다.
    static long[][] getPow(long n) {
        // 이미 결과가 있다면 참고
        if (memo.containsKey(n))
            return memo.get(n);
        
        // 그렇지 않을 경우 n / 2 제곱을 구하여
        long[][] half = getPow(n / 2);
        // 짝수라면 half를 서로 곱해 n제곱을 구하고
        if (n % 2 == 0)
            memo.put(n, matrixMultiple(half, half));
        else        // 홀수라면 추가적으로 한번 더 곱해준다.
            memo.put(n, matrixMultiple(matrixMultiple(half, half), getPow(1)));
        return memo.get(n);
    }

    // 행렬의 곱셈
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