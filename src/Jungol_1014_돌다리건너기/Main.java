/*
 Author : Ruel
 Problem : Jungol 1014번 돌다리 건너기
 Problem address : https://jungol.co.kr/problem/1014
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1014_돌다리건너기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 줄의 징검다리가 주어진다.
        // 각 돌에는 문자가 새겨져있으며 R I N G S 중 하나이다.
        // 마법의 두루마리에는 하나의 문자열이 적혀있고
        // 이 문자열 대로 징검다리를 밟고 지나가야한다.
        // 반드시 두 징검다리를 번갈아가면서 밟아야하고,
        // 이전에 밟은 돌보다 하나 이상 전진해야한다.
        // 가능한 경우의 수를 구하라
        //
        // DP 문제
        // dp를 통해
        // dp[징검다리 위 아래 여부][돌의 위치][현재까지 진행한 문자의 수] = 경우의 수
        // 로 놓고 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 마법의 두루마리
        String order = br.readLine();
        // 징검 다리
        char[][] ladders = new char[2][];
        for (int i = 0; i < ladders.length; i++)
            ladders[i] = br.readLine().toCharArray();

        // dp
        int[][][] dp = new int[2][ladders[0].length][order.length()];
        // 첫 문자에 경우의 수 1 할당
        for (int i = 0; i < ladders.length; i++) {
            for (int j = 0; j < ladders[i].length; j++) {
                if (ladders[i][j] == order.charAt(0))
                    dp[i][j][0] = 1;
            }
        }

        // 가장 첫 돌부터 살펴본다.
        for (int j = 0; j < dp[0].length; j++) {
            // 위 아래 징검다리를 번갈아가며 보며
            for (int i = 0; i < dp.length; i++) {
                // 현재 진행한 문자의 위치를 모두 살펴본다.
                for (int k = 0; k < dp[i][j].length - 1; k++) {
                    // 경우의 수가 0이 아니라면
                    if (dp[i][j][k] != 0) {
                        // 다른 징검다리, j+1 위치부터 다음 문자와 일치하는 돌을 찾는다.
                        // 해당 돌을 찾아 경우의 수를 누적
                        for (int l = j + 1; l < dp[(i + 1) % 2].length; l++) {
                            if (ladders[(i + 1) % 2][l] == order.charAt(k + 1))
                                dp[(i + 1) % 2][l][k + 1] += dp[i][j][k];
                        }
                    }
                }
            }
        }

        int sum = 0;
        // 모든 돌을 살펴보며 두루마리의 모든 문자를 진행한 경우의 수를 누적한다.
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++)
                sum += dp[i][j][order.length() - 1];
        }
        // 답 출력
        System.out.println(sum);
    }
}