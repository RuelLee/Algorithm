/*
 Author : Ruel
 Problem : Baekjoon 16494번 가장 큰 값
 Problem address : https://www.acmicpc.net/problem/16494
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16494_가장큰값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수로 이루어진 수열이 주어진다.
        // m개의 그룹으로 나누었을 때, 그룹에 속한 모든 수의 합의 최대값을 구하라
        //
        // DP 문제
        // dp[i][j] = i번째 수까지를 j개의 그룹으로 나눴을 때, 그룹에 속한 모든 수의 합의 최대값
        // 으로 정의하고 dp를 채워나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 수, m개의 그룹
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp
        int[][] dp = new int[n][m + 1];
        // 초기값
        for (int[] d : dp)
            Arrays.fill(d, Integer.MIN_VALUE);
        for (int i = 0; i < dp.length; i++) {
            // 0개의 그룹으로 나눴을 때의 최대값은 0이며
            dp[i][0] = 0;
            // i번째 수를 첫번째 그룹의 첫 수로 시작되는 경우를 초기값으로 한다.
            dp[i][1] = nums[i];
        }

        // n-1개 수를 돌아보며
        for (int i = 0; i < dp.length - 1; i++) {
            // i번째까지 j개의 그룹으로 나뉜 경우를 살펴본다.
            for (int j = 1; j < dp[i].length; j++) {
                // 최소값이라면 불가능한 경우. 건너뛴다.
                if (dp[i][j] == Integer.MIN_VALUE)
                    continue;
                
                // i+1번째 수까지 포함하여 j개의 그룹으로 되는 경우
                // 현재 그룹에 i+1번째 수를 추가하는 경우를 계산한다.
                dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + nums[i + 1]);
                
                // 그룹을 j+1개로 하나 더 늘리는 경우
                // j가 m보다 작아야한다.
                if (j < m) {
                    // 중간에 수들을 건너뛸수도 뛰지 않을 수도 있다.
                    // j+1번째 그룹의 첫 수로 next번째 수를 편성하는 경우
                    for (int next = i + 1; next < dp.length; next++)
                        dp[next][j + 1] = Math.max(dp[next][j + 1], dp[i][j] + nums[next]);
                }
            }
        }

        // m개의 그룹으로 나뉜 값들 중 최대값을 찾는다.
        int answer = Integer.MIN_VALUE;
        for (int i = 0; i < dp.length; i++)
            answer = Math.max(answer, dp[i][m]);
        // 답 출력
        System.out.println(answer);
    }
}