/*
 Author : Ruel
 Problem : Baekjoon 12019번 동아리방 청소!
 Problem address : https://www.acmicpc.net/problem/12019
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12019_동아리방청소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int n, m;

    public static void main(String[] args) throws IOException {
        // 동아리방에 n일 동안 하루 최대 20명이 이용한다.
        // x명의 사람이 하루 동안 지내면, 더러움은 x만큼 누적되고,
        // 더러움 총합 * 일일 이용자 = 그 날의 불쾌감이 된다.
        // 이용자들이 모두 나가는 밤이 되면, 청소하여 더러움을 0으로 만들 수 있다.
        // n일 중 m일만 청소하여, 불쾌감 총합을 최소화하고자 할 때
        // 불쾌감 총합과, 청소해야하는 날을 구하라
        //
        // DP 문제
        // dp[날짜][현재까지청소한횟수][더러움합] = 불쾌감 총합
        // 으로 dp를 세우고 dp를 풀어주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n일 동안 m번 청소한다.
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 이용객의 수
        int[] users = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < users.length - 1; i++)
            users[i] = Integer.parseInt(st.nextToken());
        
        // dp[날짜][청소한횟수][더러움합] = 불쾌감 합
        int[][][] dp = new int[n + 1][m + 1][20 * n + 1];
        for (int[][] day : dp) {
            for (int[] clean : day)
                Arrays.fill(clean, Integer.MAX_VALUE);
        }
        // 첫날은 0
        dp[0][0][0] = 0;

        // 마지막으로 청소한 날을 역추적한다.
        int[][][] lastCleanedDay = new int[n + 1][m + 1][20 * n + 1];
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    if (dp[i][j][k] == Integer.MAX_VALUE)
                        continue;

                    // 청소하지 않는 경우
                    if (dp[i + 1][j][k + users[i]] > dp[i][j][k] + (k + users[i]) * users[i + 1]) {
                        dp[i + 1][j][k + users[i]] = dp[i][j][k] + (k + users[i]) * users[i + 1];
                        // 마지막 청소한 날을 그대로 전달.
                        lastCleanedDay[i + 1][j][k + users[i]] = lastCleanedDay[i][j][k];
                    }
                    
                    // 청소한 경우
                    if (j < m && dp[i + 1][j + 1][0] > dp[i][j][k]) {
                        dp[i + 1][j + 1][0] = dp[i][j][k];
                        // (i, j, k)를 마지막으로 청소한 정보로 전달.
                        lastCleanedDay[i + 1][j + 1][0] = stateToIdx(i, j, k);
                    }
                }
            }
        }

        // n일 중 m일 청소를 시행했을 때, 가장 불쾌감이 적은 경우의 더러움.
        int minDirtyUser = 0;
        for (int i = dp[n][m].length - 1; i >= 0; i--) {
            if (dp[n][m][minDirtyUser] > dp[n][m][i])
                minDirtyUser = i;
        }

        // lastCleanedDay[n][m][minDirtyUser]를 역추적하여
        // 청소한 날들을 찾는다.
        Stack<Integer> stack = new Stack<>();
        stack.push(lastCleanedDay[n][m][minDirtyUser]);
        while (lastCleanedDay[stack.peek() / (20 * n + 1) / (m + 1)][(stack.peek() / (20 * n + 1)) % (m + 1)][stack.peek() % (20 * n + 1)] != 0)
            stack.push(lastCleanedDay[stack.peek() / (20 * n + 1) / (m + 1)][(stack.peek() / (20 * n + 1)) % (m + 1)][stack.peek() % (20 * n + 1)]);

        StringBuilder sb = new StringBuilder();
        // 불쾌감 총합의 최소값
        sb.append(dp[n][m][minDirtyUser]).append("\n");
        // 청소한 날들
        while (!stack.isEmpty())
            sb.append(stack.pop() / (20 * n + 1) / (m + 1) + 1).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력
        System.out.println(sb);
    }

    static int stateToIdx(int day, int clean, int user) {
        return (day * (m + 1) + clean) * (20 * n + 1) + user;
    }
}