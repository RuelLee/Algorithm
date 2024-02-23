/*
 Author : Ruel
 Problem : Baekjoon 2421번 저금통
 Problem address : https://www.acmicpc.net/problem/2421
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2421_저금통;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 개의 저금통이 주어진다. 두 저금통엔 1원씩 들어있다.
        // 두 저금통에 1원씩 저금하여 각각 n원을 만들고자한다.
        // 주인공은 소수를 좋아하여 두 저금통의 금액을 이어붙인 수가 소수가 가장 많이 만들어지게끔 하고자 한다.
        // n이 4라면
        // 1,1 → 2,1 → 2,2 → 3,2 → 3,3 → 4,3 → 4,4
        // 위와 같은 경우엔 소수가 43 한번만 등장하지만
        // 1,1 → 2,1 → 3,1 → 4,1 → 4,2 → 4,3 → 4,4
        // 위의 경우엔 31, 41, 43 3번이 등장한다.
        // 가장 소수를 많이 만들고자할 때 그 횟수는?
        //
        // 에라토스테네스의 체, DP 문제
        // dp[왼쪽저금통금액][오른쪽저금통금액] = 여태까지 만든 소수의 수로 하여
        // 각각의 저금통에 들어있는 금액을 구분하여 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 에라토스테네스의 체로 소수들을 모두 구해둔다.
        boolean[] eratosthenes = new boolean[n * (int) Math.pow(10, (int) (Math.log(n) / Math.log(10) + 1)) + n + 1];
        Arrays.fill(eratosthenes, true);
        for (int i = 2; i * i <= eratosthenes.length; i++) {
            for (int j = 2; i * j < eratosthenes.length; j++)
                eratosthenes[i * j] = false;
        }

        //  dp를 통해 각 저금통에 들어있는 금액을 기준으로 만들 수 있는 최대 소수의 개수를 계산한다.
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                // 첫번째 저금통에 1원을 저금하는 경우.
                if (i + 1 < dp.length) {
                    // 만들어지는 해당 수
                    int next = (i + 1) * (int) Math.pow(10, (int) (Math.log(j) / Math.log(10) + 1)) + j;
                    // dp[i][j] -> dp[i+1][j]로 가는 경우
                    // next가 소수라면 dp[i][j]보다 소수 등장 횟수가 1 증가하고
                    // 그렇지 않다면 dp[i][j]와 같은 값을 갖게 된다.
                    dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + (eratosthenes[next] ? 1 : 0));
                }
                
                // 두번째 저금통에 1원을 저금하는 경우.
                if (j + 1 < dp.length) {
                    int next = i * (int) Math.pow(10, (int) (Math.log(j + 1) / Math.log(10) + 1)) + j + 1;
                    dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] + (eratosthenes[next] ? 1 : 0));
                }
            }
        }

        // 각각의 저금통에 n원을 모두 채웠을 때
        // 거쳐온 소수의 최대 개수 출력
        System.out.println(dp[n][n]);
    }
}