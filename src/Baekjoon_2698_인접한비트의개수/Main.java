/*
 Author : Ruel
 Problem : Baekjoon 2698번 인접한 비트의 개수
 Problem address : https://www.acmicpc.net/problem/2698
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2698_인접한비트의개수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0과 1로 이루어진 수열 S가 주어지고 인접한 비트의 개수는
        // s1 * s2 + s2 * s3 + ... + sn-1 * sn 으로 구할 수 있다.
        // 수열의 크기 n, 인접한 비트의 개수 k가 주어질 때 수열 s의 개수를 구하라. n과 k는 최대 100. 답은 2,147,483,647보다 작거나 같다.
        // n = 5, k = 2라면 11100, 01110, 00111, 10111, 11101, 11011 으로 6가지가 가능하다.
        //
        // DP문제
        // 답이 int 범위를 넘어가지 않는다고 명시되어있다.
        // 각 n에서 k에 따른 경우의 수를 구하되, 끝이 0으로 끝나는 경우와 1로 끝나는 경우를 구한다.
        // 1. n개의 비트에서 k개의 인접한 비트를 갖으며, 끝이 0으로 끝나는 경우는
        //  1) n-1개의 비트에서 k개의 인접한 비트를 갖으며, 0으로 끝난 경우에 0을 뒤에 붙인 경우
        //  2) n-1개의 비트에서 k개의 인접한 비트를 갖으며, 1으로 끝난 경우에 0을 뒤에 붙인 경우.
        // 두 가지 모두 성립한다.
        // 2. n개의 비트에서 k개의 인접한 비트를 갖으며, 끝이 1으로 끝나는 경우는
        //  1) n - 1개의 비트에서 k - 1개의 인접한 비트를 갖으며 1로 끝난 경우에 1을 뒤에 붙인 경우.
        //  2) n - 1개의 비트에서 k개의 인접한 비트를 갖으며 0으로 끝난 경우에 1을 뒤에 붙인 경우.
        // 위의 성질을 통해 점화식을 세우고 DP를 채워나가자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        // 여러개의 테스트케이스가 주어지므로, n과 k를 최대값에 대해 구하고, 각 테스트케이스 마다 답을 바로 출력하자.
        int[][][] dp = new int[101][101][2];
        dp[1][0][0] = dp[1][0][1] = 1;
        // i개의 비트에서
        for (int i = 2; i < dp.length; i++) {
            // 인접한 비트의 개수는 i를 넘을 수 없다.
            // j개의 인접한 비트를 갖는 경우.
            for (int j = 0; j < i; j++) {
                // i개의 비트에서 j개의 인접한 비트를 갖으며 0으로 끝나는 경우.
                // 위의 1번 경우.
                dp[i][j][0] = dp[i - 1][j][0] + dp[i - 1][j][1];

                // i개의 비트에서 j개의 인접한 비트를 갖으면 1로 끝나는 경우.
                // 위의 2번 경우.
                dp[i][j][1] = (j > 0 ? dp[i - 1][j - 1][1] : 0) + dp[i - 1][j][0];
            }
        }

        StringBuilder sb = new StringBuilder();
        // 각 테스트케이스에 대한 답안을 위에 구해둔 DP테이블을 참고하여 바로 출력.
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            sb.append(Arrays.stream(dp[n][k]).sum()).append("\n");
        }
        System.out.println(sb);
    }
}