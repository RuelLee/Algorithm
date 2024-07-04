/*
 Author : Ruel
 Problem : Baekjoon 16876번 재미있는 숫자 게임
 Problem address : https://www.acmicpc.net/problem/16876
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16876_재미있는숫자게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;

    public static void main(String[] args) throws IOException {
        // 4자리 정수 n과 턴 m을 정한다.
        // 구사과가 먼저 시작하여 턴을 번갈아가며 진행한다.
        // 각 사람은 자기 턴이 됐을 때, 숫자 하나를 골라 1 증가시켜야한다.
        // 고른 숫자가 9인 경우는 0이 된다.
        // 게임이 끝난 후, 수가 n보다 커진 경우에는 구사과가 이기고, 그 외의 경우는 큐브러버가 이긴다.
        // n, m이 주어질 때, 이기는 사람을 구하라
        //
        // 게임 이론
        // 두 사람 모두 최적의 방법으로 움직이므로 게임 이론과 DP를 통해 풀 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 수 n, 턴 m
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // dp
        int[][] memo = new int[10000][101];
        // 수 n으로 m개 턴을 1이 먼저 진행했을 때, 승자를 구한다.
        // 1이면 구사과, 2면 큐브러버
        int answer = findAnswer(n, m, 1, memo);
        // 답 출력
        System.out.println(answer == 1 ? "koosaga" : "cubelover");
    }
    
    // 현재 수와 턴 그리고 게임을 진행하는 사람
    static int findAnswer(int num, int turn, int person, int[][] memo) {
        // 이미 결과가 있다면 해당 결과 반환.
        if (memo[num][turn] > 0)
            return memo[num][turn];
        else if (turn == 0)     // 턴이 0이 된 경우 결과를 낼 수 있다. num이 n보다 크다면 구사과, 그 외의 경우 큐브러버
            return memo[num][turn] = num > n ? 1 : 2;

        // 결과가 나지 않는 경우라면 할 수 있는 경우들을 모두 살펴본다.
        // 상대방이 초기값은 상대방이 이기는 값.
        memo[num][turn] = (person == 1 ? 2 : 1);
        // 4개의 자릿수를 하나씩 변경한다.
        for (int i = 0; i < 4; i++) {
            // (i + 1)번째 자리 수
            int number = (int) ((num % Math.pow(10, i + 1)) / Math.pow(10, i));

            // 만약 자리수가 9라면 0으로, 그 외의 경우는 1씩 증가시킨다.
            int next = num + (int) Math.pow(10, i) * (number == 9 ? -9 : 1);
            // 해당 수로 다음 턴을 넘겼을 때
            // person이 승리할 수 있는 경우가 있는지 찾는다.
            // 그러한 경우가 하나라도 발견된다면 person은 최적의 경우로 진행하므로
            // 반드시 그 값을 선택한다.
            // 따라서 dp에 해당 값을 기록하고 반복문 종료
            if (findAnswer(next, turn - 1, (person == 1 ? 2 : 1), memo) == person) {
                memo[num][turn] = person;
                break;
            }
        }
        // 찾은 값 반환.
        return memo[num][turn];
    }
}