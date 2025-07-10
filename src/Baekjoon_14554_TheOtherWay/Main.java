/*
 Author : Ruel
 Problem : Baekjoon 14554번 The Other Way
 Problem address : https://www.acmicpc.net/problem/14554
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14554_TheOtherWay;

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
    static final int LIMIT = 1000000000 + 9;

    public static void main(String[] args) throws IOException {
        // n개의 마을과 m개의 도로가 주어진다.
        // 도로는 두 마을을 잇는 양방향이며, 거리가 정해져있다.
        // s 마을에서 출발하여, e 마을에 가고자할 때, 최단 경로로 가는 경우의 수를 구하라
        //
        // dijkstra, dp 문제
        // s에서 출발하여, 다른 지점에 이르는 최단 경로를 찾으며,
        // 같은 거리일 경우, 해당 지점에 이르는 경로의 수를 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 마을, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 출발지 s, 도착지 e
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        // 도로들
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, c));
            roads.get(b).add(new Road(a, c));
        }
        
        // dp[i][0] = i에 이르는 최단 경로의 길이
        // dp[i][1] = i에 이르는 최단 경로의 개수
        long[][] dp = new long[n + 1][2];
        for (long[] d : dp)
            Arrays.fill(d, Long.MAX_VALUE);
        // 출발지에서의 거리는 0, 경우의 수는 1
        dp[s][0] = 0;
        dp[s][1] = 1;

        PriorityQueue<Road> priorityQueue = new PriorityQueue<>((Comparator.comparingLong(o -> o.distance)));
        priorityQueue.offer(new Road(s, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 도착지에서는 더 이상 다른 도시로 갈 필요가 없다.
            // 반복문 종료
            if (current.end == e)
                break;
            // 이미 더 적은 거리로 current.end 마을에 도달할 수 있는 경우
            // 이미 해당 경우로 계산되었으므로 건너뛴다.
            else if (dp[current.end][0] < current.distance)
                continue;
            
            // current.end에서 갈 수 있는 다른 마을들
            for (Road r : roads.get(current.end)) {
                // 그 때의 거리
                long sum = r.distance + current.distance;

                // 최소 거리를 갱신한다면
                // r.end에 이르는 최단 경로의 길이 변경
                // current.end에 이르는 경로의 수만큼 r.end에 최단 경로로 도달할 수 있다.
                if (dp[r.end][0] > sum) {
                    dp[r.end][0] = sum;
                    dp[r.end][1] = dp[current.end][1];
                    priorityQueue.offer(new Road(r.end, sum));
                } else if (dp[r.end][0] == sum)     // 거리가 같은 경우, 경우의 수를 누적
                    dp[r.end][1] = (dp[r.end][1] + dp[current.end][1]) % LIMIT;
            }
        }
        // e에 이르는 최단 경로의 수가 초기값이라면 불가능한 경우이므로 0 출력
        // 그 외의 경우 해당하는 최단 경로의 수 출력
        System.out.println(dp[e][1] != Long.MAX_VALUE ? dp[e][1] : 0);
    }
}