/*
 Author : Ruel
 Problem : Baekjoon 11570번 환상의 듀엣
 Problem address : https://www.acmicpc.net/problem/11570
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11570_환상의듀엣;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 상덕이와 희원이가 같이 노래를 부른다
        // 노래의 음들이 주어지며, 음은 둘 중 한명이 부른다
        // 예를 들어 3 6 2 5 4가 노래라면, 한 명은 3 2 4 한 명은 6 5를 노래한다.
        // 한 명이 부르는 연속한 음들 간의 차이가 크다면 피로도가 크다
        // 그리고 그 때의 피로도는 3 - > 2 -> 4 = + 1 + 2 = 3, 6 -> 5 = + 1 총 합 4이다.
        // 따라서 노래의 음들을 둘이 나누어 부를 때, 피로도를 가장 적게 부르는 방법은 무엇인가?
        //
        // DP문제이다
        // dp[i][j]를 설정하되, i는 전자가 부른 마지막 음의 idx, j는 후자가 부른 마지막 음의 idx이다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 노래의 음들을 입력 받는다.
        int[] sounds = new int[n + 1];
        for (int i = 1; i < sounds.length; i++)
            sounds[i] = Integer.parseInt(st.nextToken());

        int[][] dp = new int[sounds.length][sounds.length];
        // 처음에는 INF 값으로 초기화해주자.
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 첫 음을 부를 때는 피로도가 없다.
        // 전자와 후자가 부르는 경우 각각 0을 넣어줘야하지만
        // 연산을 줄이기 위해 row가 col보다 큰 경우만 계산하자.
        // dp[i][j]를 전자가 i번째 음을, 후자가 j번째 음을 마지막으로 부른 경우라고 하였다.
        // 하지만 전자가 j번째 음을, 후자가 i번째 음을 마지막에 부른 경우도 결국엔 같은 값을 갖게 된다
        // 사실상 dp[i][j] == dp[j][i]인 것이다.
        // 따라서 i == j인 지점을 기준으로 위쪽과 아랫쪽으로 나눠 한쪽만 계산하더라도 무방하다.
        dp[1][0] = 0;
        for (int i = 1; i < sounds.length - 1; i++) {
            // 연속해서 전자가 음을 부르는 경우이다. 계속해서 음 간의 차이를 더해 나간다.
            dp[i + 1][0] = dp[i][0] + Math.abs(sounds[i + 1] - sounds[i]);
            // 후자가 첫 음으로 i+1번째 음을 부르는 경우이다.
            // dp[i][i+1]에 기록하는 것이 맞으나, 상술했듯 전자 <-> 후자가 자리를 바꾸더라도 그 값은 같다.
            // 따라서 dp[i][i+1] 대신에 dp[i+1][i]로 값을 계산해 저장해도 무방하다.
            dp[i + 1][i] = dp[i][0];
        }

        // 전자가 i번째 음을 마지막으로, 후자가 j번째 음을 마지막으로 부는 경우.
        for (int i = 1; i < dp.length - 1; i++) {
            // i == j을 기준으로 아랫 부분만 계산에 활용한다.
            for (int j = 1; j < i; j++) {
                // 다음 음의 높이는 i, j 중 큰 값 + 1이다.
                // 하지만 우리는 i가 j보다 항상 클 때만 다루므로 i + 1로 하자.
                int next = i + 1;
                // 다음 음을 전자가 부르는 경우.
                // dp[next][j]에 들어있는 값보다 dp[i][j] + (i번째 음과 next번째 음의 차이)가 더 작다면 그 값으로 갱신한다.
                dp[next][j] = Math.min(dp[next][j], dp[i][j] + Math.abs(sounds[next] - sounds[i]));
                // 다음 음을 후자가 부르는 경우.
                // 원래 저장되어야하는 위치는 dp[i][next]
                // 하지만 dp[i][next] 나 dp[next][i] 나 결국엔 같은 값을 갖게 되므로, dp[next][i]에 저장하여
                // i == j인 부분의 아래쪽만 계산에 활용하도록 하자.
                // 원래대로 식을 세운다면
                // dp[i][next] = Math.min(dp[i][next], dp[i][j] + Math.abs(sounds[next] - sounds[j]));
                // 일 것이다. 하지만 i > j인 부분만 계산 및 활용하므로 이에 맞게 바꿔준다.
                dp[next][i] = Math.min(dp[next][i], dp[i][j] + Math.abs(sounds[next] - sounds[j]));
            }
        }

        // 최종적으로 dp[마지막음][] or dp[][마지막음]인 값들 중 최소값을 갖는 경우를 찾는다.
        // i == j 아랫부분만 계산에 활용했으므로, dp[dp.length - 1]에서 가장 작은 찾아 그 값을 출력한다.
        System.out.println(Arrays.stream(dp[dp.length - 1]).min().getAsInt());
    }
}