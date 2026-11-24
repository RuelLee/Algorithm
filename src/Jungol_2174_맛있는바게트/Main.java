/*
 Author : Ruel
 Problem : Jungol 2174번 맛있는 바게트
 Problem address : https://jungol.co.kr/problem/2174
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2174_맛있는바게트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // l길이의 바게트가 주어지고
        // n개의 구간에 대해 해당 구간을 먹을 경우 얻을 수 있는 맛의 값이 주어진다.
        // 0 ~ 2, 2 ~ 4의 구간은 동시에 있으나 0 ~ 3, 2 ~ 4 구간은 동시에 먹을 수 없다.
        // 원하는 구간을 골라내어 먹을 때, 얻을 수 있는 최대 맛의 값 합은?
        //
        // DP 문제
        // dp[i] = i번 위치까지 먹었을 때, 얻을 수 있는 최대 맛의 합이라고 정의하자.
        // 먼저, n개의 구간을 시작 위치에 따라 오름차순 정렬한다.
        // 구간의 시작보다 앞부분에 얻을 수 있는 최대 맛의 합에 현재 구간의 합을 더하여 구간의 마지막 위치에 기록하는 걸 반복한다.
        // 그러며 얻을 수 있는 최대 맛의 합을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // l 길이의 바게트 빵, n개의 구간
        int l = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[][] ranges = new int[n][3];
        for (int i = 0; i < ranges.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < ranges[i].length; j++)
                ranges[i][j] = Integer.parseInt(st.nextToken());
            // 시작 점의 기준과 끝 점의 기준이 다르다.
            // 시작 점이 a일 경우, a ~ b직전까지 포함하지만
            // 끝 점이 a일 경우, a -1 ~ a까지 포함하는 형태다.
            // 따라서 a가 아닌 a-1로 기록
            ranges[i][1]--;
        }
        Arrays.sort(ranges, Comparator.comparingInt(o -> o[0]));

        // idx로 시작점 이전의 최대값을 시작점까지 전파
        int idx = 0;
        // dp[i] = i번 위치까지 먹었을 때, 얻을 수 있는 최대 맛의 합
        int[] dp = new int[l + 1];
        int ans = 0;
        for (int i = 0; i < ranges.length; i++) {
            // i번째 구간의 시작점 전까지 얻을 수 있는 최대 맛의 합을 전파
            while (idx < ranges[i][0]) {
                dp[idx + 1] = Math.max(dp[idx + 1], dp[idx]);
                idx++;
            }

            // 직전까지의 최대합 + 해당 구간의 값이
            // 끝 점에서 얻을 수 있는 맛의 최대 합인지 비교
            dp[ranges[i][1]] = Math.max(dp[ranges[i][1]], (ranges[i][0] - 1 < 0 ? 0 : dp[ranges[i][0] - 1]) + ranges[i][2]);
            // 최댓값인지 비교
            ans = Math.max(ans, dp[ranges[i][1]]);
        }
        // 얻을 수 있는 맛의 합 최댓값 출력
        System.out.println(ans);
    }
}