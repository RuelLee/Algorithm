/*
 Author : Ruel
 Problem : Baekjoon 1374번 강의실
 Problem address : https://www.acmicpc.net/problem/1374
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1374_강의실;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Class {
    int start;
    int end;

    public Class(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 강의에 대해 시작 시간과 종료 시간이 주어진다.
        // n개의 강의를 진행하는데 있어, 필요한 최소 강의실의 수는?
        //
        // 정렬과 우선순위큐를 활용하여 풀었다.
        // 먼저 강의가 진행되는 시간을 오름차순으로 정렬한다.
        // 그리고 하나씩 강의의 시작 시간을 살펴보며, 해당 강의보다 같거나 이른 시간에 끝난 강의는 우선순위큐에서 제거
        // 그리고 해당 강의를 우선순위큐에 삽입해가며, 우선순위큐의 최대 크기를 구한다
        // 이 최대값이 필요한 최소 강의실의 수.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // n개의 강의에 대한 입력 처리.
        int n = Integer.parseInt(br.readLine());
        Class[] classes = new Class[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            st.nextToken();
            classes[i] = new Class(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        // 강의를 시작 시간 오름차순으로 정렬한다.
        Arrays.sort(classes, Comparator.comparing(c -> c.start));
        // 진행되고 있는 강의를 종료 시간에 따라 오름차순 정렬한다.
        PriorityQueue<Integer> endTimes = new PriorityQueue<>();
        // 동시에 진행되는 강의의 최대 수.
        int maxClassesInSameTime = 0;
        // 강의들을 순차적으로 살펴본다.
        for (int i = 0; i < classes.length; i++) {
            // i번째 강의의 시작 시간보다 같거나 이르게 끝나는 강의들은
            // 우선순위큐에서 제거해준다.
            while (!endTimes.isEmpty() && endTimes.peek() <= classes[i].start)
                endTimes.poll();

            // 그리고 i번째 강의의 종료 시간을 우선순위큐에 담아주고,
            endTimes.offer(classes[i].end);
            // 우선순위큐의 크기가 최대값을 갱신했는지 확인한다.
            maxClassesInSameTime = Math.max(maxClassesInSameTime, endTimes.size());
        }
        // 최종적으로 구해진 우선순위큐의 최대 크기가 동시에 진행되는
        // 강의들의 최대값이고, 이 값이 강의들을 진행하는데 필요한 최소 강의실의 수이다.
        System.out.println(maxClassesInSameTime);
    }
}