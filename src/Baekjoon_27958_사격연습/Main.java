/*
 Author : Ruel
 Problem : Baekjoon 27958번 사격 연습
 Problem address : https://www.acmicpc.net/problem/27958
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27958_사격연습;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][][] board;
    static int[] bullets;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int mod = 50, n, k;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 과녘이 주어진다. 과녘마다 체력이 주어진다.
        // k개의 총알이 주어지고 각 총알의 공격력이 주어진다.
        // 총알은 각 행에서 왼쪽에서 오른쪽으로 진행한다. 과녘을 통과할 수 없다.
        // 총알이 과녘을 맞췄을 때, 해당 공격력만큼 과녘의 체력이 깎이고,
        // 과녘의 체력이 0이 됐을 때, 원래 체력만큼의 점수를 얻는다.
        // 그리고 상하좌우로 빈 공간에 원래 체력의 1/4배(소수점 이하 버림)인 과녘을 생성한다.
        // 10점 이상인 과녘은 보너스 과녘으로 맞출 경우 바로 해당 체력만큼 점수를 획득하고, 추가 과녘을 생성하지 않고 사라진다.
        // 얻을 수 있는 최대 점수는?
        //
        // 브루트포스, 백트래킹 문제
        // n과 k가 그리 크지 않으므로
        // 모든 총알에 대해 맞출 과녘이 있는 행을 모두 쏴보는 방식으로 진행한다.
        // 그리하여 최대 점수를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 과녘, k개의 총알
        n = Integer.parseInt(br.readLine());
        k = Integer.parseInt(br.readLine());

        // board[턴][행][열]
        // 초기 과녘 상태 입력
        board = new int[k + 1][n][n];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int num = Integer.parseInt(st.nextToken());
                board[0][i][j] = num * mod + num;
            }
        }

        // 총알 입력
        bullets = new int[k + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < k + 1; i++)
            bullets[i] = Integer.parseInt(st.nextToken());

        // 얻은 최대 점수
        int score = findAnswer(1, 0);
        System.out.println(score);
    }

    // 현재 turn 턴이며, 얻은 점수는 sum
    static int findAnswer(int turn, int sum) {
        // 모든 총알을 발사했다면 지금까지 얻은 점수를 반환
        if (turn == bullets.length)
            return sum;

        // 현재 상태에서 얻을 수 있는 최대점수를 찾는다.
        int max = 0;
        // 0 ~ n-1행까지
        for (int i = 0; i < n; i++) {
            // 과녘 유무를 찾는다.
            int findTarget = 0;
            while (findTarget < n && board[turn - 1][i][findTarget] == 0)
                findTarget++;

            // 맞출 수 있는 과녘이 없다면 다음 행으로 진행
            if (findTarget == n)
                continue;

            // 이전 턴의 상태를 복사해온다.
            copy(turn);
            // 이번에 얻은 점수
            int add = 0;
            // 과녘이 10점 이상이거나, 이번 총알로 과녘을 쓰러뜨리는 경우
            if (board[turn][i][findTarget] / mod >= 10 || board[turn][i][findTarget] % mod <= bullets[turn]) {
                // 보너스 과녘이 아닌 경우, 상하좌우로 추가 과녘을 생성한다.
                if (board[turn][i][findTarget] / mod < 10)
                    boom(turn, i, findTarget);
                // 이번에 얻는 점수
                add = board[turn - 1][i][findTarget] / mod;
                // 과녘 제거
                board[turn][i][findTarget] = 0;
            } else      // 그 외의 경우는 해당 과녘의 체력만 깎는다.
                board[turn][i][findTarget] -= bullets[turn];
            // 다음 턴으로 진행하며 해당 경우로 파생되는 경우들 중 최대 점수를 반환받아
            // max의 값을 갱신
            max = Math.max(max, findAnswer(turn + 1, sum + add));
        }
        // 현재 상태에서 파생되는 최대 점수 반환
        return max;
    }

    // turn의 상태 복사
    static void copy(int turn) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                board[turn][i][j] = board[turn - 1][i][j];
        }
    }

    // 과녘이 넘어지며 상하좌우에 추가 과녘을 생성
    static void boom(int turn, int i, int j) {
        for (int d = 0; d < 4; d++) {
            int nextR = i + dr[d];
            int nextC = j + dc[d];

            if (checkArea(nextR, nextC) && board[turn][nextR][nextC] == 0) {
                int s = board[turn - 1][i][j] / mod / 4;
                board[turn][nextR][nextC] = s * mod + s;
            }
        }
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}