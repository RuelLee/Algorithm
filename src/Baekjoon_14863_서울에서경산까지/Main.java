/*
 Author : Ruel
 Problem : Baekjoon 14863번 서울에서 경산까지
 Problem address : https://www.acmicpc.net/problem/14863
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14863_서울에서경산까지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 구간을 자전거 혹은 도보를 통해 이동한다.
        // 각 이동 수단에 따라 이동 시간과 모금액이 달라진다.
        // 전체 이동 시간을 k 이내로 이동하며 모금액을 최대로 하고자 할 때, 그 금액은?
        //
        // DP 문제
        // DP를 통해 각 구간에서 이동 시간에 따른 최대 모금액을 계산해나간다.
        // 마지막 구간에서 시간 내로 도달한 경우들 중 최대 모금액을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 구간
        int n = Integer.parseInt(st.nextToken());
        // 시간 제한 k
        int k = Integer.parseInt(st.nextToken());

        // 구간에 따른 도보, 자전거 시 소요 시간과 모금액
        int[][] sections = new int[n][];
        for (int i = 0; i < sections.length; i++)
            sections[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // DP 초기화
        int[][] dp = new int[n + 1][k + 1];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        dp[0][0] = 0;

        // 각 구간을 살펴보며
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 해당 값으로 도달하는 경우가 없는 경우, 건너뛰기
                if (dp[i][j] == -1)
                    continue;

                // i 구간을 도보로 이동
                // 시간이 k를 초과하지 않는다면, 모금액이 최대값을 갱신하는지 확인.
                if (j + sections[i][0] <= k)
                    dp[i + 1][j + sections[i][0]] = Math.max(dp[i + 1][j + sections[i][0]], dp[i][j] + sections[i][1]);
                // 자전거로 이동하는 경우.
                if (j + sections[i][2] <= k)
                    dp[i + 1][j + sections[i][2]] = Math.max(dp[i + 1][j + sections[i][2]], dp[i][j] + sections[i][3]);
            }
        }

        // 시간 내에 도착점에 도달한 경우들 중
        // 최대 모금액을 출력한다.
        System.out.println(Arrays.stream(dp[n]).max().getAsInt());
    }
}