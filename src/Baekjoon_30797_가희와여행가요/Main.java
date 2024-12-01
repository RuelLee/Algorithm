/*
 Author : Ruel
 Problem : Baekjoon 30797번 가희와 여행가요
 Problem address : https://www.acmicpc.net/problem/30797
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30797_가희와여행가요;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int from;
    int to;
    int cost;
    int time;

    public Road(int from, int to, int cost, int time) {
        this.from = from;
        this.to = to;
        this.cost = cost;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 지점, q개의 설치 가능한 노선이 주어진다.
        // 노선의 정보는 다음과 같이 주어진다
        // from to cost time
        // from과 to를 잇는 양방향 노선을 cost를 들여 시각 time에 건설된다.
        // 1번 도시에서 모든 도시를 직간접적으로 최소 비용으로 최대한 빠르게 잇고자 할 때,
        // 시각과 총 비용을 출력하라
        // 불가능하다면 -1을 출력한다
        //
        // 최소 스패닝 트리 문제
        // 다만 비교해야하는 요소가 비용과 시간 두 개다
        // 비용을 우선하되, 동일 비용이라면 이른 시간이 우선이다.
        // prim 알고리즘으로, 현재 연결된 도시들에서 건설할 수 있는 노선들 중
        // 가장 가격이 저렴한 노선 순으로 살펴보며 도시들을 연결해 나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, q개의 노선
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 노선들
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        
        // 노선 정보
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            roads.get(from).add(new Road(from, to, cost, time));
            roads.get(to).add(new Road(to, from, cost, time));
        }
        
        // 연결된 도시들
        boolean[] connected = new boolean[n + 1];
        // 건설 비용 합
        long costSum = 0;
        // 가장 늦은 노선의 건설 시각
        int lastTime = 0;
        
        // 우선순위큐
        // 저비용 우선, 이른 시간 우선
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.cost == o2.cost)
                return Integer.compare(o1.time, o2.time);
            return Integer.compare(o1.cost, o2.cost);
        });
        priorityQueue.offer(new Road(0, 1, 0, 0));
        // 연결된 도시의 수
        int count = 0;
        while (!priorityQueue.isEmpty() && count < n) {
            Road current = priorityQueue.poll();
            // 이미 연결된 도시라면 건너뛴다.
            if (connected[current.to])
                continue;
            
            // 연결된 도시의 수 증가
            count++;
            // 비용 증가
            costSum += current.cost;
            // 가장 늦은 건설 시각값을 갱신하는지 확인
            lastTime = Math.max(lastTime, current.time);
            // 연결 체크
            connected[current.to] = true;
            
            // current.to에서 연결할 수 있는 다른 노선들을 우선순위큐에 추가
            for (Road r : roads.get(current.to)) {
                if (!connected[r.to])
                    priorityQueue.offer(r);
            }
        }
        
        // n개의 도시를 연결하는 것이 불가능했다면
        // -1 출력
        if (count < n)
            System.out.println(-1);
        else        // 가능했다면, 최종 완성 시각과 비용 출력
            System.out.println(lastTime + " " + costSum);
    }
}