/*
 Author : Ruel
 Problem : Baekjoon 20293번 연료가 부족해
 Problem address : https://www.acmicpc.net/problem/20293
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20293_연료가부족해;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // (1, 1) 위치에서 (R, C) 위치로 가되,
        // 가로 혹은 세로로 증가하는 방향으로만 한칸씩 움직일 수 있다.
        // n개 위치에 주유소가 있으며, 각 주유소에 있는 연료의 양은 다르다.
        // 도착지까지 도달하는데, 출발지에서 채워야하는 최소 연료량은?
        //
        // DP, (이분탐색)
        // R과 C는 최대 3000
        // N은 최대 1000으로 주어진다.
        // 따라서 이분탐색을 통해 연료량을 주고, 해당 도착지까지 도달할 수 있는가를 계산해도 된다.
        // 다른 방법으로는 어차피 기점은 주유소를 기점으로 움직이므로
        // 출발지 -> 주유소들 -> 도착지
        // 해당 위치들에 도달하는 최대연료량을 DP를 통해 구해도 된다.
        // 이 때 DP는 dp[위치][충전해야했던최대연료량] = 현재남아있는연료량 으로 세우고 계산했다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 목적지 (R, C)
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        
        // n개의 주유소
        int n = Integer.parseInt(br.readLine());
        int[][] fuels = new int[n + 2][];
        for (int i = 1; i < fuels.length - 1; i++)
            fuels[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 0번은 시작지, n+1번은 도착지
        fuels[0] = new int[]{1, 1, 0};
        fuels[n + 1] = new int[]{R, C, 0};
        
        // dp[위치][최대연료량] = 현재연료량
        int[][] dp = new int[n + 2][R + C + 1];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[0][0] = 0;
        
        // 최대연료량이 낮은 것을 우선적으로 계산
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o % (R + C + 1)));
        queue.offer(0);
        // 도착지에 도달한 최대 연료량의 최소값
        int answer = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 위치
            int loc = current / (R + C + 1);
            // 최대 연료량
            int maxFuel = current % (R + C + 1);
            // 도착지에 도달했다면 해당 연료량을 기록하고 종료
            if (loc == n + 1) {
                answer = maxFuel;
                break;
            }

            // 다음 경유할 장소를 찾는다.
            for (int i = 0; i < fuels.length; i++) {
                // 열과 행 모두 같거나 증가해야하며
                // 같은 장소여서는 안된다.
                if (fuels[i][0] < fuels[loc][0] || fuels[i][1] < fuels[loc][1] ||
                        (fuels[i][0] == fuels[loc][0] && fuels[i][1] == fuels[loc][1]))
                    continue;
                
                // 거리
                int distance = fuels[i][0] - fuels[loc][0] + fuels[i][1] - fuels[loc][1];
                // 최대연료량이 갱신되는지 확인.
                int nextMax = Math.max(maxFuel, dp[loc][maxFuel] + distance);
                // 해당 dp에 기록된 값보다 더 적은 연료소모량으로 도달할 수 있다면 값 갱신
                if (dp[i][nextMax] > dp[loc][maxFuel] + distance - fuels[i][2]) {
                    // nextMax보다 더 많은 최대연료량을 기록했다면, 현재 소모한 연료량이라도 더 적어야한다.
                    // 따라서 nextMax보다 더 큰 값들에 대해 모두 현재 연료량 값과 비교하여 더 작은 값을 넣어둔다.
                    for (int j = nextMax; j < dp[i].length; j++)
                        dp[i][j] = Math.min(dp[i][j], dp[loc][maxFuel] + distance - fuels[i][2]);
                    // 큐 추가
                    queue.offer(i * (R + C + 1) + nextMax);
                }
            }
        }
        // 도착지에 도달한 최대 연료량의 최소값 출력
        System.out.println(answer);
    }
}