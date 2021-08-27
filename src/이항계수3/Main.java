/*
 Author : Ruel
 Problem : Baekjoon 11401번 이항 계수 3
 Problem address : https://www.acmicpc.net/problem/11401
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 이항계수3;

import java.util.Scanner;

public class Main {
    static long[] factorial;
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        // 페르마의 소정리라는 수학적 지식이 필요했다.
        // 그 외 분할정복과 간단한 DP를 활용
        // nCr을 구할 때 이는 식으로 n! / (r! * (n - r)!)로 나타낸다
        // 이를 1_000_000_007으로 나눈 값을 구하라인데,
        // 나머지 연산의 분배법칙이 나눗셈에 대해서는 적용되지 않는다 여기서 적용되는 것이 페르마의 소정리
        // p가 소수일 때 어떤 수 n에 대해 n^(p-1) % p = 1 % p 이다 라는 것이 기본정리
        // 여기에서 양 쪽에 p^-1을 곱해
        // n^(p - 2) % p = n^(p -1) % p 라는 식을 이끌어내어
        //  나눗셈 대신 제곱 연산을 함으로써 같은 나머지 연산값을 얻어내는 것이 목적이다.
        // 여기서 제곱의 값이 크므로 Math에 기본 제공되는 pow 함수를 사용하면 x
        // 분할 정복을 이용하며, 도출된 값에 나머지 연산도 시행해서 값의 크기를 줄여주자
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        init(n);
        long answer = factorial[n];     // 이 부분이 nCr의 분자
        long multi = getPow(((factorial[n - k] * factorial[k]) % MOD), MOD - 2);    // 이 부분이 nCr의 분모. 1 / ((n - k)! * k!) 이 ((n - k)! * k!)^(1_000_000_007 - 2)으로 대체 되었다.
        answer = (answer * multi) % MOD;        // 두 수를 곱하고 나머지 연산을 취한 것이 답!
        System.out.println(answer);

    }

    static long getPow(long n, int pow) {       // 분할정복을 활용한 제곱
        if (pow == 0)       // 어떤 수의 0 제곱은 1
            return 1;
        else if (pow % 2 == 0) {        // 짝수라면
            long value = getPow(n, pow / 2);
            return (value * value) % MOD;   // 하나의 값을 구해 서로 곱해준 뒤 나머지 연산!
        }
        return (getPow(n, pow - 1) * n) % MOD;  // 홀수라면 값을 하나 빼, 짝수로 바꾸고, n을 직접 곱해 준 뒤 나머지 연산!
    }

    static void init(int n) {
        factorial = new long[n + 1];    // n! 뿐만 아니라 r!, (n - r)! 값을 사용할테니, DP로 먼저 만들어두자.
        factorial[0] = 1;
        factorial[1] = 1;

        for (int i = 2; i < factorial.length; i++)
            factorial[i] = (factorial[i - 1] * i) % MOD;
    }
}