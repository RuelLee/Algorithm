/*
 Author : Ruel
 Problem : Baekjoon 11578번 팀원 모집
 Problem address : https://www.acmicpc.net/problem/11578
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11578_팀원모집;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 문제, m명의 학생이 주어지며
        // 각 학생들이 풀 수 있는 문제가 주어진다.
        // 최소 학생 수의 조합으로 모든 문제를 풀려고할 때
        // 팀원의 수는?
        //
        // 브루트 포스, 비트마스킹 문제
        // n과 m이 둘 다 10이하로 매우 작은 문제
        // 따라서 모든 조합을 다 살펴보며 최소 학생 수를 찾아나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 주어지는 n과 m
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 학생이 풀 수 있는 문제를 비트마스킹을 통해 나타낸다.
        int[] capacity = new int[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // 풀 수 있는 문제의 수
            int problems = Integer.parseInt(st.nextToken());
            // 비트마스킹
            for (int j = 0; j < problems; j++)
                capacity[i] |= (1 << Integer.parseInt(st.nextToken()) - 1);
        }

        // 조합 가능한 최소 팀원의 수
        int minTeammate = findMinTeam(0, 0, 0, capacity, n);
        // 초기값이 돌아왔다면 모든 문제를 푸는 것이 불가능한 경우. -1 반환
        // 그렇지 않다면 반환된 최소 팀원의 수를 출력한다.
        System.out.println(minTeammate == Integer.MAX_VALUE ? -1 : minTeammate);
    }

    static int findMinTeam(int idx, int bitmask, int teammate, int[] capacity, int n) {
        if (idx >= capacity.length) {       // 마지막 학생까지 조합이 끝났고
            // 모든 문제를 풀었다면 해당 팀원의 수 반환.
            if (bitmask == (1 << n) - 1)
                return teammate;
            // 모든 문제를 풀지 못했다면 큰 값 반환.
            return Integer.MAX_VALUE;
        }

        // 조합이 아직 끝나지 않았다면
        // idx 번째 학생을 포함시키지 않는 경우와
        // 포함시키는 경우 중 어느 경우가 최종 선정되는 팀의 팀원 수가 더 적은지 살펴보고
        // 더 적은 쪽을 반환.
        return Math.min(findMinTeam(idx + 1, bitmask, teammate, capacity, n),
                findMinTeam(idx + 1, bitmask | capacity[idx], teammate + 1, capacity, n));
    }
}