/*
 Author : Ruel
 Problem : Baekjoon 15574번 신호 1
 Problem address : https://www.acmicpc.net/problem/15574
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15574_신호1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 점에 대해 (xi, yi)형태로 주어진다.
        // x가 증가하는 형태로 선을 이어, 그 길이가 최대가 되게끔할 때 그 길이는?
        //
        // 정렬, DP 문제
        // n이 최대 1000개로 주어지므로
        // dp[i] = i까지 잇는 최대 길이로 정의하고 문제를 푼다.
        // x가 증가하는 방향으로 이어야하므로
        // x에 대해 정렬 후, 같은 x에 대해서는 계산하지 않는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 점
        int n = Integer.parseInt(br.readLine());
        int[][] points = new int[n][];
        for (int i = 0; i < points.length; i++)
            points[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // x좌표에 따라 정렬
        Arrays.sort(points, Comparator.comparingInt(o -> o[0]));
        
        // dp[i] = i번째 점까지 잇는 경로의 최대 길이
        double[] dp = new double[n];
        for (int i = 0; i < dp.length; i++) {
            for (int j = i + 1; j < dp.length; j++) {
                // 같은 x 좌표일 경우 건너뜀.
                if (points[i][0] == points[j][0])
                    continue;

                // i -> j까지 잇는 경로가 j에 도달하는 최대 경로인지 확인.
                dp[j] = Math.max(dp[j], dp[i] + Math.sqrt(Math.pow(points[i][0] - points[j][0], 2) + Math.pow(points[i][1] - points[j][1], 2)));
            }
        }
        
        // 각 지점에 도달하는 경로의 길이들 중 최대값을 찾는다.
        double max = 0;
        for (double d : dp)
            max = Math.max(max, d);
        // 답안 출력
        System.out.println(max);
    }
}