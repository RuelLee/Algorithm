/*
 Author : Ruel
 Problem : Baekjoon 29756번 DDR 체력 관리
 Problem address : https://www.acmicpc.net/problem/29756
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_29756_DDR체력관리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 곡에 대해 소모하는 체력과 획득하는 점수가 주어진다.
        // 처음 체력은 100이며, 체력은 100을 초과할 수 없고, 각 곡을 진입할 때마다 k만큼 체력을 회복한다.
        // 각 노래에 대해 플레이하거나 포기할 수 있을 때, 얻을 수 있는 최대 점수는?
        //
        // DP 문제
        // dp[노래][체력] = 최대 점수
        // 로 dp를 세우고 최대 점수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노래, 각 노래에 진입할 때 얻는 체력 회복 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 노래의 점수와 체력 소모량
        int[] s = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] h = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 마지막 노래까지 마친 상태를 보기 위해 n+1개의 열을 세우고
        // 최대 체력 역시 100이므로 100까지로 지정
        int[][] dp = new int[n + 1][101];
        // 값이 없는 부분은 -1로 채우고
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // 처음 시작 체력은 100
        dp[0][100] = 0;
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // -1인 경우 건너뛴다.
                if (dp[i][j] == -1)
                    continue;
                
                // i번 노래를 플레이할 수 있는 경우
                // 체력을 h[i]만큼 소모하고, i+1번째 노래 진입 전 상태가 되므로 k만큼 체력을 회복한다.
                // 그 중 값이 100을 넘어서는 안된다.
                // 점수는 현재 점수 + s[i]
                if (j >= h[i])
                    dp[i + 1][Math.min(j - h[i] + k, 100)] = Math.max(dp[i + 1][Math.min(j - h[i] + k, 100)], dp[i][j] + s[i]);
                // i번 노래를 포기하는 경우.
                // k만큼 체력을 회복하고 점수는 그대로 가져간다.
                dp[i + 1][Math.min(j + k, 100)] = Math.max(dp[i + 1][Math.min(j + k, 100)], dp[i][j]);
            }
        }
        
        // 마지막 노래를 마쳤을 때
        // 체력과 상관 없이 가장 높은 점수를 찾는다.
        int max = 0;
        for (int i = 0; i < dp[n].length; i++)
            max = Math.max(max, dp[n][i]);
        // 답안 출력
        System.out.println(max);
    }
}