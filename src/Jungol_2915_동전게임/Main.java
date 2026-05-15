/*
 Author : Ruel
 Problem : Jungol 2915번 동전 게임
 Problem address : https://jungol.co.kr/problem/2915
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2915_동전게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 영희와 동수가 동전 던지기를 총 k라운드 한다.
        // 결과에 따라 각각 점수를 1점 혹은 0을 얻는다.
        // 영희가 먼저 진행하며,
        // 두 사람 모두 남은 기회 동안 모두 1점을 획득하더라도
        // 상대방을 이길 수 없는 시점에서 게임은 끝난다.
        // c개의 영희의 점수 m과 동수의 점수 n이 주어진다.
        // 각각이 가능한 점수인지 출력하라
        //
        // DP 문제
        // dp를 통해, (0, 0)점부터 가능한 점수들을 표시해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 총 동전 던지기 라운드 수 k
        int k = Integer.parseInt(br.readLine());
        // 주어지는 질문의 수 c개
        int c = Integer.parseInt(br.readLine());

        boolean[][] dp = new boolean[k + 1][k + 1];
        // 초기값은 가능
        dp[0][0] = true;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // i, j점이 불가능한 경우 건너뜀.
                if (!dp[i][j])
                    continue;

                // 남은 라운드 수
                int remain = k - Math.max(i, j);
                // 남은 라운드를 영희가 모두 이기더라도 동수와 동점이 되는 것이 불가능한 경우
                // 해당 라운드는 진행하지 않고 종료
                if (Math.min(i, j) + remain < Math.max(i, j))
                    continue;

                // 영희가 아직 k점이 아니고, 동수가 남은 라운드에서 모두 1점을 내 i점이상이 될 수 있다면
                // 영희가 이번 라운드에 동전을 던지며 1점을 낼 수 있다.
                if (i + 1 <= k && i <= j + remain) {
                    dp[i + 1][j] = true;
                    // 영희의 승리가 확정되지 않은 경우.
                    // 동수 또한 턴을 진행하며 j+1점이 될 수 있다.
                    if (j + 1 <= k && i + 1 <= j + remain)
                        dp[i + 1][j + 1] = true;
                }

                // 영희가 이번 라운드에 0점을 내고 남은 라운드에서 모두 1점을 내는 점수가
                // j점 이상이 될 수 있는 경우
                // 동수도 턴을 진행하며 j+1점이 될 수 있다.
                if (j + 1 <= k && i + remain - 1 >= j)
                    dp[i][j + 1] = true;
            }
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        // c개의 질문 처리
        for (int i = 0; i < c; i++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            sb.append(dp[n][m] ? 1 : 0).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}