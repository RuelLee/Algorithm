/*
 Author : Ruel
 Problem : Baekjoon 32034번 동전 쌍 뒤집기
 Problem address : https://www.acmicpc.net/problem/32034
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32034_동전쌍뒤집기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 동전이 일렬로 주어진다.
        // 두 이웃한 동전이 같은 면이면, 뒤집을 수 있다고 한다.
        // 모든 동전을 앞면으로 뒤집고자 할 때, 그 횟수는?
        // 불가능하다면 -1을 출력한다.
        //
        // 그리디, 스택 문제
        // 뒷면과 뒷면 사이에 앞면의 동전 개수가 짝수라면 모든 동전을 뒤집을 수 있다.
        // 하지만 사이에 동전의 개수가 짝수인 뒷면 동전들이 앞아서부터 짝수 개수로 이웃해 있지 않을 수도 있다.
        // 앞뒤앞뒤앞앞뒤앞뒤 로 주어지면
        // HTHTHHTHT로 주어지면 첫 두번째 T 사이는 H가 한개라서 뒤집지 못하지만
        // 두번째와 세번째 T 사이는 H가 짝수개라서 HTHHHHHHT로 바꿀 수 있고, 
        // 이제 첫 T와 마지막 T의 사이의 H개수가 짝수개가 되어 모두 뒤집을 수 있다.
        // 따라서 스택을 통해 아직 짝을 이루지 못한 T의 번호를 갖고 있으며 계산하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // n개의 동전
            int n = Integer.parseInt(br.readLine());
            char[] coins = br.readLine().toCharArray();

            long answer = 0;
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < coins.length; i++) {
                // 뒷면인 동전을 만났을 때
                if (coins[i] == 'T') {
                    // 스택이 비어있거나, 이전 동전과의 거리 차가 홀수라면 스택에 담는다.
                    if (stack.isEmpty() || (i - stack.peek()) % 2 == 0)
                        stack.push(i);
                    else
                        answer += (i - stack.pop());
                    // 거리 차가 짝수라면, 가운데 동전들을 전부 뒷면으로 뒤집은 뒤
                    // 스택의 최상단 뒷면과 i의 뒷면까지 모두 앞면으로 바꾼다.
                    // 이 횟수가 (i - stack.peek() - 1) / 2 * 2 + 1번이 될텐데
                    // 이 값은 그냥 i stack.pop()과 같다.
                    
                }
            }
            // 스택이 비지 않았다면 모든 동전을 앞면으로 만드는 것이 불가능했던 경우
            // -1 기록
            // 그 외의 경우 answer 기록
            sb.append(stack.isEmpty() ? answer : -1).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}