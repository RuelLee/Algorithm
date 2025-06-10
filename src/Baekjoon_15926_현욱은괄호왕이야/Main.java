/*
 Author : Ruel
 Problem : Baekjoon 15926번 현욱은 괄호왕이야!!
 Problem address : https://www.acmicpc.net/problem/15926
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15926_현욱은괄호왕이야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // ()는 올바른 괄호 문자열이다.
        // x, y가 옳은 괄호 문자열이라면, (x), xy도 옳은 괄호 문자열이다.
        // 괄호 문자열에서 위 조건을 만족하는 가장 긴 부분 문자열의 길이를 구하라.
        //
        // 스택 문제
        // 우선 괄호가 열렸다면, 반드시 닫혀야한다.
        // 또한 괄호문자열끼리 이어붙이는 것도 가능하다.
        // 따라서 스택의 성질 후입선출을 이용하여 문제를 해결한다.
        // 먼저 스택에 idx를 넣되, 넣는 idx의 기준은 여는 괄호이다.
        // 그리고 닫는 괄호를 만나면 여는 괄호를 스택에서 꺼낸다.
        // 그 후, 스택이 비어있지 않다면, 가장 마지막의 짝을 이루지 못한 여는 괄호 문자의 idx를 가르키고 있다.
        // 해당 idx +1 ~ 현재 idx까지가 현재 계산하고 있는 괄호 문자열의 길이이다.
        // 만약 비어있다면, 현재까지의 모든 괄호가 짝을 지었거나, 닫는 괄호가 더 많이 등장해서이다.
        // 후자는 상관없지만, 전자의 경우는 문자열의 길이가 체크되어야한다.
        // 따라서 스택에 하나 조건을 더 추가하여
        // 닫는 괄호이되, 문자열이 초기화되는 지점도 idx에 넣는 것으로 한다.
        // 위 경우, 스택에 여는 괄호들을 넣기 때문에, 닫는 괄호 끼리 매칭되지 않을까? 싶지만 초기화되는 지점이기 때문에
        // 매칭되면 스택이 비어져버린다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 문자열
        int n = Integer.parseInt(br.readLine());
        char[] input = br.readLine().toCharArray();

        // 스택
        Stack<Integer> stack = new Stack<>();
        // 0부터 시작이므로, -1에 초기화되었다고 가정하자.
        stack.push(-1);
        int maxLength = 0;
        for (int i = 0; i < input.length; i++) {
            // 여는 괄호일 경우, 해당 idx push
            if (input[i] == '(')
                stack.push(i);
            else if (!stack.isEmpty()) {
                // 닫는 괄호이며 스택이 비어있지 않은 경우
                // 여는 괄호이거나 초기화된 지점. 어쨌든 현재 닫는 괄호와 매칭시켜 꺼낸다.
                stack.pop();
                // 그 후, 스택이 비어있지 않는다면, 이전에 같이 꺼낸 idx는 여는 괄호이므로
                // 현재 최상단 +1 ~ i까지가 문자열의 길이
                if (!stack.isEmpty())
                    maxLength = Math.max(maxLength, i - stack.peek());
                else        // 비었다면, 닫는 괄호이되 초기화 지점이었던 것. 마찬가지로 이번에도 초기화되었으므로 해당 idx push
                    stack.push(i);
            }
        }
        // 계산한 최대 길이 출력
        System.out.println(maxLength);
    }
}