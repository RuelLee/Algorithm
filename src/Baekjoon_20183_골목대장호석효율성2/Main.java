/*
 Author : Ruel
 Problem : Baekjoon 20183번 골목 대장 호석 - 효율성 2
 Problem address : https://www.acmicpc.net/problem/20183
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20183_골목대장호석효율성2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    long cost;

    public Road(int end, long cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static List<List<Road>> roads;
    static int a, b;
    static long c;

    public static void main(String[] args) throws IOException {
        // n개의 교차로, m개의 도로가 주어진다.
        // a번 교차로에서 b번 교차로까지 c원 이하로 도착하고자 한다.
        // 각 도로에는 골목대장이 서 있으며, 일정 금액을 받는다고 한다.
        // 수치심은 경로에서 가장 많이 낸 돈에 비례한다.
        // 목적지까지 다다르는데, 최소한의 수치심으로 도착하고자한다면
        // 지나는 로목의 요금의 최댓값의 최솟값을 출력하라
        //
        // 다익스트라, 이분 탐색
        // 가지고 있는 금액 c가 10^14까지 주어지고
        // 각 골목의 수금 금액에 최대 10^9까지 주어진다.
        // 따라서 골목마다 일정액 이하의 통행료만 지불하면서 목적지까지 도달할 수 있는지
        // 그리고 총 금액이 c이하인지를 이분 탐색으로 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 교차로, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 출발지 a, 도착지 b, 총 금액 c
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        c = Long.parseLong(st.nextToken());
        
        // 도로들
        roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            roads.get(start).add(new Road(end, cost));
            roads.get(end).add(new Road(start, cost));
        }
        
        // 이분 탐색
        long start = 0;
        long end = c + 1;
        while (start < end) {
            long mid = (start + end) / 2;
            // 골목당 통행료를 mid 이하, 총 금액을 c 이하로
            // b에 도달할 수 있다면
            // 골목당 통행료의 최대액을 mid로 줄임
            if (possibleMaxN(mid))
                end = mid;
            else    // 불가능하다면 최소액을 mid +1로 높임.
                start = mid + 1;
        }
        // 만약 최종 금액이 c를 넘어선다면 불가능한 경우이므로 -1 출력
        // 이하라면 그 금액을 출력
        System.out.println(end > c ? -1 : end);
    }

    // 골목 당 최대 금액을 n 이하로 지불하며 b에 도달할 수 있는지를 판별한다.
    static boolean possibleMaxN(long n) {
        // 각 지점에 이르는 총 금액의 최솟값들
        HashMap<Integer, Long> minCost = new HashMap<>();
        // 방문 체크
        boolean[] visited = new boolean[roads.size()];
        // dijkstra
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.cost));
        priorityQueue.offer(new Road(a, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            if (visited[current.end])
                continue;
            else if (current.end == b)
                break;

            for (Road next : roads.get(current.end)) {
                // 미방문이며, 도로 이용료가 n이하이고, next.end에 이르는 최소 금액을 갱신하는 경우
                if (!visited[next.end] && next.cost <= n &&
                        (!minCost.containsKey(next.end) || minCost.get(next.end) > current.cost + next.cost)) {
                    priorityQueue.offer(new Road(next.end, current.cost + next.cost));
                    minCost.put(next.end, current.cost + next.cost);
                }
            }
            visited[current.end] = true;
        }

        // b에 도달할 수 있으며, 그 금액이 c이하일 경우
        // true 반환. 그 외의 경우 false
        return minCost.containsKey(b) && minCost.get(b) <= c;
    }
}