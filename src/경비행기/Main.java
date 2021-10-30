/*
 Author : Ruel
 Problem : Baekjoon 2585번 경비행기
 Problem address : https://www.acmicpc.net/problem/2585
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 경비행기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Flight {
    int station;
    int k;

    public Flight(int station, int k) {
        this.station = station;
        this.k = k;
    }
}

public class Main {
    static int[][] stations;

    public static void main(String[] args) throws IOException {
        // 이분탐색을 활용한 문제
        // 이분매칭 문제는 가지수가 너무 많이 생길 거 같은 경우 활용하면 좋은 것 같다.
        // 연료통의 값을 정해두고, 해당 연료통으로 k번 안에 목적지로 도달할 수 있는지를 반복적으로 체크한다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        stations = new int[n + 2][2];
        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < stations[i].length; j++)
                stations[i][j] = Integer.parseInt(st.nextToken());
        }
        stations[0][0] = stations[0][1] = 0;        // 시작 위치는 0,0
        stations[n + 1][0] = stations[n + 1][1] = 10000;        // 도착 위치는 10000, 10000

        int start = 0;
        int end = calcFuelGauge(0, stations.length - 1);        // 최대 연료통의 크기는 s -> t로 바로 갈 경우.
        while (start < end) {       // 이분매칭으로 범위를 줄여가며 찾는다.
            int middle = (start + end) / 2;
            if (canArrive(middle, k))
                end = middle;
            else
                start = middle + 1;
        }
        System.out.println(start);
    }

    static boolean canArrive(int fuelGauge, int k) {
        Queue<Flight> queue = new LinkedList<>();
        queue.offer(new Flight(0, 0));      // 시작점

        boolean[] visited = new boolean[stations.length];       // 방문 체크. queue이므로 k가 점차 증가하는 값이 들어온다. 따라서 먼저 도착한 적이 있다면 그것이 최소 k로 해당 위치에 도착한 경우.
        visited[0] = true;
        while (!queue.isEmpty()) {
            Flight current = queue.poll();
            if (current.station == stations.length - 1)     // 도착지에 도달했다면 true 반환
                return true;

            for (int i = 0; i < stations.length; i++) {
                if (current.station == i || visited[i])     // 자기 자신에게 가는 경우와 이미 방문한 station은 패쓰
                    continue;
                
                // i station으로 가는데 필요 연료통의 크기가 fuelGauge보다 같거나 작고,
                // 경유할 수 있는 회수가 남아있거나, k번 경유한 상태에서 도착지에 도달할 수 있다면
                if (fuelGauge >= calcFuelGauge(current.station, i) &&
                        (current.k < k || (current.k == k && i == stations.length - 1))) {
                    queue.offer(new Flight(i, current.k + 1));      // queue에 삽입
                    visited[i] = true;      // 방문체크
                }
            }
        }
        return false;
    }

    static int calcFuelGauge(int a, int b) {        // a번 station과 b번 station의 거리를 계산하여 필요한 연료통의 크기를 계산한다.
        double distance = Math.sqrt(Math.pow(Math.abs(stations[b][0] - stations[a][0]), 2) +
                Math.pow(Math.abs(stations[b][1] - stations[a][1]), 2));
        return (int) Math.ceil(distance / 10);
    }
}