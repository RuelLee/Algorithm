/*
 Author : Ruel
 Problem : Baekjoon 11690번 LCM(1, 2, ..., n)
 Problem address : https://www.acmicpc.net/problem/11690
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11690_LCM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final long LIMIT = (long) Math.pow(2, 32);

    public static void main(String[] args) throws IOException {
        // n이 주어질 때, 1 ~ n까지의 모든 자연수의 최소공배수를 구하라
        // 값이 커질 수 있으므로 2^32으로 나눈 나머지를 출력한다
        //
        // 에라토스테네스의 체 문제
        // 1 ~ n까지의 최소공배수를 lcm이라 할 때
        // lcm을 소인수분해하면 a^ai * b^bi * ...
        // 형태가 된다.
        // 이 때 인수들은 소수이다.
        // 따라서 n까지의 모든 소수를 구해
        // n까지의 범위에 해당 소수가 최대 몇 제곱까지 들어가는지 구하고
        // 해당 제곱을 계속 곱해나가며 답을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 n
        int n = Integer.parseInt(br.readLine());
        
        // 에라토스테네스의 체
        // 소수 판별
        boolean[] notPrimeNumber = new boolean[n + 1];
        // 답
        long answer = 1;
        for (int i = 2; i < notPrimeNumber.length; i++) {
            // 이미 소수가 아니라면 건너뛴다.
            if (notPrimeNumber[i])
                continue;

            // i의 배수들을 모두 소수가 아니므로 체크.
            for (int j = 2; i * j < notPrimeNumber.length; j++)
                notPrimeNumber[i * j] = true;

            // n보다 작거나 같은 가장 큰 i의 제곱수를 구한다.
            long pow = 1;
            while (pow * i <= n)
                pow *= i;
            // 해당 제곱수를 answer에 곱하고
            answer *= pow;
            // 2^32으로 나눈 나머지를 기록해둔다.
            answer %= LIMIT;
        }
        // 답안 출력
        System.out.println(answer);
    }
}