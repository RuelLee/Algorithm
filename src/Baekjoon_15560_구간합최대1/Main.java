/*
 Author : Ruel
 Problem : Baekjoon 15560번 구간 합 최대? 1
 Problem address : https://www.acmicpc.net/problem/15560
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15560_구간합최대1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 수열이 k와 상수 u, v가 주어진다.
        // q개의 쿼리에 대해 다음을 처리하라
        // 0 a b -> max(u * (ki+ ... + kj) + v * (j - i)) (a<= i <= j <= b)의 값을 구한다.
        // 1 a b -> ka의 값을 b로 바꾼다.
        //
        // DP 문제
        // dp[시작][끝] = 주어진 식의 최댓값으로 세우고 푼다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n 길이의 수열
        int n = Integer.parseInt(st.nextToken());
        // q개의 쿼리
        int q = Integer.parseInt(st.nextToken());
        // 상수 u, v
        int u = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        
        // 구간합을 구할 때가 많으므로 누적합 처리
        int[] psums = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < psums.length; i++)
            psums[i] = psums[i - 1] + Integer.parseInt(st.nextToken());

        // dp[시작][끝] = 주어진 식의 최댓값
        int[][] dp = new int[n + 1][n + 1];
        // 단일값일 때의 계산
        for (int i = 1; i < dp.length; i++)
            dp[i][i] = u * (psums[i] - psums[i - 1]);

        // 시작과 끝의 범위를 점점 벌린다.
        for (int diff = 1; diff < n; diff++) {
            // i ~ i + diff까지
            for (int i = 1; i + diff < dp.length; i++) {
                // 범위가 diff - 1일 때의 값들을 diff에 반영
                dp[i][i + diff] = Math.max(dp[i][i + diff - 1], dp[i + 1][i + diff]);
                // diff 범위에 대해 계산
                dp[i][i + diff] = Math.max(dp[i][i + diff], u * (psums[i + diff] - psums[i - 1]) + v * (diff));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // 1번 쿼리일 경우
            // dp를 참조하여 바로 답 기록
            if (c == 0)
                sb.append(dp[a][b]).append("\n");
            else {
                // 2번 쿼리일 경우
                // 누적합 값 변경
                int diff = psums[a - 1] + b - psums[a];
                for (int j = a; j < psums.length; j++)
                    psums[j] += diff;
                
                // a번 값을 b로 변경
                dp[a][a] = u * (psums[a] - psums[a - 1]);
                // a번이 속하는 값들에 대한 갱신을 진행.
                for (diff = 1; diff < n; diff++) {
                    for (int start = Math.max(a - diff, 1); start <= a && start + diff < dp.length; start++) {
                        dp[start][start + diff] = Math.max(dp[start][start + diff - 1], dp[start + 1][start + diff]);
                        dp[start][start + diff] = Math.max(dp[start][start + diff], u * (psums[start + diff] - psums[start - 1]) + v * diff);
                    }
                }
            }
        }
        // 답 출력
        System.out.print(sb);
    }
}