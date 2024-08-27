/*
 Author : Ruel
 Problem : Baekjoon 25759번 들판 건너가기
 Problem address : https://www.acmicpc.net/problem/25759
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25759_들판건너가기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 꽃들이 일렬로 핀 들판을 왼쪽에서 오른쪽으로 걷고 있다.
        // 몇몇의 꽂을 따 꽃다발을 만드는데 해당 꽃다발의 아름다움은
        // 꽃을 딴 순서대로 놓았을 때 인접한 꽃의 아름다움 차이의 제곱들의 합으로 결정된다고 한다.
        // 가장 아름다운 꽃다발의 수치는?
        //
        // DP 문제
        // dp[살펴본꽃][가장마지막꽃의아름다움수치] = 현재 만든 꽃다발의 아름다움 수치
        // 로 정의하고 계산해가면 어렵지 않은 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 꽃들
        int n = Integer.parseInt(br.readLine());
        int[] flowers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp[현재살펴본꽃][가장마지막꽃의아름다움수치] = 꽃다발의 아름다움 수치
        int[][] dp = new int[n][101];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // 첫번째꽃
        dp[0][flowers[0]] = 0;
        // 현재 살펴보는 꽃
        for (int i = 0; i < dp.length - 1; i++) {
            // 마지막 꽃의 아름다움 수치
            for (int j = 0; j < dp[i].length; j++) {
                // 값이 없다면 불가능한 경우이므로 건너뜀
                if (dp[i][j] == -1)
                    continue;
                
                // i+1번째 꽃을 포함시키지 않는 경우
                // 현재 값을 그대로 다음 dp로 이동
                dp[i + 1][j] = Math.max(dp[i][j], dp[i + 1][j]);
                // 현재 마지막 꽃의 아름다음 수치가 j이며,
                // i+1번째 꽃을 마지막에 추가하는 경우
                // 마지막 꽃은 i+1번째 꽃이 되며
                // 꽃다발의 아름다음 수치는 dp[i][j] 값에 아름다음 j를 갖는 꽃과 i+1번째 꽃의 아름다움 차이 제곱을 합한 값이 된다.
                dp[i + 1][flowers[i + 1]] = Math.max(dp[i + 1][flowers[i + 1]], dp[i][j] + (int) Math.pow(flowers[i + 1] - j, 2));
            }
        }

        // 모든 꽃을 살펴본 후
        // 계산된 가장 큰 아름다움 수치를 찾는다.
        int answer = 0;
        for (int j = 1; j < dp[n - 1].length; j++)
            answer = Math.max(answer, dp[n - 1][j]);
        // 답 출력
        System.out.println(answer);
    }
}