/*
 Author : Ruel
 Problem : Baekjoon 17258번 인기가 넘쳐흘러
 Problem address : https://www.acmicpc.net/problem/17258
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17258_인기가넘쳐흘러;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 욱제는 세계적인 스타이다.
        // 파티장에 파티 인원이 t명 이상일 때만 파티장에 들어와 공연을 한다.
        // n명의 인원에 대해 파티장 입장 시간과 퇴장 시간이 주어진다.
        // 영선이는 자신의 친구 k명을 불러 욱제의 공연 시간을 최대화하고자 한다.
        // 영선이의 친구들은 욱제와 영선이의 친구들을 제외한 인원이 t명 이상이 되는 순간 모두 파티장을 나가 돌아오지 않는다.
        // 욱제의 최대 공연 시간은?
        //
        // 누적합, DP 문제
        // n명에 대해 입장 시간과 퇴장 시간을 바탕으로 누적합을 구하여
        // 각 시간마다 있는 인원의 수를 구한다.
        // dp[시간][파티장 내의 영선이 친구들의 수][아직 부르지 않은 영선이 친구의 수] = 욱제의 공연 시간
        // 으로 dp를 세우고 문제를 푼다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 파티가 열리는 시간 n 시간
        int n = Integer.parseInt(st.nextToken());
        // 입장객 m명, 영선이가 부를 수 있는 친구의 수 k명, 욱제가 공연하는 최소 인원 t
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 누적합으로 각 시각에 파티장의 인원 수 계산
        int[] times = new int[n + 2];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            times[start]++;
            times[end]--;
        }
        for (int i = 1; i < times.length; i++)
            times[i] += times[i - 1];

        // dp[시간][파티장 내의 영선이 친구들의 수][아직 부르지 않은 영선이 친구의 수] = 욱제의 공연 시간
        int[][][] dp = new int[n + 2][k + 1][k + 1];
        for (int[][] a : dp) {
            for (int[] b : a)
                Arrays.fill(b, Integer.MIN_VALUE);
        
        }
        // 시작 시각 1, 행사장 내의 친구 0, 아직 안 부른 친구 k명
        // 이 시작 기준
        dp[1][0][k] = 0;
        
        // 모든 시간을 돌아보며
        for (int time = 0; time < dp.length - 1; time++) {
            // 참석한 친구의 수와
            for (int attend = 0; attend < dp[time].length; attend++) {
                // 아직 안 부른 친구의 수
                for (int remain = 0; remain < dp[time][attend].length; remain++) {
                    // 만약 초기값이라면 불가능한 경우이므로 건너뜀.
                    if (dp[time][attend][remain] == Integer.MIN_VALUE)
                        continue;
                    else if (times[time] >= t)      // 파티장 인원의 친구들을 제외하고 t명 이상이 되면 친구들은 모두 퇴장. 욱제는 공연을 하므로 값은 증가
                        dp[time + 1][0][remain] = Math.max(dp[time + 1][0][remain], dp[time][attend][remain] + 1);
                    else {
                        // 그 외의 경우
                        // 친구를 추가로 부르지 않는 경우
                        dp[time + 1][attend][remain] = Math.max(dp[time + 1][attend][remain], dp[time][attend][remain] + (attend + times[time] >= t ? 1 : 0));

                        // 친구를 추가로 i명 파티장으로 부르는 경우
                        // i는 remain보다 같거나 작아야하고, 파티장 내의 모든 인원의 합은 t보다 같거나 작아야한다.
                        for (int i = 1; i <= remain && i + attend + times[time] <= t; i++) {
                            int totalAttend = i + attend + times[time];
                            int totalValue = dp[time][attend][remain] + (totalAttend >= t ? 1 : 0);
                            dp[time + 1][attend + i][remain - i] = Math.max(dp[time + 1][attend + i][remain - i], totalValue);
                        }
                    }
                }
            }
        }

        // 기준 시각 n+1일 때 최대 욱제의 공연 시간을 찾는다.
        int answer = 0;
        for (int i = 0; i < dp[n + 1].length; i++) {
            for (int j = 0; j < dp[n + 1][i].length; j++)
                answer = Math.max(answer, dp[n + 1][i][j]);
        }
        // 답 출력
        System.out.println(answer);
    }
}