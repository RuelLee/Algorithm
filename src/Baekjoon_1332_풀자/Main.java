/*
 Author : Ruel
 Problem : Baekjoon 1332번 풀자
 Problem address : https://www.acmicpc.net/problem/1332
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1332_풀자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 문제가 주어진다.
        // 각 문제에는 0 ~ 1000까지의 흥미도가 주어진다.
        // 첫번째 문제는 반드시 풀어야하며,
        // a번 문제를 풀었다면, a+1 혹은 a+2번 문제를 풀 수 있다.
        // 여태까지 푼 문제의 흥미도 차이가 v이상일 때 문제 풀이를 그만둔다고 할 때
        // 풀어야하는 최소 문제의 수는?
        // v이상이 될 수 없다면 모든 문제를 푼다고 한다.
        //
        // DP 문제
        // 브루트 포스로 태그가 달려있었지만, 간단히 dp로 풀 수 있는 문제.
        // dp[푼 문제의 마지막 번호][푼 문제의 최소 흥미도][푼 문제의 최대 난이도] = 푼 문제의 최소 수
        // 로 정의하고 dp를 계산하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 문제, 문제 풀이를 그만 두기 위한 최소 흥미도 차이 v
        int n = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        
        // 문제들의 흥미도
        int[] problems = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < problems.length; i++)
            problems[i] = Integer.parseInt(st.nextToken());
        
        // dp[푼 문제의 마지막 번호][푼 문제의 최소 흥미도][푼 문제의 최대 난이도] = 푼 문제의 최소 수
        int[][][] dp = new int[n][1001][1001];
        // 문제의 전체 수로 초기 세팅
        for (int[][] d : dp) {
            for (int[] dd : d)
                Arrays.fill(dd, problems.length);
        }
        // 첫 문제를 풀었을 때 값 세팅
        dp[0][problems[0]][problems[0]] = 1;

        // 문제 풀이를 그만 두기 위한 최소 문제 풀이의 수
        int answer = problems.length;
        // i번 문제까지 풀었을 때
        for (int i = 0; i < dp.length - 1; i++) {
            // 최소 흥미도 j
            for (int j = 0; j < dp[i].length; j++) {
                // 최대 흥미도 k
                for (int k = j; k < dp[i][j].length; k++) {
                    // 만약 초기값이라면 불가능한 경우이므로 건너뛴다.
                    if (dp[i][j][k] == problems.length)
                        continue;

                    // 다음 문제로 i + l번 문제를 푼다.
                    for (int l = 1; l <= 2; l++) {
                        // i + l이 범위를 벗어나지 말아야하며
                        if (i + l < dp.length) {
                            // 그 때의 최소, 최대 흥미도
                            int min = Math.min(j, problems[i + l]);
                            int max = Math.max(k, problems[i + l]);

                            // 값 갱신.
                            dp[i + l][min][max] = Math.min(dp[i + l][min][max], dp[i][j][k] + 1);
                            // 만약 min, max 차이가 v이상이라면
                            // 해당하는 문제 풀이의 수를 answer에 갱신.
                            if (max - min >= v)
                                answer = Math.min(answer, dp[i][j][k] + 1);
                        }
                    }
                }
            }
        }
        // 최소 문제 풀이 수 출력
        System.out.println(answer);
    }
}