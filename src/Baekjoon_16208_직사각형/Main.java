/*
 Author : Ruel
 Problem : Baekjoon 16207번 직사각형
 Problem address : https://www.acmicpc.net/problem/16207
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16208_직사각형;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 막대가 주어진다.
        // 이 막대를 이용하여 만들 수 있는 직사각형 넓이의 최대 합은 얼마인가?
        // 여러개의 막대로 직사각형의 한 변을 만들 수는 없으며
        // 막대당 한번에 한하여 길이를 1 줄일 수 있다.
        //
        // 그리디 문제
        // 막대를 정렬하여 내림차순으로 살펴본다.
        // 먼저 같은 길이의 막대 1쌍이 직사각형을 만드는데 동시에 사용된다.
        // 따라서 살펴보며 자신과 같은 길이의 막대가 있다면 둘이 합쳐서 1쌍
        // 혹은 자신보다 1 작은 길이의 막대가 있다면 작은 길이로 1쌍임을 계산한다.
        // 그 후, 가장 긴 2쌍을 골라 직사각형을 만드는 작업을 반복한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 막대
        int n = Integer.parseInt(br.readLine());
        int[] sticks = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬
        Arrays.sort(sticks);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        // 모든 막대를 살펴본다.
        for (int i = sticks.length - 1; i > 0; ) {
            // 다음 막대와 길이가 같다면 같은 길이의 한 쌍
            if (sticks[i] == sticks[i - 1]) {
                priorityQueue.offer(sticks[i]);
                i -= 2;
            } else if (sticks[i] - 1 == sticks[i - 1]) {
                // 다음 막대가 이번 막대보다 길이가 1 짧다면, 다음 막대 길이의 한 쌍
                // 위 두 가지의 경우에는 다다음 막대로 건너뛴다.
                priorityQueue.offer(sticks[i - 1]);
                i -= 2;
            } else      // 다음 막대의 길이가 같거나 1 작지 않다면, 다음 막대로 순서를 넘긴다.
                i--;
        }

        // 두 쌍의 막대를 꺼내 직사각형을 만들 때의 넓이를 더한다.
        long sum = 0;
        while (priorityQueue.size() > 1)
            sum += (long) priorityQueue.poll() * priorityQueue.poll();

        // 최종 답안 출력
        System.out.println(sum);
    }
}