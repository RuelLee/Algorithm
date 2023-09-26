/*
 Author : Ruel
 Problem : Baekjoon 14945번 불장난
 Problem address : https://www.acmicpc.net/problem/14945
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14945_불장난;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static final int LIMIT = 10_007;

    public static void main(String[] args) throws IOException {
        // 모서리가 n인 방들 중 가장 왼쪽, 위의 칸에서 불이 났으며
        // 해당 칸에 있던 기웅과 민수는 딱 1초 전에 불이 붙은 칸에서 도망치기 시작했다.
        // 각각은 아랫칸 혹은 대각선 아래칸으로 이동이 가능하고, 불이 난 칸을 제외하고서는 두 사람은 서로 만나서는 안된다.
        // 두 명 모두 안전하게 대피하는 방법의 가짓수는?
        //
        // DP 문제
        // dp에서는 어떠한 정보를 기준으로 구분하여 계산할 것인지가 중요하다.
        // 위의 경우에는 시간과 그에 따른 기웅과 민수의 위치이다.
        // 따라서
        // dp[시간][기웅][민수]로 3차원 배열을 생성해주면 dp로 해결이 가능하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 모서리 길이가 n인 방
        int n = Integer.parseInt(br.readLine());

        int[][][] dp = new int[n][n][n + 1];
        // 불이 난 다음 위치에 기웅은 0, 민수는 1 위치에 있는 경우 밖에 없다.
        dp[0][0][1] = 1;
        
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    // 경우의 수가 0이라면 건너뛰고
                    if (dp[i][j][k] == 0)
                        continue;

                    // LIMIT으로 모듈러 연산을 하여 값의 크기를 줄이낟.
                    dp[i][j][k] %= LIMIT;
                    // 기웅과 민수가 그대로 아래로 진행하는 경우
                    dp[i + 1][j][k] += dp[i][j][k];
                    // 기웅은 오른쪽 대각선, 민수는 아래로 진행하는 경우
                    if (j + 1 < k)
                        dp[i + 1][j + 1][k] += dp[i][j][k];
                    // 민수가 오른쪽 대각선으로 진행하는 경우
                    if (k + 1 < n + 1) {
                        // 기웅은 그대로 아래로
                        dp[i + 1][j][k + 1] += dp[i][j][k];
                        // 기웅도 오른쪽 대각선으로 진행하는 경우
                        if (j + 1 < k)
                            dp[i + 1][j + 1][k + 1] += dp[i][j][k];
                    }
                }
            }
        }

        // 마지막 dp[n-1]에 기록된 모든 값이 가능한 경우의 수이다.
        // 모두 합쳐
        int sum = 0;
        for (int[] lastTime : dp[n - 1])
            sum += Arrays.stream(lastTime).sum();
        // 모듈러 연산을 통해 값을 줄인 후
        sum %= LIMIT;

        // 정답을 출력한다.
        System.out.println(sum);
    }
}