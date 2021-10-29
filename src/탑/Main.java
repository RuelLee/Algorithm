/*
 Author : Ruel
 Problem : Baekjoon 2493번 탑
 Problem address : https://www.acmicpc.net/problem/2493
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 탑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // (히스토그램에서 가장 큰 직사각형) https://www.acmicpc.net/problem/6549
        // 문제와 같이 모노톤 스택을 활용한 문제
        // 첫번째 타워부터 탐색하며, 스택에 담긴 타워가 현재 타워보다 작다면 pop해줘서,
        // 점차 값이 낮아지도록 스택에 담아준다.
        // 이렇게 담으면, 대상 타워보다 왼쪽에서 크거나 같은 가장 가까운 타워가 스택의 최상단에 담겨 있게 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] tower = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            tower[i] = Integer.parseInt(st.nextToken());

        Stack<Integer> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && tower[stack.peek()] < tower[i])  // 자신보다 작은 타워들은 스택에서 제해준다.
                stack.pop();

            if (stack.isEmpty())        // 스택이 비어있다면 왼쪽에 자신보다 큰 타워는 없는 것. 따라서 0
                sb.append(0).append(" ");
            else        // 아니라면 자신보다 큰 타워가 스택의 최상단에 있다. 해당 값을 담자.
                sb.append(stack.peek() + 1).append(" ");
            stack.push(i);      // 그리고 현재 타워를 담아준다.
        }
        System.out.println(sb);
    }
}