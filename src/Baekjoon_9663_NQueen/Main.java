/*
 Author : Ruel
 Problem : Baekjoon 9663번 N-Queen
 Problem address : https://www.acmicpc.net/problem/9663
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9663_NQueen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int count = 0;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 체스판이 주어질 때
        // n개의 퀸을 서로 공격할 수 없게 배치하는 경우의 수는?
        //
        // 유명한 백트래킹 문제
        // 퀸은 가로, 세로, 대각선으로 공격할 수 있으므로
        // 각 가로, 세로, 대각선에 퀸이 놓였음을 표시하고 그렇지 않은 위치에 퀸을 배치해야한다.
        // n개의 퀸을 세로 n줄에 놓아야하므로, 각 줄에 하나씩 배치하며,
        // 적절한 위치의 세로를 찾으며,
        // 대각선 공격 여부는 ↗방향의 경우는 가로 세로의 합
        // ↘는 가로 세로의 차로 표시한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력으로 주어지는 n
        int n = Integer.parseInt(br.readLine());
        // 백트래킹을 통해 0번 세로부터 n - 1번 세로줄까지 퀸을 배치한다.
        backTracking(0, new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1], n);

        System.out.println(count);
    }

    static void backTracking(int row, boolean[] col, boolean[] leftDownToRightUp, boolean[] leftUpToRightDown, int n) {
        // 퀸을 모두 배치하는 것이 끝난 경우.
        if (row == n) {
            // 경우의 수 하나 증가
            count++;
            // 종료
            return;
        }


        for (int i = 0; i < n; i++) {
            // ↗ 방향 값
            int ldtru = row + i;
            // ↘ 방향 값. 음수가 나올 수 있으므로 값을 보정해준다.
            int lutrd = row - i + n - 1;
            
            // 해당하는 i 위치에 세로, 대각선으로 공격할 수 있는 다른 퀸이 없다면
            if (!col[i] && !leftDownToRightUp[ldtru] && !leftUpToRightDown[lutrd]) {
                // 해당 위치에 퀸을 배치했음을 표시하고
                col[i] = true;
                leftDownToRightUp[ldtru] = true;
                leftUpToRightDown[lutrd] = true;
                // row + 1번째 줄로 넘어가는 함수를 호출한다.
                backTracking(row + 1, col, leftDownToRightUp, leftUpToRightDown, n);

                // 위의 함수 끝났다면 다시 i번 위치에 놓은 퀸을 회수.
                col[i] = false;
                leftDownToRightUp[ldtru] = false;
                leftUpToRightDown[lutrd] = false;
            }
        }
    }
}