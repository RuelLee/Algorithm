/*
 Author : Ruel
 Problem : Baekjoon 10476번 좁은 미술전시관
 Problem address : https://www.acmicpc.net/problem/10476
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10476_좁은미술전시관;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2 * n 크기의 미술전시관이 주어지고, 각 방에는 방문자들에게 공개 가능한 가치가 주어진다.
        // 이 중 k개의 방을 폐쇄하려고 한다.
        // 방문자들이 입구부터 출구까지 진행할 수 있어야하므로
        // 같은 줄의 두 방을 닫거나, 대각선으로 붙어있는 두 방을 닫아서는 안된다.
        // 위 조건에 해당하는 공개 가능한 최대 가치는?
        //
        // DP 문제
        // dp에는 다음과 같은 정보가 담겨야한다.
        // 1. 현재까지 살펴보면 줄
        // 2. 현재까지 폐쇄한 방의 수
        // 3. 이전 줄에 폐쇄한 방의 종류
        // 따라서 3차원 배열로
        // dp[줄][폐쇄한 방의 수][이전 줄에 폐쇄한 방] = 공개 가능한 최대가치
        // 위와 같이 설정하고 계산하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 줄, 폐쇄해야하는 k개의 방
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 방의 가치
        int[][] exhibition = new int[n][];
        for (int i = 0; i < n; i++)
            exhibition[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        int[][][] dp = new int[n][k + 1][3];
        // dp[a][b][c]
        // a 줄수, b 폐쇄한 방의 수, c 폐쇄한 방의 종류(0 -> 왼쪽, 1 -> 오른쪽, 2 -> 폐쇄하지 않음)
        // 첫 줄에서 폐쇄를 하지 않을 경우, 두 방의 가치의 합
        dp[0][0][2] = exhibition[0][0] + exhibition[0][1];
        // 폐쇄하는 방의 수가 양수인 경우에만
        // 한 쪽을 폐쇄했을 때의 값
        if (k > 0) {
            dp[0][1][0] = exhibition[0][1];
            dp[0][1][1] = exhibition[0][0];
        }
        
        // 두번째 줄(i = 1)부터 반복문을 통해 계산
        for (int i = 1; i < dp.length; i++) {
            // 현재 줄에 속한 두 방의 가치 합
            int rowSum = exhibition[i][0] + exhibition[i][1];

            // 이전까지 폐쇄하지 않았고, 이번에도 하지 않는 경우
            dp[i][0][2] = dp[i - 1][0][2] + rowSum;
            // j개의 방을 폐쇄하는 경우
            // j가 k를 넘어서는 안되고,
            // 현재 지나온 줄의 수보다 클 수도 없다.
            for (int j = 1; j < Math.min(i + 2, k + 1); j++) {
                // i번째 줄의 계산을 하는데, i+1개의 방을 폐쇄하는 경우에는
                // 지나온 모든 방을 폐쇄하고, 이번 방도 폐쇄해야하는 경우이다.
                // 이번 줄에서 두 방 모두 폐쇄하지 않는 경우는 없다.
                // 따라서 이번 줄의 두 방을 모두 살리는 경우는, j가 i + 1보다 작은 경우에서만이다.
                if (j < i + 1)
                    dp[i][j][2] = Math.max(dp[i - 1][j][0], Math.max(dp[i - 1][j][1], dp[i - 1][j][2])) + rowSum;
                // 왼쪽 방을 폐쇄하는 경우
                dp[i][j][0] = Math.max(dp[i - 1][j - 1][0], dp[i - 1][j - 1][2]) + exhibition[i][1];
                // 오른쪽 방을 폐쇄하는 경우.
                dp[i][j][1] = Math.max(dp[i - 1][j - 1][1], dp[i - 1][j - 1][2]) + exhibition[i][0];
            }
        }

        // n줄의 방을 모두 살펴보고, k개의 방을 폐쇄했을 때
        // 공개 가능한 최대 가치의 합을 출력한다.
        System.out.println(Arrays.stream(dp[n - 1][k]).max().getAsInt());
    }
}