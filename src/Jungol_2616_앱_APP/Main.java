/*
 Author : Ruel
 Problem : Jungol 2616번 앱(APP)
 Problem address : https://jungol.co.kr/problem/2616
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2616_앱_APP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 앱이 열려있고, 차지하는 메모리가 주어진다.
        // 새로 m만큼의 메모리를 확보해 새로운 앱을 열고자 한다.
        // 그리고 나서, 다시 닫힌 앱을 연다. 이 때의 필요한 재활성화 시간이 각각 주어진다.
        // 최소한의 재활성화 시간으로 m이상의 메모리를 확보하는 경우, 해당 재활성화 시간을 구하라
        //
        // 배낭 문제
        // n이 100이하, c가 100이하로 주어지므로, 모든 c의 합이 최대 1만이다.
        // 따라서 c에 따른 확보 가능한 최대 메모리를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 앱, 확보해야하는 메모리 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각각이 차지하는 메모리
        int[] memories = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            memories[i] = Integer.parseInt(st.nextToken());

        // 각각의 재활성화 시간
        int[] costs = new int[n];
        st = new StringTokenizer(br.readLine());
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += (costs[i] = Integer.parseInt(st.nextToken()));

        // 재활성화 시간마다 얻을 수 있는 최대 메모리
        int[] dp = new int[sum + 1];
        // 배낭
        for (int i = 0; i < n; i++) {
            for (int j = sum - costs[i]; j >= 0; j--)
                dp[j + costs[i]] = Math.max(dp[j + costs[i]], dp[j] + memories[i]);
        }

        // m이상의 메모리를 확보 가능한 최소 재활성화 시간을 찾는다.
        int ans = -1;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] >= m) {
                ans = i;
                break;
            }
        }
        // 답 출력
        System.out.println(ans);
    }
}