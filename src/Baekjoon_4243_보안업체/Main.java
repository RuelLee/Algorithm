/*
 Author : Ruel
 Problem : Baekjoon 4243번 보안 업체
 Problem address : https://www.acmicpc.net/problem/4243
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4243_보안업체;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 일직선 위에 n개의 회사가 위치한다.
        // 각 회사의 위치와 처음 시작 위치가 주어진다.
        // 시작 시점부터, 모든 회사를 순회하는데, 각 회사가 기다린 시간의 합이 최소가 되게끔 하고자 한다.
        // 이 때, 최소 대기 시간의 합은?
        //
        // DP 문제
        // DP라는 느낌은 오지만, 무엇을 기준으로 탐색해야하는지가 쉬이 떠오르지는 않는다.
        // 각 방문을 할 때, 현재까지의 시각을 통해 대기 시간을 기준으로 할지, 현재까지 누적된 대기 시간을 기준으로 할지말이다
        // 하지만 둘 다 아니다.
        // 처음 위치에서 왼쪽이든 오른쪽이든, 다음 회사를 방문하면
        // 해당 거리만큼의 대기 시간이 필요하다. 
        // 하지만 조금 더 생각해보면 현재 방문하는 회사의 대기 시간 뿐만 아니라
        // 미방문한 나머지 회사들의 대기 시간도 전체적으로 해당 대기 시간만큼씩 증가한다.
        // 따라서 각 회사를 방문할 때 마다, 현재 소요 시간 + 미방문 회사의 수 * 현재 소요 시간을 더해주면
        // 해당 회사를 방문함으로써 발생한 대기 시간 뿐만 아니라 미방문한 회사들에 증가한 대기 시간까지 고려할 수 있다.
        // 물론 해당 값이 답이 된다.
        // dp는 dp[left][right][현재 위치] = 대기 시간의 누적 합
        // 으로 세우고 채운다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 회사 간의 거리를 누적합으로 처리하자.
        long[] psums = new long[101];
        // dp[left][right][위치] = 대기 시간 누적 합
        long[][][] dp = new long[101][101][2];
        for (int testCase = 0; testCase < t; testCase++) {
            // dp 초기화
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < dp[i].length; j++)
                    Arrays.fill(dp[i][j], Long.MAX_VALUE);
            }

            // n개의 회사, 현재 위치 a
            int n = Integer.parseInt(br.readLine());
            int a = Integer.parseInt(br.readLine());
            // 초기 위치 값 초기화
            for (int i = 0; i < dp[a][a].length; i++)
                Arrays.fill(dp[a][a], 0);
            
            // 회사 간의 거리. 누적합 처리.
            for (int i = 2; i <= n; i++)
                psums[i] = psums[i - 1] + Integer.parseInt(br.readLine());
            
            // dp 탐색을 할 때, left와 right의 거리가 0일 때부터, n-1까지
            for (int length = 0; length < n; length++) {
                // left가 1인 지점부터 모두 탐색
                for (int left = 1; left + length <= n; left++) {
                    // 그 때의 right
                    int right = left + length;

                    // 현재 위치가 left인지, right인지
                    for (int loc = 0; loc < 2; loc++) {
                        // 초기값인 경우 건너뜀.
                        if (dp[left][right][loc] == Long.MAX_VALUE)
                            continue;
                        
                        // 왼쪽으로 갈 수 있는 경우
                        if (left > 1) {
                            // left-1까지의 거리
                            long distance = psums[loc == 1 ? right : left] - psums[left - 1];
                            // 대기 시간 누적합이 최소값을 갱신하는지 확인.
                            dp[left - 1][right][0] = Math.min(dp[left - 1][right][0],
                                    dp[left][right][loc] + (n - (right - left + 1)) * distance);
                        }

                        // 오른쪽으로 갈 수 있는 경우.
                        if (right < n) {
                            long distance = psums[right + 1] - psums[loc == 0 ? left : right];
                            dp[left][right + 1][1] = Math.min(dp[left][right + 1][1],
                                    dp[left][right][loc] + (n - (right - left + 1)) * distance);
                        }
                    }
                }
            }
            // 1 ~ n까지의 회사를 모두 방문했을 때
            // 현재 위치가 1이든 n이든 적은 값을 기록
            sb.append(Math.min(dp[1][n][0], dp[1][n][1])).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}