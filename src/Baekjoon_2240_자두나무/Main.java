/*
 Author : Ruel
 Problem : Baekjoon 2240번 자두나무
 Problem address : https://www.acmicpc.net/problem/2240
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2240_자두나무;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2개의 자두 나무가 있고, 1초마다 두 자두 나무 중 하나의 나무에서 열매가 떨어진다고 한다
        // t초 동안 최대 w번의 이동을 하여 최대한 많은 열매를 얻고자 한다
        // 몇 개의 열매를 얻을 수 있는가
        //
        // DP문제
        // 시간과 나무 그리고 이동횟수에 대해 DP를 세우고, 열매를 주워보자!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int t = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        // dp[시간][나무][남은 이동 횟수]
        int[][][] dp = new int[t + 1][2][w + 1];
        // 최대 1000초 동안이므로, -1001로 초기화.
        for (int[][] tree : dp) {
            for (int[] shift : tree)
                Arrays.fill(shift, -1001);
        }

        // 처음 위치는 1번 나무에 위치한다고 한다.
        dp[0][0][w] = 0;
        for (int i = 1; i < dp.length; i++) {
            int treePlumFalling = Integer.parseInt(br.readLine()) - 1;
            // 이동횟수를 한번도 쓰지 않은 경우에는 1번 위치 고정이다.
            // 반대로 이동 횟수를 한 번도 쓰지 않고 2번 위치로 가는 경우는 불가능하다.
            dp[i][0][w] = dp[i - 1][0][w];
            // 만약 1번 나무에서 열매가 떨어진다면 값을 증가시켜준다.
            if (treePlumFalling == 0)
                dp[i][0][w]++;
            // i초 동안 최대 i번 이동할 수 있으므로, w - i보다 적게 이동 횟수가 남은 경우는 계산하지 않아도 된다.
            for (int k = w - 1; k >= Math.max(w - i, 0); k--) {
                // 이번에 열매가 떨어지는 나무의 경우
                // 이동하지 않고 열매를 줍는 경우와 이동하여 열매를 줍는 경우를 고려한다.
                // 이 때 열매의 개수는 +1
                dp[i][treePlumFalling][k] = Math.max(dp[i - 1][treePlumFalling][k], dp[i - 1][(treePlumFalling + 1) % 2][k + 1]) + 1;
                // 이번에 열매가 떨어지지 않는 나무의 경우.
                // 마찬가지로 이동하지 않는 경우와, 이동하는 경우를 고려한다
                // 열매의 개수는 증가하지 않는다.
                dp[i][(treePlumFalling + 1) % 2][k] = Math.max(dp[i - 1][(treePlumFalling + 1) % 2][k], dp[i - 1][treePlumFalling][k + 1]);
            }
        }

        // 최종 시간에 각 나무들의 모든 이동횟수에 대해 가장 많은 열매를 얻은 개수를 출력한다.
        System.out.println(Math.max(Arrays.stream(dp[t][0]).max().getAsInt(), Arrays.stream(dp[t][1]).max().getAsInt()));
    }
}