/*
 Author : Ruel
 Problem : Baekjoon 22236번 가희와 비행기
 Problem address : https://www.acmicpc.net/problem/22236
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22236_가희와비행기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 비행기는 운행하며 고도를 1상승 혹은 1하강을 한다(수평 x)
        // 고도가 0일 때는 착륙을 할 때 뿐이다.
        // d거리가 떨어진 지점으로 일직선으로 날아갈 때
        // 가능한 비행 가짓수는?
        // 가짓수가 클 수 있으므로 m으로 나눈 나머지를 출력한다.
        //
        // DP 문제
        // 거리와 고도에 따른 2차원 배열을 세우고 가짓수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 거리 d
        int d = Integer.parseInt(st.nextToken());
        // 가짓수의 나머지를 구할 m
        int m = Integer.parseInt(st.nextToken());

        // 거리와 고도에 따른 2차원 배열
        // 상승 or 하강을 반복하며 d만큼의 거리를 가므로
        // 최대 고도가 d/2 보다 높아지면 0으로 하강이 불가하므로
        // 맞게끔 크기 조정
        long[][] dp = new long[d + 1][d / 2 + 1];
        // 처음엔 고도 0 위치에서 시작
        dp[0][0] = 1;
        // 거리
        for (int i = 1; i < dp.length; i++) {
            // 고도
            for (int j = 1; j < dp[i].length; j++) {
                // 상승
                dp[i][j] += dp[i - 1][j - 1];
                // 하강
                dp[i][j] += (j + 1 < dp[i - 1].length ? dp[i - 1][j + 1] : 0);
                dp[i][j] %= m;
            }
        }

        // d-1 위치에서 고도 1일 경우에만
        // d 위치에 착륙할 수 있다.
        System.out.println(dp[d - 1][1]);
    }
}