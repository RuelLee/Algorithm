/*
 Author : Ruel
 Problem : Baekjoon 20182번 골목 대장 호석 - 효율성 1
 Problem address : https://www.acmicpc.net/problem/20182
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20182_골목대장호석효율성1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int cost;

    public Road(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 지점, m개의 길이 주어진다.
        // 각 길에는 통행료가 있다.
        // 소지금 c원을 들고서 a지점에서 b지점으로 이동하려한다.
        // 골목길에서 내는 최대 통행료를 최소로 하고자할 때 그 금액은?
        // 도달하지 못한다면 -1을 출력한다.
        //
        // 다익스트라 문제
        // 단순히 최소 비용으로 b지점에 도달하는 것이 아닌
        // 중간에 들리는 도로에서 지불하는 비용 중 최대값을 최소가 되게끔 해야한다.
        // 각 도로에서 내는 금액이 최대 20원이므로
        // remain[지점][여태까지지불한최대통행료] = 최대 소지금으로
        // 각 지점과 최대 통행료를 기준으로 최대 소지금을 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 지점, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // a지점에서 c 지점으로 이동하며, 초기 소지금은 c원
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // 도료 연결 상태
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            roads.get(left).add(new Road(right, cost));
            roads.get(right).add(new Road(left, cost));
        }
        
        // 각 지점과 최대 통행료에 따른 남은 소지금
        int[][] remain = new int[n + 1][21];
        for (int[] r : remain)
            Arrays.fill(r, -1);
        // 시작 지점에선 최대 통행료가 0이고, c원을 들고 있다.
        remain[a][0] = c;
        // 우선순위큐를 통해 최대 통행료가 낮은 순으로 계산한다.
        PriorityQueue<Road> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        queue.offer(new Road(a, 0));
        int answer = -1;
        while (!queue.isEmpty()) {
            Road current = queue.poll();
            // 목적지에 도달했다면 그 때의 최대 통행료 기록하고 종료
            if (current.end == b) {
                answer = current.cost;
                break;
            }

            // current에서 갈 수 있는 다른 지점들을 살펴본다.
            for (Road next : roads.get(current.end)) {
                // 최대 통행료 계산
                int maxCost = Math.max(current.cost, next.cost);
                int currentRemain = remain[current.end][current.cost] - next.cost;
                // 다음 지점으로 이동할 때, 최대 소지금을 갱신한다면
                if (remain[next.end][maxCost] < currentRemain) {
                    // maxCost보다 더 최대 통행료들에 대해서
                    // currentReamin보다 더 작은 소지금을 갖는다면 의미가 없다.
                    // 따라서 maxCost부터 20까지 모두 currentRemain 값을 채운다.
                    for (int i = maxCost; i < remain[next.end].length; i++)
                        remain[next.end][i] = currentRemain;
                    // 우선순위큐에 추가.
                    queue.offer(new Road(next.end, maxCost));
                }
            }
        }

        // 최대 통행료 중 최소값을 출력.
        System.out.println(answer);
    }
}