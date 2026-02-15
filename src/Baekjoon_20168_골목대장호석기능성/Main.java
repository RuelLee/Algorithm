/*
 Author : Ruel
 Problem : Baekjoon 20168번 골목 대장 호석 - 기능성
 Problem address : https://www.acmicpc.net/problem/20168
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20168_골목대장호석기능성;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 교차로, m개의 도로가 주어진다.
        // a에서 시작하여 b 교차로에 가고 싶다.
        // 도로마다 통행료가 있으며, 현재 소지금은 c이다.
        // 한 도로의 최대 요금을 최소화하고자 할 때, 그 값은?
        //
        // dijkstra 문제
        // 각 교차로에 도달하는 전체 통행료 별로 단일 구간에서 낸 최대요금을 계산하는 방식으로
        // dijkstra를 돌려주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 교차로, m개의 도로, 시작점 a, 도착점 b, 소지금 c
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 도로 정보
        List<List<int[]>> roads = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            roads.get(u).add(new int[]{v, x});
            roads.get(v).add(new int[]{u, x});
        }

        // dp[위치][사용금액] = 단일 구간 최대 요금
        int[][] dp = new int[n + 1][c + 1];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[a][0] = 0;

        // dijkstra
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> dp[o[0]][o[1]]));
        priorityQueue.offer(new int[]{a, 0});
        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();

            for (int[] r : roads.get(current[0])) {
                // 소지금을 넘지 않고,
                // 단일 최대 통행료 최소값을 갱신하는 경우
                if (current[1] + r[1] <= c &&
                        dp[r[0]][current[1] + r[1]] > Math.max(dp[current[0]][current[1]], r[1])) {
                    dp[r[0]][current[1] + r[1]] = Math.max(dp[current[0]][current[1]], r[1]);
                    priorityQueue.offer(new int[]{r[0], current[1] + r[1]});
                }
            }
        }

        // 전체 통행료에 상관없이 단일 최대 통행료가 최소인 값을 찾는다.
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i <= c; i++)
            answer = Math.min(answer, dp[b][i]);
        // 없다면 -1, 가능하다면 찾은 값 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}