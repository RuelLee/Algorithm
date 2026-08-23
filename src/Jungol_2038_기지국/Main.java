/*
 Author : Ruel
 Problem : Jungol 2038번 기지국
 Problem address : https://jungol.co.kr/problem/2038
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2038_기지국;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개 건물의 위치가 (x, y)로 주어진다.
        // x축 위에 평행한 정사각형 모양으로 정사각형으로 통신범위를 설치할 수 있으며, 한 변의 길이만큼 비용이 소모된다.
        // 최소의 비용으로 모든 건물을 커버하고자할 때, 총 비용은?
        //
        // DP 문제
        // dp[i] = i번째 건물까지 커버하는 최소 비용으로 정하고
        // dp[0] + 1번 ~ i번 건물까지 하나로 커버하는 비용, dp[1] + 2번 ~ i번, ...
        // 경우들을 비교하며 dp[i] 값을 찾아나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 건물
        int n = Integer.parseInt(br.readLine().trim());
        int[][] stations = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < stations.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < stations[i].length; j++)
                stations[i][j] = Integer.parseInt(st.nextToken());
        }
        // x값에 따라 정렬
        Arrays.sort(stations, Comparator.comparingInt(o -> o[0]));

        // dp[i] = i번째 건물까지 커버하는 최소 비용
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = 0; i < stations.length; i++) {
            int max = 0;
            for (int j = i; j >= 0; j--) {
                // j ~ i번째 건물 중 최대 y값 * 2와 i ~ j번째 건물 사이의 거리
                max = Math.max(max, Math.max(stations[i][0] - stations[j][0], Math.abs(stations[j][1]) * 2));
                // j-1번째 건물까지 최소값 + j ~ i번쨰 건물을 하나로 커버할 때의 비용
                dp[i] = Math.min(dp[i], max + (j > 0 ? dp[j - 1] : 0));
            }
        }
        // 답 출력
        System.out.println(dp[n - 1]);
    }
}