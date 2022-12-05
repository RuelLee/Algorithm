/*
 Author : Ruel
 Problem : Baekjoon 14722번 우유 도시
 Problem address : https://www.acmicpc.net/problem/14722
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14722_우유도시;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] dr = {0, 1};
    static int[] dc = {1, 0};

    public static void main(String[] args) throws IOException {
        // n * n 의 지도가 주어지고, 각 지점에선 정해진 우유만 판다.
        // 1,1 에서 동남쪽으로 진행하며, 딸기우유 -> 초코우유 -> 바나나우유를 순서대로 마시고자한다.
        // 각 지점에서는 우유를 마시거나 안 마시고 지나갈 수 있다.
        // n, n에 도달하는 동안 가능한 우유를 많이 마시고자 할 때 그 개수는?
        //
        // DP문제
        // 각 지점에 도달했을 때, 이전에 마신 우유의 종류에 따른 최대 개수를 DP로 기록해나가며
        // n, n에서의 상태를 체크한다.
        //
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 지도의 가로, 세로 크기 n
        int n = Integer.parseInt(br.readLine());
        // 각 지점에서 파는 우유의 종류
        int[][] milks = new int[n][];
        for (int i = 0; i < milks.length; i++)
            milks[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 지점에서 직전에 마신 우유에 따른 최대 개수를 DP에 기록한다.
        int[][][] dp = new int[n][n][3];
        // 왼쪽 위에서 오른쪽 아래로 진행하며
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                // 현재 지점에서 파는 우유가 딸기우유거나(= 딸기 우유는 첫 우유로 마실 수 있기 때문에)
                // 현재 지점에서 파는 우유를 마실 수 있는 직전 우유를 마신 경우가 존재할 때
                // 현재 지점의 우유를 마신다.
                if (milks[i][j] == 0 || dp[i][j][(milks[i][j] + 2) % 3] != 0)
                    dp[i][j][milks[i][j]] = Math.max(dp[i][j][milks[i][j]], dp[i][j][(milks[i][j] + 2) % 3] + 1);
                
                // 다음 동쪽과 남쪽으로
                for (int d = 0; d < 2; d++) {
                    int nextR = i + dr[d];
                    int nextC = j + dc[d];
                    
                    // 다음 지점이 존재할 때
                    if (!checkArea(nextR, nextC, n))
                        continue;

                    // nextR, nextC에, i, j 지점의 값과 이전에 기록된 값 중 더 큰 값을 DP에 남겨준다.
                    for (int k = 0; k < dp[i][j].length; k++)
                        dp[nextR][nextC][k] = Math.max(dp[nextR][nextC][k], dp[i][j][k]);
                }
            }
        }
        // 최종적으로 n,n 에 도달했을 때 마실 수 있는 최대 우유의 개수를 출력한다.
        System.out.println(Arrays.stream(dp[n - 1][n - 1]).max().getAsInt());
    }

    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}