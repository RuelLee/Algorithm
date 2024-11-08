/*
 Author : Ruel
 Problem : Baekjoon 16723번 원영이는 ZOAC과 영원하고 싶다
 Problem address : https://www.acmicpc.net/problem/16723
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16723_원영이는ZOAC과영원하고싶다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. t회 zoac의 기념품은 2 * t개이다.
        // 2. 참가자의 수는 기념품을 남김없이 동일 개수로 나눌 수 있는 2의 거듭제곱이다.
        // 3. 2번 조건을 만족하는 참가자의 수들 중 가장 큰 수가 t회 zoac의 참가자 수이다.
        // n이 주어질 때, 1 ~ n회까지 참가자 수의 합을 구하라
        //
        // 수학 문제
        // 1 ~ n까지의 수들 중 홀수의 개수를 센다. 해당 회차의 참가자 수는 2명이다.
        // 1 ~ n까지의 수들 중 짝수의 수는 n/2개이다.
        // 해당 짝수들을 생각하며, 1 ~ n/2까지의 수들 중 홀수의 개수를 센다, 해당 회차의 참가자 수는 2 * 2명이다.
        // ...
        // 위 과정을 반복하여 전체 참가자의 수를 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 1 ~ n까지의 범위
        long n = Long.parseLong(br.readLine());
        
        // 전체 참가자의 수
        long sum = 0;
        // 곱하는 수
        long multi = 2;
        // n이 1보다 큰 동안
        while (n > 0) {
            // 1 ~ n 까지의 범위의 홀수의 개수를 구한다.
            // 해당 회차들의 참가자 수는 multi명.
            sum += (n % 2 == 0 ? n / 2 : n / 2 + 1) * multi;
            
            // n에 2를 나눈 값이 남은 짝수들의 수
            n /= 2;
            // 다음 짝수들의 참가자 수는 multi의 2배가 된다.
            multi *= 2;
        }
        // 구한 전체 참가자의 수 출력
        System.out.println(sum);
    }
}