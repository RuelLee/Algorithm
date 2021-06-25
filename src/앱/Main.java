package 앱;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 배낭 문제와 유사! -> DP문제!
        // 그런데 확보해야하는 메모리의 범위가 너무 크다(최대 천만까지)
        // 대신 최대 앱의 갯수에 따른 cost 합계가 최대 100만이다.
        // -> DP의 Column 을 메모리가 아닌 cost로 둬야한다.
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] memories = new int[N];
        int[] costs = new int[N];

        for (int i = 0; i < memories.length; i++)
            memories[i] = sc.nextInt();
        for (int i = 0; i < memories.length; i++)
            costs[i] = sc.nextInt();

        // 비용의 범위는 모든 앱들의 비용의 합.
        int[][] dp = new int[N + 1][Arrays.stream(costs).sum() + 1];
        for (int i = 1; i < dp.length; i++) {       // 첫번째 row 는 아무 앱도 끄지 않았을 때이므로 건너 뛰고
            for (int j = 0; j < dp[i].length; j++) {
                // j(현재 비용)이 현재 물건(row-1)의 비용보다 같거나 클 때
                // 현재 앱을 선택 안했을 때의 확보할 수 있는 메모리(dp[i - 1][j])와
                // 현재 앱을 선택했을 때 확보할 수 있는 메모리(dp[i - 1][j - costs[i - 1]] + memories[i - 1])와 비교하여 큰 값을 저장.
                if (costs[i - 1] <= j)
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - costs[i - 1]] + memories[i - 1]);
                else        // 현재 물건보다 적은 비용이라면, 이전 앱들을 선택했을 때 확보할 수 있는 최대 메모리를 나타낸다.
                    dp[i][j] = dp[i - 1][j];
            }
        }

        // 답은 마지막 row 의 값들을 살펴보며, 가장 먼저 등장하는 M보다 같거나 큰 값의 cost(col).
        int answer = 0;
        for (int i = 0; i < dp[dp.length - 1].length; i++) {
            if (dp[dp.length - 1][i] >= M) {
                answer = i;
                break;
            }
        }
        System.out.println(answer);
    }
}