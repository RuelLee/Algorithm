/*
 Author : Ruel
 Problem : Jungol 8278번 나는 기말고사형 인간이야
 Problem address : https://jungol.co.kr/problem/8278
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8278_나는기말고사형인간이야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 시험까지 n * 24시간이 남아있다.
        // m개의 과목에 대해 시험을 치는데
        // 공부하지 않더라도 i번째 과목은 ai점을 맞는다.
        // 또 한 시간 공부한다면 각 과목의 점수가 bi점씩 오르며, 최대 100점까지 오를 수 있다.
        // 얻을 수 있는 점수의 최대 총합은?
        //
        // 그리디, 우선순위큐 문제
        // bi가 높은 순서대로 공부하여 최대 100점까지 채운다.
        // 100점까지 남은 점수가 bi보다 작은 경우, 해당 점수로 bi값을 변경하여 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 남은 시간 n * 24시간, m개의 과목
        int n = Integer.parseInt(st.nextToken()) * 24;
        int m = Integer.parseInt(st.nextToken());

        // 공부하지 않아도 받는 점수
        int[] a = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++)
            a[i] = Integer.parseInt(st.nextToken());

        // 공부할 경우, 시간당 점수 상승 폭
        int[] b = new int[m];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(b[o2], b[o1]));
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            b[i] = Integer.parseInt(st.nextToken());
            priorityQueue.offer(i);
        }

        // 점수 상승폭이 높은 과목을 우선 공부한다.
        while (!priorityQueue.isEmpty() && n > 0) {
            int idx = priorityQueue.poll();
            // 100점까지 남은 점수들 중 시간당 b[idx]만큼씩 오르는 만큼 일단 공부한다.
            int remain = (100 - a[idx]) / b[idx];
            int time = Math.min(n, remain);

            a[idx] += b[idx] * time;
            n -= time;
            // n이 아직 남은 경우
            // 이는 남은 점수가 b[idx]보다 작은 경우
            if (n > 0) {
                // b[idx]를 남은 점수로 변경
                b[idx] = (100 - a[idx]);
                // 남은 점수가 0점이 아닌 경우(현재 100점이 아닌 경우)
                // 다시 우선순위큐에 담는다.
                if (b[idx] != 0)
                    priorityQueue.offer(idx);
            }
        }

        // 점수 총합을 구해
        int sum = 0;
        for (int i = 0; i < m; i++)
            sum += a[i];
        // 출력
        System.out.println(sum);
    }
}