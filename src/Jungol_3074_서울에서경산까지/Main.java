/*
 Author : Ruel
 Problem : Jungol 3074번 서울에서 경산까지
 Problem address : https://jungol.co.kr/problem/3074
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3074_서울에서경산까지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 구간이 주어지고 각 구간마다 도보 혹은 자전거로 이동한다.
        // 각 구간 별 이동 수단에 대해 이동 시간과 모금액이 주어진다.
        // k분 이내에 모든 구간을 이동하며, 최대 모금액을 구하고자 할 때
        // 그 금액은?
        //
        // DP 문제
        // dp[이동구간][시간] = 최대 모금액
        // 으로 정하고 값을 계산한다.
        // 단 dp는 이전 값만을 참고하므로, 2개의 공간에 번갈아가며 사용하면 메모리를 아낄 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 구간, 최대 시간 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 두 개의 dp를 왔다갔다 거리며 계산한다.
        int[][] dp = new int[2][k + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 도보로 이동 할 때의 시간과 보금액
            int walkT = Integer.parseInt(st.nextToken());
            int walkM = Integer.parseInt(st.nextToken());
            // 자전거로 이동할 때의 시간과 모금액
            int bicycleT = Integer.parseInt(st.nextToken());
            int bicycleM = Integer.parseInt(st.nextToken());
            Arrays.fill(dp[(i + 1) % 2], 0);

            // dp 계산
            int next = (i + 1) % 2;
            for (int j = 0; j < k; j++) {
                if (j == 0 && i != 0)
                    continue;

                // 도보로 이동
                if (j + walkT <= k)
                    dp[next][j + walkT] = Math.max(dp[(i + 1) % 2][j + walkT], dp[i % 2][j] + walkM);
                // 자전거로 이동
                if (j + bicycleT <= k)
                    dp[(i + 1) % 2][j + bicycleT] = Math.max(dp[(i + 1) % 2][j + bicycleT], dp[i % 2][j] + bicycleM);
            }
        }

        // 마지막 결과에서 가장 큰 금액을 구해
        int answer = 0;
        for (int i = 0; i < dp[n % 2].length; i++)
            answer = Math.max(answer, dp[n % 2][i]);
        // 출력
        System.out.println(answer);
    }
}