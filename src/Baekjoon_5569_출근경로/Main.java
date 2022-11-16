/*
 Author : Ruel
 Problem : Baekjoon 5569번 출근 경로
 Problem address : https://www.acmicpc.net/problem/5569
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5569_출근경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int LIMIT = 100_000;

    public static void main(String[] args) throws IOException {
        // 남북 방향으로 도로가 w개, 동서방향으로 도로가 h개 있다.
        // 교차로를 돈 차량은 다음 교차로에서 방향 전환을 할 수 없다고 한다.
        // 1, 1에서 h, w에 도달하는 방법의 경우는 모두 몇 가지인가
        // 경우의 수가 클 수 있으므로 100000로 나눈 나머지를 출력
        //
        // DP문제
        // 교차로 방향 전환 제한만 없다면 일반적인 경우의 수 찾기 문제다.
        // 사실 방향 전환 제한이 있더라도 해당 사항을 DP에 기록해주면 된다.
        // 일반적으로는 해당 지점에 도달하는 경우만 세면 되기 때문에
        // 2차원 배열로 해결이 되지만, 이번에는 직전 진행 방향과 방향 가능 여부를 DP로 기록해서 계산한다.
        // dp[i][j][k][l]로
        // i, j에서 k는 진행 방향, l은 방향 전환 가능 여부를 기록한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        int[][][][] dp = new int[h][w][2][2];
        // 0, 0에서는 어느 방향으로도 진행할 수 있다.
        // 따라서 각각 방향에 경우의 수 하나씩을 초기값으로 준다.
        dp[0][0][0][0] = dp[0][0][1][0] = 1;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 가로로 진행이 가능한 경우.
                if (j + 1 < dp[i].length) {
                    // j + 1에서 방향 전환이 불가능한 경우는
                    // i, j에서 세로로 도착한 후, i, j+1로 진행하는 경우
                    dp[i][j + 1][0][0] += dp[i][j][1][1];
                    // 모듈러 연산.
                    dp[i][j + 1][0][0] %= LIMIT;
                    // j + 1에서 방향 전환이 가능한 경우
                    // i, j에 가로로 도착한 경우.
                    dp[i][j + 1][0][1] += dp[i][j][0][0] + dp[i][j][0][1];
                    dp[i][j + 1][0][1] %= LIMIT;
                }

                // 세로로 진행이 가능한 경우.
                // 가로와 동일하게 계산.
                if (i + 1 < dp.length) {
                    dp[i + 1][j][1][0] += dp[i][j][0][1];
                    dp[i + 1][j][1][0] %= LIMIT;
                    dp[i + 1][j][1][1] += dp[i][j][1][0] + dp[i][j][1][1];
                    dp[i + 1][j][1][1] %= LIMIT;
                }
            }
        }

        // 최종적으로 h - 1, w - 1에 도착하는 모든 상황에 따른 경우의 수들을
        // 모두 더해 해당 지점에 도달하는 모든 경우를 구한다.
        int sum = 0;
        for (int i = 0; i < dp[h - 1][w - 1].length; i++) {
            for (int j = 0; j < dp[h - 1][w - 1][i].length; j++) {
                sum += dp[h - 1][w - 1][i][j];
                sum %= LIMIT;
            }
        }
        // 모든 경우의 수 합 출력.
        System.out.println(sum);
    }
}