/*
 Author : Ruel
 Problem : Baekjoon 1633번 최고의 팀 만들기
 Problem address : https://www.acmicpc.net/problem/1633
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1633_최고의팀만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Player {
    int a;
    int b;

    public Player(int a, int b) {
        this.a = a;
        this.b = b;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 흑, 백으로 팀을 나눠 15명씩 총 30명이 하는 게임이 있다.
        // 30이상 1000이하의 선수들이 각 팀에서 발휘하는 능력들이 주어질 때
        // 팀들의 능력치 총합을 최대로 하는 선수들을 선발하고 싶다.
        // 그 때 팀들의 능력치 총합은?
        //
        // DP문제
        // dp를 통해 dp[흑의 인원][백의 인원]으로 DP를 세우고 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 선수의 수가 주어지지 않으므로 탄력적으로 수를 조절할 수 있는 리스트로 받는다.
        List<Player> list = new ArrayList<>();
        String input = br.readLine();
        while (input != null) {
            StringTokenizer st = new StringTokenizer(input);
            list.add(new Player(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
            input = br.readLine();
        }

        // 최대 15명씩 선수를 선발이 가능하므로 가로, 세로가 16인 2차원 배열로 DP를 세운다.
        int[][] dp = new int[16][16];
        // 모든 선수들에 대해
        for (Player p : list) {
            // 공간적 낭비를 막기 위해 DP는 역순으로 체크한다.
            for (int i = dp.length - 1; i >= 0; i--) {
                for (int j = dp[i].length - 1; j >= 0; j--) {
                    // 흑팀의 인원이 15 미만인 경우에 p를 흑팀에 넣을 경우
                    // 팀들의 능력치 총합이 최대가 되는지 확인하고 갱신.
                    if (i + 1 < dp.length)
                        dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + p.a);
                    // 백팀의 인원이 15 미만인 경우, p를 백팀에 넣는 경우
                    // 팀의 능력치 총합을 확인하고 값 갱신.
                    if (j + 1 < dp[i].length)
                        dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] + p.b);
                }
            }
        }

        // 모든 선수들을 살펴보았다면 흑백팀 15, 15명 모두 선출되었을 때
        // 능력치 총합을 출력한다.
        System.out.println(dp[15][15]);
    }
}