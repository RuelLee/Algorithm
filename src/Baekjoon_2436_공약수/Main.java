/*
 Author : Ruel
 Problem : Baekjoon 2436번 공약수
 Problem address : https://www.acmicpc.net/problem/2436
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2436_공약수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어떤 두 수의 최대공약수와 최소공배수가 주어진다.
        // 이 때 가능한 두 수를 구하라
        // 해당하는 답이 여러개일 경우, 합이 최소인 두 수를 구하라
        //
        // 유클리드 호제법 문제
        // 유클리드 호제법을 통해 가능한 쌍을 찾고
        // 최대공약수가 일치하는지 살펴본다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 주어지는 최대공약수와 최소공배수
        int gcd = Integer.parseInt(st.nextToken());
        int lcm = Integer.parseInt(st.nextToken());
        
        // 가능한 숫자 쌍
        int a = gcd;
        int b = lcm;
        for (int i = 2; i * i <= lcm / gcd; i++) {
            // i가 lcm의 약수일 때
            if (lcm % i == 0) {
                // 가능한 A는 gcd / i
                // 가능한 B는 lcm / i
                int tempA = gcd * i;
                int tempB = lcm / i;

                // 두 수의 최대공약수가 gcd인지 살펴보고
                // 그러하다면 합이 최소인지 살펴본다.
                if (getGCD(tempA, tempB) == gcd && a + b > tempA + tempB) {
                    a = tempA;
                    b = tempB;
                }
            }
        }
        // 답 출력
        System.out.println(a + " " + b);
    }

    static int getGCD(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}