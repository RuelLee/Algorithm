/*
 Author : Ruel
 Problem : Baekjoon 2315번 가로등 끄기
 Problem address : https://www.acmicpc.net/problem/2315
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2315_가로등끄기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 가로등이 주어지고, m번 가로등 위치에 마징가가 서 있다.
        // 새벽 5시가 되면, 마징가는 1m/s의 속도로 가로등을 끄고 다니며, 끄는데는 시간이 소모되지 않는다.
        // 가로등의 각 위치와 소모 전력이 주어졌을 때, 낭비되는 전력을 최소로 가로등을 모두 끈다고 했을 때
        // 낭비되는 전력의 양은?
        //
        // DP, 누적합 문제
        // dp를 통해 현재 꺼진 가로등의 범위와 마징가의 위치를 나타내면 된다.
        // dp[꺼진가로등중가장왼쪽][꺼진가로등중가장오른쪽][마징가의위치] = 현재까지 낭비한 전력의 양

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 가로등과 마징가의 초기 위치 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 가로등들의 입력 정보
        int[][] streetLamps = new int[n + 1][2];
        for (int i = 1; i < streetLamps.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < streetLamps[i].length; j++)
                streetLamps[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 소모 전력의 누적합
        long[] psums = new long[n + 1];
        for (int i = 1; i < psums.length; i++)
            psums[i] += psums[i - 1] + streetLamps[i][1];
        
        // dp[꺼진가로등중가장왼쪽][꺼진가로등중가장오른쪽][마징가의위치] = 현재까지 낭비한 전력의 양
        long[][][] dp = new long[n + 1][n + 1][2];
        for (long[][] leftLamp : dp) {
            for (long[] rightLamp : leftLamp)
                Arrays.fill(rightLamp, Long.MAX_VALUE);
        }
        // 마징가의 처음 위치
        Arrays.fill(dp[m][m], 0);

        // 마징가는 다니면서, 왼쪽 혹은 오른쪽 가로등을 추가로 꺼가면서
        // 꺼진 가로등들의 범위를 늘린다.
        for (int diff = 0; diff < n; diff++) {
            // 왼쪽 가로등
            for (int left = 1; left + diff <= n; left++) {
                // 오른쪽 가로등
                int right = left + diff;
                // 0일 경우, 마징가의 위치는 left
                // 1일 경우 마징가의 위치는 right
                for (int direction = 0; direction < dp[left][right].length; direction++) {
                    // 값이 초기값이라면 불가능한 경우이므로 건너뛴다.
                    if (dp[left][right][direction] == Long.MAX_VALUE)
                        continue;

                    // 현재 꺼진 가로등들의 소모 전력 총합.
                    long offLampsTotal = psums[right] - psums[left - 1];
                    // 왼편에 끌 가로등이 남아있는 경우.
                    if (left > 1) {
                        // 해당 가로등까지의 거리
                        int distance = (direction == 0 ? streetLamps[left][0] : streetLamps[right][0]) - streetLamps[left - 1][0];
                        // 해당 가로등까지 가는 동안 소모되는 전략량을 계산하여 dp 값 반영
                        dp[left - 1][right][0] = Math.min(dp[left - 1][right][0], dp[left][right][direction] + (psums[n] - offLampsTotal) * distance);
                    }
                    
                    // 마찬가지로 오른편에 끌 가로등이 남아있는 경우.
                    if (right < n) {
                        int distance = streetLamps[right + 1][0] - (direction == 0 ? streetLamps[left][0] : streetLamps[right][0]);
                        dp[left][right + 1][1] = Math.min(dp[left][right + 1][1], dp[left][right][direction] + (psums[n] - offLampsTotal) * distance);
                    }
                }
            }
        }
        // 모든 가로등을 끄고 난 후
        // 소모된 전력을 왼쪽 가로등에서 끝났을 때와 오른쪽 가로등에서 끝났을 때를 비교하여
        // 더 적은 값을 출력
        System.out.println(Math.min(dp[1][n][0], dp[1][n][1]));
    }
}