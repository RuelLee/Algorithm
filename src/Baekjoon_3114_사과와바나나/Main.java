/*
 Author : Ruel
 Problem : Baekjoon 3114번 사과와 바나나
 Problem address : https://www.acmicpc.net/problem/3114
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3114_사과와바나나;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // r * c 칸의 과수원이 주어진다.
        // 각 칸에는 사과 혹은 바나나 나무가 심어져있다.
        // A는 사과를, B는 바나나를 좋아한다.
        // (1, 1) 칸에서 시작하여 불도저를 타고 오른쪽, 아래, 오른쪽 아래 대각선으로만 이동하여
        // (r, c) 칸에 도달하며, 이동한 칸에는 나무를 제거하고, 
        // 이동한 칸 위로는 B에게 아래로는 A에게 주게된다.
        // 서로 좋아하는 나무를 최대한 많이 갖도록 이동하고자할 때
        // 그 때 각각이 좋아하는 나무의 합은?
        //
        // 누적합 + DP 문제
        // 먼저 각 칸까지, 바나나는 위에서부터 누적합을, 사과는 아래에서부터 누적합을 계산한다.
        // dp[i][j]는 (i, j) 칸을 이동할 때 최대 합으로 한다.
        // (i, j)로 이동하는 방법은 
        // (i, j-1)에서 오른쪽으로, (i-1, j)에서 아래로, (i-1, j-1)에서 오른쪽 아래로
        // 이동하는 방법들이 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 과수원
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // 바나나 나무와 사과 나무
        int[][] bananas = new int[r][c];
        int[][] apples = new int[r][c];
        
        // 각 칸에 해당하는 나무들 값 정리
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++) {
                String tree = st.nextToken();
                if (tree.charAt(0) == 'B')
                    bananas[i][j] = Integer.parseInt(tree.substring(1));
                else
                    apples[i][j] = Integer.parseInt(tree.substring(1));
            }
        }
        
        // 누적합 처리
        for (int i = 1; i < r; i++) {
            for (int j = 0; j < c; j++) {
                bananas[i][j] += bananas[i - 1][j];
                apples[r - i - 1][j] += apples[r - i][j];
            }
        }
        
        int[][] dp = new int[r][c];
        // 시작 위치는 (0, 0)
        // 이 때 시작 위치는 불도저로 밀리므로, 아래로의 사과 개수만 센다.
        dp[0][0] = apples[1][0];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                // 세로로 봤을 때, 현재 칸만을 불도저로 밀 때의 나무 합
                int sum = (i > 0 ? bananas[i - 1][j] : 0) + (i + 1 < r ? apples[i + 1][j] : 0);
                // 아래로 이동하는 경우
                // dp[i-1][j]에서 현 위치에 해당하는 나무를 더 밀어버리는 경우다.
                // 이 때 현 위치에 해당하는 나무가 사과나무라면 해당 개수만큼이 차감된다.
                dp[i][j] = Math.max(dp[i][j], i > 0 ? dp[i - 1][j] - (i + 1 < r ? apples[i][j] - apples[i + 1][j] : apples[i][j]) : 0);
                // 오른쪽으로 이동하는 경우
                dp[i][j] = Math.max(dp[i][j], j > 0 ? dp[i][j - 1] + sum : 0);
                // 오른쪽 대각선 아래로 이동하는 경우.
                dp[i][j] = Math.max(dp[i][j], i > 0 && j > 0 ? dp[i - 1][j - 1] + sum : 0);
            }
        }
        
        // 도착지는 (r-1, c-1)이므로
        // 해당하는 값을 출력.
        System.out.println(dp[r - 1][c - 1]);
    }
}