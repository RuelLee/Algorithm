/*
 Author : Ruel
 Problem : Baekjoon 26259번 백룸
 Problem address : https://www.acmicpc.net/problem/26259
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26259_백룸;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MIN = -1000 * 2000 - 1;

    public static void main(String[] args) throws IOException {
        // n * m 개의 방이 주어지며 각 방의 수가 주어진다.
        // 현재 가장 왼쪽 위 방에서 오른쪽 맨 아래 마지막 방으로 나아가려고 한다.
        // 이동은 오른쪽 혹은 아래로만 이동할 수 있다.
        // 중간에 방 간의 이동을 막는 가로 혹은 세로 벽이 주어진다.
        // 마지막 방까지 도달할 수 있는 수의 최대합을 구하라
        //
        // dp 문제
        // 크기가 그리 크지 않으므로 벽에 대해 일일이 방의 이동을 제한하면 된다.
        // 그 후, dp를 통해 각 방에 도달하는 최대 점수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m개의 방들
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 방의 점수
        int[][] rooms = new int[n][m];
        for (int i = 0; i < rooms.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < rooms[i].length; j++)
                rooms[i][j] = Integer.parseInt(st.nextToken());
        }

        // 벽
        st = new StringTokenizer(br.readLine());
        int[] wall = new int[4];
        for (int i = 0; i < wall.length; i++)
            wall[i] = Integer.parseInt(st.nextToken());
        int temp = 0;
        for (int i = 0; i < 2; i++) {
            temp = Math.min(wall[i], wall[i + 2]);
            wall[i + 2] = Math.max(wall[i], wall[i + 2]);
            wall[i] = temp;
        }

        boolean[][] bottomWall = new boolean[n][m];
        boolean[][] rightWall = new boolean[n][m];
        // 가로 벽일 경우
        if (wall[0] == wall[2] && wall[1] != wall[3] && wall[0] != 0 && wall[0] != n) {
            for (int c = wall[1]; c < wall[3]; c++)
                bottomWall[wall[0] - 1][c] = true;
        } else if (wall[1] == wall[3] && wall[0] != wall[2] && wall[1] != 0 && wall[1] != m) {
            // 세로 벽일 경우
            for (int r = wall[0]; r < wall[2]; r++)
                rightWall[r][wall[1] - 1] = true;
        }

        // dp에는 작은 값들로 세팅
        // 음수도 가능하므로, 정상적으로 가능한 최저값은 -1000 * (n + m)
        int[][] dp = new int[n][m];
        for (int[] d : dp)
            Arrays.fill(d, MIN);
        // 시작점
        dp[0][0] = rooms[0][0];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 초기값 그대로라면 해당 방에 도달할 수 없는 경우
                // 건너뛴다.
                if (dp[i][j] == MIN)
                    continue;
                
                // 아래로 이동이 가능한 경우
                if (i + 1 < dp.length && !bottomWall[i][j])
                    dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + rooms[i + 1][j]);
                // 오른쪽으로 이동이 가능한 경우
                if (j + 1 < dp[i].length && !rightWall[i][j])
                    dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] + rooms[i][j + 1]);
            }
        }
        // 마지막 방에서 수의 합이 초기값인 경우 Entity
        // 그 외의 경우 해당 값 출력
        System.out.println(dp[n - 1][m - 1] == MIN ? "Entity" : dp[n - 1][m - 1]);
    }
}