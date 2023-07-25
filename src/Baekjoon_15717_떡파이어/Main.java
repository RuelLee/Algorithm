/*
 Author : Ruel
 Problem : Baekjoon 15717번 떡파이어
 Problem address : https://www.acmicpc.net/problem/15717
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15717_떡파이어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static HashMap<Long, Long> memo;
    static final int LIMIT = 1000000007;

    public static void main(String[] args) throws IOException {
        // 떡국을 먹는 만큼 나이를 먹는다
        // n세로 생을 마감하기까지 떡국을 먹는 가짓 수는 총 몇 개인가?
        // 가짓 수를 10^9 + 7로 나눈 나머지를 출력하라
        //
        // 분할 정복 문제
        // n세로 나이를 마감하기 위해서는
        // n개의 1과 n - 1로 이루어진 식을 생각해보자
        // 이 중 n-1개의 더하기를 없애는 대신 양쪽의 수를 더할 수 있다고 하자.
        // 이 더하기를 없애는 방법의 수가 정답의 수이다.
        // 총 n - 1개의 더하기가 있으므로 n - 1개의 더하기를 선택해서 없애거나 선택하지 않는 경우의 수는
        // 2의 (n - 1) 제곱이다.
        // 따라서 n이 주어졌을 때, 답은 2^(n-1)이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long n = Long.parseLong(br.readLine());

        // 값의 범위는 넓으나 사용하는 수의 개수는 적으므로
        // 해쉬맵을 통해 메모이제이션을 활용하자.
        memo = new HashMap<>();
        // n이 0이라면 -1을 찾게되는데, 이 때의 초기값
        memo.put(-1L, 1L);
        // n이 1이라면 2^0은 1
        memo.put(0L, 1L);

        // 2의 (n-1)제곱을 출력한다.
        System.out.println(getPow(n - 1));
    }

    // 분할 정복
    static long getPow(long n) {
        // n에 대한 기존 결과값이 없다면
        if (!memo.containsKey(n)) {
            // 짝수일 경우 (n/2) * (n/2)로 계산
            if (n % 2 == 0)
                memo.put(n, (getPow(n / 2) * getPow(n / 2)) % LIMIT);
            // 홀수 일 경우 (n/2) * (n/2) * 2로 계산
            else
                memo.put(n, (getPow(n / 2) * getPow(n / 2) * 2) % LIMIT);
        }
        // 계산값 반환.
        return memo.get(n);
    }
}