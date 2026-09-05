/*
 Author : Ruel
 Problem : Jungol 5662번 도넛 한 입 (Donut)
 Problem address : https://jungol.co.kr/problem/5662
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5662_도넛한입_Donut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 도넛이 n조각으로 나뉘어져 있고, 이 중 한 입으로 연속한 구간을 먹을 수 있다.
        // 각 조각의 맛이 주어질 때, 얻을 수 있는 최대 맛의 합은?
        //
        // 카데인 알고리즘
        // 원형으로 연결된 경우 사용하는 알고리즘으로, 해당처럼 최적의 구간을 찾고자 할 때
        // 1 ~ n에 대해 연속한 구간의 최댓값과 최솟값을 찾는다.
        // 그 후, 최댓값과 전체 합에서 최솟값을 뺀 경우(= 가장 맛 없는 부분을 도려내고 나머지를 먹는 경우)를 비교한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 조각
        int n = Integer.parseInt(br.readLine());
        int[] donuts = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            donuts[i] = Integer.parseInt(st.nextToken());

        // 카데인 알고리즘
        int[][] dp = new int[n][2];
        dp[0][0] = dp[0][1] = donuts[0];
        // 전체 합
        int sum = donuts[0];
        // 구간 별 최대 최소 누적합
        int max = donuts[0];
        int min = donuts[0];
        for (int i = 1; i < n; i++) {
            sum += donuts[i];
            max = Math.max(max, dp[i][0] = Math.max(dp[i - 1][0], 0) + donuts[i]);
            min = Math.min(min, dp[i][1] = Math.min(dp[i - 1][1], 0) + donuts[i]);
        }

        // max가 0보다 작은 경우, 모든 조각이 음수인 경우.
        // 해당 경우는 sum - min을 할 경우, 0이 되므로 해당 값을 버려야한다.
        // 답 출력
        System.out.println(Math.max(max, max >= 0 ? (sum - min) : Integer.MIN_VALUE));
    }
}