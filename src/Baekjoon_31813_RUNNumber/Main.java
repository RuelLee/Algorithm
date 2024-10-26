/*
 Author : Ruel
 Problem : Baekjoon 31813번 RUN Number
 Problem address : https://www.acmicpc.net/problem/31813
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31813_RUNNumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Long> list;

    public static void main(String[] args) throws IOException {
        // 양의 정수 x의 모든 자릿수가 같은 숫자로 이루어져 있다면 RUN 수라고 한다.
        // n자리 수 k가 주어지면, k를 최대 n+1개의 RUN 수의 합으로 표현하라
        // 1 <= n <= 17
        //
        // 백트래킹 문제
        // 백트래킹을 통해, k에서 RUN 수를 최대 (n+1)회 빼 나가며, 0을 만든다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // RUN 수를 모두 구한다.
        list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            long num = i;
            for (int j = 0; j < 17; j++) {
                list.add(num);
                num *= 10;
                num += i;
            }
        }
        // 내림차순 정렬
        list.sort(Comparator.reverseOrder());
        
        // 테스트케이스의 수
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 주어지는 수 k와 k의 자릿수 n
            int n = Integer.parseInt(st.nextToken());
            long k = Long.parseLong(st.nextToken());

            // 스택에 합으로 더하는 RUN 수를 담는다.
            Stack<Long> stack = new Stack<>();
            findAnswer(n + 1, k, stack);
            
            // 스택의 담긴 수의 개수
            sb.append(stack.size()).append("\n");
            // 스택에 담긴 모든 수를 꺼내며 답안 작성
            while (!stack.isEmpty())
                sb.append(stack.pop()).append(" ");
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
    
    // 현재 n회 RUN 수를 더 뺄 수 있고,
    // 현재 수 num
    static boolean findAnswer(int n, long num, Stack<Long> stack) {
        // num이 0가 됐다면 답을 찾음.
        // true 반환
        if (num == 0)
            return true;
        // num이 0이 아닌데, n이 0이 됐다면
        // false 반환
        else if (n == 0)
            return false;
        // num보다 큰 RUN 수는 건너뛰며
        // 순서대로 num에서 빼나가며, 가능한지 살펴본다.
        for (long RN : list) {
            if (RN > num)
                continue;
            
            // RN을 통해, 1회 차감, num - RN
            stack.push(RN);
            // 만약 해당 경우로 num을 모두 차감할 수 있따면
            // 그대로 true 반환
            if (findAnswer(n - 1, num - RN, stack))
                return true;
            // 그렇지 않을 경우 stack에서 제거
            stack.pop();
        }
        // 모든 경우에 불가능하다면 false 반환
        return false;
    }
}