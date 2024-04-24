/*
 Author : Ruel
 Problem : Baekjoon 29726번 숏코딩의 왕 브실이
 Problem address : https://www.acmicpc.net/problem/29726
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_29726_숏코딩의왕브실이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열 A가 주어진다.
        // 길이가 L인 수열의 행복도는
        // (A2 - A1) + (A3 - A2) + ... + (AL - AL-1)로 계산된다.
        // m개의 수를 지울 수 있을 때
        // 최대 행복도를 구하라
        //
        // 그리디 문제
        // 조금 생각해보면 결국 행복도는
        // 마지막 수 - 처음 수로 계산된다.
        // 따라서 m개의 수를 처음이나 마지막에 연속한 수들을 지워서
        // 처음 시작하는 수 혹은 마지막 수 혹은 둘 다를 바꿔야한다.
        // 시작 지점은 0 ~ m 까지 살펴보며
        // 그 때 가능한 마지막 지점을 우선순위큐로 관리하며 최대 행복도를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 수열의 길이 n, 지울 수 있는 수 m개
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 길이가 n일 때
        // n - m번부터 n번까지의 수들을 담는다.
        // 값에 따라 내림차순
        PriorityQueue<Integer> maxFromRight = new PriorityQueue<>((o1, o2) -> Integer.compare(nums[o2], nums[o1]));
        for (int i = n - m - 1; i < nums.length; i++)
            maxFromRight.offer(i);

        // 최대 행복도
        int answer = Integer.MIN_VALUE;
        // 시작 지점은 0번부터 m번까지 차례대로 살펴보며
        for (int start = 0; start <= m; start++) {
            // 시작 지점과 마지막 지점을 고려하여
            // 지운 수의 개수가 m개 이하로 관리한다.
            while (!maxFromRight.isEmpty() &&
                    start + n - 1 - maxFromRight.peek() > m)
                maxFromRight.poll();
            // 이 때의 행복도를 계산하고, 최대 행복도와 비교한다.
            answer = Math.max(answer, nums[maxFromRight.peek()] - nums[start]);
        }
        // 구한 최대 행복도를 출력한다.
        System.out.println(answer);
    }
}