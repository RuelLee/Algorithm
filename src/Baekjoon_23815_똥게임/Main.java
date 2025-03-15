/*
 Author : Ruel
 Problem : Baekjoon 23815번 똥게임
 Problem address : https://www.acmicpc.net/problem/23815
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23815_똥게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 처음 1명의 사람이 등장하며, 
        // n개의 턴 동안 4개의 선택지 중 2개가 등장한다.
        // +x, -x, *x, /x
        // n개의 선택지 중 한 번에 한해 선택지를 건너뛸 수 있다.
        // 턴을 진행하는 도중 사람이 0명 이하가 되면 게임 오버가 된다.
        // n번의 선택지를 거친 후, 가장 많은 사람을 만들고자할 때, 그 수는?
        // 만약 어떤 선택을 하더라도 0명이 된다면 ddong game을 출력한다
        //
        // dp 문제
        // 두 경우 모두 계산 후, 큰 값을 남기면 된다.
        // dp를 통해 스킵을 사용했는지 안했는지를 체크한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 선택지
        int n = Integer.parseInt(br.readLine());
        String[][] turns = new String[n][2];
        StringTokenizer st;
        for (int i = 0; i < turns.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < turns[i].length; j++)
                turns[i][j] = st.nextToken();
        }

        // 이전 결과값만 존재하면 되므르로
        // scores[현재와 과거 상태][스킵 여부] = 최댓값
        int[][] scores = new int[2][2];
        // 초기값은 1
        for (int i = 0; i < scores.length; i++)
            Arrays.fill(scores[i], 1);
        // 게임 오버됐는지 여부 체크
        boolean[] gameOver = new boolean[2];

        // 첫 선택지에서 선택을 하는 경우
        scores[1][0] = Math.max(calcChoice(scores[0][0], turns[0][0]), calcChoice(scores[0][0], turns[0][1]));
        if (scores[1][0] <= 0)
            gameOver[0] = true;

        // 두번째 이후로는 반복문으로 처리
        // 스킵 여부에 따른 두 경우 모두 게임오버 된 경우 반복문 종료
        for (int i = 1; i < turns.length && (!gameOver[0] || !gameOver[1]); i++) {
            // 이미 스킵을 한 경우가 아직 게임오버가 되지 않은 경우.
            if (!gameOver[1]) {
                // 이번 두 선택지 더 큰 값을 계산한다.
                int score = Math.max(calcChoice(scores[i % 2][1], turns[i][0]), calcChoice(scores[i % 2][1], turns[i][1]));
                // 그 값이 0이하라면 게임오버
                if (score <= 0)
                    gameOver[1] = true;
                // 값 기록
                scores[(i + 1) % 2][1] = score;
            }

            // 스킵을 아직 안한 경우이며 게임 오버가 안된 경우.
            if (!gameOver[0]) {
                // 이번 경우에 스킵을 하는 경우의 점수가
                // 기존에 계산된 스킵한 경우의 점수보다 같거나 크거나
                // 스킵을 한 경우가 이미 게임 오버 됐다면
                if (scores[i % 2][0] >= scores[(i + 1) % 2][1] ||
                        gameOver[1]) {
                    // 현재 선택지에서 스킵한 경우를
                    // 스킵한 경우에 계산한다.
                    scores[(i + 1) % 2][1] = scores[i % 2][0];
                    // 게임 오버가 됐더라도 현 상태로 덮어쓸 수 있다.
                    gameOver[1] = false;
                }
                
                // 스킵을 하지 않고 선택지를 고르는 경우
                int nonSkipMax = Math.max(calcChoice(scores[i % 2][0], turns[i][0]), calcChoice(scores[i % 2][0], turns[i][1]));
                // 만약 점수가 0이하라면 게임 오버
                if (nonSkipMax <= 0)
                    gameOver[0] = true;
                scores[(i + 1) % 2][0] = nonSkipMax;
            }
        }

        // 스킵 여부와 게임 오버 여부를 고려하여 최대 점수를 구한다.
        int max = Math.max(gameOver[0] ? 0 : scores[n % 2][0], gameOver[1] ? 0 : scores[n % 2][1]);
        // 최대 점수가 0이하라면 ddong game 출력
        if (max <= 0)
            System.out.println("ddong game");
        else        // 그 외의 경우 최대 점수 출력
            System.out.println(max);
    }

    // 현재 점수 score와 선택지가 주어질 때
    // 해당 선택을 한 경우의 값을 반환한다.
    static int calcChoice(int score, String choice) {
        char operator = choice.charAt(0);
        int num = choice.charAt(1) - '0';

        if (operator == '+')
            return score + num;
        else if (operator == '-')
            return score - num;
        else if (operator == '*')
            return score * num;
        else
            return score / num;
    }
}