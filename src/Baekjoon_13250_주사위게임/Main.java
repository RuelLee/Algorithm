/*
 Author : Ruel
 Problem : Baekjoon 13250번 주사위 게임
 Problem address : https://www.acmicpc.net/problem/13250
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13250_주사위게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1부터 6이 적힌 주사위가 있다.
        // 매번 주사위의 윗면에 적힌 수만큼 사탕을 받는다.
        // 최소 n개의 사탕을 받기 위해 던져야하는 주사위 횟수의 기댓값은?
        //
        // DP + 기대값 문제
        // 사탕을 1개 이상 받기위해서는 1번만 던져도 된다.
        // 주사위의 수가 모두 1이상이기 때문에.
        // 사탕을 2개 받기 위해서는
        // 첫번째 던짐에서 2 이상이 나오거나
        // 첫번째 던짐에서 1이 나오고, 다음 던짐에서 아무거나 나오는 경우이다.
        // 따라서 n이상의 사탕을 얻기 위해서는
        // 한번 던질 때 나올 수 있는 1 ~ 6 수에 대해서, 즉
        // n - 6 ~ n -1번 던진 기댓값에서 해당하는 수가 나와 n이 되는 경우이다.
        // 이 때 던짐은 한번 증가하였고,
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // n까지의 기대값을 구한다.
        double[] dp = new double[n + 1];
        // 0개의 사탕을 얻기 위해서는 주사위 던짐이 필요하지 않다.
        // 1개의 사탕을 얻기 위해서는 -5개, -4개, ... , 0개의 기대값에서
        // 6, 5, ..., 1개의 사탕을 주사위 한번을 던져 얻을 수 있고
        // (-5개의 기댓값 횟수 + 1회 던짐) * (1 / 6)이 -5회로부터 6을 얻어 1개를 얻는 기대값이다.
        // 하지만 당연히 음수의 기댓값은 0이므로, 음수의 기대값은 0으로 취급하여 계산한다.
        // 점화식은
        // dp[i] = (dp[i-1] + 1) / 6 + (dp[i-2] + 1) / 6 + ... + (dp[i-5] + 1) / 6
        // 으로 세울 수 있고 이 값을 구하면 된다.
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < 7; j++)
                dp[i] += (dp[Math.max(i - j, 0)] + 1) / 6;
        }

        // 답안 출력
        System.out.println(dp[n]);
    }
}