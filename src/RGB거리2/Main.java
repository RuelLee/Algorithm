/*
 Author : Ruel
 Problem : Baekjoon 17404번 RGB거리 2
 Problem address : https://www.acmicpc.net/problem/17404
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package RGB거리2;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 집을 칠할 때, 이웃한 집의 색이 같아선 안되고, 마지막 집과 첫번째 집의 색도 같아선 안된다.
        // 집을 칠하는 비용이 각각 주어질 때 최소의 비용을 구하여라
        // i번째 행동이 i+1번째 행동에 영향을 미친다 -> DP로 풀자!
        // 첫번째 집과 마지막 집과도 색에 영향을 미친다. -> 첫번째 집을 특정해서 칠하자(총 3회)
        // dp[i][j] -> i번째 집을 j번째 색으로 칠할 때 최소 누적 비용
        // -> (i -1)번째 집을 j색이 아닌 다른 두 가지색으로 칠한 비용 중 최소 비용을 고른 후, i번째 집을 j 색으로 칠할 때의 비용을 더한다.
        // 최종 최소비용은 시작한 색과 다른 두 가지 색으로 끝난 비용 2개 중 적은 쪽.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] costs = new int[n][3];
        for (int i = 0; i < costs.length; i++) {
            for (int j = 0; j < costs[i].length; j++)
                costs[i][j] = sc.nextInt();
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++)
            min = Math.min(min, calcCost(i, costs));
        System.out.println(min);
    }

    static int calcCost(int startWith, int[][] costs) { // startWith 색으로 칠하기 시작했을 때 최소 비용을 구하는 함수
        final int MAX = 1_000_000;
        int[][] dp = new int[costs.length][3];
        for (int[] d : dp)  // MAX 비용으로 미리 다 채워준다.
            Arrays.fill(d, MAX);
        dp[0][startWith] = costs[0][startWith];     // 시작하는 색만 값을 정해줌.

        for (int i = 1; i < dp.length; i++) {   // i번째 집을
            for (int j = 0; j < dp[i].length; j++) {    // j번째 색으로 칠하려고 한다.
                int min = MAX;
                for (int k = 0; k < 3; k++) {       // (i - 1)번째 집을 j번째 색으로 칠하지 않은 값 중 최소를 구해
                    if (j == k)
                        continue;
                    min = Math.min(min, dp[i - 1][k]);
                }
                dp[i][j] = min + costs[i][j];   // 이번 비용을 더해 저장한다.
            }
        }

        int min = MAX;
        for (int i = 0; i < 3; i++) {       // 최종 비용은
            if (startWith == i)             // 시작했던 색이 아닌 다른 두 색 중
                continue;
            min = Math.min(min, dp[dp.length - 1][i]);      // 최소값
        }
        return min;
    }
}