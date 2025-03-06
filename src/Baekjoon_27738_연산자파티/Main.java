/*
 Author : Ruel
 Problem : Baekjoon 27738번 연산자 파티
 Problem address : https://www.acmicpc.net/problem/27738
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27738_연산자파티;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 변수 x의 초기값은 0이며
        // i가 1 ~ n까지 증가하는 동안
        // i가 a의 배수라면 x = x + i
        // i가 b의 배수라면 x = x % i
        // i가 c의 배수라면 x = x & i
        // i가 d의 배수라면 x = x ⊕ i
        // i가 e의 배수라면 x = x | i
        // i가 f의 배수라면 x = x >> i
        // 1 <= a <= b <= c <= d <= e <= f
        // 한 번에 여러 연산을 시행한다면 위의 주어진 순서대로 행한다.
        //
        // 애드 혹
        // f가 a ~ e들의 수보다 크거나 같다.
        // 따라서, f가 되는 순간 비트 연산을 통해 x는 0이 된다.
        // 따라서 n까지의 범위의 수들 중, 가장 큰 f의 배수를 구해
        // 그 이후의 수들에 대해서만 위 연산을 적용하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 수의 범위 n
        long n = Long.parseLong(br.readLine());
        
        // 연산에 관련되는 정수들 
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int f = Integer.parseInt(st.nextToken());

        long answer = 0;
        for (long i = (n / f) * f + 1; i <= n; i++) {
            // a의 배수일 경우 + i
            if (i % a == 0)
                answer += i;
            // b의 배수일 경우 i의 나머지
            if (i % b == 0)
                answer %= i;
            // c의 배수일 경우 & i
            if (i % c == 0)
                answer &= i;
            // d의 배수일 경우, ^ i
            if (i % d == 0)
                answer ^= i;
            // e의 배수일 경우, | i
            if (i % e == 0)
                answer |= i;
            // f의 배수일 경우 >> i
            if (i % f == 0)
                answer >>= i;
        }
        // 답 출력
        System.out.println(answer);
    }
}