/*
 Author : Ruel
 Problem : Baekjoon 20160번 야쿠르트 아줌마 야쿠르트 주세요
 Problem address : https://www.acmicpc.net/problem/20160
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20160_야쿠르트아줌마야쿠르트주세요;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    long weight;

    public Road(int end, long weight) {
        this.end = end;
        this.weight = weight;
    }
}

public class Main {
    static List<List<Road>> roads;
    static HashMap<Integer, HashMap<Integer, Long>> travelTimes;

    public static void main(String[] args) throws IOException {
        // V개의 정점, E개의 도로가 주어진다.
        // 야쿠르트 아줌마는 10개의 지점을 최단 시간으로 이동하며 들리신다. 방문하시는 10개의 지점이 주어진다.
        // 야쿠르트 아줌마는 해당 지점보다 같거나 이른 시간에 도착한 사람에게 야쿠르트를 판매하시고 바로 다음 지점으로 이동하신다.
        // 만약 i번째 지점에서 i+1번째 지점으로 이동하는 것이 불가능하다면, i+2, .. i+3.. 순으로 가능한 다음 지점을 방문하신다.
        // 도로를 이동할 때는 오직 가중치만큼의 시간이 소모된다.
        // 야쿠르트 아줌마는 10개 중 첫번째 지점에서 출발하며
        // 나의 출발 지점 또한 주어질 때
        // 야쿠르트를 살 수 있는 가장 작은 번호의 지점은?
        //
        // dijkstra 문제
        // 먼저, 나는 출발지점에서 부터 다른 지점까지 이동 가능한 최단 시간을 모두 구해둔다.
        // 그 후, 야쿠르트 아줌마는 10개의 지점을 쫓아가며, 이동할 때는 최단 경로로 이동하되,
        // 10개 지점 중 중복하여 방문하는 곳이 있다면, 해당 방문 시간은 가장 늦은 시간으로 기록해둔다.
        // 그 후, 10개의 지점에 대해 야쿠르트 아줌마와 나의 방문 시간을 비교하여
        // 내가 더 먼저 혹은 동시에 도착하는 곳들 중 가장 이른 지점의 번호를 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // V개의 지점, E개의 간선
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        
        // 도로 정보
        roads = new ArrayList<>();
        for (int i = 0; i < V + 1; i++)
            roads.add(new ArrayList<>());

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            roads.get(u).add(new Road(v, w));
            roads.get(v).add(new Road(u, w));
        }
        
        // 야쿠르트 아줌마께서 방문하는 10개의 지점
        int[] stops = new int[10];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < stops.length; i++)
            stops[i] = Integer.parseInt(st.nextToken());

        // 각 지점에서 dijkstra를 돌려, 다른 지점까지 이르는 시간을 구해둔다.
        HashSet<Integer> check = new HashSet<>();
        travelTimes = new HashMap<>();
        for (int i = 0; i < stops.length; i++) {
            if (!check.contains(stops[i])) {
                dijkstra(stops[i]);
                check.add(stops[i]);
            }
        }

        // 야쿠르트 아줌마의 방문 순서를 따라가며
        // 각 지점에 이르는 가장 늦은 시간을 구한다.
        HashMap<Integer, Long> lastTimes = new HashMap<>();
        lastTimes.put(stops[0], 0L);
        int current = stops[0];
        for (int i = 1; i < stops.length; i++) {
            // current 에서 stop[i]로 방문이 불가능한 경우 건너뛴다.
            if (!travelTimes.get(current).containsKey(stops[i]))
                continue;

            // current -> stops[i]로 이동하는 경우.
            // stops[i]에 방문하는 가장 늦은 시간을 기록해둔다.
            lastTimes.put(stops[i], Math.max(!lastTimes.containsKey(stops[i]) ? 0 : lastTimes.get(stops[i]), lastTimes.get(current) + travelTimes.get(current).get(stops[i])));
            // 현재 위치는 stops[i]
            current = stops[i];
        }
        
        // start에서 출발하여 각 지점에 이르는 시각
        int start = Integer.parseInt(br.readLine());
        if (!check.contains(start))
            dijkstra(start);

        // 야쿠르트 아줌마보다 일찍 혹은 동시에 도착할 수 있는 가장 정점 번호가 작은 지점을 찾는다.
        int answer = Integer.MAX_VALUE;
        for (int stop : stops) {
            // 나와 야쿠르트 아줌마 모두 방문 가능하고
            // 내가 같거나 이른 시각에 도착하는 지점 중 가장 작은 번호를 answer에 기록
            if (lastTimes.containsKey(stop) && travelTimes.get(start).containsKey(stop) && travelTimes.get(start).get(stop) <= lastTimes.get(stop))
                answer = Math.min(answer, stop);
        }
        // answer가 초기값이라면 -1, 그렇지 않다면 해당 값 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
    
    // 다익스트라
    static void dijkstra(int start) {
        // start에서 출발하여 각 지점에 방문하는데 소요되는 시간
        HashMap<Integer, Long> times = new HashMap<>();
        // 출발지 시각
        times.put(start, 0L);
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.weight));
        priorityQueue.offer(new Road(start, 0));
        // 방문 체크
        boolean[] visited = new boolean[roads.size()];
        // dijkstra
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 이미 방문했다면 건너뜀.
            if (visited[current.end])
                continue;

            for (Road next : roads.get(current.end)) {
                // next가 아직 미방문이고,
                // next에 도달한 시각이 아직 없거나, 더 적은 시각이라면 현재 값을 기록, 우선순위큐에 추가 
                if (!visited[next.end] &&
                        (!times.containsKey(next.end) || times.get(next.end) > current.weight + next.weight)) {
                    times.put(next.end, current.weight + next.weight);
                    priorityQueue.offer(new Road(next.end, current.weight + next.weight));
                }
            }
            // 방문 체크
            visited[current.end] = true;
        }
        // 계산된 결과값들을 추가.
        travelTimes.put(start, times);
    }
}