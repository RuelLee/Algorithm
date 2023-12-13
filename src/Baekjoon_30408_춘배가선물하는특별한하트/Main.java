/*
 Author : Ruel
 Problem : Baekjoon 30408번 춘배가 선물하는 특별한 하트
 Problem address : https://www.acmicpc.net/problem/30408
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30408_춘배가선물하는특별한하트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // ng의 하트를 갖고 있다.
        // 이를 나눠 mg의 하트로 만들어 선물하고자 한다.
        // 나누는 방법은
        // n이 짝수일 경우에는 n/2g 두개의 하트가 되고
        // 홀수일 경우에는 (n-1)/2g과 (n+1)/2g의 하트 두개가 된다.
        // mg의 하트를 만들 수 있는지 계산하라
        //
        // 메모이제이션 문제
        // n부터 m이하가 될 때까지 쪼개나가면서
        // 가능한 g수들에 대한 결과들을 정리한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 시작 n
        long n = Long.parseLong(st.nextToken());
        // 도달 m
        long m = Long.parseLong(st.nextToken());

        // 메모이제이션 활용
        HashMap<Long, Boolean> memo = new HashMap<>();
        // n을 m으로 쪼갤 수 있는지 결과 출력.
        System.out.println(findAnswer(n, m, memo) ? "YES" : "NO");
    }

    static boolean findAnswer(long n, long m, HashMap<Long, Boolean> memo) {
        // 처음 방문한 n이라면
        if (!memo.containsKey(n)) {
            // n이 m보다 같거나 작은 경우
            if (n <= m)     // 같다면 true 반환, 아니라면 false 반환
                return n == m;

            // m이 n보다 큰 경우에는 쪼개야한다.
            // 짝수인 경우
            if (n % 2 == 0)
                memo.put(n, findAnswer(n / 2, m, memo));
            else {      // 홀수인 경우에는 두 개의 서로 다른 무게로 나누어지므로
                // 각각 계산하여 true가 돌아올 경우, 해당 결과를 기록한다.
                memo.put(n,
                        findAnswer(n / 2, m, memo) || findAnswer(n / 2 + 1, m, memo));
            }
        }
        // n에서 m까지 쪼개는 것이 가능한지
        // 메모이제이션된 결과를 반환한다.
        return memo.get(n);
    }
}