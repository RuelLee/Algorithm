/*
 Author : Ruel
 Problem : Baekjoon 1365번 꼬인 전깃줄
 Problem address : https://www.acmicpc.net/problem/1365
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1365_꼬인전깃줄;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] minValueByOrder;

    public static void main(String[] args) throws IOException {
        // n개의 전봇대와 해당 전봇대와 연결된 맞은 편 전봇대가 주어진다.
        // 전봇대들 사이의 전선들이 서로 교차하지 않도록, 전깃줄들을 잘라내고 싶다.
        // 이 때 잘라내야하는 전깃줄의 개수는?
        //
        // 최장 증가 수열 문제
        // 최장 증가 수열은 DP로도, 이분 탐색으로도 풀 수 있다.
        // 하지만, 이분 탐색이 효율이 좋기 때문에 이분 탐색을 쓰는 것이 유리하다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 연결된 맞은 편 전봇대 위치.
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 해당 전봇대와 연결되는 가장 적은 번호의 전봇대.
        minValueByOrder = new int[n];
        // 큰 값으로 초기화.
        Arrays.fill(minValueByOrder, Integer.MAX_VALUE);
        // 처음의 길이는 0.
        int length = 0;
        // 전봇대를 순서대로 살펴보며
        for (int i = 0; i < nums.length; i++) {
            // 이전에 살펴본 전봇대들과 교차하지 않는 전선으로
            // 자신이 최대 몇번째 위치에 올 수 있는지 확인한다.
            int order = findMaxOrder(nums[i], length);
            // order 순서에 해당하는 값들 중 자신과 연결된 맞은 편 전봇대의 위치가 가장 작은지 확인한다.
            minValueByOrder[order] = Math.min(minValueByOrder[order], nums[i]);
            // 이 때 교차하지 않는 전선의 개수가 가장 많은지 확인한다.
            length = Math.max(length, order + 1);
        }
        // 최종적으로 교차하지 않는 최대 전선의 개수를 찾았고,
        // 이를 제외한 나머지들을 잘라야한다.
        System.out.println(n - length);
    }

    // 이분 탐색으로 value에 해당하는 최대 순서 찾기.
    static int findMaxOrder(int value, int length) {
        // 0부터 legnth 까지
        int start = 0;
        int end = length;
        // 이분 탐색
        while (start < end) {
            int mid = (start + end) / 2;
            // mid에 해당하는 값보다 크다면, 다음 탐색 범위는
            // mid + 1 ~ end
            if (minValueByOrder[mid] < value)
                start = mid + 1;
            // 그렇지 않고, 작거나 같다면
            // 다음 탐색 범위는 start ~ mid
            else
                end = mid;
        }
        // 최종적으로 start가 가르키는 값이
        // value의 최대 순서.
        return start;
    }
}