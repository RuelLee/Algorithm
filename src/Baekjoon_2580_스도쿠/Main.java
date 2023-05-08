/*
 Author : Ruel
 Problem : Baekjoon 2580번 스도쿠
 Problem address : https://www.acmicpc.net/problem/2580
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2580_스도쿠;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static int[][] board;
    static boolean[][] square, rows, cols;

    public static void main(String[] args) throws IOException {
        // 9 * 9 크기의 스도쿠 문제가 주어진다.
        // 스도쿠 퍼즐은 가로, 세로, 3 * 3 크기의 사각형에 1 ~ 9까지 수가 한번씩만 쓰인 답안 형태를 찾는 것이다.
        // 답안을 찾아 출력하라
        //
        // 백트래킹 문제
        // 빈 칸을 찾아, 가로, 세로, 사각형을 살펴보아 아직 쓰이지 않은 수를 대입해보고,
        // 퍼즐에 오류가 발생하는지 여부를 따져 이를 고쳐나가는 백트래킹 방법을 사용한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 게임판
        board = new int[9][];
        for (int i = 0; i < board.length; i++)
            board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 3 * 3
        square = new boolean[9][10];
        // 행
        rows = new boolean[9][10];
        // 열에 중복 숫자 여부를 체크한다.
        cols = new boolean[9][10];
        
        // 빈 칸들의 idx
        List<Integer> blanks = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // 빈칸이라면 리스트에 추가
                if (board[i][j] == 0)
                    blanks.add(i * 9 + j);
                else {
                    // 빈 칸이 아니라면 3*3, 열, 행에 해당 수가 존재한다고 체크.
                    square[findSection(i, j)][board[i][j]] = true;
                    rows[i][board[i][j]] = true;
                    cols[j][board[i][j]] = true;
                }
            }
        }

        // 백트래킹으로 정답을 찾는다.
        findAnswer(0, blanks);

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++)
                sb.append(board[i][j]).append(" ");
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }

        // 출력
        System.out.println(sb);
    }

    // 백트래킹
    static boolean findAnswer(int idx, List<Integer> blanks) {
        // 모든 빈칸을 처리했다면
        // 가능한 경우를 찾았다. true를 반환해 끝낸다.
        if (idx >= blanks.size())
            return true;
        
        // 현재 빈 칸의 idx와 그에 따른 행와 열
        int current = blanks.get(idx);
        int row = current / 9;
        int col = current % 9;

        // 1부터 9까지 가능한 수를 찾는다.
        for (int i = 1; i < 10; i++) {
            // 3 * 3, 행, 열에 공통적으로 존재하지 않는지를 확인하고 그러하다면
            if (!square[findSection(row, col)][i] && !rows[row][i] && !cols[col][i]) {
                // i에 존재 여부 체크
                square[findSection(row, col)][i] = rows[row][i] = cols[col][i] = true;
                // 보드에도 i 대입
                board[row][col] = i;

                // 그런 상태에서 다음 모든 빈칸을 채우는 것이 가능한지 확인한다.
                // 그러하다면 true을 반환해 끝낸다.
                if (findAnswer(idx + 1, blanks))
                    return true;
                // 그렇지 않다면, 존재 여부를 다시 복구하고, 다음 수로 넘어간다.
                square[findSection(row, col)][i] = rows[row][i] = cols[col][i] = false;
            }
        }
        // 1 ~ 9까지 모든 수를 살펴봤음에도 찾지 못했다면,
        // 앞에 채웠던 수들 중 오류가 있는 경우.
        // false를 반환해 해당 수를 다른 수로 바꿔 다시 시도하도록 한다.
        return false;
    }

    // 해당 row, col 가지고서 몇번째 3 * 3 크기의 사각형에 해당하는지 찾는다.
    static int findSection(int row, int col) {
        return row / 3 * 3 + col / 3;
    }
}