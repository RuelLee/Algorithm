/*
 Author : Ruel
 Problem : Jungol 1077번 배낭채우기1
 Problem address : https://jungol.co.kr/problem/1077번
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1077_배낭채우기1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 보석상에 도둑이 침임했다.
        // n개의 보석 종류가 주어지고, 도둑은 배낭에 담을 수 있는 최대 무게가 w이다.
        // 각 보석의 무게와 값어치가 주어질 때
        // 배낭에 담을 수 있는 최대 값어지는?
        // 각 보석은 종류 별로 개수가 무한하다고 한다.
        //
        // 배낭 문제
        // dp[무게] = 최대값어치로 놓고 채우면 된다.
        // 단 종류 별로 개수가 무제한이므로 같은 종류의 보석을 중복으로 담을 수 있다.
        // 따라서 dp를 채울 때, 역순으로 채우는 것이 아닌, 정순으로 채우며 중복을 허용한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 보석 종류, 배낭의 용량 w
        int n = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        // 종류 별 보석 정보
        int[][] gems = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                gems[i][j] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[w + 1];
        for (int[] gem : gems) {
            // dp를 정순으로 채워가며 중복을 허용한다.
            // 보통은 역순으로 채워 중복을 허용하지 않는다.
            for (int j = 0; j + gem[0] <= w; j++)
                dp[j + gem[0]] = Math.max(dp[j + gem[0]], dp[j] + gem[1]);
        }
        // 무게가 w일 때, 최대 가치 출력
        System.out.println(dp[w]);
    }
}