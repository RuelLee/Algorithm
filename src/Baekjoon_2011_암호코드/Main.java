/*
 Author : Ruel
 Problem : Baekjoon 2011번 암호코드
 Problem address : https://www.acmicpc.net/problem/2011
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2011_암호코드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 대화에 포함되는 알파벳들은 A = 1, ... , Z = 26으로 지정하여 암호화하고자 한다
        // BEAN을 암호화 하면 25114가 나오는데 이는
        // BEAAD, YAAD, YAN, YKD, BEKD, BEAN으로 복호화될 수 있다.
        // 어떤 암호가 주어질 때, 그 암호의 해석이 몇 가지가 나올 수 있는지 구하라
        //
        // DP 문제
        // DP를 통해 각 자리 이전까지 만들어지는 경우의 수를 계산하여 갖고 있는다.
        // 그 후, 자신의 자리 값으로 하나의 글자로 해석할 수 있다면, 이전까지 계산된 경우의 수만큼을 다음 자리로 넘겨주고
        // 자신의 자리와 다음 자리, 두 개의 수를 하나의 글자로 해석할 수 있다면, 게산된 경우의 수를 다다음 자리로 넘겨준다.
        // 마지막으로 최종적으로 마지막자리 + 1 DP에 계산된 수가 전체 경우의 수
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 암호화된 코드
        String encryptedCode = br.readLine();
        // 자신 이전까지의 경우의 수를 계산하므로, 전체 암호코드를 살펴보려면 한자리 더 큰 DP가 필요하다.
        int[] dp = new int[encryptedCode.length() + 1];
        // 만약 처음 시작이 0으로 시작된다면 0인 글자는 존재하지 않으므로 0
        // 그 외의 경우는 1로 시작
        dp[0] = encryptedCode.charAt(0) - '0' == 0 ? 0 : 1;
        for (int i = 0; i < dp.length - 2; i++) {
            // 값이 100만이 넘어가지 않도록 모듈러 연산.
            dp[i] %= 1000000;
            // i번째 수 하나로 하나의 글자로 바꿀 수 있다면
            // 자신의 자리를 글자로 바꾸고, dp[i + 1]에 현재 경우의 수를 더한다.
            if (encryptedCode.charAt(i) - '0' > 0 && encryptedCode.charAt(i) - '0' < 10)
                dp[i + 1] += dp[i];

            // i, i+1 두 개의 수를 하나의 글자로 바꿀 수 있다면
            // 두 개의 수를 글자로 바꾸었다 생각하고, dp[i + 2]에 현재 경우의 수를 더해준다.
            int num = Integer.parseInt(encryptedCode.substring(i, i + 2));
            if (num >= 10 && num <= 26)
                dp[i + 2] += dp[i];
        }
        // 마지막 수가 하나의 글자로 바뀔 수 있는지 체크한다.
        int lastNum = encryptedCode.charAt(encryptedCode.length() - 1) - '0';
        if (lastNum > 0 && lastNum < 10) {
            dp[dp.length - 1] += dp[dp.length - 2];
            dp[dp.length - 1] %= 1000000;
        }

        // 계산된 결과, 모든 경우의 수를 출력한다.
        System.out.println(dp[dp.length - 1]);
    }
}