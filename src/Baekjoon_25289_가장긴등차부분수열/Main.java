/*
 Author : Ruel
 Problem : Baekjoon 25289번 가장 긴 등차 부분 수열
 Problem address : https://www.acmicpc.net/problem/25289
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25289_가장긴등차부분수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기가 n인 수열 a가 주어진다.
        // 이중 원소의 순서를 유지한 부분수열을 만들어, 해당 수열이 등차수열을 이루게끔하고자 한다.
        // 부분수열의 최대 길이는?
        //
        // dp, 브루트 포스 문제
        // 문제를 보면 원소의 범위가 1 ~ 100으로 매우 작다.
        // 따라서 모든 공차에 대해 시도하더라도 -99 ~ 99까지 199개에 대해 시도하면 된다.
        // 해당 공차들에 대해, 마지막 원소가 a인 최대 부분수열의 길이를 dp[a]로 정의하여 계산해나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 수열
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());

        // 각 해당하는 수가 마지막에 오는 부분수열의 최대 길이
        int[] dp = new int[101];
        int ans = 0;
        // 공차
        for (int d = -99; d <= 99; d++) {
            Arrays.fill(dp, 0);
            // array[i] - d가 범위 내에 있다면 해당 부분수열에 array[i]를 마지막으로 배치함으로써
            // 더 긴 길이의 부분 수열을 찾았다고 체크
            // 없더라도 현재 array[i]가 등장했으므로 길이 1은 최소 보장
            for (int i = 0; i < array.length; i++)
                dp[array[i]] = Math.max(dp[array[i]], (inRange(array[i] - d) ? dp[array[i] - d] : 0) + 1);

            // 가장 긴 길이를 찾는다
            for (int i = 1; i < dp.length; i++)
                ans = Math.max(ans, dp[i]);
        }
        // 답 출력
        System.out.println(ans);
    }

    static boolean inRange(int n) {
        return n >= 1 && n <= 100;
    }
}