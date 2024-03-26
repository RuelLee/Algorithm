/*
 Author : Ruel
 Problem : Baekjoon 1038번 감소하는 수
 Problem address : https://www.acmicpc.net/problem/1038
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1038_감소하는수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // 음이 아닌 정수 x의 자릿수가 가장 큰 자릿수부터 작은 자릿수까지 감소한다면
        // 그 수를 감소하는 수라고 한다.
        // 321, 950은 감소하는 수고, 322와 958은 아니다.
        // 0은 0번째, 1은 1번째 감소하는 수일 때, n번째 감소하는 수를 구하라
        // n번째 감소하는 수가 없다면 -1을 출력한다.
        //
        // 브루트 포스 문제
        // 감소하는 수가 생각보다 적다.
        // 9876543210이 가장 큰 감소하는 수
        // 따라서 모든 감소하는 수를 직접 구하더라도 시간이 그리 오래 걸리지 않는다.
        // 따라서 감소하는 수를 직접 모두 구하고 정렬하여, n번째 수를 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 감소하는 수를 리스트에 저장
        List<Long> nums = new ArrayList<>();
        // 맨 앞자리가 i부터 시작하는 감소하는 수를 찾는다.
        for (int i = 0; i < 10; i++)
            bruteForce(i, nums);
        
        // 감소하는 수 정렬
        Collections.sort(nums);
        // n이 감소하는 수의 전체 개수보다 적은 경우에만
        // 감소하는 수를 찾아 출력할 수 있다. 그 외의 경우는 -1 출력.
        System.out.println(n < nums.size() ? nums.get(n) : -1);
    }

    // 브루트 포스로 감소하는 수를 찾는다.
    static void bruteForce(long n, List<Long> nums) {
        // n을 감소하는 수에 추가
        nums.add(n);
        // 1의 자리가 0인 경우에는 더 이상 진행할 수 없으므로 종료
        if (n % 10 == 0)
            return;

        // n의 1의 자리보다 작은 수를 1의 자리에 추가한다.
        for (int i = 0; i < n % 10; i++)
            bruteForce(n * 10 + i, nums);
    }
}