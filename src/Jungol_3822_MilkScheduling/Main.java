/*
 Author : Ruel
 Problem : Jungol 3822번 Milk Scheduling
 Problem address : https://jungol.co.kr/problem/3822
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3822_MilkScheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 소와 m개의 선후 관계가 주어진다.
        // 각 소에게서 우유를 짜는데 걸리는 시간이 주어지고
        // 선후 관계에 따라 앞의 소가 먼저 마친 경우에만 뒤의 소에게서 우유를 짤 수 있다.
        // 충분한 인원이 있어 동시에 여러 마리의 소에게서 우유를 짤 수 있다할 때
        // 모든 소에게서 우유를 짜는데 걸리는 시간은?
        //
        // 위상 정렬 문제
        // 작업에 대한 선후관계가 주어지기 때문에
        // 위상 정렬을 통해 앞의 작업들이 모두 이루어진 경우에만 뒤의 작업이 일어나야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 소, m개의 관계
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 소에게서 우유를 짜는데 걸리는 시간
        int[] times = new int[n + 1];
        for (int i = 1; i < n + 1; i++)
            times[i] = Integer.parseInt(br.readLine());

        // 선후 관계
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        // 진입 차수
        int[] indegrees = new int[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a번째 소 뒤에 b번째 소가 와야한다
            connections.get(a).add(b);
            // b번째 소의 진입 차수 증가
            indegrees[b]++;
        }

        // 각 소의 젖을 짜기 시작하는 시각
        int[] minTimes = new int[n + 1];
        Arrays.fill(minTimes, Integer.MIN_VALUE);
        // 우선순위큐로 시간 순으로 처리
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 1; i < n + 1; i++) {
            // 처음부터 진입 차수가 0인 소들을 우선순위큐에 담음
            if (indegrees[i] == 0) {
                minTimes[i] = 0;
                priorityQueue.offer(i);
            }
        }

        int answer = 0;
        while (!priorityQueue.isEmpty()) {
            // 현재 소
            int current = priorityQueue.poll();
            // 현재 소가 우유 짜기를 마치는 시각
            answer = Math.max(answer, minTimes[current] + times[current]);

            // 다음 소
            for (int next : connections.get(current)) {
                // 다음 소의 작업 시간을 현재 소가 끝마친 이후로 미루고
                minTimes[next] = Math.max(minTimes[next], minTimes[current] + times[current]);
                // 진입 차수가 0이 된 경우 우선순위큐에 담음
                if (--indegrees[next] == 0)
                    priorityQueue.offer(next);
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}