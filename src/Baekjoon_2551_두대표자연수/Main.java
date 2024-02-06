/*
 Author : Ruel
 Problem : Baekjoon 2551번 두 대표 자연수
 Problem address : https://www.acmicpc.net/problem/2551
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2551_두대표자연수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 자연수들이 주어진다.
        // 이 자연수들의 대표 자연수를 구하는 방법은 두가지이다.
        // 1. 차의 합이 가장 적은 자연수
        // 2. 차의 제곱 합이 가장 적은 자연수
        // 두 경우의 대표 자연수를 모두 구해 출력하라
        //
        // 브루트포스 문제
        // n이 최대 500만개가 주어지지만 그 범위는 1만까지이다.
        // 따라서 각 자연수들의 수를 세서 각각 계산하기보단 개수로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 자연수
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 자연수들의 개수를 센다.
        int[] nums = new int[10_001];
        for (int i = 0; i < n; i++)
            nums[Integer.parseInt(st.nextToken())]++;
        
        // 차의 합
        long firstValue = Long.MAX_VALUE;
        // 차 제곱의 합
        long secondValue = Long.MAX_VALUE;
        // 첫번째 대표 자연수
        int firstAnswer = 0;
        // 두번째 대표 자연수
        int secondAnswer = 0;
        for (int i = 1; i <= 10_000; i++) {
            // 1 ~ 1만까지 모든 수에 대해 대표 자연수가 될 수 있는지 계산한다.
            // i를 대표 자연수라 생각했을 때, 각각의 합
            long firstSum = 0;
            long secondSum = 0;
            for (int j = 1; j < nums.length; j++) {
                // 해당하는 수가 없다면 건너뛴다.
                if (nums[j] == 0)
                    continue;
                // 차의 합
                firstSum += Math.abs(j - i) * (long) nums[j];
                // 차 제곱의 합
                secondSum += Math.pow(Math.abs(j - i), 2) * (long) nums[j];
            }
            // 차의 합이 최소로 갱신된다면 값 갱신
            if (firstValue > firstSum) {
                firstValue = firstSum;
                firstAnswer = i;
            }
            // 차 제곱의 합이 최소로 갱신된다면 값 갱신
            if (secondValue > secondSum) {
                secondValue = secondSum;
                secondAnswer = i;
            }
        }
        
        // 답안 출력
        System.out.println(firstAnswer + " " + secondAnswer);
    }
}