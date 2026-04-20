/*
 Author : Ruel
 Problem : Jungol 1053번 피보나치
 Problem address : https://jungol.co.kr/problem/1053
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1053_피보나치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static HashMap<Integer, int[][]> hashMap;

    public static void main(String[] args) throws IOException {
        // n번째 피보나치 수의 마지막 4자리 수를 구하라
        //
        // 행렬의 거듭 제곱, 분할 정복
        // 피보나치 수는 행렬의 거듭제곱으로 풀 수 있다.
        // 따라서 주어진 n을 따라
        // 짝수면 n/2과 n/2의 곱, 홀수일 경우 n/2, n/2, 1과의 곱으로 나눠 구할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        hashMap = new HashMap<>();
        // 1 1
        // 1 0 의 행렬을 기본 1의 행렬로 지정 후, 제곱해 나간다.
        hashMap.put(0, new int[][]{{1, 0}, {0, 0}});
        hashMap.put(1, new int[][]{{1, 1}, {1, 0}});
        StringBuilder sb = new StringBuilder();
        String input = br.readLine();
        // -1이 입력될 때까지 반복
        while (!input.equals("-1")) {
            // 입력된 수
            int n = Integer.parseInt(input);
            // 해당 피보나치 수의 마지막 4자리 수를 기록
            sb.append(findPow(n)[0][1]).append("\n");
            input = br.readLine();
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // 첫 행렬의 n제곱 결과
    static int[][] findPow(int n) {
        if (!hashMap.containsKey(n)) {
            // 짝수인 경우
            if (n % 2 == 0)
                hashMap.put(n, multiple(findPow(n / 2), findPow(n / 2)));
            else        // 홀수인 경우
                hashMap.put(n, multiple(multiple(findPow(n / 2), findPow(n / 2)), findPow(1)));
        }
        return hashMap.get(n);
    }

    // 행렬의 곱
    static int[][] multiple(int[][] a, int[][] b) {
        int[][] result = new int[a.length][b[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                    result[i][j] %= 10000;
                }
            }
        }
        return result;
    }
}