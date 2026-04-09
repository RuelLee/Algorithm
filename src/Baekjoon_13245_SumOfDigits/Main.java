/*
 Author : Ruel
 Problem : Baekjoon 13245번 Sum of digits
 Problem address : https://www.acmicpc.net/problem/13245
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13245_SumOfDigits;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n이 주어질 때
        // 1 ~ n까지의 모든 수의 각 자리 수 합을 구하라
        //
        // 조합 문제
        // 각 자리에서 각 수가 몇 번씩 등장하는지를 계산해 곱해주면 된다.
        // 각 경우의 수를 나눠 누적시켜주자.
        // XNY라는 수가 주어질 때
        // (0 ~ N-1)까지의 수가 X가 0 ~ X까지 (X + 1) * (N의 자릿수)만큼 등장한다.
        // N이라는 수가 (0 ~ X-1)까지 X * (N의 자릿수)만큼 등장하고
        // X일 때, Y + 1번 등장한다.
        // (N + 1 ~ 9)까지의 수가 X가 0 ~ (X-1)까지 X * (N의 자릿수) 만큼 등장한다.
        // 해당 경우의 수를 누적시켜주면 끝

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 수 N
        long n = Long.parseLong(br.readLine());
        // 누적 자릿수의 합
        long sum = 0;
        // 현재 살펴보는 자릿수
        long digits = 1;
        // n의 모든 자릿수를 살펴볼 때까지
        while (n >= digits) {
            // 현재 자릿수의 값
            long num = (n / digits) % 10;
            // num - 1까지의 수가 현재 자릿수에서 등장하는 횟수
            sum += (n / (digits * 10) + 1) * (num - 1) * (num) / 2 * digits;
            // num의 수가 현재 자릿수에서 등장하는 횟수
            sum += (n / (digits * 10)) * num * digits;
            sum += num * (n % digits + 1);
            // num + 1 ~ 9까지의 수가 현재 자릿수에서 등장하는 횟수
            sum += (n / (digits * 10)) * (9 - num) * (9 + num + 1) / 2 * digits;
            // 자릿수 이동
            digits *= 10;
        }
        // 답 출력
        System.out.println(sum);
    }
}