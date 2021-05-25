package 괄호회전하기;

import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        // 왼쪽으로 하나씩 문자를 밀어가며 해당 괄호들이 올바르게 열리고 닫히는지 확인한다
        String s = "[)(]";

        int count = 0;
        for (int i = 0; i < s.length(); i++) {  // 한 글자씩 왼쪽으로 문자를 회전시키며, isRight 메소드로 올바르게 열고 닫히는지 확인한다
            String turn = s.substring(i) + s.substring(0, i);
            if (isRight(turn))
                count++;
        }
        System.out.println(count);
    }

    static boolean isRight(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {    // stack 최상단에 있는 괄호의 쌍이 현재 괄호인지 확인한다. 아니라면 push.
            if (stack.isEmpty() || findPair(stack.peek()) != c)
                stack.push(c);
            else
                stack.pop();
        }
        return stack.isEmpty();     // stack 이 비어있다면 올바른 괄호문자열. 아니라면 틀린 괄호문자열.
    }

    static char findPair(char c) {  // 열린 괄호에 대한 닫힌 괄호를 리턴한다.
        return switch (c) {
            case '[' -> ']';
            case '{' -> '}';
            case '(' -> ')';
            default -> '-';
        };
    }
}