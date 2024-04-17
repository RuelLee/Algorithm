/*
 Author : Ruel
 Problem : Baekjoon 1253번 좋다
 Problem address : https://www.acmicpc.net/problem/1253
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1253_좋다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 그 중 다른 두 수의 합으로 표현되는 수를 좋다고 한다.
        // 좋은 수는 총 몇 개인가?
        //
        // 두 포인터 문제
        // 수열을 정렬한 뒤, 두 포인터를 활용하여 해당 수가 좋은 수인지 판별한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬
        Arrays.sort(nums);
        
        // 좋은 수의 개수
        int count = 0;
        // 모든 수에 대해 계산.
        for (int i = 0; i < nums.length; i++) {
            // 오른쪽 포인터
            int right = nums.length - 1;
            // 왼쪽 포인터
            for (int left = 0; left < nums.length; left++) {
                // 만약 i와 같다면 건너뛴다.
                if (left == i)
                    continue;

                // left와 right가 교차하지 않으면서
                // right가 i이거나, 두 포인터가 가르키는 수의 합이 i번째 수보다 크다면
                // right를 줄여나간다.
                while (left < right && (right == i || nums[left] + nums[right] > nums[i]))
                    right--;

                // 만약 두 포인터가 서로 다르며
                // 그 수의 합이 i번째 수와 같다면 count를 증가시키고
                // 다음 수로 넘어간다.
                if (left != right && nums[left] + nums[right] == nums[i]) {
                    count++;
                    break;
                }
            }
        }
        // 계산한 좋은 수의 개수를 출력한다.
        System.out.println(count);
    }
}