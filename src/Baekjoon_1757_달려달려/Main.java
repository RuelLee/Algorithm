/*
 Author : Ruel
 Problem : Baekjoon 1757번 달려달려
 Problem address : https://www.acmicpc.net/problem/1757
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1757_달려달려;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 운동할 시간 n분과 지침 지수 제한 m이 주어진다
        // 지침 지수는 달릴 경우 1 상승, 쉴 경우 1 하락한다. 한 번 쉬기 시작하면 지침지수가 0이 될 때까지 쉰다.
        // 달리기가 끝나면 학생들은 다시 공부해야하기 때문에 지침 지수가 0이 되어야한다.
        // 그리고 각 분마다 1분 동안 달릴 수 있는 거리가 정해져있다.
        // 최대 달릴 수 있는 거리를 구하라.
        //
        // DP 문제
        // 검색을 해보니 2차원 내지 3차원 DP를 세우는 경우들이 있었다.
        // 하지만 한번 쉬기 시작하면 지침 지수가 0이될 때까지 쉰다는 조건으로 인해
        // 1차원 DP를 통해 풀 수 있을 거라 생각했다.
        // 각 시각의 시작일 때, 지침 지수가 0이며 달려온 거리의 최대 길이를 기록하면 될거라 생각했다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 총 달릴 수 있는 시간 n과 지침 지수 제한 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // n분이 지난 후에 지침 지수가 0이어야하므로
        // 우리가 원하는 값은 dp[n + 1]이다.
        int[] maxDistances = new int[n + 2];
        // 달릴 때는 '연속'하여 달리므로 누적합을 구한다.
        int[] psum = new int[n + 1];
        for (int i = 1; i < psum.length; i++)
            psum[i] = psum[i - 1] + Integer.parseInt(br.readLine());

        // 각 시각마다 계산을 한다.
        for (int i = 1; i < maxDistances.length; i++) {
            // i - 1분에서 안 달린 경우와 현재 i분에서의 값을 비교하고 더 큰 값을 i분의 최대 거리로 한다.
            maxDistances[i] = Math.max(maxDistances[i], maxDistances[i - 1]);
            // i분에서 연속하여 달릴 수 있는 분을 계산한다.
            // 달리는 시간과 쉬는 시간을 합한 시간이 n + 1을 넘어선 안되고
            // 연속하여 달리는 시간이 지침 지수를 넘어서도 안된다.
            for (int j = 1; i + j * 2 < maxDistances.length && j <= m; j++) {
                // j분 연속하여 달릴 경우 달릴 수 있는 거리
                int currentDistance = psum[i + j - 1] - psum[i - 1];
                // i분 상태에서 j분 동안 연속하여 달릴 경우
                // i + j * 2분에서 지침 지수가 다시 0이 된다.
                // 따라서 i + j * 2분에의 기록된 최대 거리와 현재의 최대 거리 + currentDistance 를 한 값과 비교하여 더 큰 값을 남긴다.
                maxDistances[i + j * 2] = Math.max(maxDistances[i + j * 2], maxDistances[i] + currentDistance);
            }
        }

        // 달리기가 모두 끝나고 최대 달린 거리를 출력한다.
        System.out.println(maxDistances[n + 1]);
    }
}