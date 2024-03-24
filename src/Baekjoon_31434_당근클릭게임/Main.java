/*
 Author : Ruel
 Problem : Baekjoon 31434번 당근 클릭 게임
 Problem address : https://www.acmicpc.net/problem/31434
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31434_당근클릭게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 당근 클릭 게임이 주어진다.
        // 현재 상태는 시간, 스피드, 당근의 개수로 주어진다.
        // 각 턴에 행할 수 있는 행동은
        // 현재 스피드만큼 당근을 얻거나
        // n 종류 중 하나의 스피드 효과를 당근을 사용하여 구매한다.
        // 스피드 효과는 소모 당근, 스피드 증가량으로 주어진다.
        // k초동안 플레이 했을 때, 최대 당근을 몇 개 갖고 있을 수 있는가
        // 처음 스피드는 1이다.
        //
        // DP 문제
        // dp[시간][스피드] = 당근의 개수
        // 로 dp를 세우고 모든 행동에 대해 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 스피드 효과, 주어진 시간 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 스피드 효과
        int[][] actions = new int[n][];
        for (int i = 0; i < actions.length; i++)
            actions[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // dp[초의 최대값][스피드의 최대값]
        int[][] dp = new int[k + 1][k * 50];
        // -1로 초기화
        for (int[] d : dp)
            Arrays.fill(d, -1);
        // 0초일 때, 스피드는 0이고 그 때의 당근 개수 0개
        dp[0][1] = 0;
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // -1이면 불가능한 경우이므로 건너뛴다.
                if (dp[i][j] == -1)
                    continue;
                
                // 스피드만큼 당근을 얻는 방법
                dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + j);
                
                // 스피드 효과를 구매하는 방법
                for (int[] action : actions) {
                    if (dp[i][j] >= action[0])
                        dp[i + 1][j + action[1]] = Math.max(dp[i + 1][j + action[1]], dp[i][j] - action[0]);
                }
            }
        }

        // k초가 되었을 때, 가장 많은 당근의 개수를 출력한다.
        System.out.println(Arrays.stream(dp[k]).max().getAsInt());
    }
}