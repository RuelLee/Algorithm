/*
 Author : Ruel
 Problem : Jungol 4322번 짝퉁 피보나치 (일당)
 Problem address : https://jungol.co.kr/problem/4322
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4322_짝퉁피보나치_일당;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 다음과 같은 수열을 만드낟.
        // 첫번째, 두번째 수는 1이다.
        // 2 * k번째 수는 k번째 수와 같고, 2k + 1번째 수는 k번째, k+1번째 수의 합과 같다.
        // n이 주어질 때마다 max(A[1] ~ A[n])의 값을 구하라
        //
        // 브루트 포스 문제
        // n이 최대 10만으로 그리 크지 않으므로
        // 10만까지 수열을 모두 구하고, 각 범위의 답에 대해 미리 구해둔다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 수열
        int[] dp = new int[100001];
        // 첫번째, 두번째 수
        dp[0] = dp[1] = 1;
        for (int i = 2; i < dp.length; i++) {
            // 짝수 인 경우, i / 2와 같고
            if (i % 2 == 0)
                dp[i] = dp[i / 2];
            else        // 홀수인 경우는 i/2번째와 i/2 + 1번째의 합과 같다.
                dp[i] = dp[i / 2] + dp[i / 2 + 1];
        }

        // 각 답은 1 ~ n번째 수 중 최대값이므로
        // 이전 답과 이번 수를 비교하여 답을 기록해둔다.
        int[] answers = new int[100001];
        for (int i = 1; i < answers.length; i++)
            answers[i] = Math.max(answers[i - 1], dp[i]);

        // 모든 테스트케이스들에 대해 답 처리
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());
        while (n != 0) {
            sb.append(answers[n]).append("\n");
            n = Integer.parseInt(br.readLine());
        }
        // 답 출력
        System.out.print(sb);
    }
}