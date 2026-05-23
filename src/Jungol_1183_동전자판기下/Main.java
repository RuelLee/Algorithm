/*
 Author : Ruel
 Problem : Jungol 1183번 동전 자판기(下)
 Problem address : https://jungol.co.kr/problem/1183
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1183_동전자판기下;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 구입하려는 가격 w가 주어진다.
        // 갖고 있는 500, 100, 50, 10, 5, 1원짜리 동전의 개수가 주어진다.
        // 정확히 w원을 맞추되, 가장 많은 동전을 사용하고 싶다면
        // 동전 개수의 총합과, 각 사용한 동전의 개수를 출력하라
        //
        // 배낭 문제
        // dp[i][j] = j가 0 ~ 5까지는 각 사용한 동전의 수, 6은 사용한 동전의 총 개수로 정하고 채워나간다.
        // dp[i][6]을 비교하며 가장 많은 동전을 사용한 경우를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 만들고자 하는 금액
        int w = Integer.parseInt(br.readLine());
        // 동전의 값어친
        int[] values = new int[]{500, 100, 50, 10, 5, 1};
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 동전의 개수
        int[] coins = new int[6];
        for (int i = 0; i < 6; i++)
            coins[i] = Integer.parseInt(st.nextToken());

        // 배낭
        int[][] dp = new int[w + 1][7];
        for (int i = 0; i < dp.length; i++) {
            // 0원이 아닌데 사용한 개수가 0개라면
            // 불가능한 경우이므로 건너뛴다.
            if (i != 0 && dp[i][6] == 0)
                continue;

            // j개의 동전을 살펴본다.
            for (int j = 0; j < 6; j++) {
                // j번째 동전의 추가할 수 있는 개수만큼
                for (int k = 1; dp[i][j] + k <= coins[j] && i + values[j] * k <= w; k++) {
                    // 다음 금액
                    int next = i + values[j] * k;
                    // next에 현재 기록된 동전의 개수 총합보다
                    // i에서 j번재 동전을 k개 추가하는게 더 많은 경우
                    if (dp[next][6] < dp[i][6] + k) {
                        for (int l = 0; l < 6; l++)
                            dp[next][l] = dp[i][l];
                        dp[next][j] = dp[i][j] + k;
                        dp[next][6] = dp[i][6] + k;
                    }
                }
            }
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        sb.append(dp[w][6]).append("\n");
        sb.append(dp[w][0]);
        for (int i = 1; i < 6; i++)
            sb.append(" ").append(dp[w][i]);
        // 답 출력
        System.out.println(sb);
    }
}