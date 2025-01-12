/*
 Author : Ruel
 Problem : Baekjoon 31858번 간단한 순열 문제
 Problem address : https://www.acmicpc.net/problem/31858
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31858_간단한순열문제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 순열 p가 주어진다.
        // 1부터 n까지의 모든 정수를 한 번씩 사용하여 만든 임의의 배열이다.
        // 다음을 만족하는 (i, j)의 쌍을 구하라
        // 1 <= i < j <= n
        // min(pi, pj) > max (0, pi+1, ... , pj-1)
        //
        // 스택 문제
        // 다시 말해 i, j번째 수 사이에 두 수보다 큰 수가 들어있어서는 안된다.
        // 따라서 단순 감소 스택을 사용하여 위 문제를 쉽게 풀 수 있다.
        // 스택에 있는 수를 i번째, 현재 살펴보는 수를 j번째로 생각하여 한 쌍의 수를 뽑을 수 있기 때문
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 수에 대한 순열이 주어진다.
        int n = Integer.parseInt(br.readLine());
        int[] p = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < p.length; i++)
            p[i] = Integer.parseInt(st.nextToken());
        
        // 단순 감소 스택으로 유지
        Stack<Integer> stack = new Stack<>();
        // 찾은 쌍의 개수
        int answer = 0;
        for (int i = 0; i < p.length; i++) {
            // 스택이 비어있지 않고, 스택의 최상단 수가 pi보다 작다면
            // 해당 수와 한 쌍을 만들 수 있으며, 단순 감소 스택 유지를 위해 하나씩 제거
            while (!stack.isEmpty() && p[stack.peek()] < p[i]) {
                stack.pop();
                answer++;
            }
            // 스택은 비어있거나, pi보다 큰 수가 들어있을텐데
            // 비어있지 않다면, 최상단의 해당 수와도 한 쌍을 이룰 수 있다.
            // 그 사이에 있던 수들 pi보다 작아 스택에서 사라졌기 때문.
            if (!stack.isEmpty())
                answer++;
            // i번째 수를 스택에 담는다.
            stack.push(i);
        }
        // 최종 계산한 쌍의 개수 출력
        System.out.println(answer);
    }
}