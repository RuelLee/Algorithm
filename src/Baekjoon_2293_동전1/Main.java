/*
 Author : Ruel
 Problem : Baekjoon 2293번 동전 1
 Problem address : https://www.acmicpc.net/problem/2293
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2293_동전1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n가지의 동전의 종류가 주어진다
        // k원을 만들고 싶을 때, 동전을 사용하는 방법의 가짓수는?
        //
        // 간단한 DP 문제
        // DP를 k원을 만들 수 있는 가짓수라고 생각하자
        // 그리고 동전을 하나씩 따져가며, 만들 수 있는 k원의 가짓수를 하나씩 세어간다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 동전들
        int[] coins = new int[n];
        for (int i = 0; i < n; i++)
            coins[i] = Integer.parseInt(br.readLine());

        // k원을 만드는 가짓수를 저장할 DP
        int[] dp = new int[k + 1];
        // 0원을 만드는 가짓수는 아무 동전도 사용하지 않는 경우. 1가지.
        dp[0] = 1;

        // 동전을 하나씩 따져간다
        for (int coin : coins) {
            // 해당 동전으로 만들 수 있는 최소 값은
            // coin에 해당하는 값이다. 해당 값부터 시작하자
            // j원을 만드는 방법은 이미 만들어진 j - coin 값에 coin을 추가하는 방법이다.
            // 현재 dp[j]에는 coin를 사용하지 않고 j원을 만드는 가짓수가 저장되어있다.
            // 여기에 j - coin원에 + coin을 추가해서 만드는 가짓수(= dp[j - coin])를 더해준다.
            for (int j = coin; j < dp.length; j++)
                dp[j] += dp[j - coin];
        }
        // 최종적으로 dp에는 각 해당하는 값을 만드는 가짓수가 계산되어있다.
        // 우리가 원하는 값은 k원이므로, dp[k]를 출력.
        System.out.println(dp[k]);
    }
}