/*
 Author : Ruel
 Problem : Baekjoon 4811번 알약
 Problem address : https://www.acmicpc.net/problem/4811
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4811_알약;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 알약이 들어있고, 매일 알약을 반개씩 먹는다고 한다
        // 온전한 알약을 꺼내면, 반으로 쪼개 먹고서, W을 쓰고
        // 쪼개진 알약을 꺼내면 그대로 먹고서 H를 쓴다고 한다
        // n개의 알약을 모두 먹을 때, 쓸 수 있는 서로 다른 문자열의 가짓수는?
        //
        // DP문제
        // dp의 행을 전체 먹어야하는 일 수, 열을 반으로 쪼개진 알약의 개수라고 보자
        // n개의 알약이 주어진다면 2 * n일 동안 먹어야한다
        // 모두 온전한 알약으로 주어져있으므로, dp[2*n][0] = 1 한 가지이다
        // 이제 일반화해서 생각해보자
        // i날에 j개의 나뉘어진 알약이 남는 가짓수가 k라면
        // i - 1날에 j-1개의 나뉘어진 알약이 되는 가짓수도 k이고
        // i - 1 날에 j + 1개의 나뉘어진 알약이 되는 가짓수도 k이다.
        // 이를 계속 계산해 마지막 날에 쪼갠 알약이 0개가 되는 가짓수를 출력하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (true) {
            int n = Integer.parseInt(br.readLine());
            if (n == 0)
                break;

            // 알약을 쪼개 한쪽은 먹으므로, 쪼개진 알약의 최대 개수는 온전한 알약의 개수와 마찬가지로 n이다.
            long[][] pills = new long[n * 2 + 1][n + 1];
            // 첫날에는 쪼개진 알약이 없다.
            pills[2 * n][0] = 1;
            for (int i = pills.length - 1; i > 0; i--) {
                for (int j = 0; j < pills[i].length; j++) {
                    // j개의 쪼개진 알약이 있는 가짓수가 없다면 건너뛴다.
                    if (pills[i][j] == 0)
                        continue;

                    // j가 0보다 큰 경우(쪼개진 알약을 먹을 수 있는 경우)
                    // 다음 날에 j-1개의 쪼개진 알약이 남을 수 있는 경우.
                    if (j > 0)
                        pills[i - 1][j - 1] += pills[i][j];

                    // j가 n보다 작은 경우(온전한 알약을 먹어 쪼갤 수 있는 경우)
                    // 다음 날에 쪼개진 알약이 j + 1개 남을 수 있는 경우.
                    if (j < pills[i].length - 1)
                        pills[i - 1][j + 1] += pills[i][j];
                }
            }
            // 최종적으로 남은 0일에 쪼개진 알약이 0개가 되는 경우를 출력.
            sb.append(pills[0][0]).append("\n");
        }
        System.out.print(sb);
    }
}