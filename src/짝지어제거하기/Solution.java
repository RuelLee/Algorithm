package 짝지어제거하기;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        // 인근 문자가 서로 같다면 같은 문자 2개를 지운다.
        // 순서대로 문자를 보며, 일치 하지 않는다면, stack에 담는다.
        // 다음부터는 순서대로 문자와 stack의 다음 문자, 그리고 다음 순서의 문자를 비교하며 같을 경우 제거.
        // 마지막에 stack에 문자가 남아있다면 짝지어 제거할 수 없는 문자열.

        String s = "cdcd";

        Queue<Character> queue = new LinkedList<>();

        for (int i = 0; i < s.length(); i++)
            queue.add(s.charAt(i));

        Stack<Character> stack = new Stack<>();

        while (!queue.isEmpty()) {
            char c = queue.poll();
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
                continue;
            }

            if (!queue.isEmpty() && c == queue.peek()) {
                queue.poll();
                continue;
            }

            stack.push(c);
        }

        System.out.println(stack.size() == 0 ? 1 : 0);
    }
}