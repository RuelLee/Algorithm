package 색상환;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // DP 문제에서 가장 중요한 점은 연속된 값들이 어떤 관계를 갖고 있는지에 대한 점화식을 찾아내는 것.
        // N개의 색상 중 K개의 색을 고르는 가짓수는
        // 1. N-2개의 색상 중 K-1을 고른 후, 마지막 N번째 색상을 고르는 방법.
        // 2. N-1개의 색상 중 K개를 모두 골라서, N번재 색상을 고르지 않는 방법. 두 가지의 합.
        // 그러나 추가적으로 이 문제는 색상'환'으로 고리 형태를 띄고 있기 때문에
        // 첫번째 색상을 선택하였다면 마지막 색상을 선택할 수 없다.
        // 따라서 주어진 n, k에 대해서 답은
        // 1번에서 첫 칸을 비운 경우인 N-3개의 색상 중 K-1개를 고른 가짓수
        // N-1 색상 중 K개를 모두 고른 경우의 수(마지막 색상을 안 고르므로, 첫 칸을 비울 필요 없음)
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[][] dp = new int[n + 1][k + 1];

        // 초기값에 대해 생각해보자.
        // 답과 점화식이 n-3의 k-1, k값까지 접근하므로 0, 1, 2 row의 0, 1 col 값은 정해줘야한다.
        // n이 0일대 dp[0][0] = 1 dp[0][1] = ?
        // n이 1일 때는 dp[1][0] = 1, dp[1][1] = 1
        // n이 2일 때는 dp[2][0] = 1, dp[2][1] = 2, dp[2][2] = 0 <- (연속한 두개를 선택할 수 없으므로)
        for (int i = 0; i < dp.length; i++)
            dp[i][0] = 1;
        dp[1][1] = 1;
        dp[2][1] = 2;

        for (int i = 3; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++)
                dp[i][j] = (dp[i - 2][j - 1] + dp[i - 1][j]) % 1000000003;
        }
        int answer = (dp[n - 3][k - 1] + dp[n - 1][k]) % 1000000003;
        System.out.println(answer);
    }
}