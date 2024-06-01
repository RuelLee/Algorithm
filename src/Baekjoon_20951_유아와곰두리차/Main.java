/*
 Author : Ruel
 Problem : Baekjoon 20951번 유아와 곰두리차
 Problem address : https://www.acmicpc.net/problem/20951
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20951_유아와곰두리차;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int LIMIT = 1_000_000_000 + 7;

    public static void main(String[] args) throws IOException {
        // n개의 정점, m개의 도로가 주어진다.
        // 동일 정점, 동일 간선 재방문을 허용할 때
        // 길이 7인 경로의 개수는 몇 개인가?
        // 10^9 + 7로 나눈 나머지를 출력한다.
        //
        // DP, BFS 문제
        // 특별한 출발점이 없이, 경로의 길이가 7인 것들의 개수이므로
        // 모든 정점에서 출발하여
        // 총 경로의 길이가 7인 경우들을 모두 계산한다.
        // dp[정점][경로]로 dp를 세우고,
        // 동일한 정점과 경로의 길이를 가질 경우, dp를 통해 한번에 계산할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 정점과 도로의 개수 n, m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 도로의 정점 연결 정보
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            connections.get(u).add(v);
            connections.get(v).add(u);
        }

        // dp
        int[][] dp = new int[n + 1][8];
        Queue<Integer> queue = new LinkedList<>();
        boolean[][] enqueued = new boolean[n + 1][8];
        // 각 정점에서 출발하는 경우 1가지
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
            queue.offer(i * 8 + 0);
        }
        // BFS
        while (!queue.isEmpty()) {
            // 현재 위치와 거리
            int currentLoc = queue.peek() / 8;
            int distance = queue.poll() % 8;

            for (int next : connections.get(currentLoc)) {
                // 다음 정점으로 이동
                dp[next][distance + 1] += dp[currentLoc][distance];
                dp[next][distance + 1] %= LIMIT;
                // 다음 정점과 경로 길이에 대해
                // 아직 큐에 들어가지 않았고, 거리가 7 미만이라면
                // 큐에 담아 경로를 계속 진행한다.
                if (!enqueued[next][distance + 1] && distance + 1 < 7) {
                    queue.offer(next * 8 + distance + 1);
                    enqueued[next][distance + 1] = true;
                }
            }
        }

        // 경로가 7인 경우의 수를 모두 더한다.
        int answer = 0;
        for (int i = 1; i < dp.length; i++) {
            answer += dp[i][7];
            answer %= LIMIT;
        }
        // 답안 출력
        System.out.println(answer);
    }
}