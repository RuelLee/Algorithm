/*
 Author : Ruel
 Problem : Baekjoon 2673번 교차하지 않는 원의 현들의 최대집합
 Problem address : https://www.acmicpc.net/problem/2673
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2673_교차하지않는원의현들의최대집합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 원 둘레에 100개의 점이 일정한 간격으로 시계방향으로 1 ~ 100 번호가 붙여져있다.
        // n개의 선분(원의 현)이 두 점으로 표현된다.
        // 서로 교차하지 않는 최대 크기의 현의 집합을 구하고자할 때, 그 크기는?
        //
        // DP 문제
        // dp[i][j] = i ~ j번까지의 점들 중 서로 교차하지않는 현의 최대개수로 정의하고 채워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 선분
        int n = Integer.parseInt(br.readLine());
        int[][] dp = new int[100][100];
        boolean[][] lines = new boolean[100][100];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 1 ~ 100이므로 0 ~ 99로 값 보정
            int left = Integer.parseInt(st.nextToken()) - 1;
            int right = Integer.parseInt(st.nextToken()) - 1;
            // 대소관계 보정
            if (right < left) {
                int temp = left;
                left = right;
                right = temp;
            }
            lines[left][right] = true;
        }

        int max = 0;
        // 길이가 diff인 구간에 대해 탐색
        for (int diff = 1; diff <= 99; diff++) {
            // 왼쪽 끝 점
            for (int start = 0; start + diff <= 99; start++) {
                // start ~ mid, mid ~ start + diff 까지의 범위를 합친다.
                for (int mid = start + 1; mid < start + diff; mid++) {
                    dp[start][start + diff] = Math.max(dp[start][start + diff],
                            dp[start][mid] + dp[mid][start + diff]);
                }

                // 혹시 start ~ start + diff에 해당하는 선분이 존재한다면
                // 안의 현들을 포함하는 형태로 하나 더 추가 가능
                if (lines[start][start + diff])
                    dp[start][start + diff]++;
                // max에 최댓값 갱신
                max = Math.max(max, dp[start][start + diff]);
            }
        }

        // 찾은 최댓값 출력
        System.out.println(max);
    }
}