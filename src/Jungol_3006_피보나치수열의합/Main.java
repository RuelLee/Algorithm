/*
 Author : Ruel
 Problem : Jungol 3006번 피보나치수열의 합
 Problem address : https://jungol.co.kr/problem/3006
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3006_피보나치수열의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 피보나치 수열의 합
        // 1 ~ n번째 피보나치 수의 합을 m으로 나눈 값을 출력하라
        //
        // dp 문제
        // n이 최대 100만으로 그리 크지 않으므로, 피보나치수를 구하고
        // 합쳐 m으로 나눈 나머지를 구한다.
        // 문제에서 원하는 값은 m으로 나눈 나머지 값이므로
        // 구하는 피보나치 수도 m으로 나눈 나머지로 값의 크기를 관리하고
        // 피보나치 수의 합도 m으로 나눈 나머지로 값을 관리한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n번째 피보나치 수까지의 합을 m으로 나눈 나머지를 구하라
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 피보나치 수
        int[] fibo = new int[n + 1];
        fibo[1] = fibo[2] = 1;
        // n번째 피보나치 수까지 직접 구하되, m으로 나눈 나머지로 계산하여 값의 범위를 관리한다.
        for (int i = 3; i < fibo.length; i++)
            fibo[i] = (fibo[i - 1] + fibo[i - 2]) % m;

        // 마찬가지로 n번째 피보나치 수까지 더하며
        // 값을 m으로 나눈 나머지로 관리한다.
        int sum = 0;
        for (int i = 1; i <= n; i++)
            sum = (sum + fibo[i]) % m;
        // 답 출력
        System.out.println(sum);
    }
}