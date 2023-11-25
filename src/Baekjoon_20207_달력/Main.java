/*
 Author : Ruel
 Problem : Baekjoon 20207번 달력
 Problem address : https://www.acmicpc.net/problem/20207
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20207_달력;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1부터 365로 한 줄로 표시되어있는 달력을 가지고 있다.
        // 일정이 있는 곳에 코팅지를 붙이려한다.
        // 같은 날짜에 여러 일정이 있다면 아래로 써나간다.
        // 연속된 날짜에 일정이 1개 이상 있다면 연속된 일정이라 보고 하나의 코팅지로 포함시킨다.
        // 하루에 있는 일정은 모두 하나의 코팅지에 포함시킨다.
        // 필요한 전체 코팅지의 넓이는?
        //
        // 정렬 문제
        // 가장 이른 시간에 시작되는 일정부터 살펴보며
        // 연속한 일정에 주의하고, 하루에 있는 최대 일정 수를 계산하며
        // 필요한 코팅지의 넓이를 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 일정
        int n = Integer.parseInt(br.readLine());
        int[][] tasks = new int[n][];
        for (int i = 0; i < tasks.length; i++)
            tasks[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 시작일에 따라 정렬
        Arrays.sort(tasks, Comparator.comparing(o -> o[0]));

        // 종료일에 따라 최소힙 우선순위큐에 담는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // 시작일
        int start = 0;
        // 종료일
        int end = 0;
        // 하루 최대 일정
        int maxSize = 0;
        // 계산된 코팅지의 넓이
        int sum = 0;
        for (int[] task : tasks) {
            // task[i]와 우선순위큐에 담긴 일정이 하루 이상 간격이 있다면
            // 모두 꺼낸다.
            while (!priorityQueue.isEmpty() && task[0] > priorityQueue.peek() + 1)
                priorityQueue.poll();

            // 우선순위큐가 비었다면,
            // 연속한 일정이 아니므로 해당 일정들에 대해 코팅지를 잘라 붙인다.
            if (priorityQueue.isEmpty()) {
                // 해당하는 넓이
                sum += (end - start + 1) * maxSize;

                // 값 초기화
                start = Integer.MAX_VALUE;
                end = 0;
                maxSize = 0;
            }

            // 이번 일정을 우선순위큐에 추가
            priorityQueue.offer(task[1]);
            start = Math.min(start, task[0]);
            end = Math.max(end, task[1]);
            // 주의할 점으로 1 ~ 2일에 하는 일정과 3 ~4일에 하는 일정은 연속되어있으나
            // 서로 일정이 겹치지는 않으므로 하루 최대 일정이 늘어나선 안된다.
            // 따라서 위에서는 하나 이상 간격이 있다면 제거했지만.
            // 하루 일정을 계산할 땐 겹치지 않는 일정들에 대해서는 제거한다.
            while (!priorityQueue.isEmpty() && task[0] > priorityQueue.peek())
                priorityQueue.poll();
            maxSize = Math.max(maxSize, priorityQueue.size());
        }
        // 최종적으로 남아있는 일정에 대한 코팅지의 넓이
        sum += (end - start + 1) * maxSize;

        // 전체 넓이는 계산한다.
        System.out.println(sum);
    }
}