/*
 Author : Ruel
 Problem : Baekjoon 2230번 수 고르기
 Problem address : https://www.acmicpc.net/problem/2230
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2230_수고르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 이 중 차이가 m 이상이지만 가장 적은 두 수를 구하여 그 차를 구하라.
        //
        // 두 포인터 문제
        // 수열을 정렬하여 두 포인터를 가지고서 차이가 m보다 같거나 크지만
        // 가장 적은 차를 갖는 두 수를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n개의 수, 최소 차 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 수열
        int[] nums = new int[n];
        for (int i = 0; i < nums.length; i++)
            nums[i] = Integer.parseInt(br.readLine());
        // 정렬
        Arrays.sort(nums);

        // 두 포인터
        int j = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            // 두 포인터가 같은 수를 가르키면 안되기 때문에
            // j는 항상 i보다 클 것이 보장되어야한다.
            j = Math.max(j, i + 1);

            // j는 수열을 벗어나지 않는 범위 내에서
            // nums[i]와 nums[j]의 차가 m보다 작다면 j를 하나씩 뒤로 민다.
            while (j < nums.length && nums[j] - nums[i] < m)
                j++;
            // 만약 j가 수열의 범위를 벗어난다면 반복문을 종료한다.
            if (j >= nums.length)
                break;

            // 종료되지 않았다면 두 포인터는 차이가 m보다 크거나 같은 수를 가르키고 있다.
            // 이 차가 최소값을 갱신하는지 확인.
            min = Math.min(min, nums[j] - nums[i]);
        }

        // 답안을 출력
        System.out.println(min);
    }
}