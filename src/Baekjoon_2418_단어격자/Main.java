/*
 Author : Ruel
 Problem : Baekjoon 2418번 단어 격자
 Problem address : https://www.acmicpc.net/problem/2418
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2418_단어격자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws IOException {
        // 가로 w, 세로 h 크기의 격자가 주어진다.
        // 각 격자에는 하나의 알파벳이 들어있다.
        // 이 격자들을 상하좌우대각선의 인접한 격자들로 옮겨가며
        // 읽었을 때, 길이 l의 단어가 되는 경우의 수를 구하라
        //
        // DP 문제
        // dp[가로][세로][단어내의순서] = 경우의 수
        // 로 DP를 정의하고 모든 칸을 방문하며 가능한 경우의 수를 센다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 세로 h, 가로 w, 길이 l의 단어
        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        
        // 주어지는 격자 상태
        char[][] board = new char[h][];
        for (int i = 0; i < board.length; i++)
            board[i] = br.readLine().toCharArray();
        // 주어진 단어
        char[] standard = br.readLine().toCharArray();

        // dp
        long[][][] dp = new long[h][w][standard.length];
        // 첫 시작 알파벳에 대해 dp값으로 1을 넣어준다.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == standard[0])
                    dp[i][j][0] = 1;
            }
        }
        
        // 두번째 알파벳부터 계산 시작
        for (int idx = 1; idx < standard.length; idx++) {
            // 모든 칸을 순회하며
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    // 만약 현재 칸의 알파벳이 단어의 idx번째 알파벳과 일치한다면
                    if (board[i][j] == standard[idx]) {
                        // 8방향에 대해
                        for (int d = 0; d < 8; d++) {
                            int nearR = i + dr[d];
                            int nearC = j + dc[d];

                            // 칸이 존재한다면
                            // 해당 칸에 idx - 1번째 단어까지 완성된 경우의 수를
                            // 현재 칸의 idx번째 단어까지 완성된 경우의 수에 더한다.
                            if (checkArea(nearR, nearC, board)) {
                                dp[i][j][idx] += dp[nearR][nearC][idx - 1];
                            }
                        }
                    }
                }
            }
        }

        // 모든 칸을 돌며 전체 단어가 완성된 경우의 수를 모두 더한다.
        long sum = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++)
                sum += dp[i][j][standard.length - 1];
        }
        // 결과 출력
        System.out.println(sum);
    }

    static boolean checkArea(int r, int c, char[][] board) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }
}