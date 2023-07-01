/*
 Author : Ruel
 Problem : Baekjoon 1379번 강의실 2
 Problem address : https://www.acmicpc.net/problem/1379
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1379_강의실2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Class {
    int no;
    int start;
    int end;

    public Class(int no, int start, int end) {
        this.no = no;
        this.start = start;
        this.end = end;
    }
}

class Room {
    int no;
    int end;

    public Room(int no, int end) {
        this.no = no;
        this.end = end;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 강의들에 대해 강의 번호, 시작 시간, 종료 시간이 주어진다.
        // 강의들을 최소한의 강의실에 배정하려할 때
        // 강의실의 최소 수와
        // 각 강의들이 배정되는 강의실 번호를 출력하라
        //
        // 그리디 문제
        // 대개 최소 강의실의 수만 출력하는 것과 다르게 이번에는
        // 강의를 강의실에 배정한 후, 강의실 번호를 출력해야한다
        // 매 강의마다 현재 공실이 있는지 확인하고, 없다면 강의실을 추가해나가며
        // 강의실을 배정해주자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 강의들
        int n = Integer.parseInt(br.readLine());

        // 우선순위큐에 강의 시작 시간으로 담자.
        PriorityQueue<Class> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.start));
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            priorityQueue.offer(new Class(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        // 각 강의들이 배정되는 강의실 번호를 기록한다.
        int[] answer = new int[n + 1];
        // 다음에 사용될 강의실 번호
        int RoomCounter = 1;
        // 강의실은 배정된 강의의 종료 시간에 따라 우선순위큐에 담는다.
        PriorityQueue<Room> rooms = new PriorityQueue<>(Comparator.comparingInt(value -> value.end));
        while (!priorityQueue.isEmpty()) {
            // 이번에 강의실을 배정해야할 강의
            Class current = priorityQueue.poll();
            Room next;
            // 만약 다른 강의가 할당됐던 강의실이 비어 현재 공실이라면
            // 해당 공실에 현재 강의을 배정한다.
            if (!rooms.isEmpty() && current.start >= rooms.peek().end)
                next = new Room(rooms.poll().no, current.end);
            // 없다면 새로운 강의실을 늘려, 해당 강의를 배정한다.
            else
                next = new Room(RoomCounter++, current.end);
            // 배정된 강의에 해당하는 강의실 번호를 기록하고
            answer[current.no] = next.no;
            // 강의 종료 시간에 따른 우선순위큐에 담는다.
            rooms.offer(next);
        }

        StringBuilder sb = new StringBuilder();
        // 사용된 강의실의 개수를 기록하고
        sb.append(RoomCounter - 1).append("\n");
        // 각 강의마다 사용한 강의실 번호를 토대로 답안 작성.
        for (int i = 1; i < answer.length; i++)
            sb.append(answer[i]).append("\n");
        // 답안 출력
        System.out.print(sb);
    }
}