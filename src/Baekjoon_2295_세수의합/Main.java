/*
 Author : Ruel
 Problem : Baekjoon 2295번 세 수의 합
 Problem address : https://www.acmicpc.net/problem/2295
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2295_세수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수로 이루어진 집합 u가 주어진다.
        // 적당히 세 수를 골라 합했을 때, 그 합이 u에 속하는 경우 중 가장 큰 값을 구하라
        // 모두 같은 원소를 골라도 상관없다.
        //
        // 해쉬 맵 문제
        // ax+ ay + az = ak가 되어야한다.
        // 세 수를 고르고자 한다면, n이 최대 1000이므로, 1000^3 = 10억까지로 연산이 너무 많다
        // 따라서 ax + ay = ak - az로 보고
        // ax + ay를 해시 맵을 통해 표현해둔 뒤
        // ak - az값이 해쉬 맵에 있는가를 살펴보면 연산을 줄일 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // u의 크기 n
        int n = Integer.parseInt(br.readLine());

        // 집합 u
        int[] nums = new int[n];
        for (int i = 0; i < n; i++)
            nums[i] = Integer.parseInt(br.readLine());
        Arrays.sort(nums);
        
        // ax + ay
        HashSet<Long> sums = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++)
                sums.add((long) nums[i] + nums[j]);
        }

        int answer = -1;
        // ak - az
        for (int i = nums.length - 1; i >= 0 && answer == -1; i--) {
            for (int j = i; j >= 0; j--) {
                // 해당 값이 합에 존재한다면
                // ak 값을 기록 후 종료
                if (sums.contains((long) nums[i] - nums[j])) {
                    answer = nums[i];
                    break;
                }
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}