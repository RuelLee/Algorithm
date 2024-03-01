/*
 Author : Ruel
 Problem : Baekjoon 2247번 실질적 약수
 Problem address : https://www.acmicpc.net/problem/2247
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2247_실질적약수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000;

    public static void main(String[] args) throws IOException {
        // 실질적 약수란 자신과 1을 제외한 약수들을 의미한다
        // SOD(n)은 n의 실질적 약수의 합을 나타낸다.
        // CSOD(n) = SOD(1) + ... + SOD(n)이라 한다.
        // n이 주어질 때, CSOD(n)을 구하라
        //
        // 계산 문제
        // n이 2억, 시간이 2초까지 주어지므로 하나씩 모두 계산해서는 안된다.
        // n을 i로 나누면, n 범위에서 i를 약수로 가지는 수의 개수가 나온다.
        // 이 개수들 중 i 자신의 개수도 포함되어있으므로 1을 빼고, 그만큼의 i가 존재하므로
        // (n / i) - 1 = i 자신을 제외한 i가 약수로 포함된 개수를 i만큼 곱하면
        // SOD(n) 내에서 i에 대한 모든 처리를 해준 것이 된다.
        // 이를 i <= n /2 범위까지 해준다.
        // n/2보다 큰 수들을 약수로 등장하지 않기 때문.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 n
        int n = Integer.parseInt(br.readLine());

        // 합
        int sum = 0;
        // 2부터 n / 2까지 범위로 계산한다.
        for (int i = 2; i <= n / 2; i++) {
            // i가 실질적 약수로 등장하는 횟수 ((n / i) - 1)
            // 에 i만큼을 곱한 값을 더한다.
            sum += ((n / i) - 1) * i;
            // 모듈러 처리
            sum %= LIMIT;
        }
        // 답안 출력
        System.out.println(sum);
    }
}