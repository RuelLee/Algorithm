/*
 Author : Ruel
 Problem : Baekjoon 12834번 주간 미팅
 Problem address : https://www.acmicpc.net/problem/12834
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12834_주간미팅;

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
    static List<List<Road>> roads;

    public static void main(String[] args) throws IOException {
        // KIST 기사단 팀원의 수 n, 장소의 수 v, 도로의 수 e가 주어진다.
        // 각 기사단은 KIST의 위치A와 씨알푸드 위치B로 이동하며
        // 두 거리의 합을 한 사람의 거리라고 한다. 
        // 만약 이동이 불가능하다면 해당 거리는 -1이라고 생각한다.
        // KIST와 씨알푸드 모두 이동이 불가능하다면 -1 + -1 = -2
        // 모든 사람의 최단 거리 합을 구하라
        //
        // 다익스트라 문제
        // 문제의 설명상 기사단 -> KIST or 씨알푸드라서
        // 각각의 사람들에 대해서 다익스트라를 돌려서는 안된다.
        // KIST와 씨알푸드에서 시작하는 거리를 계산하고
        // 각 사람들의 출발지에서의 거리를 더해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 기사단 팀원의 수 n, 장소의 수 v, 도로의 수 e
        int n = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // KIST의 위치
        int A = Integer.parseInt(st.nextToken());
        // 씨알푸드의 위치
        int B = Integer.parseInt(st.nextToken());
        
        // 기사단의 출발 장소
        int[] homes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 인접 리스트로 도로에 대한 정보를 저장
        roads = new ArrayList<>();
        for (int i = 0; i < v + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, l));
            roads.get(b).add(new Road(a, l));
        }
        
        // KIST부터 각 장소에 이르는 최소 거리
        int[] distancesFromKIST = dijkstra(A);
        // 씨알푸드부터 각 장소에 이르는 최소 거리 
        int[] distancesFromFood = dijkstra(B);

        int sum = 0;
        // 각 기사단의 최단 거리의 합 계산.
        for (int home : homes) {
            sum += distancesFromKIST[home] == Integer.MAX_VALUE ? -1 : distancesFromKIST[home];
            sum += distancesFromFood[home] == Integer.MAX_VALUE ? -1 : distancesFromFood[home];
        }
        
        // 답안 출력
        System.out.println(sum);
    }

    static int[] dijkstra(int start) {
        // 각 장소의 최소 거리
        int[] minDistances = new int[roads.size()];
        // 초기값으로 큰 값
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        // 시작점에 대해서는 거리가 0
        minDistances[start] = 0;
        // 방문체크
        boolean[] visited = new boolean[roads.size()];
        
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.distance));
        priorityQueue.offer(new Road(start, 0));
        while (!priorityQueue.isEmpty()) {
            // 현재 위치
            Road current = priorityQueue.poll();
            // 방문체크가 되어있다면 건너뛴다.
            if (visited[current.end])
                continue;

            // 현재 위치에서 도달가능한 지점들을 살펴보고
            // 최소거리를 갱신하는지 확인한다
            for (Road next : roads.get(current.end)) {
                if (!visited[next.end] && minDistances[next.end] > minDistances[current.end] + next.distance) {
                    minDistances[next.end] = minDistances[current.end] + next.distance;
                    priorityQueue.offer(new Road(next.end, minDistances[next.end]));
                }
            }
            // 방문 체크
            visited[current.end] = true;
        }
        // 계산된 최소 거리 배열을 반환한다.
        return minDistances;
    }
}