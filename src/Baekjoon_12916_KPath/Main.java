/*
 Author : Ruel
 Problem : Baekjoon 12916번 K-Path
 Problem address : https://www.acmicpc.net/problem/12916
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12916_KPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static final int LIMIT = 1_000_000_007;
    static HashMap<Integer, long[][]> memo;

    public static void main(String[] args) throws IOException {
        // n개의 마을이 있으며, 각 마을에서 다른 마을로 갈 수 있는 도로가
        // n * n크기의 행렬로 주어진다.
        // 각 도로의 길이는 1이라고 할 때
        // 길이 k의 경로의 개수는 몇 개인가?
        // 값이 크므로 10^9 + 7로 나눈 나머지를 출력한다
        //
        // 행렬의 곱, 분할 정복 문제
        // 주어지는 행렬을 k제곱 하면되는 문제
        // k가 10^9까지 주어지므로 일일이 계산하여서는 안되고,
        // 분할 정복을 통해 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 마을
        n = Integer.parseInt(st.nextToken());
        // 길이 k인 경로의 수를 구한다.
        int k = Integer.parseInt(st.nextToken());
        
        long[][] factor = new long[n][n];
        for (int i = 0; i < factor.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < factor[i].length; j++)
                factor[i][j] = Long.parseLong(st.nextToken());
        }
        // memo에 1짜리 경로 추가
        memo = new HashMap<>();
        memo.put(1, factor);
        
        // k짜리 경로들
        // routes[i][j] = i에서 출발하여 최종적으로 j에 도달하는 길이 k 경로의 수
        long[][] routes = findAnswer(k);

        // 모두 더해준다.
        long sum = 0;
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++) {
                sum += routes[i][j];
                sum %= LIMIT;
            }
        }
        // 답 출력
        System.out.println(sum);
    }
    
    // 행렬의 곱
    static long[][] matrixMultiple(long[][] a, long[][] b) {
        long[][] c = new long[n][n];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                    c[i][j] %= LIMIT;
                }
            }
        }
        return c;
    }
    
    // 분할 정복
    static long[][] findAnswer(int pow) {
        if (!memo.containsKey(pow)) {
            if (pow % 2 == 0)
                memo.put(pow, matrixMultiple(findAnswer(pow / 2), findAnswer(pow / 2)));
            else
                memo.put(pow, matrixMultiple(matrixMultiple(findAnswer(pow / 2), findAnswer(pow / 2)), findAnswer(1)));
        }
        return memo.get(pow);
    }
}