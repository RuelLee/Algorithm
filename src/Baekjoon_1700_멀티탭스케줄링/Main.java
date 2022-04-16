/*
 Author : Ruel
 Problem : Baekjoon 1700번 멀티탭 스케줄링
 Problem address : https://www.acmicpc.net/problem/1700
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1700_멀티탭스케줄링;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n구의 멀티탭이 있고, 전자기기를 k번 사용하려고 한다
        // 플러그를 뽑는 횟수를 최소화할 때, 그 횟수는?
        //
        // 그리디한 문제다
        // 최대 n개의 전자 제품을 멀티탭에 꽂아둘 수 있다.
        // 따라서 멀티탭이 가득 차 있을 때, 뽑아야할 플러그는 다음 사용 시간이 가장 늦은 것을 뽑는 것이 유리하다.
        // 다음 사용 시간이 이른 것들은 멀티탭에 꽂혀있는 상태로 쓰일 확률이 높다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] orders = new int[k];
        // 다음 사용 시간을 큐로 관리하자.
        List<Queue<Integer>> sequences = new ArrayList<>();
        for (int i = 0; i < k + 1; i++)
            sequences.add(new LinkedList<>());

        for (int i = 0; i < orders.length; i++) {
            orders[i] = Integer.parseInt(st.nextToken());
            sequences.get(orders[i]).offer(i);
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            // o1 제품을 더 이상 사용하지 않는다면, 우선적으로 뽑아준다.
            if (sequences.get(o1).isEmpty())
                return -1;
            // 마찬가지로 o2 제품도 더 이상 사용하지않는다면 우선적으로 뽑는다.
            else if (sequences.get(o2).isEmpty())
                return 1;
            // 그렇지 않다면, o1, o2 제품 중 사용 시간이 늦은 걸 우선적으로 뽑는다.
            return Integer.compare(sequences.get(o2).peek(), sequences.get(o1).peek());
        });

        // 플러그를 뽑는 횟수를 계산한다.
        int count = 0;
        for (int order : orders) {
            if (priorityQueue.size() < n) {     // 만약 멀티탭이 가득차지 않았다면
                // 이번에 사용하는 전자 제품의 큐를 다음 순서로 넘기고
                sequences.get(order).poll();
                // 우선순위큐에서 order를 제거하는 이유는,
                // 멀티탭에 이미 꽂혀있는 경우와 우선순위 큐 갱신을 위해서다.
                priorityQueue.remove(order);
            } else {        // 멀티탭이 가득 차 있다면
                // 이번 순서에 사용할 전자 제품이 멀티탭에 꽂혀 있지 않다면
                // 하나의 제품의 플러그를 뽑아야한다.
                if (!priorityQueue.contains(order)) {
                    // 다음 사용 시간이 가장 늦은 제품을 뽑고,
                    priorityQueue.poll();
                    // 횟수 증가.
                    count++;
                } else      // 멀티탭에 이미 꽂혀있다면, 우선순위 큐 갱신을 위해 제거해준다.
                    priorityQueue.remove(order);
                // orders[i] 제품을 꽂을 것이므로, 해당 제품의 큐를 하나 뽑아준다.
                sequences.get(order).poll();
            }
            // 그리고 order를 우선순위큐에 담는다.
            priorityQueue.offer(order);
        }
        // 최종적으로 플러그를 뽑은 횟수를 출력한다.
        System.out.println(count);
    }
}