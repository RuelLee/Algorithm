/*
 Author : Ruel
 Problem : Baekjoon 14462번 소가 길을 건너간 이유 8
 Problem address : https://www.acmicpc.net/problem/14462
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14462_소가길을건너간이유8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 왼쪽과 오른쪽에 n개의 목초지가 있고
        // 각 목초지에는 소가 한마리씩 있다.
        // 양쪽 목초지를 횡단보도로 이으려 하는데, 소들은 번호의 차가 4이하인 소들과 친하다고 한다.
        // 왼쪽과 오른쪽 각 목초지에 있는 소들의 번호가 주어질 때
        // 최대한 많은 목초지들을 횡단보도로 잇고자 한다.
        // 이을 수 있는 목초지의 수는?
        //
        // DP문제
        // 어디까지 소들을 살펴봤는지가 중요하므로 이차원 배열로
        // dp[왼쪽소][오른쪽소]로 정하고 해당 소까지 살펴봤을 때, 그 때까지 만든 횡단보도의 최대수로 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 양편에 n개의 목초지가 있다.
        int n = Integer.parseInt(br.readLine());

        // 왼쪽 소들
        int[] left = new int[n];
        for (int i = 0; i < left.length; i++)
            left[i] = Integer.parseInt(br.readLine());
        // 오른쪽 소들
        int[] right = new int[n];
        for (int i = 0; i < right.length; i++)
            right[i] = Integer.parseInt(br.readLine());

        // dp[왼쪽소][오른쪽소]
        int[][] dp = new int[n + 1][n + 1];
        // 최대 횡단보도의 수
        int max = 0;

        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length - 1; j++) {
                // (i-1, j)까지 고려한 경우가 (i, j)까지 고려한 경우보다 값이 더 큰지 확인
                if (i > 1)
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                // 마찬가지로 (i, j-1)까지 고려한 경우가 더 큰 경우
                if (j > 1)
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);

                // 왼쪽 i번째 소와 오른쪽 j번째 소가 서로 친한 경우
                // 횡단보도로 잇는다.
                if (Math.abs(left[i] - right[j]) <= 4) {
                    // 위 경우과 최대 횡단보도의 수를 갱신하는지 확인.
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i][j] + 1);
                    // 전체 결과에서 최대값인지 확인.
                    max = Math.max(max, dp[i + 1][j + 1]);
                }
            }
        }

        // 전체 결과에 대한 최댓값 출력.
        System.out.println(max);
    }
}