/*
 Author : Ruel
 Problem : Baekjoon 14238번 출근 기록
 Problem address : https://www.acmicpc.net/problem/14238
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14238_출근기록;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 매일 출근할 수 있는 A, 격일로 출근하는 B,
        // 한번 출근하면 이틀을 쉬어야하는 C가 있다.
        // 출근 기록 S까 주어졌을 때, S의 모든 순열 중 올바른 출근 기록 중 하나를 출력하라
        //
        // DP 문제
        // 각 사람마다 연속하여 출근할 수 있는 정도가 다르다.
        // 특히 C의 경우, 이틀을 걸러 출근해야하므로 DP에 어제와 그제 출근한 기록이 필요하다
        // 따라서
        // dp[a의 남은 출근 횟수][b의 남은 출근 횟수][c의 남은 출근 횟수][어제와 그제 출근한 사람들] = 사흘전과 그제 출근한 사람들
        // 로 표시하였다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 출근 기록
        String record = br.readLine();

        // 기록으로 각 사람들이 전체 출근한 일을 센다.
        int[] counts = new int[4];
        for (int i = 0; i < record.length(); i++) {
            switch (record.charAt(i)) {
                case 'A' -> counts[1]++;
                case 'B' -> counts[2]++;
                case 'C' -> counts[3]++;
            }
        }

        // a가 출근해야하는 수, b가 출근해야하는 수, c가 출근해야하는 수, 어제와 그제 출근한 사람
        // 어제와 그제 출근한 사람은 16개의 값으로
        // 그제 출근한 사람이 A라면 +4, B라면 +8, C라면 +12
        // 어제 출근한 사람이 A라면 +1, B라면 +2, C라면 +3 으로 하여 하나의 수로 표현하엿다.
        int[][][][] dp = new int[counts[1] + 1][counts[2] + 1][counts[3] + 1][16];
        // -1로 초기화
        for (int[][][] a : dp) {
            for (int[][] b : a) {
                for (int[] c : b)
                    Arrays.fill(c, -1);
            }
        }
        // 모두 출근하지 않은 상태에서 시작.
        dp[counts[1]][counts[2]][counts[3]][0] = 0;

        for (int i = dp.length - 1; i >= 0; i--) {
            for (int j = dp[i].length - 1; j >= 0; j--) {
                for (int k = dp[i][j].length - 1; k >= 0; k--) {
                    for (int l = 0; l < dp[i][j][k].length; l++) {
                        // 만약 초기값이라면 해당 상황이 불가능한 경우
                        // 건너뛴다.
                        if (dp[i][j][k][l] == -1)
                            continue;

                        // 그제 출근한 사람
                        int pp = l / 4;
                        // 어제 출근한 사람
                        int p = l % 4;

                        // A는 항상 출근할 수 있으므로, A가 출근해야하는 날이 남았다면
                        // A의 출근 횟수 차감 후, p * 4 + 1을 한 위치에 현재 l값을 넣는다.
                        if (i > 0)
                            dp[i - 1][j][k][p * 4 + 1] = l;
                        // B는 격일로 출근해야하므로, p가 2가 아니고, B가 출근해야하는 날이 남았다면
                        if (j > 0 && p != 2)
                            dp[i][j - 1][k][p * 4 + 2] = l;
                        // C는 이틀을 쉬어야하므로, pp와 p 모두 3이 아니고, C가 출근해야하는 날이 남은 경우에
                        if (k > 0 && pp != 3 && p != 3)
                            dp[i][j][k - 1][p * 4 + 3] = l;
                    }
                }
            }
        }

        // 출근해야하는 날을 모두 소진한 dp[0][0][0]에서
        // 초기값이 아닌 값 하나를 찾는다.
        int state = 0;
        for (int i = 0; i < dp[0][0][0].length; i++) {
            if (dp[0][0][0][i] != -1) {
                state = i;
                break;
            }
        }

        // DP 기록된 값을 바탕으로 올라가며 가능한 경우를 찾아간다.
        // 가능한 출근 기록이라면 역순으로 출근하더라도 가능한 경우이므로
        // StringBuilder에 출근을 기록한다.
        StringBuilder sb = new StringBuilder();
        int a = 0;
        int b = 0;
        int c = 0;
        // state가 0이 될 때까지 (시작하는 값을 0으로 지정했으므로)
        while (state != 0) {
            // 어제 출근한 사람
            int p = state % 4;
            // dp[a][b][c][state]에 기록된 값을 다음 살펴볼 state로 가져온다.
            state = dp[a][b][c][state];
            switch (p) {
                // 어제 출근한 사람이 A라면
                case 1 -> {
                    sb.append('A');
                    a++;
                }
                // 어제 출근한 사람이 B라면
                case 2 -> {
                    sb.append('B');
                    b++;
                }
                // 어제 출근한 사람이 C라면
                case 3 -> {
                    sb.append('C');
                    c++;
                }
            }
        }

        // StringBuilder가 비어있다면 불가능한 경우이므로 -1 출력
        // 그렇지 않다면 StringBuilder에 기록된 답안 출력.
        System.out.println(sb.isEmpty() ? -1 : sb);
    }
}