/*
 Author : Ruel
 Problem : Baekjoon 8973번 수학 공책
 Problem address : https://www.acmicpc.net/problem/8973
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8973_수학공책;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 수열이 2개 주어진다.
        // 두 수열의 흐릿함은 두번째 수열의 순서를 뒤집은 뒤 같은 위치에 있는 두 수의 곱의 합이다.
        // 예를 들어
        // 3 -4 -3 -2 2 0
        // -3 0 5 -1 3 2 가 주어진다면
        // 3×2 + (-4)×3 + (-3)×(-1) + (-2)×5 + 2×0 + 0×(-3) = -13 이다.
        // 수열의 앞에서부터 b개, 뒤에서부터 e개를 지워 흐릿함을 되도록 크게 만들고자한다.
        // 이 때 b, e와 흐릿함 값은?
        //
        // 브루트포스, DP 문제
        // n이 최대 2000으로 주어지므로, 일일이 모두 계산하더라도 계산할 수 있다.
        // 하지만 DP를 통한다면 연산을 많이 줄일 수 있다.
        // 먼저 b 혹은 e가 0인 경우는 직접 계산해야한다.
        // 하지만 b와 e가 0보다 큰 상황에서는 양쪽의 길이가 1씩 긴 수열의 흐릿함에서 양쪽 수의 곱만 지워주면 된다.
        // 예를 들어
        // 1 2 3 4
        // 4 3 2 1 이라는 수열의 흐릿함은 30이다
        // 여기서 b=1, e=1인 경우인
        // 2 3
        // 3 2 의 경우는 30에서 4*4 + 1*1의 값을 빼준 13이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n
        int n = Integer.parseInt(br.readLine());
        // 두 수열
        int[][] nums = new int[2][];
        for (int i = 0; i < 2; i++)
            nums[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp를 통해 푼다.
        int[][] dp = new int[n][n];
        // 흐릿함이 최대일 때의 b와 e값
        int answerB = 0;
        int answerE = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; i + j < n; j++) {
                // i와 j가 모두 0보다 큰 경우에는
                // dp[i-1][j-1] 값에서 양쪽 수를 하나씩 지운 값을 계산해준다.
                // 이 값이 dp[i][j]의 값
                if (i > 0 && j > 0)
                    dp[i][j] = dp[i - 1][j - 1] - (nums[0][i - 1] * nums[1][n - j] + nums[0][n - j] * nums[1][i - 1]);
                else {
                    // 그 외의 경우에는 직접 계산해준다.
                    for (int k = 0; i + k <= n - 1 - j; k++)
                        dp[i][j] += nums[0][i + k] * nums[1][n - 1 - j - k];
                }
                
                // 흐릿함의 최대값을 갱신하는지 확인.
                if (dp[i][j] > dp[answerB][answerE]) {
                    answerB = i;
                    answerE = j;
                }
            }
        }
        // 그 때의 b, e, 흐릿함 값 출력
        System.out.println(answerB + " " + answerE);
        System.out.println(dp[answerB][answerE]);
    }
}