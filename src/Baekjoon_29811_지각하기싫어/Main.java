/*
 Author : Ruel
 Problem : Baekjoon 29811번 지각하기 싫어
 Problem address : https://www.acmicpc.net/problem/29811
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_29811_지각하기싫어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Channel {
    int idx;
    int pop;

    public Channel(int idx, int pop) {
        this.idx = idx;
        this.pop = pop;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 애지문, ITBT관, 대운동장의 세 장소가 주어진다.
        // 김한양은 애지문에서 ITBT관으로 가는데, 대운동장을 반드시 거쳐간다.
        // 애지문에서 대운동장으로 가는 경로 n개가 1 ~ n번
        // 대운동장에서 ITBT관으로 가는 경로 m개가 n+1 ~ n+m번으로 주어진다.
        // 각 경로는 경로에 있는 사람의 수와 이동 시간이 비례한다.
        // 각 경로에 처음에 있는 사람의 수들이 주어진다. 이 때 다음 두 쿼리를 처리하는 프로그램을 작성하라
        // U x y -> x번 경로의 인구를 y로 바꾼다.
        // L -> 애지문에서 ITBT관으로 이동하는 최소 경로를 출력한다.
        //
        // 우선순위큐
        // 로 간단히 풀 수 있는 문제
        // 우선순위큐로 해당 인구 수가 최소인 경로를 우선적으로 꺼낸다.
        // 경로의 인구수가 갱신될 때는 값을 추가한 뒤
        // 최소 경로를 따질 때, 유효하지 않은 우선순위큐 값은 제거하는 방법으로 사용한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 애지문에서 대운동장까지의 경로 수 n
        int n = Integer.parseInt(st.nextToken());
        // 대운동장에서 ITBT관까지의 경로 수 m
        int m = Integer.parseInt(st.nextToken());
        
        // 각 경로 위의 사람 수
        int[] pops = new int[n + m];
        for (int i = 0; i < n + m; i++) {
            if (i == 0 || i == n)
                st = new StringTokenizer(br.readLine());
            pops[i] = Integer.parseInt(st.nextToken());
        }
        
        // 애지문에서 대운동장까지의 경로들을 우선순위큐를 통해 최소 인구우선으로 관리
        PriorityQueue<Channel> priorityQueue1 = new PriorityQueue<>((o1, o2) -> {
            if (o1.pop == o2.pop)
                return Integer.compare(o1.idx, o2.idx);
            return Integer.compare(o1.pop, o2.pop);
        });
        for (int i = 0; i < n; i++)
            priorityQueue1.offer(new Channel(i, pops[i]));
        // 대운동장에서 ITBT관으로의 경로를 우선순위큐를 통해 최소 인구 우선으로 관리
        PriorityQueue<Channel> priorityQueue2 = new PriorityQueue<>((o1, o2) -> {
            if(o1.pop == o2.pop)
                return Integer.compare(o1.idx, o2.idx);
            return Integer.compare(o1.pop, o2.pop);
        });
        for (int i = n; i < n + m; i++)
            priorityQueue2.offer(new Channel(i, pops[i]));

        int k = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            // 첫번째 쿼리 처리
            if (st.nextToken().equals("U")) {
                // 경로 위의 인구 수 갱신
                // x번 경로의 인구는 y
                int x = Integer.parseInt(st.nextToken()) - 1;
                int y = Integer.parseInt(st.nextToken());
                pops[x] = y;
                
                // x가 n미만이라면 애지문 <-> 대운동장 우선순위큐에 추가
                if (x < n)
                    priorityQueue1.offer(new Channel(x, y));
                else        // 그 외라면 대운동장 <-> ITBT관 우선순위큐에 추가
                    priorityQueue2.offer(new Channel(x, y));
            } else {        // 두번째 쿼리 처리
                // 각 우선순위큐에서 유효하지 않은 값 제거
                while (!priorityQueue1.isEmpty() && pops[priorityQueue1.peek().idx] != priorityQueue1.peek().pop)
                    priorityQueue1.poll();
                while (!priorityQueue2.isEmpty() && pops[priorityQueue2.peek().idx] != priorityQueue2.peek().pop)
                    priorityQueue2.poll();
                
                // 각 우선순위큐에서 가장 빠른 경로의 idx를 꺼내 답안 작성
                sb.append(priorityQueue1.peek().idx + 1).append(" ").append(priorityQueue2.peek().idx + 1).append("\n");
            }
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}