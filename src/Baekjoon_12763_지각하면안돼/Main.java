/*
 Author : Ruel
 Problem : Baekjoon 12763번 지각하면 안 돼
 Problem address : https://www.acmicpc.net/problem/12763
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12763_지각하면안돼;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int time;
    int cost;

    public Road(int end, int time, int cost) {
        this.end = end;
        this.time = time;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 건물과 l개의 도로 정보가 주어진다.
        // 도로 정보는 두 건물의 번호, 이동 시간, 택시비로 주어진다.
        // 1번 건물에서 n번 건물까지 t시간 이내에 도착하되, 그 비용이 m이하이며 최소로 하고자할 때
        // 그 비용은?
        //
        // 다익스트라 문제
        // 각 지점에 언제까지 얼마에 도착할 수 있는지를 계산하며 다익스트라를 돌린다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 건물
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 시간 t와 소지금 m
        int t = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 도로 정보
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        int l = Integer.parseInt(br.readLine());
        for (int i = 0; i < l; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(b, time, cost));
            roads.get(b).add(new Road(a, time, cost));
        }
        
        // dp[건물][비용] = 걸리는 시간
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[1][0] = 0;
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        priorityQueue.offer(new Road(1, 0, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 같은 값으로 더 적은 시간에 도착할 수 있다면 건너뛴다.
            if (dp[current.end][current.cost] < current.time)
                continue;
            
            // 다음 이동 장소
            for (Road next : roads.get(current.end)) {
                // 총 이동 비용
                int costSum = current.cost + next.cost;
                // 이동 비용이 m을 넘지 않고,
                // 도달하는 시간이 최소 시간을 갱신한다면
                if (costSum <= m && dp[next.end][costSum] > current.time + next.time) {
                    // costSum보다 더 큰 비용을 지출해서 next.end에 도달하는 경우
                    // current.time + next.time 보다 작아야만 한다.
                    // 값을 비교하고 더 적은 값을 모두 넣어주어, 필요없는 연산을 막자.
                    for (int i = costSum; i < dp[next.end].length; i++)
                        dp[next.end][i] = Math.min(dp[next.end][i], current.time + next.time);
                    // 큐에 추가
                    priorityQueue.offer(new Road(next.end, dp[next.end][costSum], costSum));
                }
            }
        }

        // n 건물에 도달하는 경우들 중 t시간 이내이며
        // 최소인 값을 찾는다.
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < dp[n].length; i++) {
            if (dp[n][i] <= t) {
                answer = i;
                break;
            }
        }
        // 값을 못 찾았다면 불가능하므로 -1
        // 찾았다면 그 값을 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}