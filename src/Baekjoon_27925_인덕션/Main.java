/*
 Author : Ruel
 Problem : Baekjoon 27925번 인덕션
 Problem address : https://www.acmicpc.net/problem/27925
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27925_인덕션;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 3개의 화구를 가진 인덕션이 주어진다.
        // n개의 요리를 0 ~ 9까지의 온도로 조리하려고 한다.
        // 각 화구의 온도는 0에서 시작하며 1단계를 바꿀 때마다 스위치를 한번씩 눌러야한다.
        // 0에서 -1을 할 경우 9가 되고, 9에서 +1을 할 경우 10이 된다.
        // 모든 음식을 조리하는데 눌러야하는 최소 버튼 누름 수는?
        //
        // dp 문제
        // 4차원 dp를 통해
        // dp[요리순서][1번화구온도][2번화구온도][3번화구온도] = 최소 버튼 누름 수
        // 로 계산하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 요리
        int n = Integer.parseInt(br.readLine());
        // 각각을 조리해야하는 온도
        int[] temperatures = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp
        int[][][][] dp = new int[n + 1][10][10][10];
        // 초기값으로 Integer.MAX_VALUE를 채움
        for (int[][][] a : dp) {
            for (int[][] b : a)
                for (int[] c : b)
                    Arrays.fill(c, Integer.MAX_VALUE);
        }
        // 처음 시작 상태
        dp[0][0][0][0] = 0;
        
        // press[i][j] = i에서 j단계로 온도를 조절하는데 드는 최소 버튼 누름 수
        // 9 + 1 -> 0, 0 - 1 -> 9가 되므로, 모두 계산해서 기록.
        int[][] press = new int[10][10];
        for (int i = 0; i < press.length; i++) {
            for (int j = i + 1; j < press[i].length; j++)
                press[i][j] = press[j][i] = Math.min(j - i, i + 10 - j);
        }

        for (int i = 0; i < dp.length - 1; i++) {
            for (int a = 0; a < dp[i].length; a++) {
                for (int b = 0; b < dp[i][a].length; b++) {
                    for (int c = 0; c < dp[i][a][b].length; c++) {
                        // i번째 요리를 조리했을 때
                        // 각 화구의 온도가 a, b, c가 되는 경우가 존재하지 않는다면 건너뜀.
                        if (dp[i][a][b][c] == Integer.MAX_VALUE)
                            continue;

                        // 존재한다면
                        // 각 화구에서 i번째 요리를 할 경우, 최소 버튼 누름 수를 계산.
                        dp[i + 1][temperatures[i]][b][c] = Math.min(dp[i + 1][temperatures[i]][b][c], dp[i][a][b][c] + press[a][temperatures[i]]);
                        dp[i + 1][a][temperatures[i]][c] = Math.min(dp[i + 1][a][temperatures[i]][c], dp[i][a][b][c] + press[b][temperatures[i]]);
                        dp[i + 1][a][b][temperatures[i]] = Math.min(dp[i + 1][a][b][temperatures[i]], dp[i][a][b][c] + press[c][temperatures[i]]);
                    }
                }
            }
        }

        // 모든 요리를 마쳤을 때, 최소 버튼 누름 횟수 계산.
        int min = Integer.MAX_VALUE;
        for (int a = 0; a < dp[n].length; a++) {
            for (int b = 0; b < dp[n][a].length; b++) {
                for (int c = 0; c < dp[n][a][b].length; c++)
                    min = Math.min(min, dp[n][a][b][c]);
            }
        }
        // 답안 출력
        System.out.println(min);
    }
}