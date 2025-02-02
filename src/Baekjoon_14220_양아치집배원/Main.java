/*
 Author : Ruel
 Problem : Baekjoon 14220번 양아치 집배원
 Problem address : https://www.acmicpc.net/problem/14220
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14220_양아치집배원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

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
        // n개의 도시와 각 도시들을 연결하는 도로의 정보가 주어진다.
        // 한 집배원은 한 도시에 하나의 배달물을 배달하고선, 다른 도시로 떠난다.
        // 이 과정을 n번 반복하는데, 방문한 도시를 재방문하여도 된다.
        // n번 도시를 방문하는데 걸리는 최소 거리는?
        //
        // DP 문제
        // dp[방문한횟수][도시] = 최소 거리
        // 로 dp를 계산하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 도시
        int n = Integer.parseInt(br.readLine());
        
        // 각 도시를 연결하는 도로
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            roads.add(new ArrayList<>());
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int distance = Integer.parseInt(st.nextToken());
                if (distance != 0)
                    roads.get(i).add(new Road(j, distance));
            }
        }

        // dp[방문한횟수][도시] = 최소 거리
        int[][] dp = new int[n + 1][n];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 어느 도시에서나 처음 시작할 수 있다.
        // 따라서 각 도시의 첫번째 방문의 거리는 0
        for (int i = 0; i < n; i++)
            dp[1][i] = 0;

        // 방문 횟수 i
        for (int i = 1; i < dp.length - 1; i++) {
            // 도시 번호 j
            for (int j = 0; j < dp[i].length; j++) {
                // dp값이 초기값이라면 불가능한 경우이므로 건너뛴다.
                if (dp[i][j] == Integer.MAX_VALUE)
                    continue;

                // 값이 존재한다면 j번 도시에서 갈 수 있는 다른 도시들을 살펴본다.
                for (Road next : roads.get(j))
                    dp[i + 1][next.end] = Math.min(dp[i + 1][next.end], dp[i][j] + next.distance);
            }
        }

        // 총 n번 방문한 경우들 중 최소 거리를 찾는다.
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++)
            answer = Math.min(answer, dp[n][i]);
        // 초기값 그대로라면 불가능한 경우이므로 -1 출력
        // 그 외의 경우 찾은 최소 거리 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}