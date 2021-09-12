/*
 Author : Ruel
 Problem : Baekjoon 17298번 오큰수
 Problem address : https://www.acmicpc.net/problem/17298
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 오큰수;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // 수열에서 자신보다 크며, 오른쪽에 있는 수 중 가장 왼쪽의 수들을 각각 찾아내어 수열로 나타내는 문제.
        // 입력이 매우 많으므로, 스택의 활용을 생각해봐야한다.
        // 먼저 순서대로 지나가며, 스택이 비어있거나, 스택의 top보다 작은 값일 경우, 스택에 현재 값의 주소를 집어넣는다.
        // 그러다보면 현재 숫자가 stack의 top이 가르키는 숫자보다 큰 경우를 만날텐데
        // 이 때 스택 안의 값을 지속적으로 pop하며 top의 주소가 가르키는 값과 현재 숫자와 계속 비교하며, 현재 숫자가 더 클 경우, 스택의 숫자에 해당하는 위치에 현재 숫자값을 넣어준다
        // 그러다 현재 숫자보다 스택 top이 가르키는 값이 같거나 커졌을 경우, stack 비교를 종료하고, 현재 값의 주소를 stack에 넣는다.
        // 위 과정을 마지막 숫자까지 반복하고,
        // stack에 남아있는 숫자들은 자신보다 큰 오른쪽의 수가 경우이므로, stack 안에 모든 숫자들의 주소에 -1 값을 넣어주자
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = sc.nextInt();
        int[] answer = new int[n];

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < array.length; i++) {
            while (!stack.isEmpty() && array[stack.peek()] < array[i])  // stack의 top이 현재값보다 작은경우는 오큰수를 만난 경우.
                answer[stack.pop()] = array[i];     // 계속 비교하며, 해당 숫자의 주소에 현재 값을 넣어주자.

            if (stack.isEmpty() || array[stack.peek()] >= array[i])     // 스택이 비어있거나, stack의 top 값이 현재값보다 같거나 큰 경우
                stack.push(i);      // 스택에 값을 넣어 오큰수를 만날 때까지 기다리자.
        }
        while (!stack.isEmpty())        // 스택에 남아있는 수들은 오큰수가 없는 경우.
            answer[stack.pop()] = -1;

        StringBuilder sb = new StringBuilder();
        for (int an : answer)
            sb.append(an).append(" ");
        System.out.println(sb);
    }
}