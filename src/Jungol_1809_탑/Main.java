/*
 Author : Ruel
 Problem : Jungol 1809번 탑
 Problem address : https://jungol.co.kr/problem/1809
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1809_탑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 탑이 일렬로 늘어서있다.
        // 각 탑의 높이가 주어진다.
        // 각 탑의 꼭대기에는 레이저 송신기가 부착되어있고, 왼쪽으로 쏜다.
        // 레이저 신호는 직진하다, 막히는 건물 하나에서만 송신이 가능하다.
        // 각 건물마다 자신이 쏘는 레이저 신호가 몇번째 건물에서 수신가능한지 출력하라
        // 그러한 건물이 없다면 0을 출력한다.
        //
        // 스택 문제
        // 왼쪽에서부터 스택에 건물들을 담아간다.
        // 스택에 담긴 건물보다 같거나 높은 건물을 만나면, 스택에 담긴 건물의 신호는 새로 만난 탑에서 수신하게 된다.
        // 더 낮은 건물을 만나면, 계속 스택에 담아가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 건물
        int n = Integer.parseInt(br.readLine());
        // 높이
        int[] heights = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            heights[i] = Integer.parseInt(st.nextToken());

        // 스택
        Stack<Integer> stack = new Stack<>();
        int[] answer = new int[n];
        // 오른쪽에서 왼쪽으로 가며
        for (int i = heights.length - 1; i >= 0; i--) {
            // i번째 탑이 스택에 담긴 탑들보다 같거나 높은 높이인 경우
            // 해당 스택의 탑들의 신호는 i번 건물에서 수신
            while (!stack.isEmpty() && heights[i] >= heights[stack.peek()])
                answer[stack.pop()] = i + 1;
            // 스택에 i번 탑 추가
            stack.push(i);
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        sb.append(answer[0]);
        for (int i = 1; i < n; i++)
            sb.append(" ").append(answer[i]);
        // 답 출력
        System.out.println(sb);
    }
}