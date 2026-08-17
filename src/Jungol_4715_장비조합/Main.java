/*
 Author : Ruel
 Problem : Jungol 4715번 장비 조합
 Problem address : https://jungol.co.kr/problem/4715
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4715_장비조합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int LIMIT = 1_000_000_009;

    public static void main(String[] args) throws IOException {
        // N개의 장비 슬롯이 주어지고, 전투력은 S로 주어진다.
        // 슬롯마다 중요도 w가 있으며, 하나의 장비만 착용할 수 있고, 필수 장비 착용 여부 q가 주어진다.
        // 전투력은 슬롯의 중요도 * 장비의 강화레벨의 합으로 계산된다.
        // S 이하의 전투력을 만드는 경우의 수는?
        //
        // DP 문제
        // dp[현재까지 계산된 장비의 마지막 번호][전투력] = 경우의 수로 정의한다.
        // 점화식은
        // 필수 착용이 아닌 경우
        // dp[i][j] = dp[i-1][j] + dp[i][j-w]로 정의 된다.
        // 뜻은 i-1번째 장비까지 착용하고 전투력이 j인 경우와, i번째 장비를 착용하고 전투력이 j-w인 경우에서 i번째 장비의 강화레벨을 1 올린 경우이다.
        // 필수 착용인 경우는
        // dp[i][j] = dp[i-1][j - w] + dp[i][j-w]이다. i-1번째 장비까지 착용하고 전투력이 j-w이라 i번째 장비를 착용해서 j가 되는 경우
        // 그리고 마찬가지로 i번째 장비를 착용하여 전투력이 j-w인데, 해당 장비의 강화 레벨이 1 오르는 경우이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // N개의 장비 슬롯, 전투력 컷 S
        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());

        // DP
        long[][] dp = new long[N + 1][S + 1];
        // 모든 장비를 착용하지 않는 경우에서부터 시작
        dp[0][0] = 1;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            // 중요도 및 필수 착용 여부
            int w = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            // 필수 착용이 아닌 경우
            if (q == 0) {
                // 전투력 w까지는 이전 장비까지의 착용 결과만 반영
                for (int j = 0; j < w; j++)
                    dp[i + 1][j] = dp[i][j];
                // 그 이후는 이전 장비까지의 착용 경우의 수 + 현재 장비를 착용하고 있으면서 강화 레벨이 1오르는 경우
                for (int j = w; j <= S; j++)
                    dp[i + 1][j] = (dp[i][j] + dp[i + 1][j - w]) % LIMIT;
            } else {    // 필수 착용인 경우
                // w미만의 전투력은 존재하지 않는다.
                // w부터 시작하여
                // 현재 장비까지 착용하여 전투력이 되는 경우 + 현재 장비의 강화 레벨이 1오르는 경우
                for (int j = w; j <= S; j++)
                    dp[i + 1][j] = (dp[i][j - w] + dp[i + 1][j - w]) % LIMIT;
            }
        }
        // 전체 경우의 수
        long ans = 0;
        for (int j = 0; j < dp[N].length; j++)
            ans = (ans + dp[N][j]) % LIMIT;
        // 출력
        System.out.println(ans);
    }
}