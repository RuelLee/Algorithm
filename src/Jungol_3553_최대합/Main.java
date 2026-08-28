/*
 Author : Ruel
 Problem : Jungol 3553번 최대 합
 Problem address : https://jungol.co.kr/problem/3553
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3553_최대합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 연속하지 않은 수를 선택하여 더한 후 m으로 나눈 나머지를 최대화하고자 한다.
        // 최댓값은?
        //
        // DP 문제
        // 나머지의 합이기 때문에, 수들을 m으로 나눈 나머지들로 비교해도 무방하다.
        // dp[계산된 수의 idx][0 ~ m-1까지의 나머지] = 가능 여부로 채워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 수, 나누는 수 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 수
        st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = Integer.parseInt(st.nextToken());

        // dp[계산된 수의 idx][0 ~ m-1까지의 나머지] = 가능 여부
        // 2개 이전의 결과, 3개 이전의 결과까지 필요하다.
        // 4개의 공간을 돌아가며 사용한다.
        boolean[][] dp = new boolean[4][m];
        dp[0][0] = dp[1][0] = dp[2][0] = dp[3][0] = true;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 3개 이전의 수 혹은 2개 이전의 수까지 결과들 중
                // 나머지의 합이 j가 되는 경우
                // j + arr[i] 결과를 반영
                if (dp[(i + 1) % 4][j] || dp[(i + 2) % 4][j]) {
                    dp[i % 4][(j + arr[i]) % m] = true;
                    ans = Math.max(ans, (j + arr[i]) % m);
                }
            }
        }
        System.out.println(ans);
    }
}