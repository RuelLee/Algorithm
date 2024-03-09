/*
 Author : Ruel
 Problem : Baekjoon 14221번 편의점
 Problem address : https://www.acmicpc.net/problem/14221
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14221_편의점;

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
        // n개의 정점, m개의 도로가 주어진다.
        // p개의 집 후보지와, q개의 편의점이 주어진다.
        // 편의점으로부터 거리가 가장 가까운 집 후보지 중 가장 번호가 적은 후보지를 출력하라
        //
        // 다익스트라 문제
        // 편의점 여러개를 그냥 동시에 우선순위큐에 담아 시작하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, m개의 간선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        // 간선들
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, c));
            roads.get(b).add(new Road(a, c));
        }

        // p개의 집 후보지, q개의 편의점
        st = new StringTokenizer(br.readLine());
        int p = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        HashSet<Integer> homes = new HashSet<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < p; i++)
            homes.add(Integer.parseInt(st.nextToken()));
        HashSet<Integer> stores = new HashSet<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < q; i++)
            stores.add(Integer.parseInt(st.nextToken()));

        // 편의점부터의 거리들
        int[] distances = new int[n + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));
        for (int store : stores) {
            // 편의점의 거리는 0
            distances[store] = 0;
            // 우선순위큐에 편의점 위치를 담는다.
            priorityQueue.offer(new Road(store, 0));
        }
        
        // 다익스트라
        while (!priorityQueue.isEmpty()) {
            // 현재 정점
            Road current = priorityQueue.poll();
            if (distances[current.end] < current.distance)
                continue;

            // 현재 정점에서 갈 수 있는 다음 정점들을 살펴본다.
            for (Road next : roads.get(current.end)) {
                if (distances[next.end] > distances[current.end] + next.distance) {
                    distances[next.end] = distances[current.end] + next.distance;
                    priorityQueue.offer(new Road(next.end, distances[next.end]));
                }
            }
        }

        // 거리가 작은 집 후보지들 중 가장 번호가 낮은 집 후보지를 찾는다.
        int minIdx = 0;
        for (int home : homes) {
            if (distances[minIdx] > distances[home] ||
                    (distances[minIdx] == distances[home] && minIdx > home))
                minIdx = home;
        }
        // 답안 출력
        System.out.println(minIdx);
    }
}