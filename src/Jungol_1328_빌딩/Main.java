/*
 Author : Ruel
 Problem : Jungol 1328번 빌딩
 Problem address : https://jungol.co.kr/problem/1328
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1328_빌딩;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 빌딩의 높이가 주어진다.
        // 각 빌딩의 오른쪽에 있는 빌딩 중 자신의 높이보다 더 크고 가까운 빌딩의 번호를 출력하라
        //
        // 스택 문제
        // 오른쪽에서부터, 단조감소 스택으로 채워나가며
        // 현재 맞닥뜨린 건물보다 높이가 같거나 낮은 건물들을 제외하고, 높지만 가장 가까운 건물의 번호를 기록한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 건물
        int n = Integer.parseInt(br.readLine().trim());

        // 각 건물들의 높이
        int[] buildings = new int[n];
        for (int i = 0; i < n; i++)
            buildings[i] = Integer.parseInt(br.readLine().trim());

        // 스택
        Stack<Integer> stack = new Stack<>();
        int[] answer = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            // 현재 빌딩보다 높이가 같거나 낮다면 제거
            while (!stack.isEmpty() && buildings[i] >= buildings[stack.peek()])
                stack.pop();

            // 비어있지 않다면 해당 건물의 번호 기록
            if (!stack.isEmpty())
                answer[i] = stack.peek() + 1;
            // 스택에 현재 건물 추가
            stack.push(i);
        }

        // 답 작성
        StringBuilder sb = new StringBuilder();
        for (int a : answer)
            sb.append(a).append("\n");
        // 출력
        System.out.print(sb);
    }
}