/*
 Author : Ruel
 Problem : Baekjoon 1354번 무한 수열 2
 Problem address : https://www.acmicpc.net/problem/1354
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1354_무한수열2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static int p, q, x, y;
    static HashMap<Long, Long> memo;

    public static void main(String[] args) throws IOException {
        // Ai = 1 (i<=0)
        // Ai = A⌊i/P⌋-X + A⌊i/Q⌋-Y (i ≥ 1)
        // ⌊x⌋는 x를 넘지 않는 가장 큰 정수이다.
        // n, p, q, x, y가 주어질 때
        // An을 구하라
        //
        // 메모이제이션 문제
        // n이 10조까지 크게 주어지므로 범위에 주의하며
        // 메모이제이션을 통해 연산을 줄여 값을 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 상수 값
        long n = Long.parseLong(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());

        // 값의 범위가 넓으므로
        // 해쉬맵을 통해 메모이제이션을 한다.
        memo = new HashMap<>();
        // findAnswer을 통해 값을 찾고 출력.
        System.out.println(findAnswer(n));
    }

    static long findAnswer(long n) {
        // n이 0보다 같거나 작다면 그 값은 1
        if (n <= 0)
            return 1;
        else if (!memo.containsKey(n))      // n이 메모이제이션되지 않은 값이라면 그 값을 주어진 식에 따라 계산.
            memo.put(n, findAnswer(n / p - x) + findAnswer(n / q - y));
        // 이전에 메모이제이션을 했든, 방금했든 여기서부턴 값이 존재한다.
        // 해당 값을 반환.
        return memo.get(n);
    }
}