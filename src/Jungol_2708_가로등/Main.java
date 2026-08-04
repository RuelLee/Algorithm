/*
 Author : Ruel
 Problem : Jungol 2708번 가로등
 Problem address : https://jungol.co.kr/problem/2708
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2708_가로등;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<int[]>> roads;

    public static void main(String[] args) throws IOException {
        // m개의 교차로가 n개의 도로로 이어져있다.
        // 도로는 1미터랑 1비트코인의 비용을 들여 가로수를 밝힌다.
        // 이중 몇 개의 교차로를 폐쇄해 비용을 절감하고자한다.
        // 임의의 교차로에서 임의의 교차로로 항상 갈 수 있어야한다.
        // 절약하는 비용은?
        //
        // 최소 스패닝 트리 문제
        // 정점들을 최소 간선으로 잇는 유명한 문제
        // 분리 집합을 사용하는 kruskal과
        // 임의의 한 점부터 시작하여 연결된 정점들과 연결되지 않은 정점을 잇는 최소 비용 도로부터 건설하는 prim 알고리즘이 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // m개의 정점, n개의 도로
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        roads = new ArrayList<>();
        for (int i = 0; i < m; i++)
            roads.add(new ArrayList<>());

        // 처음 가로등 비용
        int total = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            total += d;

            roads.get(s).add(new int[]{e, d});
            roads.get(e).add(new int[]{s, d});
        }

        // 연결된 정점
        boolean[] connected = new boolean[m];
        // 0번부터 시작
        connected[0] = true;
        int sum = 0;
        // 연결된 정점들에 존재하는 도로들을 우선순위큐에 담아 비용 오름차순으로 살펴본다.
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        // 0번 교차로에 연결된 도로들
        for (int[] r : roads.get(0))
            priorityQueue.offer(r);

        while (!priorityQueue.isEmpty()) {
            // 이번 도로
            int[] current = priorityQueue.poll();
            // 이미 연결되어있다면 건너뛰고
            if (connected[current[0]])
                continue;

            // 아니라면 비용 추가
            sum += current[1];
            // 연결 여부 체크
            connected[current[0]] = true;
            // 새롭게 연결된 교차로에 연결된 도로들을 우선순위큐에 추가
            for (int[] r : roads.get(current[0]))
                priorityQueue.offer(r);
        }
        // 답 출력
        System.out.println(total - sum);
    }
}