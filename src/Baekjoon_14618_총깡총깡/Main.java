/*
 Author : Ruel
 Problem : Baekjoon 14618번 총깡 총깡
 Problem address : https://www.acmicpc.net/problem/14618
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14618_총깡총깡;

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
        // n개의 집과 m개의 도로가 주어진다.
        // 집에는 A형과 B형 두 종류가 있다.
        // 주인공은 서로 다른 타입의 집에 총깡총깡 뛰는 동물과 짝폴짝폴 뛰는 동물을 맡기고자한다.
        // 이 중 더 가까운 집에 총깡총깡 뛰는 동물을 맡기고자할 때
        // 해당 동물을 맡기는 집의 타입과 거리는?
        // A, B 둘 다 상관없는 경우는 A형에 맡기기로 한다.
        //
        // 다익스트라 문제
        // 간단한 다익스트라문제
        // 주인공 집에서부터 다익스트라를 돌려 각 집에 이르는 최소거리를 찾고
        // 각각 타입의 집들 중 최소 거리를 찾는다
        // 그 후, 더 거리가 이른 타입에 총깡총깡 뛰는 동물을 맡기므로
        // 해당 집의 타입과 거리를 출력한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 집, m개의 도로, 주인공의 집 j, 각각 타입의 집의 개수 k
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int j = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        
        // A형, B형 집
        int[] aHomes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] bHomes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 간선들
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            roads.get(x).add(new Road(y, z));
            roads.get(y).add(new Road(x, z));
        }
        
        // 최소거리
        int[] distances = new int[n + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        // 주인공의 집
        distances[j] = 0;
        
        // 다익스트라
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));
        priorityQueue.offer(new Road(j, 0));
        boolean[] visited = new boolean[n + 1];
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            if (distances[current.end] < current.distance)
                continue;

            for (Road next : roads.get(current.end)) {
                if (!visited[next.end] && distances[next.end] > distances[current.end] + next.distance) {
                    distances[next.end] = distances[current.end] + next.distance;
                    priorityQueue.offer(new Road(next.end, distances[next.end]));
                }
            }
            visited[current.end] = true;
        }
        
        // A형 집들 중 최소 거리
        int aMin = Integer.MAX_VALUE;
        for (int a : aHomes)
            aMin = Math.min(aMin, distances[a]);
        // B형 집들 중 최소 거리
        int bMin = Integer.MAX_VALUE;
        for (int b : bHomes)
            bMin = Math.min(bMin, distances[b]);
        
        // 둘 다 초기값이라면 어느 타입의 집도 연결되어있지 않은 경우
        // -1 출력
        if (aMin == Integer.MAX_VALUE && bMin == Integer.MAX_VALUE)
            System.out.println(-1);
        else if (aMin <= bMin) {    // A형 타입의 집이 더 가까운 경우
            System.out.println("A");
            System.out.println(aMin);
        } else {        // B형 타입의 집이 더 가까운 경우
            System.out.println("B");
            System.out.println(bMin);
        }
    }
}