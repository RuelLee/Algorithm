/*
 Author : Ruel
 Problem : Baekjoon 20925번 메이플스토리
 Problem address : https://www.acmicpc.net/problem/20925
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20925_메이플스토리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 사냥터에 대해 입장 경험치와 1분당 획득 경험치가 주어진다.
        // 최소 1개 이상의 입장 경험치가 0인 사냥터가 주어진다.
        // 그리고 각 사냥터 간의 이동 시간이 주어진다.
        // t분 동안 가장 많은 경험치를 얻고자할 때, 경험치는?
        //
        // DP 문제
        // n이 최대 200, t가 1000으로 주어진다.
        // dp[시간][사냥터] = 얻은 최대 경험치
        // 로 세우고 풀면 간단하게 풀 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 사냥터에서 t분간 사냥을 한다.
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 사냥터 정보
        int[][] farm = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                farm[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 사냥터 간의 이동 소요 시간
        int[][] adjMatrix = new int[n][n];
        for (int i = 0; i < adjMatrix.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < adjMatrix[i].length; j++)
                adjMatrix[i][j] = Integer.parseInt(st.nextToken());
        }

        // -1을 모두 채워둔다.
        int[][] dp = new int[t + 1][n];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // 그 중 입장 경험치가 0인 사냥터의 0일 때의 경험치를 0으로 변경
        for (int i = 0; i < farm.length; i++) {
            if (farm[i][0] == 0)
                dp[0][i] = 0;
        }

        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // -1값인 경우 불가능한 경우이므로 건너뜀.
                if (dp[i][j] == -1)
                    continue;
                
                // 현재 경험치가 얻은 최대 경험치를 갱신하는지 확인
                max = Math.max(max, dp[i][j]);
                // 현재 사냥터에서 1분간 더 사냥하는 경우
                if (i + 1 < dp.length)
                    dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + farm[j][1]);
                
                // 현재 j 사냥터에서 k 사냥터로 이동하는 경우
                for (int k = 0; k < adjMatrix.length; k++) {
                    if (farm[k][0] <= dp[i][j] && i + adjMatrix[j][k] < dp.length)
                        dp[i + adjMatrix[j][k]][k] = Math.max(dp[i + adjMatrix[j][k]][k], dp[i][j]);
                }
            }
        }
        // 획득할 수 있는 최대 경험치 출력
        System.out.println(max);
    }
}