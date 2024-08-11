/*
 Author : Ruel
 Problem : Baekjoon 16991번 외판원 순회 3
 Problem address : https://www.acmicpc.net/problem/16991
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16991_외판원순회3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시와 좌표가 주어진다.
        // 외판원이 한 도시에서 출발해, 모든 도시를 거쳐, 출발 도시로 다시 돌아오고자 한다.
        // 두 도시의 거리는 √((xB-xA)^2 + (yB-yA)^2) 와 같다.
        // 이 때 경로의 최소 거리는?
        //
        // 비트마스킹, dp 문제
        // 도시가 최대 16개로 그리 크지 않다.
        // 따라서 비트마스킹을 통해 해당 도시를 방문했는지 여부를 체크해나가며
        // 다음 도시들을 방문한다.
        // 모든 도시를 돌고, 다시 출발 도시로 돌아가는 것이므로
        // 어느 도시에서 시작하나 답은 같다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 도시
        int n = Integer.parseInt(br.readLine());
        
        // 도시 위치
        int[][] cities = new int[n][];
        for (int i = 0; i < cities.length; i++)
            cities[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 각 도시 간의 거리
        double[][] distances = new double[n][n];
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < i; j++)
                distances[i][j] = distances[j][i] = calcDistance(i, j, cities);
        }

        // dp[비트마스킹][마지막방문도시] = 현재까지 경로의 합
        double[][] dp = new double[1 << n][n];
        for (double[] d : dp)
            Arrays.fill(d, Double.MAX_VALUE);

        // 0번 도시에서 시작. 마지막 방문 도시 0
        dp[1][0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1 * n);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int bitmask = current / n;
            int lastCity = current % n;

            for (int i = 1; i < n; i++) {
                // 이미 방문한 도시라면 건너뛴다.
                if ((bitmask & (1 << i)) != 0)
                    continue;

                int newBitmask = bitmask | (1 << i);
                // 다음으로 i번 도시를 방문하는 경우
                if (dp[newBitmask][i] > dp[bitmask][lastCity] + distances[lastCity][i]) {
                    dp[newBitmask][i] = dp[bitmask][lastCity] + distances[lastCity][i];
                    queue.offer(newBitmask * n + i);
                }
            }
        }

        double min = Double.MAX_VALUE;
        // 최종 도착 도시가 i일 경우, i -> 0번 도시로 다시 되돌아간다.
        // 해당 값이 경로의 총 길이가 되고 그 값의 최소값을 구한다.
        for (int i = 1; i < n; i++)
            min = Math.min(min, dp[dp.length - 1][i] + distances[i][0]);
        // 답 출력
        System.out.println(min);
    }

    static double calcDistance(int a, int b, int[][] cities) {
        return Math.sqrt(Math.pow(cities[a][0] - cities[b][0], 2) +
                Math.pow(cities[a][1] - cities[b][1], 2));
    }
}