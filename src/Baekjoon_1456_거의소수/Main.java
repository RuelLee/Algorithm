/*
 Author : Ruel
 Problem : Baekjoon 1456번 거의 소수
 Problem address : https://www.acmicpc.net/problem/1456
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1456_거의소수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어떤 수가 n제곱(n>=2)일 때 그 수를 거의 소수라고 한다.
        // 두 정수 a, b가 주어질 때, a보다 크거나 같고, b보다 작거나 같은 거의 소수가 몇 개 인지 출력하라
        //
        // 에라토스테네스의 체 문제
        // a와 b가 최대 10^14으로 주어진다.
        // n이 최소 2이므로
        // sqrt(b) 까지의 소수를 판별하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 범위 a ~ b
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        // 에라토스테네스의 체
        // root b 까지의 범위만 계산
        boolean[] primeNumbers = new boolean[(int) Math.sqrt(b) + 1];
        Arrays.fill(primeNumbers, true);
        primeNumbers[0] = primeNumbers[1] = false;
        for (int i = 2; i < primeNumbers.length; i++) {
            if (!primeNumbers[i])
                continue;
            for (int j = 2; i * j < primeNumbers.length; j++)
                primeNumbers[i * j] = false;
        }

        // 거의 소수의 개수를 센다.
        int count = 0;
        for (int i = 2; i < primeNumbers.length; i++) {
            // 소수가 아니면 건너뛰고
            if (!primeNumbers[i])
                continue;
            
            // a보다 같거나 큰 범위에서 가장 작은 i의 제곱을 구한다.
            int min = (int) Math.ceil(Math.log(a) / Math.log(i));
            // n은 2보다 같거나 커야하므로, 해당 사항 반영
            min = Math.max(min, 2);
            // b보다 같거나 작은 범위에서 가장 큰 i의 제곱을 구한다.
            int max = (int) Math.floor(Math.log(b) / Math.log(i));
            // min <= max가 성립한다면
            // 해당 제곱들의 개수를 구해 더한다.
            if (min <= max)
                count += max - min + 1;
        }
        // 정답 출력
        System.out.println(count);
    }
}