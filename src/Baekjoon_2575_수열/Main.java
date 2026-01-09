/*
 Author : Ruel
 Problem : Baekjoon 2575번 수열
 Problem address : https://www.acmicpc.net/problem/2575
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2575_수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // m이 주어진다.
        // 문제 A : a1 + ... + an = m을 만족하며, a1 * ... * an의 값이 가장 크게 되는 수열의 최대 크기
        // 문제 B : a1 * ... * ai = m을 만족하며, a1 + ... + ai의 값이 최소인 수열의 최소 크기
        //
        // 소인수분해
        // 문제 A
        // 합이 일정할 때, 곱이 가장 크게하려면, 3이 가장 많은 것이 유리
        // 가능한 3으로 배정하되, 1은 곱했을 때, 아무런 값의 증가를 가져오지 않으므로, 나머지가 1인 경우
        // 3 + 1을 2 + 2로 분리한다.
        // 문제 B
        // 소인수분해를 하면 된다.
        // 단, 2의 경우, 2^2이나 4^1이나 값은 같지만 길이가 하나 줄게 된다. 따라서 먼저 4의 배수가 되는지 여부를 판단하고, 추후에 2가 남는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 m
        int m = Integer.parseInt(br.readLine());

        // 가능한 3을 많이 배정한다.
        // 나머지가 2인 경우, 추가로 2가 되며, 1인 경우는 앞의 3중에 하나를 2 + 1로 쪼개 3 + 1을 2 + 2로 만든다.
        // m이 3으로 나누어 떨어지는 경우는 추가로 개수를 안 더해줘도 된다.
        // int에서 나눗셈의 경우, 나머지가 버려지는 걸 이용하여
        // (m + 2) 값을 3으로 나누면, 나머지가 있는 경우는 몫이 증가, 없는 경우는 증가하지 않는다.
        int answer1 = (m + 2) / 3;

        int answer2 = 0;
        // 먼저 4로 나누어떨어지는 경우, 최대한 4로 배정.
        while (m % 4 == 0) {
            answer2++;
            m /= 4;
        }
        // 그 후, 2부터 소인수 분해
        // 제곱이 m보다 같거나 작을 때까지 반복
        for (int i = 2; i * i <= m; i++) {
            while (m % i == 0) {
                answer2++;
                m /= i;
            }
        }
        // m이 1보다 크다면, 남은 m이 하나의 인수인 경우. 이 때 하나 증가
        if (m > 1)
            answer2++;

        // 만약 m이 1이었던 경우. 수열에 1 하나는 있어야한다.
        // 따라서 answer가 0인 경우 1로 변경
        answer2 = Math.max(answer2, 1);
        // 답 출력
        System.out.println(answer1 + " " + answer2);
    }
}