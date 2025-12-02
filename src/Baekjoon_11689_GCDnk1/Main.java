/*
 Author : Ruel
 Problem : Baekjoon 11689번 GCD(n, k) = 1
 Problem address : https://www.acmicpc.net/problem/11689
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11689_GCDnk1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static long n;
    static List<Long> factors;

    public static void main(String[] args) throws IOException {
        // 자연수 n이 주어질 때, GCD(n, k) = 1을 만족하는 n이하의 k의 개수를 구하라
        //
        // 소인수분해, 포함 배제의 원리
        // n이 최대 10^12
        // 가장 작은 소수들을 오름차순으로 곱해갈 때, 12번째 소수에서 10^12을 넘는다.
        // 따라서 n을 소인수분해했을 때, 소인수의 개수는 최대 11개
        // 그렇다면, n을 소인수분해하여, 소인수들의 조합과 포함 배제의 원리로
        // n과 gcd가 1이 되지 않는 수들의 개수를 구한다.
        // n에서 구한 개수를 빼주면 답
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어진 n
        n = Long.parseLong(br.readLine());
        
        // 소인수
        factors = new ArrayList<>();
        long temp = n;
        for (long i = 2; i * i <= n; i++) {
            if (temp % i == 0) {
                factors.add(i);
                while (temp % i == 0)
                    temp /= i;
            }
        }

        // temp가 1이 아닌 경우 temp도 담는다.
        if (temp != 1)
            factors.add(temp);

        long count = 0;
        // 포함 배제의 원리
        // 선택한 소인수가 홀수인 경우 -> 합산
        // 짝수인 경우 -> 차감
        for (int i = 1; i <= factors.size(); i++)
            count += getMultiples(0, i, 1) * (i % 2 == 1 ? 1 : -1);
        // n과 gcd가 1이 아닌 수의 개수 count
        // n - count = n과의 gcd가 1인 수의 개수
        System.out.println(n - count);
    }

    // 현재 소인수의 idx번째 수 차례이며
    // 아직 남은 선택해야하는 수 remain개
    // 현재까지 선택한 수의 곱 multiple
    static long getMultiples(int idx, int remain, long multiple) {
        // 남은 수가 0개라면
        // 소인수 선택이 끝났다. multiple이 조합으로 선택한 n과 GCD가 1이 아닌 수
        // n 범위에서 multiple의 배수들의 개수를 반환
        if (remain == 0)
            return n / multiple;
        // idx가 소인수 개수를 넘어간다면 온전히 선택하지 못한 경우
        // 0 반환
        else if (idx >= factors.size())
            return 0;

        // 아직 선택해야할 수가 남은 경우
        // idx번째 수를 선택하지 않는 경우와
        // idx번째 수를 선택하는 경우 모두 합산.
        return getMultiples(idx + 1, remain, multiple)
                + getMultiples(idx + 1, remain - 1, multiple * factors.get(idx));
    }
}