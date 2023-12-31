/*
 Author : Ruel
 Problem : Baekjoon 17497번 계산기
 Problem address : https://www.acmicpc.net/problem/17497
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17497_계산기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // 계산기를 통해
        // + : x에 2를 더함
        // - : x에 2를 뺌
        // * : x에 2를 곱함
        // / : x에 2를 나눔
        // 과 같은 4가지 연산을 할 수 있다
        // x가 음수 혹은 2^63 -1을 넘을 수는 없다.
        // n이 주어질 때 0을 n으로 만드는 방법 중 하나를 출력하라
        // 연산을 사용할 수 있는 횟수는 최대 99번이다.
        //
        // 그리디 문제
        // 처음 문제를 보면 BFS 문제인가...? 싶지만
        // 값의 범위를 보면 n이 최대 10^13까지 주어지므로 BFS로 해선 안된다.
        // 문제를 이진법으로 보며 역으로 n을 0으로 만드는 방법을 취한다.
        // 이진법으로 보아 1의 자리에 1이 들어있다면 이를 없앨 수 있는 방법은 없다.
        // 따라서 2를 곱해 1을 2^1의 1로 바꿔버린다.
        // 1의 자리에 0, 2^1의 자리에 1이 있다면 이는 - 연산으로 지울 수 있다.
        // 1의 자리에 0, 2^1의 자리에 0이 있다면 이는 / 연산으로 두 칸을 지울 수 있다.
        // 이 과정에 대해 순서와 과정을 반대로 기록하고, 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지눈 n
        long n = Long.parseLong(br.readLine());
        StringBuilder sb = new StringBuilder();
        // n에 2를 곱하더라도 값을 넘어서는 안된다.
        if (n < Long.MAX_VALUE / 2) {
            // 스택을 통해 역순으로 기록한다.
            Stack<Character> stack = new Stack<>();
            while (n > 0) {
                // 1의 자리에 값이 있다면 2를 곱한다.
                // 스택에는 / 로 넣는다.
                if ((n & 1) == 1) {
                    n *= 2;
                    stack.push('/');
                } else if ((n & (1 << 1)) == 2) {
                    // 1의 자리가 0, 2의 자리가 1이라면
                    // 2를 뺌으로써 1의 자리와 2의 자리를 0으로 만들 수 있다.
                    // 스택에는 + 로 넣는다.
                    n -= 2;
                    stack.push('+');
                } else {
                    // 1의 자리가 0, 2의 자리가 0이라면
                    // 2를 나눔으로써 두 자리를 지울 수 있다.
                    // 스택에는 * 로 넣는다.
                    n /= 2;
                    stack.push('*');
                }
            }

            // 기록된 연산의 횟수가 100보다 작은지 확인
            // 그렇다면 그 횟수와 연산을 꺼내며 기록하고
            if (stack.size() < 100) {
                sb.append(stack.size()).append("\n");
                while (!stack.isEmpty())
                    sb.append('[').append(stack.pop()).append(']').append(" ");
                sb.deleteCharAt(sb.length() - 1);
            } else      // 그렇지 않다면 -1 기록
                sb.append(-1);
        } else      // n에 2를 곱한 값이 2^63 이상이라면 -1 기록
            sb.append(-1);

        // 답안 출력
        System.out.println(sb);
    }
}