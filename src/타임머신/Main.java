/*
 Author : Ruel
 Problem : Baekjoon 11657번 타임머신
 Problem address : https://www.acmicpc.net/problem/11657
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 타임머신;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Route {
    int start;
    int end;
    int cost;

    public Route(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static final int MAX = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 최소 경로를 구하는 문제이되, 음의 가중치가 있기 때문에 무한한 사이클이 발생할 수 있다
        // 벨만 포드 알고리즘을 이용하여 사이클이 발생하는지 여부를 검출해내야 한다.
        //
        // 벨만 포드 알고리즘 -> n-1회 반복하여 목적지의 값들을 최소값으로 갱신해준다.
        // 여기서 n-1회는 경로의 순서가 차근차근 앞으로 진행하는 형태로 주어졌으리란 보장이 없기 때문이다
        // 최소 n-1회 모든 경로를 살펴보면 모든 경로들이 이전에 갱신되었던 최소 거리에 영향을 받은 거리라는 걸 보장할 수 있다.
        // 그 후 -> n번째 경로를 갱신함으로써, 값의 갱신이 생긴다면 이것은 음의 사이클이 있는 경우이다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < m; i++)
            routes.add(new Route(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt()));

        long[] distance = new long[n];
        Arrays.fill(distance, MAX);         // 초기값은 MAX로 세팅
        distance[0] = 0;                    // 시작점의 거리는 0!

        for (int i = 0; i < n - 1; i++)     // n-1회 돌려주고
            bellmanFord(routes, distance);

        if (bellmanFord(routes, distance))      // n번째 돌렸을 때 값의 갱신이 이루어진다면
            System.out.println(-1);     // 음의 사이클이 존재
        else {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < distance.length; i++) {     // 아닐 경우, 2번 도착점부터 최소거리를 출력
                if (distance[i] == MAX)
                    sb.append(-1).append("\n");
                else
                    sb.append(distance[i]).append("\n");
            }
            System.out.println(sb);
        }
    }

    static boolean bellmanFord(List<Route> routes, long[] distance) {
        boolean changed = false;
        for (Route route : routes) {        // 모든 경로를 살펴본다
            if (distance[route.start] == MAX)       // 시작점이 아직 1지점으로부터 도달하는 경로가 있지 않을 때는 패쓰
                continue;

            else if (distance[route.end] > distance[route.start] + route.cost) {        // 도착점의 최소거리가 갱신될 수 있다면 갱신!
                distance[route.end] = distance[route.start] + route.cost;
                changed = true;
            }
        }
        return changed;
    }
}