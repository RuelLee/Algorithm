/*
 Author : Ruel
 Problem : Jungol 1889번 N Queen
 Problem address : https://jungol.co.kr/problem/1889
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1889_NQueen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int n;
    static boolean[] col, leftUpRightDown, leftDownRightUp;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 체스판에 n개의 퀸을 서로 쓰러뜨릴 수 없는 위치에 놓는 경우의 수를 구하라
        //
        // 백트래킹 문제
        // n개의 퀸을 n * n 크기에 체스판에 놓으려면 각 행과 열에 무조건 하나씩 놓아야한다.
        // 다만 퀸은 대각선으로 이동이 가능하므로, 겹치는 대각선에 놓아서는 안된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 체스판에 n개의 퀸을 놓는다.
        n = Integer.parseInt(br.readLine());

        // 열 중복 여부 체크
        col = new boolean[n];
        // ↗과 ↘ 두 가지 대각선에 대해 중복 여부 체크
        leftUpRightDown = new boolean[n * 2 - 1];
        leftDownRightUp = new boolean[n * 2 - 1];

        int answer = countCases(0, 0);
        // 답 출력
        System.out.println(answer);
    }

    // 현재 r행을 살펴보고, 현재 queen개의 퀸을 놓았다.
    static int countCases(int r, int queen) {
        // queen이 n개라면 1개의 경우 구함
        if (queen == n)
            return 1;
        else if (r == n)        // 그러지 못했는데 마지막 행까지 지나쳤다면 0개
            return 0;

        int sum = 0;
        // 모든 열을 살펴보며
        for (int c = 0; c < n; c++) {
            // 열에 놓이지 않았고, 두 대각선에도 중복 여부가 없는 경우
            if (!col[c] && !leftUpRightDown[r - c + n - 1] && !leftDownRightUp[r + c]) {
                // 퀸을 놓고
                col[c] = true;
                leftUpRightDown[r - c + n - 1] = true;
                leftDownRightUp[r + c] = true;
                // 다음 행으로 넘어가 경우의 수를 센다.
                sum += countCases(r + 1, queen + 1);

                // 퀸 회수
                col[c] = false;
                leftUpRightDown[r - c + n - 1] = false;
                leftDownRightUp[r + c] = false;
            }
        }
        // 경우의 수 반환
        return sum;
    }
}