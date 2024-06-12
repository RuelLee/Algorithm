/*
 Author : Ruel
 Problem : Baekjoon 31671번 특별한 오름 등반
 Problem address : https://www.acmicpc.net/problem/31671
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31671_특별한오름등반;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // (0, 0), (n, n), (2n, 0) 모양의 산이 주어진다.
        // (0, 0)에서 출발하여, (2n, 0)에 도착해야한다.
        // 이동은 (x, y)에서 (x+1, y+1) 혹은 (x+1, y-1)로만 이동할 수 있다.
        // 또한 이동은 산의 내부 혹은 경계여야한다.
        // m개 지점에는 선생님들이 계시며, 선생님들을 마주치지 않고 가야한다.
        // 산을 지나며 도중 사진을 찍는데, 이 때 아름다움은 산의 높이와 같다고 한다.
        // 최종 목적지에 도달했을 때, 찍을 수 있는 사진의 최대 아름다움은?
        //
        // DP 문제
        // 산을 지나며, 도달할 수 있는 곳을 체크하고,
        // 해당 지점에서 갈 수 있는 곳을 다시 계산하며 최대 높이를 기록한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 산의 높이 n, 산의 너비 2*n
        int n = Integer.parseInt(st.nextToken());
        // 선생님들이 계시는 m개의 지점
        int m = Integer.parseInt(st.nextToken());
        
        // 선생님들의 위치
        boolean[][] teachers = new boolean[2 * n + 1][n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            teachers[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = true;
        }

        // 해당 지점에 도달할 때까지 거칠 수 있는 최대 높이.
        int[][] dp = new int[2 * n + 1][];
        for (int i = 0; i < dp.length; i++)
            dp[i] = new int[Math.min(i + 1, dp.length - i)];
        
        // 방문했는지 여부
        boolean[][] visited = new boolean[2 * n + 1][n + 1];
        // (0, 0)에서 출발
        visited[0][0] = true;
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // (i, j) 지점에 도착할 수 있어야만 한다.
                if (visited[i][j]) {
                    // 앞으로 전진하며, 위로 한 칸 올라가는 경우
                    if (j + 1 < dp[i + 1].length && !teachers[i + 1][j + 1]) {
                        // dp[i][j]와 j + 1 중 더 큰 값을 기록하고 방문 표시
                        dp[i + 1][j + 1] = Math.max(dp[i][j], j + 1);
                        visited[i + 1][j + 1] = true;
                    }
                    
                    // 앞으로 전진하며 아래로 한 칸 내려가는 경우
                    if (j - 1 >= 0 && !teachers[i + 1][j - 1]) {
                        // dp[i][j]와 j-1 중 더 큰 값을 기록하고 방문 표시.
                        dp[i + 1][j - 1] = Math.max(dp[i][j], j - 1);
                        visited[i + 1][j - 1] = true;
                    }
                }
            }

        }
        // (2 * n, 0)에 도달할 수 있는지 살펴보고
        // 그렇지 않다면 -1
        // 가능하다면 dp에 기록된 값 출력
        System.out.println(!visited[2 * n][0] ? -1 : dp[2 * n][0]);
    }
}