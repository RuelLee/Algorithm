/*
 Author : Ruel
 Problem : Baekjoon 1489번 대결
 Problem address : https://www.acmicpc.net/problem/1489
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1489_대결;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // A, B 각 팀 N명이 주어진다.
        // 각 선수들은 한 번 다른 다른 팀에 속한 사람과 대결을 해야하며
        // 이길 경우 2점, 비길 경우 1점을 얻는다고 한다.
        // A팀이 얻을 수 있는 최대 점수는?
        //
        // DP 문제
        // DP를 통해 dp[i][j] = B팀에서 i번 선수, A팀에서 j번 선수까지
        // 대결에 참여했을 때 얻을 수 있는 A팀의 최대 점수로 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // A, B팀의 선수들
        int[] aTeam = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] bTeam = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬하여 더 강한 선수가 더 뒤에 나오도록 한다.
        Arrays.sort(aTeam);
        Arrays.sort(bTeam);

        // dp[i][j]가 dp[i-1][j-1], dp[i-1][j], dp[i][j-1] 등
        // 이전 결과를 참고하므로, 0점인 가장 윗쪽 행과 가장 왼쪽 열을 추가시키기 위해 n+1로 선언하낟.
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                // dp[i][j]의 초기값은
                // dp[i-1][j], dp[i][j-1]중 더 큰 값
                // 그 후, aTeam[j-1]이 bTeam[i-1]보다 더 강한 선수라면
                // 두 선수가 아직 대결에 참여하지 않은 상태에서 A팀이 얻을 수 있는 최대 점수인
                // dp[i-1][j-1]에서 2점을 더한 값과 dp[i][j]을 비교하고
                // 같은 경우엔 +1점을 한 값과 비교하여 더 큰 값을 남겨둔다.
                dp[i][j] = Math.max(Math.max(dp[i - 1][j], dp[i][j - 1]),
                        dp[i - 1][j - 1] + (aTeam[j - 1] > bTeam[i - 1] ? 2 : (aTeam[j - 1] == bTeam[i - 1] ? 1 : 0)));
            }
        }

        // 최종적으로 모든 선수가 참여한 상태에서의
        // A팀이 얻을 수 있는 최대 점수를 출력한다.
        System.out.println(dp[n][n]);
    }
}