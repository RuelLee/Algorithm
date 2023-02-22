/*
 Author : Ruel
 Problem : Baekjoon 14908번 구두 수선공
 Problem address : https://www.acmicpc.net/problem/14908
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14908_구두수선공;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 하나의 작업은 번호, 소요일, 보상금으로 구성.
class Task {
    int num;
    int t;
    int s;

    public Task(int num, int t, int s) {
        this.num = num;
        this.t = t;
        this.s = s;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 구두가 있고
        // 각 구두를 수리하는데 t일이 소요되며, 구두 수선 작업을 시작하지 않았다면 s원만큼의 보상금을 매일 지불해야한다.
        // 최저 보상금을 지불하는 작업 순서를 출력하라
        // 여러가지 순서가 나올 수 있다면 오름차순 정렬에 의하 가장 첫번째 해답을 출력한다.
        //
        // 그리디 문제
        // 당연히 보상금이 큰 작업을 우선하며, 다른 작업들을 덜 지연시키는 작업을 우선해야한다.
        // 따라서 s / t 의 내림차순으로 정렬하되, 같은 값이 있다면 작업 번호 오름차순으로 정렬하여 처리한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 구두
        int n = Integer.parseInt(br.readLine());
        // 우선순위큐를 사용하여 처리.
        PriorityQueue<Task> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            // 두 작업의 우선순위
            double priority1 = (double) o1.s / o1.t;
            double priority2 = (double) o2.s / o2.t;

            // 같다면 번호 오름차순
            if (priority1 == priority2)
                return Integer.compare(o1.num, o2.num);
            // 다르다면 우선순위가 큰 값을 우선.
            return Double.compare(priority2, priority1);
        });

        // n개의 구두들을 우선순위큐에 담는다.
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            priorityQueue.offer(new Task(i + 1, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        
        StringBuilder sb = new StringBuilder();
        // 순차적으로 작업들을 꺼내며 해당 번호들을 기록
        while (!priorityQueue.isEmpty())
            sb.append(priorityQueue.poll().num).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답안 출력.
        System.out.println(sb);
    }
}