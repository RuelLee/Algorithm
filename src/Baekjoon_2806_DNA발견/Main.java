/*
 Author : Ruel
 Problem : Baekjoon 2806번 DNA 발견
 Problem address : https://www.acmicpc.net/problem/2806
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2806_DNA발견;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // A, B 두 종류의 글자로 이루어진 길이 N의 분자가 주어진다
        // 이 분자는 돌연변이를 하며
        // 1. 분자의 한 글자가 다른 글자로 변하거나
        // 2. 첫 K개의 글자가 모두 다른 글자로 바뀐다.
        // 최소 몇 번의 돌연변이가 발생하면 전부 A로 된 분자가 되는가?
        //
        // DP 문제
        // DP를 통해, dp[i][j] = i번째 글자까지 j상태가 되는데 필요한 최소 돌연변이 횟수
        // j가 0인 경우는 전부 A, j가 1인 경우는 전부 B
        // 라고 정의하고 앞에서부터 DP를 채워나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 분자
        String molecule = br.readLine();
        // DP 초기화
        int[][] dp = new int[n][2];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);

        // 첫 글자가 A라면 dp[0][0] = 0, dp[0][1] = 1
        // B라면 dp[0][0] = 1, dp[0][1] = 0
        // 로 초기값을 준다.
        dp[0][0] = molecule.charAt(0) == 'A' ? 0 : 1;
        dp[0][1] = molecule.charAt(0) == 'A' ? 1 : 0;

        // 두번째 글자부터 살펴나간다.
        for (int i = 1; i < dp.length; i++) {
            // i번째 글자가 A라면
            if (molecule.charAt(i) == 'A') {
                // i번째 글자까지 A로 만드는 방법은
                // 1) i-1 번째까지 A인 경우에서 그대로인 경우
                // 2) i-1 번째까지 B인 경우에서 두번째 돌연변이를 일으켜 앞 B가 모두 A가 된 경우.
                dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1] + 1);
                
                // i번째 글자까지 B로 만드는 방법은
                // 1) i-1번째까지 A인 경우에서 두번째 돌연변이로 모두 B로 바뀌는 경우
                // 2) i-1번째까지 B인 경우에서 첫번째 돌연변이로 i번째 글자가 B로 바뀌는 경우
                dp[i][1] = Math.min(dp[i - 1][0] + 1, dp[i - 1][1] + 1);
            } else {            // i번째 글자가 B인 경우에도 마찬가지로 계산해준다.
                dp[i][0] = Math.min(dp[i - 1][0] + 1, dp[i - 1][1] + 1);
                dp[i][1] = Math.min(dp[i - 1][0] + 1, dp[i - 1][1]);
            }
        }

        // 최종적으로 모든 글자가 A인 경우의 최소 돌연변이 횟수를 출력한다.
        System.out.println(dp[n - 1][0]);
    }
}