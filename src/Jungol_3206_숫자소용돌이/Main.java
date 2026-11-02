/*
 Author : Ruel
 Problem : Jungol 3206번 숫자 소용돌이
 Problem address : https://jungol.co.kr/problem/3206
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3206_숫자소용돌이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자가 주어진다. n과 m은 모두 짝수이다.
        // 각 직사각형 테두리를 한 회로로 보고, 회로를 반시계방향으로 r만큼 회전시킨 격자를 출력한다.
        //
        // 시뮬레이션, 구현 문제
        // 각 회로마다 자신의 idx를 구해두고, 각 위치 + r 위치의 idx를 불러와 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // r만큼 반시계 회전시킨 격자를 출력한다.
        int r = Integer.parseInt(st.nextToken());

        // 입력 받은 처음 격자.
        int[][] input = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                input[i][j] = Integer.parseInt(st.nextToken());
        }

        // 각 회로마다 idx를 저장
        int[][] idxes = new int[Math.min(n, m) / 2][];
        for (int i = 0; i < idxes.length; i++) {
            // 회로의 길이
            int length = (n - i * 2) * 2 + (m - i * 2) * 2 - 4;
            idxes[i] = new int[length];
            int row = i;
            int col = i;
            // 각 위치의 idx를 저장
            for (int j = 0; j < length; j++) {
                idxes[i][j] = row * m + col;

                if (row == i && col < m - i - 1)
                    col++;
                else if (col == m - i - 1 && row < n - i - 1)
                    row++;
                else if (row == n - i - 1 && col > i)
                    col--;
                else
                    row--;
            }
        }

        int[][] board = new int[n][m];
        // 회로 따라 돌며, r만큼 이동시킨 값을 기록
        for (int i = 0; i < idxes.length; i++) {
            // i번째 회로의 시작 위치
            int row = i;
            int col = i;

            // 회로 내의 j번째 위치
            for (int j = 0; j < idxes[i].length; j++) {
                // j + r번째 값을 가져옴
                int next = idxes[i][(j + r) % idxes[i].length];
                board[row][col] = input[next / m][next % m];

                if (row == i && col < m - i - 1)
                    col++;
                else if (col == m - i - 1 && row < n - i - 1)
                    row++;
                else if (row == n - i - 1 && col > i)
                    col--;
                else
                    row--;
            }
        }

        // 답 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            sb.append(board[i][0]);
            for (int j = 1; j < board[i].length; j++)
                sb.append(" ").append(board[i][j]);
            sb.append("\n");
        }
        // 출력
        System.out.print(sb);
    }
}