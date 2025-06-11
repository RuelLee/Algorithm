/*
 Author : Ruel
 Problem : Baekjoon 2300번 기지국
 Problem address : https://www.acmicpc.net/problem/2300
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2300_기지국;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // x축 위치에 기지국을 설치할 수 있고,
        // 각각의 주요 건물들의 (x, y) 좌표가 주어진다.
        // 통신 범위는 기지국을 중심으로 한 정사각형 범위이다. 사각형의 한 변을 통신폭이라고 한다.
        // 모든 건물들을 통신 연결하는데 필요한 통신폭 합의 최솟값은?
        //
        // 정렬, DP 문제
        // 먼저 x좌표가 증가하는 건물 순서대로 살펴보아야하므로 정렬을 한다.
        // dp[i] = i번째 건물까지 통신 연결했을 때, 통신폭 합의 최솟값
        // 위와 같이 dp를 세우고
        // dp[i]는
        // (0 ~ i)까지 하나의 기지국으로 커버하는 통신폭
        // dp[0] + (1 ~ i)까지 하나의 기지국으로 커버하는 통신폭
        // dp[1] + (2 ~ i)까지 하나의 기지국으로 커버하는 통신폭
        // ...
        // 을 계산한 값들 중 최솟값이 차지하게 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 건물들
        int n = Integer.parseInt(br.readLine());
        
        // 각 건물들의 위치
        int[][] points = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                points[i][j] = Integer.parseInt(st.nextToken());
        }
        // 정렬
        Arrays.sort(points, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });
        
        // dp[i] = i번째 건물까지 통신 연결했을 때, 통신폭 합의 최솟값
        int[] dp = new int[n];
        // 초기화
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 0번 건물만 단일 연결하는 경우.
        dp[0] = Math.abs(points[0][1]) * 2;
        for (int i = 1; i < dp.length; i++) {
            // i ~ j까지의 건물들 중 x축에서 가장 멀리 떨어진 건물의 y 절대값 * 2
            int max = 0;
            for (int j = i; j > 0; j--) {
                max = Math.max(max, Math.abs(points[j][1] * 2));
                // dp[j-1]까지의 결과값 + j ~ i까지 하나의 기지국으로 연결할 때의 통신폭
                dp[i] = Math.min(dp[i], dp[j - 1] + Math.max(points[i][0] - points[j][0], max));
            }
            max = Math.max(max, points[0][1] * 2);
            // 0 ~ i까지 하나의 기지국으로 연결할 때의 통신폭
            dp[i] = Math.min(dp[i], Math.max(points[i][0] - points[0][0], max));
        }
        // 모든 건물을 연결했을 때의 결과값 출력
        System.out.println(dp[n - 1]);
    }
}