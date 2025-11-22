/*
 Author : Ruel
 Problem : Baekjoon 7677번 Fibonacci
 Problem address : https://www.acmicpc.net/problem/7677
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7677_Fibonacci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static final int[][] element = {{1, 1}, {1, 0}};
    static HashMap<Integer, int[][]> hashMap;

    public static void main(String[] args) throws IOException {
        // 피보나치 수는
        // 0번부터,
        // 0 1 1 2 3 5 8 13 21 34 ... 으로 정의된다.
        // 이는
        // (Fn+1 Fn  )     (1  1) ^ n
        // (Fn   Fn-1)  =  (1  0)
        // 의 행렬의 제곱으로 구할 수 있다.
        // n번째 피보나치수를 10000으로 나눈 나머지를 출력하라
        //
        // 분할 정복, 행렬의 곱셈
        // 행렬의 제곱을 구하면 풀 수 있는 문제
        // 단위 행렬의 n제곱은
        // n이 짝수일 때는 n/2의 제곱 * n/2의 제곱으로 구할 수 있고
        // 홀수일 때는 n/2의 제곱 * n/2의 제곱 * 단위 행렬로 구할 수 있다.
        // 이를 해쉬맵에 기록하여 메모이제이션도 같이 사용해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 해쉬맵에 단위 행렬의 제곱의 값을 기록
        hashMap = new HashMap<>();
        // 1일 때, 단위 행렬
        hashMap.put(1, element);

        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());
        while (n != -1) {
            // 0 제곱일 때는 계산 필요없이 0 기록
            if (n == 0)
                sb.append(0);
            else    // 그 외의 경우는 단위 행렬의 n제곱을 구해, 답을 기록
                sb.append(findAnswer(n)[0][1]);
            sb.append("\n");

            n = Integer.parseInt(br.readLine());
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // 두 행렬, a와 b를 곱한다.
    static int[][] matrixMultiple(int[][] a, int[][] b) {
        int[][] answer = new int[a.length][b[0].length];
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length; j++) {
                for (int k = 0; k < a[i].length; k++)
                    answer[i][j] += a[i][k] * b[k][j];
                answer[i][j] %= 10000;
            }
        }
        return answer;
    }
    
    // n제곱을 구한다.
    static int[][] findAnswer(int pow) {
        // 처음 구하는 제곱인 경우
        if (!hashMap.containsKey(pow)) {
            // 제곱이 짝수인 경우, 반을 나눠 곱함
            if (pow % 2 == 0)
                hashMap.put(pow, matrixMultiple(findAnswer(pow / 2), findAnswer(pow / 2)));
            else        // 홀수 인 경우, 반을 나눠 곱하고, 한번 더 단위 행렬을 곱한다.
                hashMap.put(pow, matrixMultiple(matrixMultiple(findAnswer(pow / 2), findAnswer(pow / 2)), element));
        }
        // pow 제곱을 반환.
        return hashMap.get(pow);
    }
}