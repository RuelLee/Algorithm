/*
 Author : Ruel
 Problem : Baekjoon 13549번 숨바꼭질 3
 Problem address : https://www.acmicpc.net/problem/13549
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13549_숨바꼭질3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n의 위치에서 k 위치에 최소 시간으로 가고자 한다
        // 현재 위치에서 +1 , -1 위치로 1의 시간을 소비해서 이동하거나
        // 현재 위치 * 2의 위치로 순간이동할 수 있다고 한다
        // k 위치에 도달하는 최소 시간은?
        //
        // 너비 우선 탐색을 우선순위큐를 활용하여 푼다!
        // 우선순위큐를 해당 위치에 도달하는 최소 시간을 오름차순으로 정렬하여 활용하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 최대 1만의 위치까지로 주어지지만, 1만 초과의 위치로 나가서 돌아오는 경우가 더 짧은 소요시간을 가질 수도 있다.
        int[] minTime = new int[100_000 * 2];
        Arrays.fill(minTime, 100_001);
        // 시작 지점의 최소 시간은 0
        minTime[n] = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> minTime[o]));
        priorityQueue.offer(n);
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            // 우선순위큐이기 때문에 k에 도달하는 즉시 종료.
            if (current == k)
                break;

            // 순간 이동 위치가 범위를 벗어나지 않으며, 더 짧은 최소 시간을 갖고 있다면
            if (current * 2 < minTime.length &&
                    minTime[current * 2] > minTime[current]) {
                minTime[current * 2] = minTime[current];
                priorityQueue.offer(current * 2);
            }

            // -1 이동할 때.
            if (current - 1 >= 0 &&
                    minTime[current - 1] > minTime[current] + 1) {
                minTime[current - 1] = minTime[current] + 1;
                priorityQueue.offer(current - 1);
            }

            // +1 이동할 때
            if (current + 1 < minTime.length &&
                    minTime[current + 1] > minTime[current] + 1) {
                minTime[current + 1] = minTime[current] + 1;
                priorityQueue.offer(current + 1);
            }
        }
        System.out.println(minTime[k]);
    }
}