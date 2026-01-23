/*
 Author : Ruel
 Problem : Baekjoon 1351번 무한 수열
 Problem address : https://www.acmicpc.net/problem/1351
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1351_무한수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static HashMap<Long, Long> hashMap = new HashMap<>();
    static long p, q;

    public static void main(String[] args) throws IOException {
        // n과 p, q가 주어진다.
        // A0 = 1
        // Ai = A(floor(i / p)) + A(floor(i / q))로 정의 될 때
        // An의 값을 찾아라.
        //
        // 메모이제이션, 맵
        // 맵과 메모이제이션을 이용한 간단한 문제
        // N의 값이 크더라도 p와 q가 2이상이기 때문에 값이 1/2보다 같거나 작게끔 작아진다.
        // 따라서 Ai에서 A(i / p) + A(i / q)를 계산하고 Ai 값 자체는 저장해두는 방식으로 계산한다.
        // 추후에 Ai 값을 다시 요구한다면 맵을 통해 참조만 하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 n, p, q
        long n = Long.parseLong(st.nextToken());
        p = Long.parseLong(st.nextToken());
        q = Long.parseLong(st.nextToken());

        // A0의
        hashMap.put(0L, 1L);
        // An의 값 출력
        System.out.println(findAnswer(n));
    }

    // An을 구한다.
    static long findAnswer(long n) {
        // 이미 계산된 적이 있다면 값을 참조
        if (hashMap.containsKey(n))
            return hashMap.get(n);

        // 그 외의 경우
        // A(i / p) + A(i / q)를 계산해 값을 맵에 기록시켜두고 반환.
        hashMap.put(n, findAnswer(n / p) + findAnswer(n / q));
        return hashMap.get(n);
    }
}