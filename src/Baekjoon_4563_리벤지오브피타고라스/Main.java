/*
 Author : Ruel
 Problem : Baekjoon 4563번 리벤지 오브 피타고라스
 Problem address : https://www.acmicpc.net/problem/4563
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4563_리벤지오브피타고라스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 피타고라스의 정리는 다음과 같은 식으로 쓸 수 있다.
        // A^2 + B^2 = C^2
        // A가 주어졌을 때, 빗변의 길이 C가 자연수인 직각삼각형을 만드는 자연수 B(B>A)는 몇개가 있을까?
        //
        // 수학 문제
        // A^2 = C^2 - B^2 = (C + B) * (C -B)로 쓸 수 있다.
        // A^2의 약수들을 C, B의 합과 차로 나타낼 수 있어야하며
        // B는 C보다 커야하고, 합과 차가 둘 다 자연수로 나타낼 수 있어야하므로,
        // 두 수의 합 혹은 차가 짝수가 되어야한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        StringBuilder sb = new StringBuilder();
        while (true) {
            // 주어지는 A
            int a = Integer.parseInt(input);
            // A가 0일 경우 반복문 종료
            if (a == 0)
                break;
            
            // 제곱
            long powA = (long) a * a;
            int count = 0;
            for (int i = 1; i <= a; i++) {
                // powA의 약수 중 하나를 B, C의 차로 본다.
                if (powA % i == 0) {
                    // 합
                    long sum = powA / i;
                    // b는 두 수의 차 / 2
                    long b = (sum - i) / 2;
                    // b가 a보다 커야하며,
                    // 약수의 합 혹은 차가 짝수여야 한다.
                    // 자연수로 직각삼각형을 만들 수 있다.
                    // count 증가
                    if (b > a && (sum + i) % 2 == 0)
                        count++;
                }
            }
            // 답 기록
            sb.append(count).append("\n");
            // 다음 입력
            input = br.readLine();
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}