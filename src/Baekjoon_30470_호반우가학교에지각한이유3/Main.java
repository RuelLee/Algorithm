/*
 Author : Ruel
 Problem : Baekjoon 30470번 호반우가 학교에 지각한 이유 3
 Problem address : https://www.acmicpc.net/problem/30470
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30470_호반우가학교에지각한이유3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 통나무를 나란히 놓아가며 계단을 만들고자한다.
        // 통나무는 이전에 놓은 통나무보다 긴 통나무만 놓는다.
        // 마물들이 위력 m인 마법을 사용한다면 가장 긴 통나무의 길이 k를 기준으로
        // 길이가 max(k-m, 0) 이상인 통나무들의 길이를 max(k-m, 0)으로 만들어버린다.
        // n개의 쿼리가 주어질 때
        // 통나무들 길이의 합은?
        //
        // 스택 문제
        // 통나무를 항상 이전에 놓았던 것보다 더 긴 것만 놓게 되므로
        // 마법을 맞더라도, 같아지는 경우는 생겨도 더 작은 경우는 생기지 않는다.
        // 따라서 결국 통나무는 단조증가 형태를 띄게 된다.
        // 따라서 스택을 통해 현재 최대 통나무의 길이를 계산하고,
        // 남은 통나무의 길이는 역으로 스택에서 꺼내가며 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 쿼리
        int n = Integer.parseInt(br.readLine());
        
        // 스택
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 1번 쿼리라면 통나무 추가
            if (Integer.parseInt(st.nextToken()) == 1)
                stack.push(Integer.parseInt(st.nextToken()));
            // 2번 쿼리라면 현재 최대 통나무의 길이(최상단)에서 m만큼 감소
            else if (!stack.isEmpty())
                stack.push(Math.max(0, stack.pop() - Integer.parseInt(st.nextToken())));

        }
        
        // 현재 최대 통나무의 길이
        int currentMax = Integer.MAX_VALUE;
        // 통나무 길이 합
        long sum = 0;
        // 스택이 비지않고, 최대 통나무의 길이가 0보다 클 때만
        while (!stack.isEmpty() && currentMax > 0) {
            // 현재 최대 길이를 스택에서 뽑은 통나무와 비교하여 더 작은 값으로 수정
            currentMax = Math.min(currentMax, stack.pop());
            // 통나무 길이 합에 누적
            sum += currentMax;
        }
        // 모든 통나무 길이 합 출력
        System.out.println(sum);
    }
}