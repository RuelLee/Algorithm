/*
 Author : Ruel
 Problem : Baekjoon 2994번 내한 공연
 Problem address : https://www.acmicpc.net/problem/2994
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2994_내한공연;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 멤버가 길이 t의 콘서트를 진행하는데, 멤버들은 콘서트 중간 중간 쉬어야한다.
        // 각 멤버별로 필요한 휴식 시간이 주어진다.
        // 최대 2명의 멤버까지만 동시에 휴식을 취하며 콘서트를 진행하려할 때
        // 각 멤버들이 쉬기 시작하는 시간을 출력하라.
        //
        // 배낭이 2개인 배낭문제로 풀 수 있다.
        // 최대 2명이 동시에 쉬므로, 필요한 휴식 시간에 크기 t의 2개의 배낭에 멤버들을 잘 담고
        // 휴식을 시작하는 시간을 배분해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int t = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[] rests = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp[시간][0] = 한쪽 배낭에 채워진 멤버들의 총 휴식 시간
        // dp[시간][1] = 해당 값을 참고한 이전 dp의 시간
        // dp[시간][2] = 추가된 멤버의 idx
        int[][] dp = new int[t + 1][3];
        // 모든 멤버에 대해서 체크
        for (int i = 0; i < rests.length; i++) {
            // 시간은 역순으로 살펴본다.
            for (int j = dp.length - 1; j >= 0; j--) {
                // 시간이 i번째 멤버가 쉬기에 부족한 시간이라면 더 이상 살펴보지 않는다.
                if (j - rests[i] < 0)
                    break;
                
                // j - rests[i] 시간에 i번째 멤버에게 휴식 시간을 줬을 때
                // 한 쪽 배낭의 전체 휴게 시간이 커진다면 
                if (dp[j][0] < dp[j - rests[i]][0] + rests[i]) {
                    // 전체 휴게 시간 갱신
                    dp[j][0] = dp[j - rests[i]][0] + rests[i];
                    // dp[j][1]에는 이전 시간 기록.
                    dp[j][1] = j - rests[i];
                    // dp[j][2]에는 추가된 멤버 기록.
                    dp[j][2] = i;
                }
            }
        }

        int[] times = new int[n];
        Arrays.fill(times, -1);
        // t시간부터 한쪽 배낭에 속한 멤버들을 추적하며 휴식 시간을 할당한다.
        int loc = t;
        // 전체 휴게시간이 0이 될 때까지 살펴본다.
        while (dp[loc][0] != 0) {
            // loc 시간에 새로 추가된 멤버는 person
            int person = dp[loc][2];
            // person은 전체 휴게 시간 - person의 휴식 시간부터
            // 휴식을 취한다.
            times[person] = dp[loc][0] - rests[person];
            // 그리고 다음에는 dp[loc][1] 시간을 살펴본다.
            loc = dp[loc][1];
        }

        // 이제 나머지 한쪽 배낭의 멤버들에게 휴게 시간을 할당해야한다.
        // 0 시간 부터 시작.
        int time = 0;
        for (int i = 0; i < times.length; i++) {
            // 초기값이 아니라면 이미 다른 배낭에서 휴식 시작 시간이 설정되었다.
            // 따라서 건너뛰기.
            if (times[i] != -1)
                continue;

            // 그렇지 않다면, 현재 비어있는 시간부터 휴식을 취한다.
            times[i] = time;
            // 다음 사람이 휴식을 취할 수 있는 시간은 time에 현재 사람이 휴식을 취한 뒤인
            // time + rests[i] 시간이다.
            time += rests[i];
        }

        // times를 순서대로 출력한다.
        StringBuilder sb = new StringBuilder();
        for (int ti : times)
            sb.append(ti).append(" ");
        System.out.println(sb);
    }
}