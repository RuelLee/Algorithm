/*
 Author : Ruel
 Problem : Baekjoon 13911번 집 구하기
 Problem address : https://www.acmicpc.net/problem/13911
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13911_집구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge {
    int end;
    int distance;

    public Edge(int end, int distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    static List<List<Edge>> edges;

    public static void main(String[] args) throws IOException {
        // V개의 정점 E개의 도로개 주어진다
        // 도로는 u v w 형태로 주어지며, u와 v 사이에 거리 w의 도로가 있다는 뜻이다.
        // 그리고 M개의 맥도날드 위치와 맥세권 조건 x가 주어진다
        // S개의 스타벅수 수와 스세권 조건 y가 주어진다
        // 맥세권 + 스세권 조건을 만족하며 상점의 위치가 아닌 정점들 중 두 가게와 거리가 최소인 곳의
        // 두 거리 합을 구하라
        //
        // Dijkstra 문제
        // 맥도날드와 스타벅스 상점들이 주어지므로
        // 맥도날드와 스타벅스 상점들부터 시작하여 각 정점에 도달하는 최소 거리를 구한다.
        // 그 후, 모든 정점을 돌며, 맥세권, 스세권을 만족하며 최소인 거리를 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        // V가 최대 10,000개로 주어지므로 인접 리스트로 간선들을 입력받자.
        edges = new ArrayList<>(V + 1);
        for (int i = 0; i < V + 1; i++)
            edges.add(new ArrayList<>());

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            edges.get(u).add(new Edge(v, w));
            edges.get(v).add(new Edge(u, w));
        }

        // 맥도날드 상점들에 대한 정보와 맥세권 정보.
        st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int[] macs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 맥도날드 상점들로부터 각 정점까지의 최소 거리를 구한다.
        int[] macDistances = dijkstra(macs);

        // 스타벅스 상점들에 대한 정보와 스세권 정보.
        st = new StringTokenizer(br.readLine());
        int S = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int[] stars = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 스타벅스 상점들로부터 각 정점까지의 최소 거리를 구한다.
        int[] starDistances = dijkstra(stars);

        // 맥도날드, 스타벅스 까지 거리의 합 중 최소를 구한다.
        int minDistance = Integer.MAX_VALUE;
        for (int i = 1; i < V + 1; i++) {
            // 맥도날드, 스타벅스 상점이 위치한 지점이 아니면서
            // 맥세권, 스세권을 만족하는 정점들 중
            // 거리 합이 최소인 값을 구한다.
            if (macDistances[i] != 0 && starDistances[i] != 0 &&
                    macDistances[i] <= x && starDistances[i] <= y &&
                    minDistance > macDistances[i] + starDistances[i])
                minDistance = Math.min(minDistance, macDistances[i] + starDistances[i]);
        }

        // minDistance가 초기값이라면 해당 지점이 없는 경우 -1 출력.
        // 값이 있다면 해당 값 출력.
        System.out.println(minDistance == Integer.MAX_VALUE ? -1 : minDistance);
    }

    // 다익스트라.
    static int[] dijkstra(int[] stores) {
        // 각 지점까지의 최소 거리
        int[] minDistances = new int[edges.size()];
        Arrays.fill(minDistances, Integer.MAX_VALUE);

        // 우선순위큐에 상점들의 위치를 모두 담는다.
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.distance));
        for (int i = 0; i < stores.length; i++) {
            priorityQueue.offer(new Edge(stores[i], 0));
            minDistances[stores[i]] = 0;
        }

        // 방문 체크
        boolean[] visited = new boolean[edges.size()];
        while (!priorityQueue.isEmpty()) {
            Edge currentPoint = priorityQueue.poll();
            // minDistnace가 더 적은 값을 갖고 있다면 이미 앞서 crruentPoint에 대한 계산을 했다.
            // 건너뛴다.
            if (minDistances[currentPoint.end] < currentPoint.distance)
                continue;

            // currentPoint에서 갈 수 있는 다음 지점들을 모두 살펴본다.
            for (Edge next : edges.get(currentPoint.end)) {
                // 아직 방문하지 않았고, 최소 거리가 갱신된다면
                // 값을 갱신하고, 우선순위큐에 삽입한다.
                if (!visited[next.end] && minDistances[next.end] > minDistances[currentPoint.end] + next.distance) {
                    minDistances[next.end] = minDistances[currentPoint.end] + next.distance;
                    priorityQueue.offer(new Edge(next.end, minDistances[next.end]));
                }
            }
            // 방문 체크
            visited[currentPoint.end] = true;
        }
        // 최종적으로 stores로 부터 각 지점까지의 최소 거리가 계산된 minDistances 배열을 반환한다.
        return minDistances;
    }
}