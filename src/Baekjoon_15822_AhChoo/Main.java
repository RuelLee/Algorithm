/*
 Author : Ruel
 Problem : Baekjoon 15822번 Ah-Choo!
 Problem address : https://www.acmicpc.net/problem/15822
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15822_AhChoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 소리 파형에 해당되는 n개의 정수 배열 2개가 주어진다.
        // 두 소리 X, Y의 각 시점의 파형의 높이를 X(i), Y(j)라 할 때, 두 파형의 오차는 {X(i)-Y(j)}2와 같이 정의한다.
        // DTW 방식은 두 파형의 각 시점이 최대한 유사한 지점들을 대응시킨 후에 유사도를 계산한다.
        // 수열 X:[10, 20, 45, 20, 14, 15]와 Y:[10, 25, 50, 50, 30, 15]을 예를 들면
        //  0+25+25+900+256+0=1206, DTW를 사용하면 0+25+25+25+100+1+0=176가 된다.
        // DTW 기법으로 계산할 수 있는 두 파형의 최소 거리를 출력하라
        //
        // DP 문제
        // 소리의 같은 시점의 값만 비교하는 것이 아니라
        // 약간씩의 오차를 허용하는 방법이다.
        // dp[x의 시점][y의 시점] = 최소 거리 로 계산하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 기준 시점과 두 파형
        int n = Integer.parseInt(br.readLine());
        int[] x = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] y = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp[x의 시점][y의 시점] = 최소 거리
        int[][] dp = new int[n][n];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 둘 다 0일 때는 두 소리의 첫 시점의 값들 비교
        dp[0][0] = (int) Math.pow(x[0] - y[0], 2);
        
        // 모든 행과 열에 대해 계산
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                // i + 1이 x의 길이를 넘지 않을 때
                // (i, j) -> (i+1, j)로 가는 경우 계산
                if (i + 1 < dp.length) {
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + (int) Math.pow(x[i + 1] - y[j], 2));
                    // j + 1도 y의 길이를 넘지 않는다면
                    // (i, j) -> (i+1, j+1)로 가는 경우 계산
                    if (j + 1 < dp[i].length)
                        dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j] + (int) Math.pow(x[i + 1] - y[j + 1], 2));
                }
                // j + 1이 y의 길이를 넘지 않는다면
                // (i, j) -> (i, j+1)로 가는 경우 계산
                if (j + 1 < dp[i].length)
                    dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + (int) Math.pow(x[i] - y[j + 1], 2));
            }
        }
        
        // 두 파형의 비교를 모두 마친 시점의 최소 거리 출력
        System.out.println(dp[n - 1][n - 1]);
    }
}