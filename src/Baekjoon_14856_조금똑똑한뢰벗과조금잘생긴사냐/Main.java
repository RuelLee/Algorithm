/*
 Author : Ruel
 Problem : Baekjoon 14856번 조금 똑똑한 뢰벗과 조금 잘생긴 사냐
 Problem address : https://www.acmicpc.net/problem/14856
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14856_조금똑똑한뢰벗과조금잘생긴사냐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // n이 주어질 때, n을 비연속적인 피보나치 수의 합으로 표현하라
        // 그러한 방법이 여러개라면 항의 개수가 최대인 것을 출력한다.
        //
        // 그리디 문제
        // 젠코펠트(Zeckendorf) 정리라는 걸 활용해야한다.
        // 모든 자연수는 비연속적인 피보나치의 항으로 '유일'하게 표현된다고 한다.
        // 따라서 10^18이하의 피보나치 수들을 구하고
        // n보다 같거나 작은 가장 큰 피보나치수의 수부터, 해당 값을 n에서 빼나가며
        // n과 동일한 합을 같는 피보나치수들을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 피보나치 수
        // 미리 구해본 결과, 10^18 보다 같거나 작은 피보나치수의 개수는 86개
        long[] fibonacci = new long[86];
        // 첫항과 두번째 항
        fibonacci[0] = 1;
        fibonacci[1] = 2;
        // 나머지는 반복문으로 구함
        for (int i = 2; i < fibonacci.length; i++)
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        
        // 주어지는 n
        long n = Long.parseLong(br.readLine());
        // 가장 큰 피보나치 수
        int idx = 85;
        // 스택에 역순으로 담는다.
        Stack<Long> stack = new Stack<>();
        while (n > 0 && idx >= 0) {
            // 만약 n보다 피보나치 수가 더 크다면 건너뛴다.
            if (n < fibonacci[idx])
                idx--;
            else {
                // 그 외의 경우
                // 해당 수를 스택에 담고
                stack.push(fibonacci[idx]);
                // n에서 수만큼을 뺀 뒤
                n -= fibonacci[idx];
                // 비연속이므로, idx를 2 줄인다.
                idx -= 2;
            }
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 표현된 피보나치 수의 개수
        sb.append(stack.size()).append("\n");
        // 스택의 수들을 꺼내며 기록
        while (!stack.isEmpty())
            sb.append(stack.pop()).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}