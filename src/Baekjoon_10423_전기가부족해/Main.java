/*
 Author : Ruel
 Problem : Baekjoon 10423번 전기가 부족해
 Problem address : https://www.acmicpc.net/problem/10423
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10423_전기가부족해;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Cable {
    int end;
    int cost;

    public Cable(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시와 도시들을 잇는 m개의 케이블, 그리고 발전소가 있는 도시 번호들이 주어진다
        // 각 도시들을 연결하여, 각 도시에 1개의 발전소만 직간접적으로 연결하고자한다.
        // 이 때의 케이블 비용을 구하라
        //
        // 최소 신장 트리 문제
        // kruskal, prim 알고리즘 적용 가능.
        // 프림 알고리즘으로 풀어보았다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 발전소들.
        int[] plants = new int[k];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < plants.length; i++)
            plants[i] = Integer.parseInt(st.nextToken()) - 1;

        // 케이블들.
        List<List<Cable>> cables = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            cables.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int w = Integer.parseInt(st.nextToken());

            cables.get(u).add(new Cable(v, w));
            cables.get(v).add(new Cable(u, w));
        }

        // 각 도시를 발전소와 잇는 최소 비용.
        int[] costs = new int[n];
        // 초기화.
        Arrays.fill(costs, 10_001);
        // 발전소가 있는 도시의 비용은 0. 해당 도시들을 모두 우선순위큐에 최소힙으로 넣는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(p -> costs[p]));
        for (int plant : plants) {
            costs[plant] = 0;
            priorityQueue.offer(plant);
        }
        
        // 총 케이블 설치 비용
        int sum = 0;
        boolean[] connected = new boolean[n];
        while (!priorityQueue.isEmpty()) {
            int city = priorityQueue.poll();
            // 이미 연결된 도시라면 건너뛰기.
            if(connected[city])
                continue;

            // city의 케이블 비용을 sum에 더해준다.
            sum += costs[city];
            // city로 부터 연결할 수 있는 다른 도시들을 살펴본다
            // 해당 도시의 케이블 연결 비용이 city로 부터 연결하는게 더 저렴하다면
            // 우선순위큐의 순서와 비용을 갱신.
            for (Cable c : cables.get(city)) {
                if (!connected[c.end] && costs[c.end] > c.cost) {
                    costs[c.end] = c.cost;
                    priorityQueue.remove(c.end);
                    priorityQueue.offer(c.end);
                }
            }
            // 방문 체크.
            connected[city] = true;
        }
        // 총 연결 비용 출력.
        System.out.println(sum);
    }
}