/*
 Author : Ruel
 Problem : Baekjoon 20164 홀수 홀릭 호석
 Problem address : https://www.acmicpc.net/problem/20164
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20164_홀수홀릭호석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 10^9 미만의 자연수 n이 주어진다.
        // 다음과 같은 연산을 한다.
        // 각 자리의 숫자 중 홀수의 개수를 적는다.
        // 수가 한 자리라면 종료
        // 수가 두 자리라면 2개로 나눠 합을 구해, 새로운 수로 생각한다.
        // 수가 세 자리 이상이라면 임의의 위치에서 끊어, 3개의 수로 분할하고, 3개의 수를 더한 수를 새로운 수로 생각한다.
        // 종료된 순간 적힌 수의 개수를 모두 더한다.
        // 수의 최솟값과 최댓값은?
        //
        // 브루트 포스 문제
        // 자리수가 8자리로 크지 않으므로 직접 모든 경우의 수를 계산해볼 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 수 n
        int n = Integer.parseInt(br.readLine());
        int[] minMax = bruteForce(n);
        // 답 출력
        System.out.println(minMax[0] + " " + minMax[1]);
    }

    // 재귀를 통해 모든 경우의 수를 다 따져본다.
    static int[] bruteForce(int num) {
        if (num < 10) {
            // num이 한 자리 수라면 현재 홀수의 개수만 반환.
            int odd = countOdd(num);
            return new int[]{odd, odd};
        } else if (num < 100) {
            // 두 자리 수인 경우
            // 현재의 홀수의 개수를 구해두고
            int count = countOdd(num);
            // 새로운 십의 자리와 일의 자리 수를 더해 새로운 수로 만들어진 결과를 계산한다.
            int[] minMax = bruteForce(num / 10 + num % 10);
            // 해당 값에 현재 홀수의 개수를 더해 반환한다.
            for (int i = 0; i < 2; i++)
                minMax[i] += count;
            return minMax;
        }

        // 세 자리 이상의 수인 경우
        // 현재 홀수의 개수를 세고
        int count = countOdd(num);
        int[] minMax = new int[]{Integer.MAX_VALUE, 0};
        for (int i = 100; i <= num; i *= 10) {
            // i자리를 기준으로 한번 끊고
            for (int j = 10; j < i; j *= 10) {
                // j자리를 기준으로 끊어, 3개의 수를 만든다.
                // 해당 세 수를 더한 값을 새로운 수로 다시 재귀로 함수를 불러 결과값을 받고
                int[] mm = bruteForce(num / i + num % i / j + num % j);
                // 해당 결과값이 최솟값과 최댓값을 갱신하는지 확인.
                minMax[0] = Math.min(minMax[0], mm[0]);
                minMax[1] = Math.max(minMax[1], mm[1]);
            }
        }

        // 그 후, 현재 수의 홀수의 개수를 각각 더해준다.
        for (int i = 0; i < 2; i++)
            minMax[i] += count;
        return minMax;
    }
    
    // num의 각 자리들 중 홀수의 개수를 센다
    static int countOdd(int num) {
        int count = 0;
        while (num > 0) {
            if (num % 2 == 1)
                count++;
            num /= 10;
        }
        return count;
    }
}