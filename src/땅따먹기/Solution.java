package 땅따먹기;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        // n+1번째 행의 입장에서 생각하자.
        // n+1행 i열이 가질 수 있는 최대값이 오기 위해서는
        // n행 i열의 값을 제외하고 나머지 세 값 중 가장 큰 값에 land[n+1][i] 값을 더하면 된다.
        int[][] land = {{4, 3, 2, 1}, {2, 2, 2, 1}, {6, 6, 6, 4}, {8, 7, 6, 5}};
        int[][] dp = new int[land.length][land[0].length];
        dp[0] = land[0].clone();    // 0행의 값은 그대로 가져오자.

        for (int i = 1; i < land.length; i++) {     // i번째 행에서
            for (int j = 0; j < dp[i].length; j++) {    // j열의 값에 최대 값이 오기 위해선
                for (int k = 0; k < land[i - 1].length; k++) {  // i-1행 j가 아닌 열의 값 중 큰 값을 가져오면 된다.
                    if (j == k)     // j열과 k열의 값이 겹치면 안된다.
                        continue;

                    if (dp[i][j] < land[i][j] + dp[i - 1][k]) {
                        dp[i][j] = land[i][j] + dp[i - 1][k];
                        // i행 j열의 값을 land[i][j] 값 +dp[i-1][k] 값 중 큰 값으로 갱신해주자
                        // k는 j가 아닌 값으로, 0부터 3까지 돌며, 최대값을 갱신해준다.
                    }
                }
            }
        }
        // 위 반복이 마지막 행까지 반복됐을 때
        // 네 값 중 가장 큰 값이 최댓값이다.
        Arrays.sort(dp[dp.length - 1]);
        System.out.println(dp[dp.length - 1][dp[dp.length - 1].length - 1]);
    }
}