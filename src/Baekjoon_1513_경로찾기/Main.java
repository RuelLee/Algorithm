/*
 Author : Ruel
 Problem : Baekjoon 1513번 경로 찾기
 Problem address : https://www.acmicpc.net/problem/1513
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1513_경로찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {0, 1};
    static int[] dc = {1, 0};
    static final int LIMIT = 1_000_007;

    public static void main(String[] args) throws IOException {
        // n, m 크기의 도시와 c개의 오락실과 그 좌표가 주어진다.
        // 항상 (1, 1)에서 출발하여 (n, m)에 가는 동안 오락실들을 거쳐가고자 한다.
        // 이동은 항상 (r + 1, c) or (r, c + 1)로만 이동할 수 있다.
        // 오락실은 번호가 증가하는 순서로만 방문할 수 있다.
        // 가령 5번 오락실을 방문했다면 다음에는 1, 2, 3, 4번 오락실은 방문할 수 없다.
        // n, m에 도착할 때, 각 오락실에 방문한 횟수에 따른 경우의 수를 출력하라.
        // 경로의 개수는 1,000,007로 나눈 나머지를 출력한다.
        //
        // DP문제이긴 한데 고려할 점이 많아 4차원 배열까지 세워야했다.
        // dp[행][열][방문한오락실의개수][마지막방문한오락실의번호]로 세웠다.
        // 그리고 (1, 1) 부터 (n, m)까지 차례대로 방문하며 DP를 채워나가자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 오락실의 위치 표시
        // 오락실 순서에 따라 번호로 저장해둔다.
        int[][] arcades = new int[n][m];
        for (int i = 0; i < c; i++) {
            st = new StringTokenizer(br.readLine());
            arcades[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = i + 1;
        }

        int[][][][] dp = new int[n][m][c + 1][c + 1];
        // 시작점이 오락실이라면 해당하는 DP에 1로 표시
        if (arcades[0][0] > 0)
            dp[0][0][1][arcades[0][0]] = 1;
        // 아닐 경우 dp[0][0][0][0]에 1로 표시.
        else
            dp[0][0][0][0] = 1;

        // 행
        for (int row = 0; row < dp.length; row++) {
            // 열
            for (int col = 0; col < dp[row].length; col++) {
                // 오락실 방문 횟수
                for (int visitedArcades = 0; visitedArcades < dp[row][col].length; visitedArcades++) {
                    // 마지막에 방문한 오락실의 번호.
                    for (int lastVisitedIdx = 0; lastVisitedIdx < dp[row][col][visitedArcades].length; lastVisitedIdx++) {
                        // 만약 도달하는 경우의 수가 0이라면 건너뛴다.
                        if (dp[row][col][visitedArcades][lastVisitedIdx] == 0)
                            continue;

                        // 다음의 위치는 행으로 진행하거나 열로 진행하는 두가지 경우.
                        for (int d = 0; d < 2; d++) {
                            int nextR = row + dr[d];
                            int nextC = col + dc[d];

                            // 다음 위치가 도시를 벗어나지 않고,
                            if (checkArea(nextR, nextC, dp)) {
                                // 만약 오락실이 아니라면
                                if (arcades[nextR][nextC] == 0) {
                                    // (row, col)에서 (nextR, nextC)로 경우의 수를 그대로 더해준다.
                                    // 방문 오락실의 개수, 마지막 방문 오락실의 번호도 그대로 유지.
                                    dp[nextR][nextC][visitedArcades][lastVisitedIdx] += dp[row][col][visitedArcades][lastVisitedIdx];
                                    dp[nextR][nextC][visitedArcades][lastVisitedIdx] %= LIMIT;
                                } else if (arcades[nextR][nextC] < lastVisitedIdx)
                                    // nextR, nextC가 오락실인데, 현재 계산하는 상태의 마지막 방문한 오락실 번호보다 작은 번호를 갖고 있다면 건너뛴다.
                                    continue;
                                else {
                                    // nextR, nextC가 오락실이며, 현재 계산하는 상태의 마지막 방문한 오락실 번호보다 커서 방문이 가능한 경우.
                                    // 방문한 오락실의 개수 하나 증가, 마지막 방문한 오락실 번호는, (nextR, nextC)의 오락실 번호에 경우의 수를 더해준다.
                                    dp[nextR][nextC][visitedArcades + 1][arcades[nextR][nextC]] += dp[row][col][visitedArcades][lastVisitedIdx];
                                    dp[nextR][nextC][visitedArcades + 1][arcades[nextR][nextC]] %= LIMIT;
                                }
                            }
                        }
                    }
                }
            }
        }

        // 최종적으로 (n, m)에 도달한 경우의 수들을 계산해준다.
        // 마지막에 어떤 오락실을 방문했든, 방문 횟수가 같은 것들은 서로 더해주면 된다.
        // 각 칸의 경우의 수가 1,000,0007 보다 작은 값이 되어있으니, int 스트림으로 뽑아 한번에 더해준 뒤
        // 마지막에 1,000,007 모듈러 연산을 해주자.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dp[n - 1][m - 1].length; i++)
            sb.append(Arrays.stream(dp[n - 1][m - 1][i]).sum() % LIMIT).append(" ");
        System.out.println(sb);
    }

    static boolean checkArea(int r, int c, int[][][][] dp) {
        return r >= 0 && r < dp.length && c >= 0 && c < dp[r].length;
    }
}