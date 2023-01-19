/*
 Author : Ruel
 Problem : Baekjoon 25682번 체스판 다시 칠하기 2
 Problem address : https://www.acmicpc.net/problem/25682
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25682_체스판다시칠하기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // m * n 크기의 보드가 주어진다.
        // 각 격자는 W 혹은 B으로 칠해져있으며 이를 적절히 잘라
        // k * k 크기의 체스판을 만들고자 한다.
        // 체스판 위에 색을 덧칠하는 수를 최소화하려 할 때 그 수는?
        //
        // 2차원 누적합 문제
        // 2차원을 통한 누적합 문제라 생각이 꽤나 필요했다.
        // 먼저 체스판을 만들려고할 때, 가장 위의 가장 왼쪽 칸이 W, B 두 가지의 경우로 시작할 수 있다.
        // 따라서 각각의 경우로 분리하여 각각을 체스판이라 생각할 때, 덧칠해야하는 수를 2차원 누적합으로 구한다./
        // 그 후, 완성된 누적합을 통해 k * k 크기의 체스판에 덧칠해야하는 최소 횟수를 찾아낸다.
        // 예를 들어
        // 0 1 0 1
        // 1 0 1 0
        // 0 1 0 0
        // 1 0 0 0      과 같은 값이 존재한다면 누적합으로는
        //
        // 0 1 1 2
        // 1 2 3 4
        // 1 3 4 5
        // 2 4 5 6      처럼 계산이 되고
        //
        // 각 좌표가 (0, 0) ~ (i, j)에 이르는 사각형의 범위이므로
        // (2, 2) ~ (3, 3)에 해당하는 값만 구해내고 싶다면
        // (3, 3) - (3, 1) - (1, 3) + (1, 1)로 구해낸다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 보드 입력 처리
        char[][] board = new char[n][];
        for (int i = 0; i < board.length; i++)
            board[i] = br.readLine().toCharArray();

        // 첫번쨰 칸이 검정과, 하양으로 시작할 때, 덧칠해야하는 횟수의 2차원 누적합.
        int[][] startWithBlack = new int[n][m];
        int[][] startWithWhite = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // i, j칸이 현재 B이고, 칠해야하는 색이 첫번째 칸과 다르다면
                // i, j칸이 현재 W이고, 칠해야하는 색이 첫번째 칸과 색이 같다면
                if ((board[i][j] == 'B' && (i + j) % 2 == 1) ||
                        (board[i][j] == 'W' && (i + j) % 2 == 0))
                    startWithBlack[i][j]++;         // 첫번째 칸이 B인 경우는 덧칠해야하는 횟수 증가.
                else        // 그렇지 않은 경우에는 첫번째 칸이 W인 경우 증가
                    startWithWhite[i][j]++;
                
                // 누적합 처리
                // row가 0보다 큰 경우에는
                // 이전 row값 추가
                if (i > 0) {
                    startWithBlack[i][j] += startWithBlack[i - 1][j];
                    startWithWhite[i][j] += startWithWhite[i - 1][j];
                }
                // col이 0보다 큰 경우에는
                // 이전 col값 추가
                if (j > 0) {
                    startWithBlack[i][j] += startWithBlack[i][j - 1];
                    startWithWhite[i][j] += startWithWhite[i][j - 1];
                }
                // 둘 다 0보다 커서 중복으로 더해지는 구간이 생긴 경우에는
                // 중복된 값 감소
                if (i > 0 && j > 0) {
                    startWithBlack[i][j] -= startWithBlack[i - 1][j - 1];
                    startWithWhite[i][j] -= startWithWhite[i - 1][j - 1];
                }
            }
        }
        
        // 각 칸을 돌아보며 최소 덧칠 횟수 계산
        int min = Integer.MAX_VALUE;
        // k * k 칸의 체스판이므로
        // 0 ~ k * 0 ~ k 크기의 위치부터 살펴본다.
        for (int i = k - 1; i < board.length; i++) {
            for (int j = k - 1; j < board[i].length; j++) {
                // 첫번째 칸이 검정인 2차원 누적합에서
                // (i - k + 1, j - k + 1) ~ (i, j)에 이르는 k * k 크기의 넓이에 덧칠해야하는 횟수 계산
                // 최소값 갱신시 min 값 갱신.
                min = Math.min(min,
                        startWithBlack[i][j]
                                - (i - k >= 0 ? startWithBlack[i - k][j] : 0)
                                - (j - k >= 0 ? startWithBlack[i][j - k] : 0)
                                + (i - k >= 0 && j - k >= 0 ? startWithBlack[i - k][j - k] : 0));

                // 첫번째 칸이 하양인 2차원 누적합에서도 마찬가지로 계산.
                min = Math.min(min,
                        startWithWhite[i][j]
                                - (i - k >= 0 ? startWithWhite[i - k][j] : 0)
                                - (j - k >= 0 ? startWithWhite[i][j - k] : 0)
                                + (i - k >= 0 && j - k >= 0 ? startWithWhite[i - k][j - k] : 0));

            }
        }
        // 결과값 출력.
        System.out.println(min);
    }
}