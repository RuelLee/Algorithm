/*
 Author : Ruel
 Problem : Baekjoon 14727번 퍼즐 자르기
 Problem address : https://www.acmicpc.net/problem/14727
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14727_퍼즐자르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // 히스토그램을 구성하는 n개의 직사각형이 순서대로 주어진다.
        // 이들을 잘라 최대 넓이의 직시각형을 만들고자할 때, 그 넓이는?
        //
        // 스택 문제
        // 스택을 비내림차순으로 관리해주며 넓이를 계산해면 되는 문제
        // 1. 스택의 최상단보다 작은 히스토그램이 들어올 경우
        //    스택의 최상단 a를 꺼내 해당 히스토그램의 높이를 기록해둔다.
        //    스택의 다음 최상단 b와의 떨어진 거리를 계산해 너비로 기록해둔다. b를 꺼내지는 않는다.
        //    두 값을 곱한 것이, a를 통해 만들 수 있는 최대 넓이가 된다.
        //    이 과정을 최상단이 현재 히스토그램보다 같거나 작아질 때까지 반복한다.
        // 2. 스택의 최상단이 같거나 클 경우
        //    아무 것도 하지 않는다.
        //    1 혹은 2 과정을 진행하고 현재 히스토그램을 스택에 넣는다.
        // 이렇게 계산하면 스택을 비내림차순으로 관리하며, 최대 넓이를 구할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 히스토그램들
        // 첫번째와 마지막에 0을 넣어, 모든 막대들이 계산될 수 있도록 함.
        int[] sticks = new int[n + 2];
        for (int i = 1; i < n + 1; i++)
            sticks[i] = Integer.parseInt(br.readLine());

        long max = 0;
        Stack<Integer> stack = new Stack<>();
        // 순차적으로 살펴보며
        for (int i = 0; i < sticks.length; i++) {
            // 스택이 비어있지 않고, 최상단 값이 현재 값보다 클 경우.
            while (!stack.isEmpty() && sticks[stack.peek()] > sticks[i]) {
                // 최상단의 높이
                long height = sticks[stack.pop()];
                // 최상단 값부터, 다음 최상단 값까지의 너비
                long width = (i - 1) - (stack.peek() + 1) + 1;
                // 넓이 계산
                max = Math.max(max, height * width);
            }
            // 현재 히스토그램을 스택에 추가.
            stack.push(i);
        }
        // 구한 최대 넓이 출력
        System.out.println(max);
    }
}