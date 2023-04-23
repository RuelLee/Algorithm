/*
 Author : Ruel
 Problem : Baekjoon 2239번 스도쿠
 Problem address : https://www.acmicpc.net/problem/2239
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2239_스도쿠;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static int[][] sudoku;
    static boolean[][] rectangles, rows, cols;

    public static void main(String[] args) throws IOException {
        // 스도쿠 문제가 주어질 때, 답안을 출력하라
        //
        // 백트래킹 문제
        // 스도쿠는 9 * 9 크기의 숫자들이 주어지고
        // 각 행과 열에 중복되는 수 없이 1 ~ 9가 할당되고
        // 3 * 3 크기의 사각형 9개에서도 중복되는 수 없이 모두 1 ~ 9의 수를 할당하는 게임이다.
        // 각 자리에 1 ~ 9 중 하나의 수는 반드시 할당된다.
        // 따라서 열과 행, 3 * 3 크기의 사각형을 모두 살펴보며 할당가능한 수들을 할당하며
        // 백트래킹을 활용하여 불가능한 경우를 정정해나가며 답을 찾자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 9 * 9 크기의 보드
        sudoku = new int[9][9];
        // 3 * 3 크기의 사각형 내에 이미 있는 수들을 표시
        rectangles = new boolean[9][10];
        // 행에 대해
        rows = new boolean[9][10];
        // 열에 대해
        cols = new boolean[9][10];

        // 빈 칸을 리스트로 저장한다.
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < sudoku.length; i++) {
            String row = br.readLine();
            for (int j = 0; j < sudoku[i].length; j++) {
                // 각 칸에 수를 할당하고
                sudoku[i][j] = row.charAt(j) - '0';
                // 0 이라면 그 수의 위치를 list에 기록해둔다.
                if (sudoku[i][j] == 0) {
                    list.add(i * 9 + j);
                    continue;
                }

                // 그리고 해당하는 3 * 3 크기의 사격형, 행, 열에 해당 수가 있음을 표시한다.
                rectangles[distinguishRectangle(i, j)][sudoku[i][j]]
                        = rows[i][sudoku[i][j]] = cols[j][sudoku[i][j]] = true;
            }
        }

        // 0번부터, 주어진 빈칸을 모두 채워나간다.
        findAnswer(0, list);

        //답안 작성
        StringBuilder sb = new StringBuilder();
        for (int[] row : sudoku) {
            for (int i : row)
                sb.append(i);
            sb.append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }

    static boolean findAnswer(int idx, List<Integer> list) {
        // 마지막 빈 칸까지 모두 할당이 끝났다면 true 반환.
        if (idx == list.size())
            return true;
        
        // 현재 빈칸의 위치
        int current = list.get(idx);
        // 행
        int row = current / 9;
        // 열
        int col = current % 9;
        // current가 속한 3 * 3 크기의 사각형
        int rectangle = distinguishRectangle(row, col);

        // 1부터 9까지의 수들 중
        for (int i = 1; i < 10; i++) {
            // 행, 열, 3 * 3 크기의 사각형에 아직 i가 등장하지 않았다면
            if (!rectangles[rectangle][i] && !rows[row][i] && !cols[col][i]) {
                // i를 할당해보고
                sudoku[row][col] = i;
                rectangles[rectangle][i] = rows[row][i] = cols[col][i] = true;

                // 다음 빈칸으로 넘어간다.
                // 만약 그렇게 오류가 없이 모든 빈 칸에 할당이 된다면 true가 반환되어 올것이고
                // 마찬가지로 true를 반환하며 메소드를 마친다.
                if (findAnswer(idx + 1, list))
                    return true;

                // 그렇지 않다면
                // current에 i를 할당해서는 안된다.
                // 다시 행과 열, 3 * 3 사각형에 i로 할당했던 것을 취소한다.
                rectangles[rectangle][i] = rows[row][i] = cols[col][i] = false;
            }
        }

        // 현재 조건으로 어떤한 수도 할당하는 것이 불가능하다면
        // 이전에 할당했던 수 중에 불가능한 경우가 존재하는 것이다.
        // false를 반환하여 이전 수 중 불가능한 경우를 수정하도록 한다.
        return false;
    }

    // r, c를 통해 몇번째의 3 * 3 사각형이 구별해낸다.
    static int distinguishRectangle(int r, int c) {
        return (r / 3) * 3 + c / 3;
    }
}