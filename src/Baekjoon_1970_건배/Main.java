/*
 Author : Ruel
 Problem : Baekjoon 1970번 건배
 Problem address : https://www.acmicpc.net/problem/1970
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1970_건배;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 원탁에 n명이 앉아있으며 각자 콜라를 갖고 있다.
        // 각자의 콜라 종류가 주어지며, 같은 맥주를 가진 사람끼리 건배를 하고 싶어한다.
        // 단 건배를 할 때 다른 쌍과 팔이 교차할 수 없다.
        // 동시에 건배를 하고자할 때, 가능한 가장 많은 쌍의 수는?
        //
        // DP 문제
        // 범위를 지정하고, 해당 범위 내에 가능한 가장 많은 건배 쌍을 계산한다.
        // 범위를 확장할 때는
        // a < b < c에 대해 a ~ c 구간은
        // a와 c가 같은 콜라일 경우, a + 1 ~ c - 1까지의 최대 쌍에 +1을 하는 방법
        // a ~ b 구간의 쌍의 수와 b+1 ~ c까지 구간의 쌍의 합으로 구할 수 있다.
        // 해당 가지들 중 가장 큰 값을 가져온다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명
        int n = Integer.parseInt(br.readLine());
        // 콜라 종류
        int[] cokes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int max = 0;
        int[][] dp = new int[n][n];
        // 옆 자리와 같은 콜라인 경우를 센다.
        if (n > 1) {
            for (int i = 0; i < n; i++) {
                dp[i][(i + 1) % n] = (cokes[i] == cokes[(i + 1) % n] ? 1 : 0);
                max = Math.max(max, dp[i][(i + 1) % n]);
            }
        }
        
        // 건배하는 쌍 사이에 다른 사람들이 존재하는 경우
        for (int diff = 2; diff < n; diff++) {
            // 처음 사람
            for (int start = 0; start < n; start++) {
                // 마지막 사람
                int end = (start + diff) % n;
                // 사이에 start + 1 ~ end -1이 낀 경우
                dp[start][end] = dp[(start + 1) % n][(end + n - 1) % n]
                        + (cokes[start] == cokes[end] ? 1 : 0);

                // start ~ mid, mid+1 ~ end로 나뉘는 경우.
                for (int mid = start; mid < start + diff; mid++)
                    dp[start][end] = Math.max(dp[start][end], dp[start][mid % n] + dp[(mid + 1) % n][end]);
                // 최대값 갱신
                max = Math.max(max, dp[start][end]);
            }
        }
        // 건배할 수 있는 쌍의 최대값 출력
        System.out.println(max);
    }
}