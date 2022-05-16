/*
 Author : Ruel
 Problem : Baekjoon 13904번 과제
 Problem address : https://www.acmicpc.net/problem/13904
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13904_과제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    static int[] days;

    public static void main(String[] args) throws IOException {
        // n개의 과제에 대해 마감 기한과 점수가 주어진다
        // 얻을 수 있는 최대 점수를 구하라
        //
        // 그리디한 문제
        // 획득할 수 있는 점수가 높은 과제일 수록 마감기한에 가깝도록 배치한다
        // 1 20 / 2 50 / 3 30 / 4 10 / 4 60 / 4 40 / 6 5가 주어진다면
        // 가장 점수가 높은 4 60을 우선적으로 4일차에 배치하고 다음 2 50을 2일차에 배치한다
        // 다음으로 높은 점수를 갖는 과제는 4 40인데 4일에 이미 다른 과제가 배치되어있으므로 3일차에 배치한다
        // 다음으로는 3 30인데 이는 3일차도 2일차도 차있으므로 1일차에 배치한다.
        // 이런식으로 점수가 높은 과제부터 살펴보며, 마감기한보다 이른 날짜들 중 가장 늦은 날짜에 과제들을 배치해나가며 합을 구하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] works = new int[n][];
        // 가장 늦은 마감 기한
        int maxDay = 0;
        for (int i = 0; i < n; i++) {
            works[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            maxDay = Math.max(maxDay, works[i][0]);
        }

        // 가장 늦은 마감 기한까지 날짜들을 배치.
        // 해당 날짜가 비어있다면 days는 자기 자신의 idx값을 가르킬 것이고
        // 비어있지 않다면 자신보다 이른 날짜를 가르킬 것이다.
        days = new int[maxDay + 1];
        for (int i = 1; i < days.length; i++)
            days[i] = i;

        // 우선순위큐를 통해 높은 점수 순으로 살펴본다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(works[o2][1], works[o1][1]));
        for (int i = 0; i < works.length; i++)
            priorityQueue.offer(i);

        // 점수의 합
        int sum = 0;
        // 큐가 빌 때까지
        while (!priorityQueue.isEmpty()) {
            // 남은 과제 중 가장 높은 점수를 갖는 과제에 대해 계산한다
            // 해당 과제의 마감 기한까지 중 빈 날짜가 있는지 찾아본다.
            int vacantDay = findVacantDay(works[priorityQueue.peek()][0]);
            // 만약 빈 날짜가 없다면 해당 과제는 수행할 수 없다.
            if (vacantDay == 0)
                priorityQueue.poll();
            else {
                // 있다면 해당 과제의 점수를 더하고
                sum += works[priorityQueue.poll()][1];
                // 해당 날에는 과제가 할당되었으므로 days[vacantDay]는 원래 값보다 하루 이른 날짜를 가르키도록 한다.
                days[vacantDay]--;
            }
        }
        System.out.println(sum);
    }

    // n일차까지 중 비어있는 가장 늦은 날짜를 찾아낸다.
    static int findVacantDay(int n) {
        // days[n]이 n이라면 n일이 비어있는 것.
        // 해당 날짜를 리턴한다.
        if (days[n] == n)
            return n;
        // 그렇지 않다면, 재귀적으로 days[n] == n이 되는 시점까지 찾아내며, 경로 단축을 해준다.
        return days[n] = findVacantDay(days[n]);
    }
}