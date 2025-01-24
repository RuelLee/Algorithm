/*
 Author : Ruel
 Problem : Baekjoon 13884번 삭삽 정렬
 Problem address : https://www.acmicpc.net/problem/13884
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13884_삭삽정렬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 길이를 갖는 배열이 주어진다.
        // 해당 배열에서 하나의 수를 꺼내 맨 뒤로 보내는 과정을 반복하여 정렬하고자 한다.
        // 수를 꺼내는 행동을 최소화한다고 할 때, 그 횟수는?
        // p개의 테스트케이스가 주어진다.
        //
        // 스택
        // 으로 간단히 풀 수 있는 문제
        // 꺼내는 수는 뒤로 보내면서 그 순서에 따라 정렬을 할 수 있다.
        // 따라서 살펴봐야할 것은, 꺼내지 않는 수이다.
        // 꺼내지 않는 수는 스택을 통해 모노톤 스택으로 만든다.
        // 그 후, 꺼낸 수보다 작은 수가 스택에 있다면
        // 해당 수도 뒤로 보내야하므로 스택에서 제거한다.
        // 스택에 남은 수들은 꺼내지 않아도 되는 수이므로, n - 스택의 크기가 답이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // p개의 테스트 케이스
        int p = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < p; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 테스트 케이스 번호 k, 수열의 크기 n
            int k = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            
            // 스택
            Stack<Integer> stack = new Stack<>();
            // 꺼낸 수들 중 가장 작은 수
            int pullOutMin = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                // 한 줄에 최대 10개의 수가 입력되므로
                // i가 10의 배수가 될 때마다 입력을 받는다.
                if (i % 10 == 0)
                    st = new StringTokenizer(br.readLine());
                
                // 이번 수
                int num = Integer.parseInt(st.nextToken());
                // 스택이 비어있지 않으면서, num보다 큰 수가 스택에 있다면 제거한다.
                // 제거하며, pullOutMin의 값을 갱신하는지 확인
                while (!stack.isEmpty() && stack.peek() > num)
                    pullOutMin = Math.min(pullOutMin, stack.pop());
                // 스택에 추가
                stack.push(num);
            }
            // pullOutMin보다 큰 수는 스택에서 제거한다.
            while (!stack.isEmpty() && stack.peek() > pullOutMin)
                stack.pop();
            
            // 남은 스택의 사이즈 만큼은 수열에서 가만히 둬도 괜찮은 수이므로
            // 나머지 수들의 개수인 n - stack.size()가 답이 된다.
            sb.append(k).append(" ").append(n - stack.size()).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}