/*
 Author : Ruel
 Problem : Baekjoon 23083번 꿀벌 승연이
 Problem address : https://www.acmicpc.net/problem/23083
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23083_꿀벌승연이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] dr = {{-1, 0, 1}, {0, 1, 1}};
    static int[] dc = {1, 1, 0};
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 꿀벌집이 주어진다
        // 육각형 중 한 변을 맞대고 있는 방 그 중에서도
        // 아래쪽, 오른쪽 위, 오른쪽 아래 칸으로만 이동이 가능하다고 한다.
        // 그 중에는 구멍 칸이 있어 이동하지 못한다고 한다.
        // (1, 1)에서 (n, m)까지 가는 방법의 수는?
        //
        // DP문제
        // 꿀벌집 형태이므로 col에 따라 인접한 방의 위치가 달라진다.
        // 홀수 col일 경우, (-1, +1), (0, +1), (+1, 0) 으로 이동이 가능하고
        // 짝수 col일 경우, (0, +1), (+1, +1), (+1, 0) 으로 이동이 가능하다.
        // 위 점을 고려해야 작성한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 꿀벌집의 크기
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 구멍의 개수
        int k = Integer.parseInt(br.readLine());
        long[][] dp = new long[n][m];
        // 구멍의 위치에 큰 값을 넣어 계산하지 않는다.
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            dp[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = Long.MAX_VALUE;
        }
        
        // 처음 위치
        dp[0][0] = 1;
        // 더 높은 row에서 낮은 row의 col + 1 위치로 이동하기도 한다.
        // 따라서 col을 우선적으로 계산하기보다
        // row를 우선적으로 계산한다.
        for (int col = 0; col < m; col++) {
            for (int row = 0; row < n; row++) {
                // 구멍칸인 경우 건너 뛴다.
                if (dp[row][col] == Long.MAX_VALUE)
                    continue;
                
                // 이동 가능한 세 방향
                for (int d = 0; d < 3; d++) {
                    // col이 짝수냐 홀수냐에 따라 
                    // nextR은 가능한 경우가 두 종류이다.
                    int nextR = row + dr[col % 2][d];
                    int nextC = col + dc[d];
                    
                    // 다음 위치가 맵을 안 벗어나고, 구멍칸이 아니라면
                    if (checkArea(nextR, nextC, dp) && dp[nextR][nextC] != Long.MAX_VALUE) {
                        // (row, col) -> (nextR, nextC)로 이동하는 경우를 계산한다.
                        dp[nextR][nextC] += dp[row][col];
                        dp[nextR][nextC] %= LIMIT;
                    }
                }
            }
        }

        // (0, 0) -> (n-1, m-1)로 이동할 수 있는 모든 경우의 수를 출력한다.
        System.out.println(dp[n - 1][m - 1]);
    }

    static boolean checkArea(int r, int c, long[][] dp) {
        return r >= 0 && r < dp.length && c >= 0 && c < dp[r].length;
    }
}