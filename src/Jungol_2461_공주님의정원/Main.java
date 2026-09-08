/*
 Author : Ruel
 Problem : Jungol 2461번 공주님의 정원
 Problem address : https://jungol.co.kr/problem/2461
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2461_공주님의정원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] dayPsums = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    static int start = monthDayToInt(3, 1);
    static int end = monthDayToInt(11, 30);

    public static void main(String[] args) throws IOException {
        // n종류의 꽃들이 주어지고 피는 날과 지는 날이 주어진다.
        // 공주가 좋아하는 구간인 3월 1일부터 11월 30일까지는 매일 한 종류 이상의 꽃이 피어있도록 한다.
        // 가장 적은 수의 꽃 종류를 심는다고 할 때, 필요한 꽃 종류의 수는?
        //
        // 정렬, 그리디, dp 문제
        // dp[i] = 3월 1일부터 i일까지 매일 꽃을 피어있게 하는데 필요한 최소 꽃 종류의 수
        // 꽃들을 피는 시점에 따라 오름차순, 지는 날에 따라 내림차순 정렬하여 순서대로 살펴보며
        // dp를 채워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n 종류의 꽃
        int n = Integer.parseInt(br.readLine());
        // 피는 시기와 지는 시기
        int[][] flowers = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                flowers[i][j] = monthDayToInt(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        // 피는 시기에 따라 오름차순, 지는 시기에 따라 내림차순 정렬
        Arrays.sort(flowers, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o2[1], o1[1]);
            return Integer.compare(o1[0], o2[0]);
        });

        // dp[i] = 3월 1일부터 i일까지 매일 꽃을 피어있게 하는데 필요한 최소 꽃 종류의 수
        int[] dp = new int[366];
        // 3월 1일 전까지는 피어있지 않아도 상관없다.
        // 3월 1일 이후부터 큰 값으로 초기화
        Arrays.fill(dp, start, dp.length, Integer.MAX_VALUE);
        // 지는 시기가 3월 1일 이전이라면 건너뜀.
        int i = 0;
        while (i < n && flowers[i][1] < start)
            i++;

        // 순서대로 꽃을 살펴봄
        for (; i < n; i++) {
            // 만약 i번째 꽃이 피기 1일 전에 모든 꽃이 져있다면,
            // i번째 꽃을 심더라도 연속된 구간이 되지 않기 때문에 의미가 없다.
            // 반복문 종료
            if (dp[flowers[i][0] - 1] == Integer.MAX_VALUE)
                break;

            // 필요한 꽃의 종류
            // 피는 일 직전 날까지 필요한 종류의 수 + 1
            int num = dp[flowers[i][0] - 1] + 1;
            // 꽃들이 지는 날에 대해 내림차순으로 정렬되어있으므로,
            // 뒤에서부터 채워나가며, 이미 같거나 작은 같은 값이 나올 때까지만 채운다.
            for (int j = flowers[i][1] - 1; j >= flowers[i][0]; j--) {
                if (dp[j] <= num)
                    break;
                dp[j] = Math.min(dp[j], num);
            }
        }
        // 11월 30일까지 필요한 꽃 종류의 수를 출력한다.
        // 불가능한 경우 0 출력
        System.out.println(dp[end] == Integer.MAX_VALUE ? 0 : dp[end]);
    }

    // 월 일을 받아 일로 정리한다.
    static int monthDayToInt(int month, int day) {
        return dayPsums[month - 1] + day;
    }
}