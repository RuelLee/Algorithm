/*
 Author : Ruel
 Problem : Baekjoon 1016번 제곱 ㄴㄴ 수
 Problem address : https://www.acmicpc.net/problem/1016
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1016_제곱ㄴㄴ수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어떤 수 x가 1보다 큰 제곱수로 나누엉 떨어지지 않을 때, 제곱ㄴㄴ수라고 한다.
        // min보다 같거나 크고, max보다 같거나 작은 수들 중 제곱ㄴㄴ수의 개수를 출력하라.
        //
        // 에라토스테네스의 체 문제
        // max - min이 최대 100만으로 주어진다.
        // max의 최대값은 1조이다.
        // 따라서 제곱수는 100만 까지 살펴보면 100만의 제곱이 1조이므로 충분히 가능한 연산.
        // 100만까지의 수 중 제곱수를 살펴보며, 해당 범위에 속한 수들을 체크해준다.
        // 마지막에 체크되지 않은 수의 개수를 센다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 min, max
        long min = Long.parseLong(st.nextToken());
        long max = Long.parseLong(st.nextToken());

        boolean[] check = new boolean[(int) (max - min + 1)];
        // i의 제곱이 max보다 같거나 작은 동안
        for (long i = 2; i * i <= max; i++) {
            // 제곱 수 num
            long num = i * i;
            // 범위에 속한 num으로 나누어떨어지는 수들을 체크
            for (long j = (min + num - 1) / num; num * j <= max; j++)
                check[(int) (num * j - min)] = true;
        }

        // 체크 되지 않은 수들을 센다.
        int cnt = 0;
        for (int i = 0; i < check.length; i++) {
            if (!check[i])
                cnt++;
        }
        // 답 출력
        System.out.println(cnt);
    }
}