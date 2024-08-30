/*
 Author : Ruel
 Problem : Baekjoon 16167번 A Great Way
 Problem address : https://www.acmicpc.net/problem/16167
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16167_AGreatWay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int via;
    int cost;

    public Road(int end, int via, int cost) {
        this.end = end;
        this.via = via;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 거점의 수 n, 경로의 개수 r이 주어진다.
        // 경로는 각각
        // a b c d e 형태로 주어지며, a 출발지, b 도착지, c 10분간 이동하는 기본 요금, d 10분 이후 1분당 추가 요금, e 소요 시간
        // 으로 주어진다.
        // 1번 위치에서 n번 위치로 가고자 할 때
        // 최소 비용과 거치는 거점의 수를 출력하라
        // 최소 비용 경로가 여럿 존재한다면 거점의 수가 적은 곳을 출력한다.
        //
        // dijkstra 문제
        // 평범하게 풀 수 있는 다익스트라 문제
        // 경로가 10분 요금, 추가 1분 요금, 소요 시간으로 주어지기 때문에
        // 세 값을 이용하여 실제로 이동할 경우의 요금으로 따로 계산만 해주면 된다.
        // 거쳐가는 거점의 수도 중요하기 때문에
        // distances[거점번호][거쳐온겨점의수] = 최소 요금
        // 으로 정하고 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 거점, r개의 경로
        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        // 경로
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            // e를 따져보아 10분 이하라면 기본 요금만
            // 10분 초과라면 초과분만큼 추가요금을 더해 실제 요금을 계산하여 사용한다.
            roads.get(a).add(new Road(b, 1, c + Math.max(e - 10, 0) * d));
        }

        // distances[거점번호][거쳐온겨점의수] = 최소 요금
        int[][] distances = new int[n + 1][Math.max(n + 1, r + 1)];
        for (int[] d : distances)
            Arrays.fill(d, Integer.MAX_VALUE);
        Arrays.fill(distances[1], 0);
        // 방문 체크
        boolean[][] visited = new boolean[n + 1][Math.max(n + 1, r + 1)];
        // 다익스트라
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        priorityQueue.offer(new Road(1, 1, 0));
        while (!priorityQueue.isEmpty()) {
            // 현재 위치
            Road current = priorityQueue.poll();
            // 이미 방문한 적이 있다면
            // 건너뛴다.
            if (visited[current.end][current.via] ||
                    distances[current.end][current.via] < current.cost)
                continue;

            // current.end에서 갈 수 있는 거점들 체크
            for (Road next : roads.get(current.end)) {
                // next.end로 갈 때의 비용
                int totalCost = current.cost + next.cost;
                // 아직 방문하지 않았고
                // 최소 요금을 갱신하는 경우에만
                if (!visited[next.end][current.via + 1] &&
                        distances[next.end][current.via + 1] > totalCost) {
                    // 다음 거점은 next.end
                    // 방문한 거점의 수는 current.via + 1이다
                    // 하지만 해당 비용보다 더 크면서, 방문 횟수가 더 많은 경우는 필요없다.
                    // 따라서 방문한 거점의 수를 current.via + 1부터 살펴보며
                    // 더 큰 방문 횟수를 갖는 값들에 대해서도 현재 비용으로 표시해둔다.
                    for (int i = current.via + 1; i < distances[next.end].length; i++) {
                        // 더 적은 값을 만났다면 이후도 그 값이 반영되어있을 것이므로
                        // 반복문 종료
                        if (totalCost > distances[next.end][i])
                            break;
                        distances[next.end][i] = totalCost;
                    }
                    // 우선순위큐에 해당 경우 추가.
                    priorityQueue.offer(new Road(next.end, current.via + 1, distances[next.end][current.via + 1]));
                }
            }
            // 방문 표시
            visited[current.end][current.via] = true;
        }

        // n에 도달하는 경우들 중 가장 적은 비용이며
        // 그러한 경우가 여러개라면 적은 거점 방문 횟수를 갖는 값을 찾는다.
        int via = Integer.MAX_VALUE;
        int distance = Integer.MAX_VALUE;
        boolean found = false;
        for (int i = 1; i < distances[n].length; i++) {
            if (distance > distances[n][i]) {
                found = true;
                distance = distances[n][i];
                via = i;
            }
        }
        
        // 답이 존재할 경우 해당 값
        // 그렇지 않다면 정해둔 문장 출력
        System.out.println(found ? distance + " " + via : "It is not a great way.");
    }
}
