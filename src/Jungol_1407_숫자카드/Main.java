/*
 Author : Ruel
 Problem : Jungol 1407번 숫자카드
 Problem address : https://jungol.co.kr/problem/1407
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1407_숫자카드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ 34까지의 숫자 카드가 각각 무한대로 주어진다.
        // 40자리 이하의 수가 주어질 때, 해당 수를 숫자카드로 표현하는 방법의 개수는?
        //
        // DP 문제
        // dp[현재 살펴보는 자리] = 만들 수 있는 경우의 수로 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력되는 수
        String input = br.readLine();
        int[] dp = new int[input.length() + 1];
        // 처음 초기값 1
        dp[0] = 1;

        // 수를 앞에서부터 살펴본다.
        for (int i = 0; i < input.length(); i++) {
            // 해당 자리의 수가 0보다 큰 경우
            if (input.charAt(i) - '0' > 0) {
                // 그렇다면 1 ~ 9까지의 숫자카드로 표현할 수 있다.
                // 따라서 i+1에 현재 경우의 수 dp[i]를 누적한다.
                dp[i + 1] += dp[i];
                // 그리고, i와 i+1자리의 수를 살펴봤을 때, 두 자리수가 34이하인 경우
                // 두자리의 수로 표현이 가능하다.
                // 해당 경우 dp[i+2]에 dp[i]만큼을 누적한다.
                if (i + 1 < input.length() && Integer.parseInt(input.substring(i, i + 2)) <= 34)
                    dp[i + 2] += dp[i];
            }
        }
        // 모든 자리를 표현하는 경우의 수 출력
        System.out.println(dp[dp.length - 1]);
    }
}