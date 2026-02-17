/*
 Author : Ruel
 Problem : Baekjoon 14431번 소수마을
 Problem address : https://www.acmicpc.net/problem/14431
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14431_소수마을;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] towns;

    public static void main(String[] args) throws IOException {
        // 소수 마을의 위치 x1, y1과 a마을의 위치 x2, y2가 주어진다.
        // 경유할 수 있는 마을 n개가 주어진다.
        // 단 현재 도시로부터, 다음 도시까지의 거리 중 정수 부분이 소수인 경우에만 이동할 수 있다.
        // a마을에 도달하기 위해 이동해야하는 총 누적 거리는 얼마인가
        // 거리의 정수부분만 누적한다.
        //
        // 에라토스테네스의 체, dijkstra 문제
        // 먼저 좌표의 범위가 -3000 ~ +3000이므로 가장 먼 최대 거리를 따지면
        // (-3000, -3000) ~ (3000, 3000)이고 이 거리는 sqrt(6000^2 + 6000^2) 제곱이며 근사값으로 약 8486이 나온다.
        // 해당 값까지의 소수들을 에라토스테네스의 체로 구한다.
        // 그 후, 각 도시 간의 거리를 구한다.
        // 그리고 dijkstra 를 통해 a마을까지 가는 최소 거리 누적합을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 소수 마을과 a마을의 위치
        int x1 = Integer.parseInt(st.nextToken());
        int y1 = Integer.parseInt(st.nextToken());
        int x2 = Integer.parseInt(st.nextToken());
        int y2 = Integer.parseInt(st.nextToken());

        // 다른 n개의 마을
        int n = Integer.parseInt(br.readLine());
        towns = new int[n + 2][2];
        towns[0][0] = x1;
        towns[0][1] = y1;
        towns[n + 1][0] = x2;
        towns[n + 1][1] = y2;
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            towns[i][0] = Integer.parseInt(st.nextToken());
            towns[i][1] = Integer.parseInt(st.nextToken());
        }

        // 각 도시 간의 거리
        int[][] distances = new int[n + 2][n + 2];
        for (int i = 0; i < distances.length; i++) {
            for (int j = i + 1; j < distances.length; j++)
                distances[i][j] = distances[j][i] = distance(i, j);
        }

        // 소수 판정
        boolean[] notPrimeNums = new boolean[8487];
        notPrimeNums[0] = notPrimeNums[1] = true;
        for (int i = 2; i < notPrimeNums.length; i++) {
            if (notPrimeNums[i])
                continue;

            for (int j = 2; i * j < notPrimeNums.length; j++)
                notPrimeNums[i * j] = true;
        }

        // dijkstra
        int[] dp = new int[n + 2];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> dp[o]));
        priorityQueue.offer(0);

        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();

            for (int i = 1; i < dp.length; i++) {
                if (!notPrimeNums[distances[current][i]] && dp[i] > dp[current] + distances[current][i]) {
                    dp[i] = dp[current] + distances[current][i];
                    priorityQueue.offer(i);
                }
            }
        }
        // 구한 값이 초기값이라면 불가능한 경우이므로 -1을 출력
        // 그 외의 경우 거리 값을 출력
        System.out.println(dp[n + 1] == Integer.MAX_VALUE ? -1 : dp[n + 1]);
    }

    // a와 b 마을 사이의 거리
    static int distance(int a, int b) {
        int xDiff = Math.abs(towns[a][0] - towns[b][0]);
        int yDiff = Math.abs(towns[a][1] - towns[b][1]);

        return (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}