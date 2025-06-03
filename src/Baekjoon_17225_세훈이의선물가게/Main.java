/*
 Author : Ruel
 Problem : Baekjoon 17225번 세훈이의 선물가게
 Problem address : https://www.acmicpc.net/problem/17225
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17225_세훈이의선물가게;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Wrap {
    int time;
    int color;

    public Wrap(int time, int color) {
        this.time = time;
        this.color = color;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 선물 가게에서 선물을 포장을 한다.
        // 선물 가게에 주문을 할 때에는, 파란색 포장과 빨간색 포장 중 하나를 고를 수 있으며
        // 알바생은 하나씩 준비된 선물을 가져와 순서대로 포장을 한다.
        // 상민이는 파란색, 지수는 빨간색 포장을 하며, 동시에 포장을 시작할 경우, 상민이가 먼저 가져온다.
        // 각각의 알바생이 포장을 준비하는데 걸리는 시간 a, b, 선물을 주문한 손님의 수 n
        // 그리고 각각의 손님이 주문한 시점 t, 색상 c, 주문한 선물의 개수 m이 주어진다.
        // 각각 알바생이 포장한 선물의 개수와 선물의 순서들을 출력하라.
        //
        // 정렬, 우선순위큐, 시뮬레이션 문제
        // 각 손님이 주문한 시점과 알바생이 포장할 수 있는 시점을 잘 비교하여
        // 선물이 포장된 시점을 계산해내는 것이 중요하다.
        // 우선순위큐를 통해 시점이 이르다면 시점으로, 같은 시점이라면 상민이가 우선적으로 선물을 가져오도록하여
        // 각각이 포장한 선물을 분류하도록 하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 선물을 포장하는데 걸리는 시간 a, b, 손님의 수 n
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        
        // 우선순위큐
        // 같은 시간이라면 상민이가 우선, 다르다면 시간 순 정렬
        PriorityQueue<Wrap> pq = new PriorityQueue<Wrap>((o1, o2) -> {
            if (o1.time == o2.time)
                return o1.color == 0 ? -1 : 1;
            return Integer.compare(o1.time, o2.time);
        });

        // 포장을 마쳐 한가한 시점.
        int[] lastTimes = new int[2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 손님이 주문한 시점, 색상, 개수
            int t = Integer.parseInt(st.nextToken());
            int color = st.nextToken().charAt(0) == 'B' ? 0 : 1;
            int m = Integer.parseInt(st.nextToken());

            // 한가한 시점과 주문한 시점을 비교하여 더 큰 값으로 맞춰준다.
            lastTimes[color] = Math.max(lastTimes[color], t);
            for (int j = 0; j < m; j++) {
                // 선물을 포장한 시점
                pq.offer(new Wrap(lastTimes[color], color));
                // 포장하는데 걸리는 시간을 더해준다.
                lastTimes[color] += (color == 0 ? a : b);
            }
        }

        // 선물을 색상 별로 나눠준다.
        List<Integer> blues = new ArrayList<>();
        List<Integer> reds = new ArrayList<>();
        // 선물의 순서
        int count = 1;
        while (!pq.isEmpty()) {
            // 색상에 따라 count번째 선물이
            // 상민이 혹은 지수에게 전달된다.
            if (pq.peek().color == 0)
                blues.add(count++);
            else
                reds.add(count++);
            // 해당 선물 제거
            pq.poll();
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 상민이가 포장한 선물 개수
        sb.append(blues.size()).append("\n");
        // 각 선물의 순서
        for (int order : blues)
            sb.append(order).append(" ");
        sb.deleteCharAt(sb.length() - 1).append("\n");
        // 지수가 포장한 선물의 개수
        sb.append(reds.size()).append("\n");
        // 각 선물의 순서
        for (int order : reds)
            sb.append(order).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답안 출력
        System.out.print(sb);
    }
}