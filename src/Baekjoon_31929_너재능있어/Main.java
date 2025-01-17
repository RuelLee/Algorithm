/*
 Author : Ruel
 Problem : Baekjoon 31929번 너 재능 있어
 Problem address : https://www.acmicpc.net/problem/31929
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31929_너재능있어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 민석이는 게임을 한다.
        // 앞으로 n번의 승리와 m번의 패배를 하게될 것이다.
        // n개의 승리 시 얻을 수 있는 점수가 주어지고
        // 해당 번째 승리를 할 경우, 해당 점수를 얻는다.
        // m개의 패배 시 잃는 점수가 주어지고
        // 해당 번째 패배를 할 경우, 해당 점수를 잃는다.
        // 단, 한 번에 너무 큰 점수를 잃지 않도록 k점마다 점수 보호권이 존재한다.
        // 정수 a와 0 < b < k를 만족하는 정수가 존재하여, 현재 점수를 a * k + b점이라 할 때
        // 현재 질 경우, 원래 잃는 점수와 b점 중 더 작은 값의 점수를 잃는다.
        // n + m번의 게임 후, 얻을 수 있는 가장 높은 점수는?
        //
        // DP 문제
        // dp[승리횟수][패배횟수] = 최대 점수로 계산하면 된다.
        // 점수 보호권이 존재하여 이를 유의하며 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n회 동안 각 승리마다 얻을 수 있는 점수.
        int n = Integer.parseInt(br.readLine());
        int[] w = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < w.length; i++)
            w[i] = Integer.parseInt(st.nextToken());

        // m회 동안 각 패배마다 얻을 수 잃는 점수.
        int m = Integer.parseInt(br.readLine());
        int[] l = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < l.length; i++)
            l[i] = Integer.parseInt(st.nextToken());

        // 점수 보호권의 기준 정수 k
        int k = Integer.parseInt(br.readLine());
        // 점수가 음수로도 떨어질 수 있기 때문에
        // 위 경우 모듈러 연산에 오류가 발생할 수 있다.
        // 따라서 k의 배수에 해당하는 큰 수를 더해 모듈러 연산을 함으로써, 오류를 없앤다.
        int correction = 1000 * 100 / k * k;

        // dp
        int[][] dp = new int[n + 1][m + 1];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MIN_VALUE);
        dp[0][0] = 0;
        // 현재 i회 승리, j회 패배
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 아직 더 승리할 수 있다면
                // 승리하는 경우를 계산
                if (i + 1 < dp.length)
                    dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + w[i]);
                
                // 아직 더 해야할 패배가 남아있다면
                if (j + 1 < dp[i].length) {
                    // 잃게 되는 점수 계산.
                    // 원래 잃는 점수 l[j]와 점수 보호권이 발동되는지, 된다면 그 때의 잃게되는 점수를 비교하여
                    // 더 적은 점수를 잃게 된다.
                    int loseScore = Math.min(l[j], (dp[i][j] + correction) % k == 0 ? Integer.MAX_VALUE : (dp[i][j] + correction) % k);
                    // 해당 경우 반영
                    dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] - loseScore);
                }
            }
        }
        // 총 n회 승리, m회 패배 후, 얻을 수 있는 최대 점수 출력
        System.out.println(dp[n][m]);
    }
}