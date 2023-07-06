/*
 Author : Ruel
 Problem : Baekjoon 11909번 배열 탈출
 Problem address : https://www.acmicpc.net/problem/11909
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11909_배열탈출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 배열이 주어진다.
        // 주인공은 (1, 1)에서 출발하여 (n, n)에 도달하고 싶다.
        // 이동하는 방법은 아래나 오른쪽으로만 이동이 가능하며, 맵 밖을 벗어나는 건 불가능하다.
        // 또 한가지의 규칙으로 각 칸에는 정해진 값이 있는데, 현재 칸의 값이 이동하려는 칸보다 값이 높을 때만 이동이 가능하다.
        // 다음 칸을 이동하는데 현재 칸의 값이 적은 경우, 현재 칸의 값을 1칸 당 1의 비용을 지불하여 증가시킬 수 있다.
        // 도착지에 도달하는데 드는 최소 비용은?
        //
        // DP 문제
        // 이동을 오른쪽 아래로만 이동이 가능하므로, 순차적으로 배열들을 살펴봄으로써 계산이 가능하다.
        // 오른쪽 혹은 아랫쪽을 이동하는데 칸들의 값을 비교하고, 더 적은 경우, 두 값의 차 + 1의 비용을 통해
        // 현재 칸의 값을 증가시키고 이동시키는 것이 가능하다.
        // 이를 통해 최소 비용을 구한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 보드
        int n = Integer.parseInt(br.readLine());
        int[][] board = new int[n][];
        for (int i = 0; i < board.length; i++)
            board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 각 칸에 도달하는 최소 비용
        int[][] dp = new int[n][n];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 시작지에서는 비용이 0
        dp[0][0] = 0;

        // 오른쪽과 아래로만 이동이 가능하므로 순차적으로 각 칸을 살펴보더라도 상관이 없다.
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 아랫칸으로 이동하는 경우. 맵을 벗어나선 안된다.
                // 아랫칸에 도달하는데 기록되어있는 현재 최소 비용과
                // 현재 칸에서 아랫칸으로 이동하는데 드는 비용을 비교하여 더 작은 값을 남겨둔다.
                if (i + 1 < dp.length)
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + Math.max(board[i + 1][j] - board[i][j] + 1, 0));

                // 오른쪽으로 이동하는 경우. 마찬가지로 맵을 안 벗어난다.
                if (j + 1 < dp[i].length)
                    dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + Math.max(board[i][j + 1] - board[i][j] + 1, 0));
            }
        }

        // (n, n)에 도달하는 최소 비용을 출력한다.
        System.out.println(dp[n - 1][n - 1]);
    }
}