/*
 Author : Ruel
 Problem : Baekjoon 23325번 마법천자문
 Problem address : https://www.acmicpc.net/problem/23325
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23325_마법천자문;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // +와 -로만 이루어진 문자열이 주어진다.
        // +는 더하기 일수도 10일 수도 있고
        // - 는 빼기 일수도 1일 수도 있다.
        // 문자열을 해석하여 가장 큰 값을 얻고자할 때
        // 그 값은?
        //
        // DP 문제
        // dp[i] = i까지의 문자열을 해석했을 때의 최대값
        // 으로 지정하고 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 문자열
        char[] string = br.readLine().toCharArray();
        
        // dp
        int[] dp = new int[string.length];
        // 초기값 세팅
        Arrays.fill(dp, Integer.MIN_VALUE);
        // 첫 문자에 따라 0번 값 지정
        dp[0] = (string[0] == '-' ? 1 : 10);
        // 만약 첫 두글자가 +-일 경우, dp[1]에 11 값 지정
        if (string.length >= 2 && string[0] == '+' && string[1] == '-')
            dp[1] = 11;
        // 반복문을 통해, i번째 문자를 연산자로 보고
        // i+1, i+2의 값을 계산한다.
        for (int i = 1; i < dp.length - 1; i++) {
            // dp[i-1]까지의 값이 없다면, i번째 문자를 연산자로 볼 수 없다.
            // 건너 뛴다.
            if (dp[i - 1] == Integer.MIN_VALUE)
                continue;
            
            // +일 경우
            if (string[i] == '+') {
                // i+1번째 문자가 +이고
                if (string[i + 1] == '+') {
                    // i+2번째 문자가 -일 경우 dp[i-1] +11이 가능
                    if (i + 2 < dp.length && string[i + 2] == '-')
                        dp[i + 2] = Math.max(dp[i + 2], dp[i - 1] + 11);
                    // i+1번째 값과 dp[i-1] +10 값을 비교한다.
                    dp[i + 1] = Math.max(dp[i + 1], dp[i - 1] + 10);
                } else      // 문자가 -일 경우 -1
                    dp[i + 1] = Math.max(dp[i + 1], dp[i - 1] + 1);
            } else {        // i번째 문자가 -일 경우
                if (string[i + 1] == '+') {
                    if (i + 2 < dp.length && string[i + 2] == '-')
                        dp[i + 2] = Math.max(dp[i + 2], dp[i - 1] - 11);
                    dp[i + 1] = Math.max(dp[i + 1], dp[i - 1] - 10);
                } else
                    dp[i + 1] = Math.max(dp[i + 1], dp[i - 1] - 1);
            }
        }
        // 모든 문자열을 계산할 결과값 출력
        System.out.println(dp[dp.length - 1]);
    }
}