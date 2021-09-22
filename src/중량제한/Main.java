/*
 Author : Ruel
 Problem : Baekjoon 1939번 중량제한
 Problem address : https://www.acmicpc.net/problem/1939
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 중량제한;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Bridge {
    int next;
    int weight;

    public Bridge(int next, int weight) {
        this.next = next;
        this.weight = weight;
    }
}

public class Main {
    static final int MAX = 1_000_000_000;

    public static void main(String[] args) {
        // 그냥 queue로 풀 경우, 시간이 초과
        // 따라서 우선순위큐로 풀어, 우선적으로 높은 값으로 end에 도달할 때만 계산하는 걸로 바꾸었다
        // 중간에 현재 지점에서의 값이 더 크더라도, 다리를 건너서 end에 도달할 때 값이 낮아질 수도 있다.
        // 따라서 우선순위큐의 값이 현재 최종도달지점의 값보다 낮은 시점에서 그만해야한다
        // 검색을 통해 다른 사람들의 답안을 보니, 이분탐색을 통해 값의 범위를 줄여가는 방법과
        // Kruskal 을 통해 간선을 선택하는 방법으로도 풀 수 있는 것 같다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        List<List<Bridge>> bridges = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            bridges.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int weight = sc.nextInt();
            bridges.get(a).add(new Bridge(b, weight));
            bridges.get(b).add(new Bridge(a, weight));
        }

        int start = sc.nextInt();
        int end = sc.nextInt();

        int[] maxWeight = new int[n + 1];
        maxWeight[start] = MAX;
        PriorityQueue<Bridge> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.weight, o1.weight));       // 우선순위큐로 현재 지점에서 무게가 높은 순서대로 계산
        priorityQueue.offer(new Bridge(start, maxWeight[start]));
        while (!priorityQueue.isEmpty()) {
            Bridge current = priorityQueue.poll();

            for (Bridge next : bridges.get(current.next)) {
                if (maxWeight[next.next] < Math.min(maxWeight[current.next], next.weight)) {        // 다음 지점의 무게가 갱신될 때만
                    maxWeight[next.next] = Math.min(maxWeight[current.next], next.weight);      // 새로운 최대 무게를 넣고
                    priorityQueue.offer(new Bridge(next.next, maxWeight[next.next]));           // 갱신
                }
            }
            if (!priorityQueue.isEmpty() && priorityQueue.peek().weight < maxWeight[end])       // 우선순위큐에 현재 도달 지점의 최대무게보다 낮은 값이 들어있다면 그 이후는 패스
                break;
        }
        System.out.println(maxWeight[end]);
    }
}