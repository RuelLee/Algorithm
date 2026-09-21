/*
 Author : Ruel
 Problem : Jungol 3291번 최소개수 제곱수의 합 2
 Problem address : https://jungol.co.kr/problem/3291
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3291_최소개수제곱수의합2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final long LIMIT = 10_000_000_000L;
    static List<Long> pows;
    static HashSet<Long> hashSet;

    public static void main(String[] args) throws IOException {
        // t개의 수에 대해 각 수가 몇 개의 제곱수의 합으로 표현되는지 출력하라
        //
        // 라그랑주의 네 제곱수 정리
        // 모든 자연수는 4개 이하의 제곱수의 합으로 표현된다고 한다
        // 범위 내의 제곱 수들을 구한 뒤, 해당 수가 먼저 1개의 제곱수로 표현되는지 확인한다.
        // 그렇지 않다면, 제곱수를 따져가며, 두 제곱수의 합으로 표현되는지 확인한다.
        // 이번에도 그렇지 않다면
        // 4로 나누어떨어지는 한 계속 나눠가며, 더이상 나누어 떨어지지 않는 시점에서
        // 8로 나눈 나머지가 7이라면 4개의 제곱수의 합, 그렇지 않다면 3개의 제곱수의 합으로 표현된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 제곱수를 리스트와 셋으로 정리
        pows = new ArrayList<>();
        hashSet = new HashSet<>();
        for (long i = 1; i * i <= LIMIT; i++) {
            pows.add(i * i);
            hashSet.add(i * i);
        }

        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            long n = Long.parseLong(br.readLine());

            // n이 표현되는 최소 제곱수의 개수 기록
            sb.append(findAnswer(n)).append('\n');
        }
        // 답 출력
        System.out.print(sb);
    }

    static int findAnswer(long num) {
        // 1개의 제곱수로 표현되는 경우
        if (hashSet.contains(num))
            return 1;

        // num보다 작은 제곱수들을 살펴보며, 두 제곱수의 합으로 표현되는지 확인
        for (int i = 0; i < pows.size() && pows.get(i) < num; i++) {
            if (hashSet.contains(num - pows.get(i)))
                return 2;
        }

        // num이 4로 나누어떨어지는 한 나누며
        while (num % 4 == 0)
            num /= 4;
        // 마지막 수가 8로 나눈 나머지가 7인지 확인.
        // 7이라면 4개, 아니라면 3개의 제곱수로 표현 가능.
        return num % 8 == 7 ? 4 : 3;
    }
}