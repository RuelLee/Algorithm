/*
 Author : Ruel
 Problem : Baekjoon 1630번 오민식
 Problem address : https://www.acmicpc.net/problem/1630
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1630_오민식;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 987654321;

    public static void main(String[] args) throws IOException {
        // 오민식을 만족하는 수: 1보다 크거나 같고, N보다 작거나 같은 모든 자연수로 나누어 떨어지는 수.
        // n이 주어질 때 오민식을 만족하는 가장 작은 수를 구하라
        //
        // 에라토스테네스의 체 문제
        // 오민식을 만족하는 수 -> n이하의 소수들에 대해, 제곱이 n 이하를 만족하는 최대 제곱을 찾아 모두 곱해준 수
        // 따라서 에라토스테네스의 체로 n이하의 모든 소수를 구하고
        // 각 소수들의 n이하 최대 제곱을 찾아 곱해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 소수가 아닌 수
        boolean[] notPrimeNumber = new boolean[n + 1];
        for (int i = 2; i < notPrimeNumber.length; i++) {
            // 소수가 아니라면, 이미 이전 i에서 배수들로 걸러진 경우
            // 건너뛴다.
            if (notPrimeNumber[i])
                continue;
            
            // 소수인 경우
            // 소수의 배수들은 모두 소수가 아니다.
            for (int j = 2; i * j < notPrimeNumber.length; j++)
                notPrimeNumber[i * j] = true;
        }

        long answer = 1;
        // n이하의 소수들에 대해
        for (int i = 2; i < notPrimeNumber.length; i++) {
            if (!notPrimeNumber[i]) {
                // 제곱이 n보다 작은 최대 제곱을 찾아 곱해준다.
                answer *= Math.pow(i, (int) (Math.log(n) / Math.log(i)));
                // 값이 범위를 벗어남에 주의하며 문제에 주어진대로 모듈러 연산을 한다.
                answer %= LIMIT;
            }
        }

        // 최종 답안 출력
        System.out.println(answer);
    }
}