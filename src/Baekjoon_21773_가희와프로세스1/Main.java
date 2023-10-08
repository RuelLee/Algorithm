/*
 Author : Ruel
 Problem : Baekjoon 21773번 가희와 프로세스 1
 Problem address : https://www.acmicpc.net/problem/21773
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21773_가희와프로세스1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Task {
    int id;
    int b;
    int c;

    public Task(int id, int b, int c) {
        this.id = id;
        this.b = b;
        this.c = c;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 다음과 같이 동작하는 스케쥴러를 구성한다.
        // 우선 순위값이 제일 큰 프로세스
        // 그러한 프로세스가 여러개라면 id가 가장 적은 프로세스
        // 실행시킬 프로세스를 선택하고, 실행한다.
        // 1초간 해당 프로세스를 실행하고, 해당 프로세스의 작업 시간이 1 감소하고,
        // 나머지 프로세스들의 우선 순위는 1 증가한다.
        // n개의 작업에 대해
        // id, 작업 시간, 우선순위가 주어진다.
        // t 초간 실행되는 각각의 프로세스들을 출력하라
        //
        // 우선순위큐 문제
        // 우선순위큐를 통해 프로세스들의 우선순위에 따라 꺼내 작업시킨다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // t초간
        int t = Integer.parseInt(st.nextToken());
        // n개의 프로세스들에 대해 스케쥴러를 동작시킨다.
        int n = Integer.parseInt(st.nextToken());
        
        // 먼저 우선순위가 큰 순서대로, 같다면
        // id가 작은 순서대로
        PriorityQueue<Task> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.c == o2.c)
                return Integer.compare(o1.id, o2.id);
            return Integer.compare(o2.c, o1.c);
        });
        // 프로세스들을 우선순위에 추가
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            priorityQueue.offer(new Task(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        StringBuilder sb = new StringBuilder();
        // t초간 프로세스 할당 내역을 기록한다.
        for (int i = 0; i < t; i++) {
            // 프로세스를 꺼내
            Task current = priorityQueue.poll();
            // 해당 프로세스 기록
            sb.append(current.id).append("\n");
            // 작업 시간 1 감소
            current.b--;
            // 다른 모든 프로세스들의 우선순위를 증가시키기보다
            // 해당 프로세스의 우선 순위만 1 감소시키면 된다.
            current.c--;

            // 아직 current의 작업 시간이 남아있다면 다시 우선순위큐에 담는다.
            if (current.b > 0)
                priorityQueue.offer(current);
        }
        
        // 전체 기록 출력
        System.out.print(sb);
    }
}