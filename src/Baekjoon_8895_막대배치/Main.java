/*
 Author : Ruel
 Problem : Baekjoon 8895번 막대 배치
 Problem address : https://www.acmicpc.net/problem/8895
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8895_막대배치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 길이의 막대 n개가 주어질 때
        // 왼쪽에서 볼 땐 l개, 오른쪽에서 볼 땐 r개로 보이는 배치의 수를 출력하라
        //
        // DP 문제
        // dp[막대의 수][왼편에서 본 수][오르편에서 본 수] = 경우의 수
        // dp를 세운다.
        // 각각의 상태에서 가장 작은 막대를 하나 추가하는 방법으로 생각해보자.
        // dp[i][j][k]의 상태에서
        // 가장 작은 막대를 하나 왼쪽 끝에 추가하면 총 막대의 수는 i+1, 왼편에서 보이는 수는 j+1, 오른편에서 보는 수는 k가 된다.
        // 마찬가지로 가장 작은 막대를 하나 오른쪽 끝에 추가하면 총 막대의 수는 i+1, 왼편에서 보이는 수는 j, 오른편에서 보는 수는 k+1이 된다.
        // 이제 사이에 가장 작은 막대를 넣는 경우에는 왼편과 오른편에서 보는 수는 같으며
        // 그 경우의 수는 현재 막대의 수 - 1이 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // dp[막대의 수][왼편에서 본 수][오르편에서 본 수] = 경우의 수
        long[][][] dp = new long[21][21][21];
        // 막대가 하나일 때
        dp[1][1][1] = 1;
        // 막대가 1개일 때부터 19개일 때까지
        for (int i = 1; i < dp.length - 1; i++) {
            // 각 옆에서 본 수는 i보다 클 수는 없음.
            for (int j = 1; j <= i; j++) {
                for (int k = 1; k <= i; k++) {
                    // 경우의 수가 0인 경우 건너뜀.
                    if (dp[i][j][k] == 0)
                        continue;
                    
                    // 가장 작은 막대를 왼쪽, 오른쪽 끝에 둔 경우
                    dp[i + 1][j + 1][k] += dp[i][j][k];
                    dp[i + 1][j][k + 1] += dp[i][j][k];
                    // 가장 작은 막대를 막대들 사이에 둔 경우
                    dp[i + 1][j][k] += dp[i][j][k] * (i - 1);
                }
            }
        }
        
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            // n개의 막대, 왼편에서 본 수 l, 오른편에서 본 수 r일 때
            int n = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            // 해당 경의 수
            sb.append(dp[n][l][r]).append("\n");
        }
        // 답 출력
        System.out.println(sb);
    }
}