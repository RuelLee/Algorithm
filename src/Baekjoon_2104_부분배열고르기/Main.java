/*
 Author : Ruel
 Problem : Baekjoon 2104번 부분배열 고르기
 Problem address : https://www.acmicpc.net/problem/2104
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2104_부분배열고르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열이 주어진다
        // 부분 수열의 값을 (각 원소의 합) * (부분 수열의 최소값)으로 정의한다
        // 수열에서 구할 수 있는 부분 수열들 중 최대 값을 갖는 부분 수열의 값은?
        // 히스토그램에서 가장 큰 직사각형(https://www.acmicpc.net/problem/6549)과 유사한 문제다
        // 누적합과 스택을 이용해서 스위핑으로 풀 수 있다.
        // 증가하는 모노톤 스택을 만들어서 풀도록하자
        // i번째 수가 스택의 최상단보다 작은 값이라면, 부분 수열의 값을 계산하도록 하자
        // 범위는 스택의 두번째 값의 다음 자리부터, i - 1까지 그리고 그 때의 해당 구간의 최소값은 스택의 최상단 값에 해당한다
        // 이유는 스택의 최상단 값보다 큰 값이 중간에 있었다면 stack 값 순서에서 위와 같은 계산으로 제거가 되었을 것이다.
        // 따라서 해당 구간의 가장 작은 값은 스택 최상단 값이 되며, 이를 통해 계산하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] nums = new int[n + 2];        // 처음과 끝에 0값으로 세팅해둠으로써, 0보다 큰 값에 대해서는 모두 계산할 수 있도록 초기값을 설정해두자.
        long[] psum = new long[n + 2];      // 누적합
        for (int i = 1; i < n + 1; i++)
            psum[i] = psum[i - 1] + (nums[i] = Integer.parseInt(st.nextToken()));

        Stack<Integer> stack = new Stack<>();
        long maxScore = 0;
        for (int i = 0; i < nums.length; i++) {
            // 스택이 비어있지않고, 스택의 최상단보다 작은 값을 만난다면 부분 수열의 값을 계산해준다
            // 범위는 stack의 두번째 값 + 1의 위치로부터 i - 1까지. 그 때의 범위 내의 최소값은 스택의 최상단 값.
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i])
                maxScore = Math.max(maxScore, nums[stack.pop()] * (psum[i - 1] - psum[stack.peek()]));
            stack.push(i);
        }
        System.out.println(maxScore);
    }
}