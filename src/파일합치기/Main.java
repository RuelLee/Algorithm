package 파일합치기;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 순서대로 인접한 두 파일만 합칠 수 있다.
        // 각 파일을 합칠 때는 두 파일의 크기 합만큼의 시간이 소요된다.
        // 모든 파일을 하나로 합칠 때 소요되는 시간의 최솟값은?
        // DP를 통해 풀어야한다.
        // DP[i][j]는 i ~ j 파일을 합치는데 소요된 시간의 최솟값이라고 하자.
        // i ~ j 파일을 합치는데 드는 시간은 i ~ k 까지의 파일과 k+1 ~ j 까지의 파일
        // 두 개 파일을 만드는데 소요되는 시간과 두 파일을 하나로 만드는데 합치는데 소요되는 시간의 합이다.
        // DP[i][k] + DP[k+1][j] -> 각각 두 개의 파일을 만드는데 소요되는 시간
        // i ~ k 까지의 페이지의 수 + k+1 ~ j 까지의 페이지의 수 -> 두 파일을 하나로 합치는데 소요되는 시간
        // 두 값의 합이 최소가 되는 값을 찾으면 된다.
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        StringBuilder stringBuilder = new StringBuilder();
        for (int tc = 0; tc < T; tc++) {
            int k = sc.nextInt();

            int[][] dp = new int[k][k];
            int[] pageSums = new int[k];
            for (int[] d : dp)
                Arrays.fill(d, Integer.MAX_VALUE);  // 큰 값을 채워 최소값으로 갱신하도록 하자.

            pageSums[0] = sc.nextInt();     // pageSums[i]는 0 ~ i 까지의 페이지의 합
            dp[0][0] = 0;       // DP[i][i] 값은 주어지는 초기파일이므로 소요 시간은 0이다.
            for (int i = 1; i < dp.length; i++) {
                dp[i][i] = 0;
                pageSums[i] += pageSums[i - 1] + sc.nextInt();
            }

            for (int col = 1; col < dp[0].length; col++) {      // 합치는 파일의 마지막 부분
                for (int row = col - 1; row >= 0; row--) {      // 합치는 파일의 첫부분
                    for (int criteria = row; criteria < col; criteria++) {      // 두 파일을 나누는 기준
                        int preRequiredTime = dp[row][criteria] + dp[criteria + 1][col];        // 두 파일을 만드는데 소요되는 시간.
                        int currentRequiredTime = pageSums[col] - (row < 1 ? 0 : pageSums[row - 1]);    // 두 파일을 합치는데 소요되는 시간.
                        dp[row][col] = Math.min(dp[row][col], preRequiredTime + currentRequiredTime);   // 최소값이라면 갱신하자.
                    }
                }
            }
            stringBuilder.append(dp[0][dp[0].length - 1]).append("\n");
        }
        System.out.println(stringBuilder);
    }
}