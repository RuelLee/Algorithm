/*
 Author : Ruel
 Problem : Jungol 6981번 눈덩이 굴리기
 Problem address : https://jungol.co.kr/problem/6981
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_6981_눈덩이굴리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 앞마당의 길이가 n이고, 1 ~ n까지의 i 위치에 Ai의 눈이 쌓여있다.
        // m초 동안 눈덩이를 굴려 최대한 크기를 크게 만들고자 한다.
        // 1초 동안
        // 1. 현재 위치에서 +1칸으로 굴려, 다음 칸의 눈만큼을 눈덩이가 커진다.
        // 2. 현재 위치에서 +2칸으로 던져, 현재 눈덩이 크기가 1/2로 줄어들고, +2칸 위치의 눈만큼 눈덩이가 커진다.
        // 최대 키울 수 있는 크기는?
        //
        // DP 문제
        // dp[i][j] = i까지 눈을 j초까지 키웠을 때 키울 수 있는 최대 크기로 정하고 채운다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n의 마당, 제한 시간 m초
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 칸의 눈이 쌓인 정도
        int[] a = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            a[i] = Integer.parseInt(st.nextToken());

        // dp[i][j] = i까지 눈을 j초까지 키웠을 때 키울 수 있는 최대 크기
        int[][] dp = new int[n + 1][m + 1];
        // 처음 위치에 따른 값과 굴렸을 때, 던졌을 때의 값을 초기화
        dp[0][0] = 1;
        dp[1][1] = 1 + a[1];
        int max = dp[1][1];
        if (n >= 2)
            max = Math.max(max, dp[2][1] = a[2]);

        // 모든 칸을 방문하며 체크
        for (int i = 1; i < n; i++) {
            // i번째 칸에 도달하려면 최소 (i + 1) / 2초가 걸린다. 해당 초부터 시작
            for (int j = (i + 1) / 2; j < m; j++) {
                max = Math.max(max, dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i][j] + a[i + 1]));

                if (i + 2 <= n)
                    max = Math.max(max, dp[i + 2][j + 1] = Math.max(dp[i + 2][j + 1], dp[i][j] / 2 + a[i + 2]));
            }
        }
        // 답 출력
        System.out.println(max);
    }
}