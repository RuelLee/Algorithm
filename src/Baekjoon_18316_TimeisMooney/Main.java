/*
 Author : Ruel
 Problem : Baekjoon 18316번 Time is Mooney
 Problem address : https://www.acmicpc.net/problem/18316
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18316_TimeisMooney;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // N개의 도시, M개의 단방향 도로가 주어진다.
        // 1번 도시에 시작하여 도로가 연결된 다른 도시를 하루에 한 번 방문할 수 있고, 총 여행 기간 T에 따른 C * T^2의 경비가 소모된다.
        // i번 도시를 방문할 때마다 mi에 해당하는 금액을 받는다.
        // 1번 도시의 m1은 0이다.
        // 마지막 날에는 1번 도시로 돌아오가자 한다.
        // 여행을 통해 최대 많은 금액을 만들고자한다면, 얼마를 만들 수 있는가
        //
        // DP 문제
        // mi가 최대 1000으로 주어진다.
        // T일 동안 얻을 수 있는 최대 금액은 1000 * (T - 1)이다.
        // 이 금액이 여행 기간의 경비인 C * T^2 보다 커야한다.
        // 따라서 최대 여행 날짜는 (T - 1)이 T와 하므로 T로 보고
        // 양변을 C * T로 나눠, 1000 / C > T
        // 최대 1000 / C일 까지만 살펴보면 된다.
        // dp[도시][현재여행일] = 얻은 최대 금액
        // 으로 BFS 탐색을 하여 구하고
        // 마지막 날에 1번 도시에 도착한 경우에서 C * T^2을 빼준 값들의 최댓값을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // N개의 도시, M개의 단방향 도로, 여행 경비 상수 C
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        // 각 도시를 방문할 때 얻는 금액
        int[] ms = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < ms.length; i++)
            ms[i] = Integer.parseInt(st.nextToken());

        // 도로 정보
        List<List<Integer>> roads = new ArrayList<>();
        for (int i = 0; i < N + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            roads.get(a).add(b);
        }

        // 최대 여행일
        int maxDay = 1000 / C + 1;
        int[][] dp = new int[N + 1][maxDay];
        // BFS 탐색
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(maxDay);
        while (!queue.isEmpty()) {
            // 현재 값
            int current = queue.poll();
            // 현재 도시와 현재 여행일
            int city = current / maxDay;
            int day = current % maxDay;
            // 여행 마지막 날이라면 건너뛴다.
            if (day == maxDay - 1)
                continue;

            // 다음 방문할 수 있는 도시와 금액 비교
            for (int next : roads.get(city)) {
                if (dp[next][day + 1] < dp[city][day] + ms[next - 1]) {
                    dp[next][day + 1] = dp[city][day] + ms[next - 1];
                    queue.offer(next * maxDay + day + 1);
                }
            }
        }

        // 다시 1번 도시로 돌아온 경우들 중
        // 얻은 금액과 여행 경비를 비교하여 얻은 금액의 최댓값을 구한다.
        int max = 0;
        for (int i = 2; i < dp[1].length; i++)
            max = Math.max(max, dp[1][i] - C * i * i);
        // 답 출력
        System.out.println(max);
    }
}