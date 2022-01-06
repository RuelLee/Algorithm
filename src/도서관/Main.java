/*
 Author : Ruel
 Problem : Baekjoon 1461번 도서관
 Problem address : https://www.acmicpc.net/problem/1461
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 도서관;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0 위치에 있는 책들을 -10000 ~ 10000 위치에 있는 위치에 다시 정리하려고 한다
        // 전체 책의 개수와 한번에 들 수 있는 책의 수가 주어지고, 마지막 책을 정리하고서는 다시 0 위치에 돌아갈 필요는 없다
        // 전체 책을 정리하는데 소요되는 최소 걸음 수를 구하라.
        // 마지막 책은 정리하고 그대로 멈추면 되므로, 다른 책들처럼 다시 0 위치로 돌아갈 필요가 없기 떄문에
        // 가장 멀리 있는 책을 옮길 때 사용해야한다
        // 기본적으로는 절대값에 대해서 내림차순 정렬을 한 후,
        // 순차적으로 살펴보면서, 한번에 옮길 수 있는 책들(m)에 대해서는 최대값 * 2를 걸음수를 해준다(해당 책 위치로 이동 후 다시 0 위치로 이동)
        // 음수, 양수가 다를 수 있으므로, 서로 카운터를 별도로 센다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 우선순위 큐를 사용해서 절대값에 대한 내림차순으로 정렬하자.
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(Math.abs(o2), Math.abs(o1)));
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            pq.offer(Integer.parseInt(st.nextToken()));

        // 양수 위치에 대한 카운터
        int plusCount = 0;
        // 음수 위치에 대한 카운터
        int minusCount = 0;
        // 첫번째 책(가장 먼 책)에 대해서는 0 위치로 돌아올 필요가 없으므로, 한번만 계산하도록 해당 값에 대해 미리 한번 빼주자.
        int sum = pq.isEmpty() ? 0 : -Math.abs(pq.peek());

        // 큐가 빌 때까지
        while (!pq.isEmpty()) {
            int current = pq.poll();
            if (current > 0) {      // 꺼낸 수가 양수라면
                // 이 책 때문에 해당 거리를 가야한다면, 해당 거리의 2배를 더해준다.
                if (plusCount % m == 0)
                    sum += current * 2;
                // 그리고 해당 거리를 가는 동안 옮기는 책을 세는 plusCount를 하나 증가시킨다
                // 해당 거리를 가면서 지나는 m - 1권의 책은 sum엔 더하지 않고 plusCount만 증가.
                plusCount++;
            } else {        // 음수에 대해서도 같은 방법으로 계산한다.
                if (minusCount % m == 0)
                    sum -= current * 2;
                minusCount++;
            }
        }
        // 최종적으로 구해지는 sum이 최소 이동 거리.
        System.out.println(sum);
    }
}