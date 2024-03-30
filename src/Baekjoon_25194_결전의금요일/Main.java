/*
 Author : Ruel
 Problem : Baekjoon 25194번 결전의 금요일
 Problem address : https://www.acmicpc.net/problem/25194
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25194_결전의금요일;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 작업에 대한 소요일이 주어진다.
        // 쉬지않고 일을 하는데, 금요일에 정확히 일을 마치는 시점이 존재한다면
        // 해당 날에는 헬스장을 간다고 한다.
        // 헬스장에 보낼 수 있는지 확인하라
        //
        // DP 문제
        // 작업에 대해 나머지 7연산을 통해
        // 해당 연산을 하면 시작일에 비해 무슨요일로 바뀌는지에 대해 계산해둔다.
        // 그 후, dp를 통해 가능한 요일을 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 작업들
        int n = Integer.parseInt(br.readLine());
        int[] works = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 해당 작업들을 진행하면 +무슨 요일이 되는지에 대해 계산한다.
        int[] mods = new int[7];
        for (int work : works)
            mods[work % 7]++;

        // 모든 작업들에 대해
        // 작업을 진행하면 바뀌는 요일에 대해 정리된 값이
        // 0 ~ 6인데, 0인 경우는 계산할 필요가 없으므로
        // 1 ~ 6에 대해 계산한다.
        boolean[][] dp = new boolean[7][7];
        // 아무 작업을 안했을 때, 기본 시작.
        dp[0][0] = true;
        // +1 ~ +6까지의 작업들에 대해 계산
        for (int i = 0; i < 6; i++) {
            // j번째 요일에서 시작
            for (int j = 0; j < dp[i].length; j++) {
                // false라면 해당 요일에서 시작하는 것이 불가능하므로 건너뛴다.
                if (!dp[i][j])
                    continue;

                // 이전 기록을 가져온다.
                dp[i + 1][j] = true;
                // +i의 작업을 k개만큼 연이어 시행했을 때
                // 해당하는 요일을 계산한다.
                // 7에 대한 모듈러 연산이므로, k값이 7을 초과하는 값은 중복되는 연산이다.
                for (int k = 1; k <= Math.min(mods[i + 1], 7); k++)
                    dp[i + 1][(j + (i + 1) * k) % 7] = true;
            }
        }

        // 모든 mod를 계산했을 때
        // 4일에 끝나는 경우가 존재한다면 YES 그렇지 않다면 NO를 출력한다.
        System.out.println(dp[6][4] ? "YES" : "NO");
    }
}