/*
 Author : Ruel
 Problem : Baekjoon 17265번 나의 인생에는 수학과 함께
 Problem address : https://www.acmicpc.net/problem/17265
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17265_나의인생에는수학과함께;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] dr = {0, 1};
    static int[] dc = {1, 0};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자판이 주어진다.
        // (1, 1)에서 (n, n)으로 오른쪽, 아래로만 이동하여 도달하려 한다.
        // 각 격자판은 수와 연산자(+, -, *)가 존재하며
        // 가는 경로에서 만나는 수와 연산자의 연산 결과의 최소값과 최대값을 구하고자한다.
        //
        // DP 문제
        // 해당 위치가 수일 경우, 오른쪽과 아래에 수를 그대로 보내고
        // 연산자일 경우, 현재 칸의 값과 다음 칸의 수를 현재 칸의 연산자로 연산한 결과값을 보낸다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기
        int n = Integer.parseInt(br.readLine());
        
        // 격자판
        char[][] board = new char[n][];
        for (int i = 0; i < board.length; i++)
            board[i] = br.readLine().replaceAll(" ", "").toCharArray();
        
        // 3차원 배열을 통해, 각 좌표에서 최소값과 최대값을 구한다.
        int[][][] dp = new int[n][n][2];
        // 값 초기화
        for (int[][] row : dp) {
            for (int[] col : row) {
                col[0] = Integer.MAX_VALUE;
                col[1] = Integer.MIN_VALUE;
            }
        }
        dp[0][0][0] = dp[0][0][1] = board[0][0] - '0';

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 현재 칸의 좌표 (i, j)
                for (int d = 0; d < 2; d++) {
                    // 다음 칸의 좌표
                    int nextR = i + dr[d];
                    int nextC = j + dc[d];
                    
                    // 다음 칸이 범위를 벗어나지 않고
                    if (nextR < n && nextC < n) {
                        // 현재 칸이 수라면 그대로 값만 보낸다.
                        if (board[i][j] - '0' >= 0 && board[i][j] - '0' <= 5) {
                            dp[nextR][nextC][0] = Math.min(dp[nextR][nextC][0], dp[i][j][0]);
                            dp[nextR][nextC][1] = Math.max(dp[nextR][nextC][1], dp[i][j][1]);
                        } else {        // 현재 칸이 연산자라면
                            switch (board[i][j]) {
                                case '+' -> {       // + 연산
                                    dp[nextR][nextC][0] = Math.min(dp[nextR][nextC][0], dp[i][j][0] + (board[nextR][nextC] - '0'));
                                    dp[nextR][nextC][1] = Math.max(dp[nextR][nextC][1], dp[i][j][1] + (board[nextR][nextC] - '0'));

                                }
                                case '-' -> {       // - 연산
                                    dp[nextR][nextC][0] = Math.min(dp[nextR][nextC][0], dp[i][j][0] - (board[nextR][nextC] - '0'));
                                    dp[nextR][nextC][1] = Math.max(dp[nextR][nextC][1], dp[i][j][1] - (board[nextR][nextC] - '0'));
                                }
                                case '*' -> {       // * 연산
                                    dp[nextR][nextC][0] = Math.min(dp[nextR][nextC][0], dp[i][j][0] * (board[nextR][nextC] - '0'));
                                    dp[nextR][nextC][1] = Math.max(dp[nextR][nextC][1], dp[i][j][1] * (board[nextR][nextC] - '0'));
                                }
                            }
                        }
                    }
                }
            }
        }

        // 최종적으로 가장 오른쪽 아래 칸에 도달한 값을
        // 최대값, 최소값 순으로 출력한다.
        System.out.println(dp[n - 1][n - 1][1] + " " + dp[n - 1][n - 1][0]);
    }
}