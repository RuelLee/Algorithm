/*
 Author : Ruel
 Problem : Baekjoon 25958번 예쁜수
 Problem address : https://www.acmicpc.net/problem/25958
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25958_예쁜수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1보다 크거나 같은 정수 n의 각 자리 합을 s라 할 때
        // s가 n의 약수라면 그 수를 예쁜수라고 한다.
        // 자연수 m을 예쁜수들의 합으로만 표현하고 싶다.
        // 구성이 다른 경우만 다른 경우이다.
        // 1 + 1 + 2 = 4 와 2 + 1 + 1 = 4는 같은 경우이다.
        // m을 예쁜수들의 합으로만 표현하는 경우의 수를 k로 나눈 나머지를 출력하라
        //
        // 배낭 문제
        // 먼저 예쁜 수를 모두 구한다.
        // 그 후, 예쁜 수들을 순서대로 살펴보며
        // 예쁜 수들이 합으로 m을 만드는 경우를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 합으로 표현하고자 하는 수 m, 나눈 나머지를 구할 k
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 예쁜수들
        List<Integer> prettyNumbers = new ArrayList<>();
        for (int i = 1; i <= m; i++) {
            if (isPrettyNumber(i))
                prettyNumbers.add(i);
        }

        // 배낭 문제
        int[] dp = new int[m + 1];
        dp[0] = 1;
        // 순서대로 예쁜수들을 살펴보며
        for (int pn : prettyNumbers) {
            // i + pn이 m보다 같거나 작은 경우까지
            for (int i = 0; i + pn < dp.length; i++) {
                // dp[i]가 되는 경우의 수가 존재하지 않는다면 건너뛴다.
                if (dp[i] == 0)
                    continue;
                
                // i + pn을 만드는 경우의 수는
                // i를 만드는 경우의 수만큼이 추가된다.
                dp[i + pn] += dp[i];
                // 나머지 처리
                dp[i + pn] %= k;
            }
        }
        // 답안 출력
        System.out.println(dp[m]);
    }

    static boolean isPrettyNumber(int n) {
        int sum = 0;
        int num = n;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return n % sum == 0;
    }
}