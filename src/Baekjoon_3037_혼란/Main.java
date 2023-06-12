/*
 Author : Ruel
 Problem : Baekjoon 3037번 혼란
 Problem address : https://www.acmicpc.net/problem/3037
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3037_혼란;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n개의 자연수로 이루어진 수열이 존재한다.
        // 먼저 등장하는 수보다 더 작은 값을 갖는 두 수의 쌍을 혼돈의 쌍이라고 한다.
        // 이 쌍의 개수가 수열의 혼란도이다.
        // (1, 4, 3, 2)는 (4, 3), (4, 2), (3, 2)로 혼란도가 3이다.
        // n이 주어질 때, 혼란도가 C인 수열의 개수를 구하라.
        //
        // DP, 누적합 문제
        // dp[i][j]라 할 때, i는 수열의 크기, j는 혼란도로 DP를 채워나가면 된다.
        // 단, 계산을 줄이기 위해 dp에 누적합을 적용한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // psums[i][j] 
        // 수열의 크기가 i일 때, 혼란도가 0 ~ j인 수열의 개수 합
        long[][] psums = new long[n + 1][c + 1];
        // 초기값으로 psum[0][0] = 1이다.
        // 누적합이므로 psum[0]은 모두 1로 채운다.
        Arrays.fill(psums[0], 1);
        for (int i = 1; i < psums.length; i++) {
            // 혼란도가 0인 수열은 오름차순인 수열 하나가 항상 존재한다.
            psums[i][0] = 1;

            // psums[i][j]는
            // 이전 psum[i][j-1]과
            // 크기가 (i - 1)인 수열에서 혼란도가 (j - i + 1) or (0)  ~ (j)였던 경우에
            // 이번 새로운 수를 배치함으로써 만들 수 있다.
            for (int j = 1; j < psums[i].length; j++) {
                psums[i][j] = (psums[i][j - 1] + psums[i - 1][j] -
                        ((i - 1) >= j ? 0 : psums[i - 1][j - i])) % LIMIT;
            }
        }

        // 우리가 원하는 값은 크기 n인 수열에서 혼란도가 c인 경우이므로
        // 모듈러 연산을 했음에 유의하며 해당 값만 계산하여 출력한다.
        System.out.println((psums[n][c] + LIMIT - psums[n][c - 1]) % LIMIT);
    }
}