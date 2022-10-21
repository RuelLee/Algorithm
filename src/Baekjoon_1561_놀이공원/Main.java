/*
 Author : Ruel
 Problem : Baekjoon 1561번 놀이기구
 Problem address : https://www.acmicpc.net/problem/1561
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1561_놀이공원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 아이들이 m개의 놀이기구를 타려고 한다.
        // m개의 놀이기구는 각각 운행 시간이 있으며, 해당 운행 시간동안 탑승하고 내린다고 한다.
        // m개의 놀이기구는 맨 처음 모두 아무도 타고 있지 않다.
        // 아이들은 놀이기구가 비어있다면 번호가 빠른 순으로 탑승한다.
        // 마지막 아이가 타게되는 놀이기구를 출력하라.
        //
        // 이분 탐색 문제
        // 먼저 이분 탐색을 통해, 모든 아이들이 놀이기구를 타는 최소 시간을 구한다.
        // 그 후, 그 시간에 탑승하는 아이들을 일일이 따져보며 마지막 아이가 타는 놀이기구를 구한다.
        // 하지만 고려해야할 점이 0초에는 모든 놀이기구가 비어있어, m개의 놀이기구에 m명의 아이들이 모두 동시에 탑승할 수 있다는 점이다.
        // 두번째로 n이 20억까지 주어지므로 오버플로우에 주의해야한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 놀이기구들
        int[] rides = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int answer = -1;
        // 만약 놀이기구가 아이들보다 더 많다면, 모든 아이들이 순서대로 놀이기구에 탑승하게 된다.
        // n번째 아이는 n번째 놀이기구에 탑승하고 끝.
        if (n <= m)
            answer = n;

        // 그렇지 않다면 이분 탐색을 통해 모든 아이들이 놀이기구에 타는 시간을 구한다.
        else {
            long start = 0;
            long end = Long.MAX_VALUE;
            while (start < end) {
                // 중간 시간.
                long mid = (start + end) / 2;

                // mid 시간 동안 놀이기구에 탑승한 아이들을 구한다.
                long rode = 0;
                for (int ride : rides) {
                    rode += mid / ride;
                    // 이미 탑승 가능 인원이 아이들의 수보다 커졌다면 종료한다.
                    // 멈추지 않는다면 여기서 오버플로우가 날 수 있다 주의.
                    if (rode > n - m)
                        break;
                }

                // 0초에 m명의 아이들이 한번에 탑승하므로, 나머지 n - m 명의 아이들에 대해서만 계산한다.
                // 탑승한 인원이 n - m 명보다 많다면
                // end를 mid로 범위를 좁혀주고
                if (rode >= n - m)
                    end = mid;
                // 탑승 인원이 n - m보다 적다면
                // start를 mid + 1로 설정해 범위를 줄여준다.
                else
                    start = mid + 1;
            }

            // start 시간에 모든 아이들이 놀이기구에 탑승할 수 있다.
            // 따라서 start - 1 시간까지 탑승한 아이들의 수를 구하고,
            // start 시간에는 직접 순서대로 아이들을 놀이기구에 탑승시킨다.
            int remain = n - m;
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            for (int i = 0; i < rides.length; i++) {
                // start - 1 시간 동안 i번째 놀이기구에 탑승한 아이들의 수를
                // remain에서 빼준다.
                remain -= ((start - 1) / rides[i]);
                // 만약 i번째 놀이기구가 start 시간에 비어지게 된다면 우선순위큐에 삽입한다.
                if (start % rides[i] == 0)
                    priorityQueue.offer(i + 1);
            }

            // 남은 인원이 1명이 될 때까지 우선순위큐에 있는 놀이기구에 아이들을 태워준다.
            while (remain-- > 1)
                priorityQueue.poll();
            // 마지막 아이가 타는 놀이기구를 answer에 기록한다.
            answer = priorityQueue.poll();
        }
        // 정답 출력.
        System.out.println(answer);
    }
}