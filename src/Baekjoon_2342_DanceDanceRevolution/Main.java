/*
 Author : Ruel
 Problem : https://www.acmicpc.net/problem/2342
 Problem address : Baekjoon 2342번 Dance Dance Revolution
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2342_DanceDanceRevolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // # 1 #
        // 2 0 4
        // # 3 #
        // 다음과 같은 발판이 주어진다.
        // 같은 발판을 밟는 경우는 피로도 1
        // 0에서 다른 발판을 밟는 경우 피로도는 2
        // 인접한 다른 발판을 밟는 경우 (ex 2 -> 1 or 2 -> 3) 피로도는 3
        // 반대쪽 발판을 밟는 경우 (ex 2 <-> 4, 1 <-> 3 피로도는 4라고 한다.
        // 밟아야하는 발판들이 주어질 때, 모든 발판을 밟는 데 필요한 최소 피로도는 얼마인가
        //
        // DP 문제
        // dp[turn][왼발][오른발]로 세우고 풀었다.
        // 최대 10만까지 밟아야하는 발판들이 주어지는데 당연히 turn대로 배열을 세우면 안된다.
        // 필요한 것은 이전 발판을 발판을 밟았을 때의 누적 피로도이므로, 2개의 2차원 배열을 만들고, 번갈아가면서 사용하도록 하자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // dp[turn][왼발][오른발]
        int[][][] dp = new int[2][5][5];
        for (int[][] state : dp) {
            for (int[] left : state)
                Arrays.fill(left, Integer.MAX_VALUE);
        }
        // 처음 두 발은 0에 있고 피로도는 0.
        dp[0][0][0] = 0;

        int turn = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        while (true) {
            // 현재 밟아야하는 발판
            int current = Integer.parseInt(st.nextToken());
            // 0이 주어진다면 끝.
            if (current == 0)
                break;

            // 다음 발판에 대한 피로도 계산을 위해 다음 2차원 배열을 초기화.
            dp[(turn + 1) % 2] = new int[5][5];
            for (int[] left : dp[(turn + 1) % 2])
                Arrays.fill(left, Integer.MAX_VALUE);

            // 현재 발판을 살펴보며 초기값이 아닌(= 현재 가능한 발판의 상태)를 찾는다.
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    // 초기값인 경우는 불가능한 경우. 건너뛴다.
                    if (dp[turn % 2][i][j] == Integer.MAX_VALUE)
                        continue;

                    // 만약 왼발이 아직 중앙에 있다면
                    // 왼발이 current를 밟는 경우는 +2의 피로도가 추가된다.
                    if (i == 0)
                        dp[(turn + 1) % 2][current][j] = Math.min(dp[(turn + 1) % 2][current][j], dp[turn % 2][i][j] + 2);
                    // 오른발이 아직 중앙에 있다면
                    // 오른발이 current를 밟는 경우 +2의 피로도가 추가된다.
                    if (j == 0)
                        dp[(turn + 1) % 2][i][current] = Math.min(dp[(turn + 1) % 2][i][current], dp[turn % 2][i][j] + 2);

                    // 왼발이 current를 밟는 경우.
                    switch (Math.abs(current - i)) {
                        // 왼발이 현재 current를 밟고 있는 경우에는 피로도 1
                        case 0 -> dp[(turn + 1) % 2][i][j] = Math.min(dp[(turn + 1) % 2][i][j], dp[turn % 2][i][j] + 1);
                        // 반대 발판을 밟는 경우는 피로도 4
                        case 2 ->
                                dp[(turn + 1) % 2][current][j] = Math.min(dp[(turn + 1) % 2][current][j], dp[turn % 2][i][j] + 4);
                        // 인접한 발판을 밟는 경우는 피로도 3
                        default ->
                                dp[(turn + 1) % 2][current][j] = Math.min(dp[(turn + 1) % 2][current][j], dp[turn % 2][i][j] + 3);
                    }

                    // 오른발이 current를 밟는 경우.
                    switch (Math.abs(current - j)) {
                        case 0 -> dp[(turn + 1) % 2][i][j] = Math.min(dp[(turn + 1) % 2][i][j], dp[turn % 2][i][j] + 1);
                        case 2 ->
                                dp[(turn + 1) % 2][i][current] = Math.min(dp[(turn + 1) % 2][i][current], dp[turn % 2][i][j] + 4);
                        default ->
                                dp[(turn + 1) % 2][i][current] = Math.min(dp[(turn + 1) % 2][i][current], dp[turn % 2][i][j] + 3);
                    }
                }
            }
            turn++;
        }

        // 현재 턴에 가능한 최소 누적 피로도를 찾는다.
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < dp[turn % 2].length; i++) {
            for (int j = 0; j < dp[turn % 2][i].length; j++)
                min = Math.min(min, dp[turn % 2][i][j]);
        }
        // 해당 값 출력.
        System.out.println(min);
    }
}