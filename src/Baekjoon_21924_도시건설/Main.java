/*
 Author : Ruel
 Problem : Baekjoon 21924번 도시 건설
 Problem address : https://www.acmicpc.net/problem/21924
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21924_도시건설;

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
        // 신도시에 건물 사이를 잇는 양방햐 도로를 건설하고자 한다.
        // 모든 건물들을 연결하되, 그 비용을 최소로 하고싶다.
        // n개의 건물과 m개의 건물쌍과 두 건물을 잇는 도로 건설 비용이 주어질 때
        // 모든 도로를 건설하는 것보다 최소 비용으로 도로를 건설하는 것이 얼마나
        // 비용을 절감하는지 나타내라
        //
        // 최소 스패닝 트리 문제
        // Prim, Kruskal 알고리즘으로 해결 가능
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 처리
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 인접 리스트
        List<List<Road>> roads = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        
        // 모든 도로 건설 비용
        long sum = 0;
        // 도로를 a와 b에 모두 추가.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            sum += c;

            roads.get(a).add(new Road(b, c));
            roads.get(b).add(new Road(a, c));
        }

        // 서로 건물들 끼리 연결되어있는 클러스터에 포함되었는지 확인.
        boolean[] connected = new boolean[n + 1];
        // 비용
        int[] costs = new int[n + 1];
        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[1] = 0;
        // 우선순위큐로 연결 비용이 적은 도시부터 연결.
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        priorityQueue.offer(new Road(1, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // currnet가 이미 연결되어있다면 건너뛴다.
            if (connected[current.end])
                continue;

            // current에서 건설 가능한 도로들을 살펴본다.
            for (Road next : roads.get(current.end)) {
                // 연결되는 도시가 아직 클러스터 연결이 안됐고
                // 기존 연결 비용보다 더 저렴하다면
                if (!connected[next.end] && costs[next.end] > next.cost) {
                    // 비용 갱신 후, 우선순위큐에 추가.
                    costs[next.end] = next.cost;
                    priorityQueue.offer(new Road(next.end, costs[next.end]));
                }
            }
            // 연결 체크
            connected[current.end] = true;
        }

        // 모든 도로 건설 비용 sum 에서 현재 건설된 도로들의 비용을 제외한다.
        // 남은 비용이 절약한 비용.
        for (int i = 1; i < costs.length; i++) {
            // 만약 연결되지않고 초기값을 갖고 있는 도시가 있다면
            // 모든 건물 연결이 되지 않았으므로 -1을 남겨두고 종료.
            if (costs[i] == Integer.MAX_VALUE) {
                sum = -1;
                break;
            }
            sum -= costs[i];
        }
        // 결과 출력.
        System.out.println(sum);
    }
}