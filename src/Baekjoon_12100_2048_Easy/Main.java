/*
 Author : Ruel
 Problem : Baekjoon 12100번 2048 (Easy)
 Problem address : https://www.acmicpc.net/problem/12100
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12100_2048_Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[][][] board;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 칸이 주어진다.
        // 네 방향 중 하나를 입력할 수 있고, 모든 칸이 해당 방향으로 이동한다.
        // 벽에 막힌 경우, 더 이상 이동하지 못하고,
        // 같은 수를 만난 경우, 1회의 방향 입력 시에 한 칸마다 한 번 합쳐질 수 있다.
        // 총 다섯 번의 방향 입력을 할 수 있을 때, 얻을 수 있는 가장 큰 값은?
        //
        // 시뮬레이션, 구현, 브루트포스, 백트래킹 문제
        // 충실히 문제에 따르게끔 구현한다.
        // 네 방향에 대해, 빈 칸이라 칸이 밀려 벽까지 가는 경우
        // 같은 수의 칸을 만난 경우, 다른 수의 칸을 만난 경우를 따져 코드를 작성한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        // 게임 판
        board = new int[6][n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                board[0][i][j] = Integer.parseInt(st.nextToken());
        }

        System.out.println(findAnswer(0));
    }

    // turn에서의 행동
    static int findAnswer(int turn) {
        // 5개의 행동을 모두 했다면
        // 가장 큰 칸의 값을 찾아 반환
        if (turn == 5) {
            int max = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    max = Math.max(max, board[5][i][j]);
            }
            return max;
        }

        int max = 0;
        // 네 방향으로 모두 입력 후, 해당 가지로 파생되는 경우의 최대 값을 찾는다.
        moveUp(turn);
        max = Math.max(max, findAnswer(turn + 1));
        moveRight(turn);
        max = Math.max(max, findAnswer(turn + 1));
        moveDown(turn);
        max = Math.max(max, findAnswer(turn + 1));
        moveLeft(turn);
        max = Math.max(max, findAnswer(turn + 1));
        // 값 반환
        return max;
    }

    // 아랫 방향으로 입력할 경우
    static void moveDown(int turn) {
        // turn + 1번째 보드를 비워주고
        for (int[] b : board[turn + 1])
            Arrays.fill(b, 0);

        for (int i = 0; i < n; i++) {
            // 맨 아랫칸에서부터 시작한다.
            int to = n - 1;
            int from = n - 2;
            // 맨 아랫칸 값 입력
            board[turn + 1][to][i] = board[turn][to][i];
            // 맨 아랫칸보다 윗 칸들을 차례대로 살펴본다.
            while (from >= 0) {
                // 0이 아닌 칸을 찾는다.
                while (from >= 0 && board[turn][from][i] == 0)
                    from--;
                // 없다면 반복문 종료
                if (from < 0)
                    break;

                // 이동하려는 칸이 0이라면, 해당 칸으로 그냥 이동
                if (board[turn + 1][to][i] == 0)
                    board[turn + 1][to][i] = board[turn][from][i];
                // 값이 같다면 합치고, 1회에 한해 합쳐질 수 있으므로 to의 위치를 한 칸 위로 변경.
                else if (board[turn + 1][to][i] == board[turn][from][i])
                    board[turn + 1][to--][i] += board[turn][from][i];
                else    // 칸이 다른 경우, to - 1에 from을 이동
                    board[turn + 1][--to][i] = board[turn][from][i];
                // 다음 from 칸 탐색
                from--;
            }
        }
    }

    // 위로 입력할 때
    static void moveUp(int turn) {
        for (int[] b : board[turn + 1])
            Arrays.fill(b, 0);

        for (int i = 0; i < n; i++) {
            int to = 0;
            int from = 1;
            board[turn + 1][to][i] = board[turn][to][i];
            while (from < n) {
                while (from < n && board[turn][from][i] == 0)
                    from++;
                if (from == n)
                    break;

                if (board[turn + 1][to][i] == 0)
                    board[turn + 1][to][i] = board[turn][from][i];
                else if (board[turn + 1][to][i] == board[turn][from][i])
                    board[turn + 1][to++][i] += board[turn][from][i];
                else
                    board[turn + 1][++to][i] = board[turn][from][i];
                from++;
            }
        }
    }

    // 왼쪽을 입력할 때
    static void moveLeft(int turn) {
        for (int[] b : board[turn + 1])
            Arrays.fill(b, 0);

        for (int i = 0; i < n; i++) {
            int to = 0;
            int from = 1;
            board[turn + 1][i][to] = board[turn][i][to];
            while (from < n) {
                while (from < n && board[turn][i][from] == 0)
                    from++;
                if (from == n)
                    break;

                if (board[turn + 1][i][to] == 0)
                    board[turn + 1][i][to] = board[turn][i][from];
                else if (board[turn + 1][i][to] == board[turn][i][from])
                    board[turn + 1][i][to++] += board[turn][i][from];
                else
                    board[turn + 1][i][++to] = board[turn][i][from];
                from++;
            }
        }
    }

    // 오른쪽으로 입력할 때
    static void moveRight(int turn) {
        for (int[] b : board[turn + 1])
            Arrays.fill(b, 0);

        for (int i = 0; i < n; i++) {
            int to = n - 1;
            int from = n - 2;
            board[turn + 1][i][to] = board[turn][i][to];
            while (from >= 0) {
                while (from >= 0 && board[turn][i][from] == 0)
                    from--;
                if (from < 0)
                    break;

                if (board[turn + 1][i][to] == 0)
                    board[turn + 1][i][to] = board[turn][i][from];
                else if (board[turn + 1][i][to] == board[turn][i][from])
                    board[turn + 1][i][to--] += board[turn][i][from];
                else
                    board[turn + 1][i][--to] = board[turn][i][from];
                from--;
            }
        }
    }
}