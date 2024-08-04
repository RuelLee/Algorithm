/*
 Author : Ruel
 Problem : Baekjoon 13146번 같은 수로 만들기 2
 Problem address : https://www.acmicpc.net/problem/13146
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13146_같은수로만들기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 10억 이하의 자연수가 주어진다.
        // add 연산을 통해 좌우로 인접한 같은 수의 그룹이 한번에 값이 1 증가한다.
        // 1 1 1 1 3 3 1 -> 2 2 2 2 3 3 1 -> 3 3 3 3 3 3 1
        // 앞에 1 그룹에 연산을 적용시키면 위와 같이 된다.
        // 모든 수를 같은 수로 만들고자할 때, 필요한 연산의 수는?
        //
        // 그리디, 스택 문제
        // 그룹을 한번에 값을 증가시킬 수 있다.
        // 따라서 스택에 값을 담아가면서
        // 스택 최상단보다 작은 값이 나온다면 담고,
        // 같은 값이 나온다면 한 그룹으로 계산되므로 건너뛰고
        // 큰 값이 나온다면 현재 최상단의 값(최소값)과 현재값과의 차이만큼의 연산이 필요하고
        // 스택에서 현재값보다 작은 값은 모두 제거한다.
        // 모든 수를 그렇게 살펴보고 마지막으로 스택에 남은 값들을 정리해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < array.length; i++)
            array[i] = Integer.parseInt(br.readLine());
        
        // 스택
        Stack<Integer> stack = new Stack<>();
        // 연산의 수
        long count = 0;
        for (int num : array) {
            // 비어있거나 최상단보다 작은 수가 나오면 담는다.
            if (stack.isEmpty() || stack.peek() > num)
                stack.push(num);
            else if (stack.peek() == num)       // 같은 수일 경우 건너뜀.
                continue;
            else {
                // 더 큰 값일 경우
                // 현재 최상단의 값을 현재 값만큼 증가시켜야하므로
                // 차이만큼 연산이 필요하다.
                count += num - stack.peek();
                // 값을 올리는 도중 스택에 담겨있는 num보다 같거나 작은 수들은
                // 같이 값이 올라 num이 되므로 모두 제거
                while (!stack.isEmpty() && stack.peek() <= num)
                    stack.pop();
                // num을 스택에 담는다.
                stack.push(num);
            }
        }

        // 스택에 남아있는 수들을 정리한다.
        // 현재 값을 꺼내, 최상단의 수와 비교하여 같은 값만큼 필요한 연산의 수를 더한다.
        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (!stack.isEmpty())
                count += stack.peek() - current;
        }
        // 답 출력
        System.out.println(count);
    }
}