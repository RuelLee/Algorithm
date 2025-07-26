/*
 Author : Ruel
 Problem : Baekjoon 17209번 새내기와 헌내기
 Problem address : https://www.acmicpc.net/problem/17209
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17209_새내기와헌내기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] adjMatrix;
    static int[] teams;

    public static void main(String[] args) throws IOException {
        // n명의 학생이 주어진다. 학생들은 새내기 혹은 헌내기이며
        // 새내기들은 진실만을, 헌내기들은 거짓만을 말한다.
        // n개의 줄에 각 학생들이 다른 학생을 헌내기라고 신고한 정보가 주어진다.
        // 이 때, 가능한 헌내기의 가장 많은 수는?
        //
        // 이분 그래프 문제
        // i 학생이 j 학생을 신고한 경우, i 학생과 j 학생 둘 중 한 명은 헌내기이다.
        // 서로 다른 그룹으로 묶여야하는 것이다.
        // 모든 학생을 살펴보며, 신고한 내역에 서로 다른 그룹으로 나눈다.
        // 이러한 두 그룹이 여러개 생길 수 있다.
        // 두 그룹 중 많은 수를 누적시킨 것이 가능한 헌내기의 가장 많은 수이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 학생
        int n = Integer.parseInt(br.readLine());
        
        // 헌내기 신고 내역
        adjMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == '1')
                    adjMatrix[i][j] = adjMatrix[j][i] = 1;
            }
        }
        
        // 각 학생의 팀
        // 모든 학생에 대해 홀짝 두 팀으로 나눌 것이다.
        // 2씩 증가하는데 학생은 최대 2000명이므로 팀의 번호는 최대 3998이 될 수도 있다.
        teams = new int[n];
        Arrays.fill(teams, -1);
        int counter = -2;
        for (int i = 0; i < teams.length; i++) {
            // 아직 팀 배정이 안되었다면
            // 팀 배정 시작
            if (teams[i] == -1)
                divideTeam(i, counter += 2);
        }
        
        // 각 팀원의 수 합산
        int[] teamCounter = new int[4000];
        for (int i = 0; i < teams.length; i++)
            teamCounter[teams[i]]++;

        int answer = 0;
        // 홀짝 팀 중 더 많은 수의 학생이 포함된 팀을 누적시켜나간다.
        for (int i = 0; i < teamCounter.length; i += 2)
            answer += Math.max(teamCounter[i], teamCounter[i + 1]);
        // 답 출력
        System.out.println(answer);
    }

    // idx와 신고 내역을 바탕으로 신고한 학생들을 서로 다른 팀으로 나눈다.
    static void divideTeam(int idx, int team) {
        teams[idx] = team;
        // idx가 신고한 학생들
        for (int next = 0; next < adjMatrix[idx].length; next++) {
            // next가 신고를 받았고, 아직 팀배정이 안됐다면
            // idx와는 다른 홀짝팀에 배분되어야한다.
            if (adjMatrix[idx][next] == 1 && teams[next] == -1)
                divideTeam(next, (team % 2 == 0 ? team + 1 : team - 1));
        }
    }
}