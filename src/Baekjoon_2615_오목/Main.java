/*
 Author : Ruel
 Problem : Baekjoon 2615번 오목
 Problem address : https://www.acmicpc.net/problem/2615
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2615_오목;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 1};
    static int[] dc = {1, 1, 1, 0};

    public static void main(String[] args) throws IOException {
        // 19 * 19 칸의 바둑판에 검은 돌과 흰 돌이 주어진다.
        // 흑돌과 백돌은 오목을 하고 있다.
        // 바둑판 상태를 보고 어느 쪽이 이겼는지, 혹은 어느 쪽도 이기지 않았는지를 판단하라.
        // 어느 쪽이 이겼다면, 해당 색과 연속한 다섯개의 돌 중 가장 왼쪽 모두 같은 열을 갖는다면 가장 위쪽 돌의 위치를 출력한다.
        //
        // dp 문제
        // 로 풀었다. 브루트포스 태그 밖에 없었지만.
        // 연속한 다섯개의 가장 오른쪽, 혹은 가장 윗 돌을 출력해야한다.
        // 따라서 dp는 오른쪽 아래에서부터 행부터 살펴보고, 다음 열로 넘어가는 방법을 반복하며
        // 값을 채운다.
        // dp[i][j][d] = i, j번째에 d방향으로 연속한 돌의 개수
        // d는 1 : 오른쪽 위 대각선, 2 : 오른쪽, 3 : 오른쪽 아래 대각선, 4 : 아래
        // 방향으로 정한다.
        // 그리고 dp값을 채워나간다.
        // 예외 사항들이 정말 많은 문제였다
        // 5개일 때 게임이 끝나는게 아니라, 양쪽부터 채울 경우, 6개 이상 돌이 늘어서있는 경우도 있다.
        // 따라서 dp를 채워나가며 6이상이 된 경우, 해당 방향의 5가 채워진 돌의 dp 값을 지워버린다.
        // 그 후 나중에 dp값이 5인 곳의 위치를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 바둑판
        int[][] board = new int[19][19];
        StringTokenizer st;
        for (int i = 0; i < 19; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 19; j++)
                board[i][j] = Integer.parseInt(st.nextToken());
        }

        // dp[i][j][d] = i, j번째에 d방향으로 연속한 돌의 개수
        int[][][] dp = new int[19][19][4];
        // 오른쪽 열부터
        for (int i = 18; i >= 0 ; i--) {
            // 모든 행을 아래에서부터 위로 훑는다.
            for (int j = 18; j >= 0 ; j--) {
                // 해당 위치에 돌이 놓인 경우
                if (board[j][i] > 0) {
                    // 네 방향에 대해 탐색
                    for (int d = 0; d < 4; d++) {
                        // 먼저, 돌이 놓여있으므로 연속한 돌의 개수 값은 1
                        dp[j][i][d] = 1;
                        // 인근 방향의 위치
                        int nearR = j + dr[d];
                        int nearC = i + dc[d];

                        // 범위를 벗어나지 않았다면
                        if (checkArea(nearR, nearC)) {
                            // 해당 방향으로 연속한 돌이 같은 색인 경우, 값을 누적
                            dp[j][i][d] += (board[nearR][nearC] == board[j][i] ? dp[nearR][nearC][d] : 0);

                            // 만약 같은 색 돌이 6번째로 놓였다면
                            // 5번째 dp값을 지워버려, 답으로 탐색되지 않게 한다.
                            if (dp[j][i][d] == 6)
                                dp[nearR][nearC][d] = 0;
                        }
                    }
                }
            }
        }

        // 연속한 다섯개의 돌의 위치를 찾는다.
        int[] answer = new int[3];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < 4; k++) {
                    // 일치하는 위치가 주어진다면 값 기록
                    if (dp[i][j][k] == 5) {
                        answer[0] = board[i][j];
                        answer[1] = i + 1;
                        answer[2] = j + 1;
                    }
                }
            }
        }

        // 이긴 사람이 없는 경우 0 출력
        if (answer[0] == 0)
            System.out.println(0);
        else        // 그 외의 경우 조건에 일치하는 색과 위치 출력
            System.out.println(answer[0] + "\n" + answer[1] + " " + answer[2]);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < 19 && c >= 0 && c < 19;
    }
}