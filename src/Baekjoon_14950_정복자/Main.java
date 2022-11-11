/*
 Author : Ruel
 Problem : Baekjoon 14950번 정복자
 Problem address : https://www.acmicpc.net/problem/14950
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14950_정복자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Cost {
    int end;
    int cost;

    public Cost(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시, m개의 도로가 주어진다
        // 처음 점거하고 있는 도시는 1번 도시이며, 점거하고 있는 도시들로부터
        // 연결된 다른 도시들을 점거할 수 있다고 한다.
        // 한 번 도시가 정복되면 다른 도시들은 경계를 하기 때문에 모든 도로의 비용이 t만큼 증가한다고 한다.
        // 모든 도시를 정거하는데 소모되는 비용은?
        //
        // 뭔가 + 되려다가 만 최소 신장 트리 문제
        // 일반적은 크루스칼, 프림으로 풀 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // n이 최대 1만으로 주어지기 때문에 인접행렬보다는 인접리스트로 작성.
        List<List<Cost>> adjList = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            adjList.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());

            adjList.get(a).add(new Cost(b, c));
            adjList.get(b).add(new Cost(a, c));
        }
        
        // 각 도시에 이르는 최소 도로 비용
        int[] minCosts = new int[n];
        Arrays.fill(minCosts, Integer.MAX_VALUE);
        // 초기값
        minCosts[0] = 0;

        // 첫 도시로부터 갈 수 있는 다른 도시들은 우선순위큐에 담는다.
        PriorityQueue<Cost> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        for (Cost c : adjList.get(0)) {
            if (minCosts[c.end] > c.cost) {
                minCosts[c.end] = c.cost;
                priorityQueue.offer(c);
            }
        }

        // 정복된 도시의 수
        int conquered = 0;
        // 전체 비용
        int totalCost = 0;
        boolean[] checked = new boolean[n + 1];
        while (!priorityQueue.isEmpty()) {
            Cost current = priorityQueue.poll();
            // 이미 정복한 도시라면 건너 뛴다.
            if (checked[current.end])
                continue;

            // 새롭게 정복하는 도시라면, 기존에 정복됐던 도시의 수에 따라 비용이
            // t배 증가한다.
            // 따라서 원래 도로의 비용과 추가 비용을 같이 합산해서 전체 비용에 합산해주자.
            totalCost += conquered++ * t + minCosts[current.end];
            for (Cost c : adjList.get(current.end)) {
                // c.end가 아직 정복되지 않았고, 계산됐던 비용보다 더 적은 비용으로 도달 할 수 있다면
                // 값을 갱신하고 우선순위큐에 담아주자.
                if (!checked[c.end] && minCosts[c.end] > c.cost) {
                    minCosts[c.end] = c.cost;
                    priorityQueue.offer(c);
                }
            }
            // 방문 체크
            checked[current.end] = true;
        }
        // 전체 비용 출력.
        System.out.println(totalCost);
    }
}