/*
 Author : Ruel
 Problem : Baekjoon 15971번 두 로봇
 Problem address : https://www.acmicpc.net/problem/15971
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15971_두로봇;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Route {
    int end;
    int distance;

    public Route(int end, int distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 방과 n - 1개의 통로가 존재하며, 임의의 두 방 사이를 이동할 떄 같은 통로를
        // 두 번 이상 지나지 않는 경로는 유일하다.
        // 두 로봇을 통해 방들을 탐색하며, 일정 시간 이후에는 두 로봇은 서로 통신을 해야한다
        // 이 때 통신을 하기 위해서는 인접한 방에 두 로봇이 위치해야한다.
        // 두 로봇의 위치가 주어질 때, 통신을 하기 위해 두 로봇이 이동해야하는 거리 합은?
        //
        // 그래프 탐색 문제
        // 한 로봇으로 다른 한 로봇까지 거리를 모두 구한 뒤
        // 전체 경로에서 지나왔던 통로들 중 가장 거리가 긴 통로를 제외한 값을 답으로 한다.
        // 가장 긴 통로 양쪽 방에서 통신하면 되기 때문.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 방과 로봇의 위치
        int n = Integer.parseInt(st.nextToken());
        int robotA = Integer.parseInt(st.nextToken());
        int robotB = Integer.parseInt(st.nextToken());

        // 통로들
        List<List<Route>> routes = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            routes.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            routes.get(a).add(new Route(b, c));
            routes.get(b).add(new Route(a, c));
        }

        // a로봇으로부터 시작한다.
        // a로봇으로부터 각 방에 도달하는 최소 거리.
        int[] minDistances = new int[n + 1];
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        minDistances[robotA] = 0;
        // 해당 방에 도달할 때까지 거쳐온 통로들 중 가장 긴 길이
        int[] longestRoute = new int[n + 1];

        // BFS
        Queue<Integer> queue = new LinkedList<>();
        // robotA의 위치에서 시작
        queue.offer(robotA);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 현재 방에 연결된 통로들을 살펴본다.
            for (Route next : routes.get(current)) {
                // 다음 방에 이르는 거리가 current를 경유해 가는 것이 더 짧다면
                if (minDistances[next.end] > minDistances[current] + next.distance) {
                    // 최소 거리 갱신
                    minDistances[next.end] = minDistances[current] + next.distance;
                    // current까지의 최대 통로와 이번 통로를 비교하여, 다음 방에 이르는 최대 통로 길이 기록.
                    longestRoute[next.end] = Math.max(longestRoute[current], next.distance);
                    // 큐 삽입.
                    queue.offer(next.end);
                }
            }
        }
        // robotB에 이르는 전체 경로 중 최대 통로 길이를 제한 값
        // 두 로봇이 통신하기 위해 이동해야하는 최소 거리 출력.
        System.out.println(minDistances[robotB] - longestRoute[robotB]);
    }
}