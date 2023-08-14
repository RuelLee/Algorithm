/*
 Author : Ruel
 Problem : Baekjoon 17953번 디저트
 Problem address : https://www.acmicpc.net/problem/17953
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17953_디저트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n일 동안 m 종류의 디저트를 하루에 하나씩 먹는다.
        // 각 날짜에 먹는 디저트의 만족감은 정해져있으며
        // 어제와 같은 종류의 디저트를 먹는다면 오늘의 만족도는 반감된다고 한다.
        // n일 동안 얻을 수 있는 최대 만족도는?
        //
        // DP 문제
        // 어제 먹은 디저트가 오늘의 만족도에 영향을 미치기 때문에
        // dp에 어제 먹은 디저트의 종류가 들어가야한다.
        // dp를 통해, dp[i][j] = i날에 j 디저트를 먹었을 때 얻을 수 있는 최대 만족도로 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n일 동안 m 종류의 디저트 중 하나씩을 먹는다.
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 날짜별 디저트들의 만족도
        int[][] desserts = new int[m][];
        for (int i = 0; i < desserts.length; i++)
            desserts[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 첫날은 이전에 먹은 디저트가 없기 때문에
        // 온전한 만족도로 모두 먹을 수 있다.
        int[][] dp = new int[n][m];
        for (int i = 0; i < m; i++)
            dp[0][i] = desserts[i][0];

        // i번째 날
        for (int i = 1; i < dp.length; i++) {
            // current 디저트를 먹는 방법은
            for (int current = 0; current < m; current++) {
                // i-1번째 날에 pre 디저터를 먹은 후, 먹는 방법
                // dp[i-1][pre] + desserts[current][i]
                // 만약 current와 pre가 같다면, 오늘 먹은 만족도는 반감.
                for (int pre = 0; pre < m; pre++)
                    dp[i][current] = Math.max(dp[i][current],
                            dp[i - 1][pre] + (pre != current ? desserts[current][i] : desserts[current][i] / 2));

            }
        }

        // n일차 결과값들 중 가장 큰 값을 출력한다.
        System.out.println(Arrays.stream(dp[n - 1]).max().getAsInt());
    }
}