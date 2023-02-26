/*
 Author : Ruel
 Problem : Baekjoon 5546번 파스타
 Problem address : https://www.acmicpc.net/problem/5546
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5546_파스타;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 10000;

    public static void main(String[] args) throws IOException {
        // n일 동안 파스타를 먹고자한다.
        // 파스타 소스는 토마토, 크림, 바질 3종류가 있으며, 3일 이상 연속해서 같은 소스를 먹지 않으려한다.
        // k개의 날짜에 대해서는 주어진 소스로 파스타를 만들어 먹는다.
        // n일 동안 파스타를 만들어 먹을 수 있는 가짓수는?
        // 가짓수를 10000으로 나눈 나머지를 출력
        //
        // DP문제
        // DP를 통해, dp[날짜][메뉴][연속해서 먹은 일]을 구분하여 계산한다.
        // 정해진 날짜에는 정해진 메뉴만 먹는다는 점을 유의하며 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 전체 날짜 n일과 메뉴가 정해진 k개의 일
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 정해진 메뉴들
        int[] selectedDays = new int[n + 1];
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            selectedDays[Integer.parseInt(st.nextToken())] = Integer.parseInt(st.nextToken());
        }
        
        // DP를 만들고
        int[][][] dp = new int[n + 1][4][3];
        for (int i = 1; i < dp[0].length; i++) {
            // 만약 첫날의 메뉴가 정해져있다면 해당 메뉴가 아닌 것은 건너뛴다.
            if (selectedDays[1] != 0 && selectedDays[1] != i)
                continue;
            // 해당하는 메뉴를 1로 초기화시켜준다.
            dp[1][i][1] = 1;
        }

        // 첫째날부터 다음 날로의 가짓수를 계산한다.
        for (int day = 1; day < dp.length - 1; day++) {
            // 오늘 메뉴를 살펴보며
            for (int menu = 1; menu < dp[day].length; menu++) {
                // 혹시 메뉴가 정해져있는데, 해당 메뉴가 아니라면 건너뛴다.
                if (selectedDays[day] != 0 && menu != selectedDays[day])
                    continue;

                // 연속한 날짜 별로
                for (int continuity = 1; continuity < dp[day][menu].length; continuity++) {
                    // 다음 메뉴를 살펴본다.
                    for (int nextMenu = 1; nextMenu < dp[day + 1].length; nextMenu++) {
                        // 만약 오늘과 다음 메뉴가 같은데, 이미 2일 동안 먹었다면 3일 연속해서 먹지는 않으므로 건너뛴다.
                        if (menu == nextMenu && continuity == 2)
                            continue;

                        // 다음 날에 먹을 메뉴가 오늘 메뉴와 다르다면 dp[day][menu][1]로 연속성을 초기화시켜주고
                        // 같다면 dp[day][menu][continuity + 1]로 연속성을 하나 증가시킨다.
                        dp[day + 1][nextMenu][menu == nextMenu ? continuity + 1 : 1]
                                += dp[day][menu][continuity];
                        // LIMIT으로 모듈러 연산을 해준다.
                        dp[day + 1][nextMenu][menu == nextMenu ? continuity + 1 : 1] %= LIMIT;
                    }
                }
            }
        }

        // 마지막 날에 나올 수 있는 모든 경우의 수를 구한다.
        int sum = 0;
        for (int i = 0; i < dp[n].length; i++) {
            // 마지막 날 메뉴가 정해져있다면 해당 메뉴가 아닌 경우 건너뛴다.
            if (selectedDays[n] != 0 && selectedDays[n] != i)
                continue;
            // 연속성이 어떠하든 해당 하는 메뉴의 모든 경우의 수를 더한다.
            sum += Arrays.stream(dp[n][i]).sum();
        }

        // 마지막으로 모듈러 연산을 취하고 답을 출력한다.
        sum %= LIMIT;
        System.out.println(sum);
    }
}