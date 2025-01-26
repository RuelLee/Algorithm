/*
 Author : Ruel
 Problem : Baekjoon 3366번 수열 줄이기
 Problem address :https://www.acmicpc.net/problem/3366
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3366_수열줄이기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기 n의 수열이 주어진다.
        // reduce(i)를 하게 되면, ai와 ai+1이 하나의 수 max(ai, ai+1)가 된다고 한다.
        // 이 때의 비용은 마찬가지로 max(ai, ai+1)이라고 한다.
        // reduce 연산을 사용하여 수열의 길이를 1로 만들 때, 연산의 비용 합의 최솟값을 구하라
        //
        // 그리디 문제
        // 수열을 하나의 그래프로 볼 때
        // 그래프에서 아래로 뾰족한 지점이 생기는 경우를 지속적으로 reduce 연산을 해줘야한다.
        // 다시 말해 가운데의 수가 양 옆의 수보다 같거나 작을 때, 해당 연산을 시행하는 것이 유리하다.
        // 따라서 스택을 통해, 비오름차순으로 관리하다 스택의 최상단보다 큰 수가 나타나면
        // 양 옆의 수를 비교하여 더 적은쪽으로 reduce 연산을 해나가면 된다.
        // 그리고 마지막엔 스택에 남아있는 모든 수를 reduce 연산해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 수열
        int n = Integer.parseInt(br.readLine());

        long answer = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            // 이번 수
            int num = Integer.parseInt(br.readLine());
            // 스택이 비어있지 않고, 스택 최상단의 수보다 num이 더 큰 경우
            while (!stack.isEmpty() && stack.peek() < num) {
                // 스택 최상단보다 하단이 있는 경우
                if (stack.size() >= 2) {
                    // 최상단 수는 버림
                    stack.pop();
                    // 두번째 최상단의 수와 num을 비교하여
                    // 더 작은 수와 원래 최상단의 수를 reduce 연산하는 걸로 생각한다.
                    int left = stack.pop();
                    if (left <= num)
                        answer += left;
                    else
                        answer += num;
                    stack.push(left);
                } else {
                    // 스택의 크기가 하나인 경우엔 무조건 해당 수와 num을 reduce 연산한다.
                    stack.pop();
                    answer += num;
                }
            }

            // 위 반복문을 통해,
            // 비오름차순으로 유지되는 경우, num을 스택에 담는다.
            stack.push(num);
        }

        // 스택에 남아있는 모든 수에 대해 reduce 연산을 한다.
        stack.pop();
        while (!stack.isEmpty())
            answer += stack.pop();
        // 답 출력
        System.out.println(answer);
    }
}