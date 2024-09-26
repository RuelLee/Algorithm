/*
 Author : Ruel
 Problem : Baekjoon 16399번 드라이브
 Problem address : https://www.acmicpc.net/problem/16399
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16399_드라이브;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 드라이브를 통해 d km를 이동하고자 한다.
        // 자동차의 연료 용량은 c리터이고, 연비는 e로 정해져있다.
        // 출발지와 목적지 사이에 n개의 주유소가 있으며, 출발지부터 첫번째 주유소, 첫번째와 두번째 주유소, ... 사이의 거리가 주어진다.
        // 최소 비용으로 목적지로 가고자할 때 그 비용은?
        //
        // dp 문제
        // dp[현재 위치. 출발지 or 주유소 or 목적지][남은 연료의 양] = 지불한 비용

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 연료 용량 c, 연비 e, 목적지까지의 거리 d
        int c = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        
        // n개의 주유소
        int n = Integer.parseInt(br.readLine());
        int[] points = new int[n + 2];
        if (n != 0) {
            // 각 주유소의 위치를 구하기 위해 
            // 출발점을 기준으로 누적합 처리
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < points.length - 1; i++)
                points[i] = Integer.parseInt(st.nextToken()) + points[i - 1];
        }
        // 마지막 도착지
        points[n + 1] = d;
        
        // 각 주유소의 l당 가격
        int[] stations = new int[n + 2];
        if (n != 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < stations.length - 1; i++)
                stations[i] = Integer.parseInt(st.nextToken());
        }

        int[][] dp = new int[n + 2][c + 1];
        for (int[] point : dp)
            Arrays.fill(point, Integer.MAX_VALUE);
        // 출발지에선 연료가 모두 차있는 상태이고 소모한 비용은 0이다.
        dp[0][c] = 0;
        // 현재 위치
        for (int i = 0; i < dp.length - 1; i++) {
            // 현재 연료의 양
            for (int j = 0; j < dp[i].length; j++) {
                // 초기값이라면 해당 상태가 불가능한 경우이므로 건너뛴다.
                if (dp[i][j] == Integer.MAX_VALUE)
                    continue;
                
                // 다음 주유소 혹은 목적지까지 남은 거리
                int distanceToNext = points[i + 1] - points[i];
                // 이번 주유소에서 얼마나 연료를 채울지 모든 경우를 계산한다.
                // 연료가 충분한다면 채우지 않을 수도 있다.
                // 최대 주유량은 현재 잔여 연료와 채우는 양이 c를 넘어서는 안된다.
                // 그리고 그 상태로 다음 목적지에 도달했을 때의 값을 dp로 계산.
                for (int k = Math.max(distanceToNext * e - j, 0); j + k <= c; k++)
                    dp[i + 1][j + k - distanceToNext * e] = Math.min(dp[i + 1][j + k - distanceToNext * e], dp[i][j] + k * stations[i]);
            }
        }

        // 최종 목적지에 도달했을 때, 연료량에 상관없이 최소 비용인 값을 찾는다.
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < dp[n + 1].length; i++)
            answer = Math.min(answer, dp[n + 1][i]);
        // 초기값이라면 불가능한 경우이므로 -1 출력
        // 그 외의 경우 찾은 값 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}