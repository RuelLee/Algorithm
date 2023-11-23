/*
 Author : Ruel
 Problem : Baekjoon 1863번 스카이라인 쉬운거
 Problem address : https://www.acmicpc.net/problem/1863
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1863_스카이라인쉬운거;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // 도시에서 태양이 질 때 비치는 건물들의 윤곽을 스카이라인이라고 한다.
        // 높이가 변하는 지점의 x, y가 주어질 때, 스카이라인만 보고서 건물이 최소 몇 채인지 구하라
        //
        // 스택 문제
        // 만약
        //  ** *
        // *****
        // 와 같이 주어진다면
        //  ** *   22 *   ** 3
        // 11111, *22**, ****3
        // 과 같이 최소 3개의 빌딩이 있음을 알 수 있다.
        // 따라서 스택을 통해
        // 높이가 증가한다면 계속 스택에 담아가며
        // 높이 감소한다면, 해당 높이보다 큰 건물들은 모두 끝났다고 보고 스택에서 제외한다.
        // 만약 같은 높이의 건물이 이미 스택에 담겨있다면 해당 건물이 연속하는 것으로 보고 카운트 하지 않는다.
        // 물론 높이가 0인 지점에 도달한다면 스택을 모두 비워준다.
        // 해당 연산을 반복하며 최소 건물의 수를 센다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 높이가 변화는 지점과 그 높이
        int n = Integer.parseInt(br.readLine());
        int[][] buildings = new int[n][];
        for (int i = 0; i < buildings.length; i++)
            buildings[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 스택을 통해 처리한다.
        Stack<Integer> stack = new Stack<>();
        // 현재까지 센 건물의 수
        int count = 0;
        for (int i = 0; i < buildings.length; i++) {
            // 스택이 비어있거나, 스택이 이번 높이의 변화보다 더 큰 값을 갖고 있다면
            // 모두 스택에서 꺼낸다.
            while (!stack.isEmpty() && stack.peek() > buildings[i][1])
                stack.pop();
            
            // 만약 건물의 높이가 0이 아니고
            if (buildings[i][1] != 0) {
                // 스택이 비어있거나, 스택보다 이번 높이가 더 높다면
                // 새로운 건물이 등장한 것
                if (stack.isEmpty() || stack.peek() < buildings[i][1]) {
                    // 스택에 넣고, count 증가
                    stack.push(buildings[i][1]);
                    count++;
                }
            }
        }
        
        // 센 건물의 수 출력
        System.out.println(count);
    }
}