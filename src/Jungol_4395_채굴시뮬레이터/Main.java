/*
 Author : Ruel
 Problem : Jungol 4395번 채굴 시뮬레이터
 Problem address : https://jungol.co.kr/problem/4395
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4395_채굴시뮬레이터;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // m개의 광물들에 대해 kg당 가격과, n개의 광물 등장 정보가 s e t로 주어진다.
        // s와 e는 출현 시각과 사라지는 시각, 그리고 t는 종류이다.
        // 하나의 출현에 대해 온전히 해당 출현에 대해서만 채굴해야한다고 할 때
        // 얻을 수 있는 최대 이익은?
        //
        // dp 문제
        // dp[i] = i시각까지 채굴했을 때, 얻을 수 있는 최대 이익으로 채워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // m개의 광물, n개의 출현 정보
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // kg당 가격
        int[] costs = new int[m + 1];
        for (int i = 1; i < m + 1; i++)
            costs[i] = Integer.parseInt(br.readLine());

        // 출현 정보
        int[][] appearances = new int[n][3];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                appearances[i][j] = Integer.parseInt(st.nextToken());
            appearances[i][2] = costs[Integer.parseInt(st.nextToken())];
        }
        // 출현 시각에 따라 오름차순 정렬
        Arrays.sort(appearances, Comparator.comparingInt(a -> a[0]));

        // dp[i] = i시각까지 채굴했을 때, 얻을 수 있는 최대 이익
        int[] dp = new int[15_001];
        for (int i = 0; i < appearances.length; i++) {
            // 현재 출현 광물을 온전히 채굴했을 때 얻는 이익
            int value = (appearances[i][1] - appearances[i][0]) * appearances[i][2];
            // 이전까지 등장한 채굴 이익과의 최대 합
            int sum = value + dp[appearances[i][0] - 1];
            // 이후 값 갱신
            for (int j = appearances[i][1] - 1; j < dp.length; j++)
                dp[j] = Math.max(dp[j], sum);
        }
        // 답 출력
        System.out.println(dp[15_000]);
    }
}