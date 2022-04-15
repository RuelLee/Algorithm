/*
 Author : Ruel
 Problem : Baekjoon 13308번 주유소
 Problem address : https://www.acmicpc.net/problem/13308
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13308_주유소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int distance;

    public Road(int end, int distance) {
        this.end = end;
        this.distance = distance;
    }
}

class State {
    int city;
    int lowestOilPrice;
    long totalCost;

    public State(int city, int lowestOilPrice, long totalCost) {
        this.city = city;
        this.lowestOilPrice = lowestOilPrice;
        this.totalCost = totalCost;
    }
}

public class Main {
    static int[] oilPrices;
    static List<List<Road>> roads;
    static long[][] costs;

    public static void main(String[] args) throws IOException {
        // n개의 도시와 각 도시의 기름 가격이 주어진다
        // 그리고 n개의 도시를 서로 잇는 m개의 도로의 거리가 주어진다
        // 기름은 거리당 1씩 소모하며, 처음 차에는 기름이 없으며, 기름통의 크기는 무한하다고 한다.
        // n번 도시에 도착하는데 드는 가장 적은 기름 비용은 얼마인가?
        //
        //
        // 다익스트라를 사용하되, 일반적인 최소 거리와는 조금 다르다
        // 만약 경유지가 늘더라도 주유소에서 파는 기름의 값이 더 적다면 결과적으로는 n까지 도달하는데 최소 비용이 감소할 수 있다
        // 따라서 각 도시에 방문할 때의 기름값과 여태까지 거쳐온 도시들의 최소 기름값을 활용하여 DP를 사용하자
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        oilPrices = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < oilPrices.length; i++)
            oilPrices[i] = Integer.parseInt(st.nextToken());

        roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int dis = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, dis));
            roads.get(b).add(new Road(a, dis));
        }

        // 각 도시에 도달하는 최소 비용을 거쳐온 최소기름값에 따라 구분하여 저장한다
        // costs[도시번호][최소기름값]
        costs = new long[n + 1][Arrays.stream(oilPrices).max().getAsInt() + 1];
        for (long[] c : costs)
            Arrays.fill(c, Long.MAX_VALUE);
        // 처음에는 max값으로 세팅
        // 1번 도시와 해당 도시의 주유소 가격에 따른 cost는 0
        costs[1][oilPrices[1]] = 0;
        // 방문 체크
        boolean[][] visited = new boolean[n + 1][costs[0].length];
        // 현재까지의 총 소모 비용에 따라 우선순위큐로 정렬하여 생각.
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.totalCost));
        priorityQueue.offer(new State(1, oilPrices[1], 0));
        long answer = 0;
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            // n 도시에 도달했다면, 우선순위큐로 최소 비용에 따라 먼저 계산했으므로, 해당 값이 최소 비용.
            if (current.city == n) {
                answer = current.totalCost;
                break;
            }
            // 방문한 적이 있다면 건너뛴다.
            if (visited[current.city][current.lowestOilPrice])
                continue;

            // current.city에 연결된 도로들을 살펴본다.
            for (Road r : roads.get(current.city)) {
                // 현재 갖고 있는 최소 기름 값과 다음 도시 기름 값과 비교하여 최소 기름 값을 계산
                int minOil = Math.min(current.lowestOilPrice, oilPrices[r.end]);

                // 해당 도시에 minOil 값으로 방문한 적이 없고
                // 최소 비용이 갱신된다면
                if (!visited[r.end][minOil] && costs[r.end][minOil] > current.totalCost + (long) r.distance * current.lowestOilPrice) {
                    // 해당 도시에 방문하는 최소 비용을 갱신해주고,
                    costs[r.end][minOil] = current.totalCost + (long) r.distance * current.lowestOilPrice;
                    // 우선순위큐에 삽입.
                    priorityQueue.offer(new State(r.end, minOil, costs[r.end][minOil]));
                }
            }
            // 방문 체크.
            visited[current.city][current.lowestOilPrice] = true;
        }
        // 최종적으로 얻어진 answer이 1번 도시에 n번 도시에 도달하는 최소 비용.
        System.out.println(answer);
    }
}