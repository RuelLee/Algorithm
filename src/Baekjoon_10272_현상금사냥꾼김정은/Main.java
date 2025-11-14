/*
 Author : Ruel
 Problem : Baekjoon 10272번 현상금 사냥꾼 김정은
 Problem address : https://www.acmicpc.net/problem/10272
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10272_현상금사냥꾼김정은;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 행성의 (x, y) 좌표가 주어진다.
        // 시작 위치는 x값이 가장 작은 행성이고, 최종 위치는 시작 행성으로 돌아와야한다.
        // n개의 행성을 모두 방문하는데, x좌표가 증가하는 순서대로 방문 후, 가장 오른쪽 행성을 방문 후엔
        // x좌표가 감소하는 순서대로 다시 방문하며 시작 위치로 돌아온다.
        // 이동 거리를 가장 짧게 가져가고 싶을 때, 그 거리는?
        //
        // DP 문제
        // 모든 행성을 최소 한 번은 방문해야한다. 시작 행성과 가장 오른쪽 행성은 두 번.
        // 따라서 두번째 행성부터 해당 행성을 가면서 방문할지, 오면서 방문할지를 dp를 통해 계산해나간다.
        // 혹은 두 가지 경로로 마지막 행성으로 가되, 양쪽이 서로 겹치지 않는 행성을 방문한다고 생각하면 쉽다.
        // dp[i][j] = 현재 1번 경로로 마지막 방문한 행성 i, 2번 경로로 방문한 마지막 행성 j
        // k번 행성을 방문할 때는 해당 행성을 1번 경로에 넣을 때 값과 2번 경로에 넣을 때 값을 계산하면 된다.
        // 최종적으로 n-1번 행성을 모두 방문해야하므로 dp[n-1][n-1]을 구해야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 행성들 위치
        int[][] planets = new int[512][2];
        // 값
        double[][] dp = new double[512][512];
        double[][] distances = new double[512][512];
        StringTokenizer st;
        for (int testCase = 0; testCase < t; testCase++) {
            // 행성들 위치 입력
            int n = Integer.parseInt(br.readLine());
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < planets[i].length; j++)
                    planets[i][j] = Integer.parseInt(st.nextToken());
            }
            
            // i와 j 행성 사이의 거리 계산
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++)
                    distances[i][j] = distances[j][i] = calcDistance(i, j, planets);
            }

            // dp 초기화
            for (int i = 0; i < n; i++)
                Arrays.fill(dp[i], 0, n, Double.MAX_VALUE);
            dp[0][0] = 0;

            // i번 행성을 방문할 때.
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    // 1번 경로로 i-1번 행성을 마지막으로 방문했고, 2번 경로로 j번 행성을 마지막으로 방문한 경우
                    // 두 경로 모두 각각 i번 행성을 방문했을 때, 이동 거리르 계산.
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + distances[i - 1][i]);
                    dp[i - 1][i] = Math.min(dp[i - 1][i], dp[i - 1][j] + distances[j][i]);

                    // 1번 경로로 j번 행성을 마지막으로 방문했고, 2번 경로로 i-1번 행성을 마지막으로 방문한 경우
                    dp[i][i - 1] = Math.min(dp[i][i - 1], dp[j][i - 1] + distances[j][i]);
                    dp[j][i] = Math.min(dp[j][i], dp[j][i - 1] + distances[i - 1][i]);
                }
            }

            for (int i = 0; i < n; i++) {
                // 1번 혹은 2번 경로 중 하나가 아직 n-1에 도달하지 않은 경우들을 골라
                // n-1번 행성을 마지막으로 방문하도록 한다.
                dp[n - 1][n - 1] = Math.min(dp[n - 1][n - 1], dp[i][n - 1] + distances[i][n - 1]);
                dp[n - 1][n - 1] = Math.min(dp[n - 1][n - 1], dp[n - 1][i] + distances[i][n - 1]);
            }
            // 오차가 10^-2까지 허용되므로, 해당 값을 짤라 기록
            sb.append(String.format("%.2f", dp[n - 1][n - 1])).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // a번 행성과 b번 행성 사이의 거리를 계산한다.
    static double calcDistance(int a, int b, int[][] planets) {
        return Math.sqrt(Math.pow(planets[a][0] - planets[b][0], 2)
                + Math.pow(planets[a][1] - planets[b][1], 2));
    }
}