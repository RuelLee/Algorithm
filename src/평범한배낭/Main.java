package 평범한배낭;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 각 물건의 무게와 가치가 주어질 때
        // 주어진 무게 상한 내에서 최대한 가치의 값을 구하라.
        // 주어진 물건들의 부분집합을 구해 계산할 수도 있으나 물건이 증가함에 따라 계산이 너무 많이 늘어난다.
        // 동적프로그래밍을 활용해서 풀어보자.
        //
        // 세로 무게, 가로 해당하는 물건.
        //     0  0  1  2  3  4  5  6  7
        //  0  0  0  0  0  0  0  0  0  0    <- 아무 물건도 선택하지 않았을 때.
        //  1  0  0  0  0  0  0  0  13 13   <- weight 6, value 13인 물건을 선택했을 때.
        //  2  0  0  0  0  0  8  8  13 13   <- weight 4, value 8인 물건을 선택했을 때.
        //  3  0  0  0  0  6  8  8  13 14   <- weight 3, value 6인 물건을 선택했을 때.
        //  4  0  0  0  0  6  8  12 13 14   <- weight 5, value 12인 물건을 선택했을 때.
        //
        // 1번 row 는 1번 물건을 선택했을 때 무게에 따른 최대 가치.
        // 2번 row 는 1번 물건과 2번 물건을 선택했을 때 무게에 따른 최대 가치.
        // 무게가 4, 5일 때는 2번 물건을 선택하는게 좋지만, 무게가 6, 7일 때는 1번 물건만 선택하는 것이 최대 가치가 더 높다.
        // 3번 row 는 1번, 2번, 3번 물건을 선택했을 때 무게에 따른 최대 가치.
        // 3번 물건이 무게가 3이기 때문에 3번 column 에 6의 값이 들어갔으나, 최대 무게가 4, 5일 때는 2번 물건만 선택하는 것이 낫기 때문에 8이 되었다.
        // 무게가 6일 때는 1번 물건만 선택하는 것이 나아 13이 되었으나,
        // 무게가 7일 때는 2번 row 의 무게가 4인 상태의 가치 8에 무게가 3이고 가치가 6인 3번 물건을 포함시키는게 더 최대가치가 높기 때문에 14가 되었다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] weight = new int[n];
        int[] value = new int[n];
        for (int i = 0; i < n; i++) {
            weight[i] = sc.nextInt();
            value[i] = sc.nextInt();
        }

        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (weight[i - 1] > j)  // 현재 물건을 담지 못하는 무게 일 때는 i-1번째 row 의 값을 가져오자.
                    dp[i][j] = dp[i - 1][j];
                else    // 담을 수 있다면, i-1번째 row 에서 j-weight[i] 무게의 값에 현재 물건을 더한 가치의 값과, 그냥 i-1번째 row, j 무게의 가치의 값 중 큰 값을 선택해서 넣어주자.
                    dp[i][j] = Math.max(dp[i - 1][j - weight[i - 1]] + value[i - 1], dp[i - 1][j]);
            }
        }
        System.out.println(dp[dp.length - 1][dp[dp.length - 1].length - 1]);
    }
}