/*
 Author : Ruel
 Problem : Baekjoon 1407번 2로 몇 번 나누어질까
 Problem address : https://www.acmicpc.net/problem/1407
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1407_2로몇번나누어질까;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n이 최대 2의 몇 제곱으로 나누어떨어지는지 계산하고 이를 f(x)로 정의하자
        // f(40) = 8 * 5이므로 8이고, f(15) = 1 * 15이므로 1이다.
        // a, b가 주어질 때
        // f(a) + ... + f(b)의 값을 구하라
        // a, b는 최대 10^15까지 주어진다.
        //
        // 수학 문제
        // 먼저 구간에 대해서 구하는 것이므로
        // f(0) + ... + f(b) - (f(0) + ... + f(a-1))로 구한다.
        // f(0) + ... + f(n)을 구하는 방법은
        // 먼저 2^0인 1의 배수는 총 n개 포함되어있다.
        // 다음 2^1의 배수는 n / 2개 포함되어있으며, 2^0보다 2^0 크다.
        // ...
        // 2^x 배수는 n / x개 포함되어있으며, 2^(x-1)보다 2^(x-1) 크다.
        // 위 과정을 2^x가 n보다 같거나 작은 동안 반복한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 값의 범위인 a와 b
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        // f(0) + ... + f(b) - (f(0) + ... + f(a-1))
        System.out.println(findAnswerFromZeroToN(b) - findAnswerFromZeroToN(a - 1));
    }
    
    // f(0) + ... + f(n)을 구하는 메소드
    static long findAnswerFromZeroToN(long n) {
        // n까지 1의 배수는 n개
        long sum = n;
        // 2^0은 초기값 n으로 계산되었으므로 2의 1제곱부터 시작.
        long pow = 2;
        // pow가 n보다 같거나 작은 동안
        while (pow <= n) {
            // pow의 배수는 (n / pow)개 있고
            // 이미 (pow / 2)로 계산되었으니 그 차인 (pow / 2)만큼을 추가로 더해준다.
            sum += (n / pow) * (pow / 2);
            // pow의 2를 곱해 다음 2의 제곱으로 넘어간다.
            pow *= 2;
        }
        // 계산된 결과 반환.
        return sum;
    }
}