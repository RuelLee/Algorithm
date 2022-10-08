/*
 Author : Ruel
 Problem : Baekjoon 17835번 면접보는 승범이네
 Problem address : https://www.acmicpc.net/problem/17835
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17835_면접보는승범이네;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    long distance;

    public Road(int end, long distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    static List<List<Road>> roads;

    public static void main(String[] args) throws IOException {
        // n개의 도시, m개의 도로, k개의 면접장이 주어진다.
        // 도로는 u v c의 형태로 주어지며 u -> v로 가는데 c거리의 도로가 있다는 뜻이다.(단방향)
        // 이 때 면접장에서 가장 먼 도시를 구하고(복수의 도시라면 가장 번호가 이른), 도시 번호와 그 거리를 출력하라.
        //
        // 다익스트라 문제
        // 인데 아주 살짝 꼬았다.
        // 면접장에서 가장 먼 거리를 구해야한다.
        // 따라서 시작점을 면접장들로 잡고, 각 도시에 이르는 최단거리를 구해야하는데
        // 도로가 단방향이라서 각 도시에서 다른 도시로 연결된 도로를 살펴보는 것이 아니라
        // 각 도시로 들어오는 도로로 살펴봐야한다.
        // 이 점만 빼면 평범한 다익스트라.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 도로들
        roads = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 면접장에서 가장 먼 거리의 도시를 구할 것이기 때문에
            // 각 도시로 들어오는 도로들을 저장해둬야한다.
            roads.get(v).add(new Road(u, c));
        }

        // 각 도시에 이르는 최단거리.
        // n과 c가 최대 10만이므로, int 범위를 넘을 수 있다.
        long[] minDistances = new long[n + 1];
        Arrays.fill(minDistances, Long.MAX_VALUE);
        int[] interviewCities = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 도시에 이르는 거리가 짧은 순서대로 살펴본다.
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(r -> r.distance));
        for (int ic : interviewCities) {
            minDistances[ic] = 0;
            priorityQueue.offer(new Road(ic, 0));
        }

        // 방문 체크.
        boolean[] visited = new boolean[n + 1];
        // 우선순위큐가 빌 때까지.
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 이미 계산된 도시라면 건너뛰기.
            if (current.distance > minDistances[current.end])
                continue;

            // 들어오는 도로들을 살펴본다.
            for (Road next : roads.get(current.end)) {
                // 아직 미방문이고, next 도로로 최단 거리가 갱신된다면
                if (!visited[next.end] && minDistances[next.end] > current.distance + next.distance) {
                    // 거리 갱신
                    minDistances[next.end] = current.distance + next.distance;
                    // 우선순위큐 삽입.
                    priorityQueue.offer(new Road(next.end, minDistances[next.end]));
                }
            }
            // 방문 체크
            visited[current.end] = true;
        }

        // 가장 먼 도시와
        int farthestCity = 0;
        // 그 거리를 구한다.
        long farthestDistance = 0;
        for (int i = 1; i < minDistances.length; i++) {
            if (minDistances[i] > farthestDistance) {
                farthestDistance = minDistances[i];
                farthestCity = i;
            }
        }
        // 값 출력.
        System.out.println(farthestCity);
        System.out.println(farthestDistance);
    }
}