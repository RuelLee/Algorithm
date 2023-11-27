/*
 Author : Ruel
 Problem : Baekjoon 9024번 두 수의 합
 Problem address : https://www.acmicpc.net/problem/9024
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9024_두수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 서로 다른 n개의 정수가 주어진다.
        // 이 때 두 정수의 합이 k에 가장 가까운 조합이 몇 개인지 출력하라
        //
        // 두 포인터 문제
        // n이 최대 100만까지 주어지므로 모든 경우의 수를 세는 것은 안된다.
        // 따라서 두 포인터를 통해 구해주도록 한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // testCase개의 테스트 케이스
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // n개의 정수
            int n = Integer.parseInt(st.nextToken());
            // k에 가장 가까운 두 수의 합에 대한 조합 개수를 구한다.
            int k = Integer.parseInt(st.nextToken());

            int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // 정렬
            Arrays.sort(nums);
            
            // 포인터
            int i = 0;
            int j = nums.length - 1;
            // 조합의 개수
            int count = 0;
            // 최소 차이의 값
            int minDiff = Integer.MAX_VALUE;
            // 두 포인터가 교차하지 않는 시점까지만
            while (i < j) {
                // 현재 가르키는 두 수의 합
                int diff = Math.abs(k - (nums[i] + nums[j]));
                // 차이가 더 적어진다면
                if (diff < minDiff) {
                    // minDiff값 갱신.
                    minDiff = diff;
                    // 해당하는 조합 1개로 초기화
                    count = 1;
                } else if (diff == minDiff)     // 만약 같다면 count 증가
                    count++;
                
                // 두 수의 합이 k보다 크다면
                // j를 줄여 합을 줄이고
                if (nums[i] + nums[j] > k)
                    j--;
                // 그렇지 않다면 i를 증가시킨다.
                else
                    i++;
            }
            // 계산된 두 수의 합이 k에 가장 가까운 조합의 수를 기록한다.
            sb.append(count).append("\n");
        }
        // 전체 값 출력
        System.out.print(sb);
    }
}