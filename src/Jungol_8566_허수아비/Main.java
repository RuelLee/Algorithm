/*
 Author : Ruel
 Problem : Jungol 8566번 허수아비
 Problem address : https://jungol.co.kr/problem/8566
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8566_허수아비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 위치와 각 자리에 세울 수 있는 허수아비의 체력이 주어진다.
        // 0에서부터 힘이 p인 화살을 쏜다.
        // 화살은 자신의 힘보다 쎈 허수아비를 만나면 멈추고,
        // 자신보다 약한 허수아비를 만나면 허수아비의 체력만큼 힘이 깎이고 계속 날아간다.
        // 각 위치까지 최소한의 허수아비를 세워 화살을 멈추고자할 때, 각 위치까지 필요한 수아비의 수는?
        //
        // 우선순위 큐 문제
        // 우선순위큐로 해당 위치까지 합을 구하고, 우선순위큐로 오름차순으로 살펴보며, 제거할 수 있을만큼 허수아비를 제거한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 위치, 화살의 힘 p
        int n = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());

        // 우선선위큐로 범위 내 허수아비의 체력을 오름차순으로 살펴본다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // 체력 합
        long sum = 0;
        StringBuilder sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        // 필요한 허수아비의 개수
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            // 현 위치의 허수아비 체력
            int num = Integer.parseInt(st.nextToken());
            // 합에 포함
            sum += num;
            priorityQueue.offer(num);
            // 우선순위큐에서 오름차순으로 살펴보며, 제거해도 p이상의 체력합이 되는 경우 제거한다.
            while (!priorityQueue.isEmpty() && sum - priorityQueue.peek() >= p)
                sum -= priorityQueue.poll();

            // 체력 합이 p 이상인 경우, 현 우선순위큐의 크기를 ans에 반영한다.
            if (sum >= p)
                ans = Math.min(ans, priorityQueue.size());
            // 답 기록
            sb.append(ans == Integer.MAX_VALUE ? -1 : ans).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답 출력
        System.out.println(sb);
    }
}