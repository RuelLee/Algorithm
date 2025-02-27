/*
 Author : Ruel
 Problem : Baekjoon 17162번 가희의 수열놀이 (Small)
 Problem address : https://www.acmicpc.net/problem/17162
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17162_가희의수열놀이_Small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 나머지 놀이에는 3가지 쿼리가 있다.
        // 1 num : 수열의 맨 뒤에 num을 추가한다.
        // 2 : 수열의 맨 뒤에 있는 수를 제거한다.
        // 3 : 수열의 마지막부터 최소 몇 개의 수를 선택해야, 나머지가 0, 1, ..., mod - 1인 수가 최소 하나씩 선택되는가?
        //
        // 스택 문제
        // 스택을 통해 각 나머지마다 등장하는 가장 마지막 수들을 관리한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // q개의 쿼리, 나누는 정수 mod
        int q = Integer.parseInt(st.nextToken());
        int mod = Integer.parseInt(st.nextToken());

        // 스택들을 통해 각 나머지 별 가장 뒤에 있는 수의 idx들을 관리한다.
        List<Stack<Integer>> stacks = new ArrayList<>();
        for (int i = 0; i < mod; i++)
            stacks.add(new Stack<>());
        
        // 수열의 길이
        int length = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            switch (Integer.parseInt(st.nextToken())) {
                // 1인 경우.
                // num을 수열 맨 마지막에 추가
                // num % mod에 해당하는 스택에 해당 인덱스 추가
                case 1 -> stacks.get(Integer.parseInt(st.nextToken()) % mod).push(++length);
                case 2 -> {
                    // 만약 길이가 0이라면 제거할 수가 없다.
                    if (length > 0) {
                        // 각 스택들을 살펴보며 스택 길이에 해당하는 인덱스 제거
                        for (Stack<Integer> stack : stacks) {
                            if (!stack.isEmpty() && stack.peek() == length) {
                                stack.pop();
                                break;
                            }
                        }
                        // 길이 감소
                        length--;
                    }
                }
                case 3 -> {
                    // 해당하는 경우가 가능한지 여부
                    boolean possible = true;
                    // 각 나머지 별 가장 마지막 인덱스들 중
                    // 가장 작은 값을 찾는다.
                    int min = Integer.MAX_VALUE;
                    for (Stack<Integer> stack : stacks) {
                        if (stack.isEmpty()) {
                            // 스택이 하나라도 비어있는 것이 있다면 불가능하므로
                            // possible을 false 처리 후, 반복문 종료
                            possible = false;
                            break;
                        } else      // 스택들의 최상단 값들 중 최소값을 찾는다.
                            min = Math.min(min, stack.peek());
                    }
                    
                    // 불가능한 경우
                    if (!possible)
                        sb.append(-1).append("\n");
                    else        // 가능한 경우 선택해야하는 수의 개수
                        sb.append(length - min + 1).append("\n");
                }
            }
        }
        // 답 출력
        System.out.print(sb);
    }
}