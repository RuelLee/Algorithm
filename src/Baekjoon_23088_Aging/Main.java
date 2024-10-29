/*
 Author : Ruel
 Problem : Baekjoon 23088번 Aging
 Problem address : https://www.acmicpc.net/problem/23088
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23088_Aging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 우선순위 스케줄링은 우선순위가 높은 프로세스가 리소스를 먼저 사용하도록 하는 스케줄링이다.
        // 우선순위가 높은 프로세스만 처리되다보면, 낮은 프로세스는 영원히 실행되지 않을 수 있다.
        // 따라서 aging은 대기 중인 프로세스들의 우선순위를 점진적으로 증가시켜
        // 우선순위가 낮은 프로세스더라도 오랫동안 대기했다면, 결국 높은 우선순위를 갖게되어 실행될 수 있게 보완한 스케줄링이다.
        // n개의 프로세스가 순서대로 주어진다.
        // 각 프로세스는 실행이 요청된 시점 t, 초기 우선순위 p, 실행시간 b를 갖는다.
        // 현재 실행 중인 프로세스가 없다면, 실행 요청된 프로세스 중 우선순위가 가장 높은 프로세스가 실행된다.
        // 우선순위가 같은 프로세스가 여러개라면 실행 시간이 짧은 프로세스가 먼저 실행된다.
        // 우선순위, 실행 시간이 같은 프로세스가 여러개라면, 작읜 번호의 프로세스가 먼저 실행된다.
        // 프로세스가 실행 중인 도중에는 다른 프로세스 실행 요청이 들어와도 중단하지 않는다.
        // 프로세스들의 실행 순서를 출력하라
        //
        // 우선순위 큐 문제
        // 우선순위큐를 통해 조건에 가장 맞는 프로세스를 하나씩 처리해나간다.
        // 단, 실행되지 않은 프로세스들을 모두 우선순위를 변경해나가는 작업을 한다면, 연산이 너무 많아진다./
        // 따라서 시간 0을 기준으로, 실행 요청 시간에 따른 우선순위에 패널티를 적용하는 방법으로 한다.
        // 만약 3초에 실행 요청이 들어왔다면, 3만큼의 패널티를 적용하여, 3초보다 전에 들어온 프로세스들에 대해서는 이점으로 작용하도록 한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 프로세스들
        int[][] processes = new int[n][3];
        StringTokenizer st;
        for (int i = 0; i < processes.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < processes[i].length; j++)
                processes[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 우선순위큐
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            // o1과 o2의 우선순위
            // 실행 요청 시간에 따른 패널티가 적용된다.
            int p1 = processes[o1][1] - processes[o1][0];
            int p2 = processes[o2][1] - processes[o2][0];
            
            // 우선순위가 같다면
            if (p1 == p2) {
                // 실행 시간도 같다면
                // 프로세스 번호가 작은 것이 우선된다.
                if (processes[o1][2] == processes[o2][2])
                    return Integer.compare(o1, o2);
                // 그렇지 않다면 실행 시간이 적은 것이 우선.
                return Integer.compare(processes[o1][2], processes[o2][2]);
            }
            // 우선순위가 다를 경우에는
            // 우선순위가 큰 것이 우선된다.
            return Integer.compare(p2, p1);
        });
        
        // 현재 시간
        int time = 0;
        // 가장 마지막에 추가된 프로세스 번호
        int lastAddedIdx = -1;
        StringBuilder sb = new StringBuilder();
        // 우선순위큐가 비지 않았거나, 아직 처리되지 않은 프로세스가 남아있는 동안 계속 반복한다.
        while (!priorityQueue.isEmpty() || lastAddedIdx + 1 < processes.length) {
            // 만약 우선순위큐가 비었는데, 다음 프로세스의 요청 시간이 되지 않았다면
            // 다음 프로세스의 요청 시간으로 시간 변경.
            if (priorityQueue.isEmpty() && time < processes[lastAddedIdx + 1][0])
                time = processes[lastAddedIdx + 1][0];
            
            // 현재 시간보다 같거나 이른 시간에 실행 요청이 들어온 프로세스들 모두 우선순위큐에 추가
            while (lastAddedIdx + 1 < processes.length && processes[lastAddedIdx + 1][0] <= time)
                priorityQueue.offer(++lastAddedIdx);

            // 프로세스를 하나 꺼내 처리
            int current = priorityQueue.poll();
            sb.append(current + 1).append(" ");
            // 시간은 해당 프로세스의 종료 시점으로 변경
            time += processes[current][2];
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력
        System.out.println(sb);
    }
}