/*
 Author : Ruel
 Problem : Baekjoon 31877번 EDF
 Problem address : https://www.acmicpc.net/problem/31877
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31877_EDF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Task {
    int addedTime;
    int workingTime;
    int deadline;

    public Task(int addedTime, int workingTime, int deadline) {
        this.addedTime = addedTime;
        this.workingTime = workingTime;
        this.deadline = deadline;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 작업이 현재 쌓여있으며, 시간이 흐르며 m개의 작업이 추가된다.
        // 각각의 작업은 작업 시간과 마감 시각이 주어지고,
        // 추가되는 작업은 추가 시간과 작업 시간, 마감 시각이 주어진다.
        // 작업중인 작업들에 대해서는 마감 시각이 이른 작업을 우선적으로 처리한다.
        // 작업 도중 마감 시간이 더 이른 작업이 추가된 경우
        // 하던 작업을 중단하고 새롱누 작업을 시작한다.
        // 하던 작업은 작업한 시간 만큼 차감되어 나중에 작업하게 된다.
        // 모든 작업을 마감 시각 이전까지 처리할 수 있다면
        // YES와 작업 완료 시간을
        // 그렇지 않다면 NO를 출력한다.
        //
        // 우선순위큐 문제
        // 우선순위큐를 통해 현재 대기중인 작업에 대해서는 마감 시각에 따른 오름차순 정렬하여 처리하고
        // 아직 추가되지 않은 작업에 대해서는 추가 시간에 따른 오름차순으로 정렬하여 처리한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 현재 대기중인 n개의 작업
        int n = Integer.parseInt(br.readLine());
        // 마감 시각에 따른 오름차순 처리
        PriorityQueue<Task> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.deadline));
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            priorityQueue.offer(new Task(0, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        
        // 추가될 작업들
        // 추가 시간에 따른 오름차순 처리
        int m = Integer.parseInt(br.readLine());
        PriorityQueue<Task> tasksWillAdd = new PriorityQueue<>(Comparator.comparingInt(o -> o.addedTime));
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            tasksWillAdd.offer(new Task(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        // 현재 시각 0
        int time = 0;
        // 두 우선순위큐가 하나라도 비어있지 않다면 진행
        while (!priorityQueue.isEmpty() || !tasksWillAdd.isEmpty()) {
            // 만약 대기중인 작업이 없는데
            // 추가될 작업은 있다면
            if (priorityQueue.isEmpty() && !tasksWillAdd.isEmpty()) {
                // 시간을 추가시간으로 이동
                time = tasksWillAdd.peek().addedTime;
                // 대기중인 작업으로 이동
                priorityQueue.offer(tasksWillAdd.poll());
            }
            
            // 만약 이번 작업을 처리하는 시각이 마감 시각보다 늦다면
            // 모든 작업을 마감 시각 이전에 처리하는 것이 불가능한 경우
            // 그대로 반복문 종료
            if (time + priorityQueue.peek().workingTime > priorityQueue.peek().deadline)
                break;
            // 그렇지 않다면 작업을 꺼낸다.
            Task current = priorityQueue.poll();
            
            // 추가될 작업이 current 작업 도중에 추가된다면
            if (!tasksWillAdd.isEmpty() &&
                    tasksWillAdd.peek().addedTime >= time && tasksWillAdd.peek().addedTime < time + current.workingTime) {
                // 추가될 시각까진 작업을 진행하여 시간을 차감하고
                current.workingTime -= (tasksWillAdd.peek().addedTime - time);
                // 시간 이동
                time = tasksWillAdd.peek().addedTime;
                // 두 작업 모두 대기중인 작업 우선순위큐에 보낸다.
                priorityQueue.offer(current);
                priorityQueue.offer(tasksWillAdd.poll());
            } else      // 그렇지 않다면 현재 작업을 완료하고, 해당 시각으로 이동
                time += current.workingTime;
        }
        
        // 모든 작업을 처리한 경우
        // YES와 완료 시각 출력
        if (priorityQueue.isEmpty() && tasksWillAdd.isEmpty()) {
            System.out.println("YES");
            System.out.println(time);
        } else      // 그렇지 않은 경우 NO 출력
            System.out.println("NO");
    }
}