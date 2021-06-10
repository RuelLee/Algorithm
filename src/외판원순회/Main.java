package 외판원순회;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // n개의 지역이 주어지고
        // 각 지역으로 이동하는데 드는 비용을 행렬 형태로 주어질 때,
        // 모든 지점을 거쳐 출발점으로 돌아오는데, 드는 최소 비용을 구하라.
        // 모든 지점을 거쳐 다시 출발점으로 돌아와야하므로, 어느 지점에서 시작하건 고리 형태로 만들어져서 비용은 같다.
        // 임의로 출발 지점을 정한 후 다른 모든 지점을 거친 후, 마지막으로 출발점까지의 비용을 더해서 계산하자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] costs = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                costs[i][j] = sc.nextInt();
        }

        int[][] dp = new int[n][(int) Math.pow(2, n)];      // row는 각 지점, col은 비트마스킹을 통해, 각 지점의 방문여부를 나타내자.
        setDp(dp);                                          // dp[0][1] 값만 0으로 초기화해주고, 나머지는 큰 값으로 설정해주자.(출발지점을 0으로 설정)

        for (int col = 1; col < dp[0].length; col++) {      // 비트마스킹 적은 값부터
            for (int row = 0; row < dp.length; row++) {     // 각 row들을 돌며
                if (dp[row][col] == 16 * 1000 * 1000)       // 큰 값으로 세팅된 경우 방문할 수 없는 경우다. 건너 뛰자.
                    continue;
                if ((col & (1 << row)) != 0) {              // 비트마스킹에 현재 현재 지점이 포함되어있다면
                    for (int next = 0; next < dp.length; next++) {      // 각 지점들(방문 할)에 대해
                        if ((col & (1 << next)) != 0)                   // 이미 방문한 지점이라면 패쓰
                            continue;

                        // 아직 방문하지 않은 지점이라면,
                        // 각 지점에 저장되어있는 비용과, 지금 내가 있는 곳까지의 비용 + 현재 위치로부터 다음 위치까지의 비용 중 더 작은 값을 저장한다.
                        if (costs[row][next] != 0)
                            dp[next][col | (1 << next)] = Math.min(dp[next][col | (1 << next)], dp[row][col] + costs[row][next]);
                    }
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < dp.length; i++) {   // 모두 방문했다고 표시된 지점에서
            if (costs[i][0] == 0)               // 출발점인 0으로 다시 돌아가는 길이 없다면 패쓰
                continue;
            min = Math.min(min, dp[i][dp[0].length - 1] + costs[i][0]);     // 있다면 그 비용을 더해 최소값을 구하자.
        }
        System.out.println(min);
    }

    static void setDp(int[][] dp) {
        for (int[] d : dp)
            Arrays.fill(d, 16 * 1000 * 1000);
        dp[0][1] = 0;
    }
}