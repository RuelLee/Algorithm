/*
 Author : Ruel
 Problem : Baekjoon 13418번 학교 탐방하기
 Problem address : https://www.acmicpc.net/problem/13418
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13418_학교탐방하기;

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
        // n개의 건물과 m + 1개의 도로가 주어진다.
        // 각각의 도로는 오르막과 내리막이 있어, 0번 지점으로 부터 n개의 건물을 모두 들리는데 최소한의 오르막 길만 오르고 싶다.
        // 피로도 계산은 최초 조사된 길을 기준으로 한다. (오르막 <-> 내리막이 변경되지 않는다)
        // 경로의 피로도는 오르막길의 개수의 제곱이 된다고 한다.
        // 최소 피로도와 최대 피로도의 차이를 구하라.
        //
        // 최소 신장 트리 문제
        // 설정상 오류가 좀 있는 문제 같다.
        // 주어지는 길에 따라 오르막, 내리막이 결정되어 a -> b가 오르막이라면 b -> a는 당연히 내리막이어야하는데, 오르막으로 계산해야했다.
        // 이해가 가지 않는 해당 부분을 제외하면 최소 신장 트리를 두 번 구하면 되는 문제다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 주어지는 도로들
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());

        for (int i = 0; i < m + 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 오르막 0, 내리막 1로 주어지므로, 반대로 값을 넣었다.
            roads.get(a).add(new Road(b, (c + 1) % 2));
            roads.get(b).add(new Road(a, (c + 1) % 2));
        }
        
        // 최소 피로도
        boolean[] visited = new boolean[n + 1];
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparing(road -> road.cost));
        priorityQueue.offer(new Road(0, 0));
        int[] minCosts = new int[n + 1];
        Arrays.fill(minCosts, Integer.MAX_VALUE);
        minCosts[0] = 0;
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            if (visited[current.end])
                continue;
            
            for (Road next : roads.get(current.end)) {
                // 미방문이고, 해당 지점으로 갈 때 내리막으로 갈 수 있다면
                if (!visited[next.end] && minCosts[next.end] > next.cost) {
                    minCosts[next.end] = next.cost;
                    priorityQueue.offer(new Road(next.end, next.cost));
                }
            }
            visited[current.end] = true;
        }
        
        // 최대 피로도
        visited = new boolean[n + 1];
        priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.cost, o1.cost));
        priorityQueue.offer(new Road(0, 0));
        int[] maxCosts = new int[n + 1];
        Arrays.fill(maxCosts, -1);
        maxCosts[0] = 0;
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            if (visited[current.end])
                continue;

            for (Road next : roads.get(current.end)) {
                // 미방문이고, 해당 지점으로 갈 때 오르막으로 갈 수 있다면
                if (!visited[next.end] && maxCosts[next.end] < next.cost) {
                    maxCosts[next.end] = next.cost;
                    priorityQueue.offer(new Road(next.end, next.cost));
                }
            }
            visited[current.end] = true;
        }
        
        // 두 피로도 제곱의 차
        int answer = (int) (Math.pow(Arrays.stream(maxCosts).sum(), 2) - Math.pow(Arrays.stream(minCosts).sum(), 2));
        System.out.println(answer);
    }
}