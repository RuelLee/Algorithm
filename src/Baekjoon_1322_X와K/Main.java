/*
 Author : Ruel
 Problem : Baekjoon 1322번 X와 K
 Problem address : https://www.acmicpc.net/problem/1322
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1322_X와K;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // x와 k가 주어진다.
        // x + y = x | y인 k번째 수를 찾아 출력하라
        //
        // x와 k는 20억보다 작거나 같은 수이다.
        //
        // 비트마스킹 문제
        // + 와 | 연산이 같아지려면 x와 y가 서로 중복되는 비트가 없어야한다.
        // 그런 수들 중 k번째 수를 찾아야한다.
        // 중복되지 않는 가장 작은 비트가 다르면 그 수는 첫번째이고
        // 두번째 비트가 다르면 두번째, 세번째 비트가 다르면 4번째, ...
        // i번째 비트가 다르면 그 수는 2의 (i - 1)제곱 번째 수가 된다.
        // 이를 이용해, 서로 달라야하는 비트의 순서들을 찾고
        // 해당하는 비트들을 | 연산으로 합쳐 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // x + y = x | y를 만족하는 k번째 y를 찾는다.
        int x = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        Stack<Integer> stack = new Stack<>();
        // k번째 수를 찾아야한다.
        for (int i = 31; i > 0; i--) {
            // 2의 (i - 1)보다 남은 k가 크거나 같은지 비교한다.
            // k가 같거나 더 크다면 i번째 다른 비트는 1이여야한다.
            // 해당 순서를 스택에 담고, 해당 순서만큼을 k에서 뺀다.
            if (k >= (1 << (i - 1))) {
                stack.push(i);
                k -= (1 << (i - 1));
            }
        }

        long answer = 0;
        int cnt = 0;
        // stack에 담긴 달라야하는 비트의 순서를 가지고서 y를 구한다.
        for (int i = 0; i <= 62 && !stack.isEmpty(); i++) {
            // x의 i번째 비트가 0이고, 해당 비트가 1이여야하는 경우
            if ((x & (1L << i)) == 0 && stack.peek() == ++cnt) {
                // answer에 | 연산으로 값 누적
                answer |= (1L << i);
                // 스택에서 제거
                stack.pop();
            }
        }
        // 구한 answer 출력
        System.out.println(answer);
    }
}