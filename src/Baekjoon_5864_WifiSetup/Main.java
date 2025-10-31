/*
 Author : Ruel
 Problem : Baekjoon 5864번 Wifi Setup
 Problem address : https://www.acmicpc.net/problem/5864
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5864_WifiSetup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 소들이 일렬로 서 있으며, 해당 x 좌표가 주어진다.
        // 소들은 인터넷에 접속한다.
        // 기지국을 세우는 비용은 a + b * r이다. r은 전파가 미치는 범위
        // 최소 비용으로 모든 소들에게 인터넷을 제공하려면
        // 얼마가 드는가?
        //
        // dp 문제
        // dp[i] = i번째 소까지 인터넷을 제공하는 최소 비용
        // i를 증가시키며, 0 ~ i까지 하나의 기지국으로 커버할 때의 비용과
        // 0 ~ j까지 이미 계산된 비용과 j+1 ~ i의 기지국을 건설했을 때의 비용을 합산 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 소들
        int n = Integer.parseInt(st.nextToken());
        // 기지국 건설 비용
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 소들의 위치
        int[] stations = new int[n];
        for (int i = 0; i < stations.length; i++)
            stations[i] = Integer.parseInt(br.readLine());
        Arrays.sort(stations);

        // 큰 값으로 채워둔다.
        double[] dp = new double[n];
        Arrays.fill(dp, Double.MAX_VALUE);
        // 첫번째 소만 인터넷을 제공하는 비용은
        // 첫번째 소 위치에 반경 0짜리 기지국을 건설하는 것
        dp[0] = a;
        // i번째 소까지 인터넷을 제공할 때의 비용
        for (int i = 1; i < n; i++) {
            // 0 ~ i까지 하나의 기지국으로 커버할 때
            dp[i] = a + (stations[i] - stations[0]) / 2.0 * b;
            // 0 ~ j까지 이미 계산된 비용 + j+1 ~ i까지를 하나의 기지국으로 커버하는 비용
            for (int j = 0; j < i; j++)
                dp[i] = Math.min(dp[i], dp[j] + a + (stations[i] - stations[j + 1]) / 2.0 * b);
        }
        
        // 조건에 언급되지 않았으나, 소수점 아래 값이 0인 경우, 정수로만 출력해야한다.
        // 반영하여 답 출력
        if (dp[n - 1] * 10 % 10 == 0)
            System.out.println((int) dp[n - 1]);
        else
            System.out.println(dp[n - 1]);
    }
}