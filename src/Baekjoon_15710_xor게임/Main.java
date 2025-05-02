/*
 Author : Ruel
 Problem : Baekjoon 15710번 xor 게임
 Problem address : https://www.acmicpc.net/problem/15710
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15710_xor게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static HashMap<Integer, Long> hashMap;
    static final long factor = Integer.MAX_VALUE + 1L;
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // a의 카드를 갖고 있으며,
        // n개의 턴 동안 0 ~ 2^31 -1이 적혀있는 카드 중 하나와 xor 하여
        // 최종적으로 b를 만들고 싶다.
        // 만들 수 있는 경우의 수는?
        // 값이 크므로 10^9 + 7로 나눈 나머지 값을 출력한다.
        //
        // 분할 정복 문제
        // xor 연산이므로, 어떠한 수든 -> b로 만드는데 한 턴이면 된다.
        // 따라서 n턴이 주어지면, n-1 턴 동안 2^31장의 카드 중 아무거나 xor하고
        // 마지막에만 해당하는 카드와 xor 연산해주면 b가 된다.
        // 따라서 위 문제는 2^31의 n-1 제곱을 구하는 문제와 같다.
        // a와 b는 어떠한 수이든 상관이 없다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 a, b, n
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        hashMap = new HashMap<>();
        // 0제곱일 때의 값 1
        hashMap.put(0, 1L);

        // 2^32의 n-1 제곱을 구해 값을 출력한다.
        System.out.println(findAnswer(n - 1));
    }

    // 2^31의 n제곱을 구한다.
    static long findAnswer(int n) {
        // n 제곱이 계산된 적이 없다면
        if (!hashMap.containsKey(n)) {
            // n/2 제곱과 n/2 제곱을 곱해 구한다.
            long value = findAnswer(n / 2) * findAnswer(n / 2) % LIMIT;
            // 만약 n이 홀수라면 추가로 2^31을 한번 더 곱한다.
            if (n % 2 == 1)
                value = value * factor % LIMIT;
            // 해당 값을 해쉬맵에 추가
            hashMap.put(n, value);
        }
        // 해당 값 반환.
        return hashMap.get(n);
    }
}