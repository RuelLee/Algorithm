/*
 Author : Ruel
 Problem : Baekjoon 12846번 무서운 아르바이트
 Problem address : https://www.acmicpc.net/problem/12846
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12846_무서운아르바이트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 각 일마다 일급이 정해져있으며, 전체 일한 날짜들 중 가장 적은 일급 * 일 수를 급여로 받는다.
        // 일은 연속한 날짜로만 할 수 있다고 할 때, 최대로 얻을 수 있는 급여는?
        //
        // 예전에 풀었던 히스토그램에서 가장 큰 직사각형(https://www.acmicpc.net/problem/6549)과 같은 문제
        // 히스토그램을 그리고 가장 큰 넓이를 갖는 직사각형을 찾으면 된다.
        // 이 때 stack을 이용하면 좋다.
        // 증가 모노톤 스택으로 만들며, 작은 값을 만났을 때, 큰 값들을 모두 꺼내며, 현재 막대 이전 ~ 스택에 들어있는 막대까지의 넓이를 구하며
        // 가장 큰 넓이의 사각형을 찾아주면 된다.
        // 처음과 끝에 0 길이의 막대를 넣어주면 스택에 막대가 남는 것을 생각하지 않아도 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] wages = new int[n + 2];
        // wages[0]과 wages[n + 1]에는 0을 채워넣어 0보다 큰 막대에 대해 모두 계산한다.
        for (int i = 1; i < wages.length - 1; i++)
            wages[i] = Integer.parseInt(st.nextToken());

        Stack<Integer> stack = new Stack<>();
        // 사각형 넓이의 최대값.
        long sum = 0;

        for (int i = 0; i < wages.length; i++) {
            // 스택이 비어있지 않고, 이번 막대가 스택의 peek보다 더 작은 막대라면
            while (!stack.isEmpty() && wages[stack.peek()] > wages[i]) {
                // 높이는 스택의 peek 막대기의 길이.
                int height = wages[stack.pop()];
                // 너비는 peek의 이전 막대(peek보다 같거나 작은 막대)보다 하나 큰 위치(peek보다 큰 막대 위치)부터
                // peek의 위치까지
                int width = i - 1 - stack.peek();
                // 넓이를 구해 최대값을 갱신.
                sum = Math.max(sum, (long) height * width);
            }
            // i번째 막대 stack에 push
            stack.push(i);
        }
        // 가장 큰 사각형의 넓이가 sum에 계산. 출력.
        System.out.println(sum);
    }
}