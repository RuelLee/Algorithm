/*
 Author : Ruel
 Problem : Baekjoon 16193번 차이를 최대로 2
 Problem address : https://www.acmicpc.net/problem/16193
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16193_차이를최대로2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 수들을 적절히 배치하여
        // 이웃한 수의 차 합이 최대가 되도록 만들고자한다.
        // 그 때의 차 합의 최대값은?
        //
        // 정렬, 그리디 문제
        // 수들을 정렬하고
        // 기존 수열의 좌우 값과
        // 새로운 수열의 좌우값을 비교하여 차이가 커지는 곳에 수를 배치하는 과정을 반복한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬
        Arrays.sort(nums);
        
        // 데크를 사용하여 새로운 수열을 만든다.
        Deque<Integer> deque = new LinkedList<>();
        // 가장 작은 수를 일단 배치
        deque.offerLast(nums[0]);
        // 기존 수열에서 아직 배치되지 않은 수들중
        // 가장 왼쪽과 가장 오른쪽 인덱스
        int left = 1;
        int right = nums.length - 1;
        // 수열의 차 합
        long sum = 0;
        // 기존 수열의 모든 수를 배치한다.
        while (left <= right) {
            // 기존 수열에서 (왼쪽, 오른쪽) -> 새로운 수열의 (왼쪽, 오른쪽)
            int lToL = Math.abs(deque.peekFirst() - nums[left]);
            int lToR = Math.abs(deque.peekLast() - nums[left]);
            int rToL = Math.abs(deque.peekFirst() - nums[right]);
            int rToR = Math.abs(deque.peekLast() - nums[right]);

            int max = Math.max(Math.max(lToL, lToR), Math.max(rToL, rToR));
            // 왼 -> 왼 배치가 최대값인 경우
            if (max == lToL) {
                sum += lToL;
                deque.offerFirst(nums[left++]);
            } else if (max == lToR) {       // 왼 -> 오 배치가 최대값인 경우
                sum += lToR;
                deque.offerLast(nums[left++]);
            } else if (max == rToL) {       // 오 -> 왼 배치가 최대값인 경우
                sum += rToL;
                deque.offerFirst(nums[right--]);
            } else {        // 오 -> 오 배치가 최대값인 경우
                sum += rToR;
                deque.offerLast(nums[right--]);
            }
        }
        // 결과값 출력
        System.out.println(sum);
    }
}