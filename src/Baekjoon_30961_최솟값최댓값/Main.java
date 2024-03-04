/*
 Author : Ruel
 Problem : Baekjoon 30961번 최솟값, 최댓값
 Problem address : https://www.acmicpc.net/problem/30961
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30961_최솟값최댓값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열의 힘은 수열 내의 최솟값과 최댓값의 곱으로 정해진다.
        // 길이가 n인 수열이 주어질 때, 길이가 1 이상인 모든 부분수열의 힘을
        // xor한 값을 구하라
        //
        // 수학 문제
        // 먼저 최솟값과 최대값을 구하기 쉽게 정렬을 한다
        // i< j <= n인, i, j에 대해
        // i번째 수가 최솟값, j번째 수가 최대값이 되는 부분수열의 개수는
        // 두 수 사이의 개수만큼의 제곱인 2^(j - i - 1)이 된다
        // 그런데 xor을 짝수만큼 해주게 되면 원래 수로 돌아가므로
        // 두 수 사이에 다른 수가 들어있는 경우는 모두 xor할 경우 원래 수로 돌아간다.
        // 따라서 길이가 1인 부분 수열의 경우와
        // 인접한 수로 길이가 인 부분수열의 경우만 힘을 구해 xor 연산해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이가 n인 수열
        int n = Integer.parseInt(br.readLine());
        // 정렬
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(nums);

        long answer = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            // 길이가 1인 부분 수열의 힘을 xor
            answer ^= ((long) nums[i] * nums[i]);
            // 인접한 두 수로 만들어지는 부분 수열의 힘을 xor
            answer ^= ((long) nums[i] * nums[i + 1]);
        }
        // 마지막 수 하나로 만들어주는 부분 수열의 힘 xor
        answer ^= ((long) nums[n - 1] * nums[n - 1]);
        // 답안 출력
        System.out.println(answer);
    }
}