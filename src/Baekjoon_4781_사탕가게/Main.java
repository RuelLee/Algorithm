/*
 Author : Ruel
 Problem : Baekjoon 4781번 사탕 가게
 Problem address : https://www.acmicpc.net/problem/4781
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4781_사탕가게;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 사탕의 종류 n과 돈의 양 m이 주어진다. (1 ≤ n ≤ 5,000, 0.01 ≤ m ≤ 100.00)
        // n개의 사탕의 칼로리 c와 가격 p가 주어진다.(1 ≤ c ≤ 5,000, 0.01 ≤ p ≤ 100.00)
        // m과 p는 항상 소수점 둘째자리 수라고 할 때, 현재 가진 돈으로 가장 많은 칼로리의 사탕들을 구매하고자 한다
        // 이 때 칼로리는?
        //
        // 간단한 DP문제
        // 이지만 rounding error에 대해 생각해봐야하는 문제
        // 돈과 가격이 소수점 둘째자리 수라고 주어지므로, 100을 곱해서 정수로 만들면 DP로 풀 수 있네!
        // 라고 생각하지만 이 실수를 Double 형태로 받게되면, Double형 표현 방식에 따른 오차가 생겨
        // 100을 곱할 경우 우리가 원하는 수가 나오지 않을 수 있다.
        // 따라서 반올림 처리를 하거나, 작은 수를 더해 int형으로 형변환을 해주는 방법을 택해야한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 사탕의 종류
            int n = Integer.parseInt(st.nextToken());
            // 가진 돈의 양
            // Doulbe 형으로 파싱 후, 100을 곱한 값을 반올림 처리해줘서, 값을 보정한다.
            int m = (int) Math.round(Double.parseDouble(st.nextToken()) * 100);
            
            // n이 0이면 종료
            if (n == 0)
                break;

            // 돈의 양으로 DP를 세우고
            int[] dp = new int[m + 1];
            // 사탕마다 계산을 한다.
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                // 칼로리와
                int c = Integer.parseInt(st.nextToken());
                // 가격
                // 마찬가지로 값을 보정
                int p = (int) Math.round(Double.parseDouble(st.nextToken()) * 100);

                // 이미 계산되어있는 j원으로 살 수 있는 사탕의 최대 칼로리가
                // j - p원에서 이번 사탕으로 + c 칼로리를 하는 값이 더 크다면 해당 값으로 변경해준다.
                for (int j = p; j < dp.length; j++) {
                    if (dp[j] < dp[j - p] + c)
                        dp[j] = dp[j - p] + c;
                }
            }
            // 최종적으로 m원으로 살 수 있는 사탕들의 최대 칼로리를 기록한다.
            sb.append(dp[m]).append("\n");
        }
        // 테스트케이스의 답을 모두 출력.
        System.out.print(sb);
    }
}