/*
 Author : Ruel
 Problem : Baekjoon 30621번 어? 금지
 Problem address : https://www.acmicpc.net/problem/30621
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30621_어금지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 대회 도중 '어?'라는 말이 들리면 마음이 혼란해진다.
        // 적당한 선을 지키며 대회장에 최대한 큰 혼란을 주고자한다.
        // 어?를 외칠 수 있는 n개의 시간이 주어진다. ti
        // ti에 어?를 외치려면 (ti - bi)부터 지금까지 어?를 외친 적이 없어야한다.
        // ti 시간에 어?를 외치면 ci만큼의 혼란이 가해진다.
        // 대회장에 줄 수 있는 최대 혼란의 값은?
        //
        // dp, 이분탐색 문제
        // n이 최대 10만으로 주어지므로 dp로 풀되
        // 매번 이전 시간을 찾아야하므로 이를 이분탐색을 통해 빠르게 찾는다.
        // dp[i] = 0 ~ ti까지의 최대 혼란도

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 어?를 외칠수 있는 시간과
        // 이전 어? 와의 최소 간격 그리고 가해지는 혼란도.
        int n = Integer.parseInt(br.readLine());
        int[] ts = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] bs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] cs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 혼란도도 최대 10억으로 주어지므로 long타입으로 계산
        long[] dp = new long[n];
        // 첫번째 어?는 c[0]만큼
        dp[0] = cs[0];
        for (int i = 1; i < n; i++) {
            // i번째 dp는 i-1번째 dp값을 초기값으로 갖고 시작한다.
            dp[i] = dp[i - 1];
            // 이분탐색을 통해
            // ti - bi 값 중 가장 큰 시각의 순서를 찾는다.
            int start = 0;
            int end = i - 1;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (ts[mid] >= ts[i] - bs[i])
                    end = mid - 1;
                else
                    start = mid + 1;
            }
            // 찾은 순서 end

            // 만약 t1보다 이른 시간이라면
            // i번째 어?를 외치려면 이전에 어?를 외쳐선 안된다.
            // dp[i]값과 ci값 중 더 큰 값을 찾는다.
            if (end < 0)
                dp[i] = Math.max(dp[i], cs[i]);
            else        // 그 외의 경우, 현재 dp[i]값과 dp[end]에서 이번에 어?를 외친 ci값을 더한 값을 비교한다.
                dp[i] = Math.max(dp[i], dp[end] + cs[i]);
        }
        // 마지막 n번째 어? 까지 살펴봤을 때,
        // 최대 혼란도 값을 출력한다.
        System.out.println(dp[n - 1]);
    }
}