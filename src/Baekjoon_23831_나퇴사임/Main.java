/*
 Author : Ruel
 Problem : Baekjoon 23831번 나 퇴사임?
 Problem address : https://www.acmicpc.net/problem/23831
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23831_나퇴사임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 자습시간에는 정독실, 소학습실, 휴게실 세 장소 중 하나에서 공부하거나, 요양을 할 수 있다.
        // 요양은 최대 a회까지 가능하고, 휴게실에서 이틀 연속 자습할 경우, 게임하는 것으로 판단되어 퇴사된다.
        // 정독실이나 소학습실에서 자습을 b회 미만으로 할 경우, 학습 의지 상실로 판단되어 퇴사 처리 된다.
        // 날마다 각 장소나 요양할 경우, 얻을 수 있는 만족도가 다르며, 해당 값들이 주어진다.
        // n일 동안 얻을 수 있는 최대 만족도는?
        //
        // dp 문제
        // dp[날짜][정독실이나 소학습실에서 자습한 날][어제 휴게실에서 자습했는지][휴양 신청 횟수] = 최대 만족도
        // 로 dp를 세우고 값을 채운다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n일
        int n = Integer.parseInt(br.readLine());
        // 최대 요양 신청 횟수 a, 정독실이나 소학습실 최소 이용 횟수 b
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 각 장소의 만족도들
        int[][] places = new int[n][4];
        for (int i = 0; i < places.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < places[i].length; j++)
                places[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // dp
        int[][][][] dp = new int[n + 1][b + 1][2][a + 1];
        // 첫날 정독실이나 소학습실에서 자습한 경우
        dp[1][Math.min(1, b)][0][0] = Math.max(places[0][0], places[0][1]);
        // 휴게실에서 자습한 경우
        dp[1][0][1][0] = places[0][2];
        // 요양 신청한 경우
        if (a >= 1)
            dp[1][0][0][1] = places[0][3];
        
        // 둘째날부터는 반복문으로 처리
        for (int i = 1; i < places.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    for (int l = 0; l < dp[i][j][k].length; l++) {
                        // 값이 0인 경우, 불가능한 경우. 건너뜀.
                        if (dp[i][j][k][l] == 0)
                            continue;
                        
                        // 정독실이나 소학습실에서 자습하는 경우
                        dp[i + 1][Math.min(j + 1, b)][0][l]
                                = Math.max(dp[i + 1][Math.min(j + 1, b)][0][l], dp[i][j][k][l] + Math.max(places[i][0], places[i][1]));

                        // 휴게실에서 자습하려면, 전날 휴게실을 이용했으면 안된다.
                        if (k == 0)
                            dp[i + 1][j][1][l] = Math.max(dp[i + 1][j][1][l], dp[i][j][k][l] + places[i][2]);

                        // 요양 신청을 하려면 요양 신청횟수가 a회 미만이어야 한다.
                        if (l < a)
                            dp[i + 1][j][0][l + 1] = Math.max(dp[i + 1][j][0][l + 1], dp[i][j][k][l] + places[i][3]);
                    }
                }
            }
        }

        // 마지막 n일까지 지난 후,
        // 정독실이나 소학습실 이용한 횟수가 b회 이상이어야 한다
        // 해당하는 경우들 중 최대 만족도를 찾는다.
        int max = 0;
        for (int i = 0; i < dp[n][b].length; i++) {
            for (int j = 0; j < dp[n][b][i].length; j++)
                max = Math.max(max, dp[n][b][i][j]);
        }
        // 답 출력
        System.out.println(max);
    }
}