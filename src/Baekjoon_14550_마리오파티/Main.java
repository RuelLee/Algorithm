/*
 Author : Ruel
 Problem : Baekjoon 14550번 마리오 파티
 Problem address : https://www.acmicpc.net/problem/14550
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14550_마리오파티;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 일렬로 된 보드가 있다.
        // 시작점과 도착점이 주어지며, 사이의 발판들에 대해서는 숫자가 적혀있다.
        // 1 ~ s까지 적힌 주사위를 굴려, t턴 이내에 시작점에서 도착점으로 이동하고자 한다.
        // 이동시에 밟는 발판들에 대해, 적힌 수만큼 점수를 얻거나 잃는다.
        // 꼭 도착점에 도달할 필요는 없이, 도착점을 넘어서도 도착한 걸로 본다.
        // 시작점에서 도착점까지 이동하는데 얻을 수 있는 최대 점수는?
        //
        // DP 문제
        // dp[현재위치][턴수] = 최대 점수
        // 로 dp를 세우고 풀어나가며 문제를 해결한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        StringBuilder sb = new StringBuilder();
        while (!input.equals("0")) {
            StringTokenizer st = new StringTokenizer(input);
            // 시작점과 도착점 사이에 n개의 발판이 있다.
            int n = Integer.parseInt(st.nextToken());
            // 1 ~ s까지 적힌 주사위
            int s = Integer.parseInt(st.nextToken());
            // t턴 동안 진행한다.
            int t = Integer.parseInt(st.nextToken());
            
            // 발판 입력 처리
            int[] incomes = new int[n + 2];
            int idx = 1;
            while (idx < incomes.length - 1) {
                st = new StringTokenizer(br.readLine());
                while (st.hasMoreTokens())
                    incomes[idx++] = Integer.parseInt(st.nextToken());
            }

            // dp[현재위치][턴] = 최대 점수
            // 시작점과 도착점을 위해 n+2 크기로 선언.
            int[][] dp = new int[n + 2][t + 1];
            for (int[] d : dp)
                Arrays.fill(d, Integer.MIN_VALUE);
            // 출발점에서는 점수가 0
            dp[0][0] = 0;
            
            // i위치
            for (int i = 0; i < n + 1; i++) {
                // turn 턴에 얻을 수 있는 최대 점수는 dp[i][turn]
                for (int turn = 0; turn < t; turn++) {
                    // 만약 불가능한 경우라면 건너뛴다.
                    if (dp[i][turn] == Integer.MIN_VALUE)
                        continue;

                    // 에서 주사위를 굴려 1 ~ s까지의 경우를 따져본다.
                    for (int j = 1; j <= s; j++) {
                        // 만약 도착점을 벗어나는 경우,
                        // 얻는 점수는 없고, 도착한 걸로 친다.
                        if (i + j > n + 1)
                            dp[n + 1][turn + 1] = Math.max(dp[n + 1][turn + 1], dp[i][turn]);
                        else        // 그 외의 경우, i+j 지점에서 (i+j)번째 발판을 밟아 얻는 점수 고려
                            dp[i + j][turn + 1] = Math.max(dp[i + j][turn + 1], dp[i][turn] + incomes[i + j]);
                    }
                }
            }
            
            // 최종 위치 n+1 위치에서
            // 턴 수 상관없이 가장 높은 점수를 찾는다.
            // 정확히 t턴에 도착이 아니라 t턴 이내에 도착하면 되기 때문
            int answer = Integer.MIN_VALUE;
            for (int i = 0; i < dp[n + 1].length; i++)
                answer = Math.max(answer, dp[n + 1][i]);
            // 답 기록
            sb.append(answer).append("\n");
            
            // 다음 테스트 케이스 입력
            input = br.readLine();
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}