/*
 Author : Ruel
 Problem : Baekjoon 14437번 준오는 심술쟁이!!
 Problem address : https://www.acmicpc.net/problem/14437
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14437_준오는심술쟁이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 문자열이 주어진다.
        // 1. 문자열 중 아무 문자나 골라 1 <= k <= 25 만큼 변경시킨다.
        // a에서 3을 변경하면 d가 되고, z에서 1을 바꾸면 a로 바뀐다.
        // 2. 변경 후, 변경하지 않은 문자들 중 하나를 고른다.
        // 3. 위 과정을 변경한 k들의 합이 s가 될 때까지 반복한다.
        // 가능한 경우의 수를 구하라
        // 값이 클 수 있으므로, 1,000,000,007로 나눈 나머지를 출력한다.
        //
        // DP, 누적합 문제
        // dp[현재 살펴보는 문자의 번호][현재까지 변경된 k들의 합] = 경우의 수
        // 로 dp를 세우고 풀 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 총 변경 횟수의 합 s와 문자열 string
        int s = Integer.parseInt(br.readLine());
        String string = br.readLine();

        // dp[현재 살펴보는 문자의 번호][현재까지 변경된 k들의 합] = 경우의 수
        // dp가 직전 결과만 알면 되기 때문에 dp[0]과 dp[1]을 왔다갔다 거리면서 계산하는 것으로
        // 공간을 절약할 수 있다.
        long[][] dp = new long[2][s + 1];
        // 0번째 문자일 때, 각각의 경우의 수는 1
        for (int i = 0; i < 26 && i < dp[0].length; i++)
            dp[0][i] = 1;

        for (int i = 1; i < string.length(); i++) {
            // 총 변경 횟수 j가 되기 위해서는
            // 이전 변경 횟수가 j - 25인 경우부터 j까지의 경우에서
            // 25 ~ 0 변경을 통해 총 변경 횟수 j를 만들 수 있다.
            // 따라서 해당 구간에 해당하는 만큼을 누적합으로 관리한다.
            long sum = 0;
            for (int j = 0; j < dp[i % 2].length; j++) {
                // 현재 sum에 포함된 범위는 j-26 ~ j-1
                // 여기에서 j는 포함시키고 j-26은 제외시킨다.
                sum += dp[(i + 1) % 2][j];
                sum -= (j - 26 >= 0 ? dp[(i + 1) % 2][j - 26] : 0);
                // 해당하는 값을 dp에 기록
                dp[i % 2][j] = (sum + LIMIT) % LIMIT;
            }
        }
        // 답 출력
        System.out.println(dp[(string.length() + 1) % 2][s]);
    }
}