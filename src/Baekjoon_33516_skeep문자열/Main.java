/*
 Author : Ruel
 Problem : Baekjoon 33516번 skeep 문자열
 Problem address : https://www.acmicpc.net/problem/33516
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_33516_skeep문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    static char[] order = {'s', 'k', 'e', 'e', 'p'};

    public static void main(String[] args) throws IOException {
        // 길이 n의 문자열이 주어진다.
        // 문자열에서 skeep 이라는 연속한 부분 문자열을 찾는다면, s를 제외한 소문자 알파벳으로 변경할 수 있다.
        // 가능한 위 작업을 많이한다고 할 때 그 횟수는?
        //
        // stack 문제
        // stack을 토앻 skeep을 찾아가며, 해당 skeep이 완성된 시점이 다른 skeep을 완성시키던 도중이었다면
        // 다른 skeep의 진척도를 하나 증가시키는 방법으로 계산할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 문자열
        int n = Integer.parseInt(br.readLine());
        char[] input = br.readLine().toCharArray();
        
        // 스택
        Stack<Integer> stack = new Stack<>();
        // 바꾼 횟수
        int count = 0;
        for (int i = 0; i < input.length; i++) {
            // s가 들어왔다면 0으로 시작
            if (input[i] == order[0])
                stack.push(0);
            // stack이 비어있지 않고, skeep의 연속성을 갖는 다음 문자가 들어왔다면
            // 해당 문자열 완성도를 1 증가
            else if (!stack.isEmpty() && input[i] == order[stack.peek() + 1])
                stack.push(stack.pop() + 1);
            else    // 중간에 다른 글자가 들어온 경우는 완성시키는 것이 불가능해지므로 stack을 초기화
                stack.clear();
            
            // 0 ~ 4까지 진척도가 증가해, skeep이 완성됐다면
            while (!stack.isEmpty() && stack.peek() == 4) {
                // 해당 진척도 제거
                stack.pop();
                // 횟수 증가
                count++;
                // 혹시 위 값을 제거했음에도 stack이 비어있지 않다면
                // 이전에 진행하던 문자열이 있는 경우이므로
                // 해당 문자열의 진척도를 하나 증가
                if (!stack.isEmpty())
                    stack.push(stack.pop() + 1);
            }
        }
        // 답 출력
        System.out.println(count);
    }
}