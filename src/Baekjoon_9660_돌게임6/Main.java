/*
 Author : Ruel
 Problem : Baekjoon 9660번 돌 게임 6
 Problem address : https://www.acmicpc.net/problem/9660
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9660_돌게임6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] delta = {1, 3, 4};

    public static void main(String[] args) throws IOException {
        // 탁자 위에 돌이 n개 있다.
        // 이를 SK와 CY이 한번에 1개, 3개 또는 4개 가져갈 수 있다.
        // 가져갈 돌이 없는 사람이 지게 된다.
        // SK가 먼저 시작하며, 두 사람 모두 완벽하게 게임을 했을 때
        // 이기는 사람을 구하라
        //
        // 게임 이론
        // 이지만 n이 최대 1조까지 매우 큰 값으로 주어지므로
        // 일일이 계산한다는 것은 말이 안된다.
        // 위 경우는 직접 몇가지 경우들을 구해 사이클이 생기는지 확인하는 것이다.
        // 그 결과 약 7개를 기준으로 사이클이 발생함을 알았고
        // 이를 통해 n을 7 모듈러 연산을 통해 쉽게 답을 구할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 돌의 개수 최대 1조개
        long n = Long.parseLong(br.readLine());

        // 첫 사람이 이기는가.
        boolean[] firstWin = new boolean[10];
        // 한 번에 가져갈 수 있는 돌의 양
        firstWin[1] = firstWin[3] = firstWin[4] = true;
        // 5부터 계산.
        for (int i = 5; i < firstWin.length; i++) {
            for (int d = 0; d < delta.length; d++) {
                // 자신이 돌을 가져갔을 때, 상대방이 지는 경우만 존재한다면
                // 자신이 이길 수 있다.
                if (!firstWin[i - delta[d]]) {
                    firstWin[i] = true;
                    break;
                }
            }
        }

        // 7개마다 사이클이 발생하므로
        // n % 7 결과값을 출력하면 된다.
        // true라면 SK가 이기고, false라면 CY가 이긴다.
        System.out.println(firstWin[(int) (n % 7)] ? "SK" : "CY");
    }
}