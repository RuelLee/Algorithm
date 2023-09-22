/*
 Author : Ruel
 Problem : Baekjoon 28017번 게임을 클리어하자
 Problem address : https://www.acmicpc.net/problem/28017
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28017_게임을클리어하자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 게임을 n회차 반복한다.
        // 각 게임을 시작할 때는 m개의 무기를 고를 수 있으며, 각 무기마다 클리어타임이 다르다.
        // 이전 회차에 사용한 무기는 사용하지 않는다고 했을 때
        // n회차를 모두 마치는 최소 시간은 얼마인가?
        //
        // DP 문제
        // 이전 게임 결과가 총 클리어 타임에 영향을 미치므로 dp로 풀 수 있다.
        // 단 이전 회차에 선택한 무기만 사용할 수 없기 때문에
        // m개의 무기에 대해 모두 계산할 필요없이
        // 가장 빠를 때의 무기와 클리어타임
        // 그리고 두번째로 빠를 때의 무기와 클리어 타임을 기록한다.
        // 첫번째 클리어타임을 갖는 무기를 중복 선택해서는 안되기 때문에 이전에 선택했던 무기 한정으로
        // 두번째 빠른 클리어타임을 사용한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n회차 게임을 반복하며, m종류의 무기를 고를 수 있다.
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 클리어 무기와 타임
        int[][][] dp = new int[2][2][2];
        for (int[][] ranks : dp) {
            for (int[] state : ranks)
                Arrays.fill(state, Integer.MAX_VALUE);
        }
        
        // 1회차
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            int time = Integer.parseInt(st.nextToken());
            if (time < dp[0][0][1]) {
                dp[0][1] = dp[0][0];
                dp[0][0] = new int[]{i, time};
            } else if (time < dp[0][1][1]) {
                dp[0][1][0] = i;
                dp[0][1][1] = time;
            }
        }
        
        // 2회차 ~ n회차
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // j번 무기를 골랐을 때
            for (int j = 0; j < m; j++) {
                // 이번 클리어타임
                int time = Integer.parseInt(st.nextToken());
                
                // 누적 클리어타임 계산
                // 최단 기록이 j번 무기라면 두번째 클리어타임을, 그렇지 않다면 최단 클리어타임에
                // 현재 클리어타임을 더해준다.
                int timeSum = time + (j == dp[(i + 1) % 2][0][0] ? dp[(i + 1) % 2][1][1] : dp[(i + 1) % 2][0][1]);
                // 누적 결과가 최단 클리어타임이라면
                if (dp[i % 2][0][1] > timeSum) {
                    dp[i % 2][1] = dp[i % 2][0];
                    dp[i % 2][0] = new int[]{j, timeSum};
                } else if (dp[i % 2][1][1] > timeSum) {
                    /// 두번째 클리어 타임이라면
                    dp[i % 2][1][1] = timeSum;
                    dp[i % 2][1][0] = j;
                }
            }
            // 다음 계산에 이전 결과가 남아있으면 안되므로
            // 큰 값으로 초기화
            for (int[] rank : dp[(i + 1) % 2])
                Arrays.fill(rank, Integer.MAX_VALUE);
        }

        // n회차 반복했을 때의 최단 클리어타임을 출력한다.
        System.out.println(dp[(n + 1) % 2][0][1]);
    }
}