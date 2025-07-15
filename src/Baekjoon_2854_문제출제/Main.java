/*
 Author : Ruel
 Problem : Baekjoon 2854번 문제 출제
 Problem address : https://www.acmicpc.net/problem/2854
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2854_문제출제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n개의 난이도에 대해 문제를 출제하려한다.
        // 각 문제는 난이도가 고정된 문제와 i, i+1 난이도 모두 출제 할 수 있는 문제로 나뉜다.
        // 난이도가 고정된 문제와, 두 난이도로 출제할 수 있는 문제의 수가 주어질 때
        // 각 난이도에 하나씩 문제를 출제하는 경우의 수를 출력하라
        //
        // DP 문제
        // dp[i][0] = i번째 문제에 i, i+1 난이도로 출제할 수 있는 문제를 내지 않은 경우의 수
        // dp[i][1] = 해당 문제를 낸 경우의 수
        // 로 세우고 문제를 풀자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 난이도
        int n = Integer.parseInt(br.readLine());
        
        // 고정된 난이도의 문제
        int[] fixed = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < fixed.length; i++)
            fixed[i] = Integer.parseInt(st.nextToken());
        // 고정되지 않은 난이도를 갖는 문제
        int[] notFixed = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < notFixed.length - 1; i++)
            notFixed[i] = Integer.parseInt(st.nextToken());

        long[][] dp = new long[n + 1][2];
        dp[1][0] = fixed[1];
        dp[1][1] = notFixed[1];
        for (int i = 2; i < dp.length; i++) {
            // i번째 문제에 notFixed[i]를 사용하지 않는 경우의 수
            // dp[i-1][0]에서 fixed[i]와 notFixed[i-1]을 사용할 수 있고,
            // dp[i-1][1]에서 fixed[i]와 notFixed[i-1] - 1(사용된 문제)를 출제할 수 있다.
            dp[i][0] = dp[i - 1][0] * (fixed[i] + notFixed[i - 1]) +
                    dp[i - 1][1] * (fixed[i] + notFixed[i - 1] - 1);
            // i번 문제에 i, i+1 난이도가 고정되지 않은 문제를 출제하는 경우
            // dp[i-1][0], dp[i-1][0]에 notFixed[i] 문제를 출제한다.
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][1]) * notFixed[i];
            dp[i][0] %= LIMIT;
            dp[i][1] %= LIMIT;
        }
        // 답 출력
        System.out.println(dp[n][0]);
    }
}