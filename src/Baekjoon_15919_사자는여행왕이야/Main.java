/*
 Author : Ruel
 Problem : Baekjoon 15919번 사자는 여행왕이야!!
 Problem address : https://www.acmicpc.net/problem/15919
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15919_사자는여행왕이야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n일 동안, 여러 여행을 다니는데, 여행이 아닌 연속된 기간의 최대 길이를 최소화하고자 한다.
        // m개의 여행 구간이 주어졌을때,
        // 여행이 아닌 연속된 기간의 최대 길이는?
        //
        // DP 문제
        // dp[i] = i번째 여행을 갈 때, 이전까지의 여행이 아닌 연속된 최대 기간 길이의 최솟값
        // 으로 정하고 풀면 된다!

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 기간의 길이 n, 여행 구간의 개수 m
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        // m개의 여행 구간
        int[][] trips = new int[m][2];
        for (int i = 0; i < trips.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < trips[i].length; j++)
                trips[i][j] = Integer.parseInt(st.nextToken());
        }
        // 시작일에 따라, 시작일이 같다면 종료일에 따라 오름차순 정렬
        Arrays.sort(trips, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });

        // dp[i] = i번째 여행을 갈 때, 이전까지의 여행이 아닌 연속된 최대 기간 길이의 최솟값
        int[] dp = new int[m];
        // 각 여행을 처음으로 갈 때의 값으로 초기화
        for (int i = 0; i < dp.length; i++)
            dp[i] = trips[i][0] - 1;
        // 전체 여행에서의 여행이 아닌 연속된 최대 기간의 길이의 최솟값
        int answer = Integer.MAX_VALUE;
        
        // i번째 여행을 가기 전에
        for (int i = 0; i < trips.length; i++) {
            // j번째 여행을 다녀올 수 있는지 체크
            for (int j = i - 1; j >= 0; j--) {
                // 만약 여행 기간이 겹친다면 건너뛰고
                if (trips[j][1] >= trips[i][0])
                    continue;

                // 겹치지 않는다면, 그 전에 j번째 여행을 다녀올 수 있다.
                // j번째 여행 후, i번째 여행을 간다한다면
                // j번째까지의 dp값과 i와 j번째 여행 사이의 여행이 아닌 기간의 길이 중 더 큰 값을
                // dp[i] 값이 될 수 있다. 해당 값이 최솟값을 갱신하는지 확인.
                dp[i] = Math.min(dp[i], Math.max(dp[j], trips[i][0] - trips[j][1] - 1));
            }
            // 이후, i번째 여행으로 전체 여행을 마칠 경우의 여행이 아닌 연속된 최대 기간의 길이
            // 해당 값이 최솟값을 갱신하는지 확인.
            answer = Math.min(answer, Math.max(dp[i], n - trips[i][1]));
        }
        // 답 출력
        System.out.println(answer);
    }
}