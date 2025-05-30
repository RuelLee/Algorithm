/*
 Author : Ruel
 Problem : Baekjoon 2302번 극장 좌석
 Problem address : https://www.acmicpc.net/problem/2302
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2302_극장좌석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 좌석이 주어지고, 일반 입장권을 구매한 사람은 자신의 자리를 포함하여 양 옆 자리도 앉을 수 있다고 한다.
        // m개의 vip 입장권이 주어지고, 해당 좌석에만 앉을 수 있다.
        // 1 ~ n의 입장권이 모두 팔렸을 때, 관람객들이 좌석에 앉는 방법의 수를 구하라
        //
        // DP 문제
        // 모든 입장권이 팔렸으므로, 옆자리에 앉기 위해서는 옆 자리 사람과 좌석을 바꿔 앉는 수밖에 없다.
        // dp[i][0] = i번째 관람객이 i번째 자리에 앉는 경우의 수
        // dp[i][1] = (i - 1)번 관람객이 i번째 자리, i번 관람객이 (i - 1)번째 자리에 앉는 경우
        // 로 dp를 세우고 채워나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 좌석
        int n = Integer.parseInt(br.readLine());
        
        // m개의 vip 좌석
        int m = Integer.parseInt(br.readLine());
        boolean[] fixed = new boolean[n + 1];
        for (int i = 0; i < m; i++)
            fixed[Integer.parseInt(br.readLine())] = true;

        // DP
        long[][] dp = new long[n + 1][2];
        // 1번 좌석에 1번 관람객이 앚는 경우
        dp[1][0] = 1;
        for (int i = 2; i < dp.length; i++) {
            // i번째 좌석에 i번째 관람객이 앉는 경우
            // i-1번째까지 어떻게 앉았든, i번째 좌석에 i번째 관람객을 배치하는 것이 가능
            dp[i][0] = dp[i - 1][0] + dp[i - 1][1];

            // i-1번 자리와 i번 자리를 바꾸는 경우
            // 두 자리 모두 vip 자리이면 안되고, i-1번 자리에 i-1번 관람객이 앉아있어야함.
            if (!fixed[i - 1] && !fixed[i])
                dp[i][1] = dp[i - 1][0];
        }
        System.out.println(dp[n][0] + dp[n][1]);
    }
}