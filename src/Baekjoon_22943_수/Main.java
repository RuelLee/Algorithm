/*
 Author : Ruel
 Problem : Baekjoon 22943번 수
 Problem address : https://www.acmicpc.net/problem/22943
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22943_수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static boolean[] notPrimeNumber;
    static int k, m, SIZE;

    public static void main(String[] args) throws IOException {
        // 0 ~ 9 까지 k가지의 수를 한 번씩만 사용하여 만든 수들 중
        // 다음 두 조건을 만족하는 수의 개수를 구하라
        // 1. 서로 다른 두 소수의 합
        // 2. m으로 나누어떨어지지 않을 때까지 나눈 수가 두 소수의 곱(이 때 같은 소수여도 됨)
        //
        // 브루트 포스, 에라토스테네스의 체 문제
        // 먼저, 에라토스테네스의 체로 k에 따른 최대 범위에 대해 소수들을 구한다.
        // 최대 약 10만으므로 그리 크지 않다.
        // 0 ~ 9까지의 수를 한 번씩만 사용한다는 조건이 유니크한 조건이다.
        // 따라서 해당 조건을 만족하는 수들을 브루트 포스를 통해 만든 후
        // 해당 수가 1, 2번 조건을 만족하는지 확인하며 개수를 세어 준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // k가지의 수, 나누는 수 m
        k = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // k에 따른 범위 제한
        SIZE = 98765;
        for (int i = 0; i < 5 - k; i++)
            SIZE /= 10;

        // 에라토스테네스의 체
        notPrimeNumber = new boolean[SIZE + 1];
        notPrimeNumber[1] = true;
        for (int i = 2; i <= SIZE; i++) {
            if (notPrimeNumber[i])
                continue;

            for (int j = 2; i * j <= SIZE; j++)
                notPrimeNumber[i * j] = true;
        }

        // k가지의 수를 한번씩 사용하여 만든 수가 조건을 만족하는지 확인 후 개수 출력
        System.out.println(makeNum(0, 0, new boolean[10]));
    }

    // 현재 수의 자릿수 idx, 현재 수 num, 사용된 수 체크 used
    static int makeNum(int idx, int num, boolean[] used) {
        // k자리의 수가 만들어졌다면
        if (idx == k) {
            // 첫번째 조건 소수의 합인지 체크
            if (isPrimeNumSum(num)) {
                // 두번째 조건 m으로 나누어떨어지지 않을 때까지 나눈 후
                while (num % m == 0)
                    num /= m;

                // 소수의 곱인지 체크
                for (int i = 2; i * i <= num; i++) {
                    // 맞다면 1 반환
                    if (num % i == 0 && !notPrimeNumber[i] && !notPrimeNumber[num / i])
                        return 1;
                }
            }
            // 위 조건들에 속하지 않는다면 0 반환
            return 0;
        }

        // k자리의 수가 아닌 경우
        // 현재 경우에서 파생되는 조건에 맞는 수의 개수 누적
        int sum = 0;
        for (int i = 0; i < used.length; i++) {
            // 만약 첫번째 자리라면 0이 와서는 안 된다.
            if (idx == 0 && i == 0)
                continue;

            // 사용하지 않으 수라면
            if (!used[i]) {
                used[i] = true;
                // 마지막에 i를 붙여 다음 경우의 수들을 체크
                // 반환된 가능한 경우의 수는 sum에 누적
                sum += makeNum(idx + 1, num * 10 + i, used);
                used[i] = false;
            }
        }
        return sum;
    }

    // num이 서로 다른 두 소수의 합으로 표현되는지 체크
    static boolean isPrimeNumSum(int num) {
        for (int i = 2; i < num / 2; i++) {
            if (!notPrimeNumber[i] && !notPrimeNumber[num - i])
                return true;
        }
        return false;
    }
}