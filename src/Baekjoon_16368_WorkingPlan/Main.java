/*
 Author : Ruel
 Problem : Baekjoon 16368번 Working Plan
 Problem address : https://www.acmicpc.net/problem/16368
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16368_WorkingPlan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n일 동안 m명의 작업자가 작업을 하려고 한다.
        // 각각의 작업자가 n일 동안 일하는 총 근로 시간이 주어지고
        // 각 날짜별로 필요한 작업자의 수가 주어진다.
        // 작업자들은 w일 동안 작업을 하고, h일 동안 쉬어야 다음 작업에 투입될 수 있다.
        // 작업자들을 적절히 배치하여, 근무 쉬프트를 만들 수 있는지 계산하라
        // 그러하다면, 각 작업자가 근무를 시작하는 날을 출력하고,
        // 불가능한 경우 -1을 출력한다.
        //
        // 그리디, 누적합, 우선순위큐 문제
        // 먼저 잔여 작업 시간이 가장 많은 작업자를 우선 배치한다.
        // 이를 위해, 현재 배정된 작업자의 수는 누적합을 통해 관리하고
        // 작업자는 우선순위큐를 통해 작업 시간이 많은 남은 사람을 우선적으로 배치한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n일 동안, m명의 작업자가
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        // w일 일하고, h일을 쉰다.
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 각 작업자들의 잔여 작업 시간
        int[] workers = new int[m];
        for (int i = 0; i < workers.length; i++)
            workers[i] = Integer.parseInt(st.nextToken());
        
        // 날짜별 필요한 작업자의 수
        int[] needWorkers = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < needWorkers.length; i++)
            needWorkers[i] = Integer.parseInt(st.nextToken());
        
        // 처음에는 모든 작업자들이 가용 자원
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(workers[o2], workers[o1]));
        for (int i = 0; i < workers.length; i++)
            priorityQueue.offer(i);
        
        // 해당 날부터 다시 투입할 수 있는 작업자들
        Queue<Integer>[] availableWorkers = new Queue[n + 1];
        for (int i = 0; i < availableWorkers.length; i++)
            availableWorkers[i] = new LinkedList<>();

        // 해당 작업자가 작업을 시작한 날짜들.
        Queue<Integer>[] workedDays = new Queue[m];
        for (int i = 0; i < workedDays.length; i++)
            workedDays[i] = new LinkedList<>();
        
        // 누적합을 통해 현재 배정된 작업자들을 괸리한다.
        int[] psums = new int[n + 2];
        // 가능 여부
        boolean possible = true;
        for (int i = 1; i < psums.length - 1; i++) {
            // 누적합 처리
            psums[i] += psums[i - 1];

            // 오늘부터 다시 작업이 가능한 작업자들을 우선순위큐에 추가.
            while (!availableWorkers[i].isEmpty())
                priorityQueue.offer(availableWorkers[i].poll());

            // i일에 필요한 작업자들을 배정한다.
            while (!priorityQueue.isEmpty() && psums[i] < needWorkers[i]) {
                int worker = priorityQueue.poll();
                // 만약 작업자의 잔여 작업 시간이 w보다 작다면
                // 작업이 불가능하다.
                // 인원 배치가 불가능한 경우이므로 possible = false 처리
                if (workers[worker] < w) {
                    possible = false;
                    break;
                }
                // i일 작업자 수 증가
                psums[i]++;
                // 현재 작업자가 참가하지 않는 i + w일에는 -1
                psums[i + w]--;
                // 다음 투입 시간이 n일보다 작다면
                // 다음 투입 가능한 날의 큐에 추가.
                if (i + w + h < n + 1)
                    availableWorkers[i + w + h].offer(worker);
                // worker의 작업 시작일 추가.
                workedDays[worker].add(i);
                // worker의 잔여 작업 시간 차감.
                workers[worker] -= w;
            }
            // 만약 작업자들을 필요한 수만큼 배치하지 못했다면
            // 불가능한 경우.
            if (psums[i] < needWorkers[i])
                possible = false;

            // 불가능하다면 반복문 종료
            if (!possible)
                break;
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 가능한 경우.
        if (possible) {
            // 1 출력 후
            sb.append(1).append("\n");
            // 각 작업자들의 작업 시작일 기록
            for (Queue<Integer> wd : workedDays) {
                while (!wd.isEmpty())
                    sb.append(wd.poll()).append(" ");
                sb.deleteCharAt(sb.length() - 1).append("\n");
            }
        } else      // 불가능한 경우 -1 기록
            sb.append(-1).append("\n");
        // 전체 답안 출력
        System.out.print(sb);
    }
}