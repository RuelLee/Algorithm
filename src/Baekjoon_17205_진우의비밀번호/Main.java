/*
 Author : Ruel
 Problem : Baekjoon 17205번 진우의 비밀번호
 Problem address : https://www.acmicpc.net/problem/17205
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17205_진우의비밀번호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 진우는 웹사이트의 암호를 잘 까먹는다.
        // 암호는 소문자로만 이루어져있으며, 웹사이트에서는 최대 n글자의 암호를 허용한다.
        // 진우는 암호를 까먹었기 때문에 알파벳 순으로 모든 문자열을 시도하며,
        // 하나의 암호를 시도하는데 1초가 걸린다고 한다.
        // 올바른 암호가 주어질 때, 해당 암호까지 도달하는데 걸리는 시간을 출력하라
        //
        // 조합 문제
        // 구현 자체는 매우 간단했지만, 조금 헷갈릴 수 있었던 문제.
        // 각 자리에서 z까지 도달하는 횟수를 계산해둔다.
        // 가령 n = 2, 비밀번호가 b인 경우에는
        // a, aa, ab, ac, ad, ... , az, b로 넘어간다.
        // 따라서 a에서 b로 넘어가기 위해서는 a ~ aa ~ az ~ b를 넘어가야한다.
        // aa ~ az는 알파벳의 개수인 26이고, 따라서 a ~ az까지는 27이다.
        // 따라서 i번째 자리에 z까지의 걸리는 소요 시간은
        // (i + 1)번째 자리가 26번 다음 알파벳으로 넘어간 후, +1(자신이 공백인 경우) 다.
        // 모든 자리에 대해 계산한 후 그 차이만큼을 더해 소요 시간을 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        String password = br.readLine();

        long[] dp = new long[n];
        // i번째 알파벳을 다음 알파벳으로 바꾸는데 소요시간을 계산한다.
        // 끝자리인 경우, 다음 알파벳으로 바로 넘어간다.
        dp[dp.length - 1] = 1;
        // i번째 자리를 다음 알파벳으로 바꾸기 위해서는
        // (i + 1)번째 자리의 알파벳이 26번 바뀌고, +1(맨 처음 공백인 경우)이다.
        for (int i = dp.length - 2; i >= 0; i--)
            dp[i] = dp[i + 1] * 26 + 1;

        long sum = 0;
        // 각 자리를 비교하며 암호의 해당 알파벳으로 도달하는 소요시간의 총합을 구한다.
        for (int i = 0; i < password.length(); i++) {
            // password의 i번째 알파벳과 'a'의 차이 *dp[i] + 1만큼으로 원하는 알파벳으로 바꿀 수 있다.
            sum += (password.charAt(i) - 'a') * dp[i] + 1;
        }

        // 총 소요시간을 출력한다.
        System.out.println(sum);
    }
}