/*
 Author : Ruel
 Problem : Jungol 4775번 고드름
 Problem address : https://jungol.co.kr/problem/4775
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4775_고드름;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] icicles;
    static int[] near = new int[]{-1, 1};

    public static void main(String[] args) throws IOException {
        // n개의 고드름이 달려있다.
        // 좌우에 자신보다 큰 고드름이 없을 경우, 매 시간마다 1센치씩 자란다고 한다.
        // 그러다 l센치가 되면 끊어져 0이 되고 더 이상 자라지 않는다고 한다.
        // 모든 고드름이 사라지는 시점은 언제인가
        //
        // 우선순위큐 문제
        // 우선순위큐를 통해 각 고드름이 끊어지는 시각을 계산하고, 해당 고드름이 끊어질 때
        // 좌우의 고드름이 이제 성장할 수 있는지를 체크하고 우선순위큐에 담아가며 모든 고드름이 끊어지는 시간을 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 고드름, 끊어지는 길이 l
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        // 고드름
        icicles = new int[n];
        for (int i = 0; i < n; i++)
            icicles[i] = Integer.parseInt(br.readLine());

        // 끊어지는 시간
        int[] endTimes = new int[n];
        Arrays.fill(endTimes, -1);

        // 초기 상태에서 자라는 고드름을 계산한다.
        // 우선순위큐로 끊어지는 시각이 이른 고드름부터 탐색한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> endTimes[o]));
        for (int i = 0; i < icicles.length; i++) {
            if (tallerThanNear(i)) {
                // 끊어지는 시각
                endTimes[i] = l - icicles[i];
                // 우선순위큐에 추가
                priorityQueue.offer(i);
            }
        }

        // 현재 시각과 끊어지는 시각이 제일 큰 값
        int time = 0;
        int max = 0;
        // 우선순위큐가 빌 때까지
        while (!priorityQueue.isEmpty()) {
            // 현재 고드름이 끊어지는 시점
            int current = priorityQueue.poll();
            // 시각 조정
            time = Math.max(time, endTimes[current]);
            // 끊어짐
            icicles[current] = 0;

            // 좌우를 살펴보고, 이제 성장하는 고드름이 있는지 확인
            for (int d = 0; d < near.length; d++) {
                int next = current + near[d];

                // 있다면
                if ((next >= 0 && next < n) && endTimes[next] == -1 && tallerThanNear(next)) {
                    // 끊어지는 시각 계산 후, 최댓값 갱신 여부 확인
                    endTimes[next] = time + (l - icicles[next]);
                    max = Math.max(max, endTimes[next]);
                    // 우선순위큐에 추가
                    priorityQueue.offer(next);
                }
            }
        }
        // 답 출력
        System.out.println(max);
    }

    // n번째 고드름이 주위 고드름보다 작지 않은지 확인
    static boolean tallerThanNear(int n) {
        return icicles[n] > 0 &&
                (n - 1 < 0 || icicles[n - 1] <= icicles[n]) &&
                (n + 1 >= icicles.length || icicles[n + 1] <= icicles[n]);
    }
}