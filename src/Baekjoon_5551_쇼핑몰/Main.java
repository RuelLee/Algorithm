/*
 Author : Ruel
 Problem : Baekjoon 5551번 쇼핑몰
 Problem address : https://www.acmicpc.net/problem/5551
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5551_쇼핑몰;

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

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시가 있고, m개의 도로가 양방향으로 도시들을 잇고 있다.
        // 이 중 k개의 도시에는 쇼핑몰이 존재한다.
        // 가장 가까운 쇼핑몰의 거리가 가장 먼 집을 찾는다.
        // 집은 도시에 있을 수도 있고, 도로 위에 있을 수도 있다.
        //
        // 다익스트라 문제
        // 평범한 약간 다름을 얹은 문제
        // 평범하게 쇼핑몰에서 도시까지 이르는 거리를 다익스트라를 통해 구한다.
        // 그 후, 도로들을 모두 살피면서, 양쪽 도시에서 쇼핑몰에 이르는 최소거리를 살펴본다.
        // 두 도시를 i, j, 쇼핑몰까지의 최소거리를 di, dj, 두 도시를 잇는 도로의 길이를 l이라 할 때
        // |di - dj| >= l인 경우는 도로 위에 집이 존재하는 경우를 살펴볼 필요가 없다.
        // 거리가 먼 도시에 집이 있는 경우가 유리하기 때문
        // 그 외의 경우는 도로 위에 집이 있는 경우가 유리하다
        // 위 경우는 (di + dj + l) / 2로 도로 위에 집이 존재할 때 가장 먼 위치를 구할 수 있다.
        // 반올림을 하기로 하였으므로 계산 상 편의를 위해 (di + dj + l + 1) / 2로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 도로, k개의 쇼핑몰
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 도로 정보
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, l));
            roads.get(b).add(new Road(a, l));
        }
        
        // 각 도시에서 쇼핑몰에 이르는 최소 거리
        int[] distances = new int[n + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));
        // 쇼핑몰의 위치 입력
        for (int i = 0; i < k; i++) {
            int shop = Integer.parseInt(br.readLine());
            distances[shop] = 0;
            priorityQueue.offer(new Road(shop, 0));
        }

        // 다익스트라
        int answer = 0;
        boolean[] visited = new boolean[n + 1];
        while (!priorityQueue.isEmpty()) {
            // current.end에서 연결되어있는 도로들을 살펴본다.
            Road current = priorityQueue.poll();
            // 이미 방문했다면 건너뜀
            if (visited[current.end])
                continue;
            
            // 연결된 도로들
            for (Road next : roads.get(current.end)) {
                // next.end를 아직 방문하지 않았고
                // 최소 거리가 갱신되는 경우
                if (!visited[next.end] && distances[next.end] > distances[current.end] + next.distance) {
                    distances[next.end] = distances[current.end] + next.distance;
                    priorityQueue.offer(new Road(next.end, distances[next.end]));
                }
            }
            // current.end 방문 체크
            visited[current.end] = true;
            // 도시에 집에 있는 경우, distance[current.end]가 답이 될 수 있다.
            answer = Math.max(answer, distances[current.end]);
        }
        
        // 도로 위에 집이 있는 경우 계산
        for (int i = 1; i < roads.size(); i++) {
            for (int j = 0; j < roads.get(i).size(); j++) {
                // i 도시까지의 거리
                int distanceToI = distances[i];
                // end 도시까지의 거리
                int distanceToEnd = distances[roads.get(i).get(j).end];
                // i와 end를 잇는 도로의 길이
                int roadLength = roads.get(i).get(j).distance;

                // 한 쪽 도시의 거리가 짧은 쪽 거리 + l보다 같거나 긴 경우
                // 계산할 필요가 없이 건너뛴다.
                if (Math.abs(distanceToI - distanceToEnd) >= roadLength)
                    continue;

                // 도로 위에 집이 있는 것이 더 거리가 먼 경우를 계산.
                answer = Math.max(answer, (distanceToI + distanceToEnd + roadLength + 1) / 2);
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}