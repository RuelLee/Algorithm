/*
 Author : Ruel
 Problem : Jungol 1141번 불쾌한 날
 Problem address : https://jungol.co.kr/problem/1141
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1141_불쾌한날;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 소가 일렬로 오른쪽을 보고 있다.
        // 각 소의 키가 주어진다.
        // 각 소는 자신과 자신보다 오른편에 있는 자신보다 키가 같거나 큰 소 사이에 있는
        // 자신보다 작은 소들을 볼 수 있다.
        // 각 소가 볼 수 있는 소들의 수를 합한 값은?
        //
        // 스택 문제
        // 단조 스택 문제.
        // 왼쪽에서 오른쪽으로 살펴가며 스택에서 자신보다 같거나 작은 값을 제거해나간다.
        // 그러면 자신보다 왼편에 있는 자신보다 큰 소들의 값만 스택에 담겨있으므로
        // 해당 스택의 크기를 누적시킨다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 소
        int n = Integer.parseInt(br.readLine());
        Stack<Integer> stack = new Stack<>();
        // 값 누적
        long sum = 0;
        for (int i = 0; i < n; i++) {
            // 현재 소의 높이
            int num = Integer.parseInt(br.readLine());
            // 스택에서 자신보다 같거나 낮은 소들을 제거한다.
            while (!stack.isEmpty() && stack.peek() <= num)
                stack.pop();
            // 스택의 크기를 누적시킨다.
            sum += stack.size();
            // 스택에 현재 값을 넣는다.
            stack.push(num);
        }
        // 답을 출력한다.
        System.out.println(sum);
    }
}