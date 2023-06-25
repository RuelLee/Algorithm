/*
 Author : Ruel
 Problem : Baekjoon 2170번 선 긋기
 Problem address : https://www.acmicpc.net/problem/2170
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2170_선긋기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 일직선 상에 n개의 선분이 주어진다.
        // 겹쳐진 선분은 구분할 수 없다고 할 때, 선분들의 길이 총 합은?
        //
        // 정렬, 스위핑 문제
        // 먼저 선분들을 시작점을 위치로 정렬한다.
        // 그리고 해당 선분의 시작점과 끝점을 기록해두고 다음 선분들을 살펴나간다
        // 다음 선분의 시작점이 이전 끝점보다 작다면 선분이 겹치는 경우이므로
        // 기록된 끝점과 이번 선분의 끝점 중 더 큰 값을 남겨둔다.
        // 다음 선분의 시작점이 이전 끝점보다 크다면, 선분이 겹치지 않는 경우이므로
        // 기록된 이전 시작점과 끝점을 토대로 길이를 구해서 더한 후
        // 새로운 선분의 시작점과 끝점을 기록해준다.
        // 위 과정을 모든 선분에 대해 반복한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 우선 순위큐를 통해 정렬해주었다.
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value[0]));
        for (int i = 0; i < n; i++)
            priorityQueue.offer(Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray());
        
        // 선분 길이의 합
        int lengthSum = 0;
        // 시작점과 끝점 초기값
        // 작은 같은 값으로 설정하여 길이가 0이다.
        int start = Integer.MIN_VALUE;
        int end = Integer.MIN_VALUE;
        while (!priorityQueue.isEmpty()) {
            // 현재 선분
            int[] current = priorityQueue.poll();
            // 새로운 선분의 시작점이 이전 끝점보다 큰 경우
            if (current[0] > end) {
                // 이전 선분까지의 길이 정리
                lengthSum += end - start;
                // 새로운 선분을 통해 새로운 시작점과 끝점 지정
                start = current[0];
                end = current[1];
            } else      // 선분이 겹치는 경우, 이전 끝 점과 새로운 끝 점을 비교해 더 큰 값을 남겨둔다.
                end = Math.max(end, current[1]);
        }
        // 마지막 선분의 길이 정리
        lengthSum += end - start;

        // 전체 선분들의 길이 합 출력
        System.out.println(lengthSum);
    }
}