/*
 Author : Ruel
 Problem : Baekjoon 1079번 마피아
 Problem address : https://www.acmicpc.net/problem/1079
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1079_마피아;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] suspects;
    static int[][] diffs;
    static int mafia;

    public static void main(String[] args) throws IOException {
        // n명이 마피아 게임을 한다.
        // 참가자가 짝수일 땐 밤이고, 마피아가 죽일 사람 한 명을 고른다.
        // 홀수일 때는 낮이고, 시민들이 의심지수를 바탕으로 한 명을 죽인다.
        // 마피아가 사람을 죽일 때는 제시된 표에 따라 남은 참가자들의 의심도가 변화한다.
        // 마피아인 사람이 주어지고, 최대한 오랫동안 게임을 유지하고자할 때
        // 최대 며칠 동안 게임을 유지할 수 있는가
        //
        // 브루트포스 문제
        // 주어진 조건을 통해 게임을 유지할 수 있는 최대일을 그대로 구현한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 참가자 수
        int n = Integer.parseInt(br.readLine());
        // 의심 지수
        suspects = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 마피아가 사람을 죽일 때 변화하는 의심 지수 변동표
        diffs = new int[n][];
        for (int i = 0; i < diffs.length; i++)
            diffs[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 마피아
        mafia = Integer.parseInt(br.readLine());
        // 최대 게임 유지 날짜 출력.
        System.out.println(findMaxDay(0, n, new boolean[n]));
    }

    // 최대 게임 지속일을 찾는다.
    static int findMaxDay(int day, int citizen, boolean[] dead) {
        int maxDay = 0;
        // 밤일 경우
        if (citizen % 2 == 0) {
            // 마피아를 제외한 살아있는 모든 시민들을 죽여본다.
            for (int i = 0; i < dead.length; i++) {
                if (i != mafia && !dead[i]) {
                    // i번째 참가자를 죽이고
                    dead[i] = true;
                    // 의심 지수 변화
                    for (int j = 0; j < suspects.length; j++)
                        suspects[j] += diffs[i][j];
                    // 해당 상태로 다음 낮 진행
                    // 최대 게임 지속일 반환
                    maxDay = Math.max(maxDay, findMaxDay(day + 1, citizen - 1, dead));
                    
                    // i번째 사람과 의심 지수 복구
                    dead[i] = false;
                    for (int j = 0; j < suspects.length; j++)
                        suspects[j] -= diffs[i][j];
                }
            }
        } else {        // 낮일 경우
            // 의심 지수가 가장 높은 사람을 찾는다.
            int citizenTarget = -1;
            for (int i = 0; i < suspects.length; i++) {
                if (!dead[i] && (citizenTarget == -1 || suspects[i] > suspects[citizenTarget]))
                    citizenTarget = i;
            }

            // 그 사람이 마피아라면 게임 종료.
            // 현재 날짜 반환.
            if (citizenTarget == mafia)
                return day;
            else {      // 그렇지 않을 경우
                // citizenTarget은 죽고
                dead[citizenTarget] = true;
                // 게임을 밤으로 진행시켜 최대 지속 게임 일을 찾는다.
                maxDay = Math.max(maxDay, findMaxDay(day, citizen - 1, dead));
                // citizenTarget 복구.
                dead[citizenTarget] = false;
            }
        }
        // day 분기에서 가능한 최대 게임 지속일 반환.
        return maxDay;
    }
}