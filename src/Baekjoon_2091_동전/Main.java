/*
 Author : Ruel
 Problem : Baekjoon 2091번 동전
 Problem address : https://www.acmicpc.net/problem/2091
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2091_동전;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] values = {1, 5, 10, 25};

    public static void main(String[] args) throws IOException {
        // 1센트 동전 a개, 5센트 동전 b개, 10센트 동전 c개, 25센트 동전 d개가 주어진다.
        // 이를 통해 x원을 만들되, 가장 많은 동전을 사용하고 싶다.
        // 사용하는 동전의 개수를 구하라
        //
        // DP 문제
        // dp[값][사용된 동전] = 개수 로 정하고 dp를 채워나가며 문제를 해결한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 목표 금액
        int x = Integer.parseInt(st.nextToken());
        // 동전의 개수
        int[] coins = new int[4];
        for (int i = 0; i < coins.length; i++)
            coins[i] = Integer.parseInt(st.nextToken());
        
        // dp[i][j] = i원을 만드는데 j번째 동전이 사용된 개수
        // dp[i][4] = 사용된 동전의 총 개수
        int[][] dp = new int[x + 1][5];
        // i에서 추가로 동전을 사용한다.
        for (int i = 0; i < dp.length; i++) {
            // 만약 i가 0이 아닌데 사용된 동전이 0인 경우는
            // 초기값이어서, 불가능한 경우. 건너뛴다.
            if (i != 0 && dp[i][4] == 0)
                continue;

            // i원인 상태에서 j번째 동전을 하나 더 사용할 수 있는지 계산한다.
            for (int j = 0; j < 4; j++) {
                // 아직 j번째 동전이 여유가 있다면
                if (coins[j] - dp[i][j] > 0) {
                    // j번째 동전을 하나 더 사용하여 만들어지는 금액 sum
                    int sum = i + values[j];
                    // sum이 x를 넘지 않으며, 이전에 계산된 결과보다 더 많은 동전을 사용할 경우
                    if (sum < dp.length && dp[sum][4] < dp[i][4] + 1) {
                        // 해당 결과값으로 덮어쓴다.
                        for (int k = 0; k < dp[sum].length; k++)
                            dp[sum][k] = dp[i][k];
                        // 추가된 j번째 동전 반영
                        dp[sum][j]++;
                        dp[sum][4]++;
                    }
                }
            }
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++)
            sb.append(dp[x][i]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}