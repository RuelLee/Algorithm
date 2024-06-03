/*
 Author : Ruel
 Problem : Baekjoon 25188번 1, 3, 모 나누기
 Problem address : https://www.acmicpc.net/problem/25188
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25188_13모나누기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 n인 수열이 주어진다.
        // 수열을 6개의 구간으로 나누어
        // 1, 3, 5 구간에 포함된 원소의 합을 최대화하고자할 때
        // 그 합을 구하라
        // 원소는 구간 중 하나에 포함되어야하지만, 한 구간에 속한 원소는 없을 수도 있다.
        //
        // DP 문제
        // dp[i][j] = i까지 원소를 j+1 구간으로 나누었을 때 최대값으로 정의하고 답을 구한다.
        // 홀수 구간의 원소들은 합을 더하고, 짝수 구간의 원소들은 합을 더하지 않는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 수열의 크기 n
        int n = Integer.parseInt(br.readLine());
        // 수열
        int[] a = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp
        // 1 ~ 6구간이지만, 0 ~ 5로 변경
        long[][] dp = new long[n][6];
        // 첫번째 원소를 0구간에 포함시키는 경우.
        dp[0][0] = a[0];
        // 첫번째 원소를 1구간에 포함시켜 더하지 않는 경우.
        dp[0][1] = 0;
        // 두번째 원소부터 반복문으로 처리
        for (int i = 1; i < dp.length; i++) {
            // i번째 원소가 첫번째 구간에 속하는 경우.
            // 더해줌.
            dp[i][0] = dp[i - 1][0] + a[i];
            for (int j = 1; j < dp[i].length; j++) {
                // i번째 원소가 j번째 구간에 속하는 경우.
                // j가 홀수라면
                // 더하지 않는 구간이므로
                // dp[i-1][j] 값과 dp[i-1][j-1] 값 중 큰 값을 반영.
                // 전자는 이전 원소부터 j구간이었던 경우와
                // 후자는 i번째 원소부터 j번째 구간인 경우.
                if (j % 2 == 1)
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1]);
                else        // 짝수인 경우, 마찬가지로 두 경우의 최대값을 구한 후, 짝수는 해당 원소를 포함함으로 원소에 해당하는 수를 더해준다.
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1]) + a[i];
            }
        }
        
        // 각 구간에는 원소가 포함되지 않을 수도 있다.
        // 따라서 꼭 6가지 구간으로 나눈 경우만 답이 되는 건 아니다.
        // n개의 원소를 모두 살펴보았고, 1 ~ 6개의 구간으로 나눈 합들 중 가장 큰 값을 출력
        System.out.println(Arrays.stream(dp[n - 1]).max().getAsLong());
    }
}