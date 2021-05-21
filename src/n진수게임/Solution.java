package n진수게임;

import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        // 숫자를 n진법으로 표현하고,
        // 각각의 자리수에 대해 인원가 순서를 따져 해당 순번일 때의 값을 취하자

        int n = 2;
        int t = 4;
        int m = 2;
        int p = 1;

        char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder sb = new StringBuilder();

        int order = 0;
        int num = 1;
        if (p == 1)
            sb.append(0);

        while (true) {
            int target = num;   // 이번 숫자
            Stack<Character> stack = new Stack<>();
            while (target > 0) {    // n진법으로 표현.
                stack.add(numbers[target % n]);
                target /= n;
            }
            while (!stack.isEmpty()) {  //표현된 숫자의 자릿수 하나씩 모두 검사.
                char current = stack.pop();
                order++;
                if (order % m == p - 1)     // m명의 인원이 있을 때의 현재 순번이 (p - 1)일 때 자기 차례이므로 값을 취함.
                    sb.append(current);

                if (sb.length() == t)
                    break;
            }
            if (sb.length() == t)
                break;
            num++;
        }
        System.out.println(sb.toString());
    }
}