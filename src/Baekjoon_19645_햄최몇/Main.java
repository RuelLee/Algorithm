/*
 Author : Ruel
 Problem : Baekjoon 19645번 햄최몇?
 Problem address : https://www.acmicpc.net/problem/19645
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19645_햄최몇;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개(50 이하의 자연수)의 햄버거와 각 햄버거의 효용(50이하의 자연수)이 주어진다.
        // 세 명이 햄버거를 나눠먹는데, 주인공이 셋 중에선 가장 적거나 같은 효용합을 갖되
        // 가질 수 있는 한 최대한의 효용합을 갖고자 한다.
        // 주인공이 얻을 수 있는 효용합의 최대값은?
        //
        // DP, 배낭 문제
        // dp[a][b] = c로 정하고
        // 세 사람이 가질 수 있는 효용합을 a, b, c로 하였다.
        // 그 중 가능한 경우를 모두 구하고, 세 값의 최소값 중 가장 큰 값을 찾아낸다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 햄버거
        int n = Integer.parseInt(br.readLine());
        int[] burgers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp[a][b] = c
        // 세 사람이 얻는 효용 a, b, c
        // 각 사람이 얻는 효용합은 전체 햄버거 효용합의 반을 넘을 수는 없다.
        // 따라서 dp를 50 * 25 + 1값으로 제한하여 생성
        int[][] dp = new int[50 * 25 + 1][50 * 25 + 1];
        // 초기값 세팅
        for (int[] d : dp)
            Arrays.fill(d, -1);
        dp[0][0] = 0;
        // 각 햄버거마다 검사
        for (int burger : burgers) {
            // 한 햄버거를 중복해서 먹지 않기 위해
            // 큰 값에서부터 검사
            for (int a = dp.length - 1; a >= 0; a--) {
                for (int b = dp[a].length - 1; b >= 0; b--) {
                    // 값이 -1 이라면 불가능한 경우이므로 건너 뜀.
                    if (dp[a][b] == -1)
                        continue;

                    // 첫번째 사람이 먹을 수 있다면 먹인다.
                    if (a + burger < dp.length)
                        dp[a + burger][b] = Math.max(dp[a + burger][b], dp[a][b]);
                    // 두번째 사람이 먹을 수 있다면 먹인다.
                    if (b + burger < dp[a].length)
                        dp[a][b + burger] = Math.max(dp[a][b + burger], dp[a][b]);
                    // 세번째 사람에게 먹인다.
                    dp[a][b] += burger;
                }
            }
        }

        // 모든 값을 찾아보며
        // 세 값 중 최소값이 최대인 경우를 찾는다.
        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++)
                max = Math.max(max, Math.min(i, Math.min(j, dp[i][j])));
        }
        // 해당 값 출력
        System.out.println(max);
    }
}