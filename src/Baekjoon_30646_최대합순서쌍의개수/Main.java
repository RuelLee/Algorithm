/*
 Author : Ruel
 Problem : Baekjoon 30646번 최대 합 순서쌍의 개수
 Problem address : https://www.acmicpc.net/problem/30646
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30646_최대합순서쌍의개수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기가 n인 배열 a가 주어진다.
        // 1 <= i <= j <= n, ai == aj인 (i, j)를 골랐을 때
        // ai + ... + aj를 같은 수 순서쌍 (i, j)의 합이라고 하자.
        // 같은 수 순서쌍 (i, j)의 최대값과 같은 최대값을 가지는 순서쌍의 개수를 출력하라.
        //
        // 누적합 문제
        // 누적합을 통해 수를 살펴보며
        // 이전에 등장한 적이 있는 수라면, 가장 먼저 등장한 순서쌍의 idx부터 현재까지의 합을 누적합을 통해 구하고
        // 이 값이 최대값을 갱신하는지 확인한다.
        // 등장한 적이 없다면, 등장한 idx를 기록하고, 현재 수만으로 최대값이 갱신되는지 확인한다.
        // 두 경우 모두 최대값과 같은 값이라면 count를 증가시켜, 개수를 세어나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기 n의 배열
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] nums = new int[n + 1];
        for (int i = 1; i < nums.length; i++)
            nums[i] = Integer.parseInt(st.nextToken());
        
        // 각 수의 처음 등장한 idx
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        // 같은 수 순서쌍의 최대합
        long maxSum = 0;
        // 그 개수
        int count = 0;
        // 누적합
        long[] psums = new long[n + 1];
        for (int i = 1; i < nums.length; i++) {
            // 누적합
            psums[i] = psums[i - 1] + nums[i];
            // 현재 순서쌍의 합
            long currentSum;
            // 이전에 등장한 적 있는 수라면
            // 해당 idx부터 현재까지의 합으로 계산
            if (hashMap.containsKey(nums[i]))
                currentSum = psums[i] - psums[hashMap.get(nums[i]) - 1];
            else {
                // 그렇지 않다면 현재 수만으로 합을 구하고
                // 처음 등장한 idx로 기록한다.
                currentSum = nums[i];
                hashMap.put(nums[i], i);
            }

            // 최대값을 갱신했다면
            // 값 갱신 후, 카운터 1
            if (maxSum < currentSum) {
                maxSum = currentSum;
                count = 1;
            } else if (maxSum == currentSum)        // 만약 최대값과 값이 같다면 카운터 증가.
                count++;
        }
        // 결과값 출력
        System.out.println(maxSum + " " + count);
    }
}