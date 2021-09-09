/*
 Author : Ruel
 Problem : Baekjoon 7682번 택택토
 Problem address : https://www.acmicpc.net/problem/7682
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 틱택토;

import java.util.Scanner;

public class Main {
    static char[][] board;

    public static void main(String[] args) {
        // 틱택토 문제
        // 틱택토는 가로, 세로, 대각선으로 먼저 돌을 3개 놓으면 이기는 게임
        // 돌은 X, O가 있으며 X가 먼저 둔다. 이 때 주어진 상황이 최종상태인지 판별하는 문제
        // 틱택토 규칙을 떠올리며, 어느 경우일 때 게임이 끝나는지 생각해보자
        // X가 O보다 먼저두므로, X의 개수는 O보다 하나 많거나 같다
        // X가 하나 많은 시점에서 게임 끝나는 경우는, X가 연속한 3개의 돌을 두거나, X가 마지막으로 게임판의 모든 칸을 채웠을 때다
        // X와 O의 개수가 같은 시점에서 게임이 끝나는 경우는 X는 연속한 3개의 돌을 두지 못했고, O가 먼저 연속한 3개의 돌을 두었을 때다.
        // 이 부분에 유의하며 풀자!
        Scanner sc = new Scanner(System.in);

        StringBuilder sb = new StringBuilder();
        while (true) {
            String input = sc.nextLine();
            if (input.equals("end"))
                break;

            board = new char[3][3];
            int x = 0;
            int o = 0;
            int empty = 0;
            for (int i = 0; i < 9; i++) {
                board[i / 3][i % 3] = input.charAt(i);
                if (input.charAt(i) == 'O')
                    o++;
                else if (input.charAt(i) == 'X')
                    x++;
                else
                    empty++;
            }
            if (x == o + 1 && !are3Stones('O') && (empty == 0 || are3Stones('X')))      // X의 개수가 O보다 많은 시점은 O이 연속하지 않고, 빈자리의 개수가 없거나, X가 연속했을 때.
                sb.append("valid").append("\n");
            else if (x == o && !are3Stones('X') && are3Stones('O'))     // X와 O의 개수가 같을 때 끝나는 경우는 X는 연속한 3개를 못 만들었고, O만 연속한 3개를 만들었을 때.
                sb.append("valid").append("\n");
            else
                sb.append("invalid").append("\n");
        }
        System.out.println(sb);
    }

    static boolean are3Stones(char stone) {     // 가로, 세로, 대각선으로 stone이 연속하여 놓여있는지 검사한다.
        for (int i = 0; i < 3; i++) {       // 가로줄이 연속한지 검사한다.
            if (board[i][0] == stone && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return true;
        }

        for (int i = 0; i < 3; i++) {   // 세로 줄이 연속한지 검사한다.
            if (board[0][i] == stone && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return true;
        }
        if (board[1][1] == stone) {     // 대각선이 연속한지 검사한다.
            if ((board[0][0] == board[1][1] && board[2][2] == board[1][1]) || board[0][2] == board[1][1] && board[2][0] == board[1][1])
                return true;
        }
        // 위 조건들을 만족하지 않는다면 연속된 3개의 돌이 없다.
        return false;
    }
}