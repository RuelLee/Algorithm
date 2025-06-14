/*
 Author : Ruel
 Problem : Baekjoon 2590번 색종이
 Problem address : https://www.acmicpc.net/problem/2590
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2590_색종이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1*1, 2*2, ... , 6*6 크기의 색종이가 주어지고
        // 6*6 크기의 판에 색종이를 붙인다.
        // 색종이들은 서로 겹쳐서는 안되고, 판을 벗어나서도 안된다.
        // 모든 색종이를 붙이는데 필요한 판의 최소 개수는?
        //
        // 브루트 포스, 백트래킹
        // 많은 조건 분기로.. 풀 수 있다고 한다.
        // 뭐 6일 때는 한 장, 5일 때는 하나 붙이고, 남은 자리에 1
        // 4일 때는 하나 붙이고 남은 자리에 2 혹은 1을 채워나가는 방법으로 할 수도 있지만
        // 너무 조건이 많고, 고려해야할 점도 많고 복잡해져서
        // 그냥 브루트 포스로 일일이 붙이고, 이를 백트래킹 하는 방법으로 구했다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각 크기의 색종이들
        int[] papers = new int[7];
        for (int i = 1; i < papers.length; i++)
            papers[i] = Integer.parseInt(br.readLine());

        // 필요한 판의 개수
        int count = 0;
        // 판의 상탠
        boolean[][] board = new boolean[6][6];
        while (true) {
            // 남은 색종이의 수
            int sum = 0;
            for (int i = 1; i < papers.length; i++)
                sum += papers[i];
            // 모든 색종이를 사용했다면 종료
            if (sum == 0)
                break;

            // 판 초기화
            for (boolean[] b : board)
                Arrays.fill(b, false);

            // 6*6부터 색종이들을 빈 공간에 붙여 나간다.
            for (int i = 6; i > 0; i--)
                fillPaper(0, i, papers, board);
            // 현재 판의 개수 증가
            count++;
        }
        // 사용한 판의 개수 출력
        System.out.println(count);
    }

    // idx 위치에 size 크기의 색종이를 붙일 수 있는지 확인한다.
    // 현재 남은 색종이 papers, 판의 상태 board
    static void fillPaper(int idx, int size, int[] papers, boolean[][] board) {
        // 해당 크기의 남은 색종이가 0이거나, 판의 마지막까지 살펴봤다면 종료
        if (papers[size] == 0 || idx >= 36)
            return;

        // 현재 위치
        int row = idx / 6;
        int col = idx % 6;
        // 현재 위치에 size 크기의 색종이를 붙일 경우
        // 판을 벗어나는지, 다른 색종이가 붙어있지 않은지 확인
        if (row + size - 1 < 6 && col + size - 1 < 6) {
            boolean possible = true;
            for (int i = row; i < row + size; i++) {
                for (int j = col; j < col + size; j++) {
                    if (board[i][j]) {
                        possible = false;
                        break;
                    }
                }
            }

            // 가능하다면 해당 위치에 색종이를 붙인다.
            if (possible) {
                for (int i = row; i < row + size; i++) {
                    for (int j = col; j < col + size; j++)
                        board[i][j] = true;
                }
                papers[size]--;
            }
        }
        // 그리고 다음 idx로 넘김.
        fillPaper(idx + 1, size, papers, board);
    }
}