/*
 Author : Ruel
 Problem : Jungol 5342번 프리소비 (Cow Frisbee)
 Problem address : https://jungol.co.kr/problem/5342
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5342_프리소비_CowFrisbee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 소가 주어지고 각각의 높이가 주어진다.
        // 두 소 사이에 더 높은 소가 없다면 두 소가 서로 프리소비를 할 수 있다.
        // i번째 소와 j번째 소 사이의 거리는 j - i + 1이라고 할 때
        // 프리소비를 할 수 있는 모든 쌍의 거리 합을 구하라
        //
        // 스택 문제
        // 단조 스택 문제
        // 내림차순 단조 스택으로 관리해나가며, 프리소비를 할 수 있는 쌍을 구한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 소
        int n = Integer.parseInt(br.readLine());
        // 높이
        int[] heights = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            heights[i] = Integer.parseInt(st.nextToken());

        // 단조스택으로 관리
        Stack<Integer> stack = new Stack<>();
        long ans = 0;
        for (int i = 0; i < n; i++) {
            // 만약 나보다 더 작은 소가 먼저 나왔다면, 꺼내며, 해당 소와 거리 합산
            while (!stack.isEmpty() && heights[stack.peek()] <= heights[i])
                ans += i - stack.pop() + 1;

            // 스택이 비어있지 않다면, 최상단 소와 프리소비 거리 합산
            if (!stack.isEmpty())
                ans += i - stack.peek() + 1;
            // i번째 소 추가
            stack.push(i);
        }
        // 답 출력
        System.out.println(ans);
    }
}