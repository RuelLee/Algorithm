/*
 Author : Ruel
 Problem : Baekjoon 2504번 괄호의 값
 Problem address : https://www.acmicpc.net/problem/2504
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2504_괄호의값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;


public class Main {
    static final int small = 100000000;
    static final int big = 100000001;

    public static void main(String[] args) throws IOException {
        // (, ), [, ]를 이용해서 만들어지는 괄호열 중 올바른 괄호열은 다음을 만족한다.
        // 한 쌍의 괄호로 이루어진 () 와 []을 올바르다.
        // x가 올바르면 (x)나 [x]도 올바르다
        // x와 y가 올바르다면 xy도 올바르다
        //
        // ()의 괄호열의 값은 2이고 []은 3이다
        // (x)는 x * 2, [x]는 x * 3으로 계산된다.
        // 주어진 괄호열이 올바르다면 그 값을, 그렇지 않다면 0을 출력한다
        //
        // 스택 문제
        // 스택을 통해 괄호들의 쌍이 맞는지 확인해가며 값을 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        // 올바르지 않은 괄호열
        boolean error = false;
        // 스택
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            // 현재 문자
            char current = s.charAt(i);

            // 여는 괄호라면 스택에 추가
            if (current == '(' || current == '[')
                stack.push(current == '(' ? small : big);
                // 비어있는데 여는 괄호가 아니라면 올바르지 않은 괄호열
            else if (stack.isEmpty()) {
                error = true;
                break;
            } else {
                int value = 0;
                // 스택의 최상단에 괄호가 아니라 값이 존재한다면
                // 해당 값들을 모두 더한다.
                while (!stack.isEmpty() && stack.peek() != small && stack.peek() != big)
                    value += stack.pop();
                // 만약 값을 모두 꺼내었는데,
                // 여는 괄호가 스택에 존재하지 않는다면 올바르지 않은 문자열
                if (stack.isEmpty()) {
                    error = true;
                    break;
                }
                
                // 현재 닫는 괄호와 일치하는 쌍의 여는 괄호가 스택에 잘 담겨있다면
                if (stack.peek() + (current == ')' ? -small : -big) == 0) {
                    stack.pop();
                    // 해당 여는 괄호 제거
                    // 기존의 값이 없다면 해당하는 괄호열의 값을 스택에 추가
                    if (value == 0)
                        stack.push(current == ')' ? 2 : 3);
                    else        // 값이 존재한다면 곱으로 계산
                        stack.push(value * (current == ')' ? 2 : 3));
                } else {        // 쌍이 맞지 않는 괄호열이라면 올바르지 않은 괄호열
                    error = true;
                    break;
                }
            }
        }

        if (!error) {       // 마지막까지 살펴봤을 때, 닫는 괄호에 모두 여는 괄호가 매칭된 경우.
            int sum = 0;
            while (!stack.isEmpty()) {
                // 아직 스택에 여는 괄호가 있다면 올바르지 않은 괄호열
                if (stack.peek() == small || stack.peek() == big) {
                    // 올바르지 않기 때문에 sum에 0 값을 대입하고 반복문 종료.
                    sum = 0;
                    break;
                }
                // 값이라면 모두 더한다.
                sum += stack.pop();
            }
            // 값 출력
            System.out.println(sum);
        } else      // 올바르지 않은 괄호열이므로 0 출력.
            System.out.println(0);
    }
}