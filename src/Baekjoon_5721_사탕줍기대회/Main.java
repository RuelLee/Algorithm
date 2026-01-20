/*
 Author : Ruel
 Problem : Baekjoon 5721번 사탕 줍기 대회
 Problem address : https://www.acmicpc.net/problem/5721
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5721_사탕줍기대회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m ( <= 10^5) 크기의 격자판이 주어진다.
        // 각 격자판에는 최대 1000개의 사탕이 있다.
        // 한 격자의 사탕을 선택하면, 윗 row와 아랫 row 그리고 양 옆의 칸의 사탕들이 사라진다.
        // 모든 사탕이 사라지기 전까지 사탕을 선택할 수 있다면, 가장 많은 사탕을 선택할 때, 그 수는?
        //
        // DP 문제
        // 먼저 하나의 줄만 놓고 보자. 하나의 사탕을 선택하면 양 옆의 사탕이 사라지므로
        // 이를 dp로 풀고자 한다면,
        // dp[i] = i번째까지 선택한 최대 사탕의 수
        // 그리고 dp[i]는 dp[i-2] 값 + i번째 사탕 혹은 dp[i-1] 중 더 큰 값을 선택하면 된다.
        // 이제 여러 열을 보자.
        // 하나의 열만 봤을 때와 마찬가지로
        // 하나의 열에서 사탕을 고른다면, 이전 열과 다음 열의 사탕들이 사라진다.
        // 따라서 이 또한 dp로 표현하자면
        // dp[i] = i번째 열까지 선택한 최대 사탕의 수
        // dp[i]는 마찬가지로 dp[i-2]의 값 + 현재 열에서 선택한 최대 사탕의 수, dp[i-1] 중 더 큰 값을 선택하면 된다.
        // 하나의 열에서 dp를 통해 최대 고를 수 있는 사탕의 수를 계산하고
        // 이를 바탕으로 여러 열에서 dp를 통해 고를 수 있는 최대 사탕의 수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n열, m행
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 각 열에서 고를 수 있는 최대 사탕의 수
        int[] max = new int[3];
        // 하나의 열을 계산할 때, 쓸 dp 공간
        int[] dp = new int[3];
        // 현재 사탕과 이전 사탕의 개수를 둘 다 갖고 있어야하므로 해당 배열
        int[] preAndCurrentNum = new int[2];
        StringBuilder sb = new StringBuilder();
        while (n != 0 && m != 0) {
            Arrays.fill(max, 0);

            // n개의 열에 대해
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                // dp, 현재와 이전 수 초기화
                Arrays.fill(dp, 0);
                Arrays.fill(preAndCurrentNum, 0);
                for (int j = 0; j < m; j++) {
                    // 현재 수 입력
                    preAndCurrentNum[j % 2] = Integer.parseInt(st.nextToken());
                    // i까지의 최대 사탕의 개수는
                    // 2번 이전의 dp값 + 현재 사탕 혹은 1번 이전의 dp값 중 더 큰 값을 선택
                    dp[j % 3] = Math.max(dp[(j + 1) % 3] + preAndCurrentNum[j % 2], dp[(j + 2) % 3]);
                }

                // 이번 열에서의 최대 사탕의 개수는 dp의 마지막 두 칸 중 큰 값
                // 이를 바탕으로 여러 열에서의 최대 사탕의 개수를 계산.
                // 2번 이전의 열까지의 값과 이번 열의 사탕을 선택하거나
                // 1번 이전의 열까지의 값 중 더 큰 값을 선택
                max[i % 3] = Math.max(max[(i + 1) % 3] + Math.max(dp[m % 3], dp[(m + 2) % 3]),
                        max[(i + 2) % 3]);
            }
            // 답 기록
            sb.append(Math.max(max[n % 3], max[(n + 2) % 3])).append("\n");
            // 다음 테스트케이스를 위한 입력
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
        }
        // 답 출력
        System.out.print(sb);
    }
}