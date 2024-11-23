/*
 Author : Ruel
 Problem : Baekjoon 28325번 호숫가의 개미굴
 Problem address : https://www.acmicpc.net/problem/28325
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28325_호숫가의개미굴;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 호숫가에 1 ~ n까지의 번호가 붙은 개미굴이 원형으로 연결되어있다.
        // i번째 개미굴에는 개미굴과 연결된 C[i]개의 쪽방이 있다.
        // 개미굴 내지 쪽방에는 한 마리의 개미가 있을 수 있으나
        // 직접적으로 연결된 개미굴 끼리 혹은 개미굴과 쪽방에 각각 개미가 있으면 서로가 불편해한다.
        // 현재 주어진 개미굴에 불편하지 않도록 개미를 최대한 배치하고자할 때
        // 최대 개미의 수는?
        //
        // DP 문제
        // dp[개미굴번호][현재상태] = 최대 개미의 수
        // 로 dp를 세우고 채우면 된다.
        // 다만, 첫번째 개미굴과 마지막 개미굴이 연결되어있으므로
        // 상태를 나타내는 경우는 
        // 0 -> 첫번째 개미굴이 비어있고, i번째 개미굴도 빈 경우
        // 1 -> 첫번째 개미굴이 비어있고, i번째 개미굴에는 개미가 있는 경우
        // 2 -> 첫번재 개미굴에 개미가 있고, i번째 개미굴은 빈 경우
        // 3 -> 첫번째 개미굴에 개미가 있고, i번째 개미굴에도 개미가 있는 경우
        // 로 지정하고 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 개미굴
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 개미굴에 연결된 쪽방의수
        long[] cs = new long[n];
        for (int i = 0; i < cs.length; i++)
            cs[i] = Long.parseLong(st.nextToken());

        long[][] dp = new long[n][4];
        // 초기값
        for (long[] d : dp)
            Arrays.fill(d, Long.MIN_VALUE);
        // 첫번째 개미굴에 개미가 없는 경우
        // 쪽방에는 모두 개미를 채워넣을 수 있다.
        dp[0][0] = cs[0];
        // 첫번째 개미굴에 개미가 있는 경우
        dp[0][3] = 1;
        for (int i = 0; i < dp.length - 1; i++) {
            // i번째 개미굴의 상태에 상관 없이
            // 다음 개미굴을 비우고, 쪽방을 채울 수 있다.
            // 해당 경우 계산
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i][j] != Long.MIN_VALUE)
                    dp[i + 1][j / 2 * 2] = Math.max(dp[i + 1][j / 2 * 2], dp[i][j] + cs[i + 1]);
            }
            // i번째 개미굴이 비어있는 상태에만
            // i+1번째 개미굴을 채울 수 있다.
            for (int j = 0; j < dp[i].length; j += 2) {
                if (dp[i][j] != Long.MIN_VALUE)
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i][j] + 1);
            }
        }

        // 4번 상태 -> 첫번째 개미굴이 차있고, i번째 개미굴도 개미가 있는 상태
        // 는 1번째와 마지막 번째 개미굴에 둘 다 개미가 차 있는 상태가 되므로 버리고
        // 나머지 상태들 중 최대값을 찾는다.
        long answer = Long.MIN_VALUE;
        for (int i = 0; i < 3; i++)
            answer = Math.max(answer, dp[n - 1][i]);
        // 답 출력
        System.out.println(answer);
    }
}