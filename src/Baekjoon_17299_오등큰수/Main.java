/*
 Author : Ruel
 Problem : Baekjoon 17299번 오등큰수
 Problem address : https://www.acmicpc.net/problem/17299
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17299_오등큰수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기 n인 수열이 주어진다
        // 오등큰수는 수열에서 자신보다 많이 등장한, 자신보다 오른쪽에 있는 수들 중 가장 왼쪽에 있는 수이다.
        // 수열의 각 수에 대한 모든 오등큰수를 공백으로 구분하여 출력한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 수에 대한 등장 횟수를 센다.
        int[] appearances = new int[1_000_001];
        for (int num : nums)
            appearances[num]++;

        // 오른쪽에서부터 등장 횟수에 따라 내림차순 모노톤 스택을 만든다.
        Stack<Integer> right = new Stack<>();
        // 거꾸로 답안을 채워가니, 스택으로 답을 저장해 나중에 역순으로 출력한다.
        Stack<Integer> answer = new Stack<>();

        // 역순으로 살펴본다.
        for (int i = nums.length - 1; i >= 0; i--) {
            // right 스택이 비어있지 않고, 스택에 최상위 값이 i번째 수보다 등장 횟수가 작다면, right 스택에서 꺼낸다.
            while (!right.isEmpty() && appearances[nums[i]] >= appearances[right.peek()])
                right.pop();
            // 만약 스택이 비어있다면 오등큰수가 없는 경우. -1.
            if (right.isEmpty())
                answer.push(-1);
            // 그렇지 않다면 right 가장 위에 있는 수가 i번째 수에 대한 오등큰수.
            else
                answer.push(right.peek());
            // 그리고 i번째 수를 right 스택에 담는다.
            right.push(nums[i]);
        }

        // 최종적으로 answer에 담긴 수를 꺼내가며 답안을 만든다.
        StringBuilder sb = new StringBuilder();
        while (!answer.isEmpty())
            sb.append(answer.pop()).append(" ");
        // 답안 출력.
        System.out.println(sb);
    }
}