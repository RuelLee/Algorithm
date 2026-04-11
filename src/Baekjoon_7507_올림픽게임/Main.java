/*
 Author : Ruel
 Problem : Baekjoon 7507번 올림픽 게임
 Problem address : https://www.acmicpc.net/problem/7507
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7507_올림픽게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // m개의 경기에 대해
        // 경기가 이루어지는 날, 시작 시간, 종료 시간이 주어진다.
        // 경기장 별 이동 시간은 없다고 할 때
        // 최대한 많은 경기른 본다면 총 몇 경기를 볼 수 있는가
        //
        // 그리디, 정렬 문제
        // 경기들에 한해, 끝나는 시각을 기준으로, 종료 시각이 같다면 시작시간을 기준으로 오름차순 정렬한다.
        // 그리고, 경기들을 순차적으로 살펴보며, 현재 시작 경기가, 내가 마지막으로 본 경기의 종료 시각보다 늦게 시작한다면
        // 해당 경기를 보는 것으로 한다.
        // 이미 끝나는 시각을 기준으로 정렬했기 때문에, 항상 먼저 오는 경기를 볼 수 있는 경우 보는 것이 유리하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 테스트케이스
        int n = Integer.parseInt(br.readLine());
        // 각 경기들
        int[][] matches = new int[50000][2];
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < n; testCase++) {
            int m = Integer.parseInt(br.readLine());
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                // 경기가 이루어지는 날
                int day = (Integer.parseInt(st.nextToken()) - 1) * 10000;
                // 경기들의 시작 시각과 종료 시각에 날에 따른 값도 반영한다.
                for (int j = 0; j < 2; j++)
                    matches[i][j] = Integer.parseInt(st.nextToken()) + day;
            }
            // 종료 시각을 기준으로, 종료 시각이 같다면 시작 시각을 기준으로 정렬
            Arrays.sort(matches, 0, m, (o1, o2) -> {
                if (o1[1] == o2[1])
                    return Integer.compare(o1[0], o2[0]);
                return Integer.compare(o1[1], o2[1]);
            });

            // 최대 볼 수 있는 경기의 수를 센다.
            int cnt = 0;
            // 본 마지막 경기의 종료 시각
            int lastMatchEndTime = 0;
            for (int i = 0; i < m; i++) {
                // 현재 경기가 내가 본 마지막 경기시각보다 늦게 시작한다면
                // 해당 경기를 본다.
                if (lastMatchEndTime <= matches[i][0]) {
                    cnt++;
                    lastMatchEndTime = matches[i][1];
                }
            }
            // 답안 작성
            sb.append("Scenario #").append(testCase + 1).append(":\n");
            sb.append(cnt).append("\n");
            if (testCase != n - 1)
                sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}