/*
 Author : Ruel
 Problem : Baekjoon 20667번 크롬
 Problem address : https://www.acmicpc.net/problem/20667
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30667_크롬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개 크롬 탭들의 cpu, 메모리, 우선순위가 주어진다.
        // 이 중 몇 개를 닫아, m 이상의 cpu, k 이상의 메모리를 확보하고자 한다.
        // 이 때 닫아야하는 탭들의 우선순위 합의 최솟값은?
        //
        // 배낭 문제
        // 이긴한데 메모리가 최대 10만 * 100으로 주어질 수 있으므로
        // dp[cpu][메모리] = 우선순위로 dp를 세울 수는 없다.
        // 큰 값인 메모리를 배열의 값으로 바꾸어
        // dp[우선순위][cpu사용량] = 메모리로 세우고
        // 배낭 문제를 해결한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 탭, 목표 cpu 사용량 m, 목표 메모리 할당량 k
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 탭들
        int[][] tabs = new int[n][3];
        for (int i = 0; i < tabs.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < tabs[i].length; j++)
                tabs[i][j] = Integer.parseInt(st.nextToken());
        }

        // 우선 순위는 최대 5까지 주어지므로
        // n * 5가 최대값
        // cpu는 최소 m이상 확보하면 된다.
        // 그 이상을 확보하는 것에 대해서는 별 상관 없다.
        int[][] dp = new int[5 * n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // 초기값
        dp[0][0] = 0;

        // dp, 배낭
        // 모든 탭들에 대해
        for (int[] tab : tabs) {
            // 우선 순위 합이 dp를 넘지 않도록
            for (int i = dp.length - 1 - tab[2]; i >= 0; i--) {
                // cpu는 더 많은 사용량을 확보하더라도 상관없다.
                for (int j = dp[i].length - 1; j >= 0; j--) {
                    // 만약 dp[i][j]가 -1 값이라면 불가능한 경우이므로 건너뛴다.
                    if (dp[i][j] == -1)
                        continue;

                    // 현재 j사용량이고, tab을 닫으면 j + tab[0]만큼 사용량이 확보된다.
                    // 하지만 dp를 넘어서는 안되므로 m과 더 작은 값을 사용한다.
                    int nextCpu = Math.min(j + tab[0], m);
                    // 현재 우선순위 i, cpu 사용량 j, 메모리 확보량 dp[i][j]인 상태에서
                    // tab을 추가적으로 닫는 경우를 계산한다.
                    dp[i + tab[2]][nextCpu] = Math.max(dp[i + tab[2]][nextCpu], dp[i][j] + tab[1]);
                }
            }
        }

        // 답 초기값
        int answer = -1;
        // dp에서 모든 우선순위를 살펴보며 cpu 사용량을 m이상 확보하며
        for (int i = 0; i < dp.length; i++) {
            // 메모리 또한 k 이상 확보했다면
            if (dp[i][m] >= k) {
                // 해당 우선순위 기록 후 반복문 종료
                answer = i;
                break;
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}