/*
 Author : Ruel
 Problem : Baekjoon 10573번 증가하는 수
 Problem address : https://www.acmicpc.net/problem/10573
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10573_증가하는수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 증가하는 수는 수의 각 자리가 증가하거나 같은 경우이다.
        // ex) 111, 122, 123, ...
        // n개의 수가 주어질 때
        // 그 수가 증가하는 수라면 해당 수보다 작은 증가하는 수의 개수를
        // 그렇지 않다면 -1을 출력하라
        // 수는 80자리 수를 넘지 않는다.
        //
        // DP 문제
        // DP를 통해 각 자리에서 0부터 9까지로 시작하는 증가하는 수의 개수를 센다.
        // 그리고 수가 입력이 되면, 작은 자리부터, 가능한 증가하는 수의 개수를 누적시켜나간다.
        // 예를 들어 234라면, 
        // 일의 자리에 들어올 수 있는 수는, 십의 자리인 3보다 크거나 같고, 4보다 작은 수이다.
        // 따라서 3 하나만 들어올 수 있다.
        // 십의 자리에 들어올 수 있는 수는 2보다 크고, 3보다 작은 수이다.
        // 따라서 2로 시작하는 두자리 증가하는 수의 개수를 더한다.
        // 백의 자리에 들어올 수 있는 수는 1보다 크거나 같고(0으로 시작하는 수는 없으므로), 2보다 작은 수이다.
        // 따라서 1로 시작하는 세자리 증가하는 수의 개수를 더한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // dp[i][j]
        // 십의 i제곱 자리에서 j로 시작하는 증가하는 수의 개수를 계산한다.
        long[][] dp = new long[80][10];
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < dp.length; i++) {
            // 십의 i 제곱 자리에서
            for (int j = 0; j < dp[i].length; j++) {
                // j로 시작하는 증가하는 수의 개수는
                // 십의 (i - 1) 제곱 자리에서 j ~ 9까지의 증가하는 수의 개수 합과 같다.
                for (int k = j; k < dp[i - 1].length; k++)
                    dp[i][j] += dp[i - 1][k];
            }
        }

        // n개의 수
        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            // 입력 받은 수
            String input = br.readLine();
            // input보다 작은 증가하는 수 계산
            long answer = 0;
            // 증가하는 수인지 계산
            boolean asc = true;
            // 끝자리부터 계산한다.
            for (int j = 0; j < input.length() - 1; j++) {
                // 끝에서 j+1번째 자리수
                int current = input.charAt(input.length() - 1 - j) - '0';
                // 끝에서 j+2번째 자리수
                int left = input.charAt(input.length() - 2 - j) - '0';
                // 증가하는 수가 아니라면 종료
                if (left > current) {
                    asc = false;
                    break;
                }

                // 맞다면, 현재 자리에서 가능한 증가하는 수의 개수를 더한다.
                // j+1번째 자리에서 left보다 크거나 같고, current보다 작은 수의 개수를 더한다.
                for (int k = left; k < current; k++)
                    answer += dp[j][k];
            }
            
            // 마지막으로 가장 큰 자리수에 올 수 있는 증가하는 수의 개수를 더한다.
            // 1보다 크거나 같고, 해당 수보다는 작은 수로 시작하는 증가하는 수의 개수
            for (int j = 0; j < input.charAt(0) - '0'; j++)
                answer += dp[input.length() - 1][j];
            
            // 증가하는 수가 맞다면 answer 기록
            // 그렇지 않다면 -1 기록
            sb.append(asc ? answer : -1).append("\n");
        }
        // 전체 답안 출력
        System.out.println(sb);
    }
}