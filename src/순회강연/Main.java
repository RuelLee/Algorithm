/*
 Author : Ruel
 Problem : Baekjoon 2109번 순회강연
 Problem address : https://www.acmicpc.net/problem/2109
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 순회강연;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Lecture {
    int pay;
    int days;

    public Lecture(int pay, int days) {
        this.pay = pay;
        this.days = days;
    }
}

public class Main {
    static int[] schedules;

    public static void main(String[] args) throws IOException {
        // 각 강연에 대해 강연비와 강연을 해야하는 기한이 주어진다
        // 강연비 합의 최대값을 구하라
        // 강연비에 대해서 내림차순 정렬
        // 그리고 해당 강연에 대해 스케쥴과 기한을 고려하여 가장 늦은 날에 배치를 반복하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        PriorityQueue<Lecture> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.pay, o1.pay));        // 페이순으로 내림차순 정렬
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            priorityQueue.offer(new Lecture(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        // n일에 대해서 가장 비어있는 이른 날짜를 저장할 것이다.
        // 초기값은 당일이 비어있으므로 n = n
        schedules = new int[10001];
        for (int i = 1; i < schedules.length; i++)
            schedules[i] = i;

        int sum = 0;
        while (!priorityQueue.isEmpty()) {
            // 가장 페이가 좋은 강연을 꺼내
            Lecture current = priorityQueue.poll();
            // 기한과 스케쥴을 고려한 가장 늦은 날짜를 구한다(=day)
            int day = findVacantDay(current.days);
            // day가 0이라면 가능하지 않다.
            // 0이 아니라면 해당 날짜에 해당 강연을 간다고 생각하고
            // 강연비를 더한다.
            // schedules[day]에는 day보다 이르지만 가장 가까운 비어있는 날의 값을 넣어둔다.
            if (day != 0) {
                schedules[day] = findVacantDay(day - 1);
                sum += current.pay;
            }
        }

        System.out.println(sum);
    }

    static int findVacantDay(int day) {     // day보다 같거나 이르면서 가장 가까운 비어있는 날을 찾는다.
        if (schedules[day] == day)      // 그 값이 자기 자신아라면, day 그대로 반환
            return day;
        // 그렇지 않다면, schedules[day]에 들어있는 날보다 같거나 이르면서 가장 가까운 비어있는 날을 가져와, schedules[day]에 저장하고 반환한다.
        return schedules[day] = findVacantDay(schedules[day]);
    }
}