/*
 Author : Ruel
 Problem : Baekjoon 16936번 나3곱2
 Problem address : https://www.acmicpc.net/problem/16936
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16936_나3곱2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 가지 연산이 존재한다.
        // 나3 : x를 3으로 나눈다. x는 3으로 나누어 떨어져야 한다.
        // 곱2 : x에 2를 곱한다.
        // 순서가 섞인 수열이 주어질 때
        // 가능한 순서 중 하나를 출력하라
        //
        // 정렬 로도 풀 수 있는 문제
        // 나3을 통해서는 소인수분해했을 때, 3의 제곱이 줄어만 간다.
        // 또한 곱2를 통해서는 2의 제곱이 늘어만 간다.
        // 항상 정답이 존재하는 경우에만 주어진다고 했으므로
        // 각 수를 소인수분해했을 때, 3이 가장 많은 순서대로
        // 3의 개수가 같다면, 2의 개수가 가장 적은 순서(= 작은 순서)대로 정렬을 하면
        // 해당 순서가 정답이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 수열의 크기
        int n = Integer.parseInt(br.readLine());

        long[] nums = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        // 수를 소인수분해했을 때, 3의 몇제곱인지 센다.
        int[] threes = new int[n];
        // 3의 제곱이 많은 순으로, 같다면 작은 순으로 정렬한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (threes[o1] == threes[o2])
                return Long.compare(nums[o1], nums[o2]);
            return Integer.compare(threes[o2], threes[o1]);
        });
        for (int i = 0; i < nums.length; i++) {
            threes[i] = calcThrees(nums[i]);
            priorityQueue.offer(i);
        }

        StringBuilder sb = new StringBuilder();
        // 우선순위큐에서 순서대로 꺼내며 답안을 작성한다.
        while (!priorityQueue.isEmpty())
            sb.append(nums[priorityQueue.poll()]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력.
        System.out.println(sb);
    }

    // 소인수분해했을 때, 3의 몇 제곱이 인수로 포함되는지 계산한다.
    static int calcThrees(long n) {
        int count = 0;
        while (n % 3 == 0) {
            n /= 3;
            count++;
        }
        return count;
    }
}