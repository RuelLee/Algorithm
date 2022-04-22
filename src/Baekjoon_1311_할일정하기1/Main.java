/*
 Author : Ruel
 Problem : Baekjoon 1311번 할 일 정하기 1
 Problem address : https://www.acmicpc.net/problem/1311
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1311_할일정하기1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] costs;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        // n명의 사람과 n개의 일이 있다
        // 사람은 모든 일을 할 수 있지만, 일 마다 처리하는데 드는 비용은 사람마다 모두 다르다고 한다
        // 모든 일을 처리하는데 드는 최소 비용은 얼마인가?
        //
        // DP를 활용한 문제
        // dp의 행은 현재 일을 맡을 사람, 열은 bitmask로 하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        costs = new int[n][];
        for (int i = 0; i < n; i++)
            costs[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        dp = new int[n][(int) Math.pow(2, n)];
        System.out.println(dfs(0, 0));
    }

    static int dfs(int person, int bitmask) {
        if (person == costs.length)     // 0번부터 할당했으므로, n에 도달한 시점엔 모든 일의 할당이 끝난 시점. 0을 리턴.
            return 0;

        if (dp[person][bitmask] != 0)       // 이미 계산한 결과가 있다면 바로 참고.
            return dp[person][bitmask];

        // 없다면, Integer.MAX_VALUE 값으로 초기화시켜주자.
        dp[person][bitmask] = Integer.MAX_VALUE;
        // bitmask를 통해 아직 할당되지 않은 일을 person에게 할당했을 때, 최소 비용을 계산하자.
        for (int i = 0; i < costs.length; i++) {
            if ((bitmask & 1 << i) != 0)        // i번째 일이 다른 사람에게 할당이 되었다면, 건너뛰기.
                continue;

            // 그렇지 않다면, i일을 person에게 할당했을 때의 비용이, 현재 bitmask의 비용을 갱신하는지 알아보고, 그렇다면 값 대체.
            // i일을 person에게 할당했을 때 비용은 person이 i일을 맡는 비용과 costs[person][i]과 person 이후의 사람들이 일을 최소 비용으로 맡았을 때 비용의 합이다.
            dp[person][bitmask] = Math.min(dp[person][bitmask], costs[person][i] + dfs(person + 1, bitmask | (1 << i)));
        }
        // 해당 최소 값을 리턴.
        return dp[person][bitmask];
    }
}