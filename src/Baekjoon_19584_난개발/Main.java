/*
 Author : Ruel
 Problem : Baekjoon 19584번 난개발
 Problem address : https://www.acmicpc.net/problem/19584
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19584_난개발;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Connection {
    int start;
    int end;
    int traffic;

    public Connection(int start, int end, int traffic) {
        this.start = start;
        this.end = end;
        this.traffic = traffic;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 버스 정류장 좌표와 이를 잇는 m개의 도로 정보가 주어진다.
        // x와 평행한 선을 이와 만나는 도로들의 통행량 총합을 최대로 하고자할 때 그 값은?
        //
        // 스위핑 문제
        // 정류장과 도로의 x좌표는 중요하지 않다.
        // y좌표를 가지고서 최대한 많은 도로가 중첩되는 구간을 찾아 그 때의 통행량을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정류장, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 정류장의 위치
        int[][] points = new int[n + 1][0];
        for (int i = 1; i < points.length; i++)
            points[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 최소힙 우선순위큐를 통해
        // 도로에 연결된 두 정류장 중 더 낮은 y좌표를 기준으로 우선적으로 처리한다.
        PriorityQueue<Connection> asc = new PriorityQueue<>(Comparator.comparingInt(o -> o.start));
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int p1 = Integer.parseInt(st.nextToken());
            int p2 = Integer.parseInt(st.nextToken());
            int traffic = Integer.parseInt(st.nextToken());
            asc.offer(new Connection(Math.min(points[p1][1], points[p2][1]), Math.max(points[p1][1], points[p2][1]), traffic));
        }

        // 현재 범위에 들어있는 도로들.
        PriorityQueue<Connection> inRange = new PriorityQueue<>(Comparator.comparingInt(o -> o.end));
        // 최대 통행량
        long maxTraffic = 0;
        // 현재 통행량
        long currentTraffic = 0;
        while (!asc.isEmpty()) {
            // currnet 도로 차례.
            Connection current = asc.poll();
            // inRange에 들어있는 도로들 중 최대 y값이
            // current의 최소 y값보다 작은 도로들은 모두 제거한다.
            while (!inRange.isEmpty() && inRange.peek().end < current.start)
                currentTraffic -= inRange.poll().traffic;
            // current 도로의 통행량 추가
            currentTraffic += current.traffic;
            // 최대값 갱신 여부 확인
            maxTraffic = Math.max(maxTraffic, currentTraffic);
            // inRange에 currnet 추가
            inRange.offer(current);
        }

        // 최대 통행량 출력.
        System.out.println(maxTraffic);
    }
}