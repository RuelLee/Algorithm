/*
 Author : Ruel
 Problem : Jungol 1749번 구슬게임
 Problem address : https://jungol.co.kr/problem/1749
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1749_구슬게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] b;
    static int[][] memo;

    public static void main(String[] args) throws IOException {
        // 두 개의 주머니에 k1개, k2개의 구슬이 담겨있다.
        // 두 주머니 중 하나를 골라 b1 b2 b3 개들 중 한 가지 경우로 구슬을 가져갈 수 있다.
        // 이 과정을 A와 B가 반복한다.
        // 더 이상 구슬을 가져갈 수 없다면 진다.
        // b1 b2 b3가 주어지고, k1 k2가 5가지 경우로 주어질 때
        // 각각 이기는 사람을 출력하라
        //
        // 게임 이론, 백트래킹 문제
        // memo[k1][k2] = 현재 상태를 접한 사람이 이기는지 여부
        // 로 정하고 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 구슬을 가져갈 수 있는 3가지 경우의 수
        b = new int[3];
        for (int i = 0; i < b.length; i++)
            b[i] = Integer.parseInt(st.nextToken());

        // 각 주머니에 남은 구슬에 따른 승리 여부
        memo = new int[501][501];
        // 가져갈 게 없다면 진다.
        memo[0][0] = 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine());
            // 주어진 k1 k2
            int k1 = Integer.parseInt(st.nextToken());
            int k2 = Integer.parseInt(st.nextToken());
            // 결과 기록
            sb.append((char) (findAnswer(k1, k2) - 1 + 'A')).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // k1 k2개 남았을 때의 결과
    static int findAnswer(int k1, int k2) {
        // 이미 계산된 결과라면 값 반환
        if (memo[k1][k2] != 0)
            return memo[k1][k2];

        // 기본 값으로 진다고 채워두고
        memo[k1][k2] = 2;
        for (int i = 0; i < 3; i++) {
            // 각각의 주머니에서 b[i]개의 구슬을 가져갔을 때
            // 상대방이 반드시 지는 경우가 있는지 확인한다.
            // 해당 경우가 존재한다면 나는 그 경우의 수를 택하면 되므로 이긴다.
            if (k1 >= b[i] && findAnswer(k1 - b[i], k2) == 2) {
                memo[k1][k2] = 1;
                break;
            }

            if (k2 >= b[i] && findAnswer(k1, k2 - b[i]) == 2) {
                memo[k1][k2] = 1;
                break;
            }
        }
        // 결과 반환
        return memo[k1][k2];
    }
}