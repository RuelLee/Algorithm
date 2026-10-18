/*
 Author : Ruel
 Problem : Jungol 1560번 피타고라스의 정리2
 Problem address : https://jungol.co.kr/problem/1560
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1560_피타고라스의정리2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 10,000이하의 자연수를 입력받아, 해당 값 이하의 정수로 구성된 직각삼각형의 개수를 출력하라
        //
        // 누적합 문제
        // z^2 = x^2 + y^2 이므로 가장 큰 값은 z이다.
        // z 또한 1만이하로 제한되어야한다.
        // 이중 for문으로 x와 y에 모든 경우의 수를 대입한다.
        // 대입하며, 제곱의 합이 다른 수의 제곱인지 판별하고 해당하는 z값을 찾아 개수를 추가시킨다.
        // 그 후, 누적합 처리하여 빠르게 쿼리를 처리한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] psums = new int[10_000 + 1];
        // z^2 = x^2 + y^2에서 모든 x와 y에 대해서 시행
        for (int i = 1; i <= 10_000; i++) {
            for (int j = 1; j <= i; j++) {
                // 제곱의 합
                int powSum = i * i + j * j;
                // powSum의 제곱근을 int로 짜른 값
                int sqrt = (int) Math.sqrt(powSum);
                // sqrt의 제곱이 powSum과 같다면, 조건을 만족하는 직각삼각형
                if (sqrt <= 10000 && sqrt * sqrt == powSum)
                    psums[sqrt]++;
            }
        }
        // 누적합 처리
        for (int i = 1; i < psums.length; i++)
            psums[i] += psums[i - 1];

        // 변의 최대 길이 입력 받음
        long value = Long.parseLong(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 0이 입력될 때까지
        while (value != 0) {
            // 값의 범위를 만족하는지 살펴보고, 해당하는 누적합을 참조
            sb.append(check(value) ? psums[(int) value] : "입력에러").append("\n");
            value = Long.parseLong(br.readLine());
        }
        // 답 출력
        System.out.print(sb);
    }

    static boolean check(long n) {
        return n > 0 && n <= 10_000;
    }
}