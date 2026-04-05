/*
 Author : Ruel
 Problem : Baekjoon 1437번 수 분해
 Problem address : https://www.acmicpc.net/problem/1437
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1437_수분해;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 10_007;

    public static void main(String[] args) throws IOException {
        // 음이 아닌 정수 n을 한 개 이상의 음이 아닌 정수의 합으로 나타낼 때
        // 이를 n을 분해한다고 한다.
        // 4는
        // 1 + 1 + 1 + 1
        // 1 + 1 + 2
        // 1 + 3
        // 2 + 2
        // 4
        // 와 같이 다섯가지의 경우로 나타낼 수 있다.
        // 이 때 각각의 분해한 수를 곱한 값이 최대인 값을 구하라.
        // 위의 경우 1 * 1 * 1 * 1 = 1, 1 * 1 * 2 = 2, 1 * 3 = 3, 2 * 2 = 4, 4 = 4이다.
        //
        // 그리디 문제
        // 합이 일정한 수들의 곱이 최대가 되게끔 하고자 한다면
        // 최대한 3을 많이 배치하되, 4가 남는 경우에는 1 + 3이 아닌 2 + 2으로 나눈다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 곱
        int answer = 1;
        // n이 4보다 큰 경우는 무조건 3으로 하나씩 분해
        while (n > 4) {
            answer = (answer * 3) % LIMIT;
            n -= 3;
        }
        // 남은 수는 0, 2 ~ 4
        // 0인 경우는 처음에 n이 0이었던 경우.
        // 답이 0이어야하므로 n을 곱하는 것이 맞다.
        // 2 ~ 4인 경우도 분해곱이 각각
        // 2 3 4인 경우가 최대인 경우가 맞으므로 n을 곱한다.
        answer = (answer * n) % LIMIT;
        // 답 출력
        System.out.println(answer);
    }
}