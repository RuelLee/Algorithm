/*
 Author : Ruel
 Problem : Baekjoon 25342번 최대 최소공배수
 Problem address : https://www.acmicpc.net/problem/25342
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25342_최대최소공배수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 수가 주어질 대
        // 최소공배수가 최대가 되도록 서로 다른 3개의 수를 선택해보자
        //
        // 유클리드 호제법 문제
        // 먼저 세 수의 최소공배수를 구하기 위해서는
        // 두 수에 대해 최대공약수를 구하고, 두 수의 곱 / 최대공약수를 진행해
        // 두 수에 대한 최소공배수를 구하고, 구해진 최소공배수와 다른 한 수에 대해서도 최소공배수를 구하는 방법을 진행한다.
        // 이제 세 수를 어떻게 고르냐가 문제인데
        // 세 수 중 두 수가 홀수이고, 한 수가 짝수일 때가 최소공배수가 컸다
        // 따라서 (n, n-1, n-2)인 경우와 (n-1, n-2, n-3), (n, n-1, n-3)인 세 경우를 비교해
        // 가장 큰 값을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 1 ~ n까지의 범위
            int n = Integer.parseInt(br.readLine());
            // 최소공배수
            long lcm = Math.max(Math.max(getLCM(n, n - 1, n - 2), getLCM(n - 1, n - 2, n - 3)), getLCM(n, n - 1, n - 3));
            sb.append(lcm).append("\n");
        }
        System.out.print(sb);
    }
    
    // a, b, c의 최소 공배수를 구한다.
    static long getLCM(int a, int b, int c) {
        // a, b에 대한 최소공배수를 먼저 구하고
        long lcm = (long) a * b / getGCD(a, b);
        // lcm와 c에 대한 최소공배수를 구해 반환.
        return lcm * c / getGCD(lcm, c);
    }
    
    // 유클리드 호제법을 통해 최대 공약수를 구한다.
    static long getGCD(long a, long b) {
        long big = Math.max(a, b);
        long small = Math.min(a, b);
        while (small > 0) {
            long temp = big % small;
            big = small;
            small = temp;
        }
        return big;
    }
}