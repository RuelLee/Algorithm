/*
 Author : Ruel
 Problem : Baekjoon 3908번 서로 다른 소수의 합
 Problem address : https://www.acmicpc.net/problem/3908
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3908_서로다른소수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 숫자 n을 k개 소수의 합으로 나타내는 경우의 수를 출력하라
        // t개의 테스트케이스가 주어진다.
        //
        // DP, 에라토스테네스의 체 문제
        // 모든 소수를 먼저 구하고
        // dp[합][선택한 수의 개수] = 경우의 수로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        
        // 소수
        boolean[] primeNumbers = new boolean[1121];
        Arrays.fill(primeNumbers, true);
        int[][] dp = new int[1121][15];
        primeNumbers[0] = primeNumbers[1] = false;
        for (int i = 2; i < primeNumbers.length; i++) {
            if (!primeNumbers[i])
                continue;

            for (int j = 2; i * j < primeNumbers.length; j++)
                primeNumbers[i * j] = false;
        }

        // 계산을 위한 초기값
        dp[0][0] = 1;
        for (int i = 2; i < primeNumbers.length; i++) {
            // 소수가 아니라면 건너뛴다.
            if (!primeNumbers[i])
                continue;

            // 동일한 수가 중복하여 계산되는 걸 막기 위해
            // 큰 값부터 작은 값으로 역순으로 계산한다.
            for (int j = dp.length - 1; j - i >= 0; j--) {
                // k개의 소수로 합이 j가 되는 경우는
                // k-1개의 소수로 합이 j- i가 되는 경우에
                // 소수 i를 더한 경우도 포함된다.
                for (int k = 1; k < dp[j].length; k++)
                    dp[j][k] += dp[j - i][k - 1];
            }
        }
        
        // 테스트 케이스 처리
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            sb.append(dp[n][k]).append("\n");
        }

        // 답안 출력
        System.out.print(sb);
    }
}