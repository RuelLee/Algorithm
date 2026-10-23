/*
 Author : Ruel
 Problem : Jungol 8252번 숫자 합
 Problem address : https://jungol.co.kr/problem/8252
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8252_숫자합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n이 주어질 때, 다음 연산을 0회 이상하여 n을 만들 수 있는 경우의 수를 구하라.
        // 어떤 정수 x에 x의 각 자리 수 합을 더해 새로운 수를 만든다.
        //
        // dp 문제
        // 연산을 통해서 계속 수가 커져나간다.
        // 따라서 dp로 순차적으로 수를 살펴나가며, 다음 수를 만들 수 있는 경우의 수를 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 수 n
        int n = Integer.parseInt(br.readLine());
        int[] dp = new int[n + 1];
        // 연산 0회일 때, 각 수는 한 번씩
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
            // 연산을 통해 만들어지는 수
            int next = i + toSum(i);
            // 범위 내일 경우, 경우의 수를 합산
            if (next <= n)
                dp[next] += dp[i];
        }
        // 답 출력
        System.out.println(dp[n]);
    }

    // 수 n을 10진법으로 나타냈을 때, 각 자리의 합을 구한다.
    static int toSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}