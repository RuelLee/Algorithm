/*
 Author : Ruel
 Problem : Baekjoon 32404번 일이 커졌어
 Problem address : https://www.acmicpc.net/problem/32404
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32404_일이커졌어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 수들을 나열해 순열 p1, ... pn을 만든 후, 이 순열을 이용하여
        // x의 초기값은 1
        // i가 홀수라면 x에 pi를 곱하고
        // i가 짝수라면 x에 pi를 더한다
        // 는 과정을 반복하여 x값을 구한다.
        // 이 때, 얻을 수 있는 최댓값을 갖는 순열을 구하라
        //
        // 그리디 문제
        // 먼저 홀수일 경우, 곱한다는 행위를 생각해보면
        // 자기보다 먼저 등장한 수들에 대해 모두 곱해지는 것이다.
        // 따라서 곱하는 수는 마지막에 큰 수가 올수록 많은 수들에 큰 수를 곱해 값을 키울 수 있다.
        // 반대로 더하는 수는 이후로 등장하는 곱하는 수에 대해 자신이 곱해진다.
        // 따라서 더하는 수는 큰 수가 먼저 올수록 값이 커질 수 있다.
        // 일반적으로 곱하는 경우가 더하는 경우보단 값을 더 키울 수 있으므로
        // n까지 주어졌을 때
        // 1 ~ n / 2 까지의 정수에 짝수번째에 배치해 더해지도록 하고
        // n / 2 + 1 ~ n까지의 수들은 홀수번째에 배치해 곱해지도록 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 정수
        int n = Integer.parseInt(br.readLine());

        // 곱해지는 수는 작은 수부터 시작하여 큰 수로 값이 커져나가므로
        // 가장 작은 값을 구해둔다
        int multiMin = n / 2 + 1;
        // 더해지는 수는 큰 수부터 작은 수로 값이 작아지므로
        // 가장 큰 값을 구해둔다.
        int plusMax = n / 2;

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            // 홀수일 경우
            // 곱해지는 수를 하나 배치하고, 값을 키운다.
            if (i % 2 != 0)
                sb.append(multiMin++).append(" ");
            // 짝수일 경우
            // 더하는 수를 하나 배치하고 값을 줄인다.
            else
                sb.append(plusMax--).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}