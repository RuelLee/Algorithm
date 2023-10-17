/*
 Author : Ruel
 Problem : Baekjoon 11025번 유세푸스 문제 3
 Problem address : https://www.acmicpc.net/problem/11025
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11025_요세푸스문제3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n번까지 사람이 원을 이루고 앉아있다.
        // 1번부터 k번째 사람을 제거한 후, 남은 사람들 중 다시 k번째 사람을 제거하는 행동을 반복한다.
        // 마지막에 남는 사람은?
        //
        // dp문제
        // dp이긴하지만 배열을 사용하거나 하진 않고, 점화식을 통해 이전 결과값을 지속적으로 사용한다.
        // f(n, k)에서 첫번째 사람을 제거한 후, 이후 남는 6명이서 하는 행동은
        // f(n-1, k)와 완전히 동일하다.
        // 따라서 서로 간의 번호 차이만 보정해준다면 f(n-1, k)에서 사용한 결과값을 f(n, k)에서도 사용할 수 있다.
        // f(n, k) = ((f(n-1, k) + k-1) % n + 1
        // 일반화하면 위 점화식을 얻게되고 이를 통해 값을 구할 수 있다.
        // f(1, k)는 k가 어떤 값이든 1이다.
        // 코딩 자체는 간단하나, 아이디어 생각이 너무 어려운 문제.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n명의 사람들 중 k번째 사람을 제거해나간다.
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // f(1, k)일 때 답은 1
        int answer = 1;
        // 이를 f(n, k)까지 구한다.
        for (int i = 2; i <= n; i++)
            answer = (answer + k - 1) % i + 1;

        // 답안 출력.
        System.out.println(answer);
    }
}