/*
 Author : Ruel
 Problem : Baekjoon 10710번 실크로드
 Problem address : https://www.acmicpc.net/problem/10710
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10710_실크로드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 일렬로 늘어선 n+1개의 도시와 m개의 날에 대한 날씨가 주어진다.
        // 옆 도시와 이어지는 n개의 도로의 길이 또한 주어진다.
        // 0번 도시에서 n번 도시로 도착하려한다.
        // 각 도시를 이동할 때마다 피로도가 누적되는데 이는
        // 거리 * 날씨로 계산된다.
        // 최소 피로도로 m날 이내에 n번 도시로 이동하고자할 때, 그 피로도는?
        //
        // DP 문제
        // dp를 통해
        // dp[도시][일] = 최소 피로도로 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n+1개의 도시와 n개의 도로 그리고 m개의 날에 대한 날씨
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 옆 도시와 이어지는 도로의 길이
        int[] distances = new int[n];
        for (int i = 0; i < distances.length; i++)
            distances[i] = Integer.parseInt(br.readLine());
        // 날씨
        int[] weathers = new int[m];
        for (int i = 0; i < weathers.length; i++)
            weathers[i] = Integer.parseInt(br.readLine());

        long[][] dp = new long[n + 1][m + 1];
        for (int i = 0; i < dp.length - 1; i++) {
            // 한 도시에 체재하는 경우, 피로도가 누적되지 않는다.
            // 따라서 이전에 더 낮은 피로도로 해당 도시에 도착한 경우,
            // 이전에 도착해 해당 날짜까지 체제하는 것이 더 피로도가 적음에 유의하자

            // i+1번째 도시에 i+1번째 날에 도착하는 경우는
            // 하루도 쉬지 않고 계속 이동한 경우이다.
            dp[i + 1][i + 1] = dp[i][i] + distances[i] * weathers[i];
            // i+1번째 도시에 j날에 도착하는 경우와 이전에 도착해서 체재하는 경우들 중
            // 더 피로도가 낮은 쪽을 선택한다.
            for (int j = i + 1; j < dp[i].length - 1; j++)
                dp[i + 1][j + 1] = Math.min(dp[i + 1][j], dp[i][j] + distances[i] * weathers[j]);
        }
        // n번 도시에 m번째 날 안에 도착하는 경우들 중 가장 적은 피로도를 출력한다.
        System.out.println(dp[n][m]);
    }
}