/*
 Author : Ruel
 Problem : Baekjoon 3687번 성냥개비
 Problem address : https://www.acmicpc.net/problem/3687
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3687_성냥개비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] matches = new int[]{6, 2, 5, 5, 4, 5, 6, 3, 7, 6};

    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스마다 n이 주어진다.
        // 각 수는 0부터 9까지 6, 2, 5, 5, 4, 5, 6, 3, 7, 6개의 성냥개비로 표현할 수 있다.
        // n개의 성냥개비로 만들 수 있는 최소, 최대 수를 구하라
        //
        // DP, 그리디 문제
        // 성냥개비의 개수를 토대로 dp를 통해 각 성냥개비로 만들 수 있는 최솟값을 찾는다.
        // 최댓값은 그리디로 풀 수 있는데, 가능한 자릿수를 늘리는 것이 유리하다.
        // 가장 적은 성냥개비를 사용하는 수는 1이다. 또한 홀수개의 성냥개비 중 가장 적은 것은 7로 3개를 사용한다.
        // 따라서 n이 홀수일 경우, 가장 앞에 7을 배치하고 남은 1을 모두 배치하는 것이 유리하고,
        // 짝수일 경우는 전부 1로 채우는 것이 유리하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // dp[성냥의 개수] = 최솟값
        long[] dp = new long[101];
        Arrays.fill(dp, Long.MAX_VALUE);
        // 하나의 수로 만들 수 있는 값
        // 0으로 시작할 수는 없다했으므로 0 제외
        for (int i = 1; i < matches.length; i++)
            dp[matches[i]] = Math.min(dp[matches[i]], i);
        // 현재 성냥의 수에서 뒤에 추가로 수를 붙여 수를 만들 경우
        // 해당하는 사용 성냥개수로 만들 수 있는 최솟값을 갱신하는지 확인.
        for (int i = 2; i < dp.length; i++) {
            for (int j = 0; j < matches.length; j++) {
                if (i + matches[j] < dp.length)
                    dp[i + matches[j]] = Math.min(dp[i + matches[j]], dp[i] * 10 + j);
            }
        }

        StringBuilder sb = new StringBuilder();
        // 테스트케이스 개수
        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) {
            // n개의 성냥개비
            int n = Integer.parseInt(br.readLine());
            // 최솟값은 dp의 값을 사용
            sb.append(dp[n]).append(" ");

            // 최댓값은 홀수일 경우 앞에 7을 배치하고
            if (n % 2 == 1) {
                sb.append(7);
                n -= 3;
            }
            // 나머지 짝수개의 성냥개비만큼 1을 배치
            while (n > 0) {
                sb.append(1);
                n -= 2;
            }
            sb.append("\n");
        }
        // 답 출력
        System.out.println(sb);
    }
}