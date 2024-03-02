/*
 Author : Ruel
 Problem : Baekjoon 5535번 패셔니스타
 Problem address : https://www.acmicpc.net/problem/5535
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5535_패셔니스타;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // d개의 날에 대한 기온와
        // n개의 옷에 대한 정보가 주어진다.
        // 옷은 옷을 입을 수 있는 최소, 최고 기온과 화려함 값으로 주어진다.
        // 연속해서 비슷한 옷을 입지 않기 위해,
        // 연속한 날에 대한 화려함 차이의 합이 크도록 입고자한다.
        // 그렇게 입었을 때, 최대 화려함 차이의 합은?
        //
        // DP 문제
        // dp[날][옷] = 화려함 차이 합의 최대값
        // 으로 정의하고 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // d개의 날, n개의 옷
        int d = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        
        // 기온
        int[] temperatures = new int[d];
        for (int i = 0; i < temperatures.length; i++)
            temperatures[i] = Integer.parseInt(br.readLine());
        
        // 옷 정보
        int[][] clothes = new int[n][];
        for (int i = 0; i < clothes.length; i++)
            clothes[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp[날][옷]
        int[][] dp = new int[d][n];
        // day와 전날과 비교하여 화려함 차이를 더한다.
        // 날짜
        for (int day = 1; day < dp.length; day++) {
            // 어제 입었던 옷
            for (int preCloth = 0; preCloth < dp[day - 1].length; preCloth++) {
                // 어제 입을 수 없는 옷의 경우 건너뜀
                if (temperatures[day - 1] < clothes[preCloth][0] || temperatures[day - 1] > clothes[preCloth][1])
                    continue;
                
                // 오늘 입을 옷
                for (int curCloth = 0; curCloth < dp[day].length; curCloth++) {
                    // 오늘 입을 수 있는 옷인 경우
                    // 어제 preCloth를 입고, 오늘 curCloth를 입었을 때
                    // 화려함 차이 합을 구하고, 그 값이 최대값을 갱신하는지 확인
                    if (temperatures[day] >= clothes[curCloth][0] && temperatures[day] <= clothes[curCloth][1])
                        dp[day][curCloth] = Math.max(dp[day][curCloth],
                                dp[day - 1][preCloth] + Math.abs(clothes[preCloth][2] - clothes[curCloth][2]));
                }
            }
        }

        // d날까지 만들 수 있는 최대 화려함 차이 합을 출력한다.
        System.out.println(Arrays.stream(dp[d - 1]).max().getAsInt());
    }
}