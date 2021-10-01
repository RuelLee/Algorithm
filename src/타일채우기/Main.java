/*
 Author : Ruel
 Problem : Baekjoon 2718번 타일 채우기
 Problem address : https://www.acmicpc.net/problem/2718
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 타일채우기;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 기존에 2 * n 형식을 따르는 타일에 대한 문제는 푼 적이 있으나
        // 이번에는 4 * n의 형태라 생각을 하는 시간이 소요되었다.
        // DP로 풀되, 상태를 나눠서 생각해야했다
        // 타일을 채울 때, 현재 모든 타일을 가득 채운 경우
        // 가로 타일을 선택해서, 다음 순서에서 선택할 타일이 제한되는 경우
        // 0 -> 가득 차는 경우, 1 -> 위에 2개가 가로 타일인 경우, 2 -> 맨 위와 맨 아래가 가로 타일인 경우, 3 -> 아래 2개가 가로 타일인 경우
        // 1  ─     2  ─    3   |       0   |                  ─
        //    ─        |         |           |                  ─
        //    |         |        ─          |  or 이전 타일이    ─  이어서 모두 선택된 경우.
        //    |         ─       ─          |                   ─
        // 이 네가지 경우에 대해 고려하고 DP를 채워나가면 된다.
        Scanner sc = new Scanner(System.in);

        int testCase = sc.nextInt();
        int[][] dp = new int[20][4];
        dp[0][0] = dp[1][0] = dp[1][1] = dp[1][2] = dp[1][3] = 1;
        // 0번째의 현재 모두 안 채워진 경우 1가지
        // 1번째는 모두 한가지씩 경우가 있다.

        for (int i = 2; i < dp.length; i++) {
            // 0번으로 모두 채워지는 경우는, i-1의 4가지 경우에 대해서 맞춰서 타일을 놓는 경우 + i-2번째에서 모두 가로 타일을 선택하는 경우이다.
            dp[i][0] += dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2] + dp[i - 1][3] + dp[i - 2][0];
            // 1번으로 위에 두 개의 가로 타일을 놓으려면, i-1의 0번이나, 3번 상태인 경우 가능하다.
            dp[i][1] = dp[i - 1][0] + dp[i - 1][3];
            // 2번으로 위 아래로 가로 타일을 놓기 위해서는, i-1번의 0번 상태거나, i-2번 상태가 2번이어서, i-1번째에 가운데 2줄에 가로 타일을 놓는 경우가 가능하다.
            dp[i][2] = dp[i - 1][0] + dp[i - 2][2];
            // 3번으로 아래 2개의 가로 타일을 놓으려면 i-1번의 상태가 0번이거나 1번인 경우 가능하다.
            dp[i][3] = dp[i - 1][0] + dp[i - 1][1];
        }
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++)
            sb.append(dp[sc.nextInt()][0]).append("\n");
        System.out.println(sb);
    }
}