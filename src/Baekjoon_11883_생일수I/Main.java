/*
 Author : Ruel
 Problem : Baekjoon 11883번 생일수 I
 Problem address : https://www.acmicpc.net/problem/11883
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11883_생일수I;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] nums = new int[]{3, 5, 8};

    public static void main(String[] args) throws IOException {
        // 수들 중 3, 5, 8로만 이루어진 수를 생일수라고 부른다.
        // t개의 n이 주어질 때, 각 자리 수의 합이 n인 최소 생일수를 구하라
        //
        // dp 문제
        // dp를 통해 각 자리수의 합에 따라, 포함되는 수의 개수, 3의 개수, 5의 개수, 8의 개수를 구해
        // 가장 작은 수에 대해 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());

        // dp[각자리수의합][0] == 수의 개수
        // dp[각자리수의합][1] == 3의 개수
        // dp[각자리수의합][2] == 5의 개수
        // dp[각자리수의합][3] == 8의 개수
        int[][] dp = new int[1_000_000 + 1][4];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 초기값
        Arrays.fill(dp[0], 0);

        for (int i = 0; i < dp.length; i++) {
            // 수의 개수 합이 초기값일 경우, 불가능한 경우이므로 건너뛴다.
            if (dp[i][0] == Integer.MAX_VALUE)
                continue;
            
            // 3, 5, 8을 하나 추가하는 경우를 계산한다.
            for (int d = 0; d < nums.length; d++) {
                // 값이 범위를 넘지 않으며
                if (i + nums[d] < dp.length) {
                    boolean smaller = true;
                    // i에 nums[d]를 추가시켜 새로운 수를 만든다.
                    // 새롭게 만들어진 수가 최소값을 갱신하는지 확인한다.
                    // 기록되어있는 dp값의 수의 개수와 새로 만들어진 수의 개수가 일치하는 경우
                    // 더 작은 수가 많이 포함된 경우가 더 작은 수이다.
                    if (dp[i][0] + 1 == dp[i + nums[d]][0]) {
                        for (int j = 1; j < dp[i + nums[d]].length; j++) {
                            if (dp[i + nums[d]][j] > dp[i][j] + (j - 1 == d ? 1 : 0)) {
                                smaller = false;
                                break;
                            }
                        }
                    }

                    // 새로 만들어진 수가 기록된 수보다 총 길이가 더 짧거나
                    // 작은 수가 더 많다면 값 갱신.
                    if (dp[i + nums[d]][0] > dp[i][0] + 1 ||
                            (dp[i + nums[d]][0] == dp[i][0] + 1 && smaller)) {
                        dp[i + nums[d]][0] = dp[i][0] + 1;
                        for (int j = 1; j < dp[i].length; j++)
                            dp[i + nums[d]][j] = dp[i][j] + (d == j - 1 ? 1 : 0);
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        // t개의 테스트케이스 처리
        for (int testCase = 0; testCase < t; testCase++) {
            int n = Integer.parseInt(br.readLine());
            
            // dp[n][0]이 초기값일 경우, 불가능한 경우. -1 기록
            if (dp[n][0] == Integer.MAX_VALUE)
                sb.append(-1);
            else {      // 값이 존재하는 경우
                // 작은 수들을 큰 자릿수에 우선적으로 배치하는 것이 더 작은 수이다.
                // 개수를 비교하여
                // 작은 수들을 우선적으로 배치하여 수를 만든다.
                for (int i = 0; i < nums.length; i++) {
                    for (int j = 0; j < dp[n][i + 1]; j++)
                        sb.append(nums[i]);
                }
            }
            sb.append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }
}