/*
 Author : Ruel
 Problem : Baekjoon 14719번 빗물
 Problem address : https://www.acmicpc.net/problem/14719
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14719_빗물;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2차원 세계에 블록이 있고, 이에 비가 내린다.
        // 바닥은 막혀있다고 할 때 블록들 사이에 쌓이는 비의 양은?
        //
        // 주어지는 높이와 너비가 작아, 전부 체크하는 방법으로 시도하더라도 문제는 풀린다.
        // 하지만 다른 문제들을 풀어봤을때, 시간과 공간 복잡도를 줄이기 위해서는 스택을 사용하면 훨씬 효율적으로
        // 문제를 풀 수 있다는 점을 지금은 알고 있다.
        // 따라서 스택을 이용해 문제를 풀어보았다.
        // 내림차순 모노톤 스택을 이용한다.
        // 각 블럭을 순차적으로 살펴보면서
        // 스택에 최상단 블럭이 이번 블럭보다 크거나 없을 때까지
        // 스택 최상단과 이번 블럭 사이에 쌓인 물의 양을 계산한다.
        // 이 때 높이는 이전에 계산되었던 높이만큼은 빼준다.
        // 위 작업이 끝나면 스택이 비거나 이번 블럭보다 더 높은 블럭이 스택 최상단에 온다.
        // 스택이 만약 있다면(이번에는 스택의 블럭이 더 높은) 두 블럭 사이의 빗물도 계산해준다.
        // 그리고 스택에 이번 블럭을 넣어준다.
        // 위의 과정을 반복하면 내림차순 모노톤 스택이 유지되고 쌓인 빗물의 양을 선형적으로 계산이 가능하다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        // 블럭들의 길이.
        int[] blocks = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 내림차순 모노톤 스택.
        Stack<Integer> stack = new Stack<>();
        // 계산된 빗물의 양
        int stored = 0;
        // 계산이 끝난 빗물의 높이.
        int calculatedHeight = 0;
        for (int i = 0; i < blocks.length; i++) {
            // 스택이 비어있지 않고, i번째 블럭이 스택의 블럭의 높이보다 같거나 클 경우 지속해서.
            while (!stack.isEmpty() && blocks[stack.peek()] <= blocks[i]) {
                // 더 작은 블럭(스택의 블럭) 높이에서 계산된 높이를 빼준 만큼과, 두 블럭 사이의 거리를 곱해
                // 비의 양에 더해준다.
                stored += (blocks[stack.peek()] - calculatedHeight) * (i - stack.peek() - 1);
                // 계산된 높이를 스택 블럭의 높이로 바꿔준다.
                calculatedHeight = blocks[stack.pop()];
            }

            // 스택이 비어있지 않고, 이번 블럭이 스택 최상단 블럭보다 작다면
            if (!stack.isEmpty() && blocks[stack.peek()] > blocks[i]) {
                // 더 작은 블럭(이번 블럭)에서 계산된 높이를 빼주고, 두 블럭 사이의 거리만큼을 곱해 빗물의 양에 더해준다.
                stored += (blocks[i] - calculatedHeight) * (i - stack.peek() - 1);
                // 역시 계산된 높이를 이번 블럭의 높이로 바꿔준다.
                calculatedHeight = blocks[i];
            }
            // 스택에 이번 블럭을 넣어준다.
            stack.push(i);
        }
        // 계산된 블럭의 양을 출력한다.
        System.out.println(stored);
    }
}