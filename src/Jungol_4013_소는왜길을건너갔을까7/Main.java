/*
 Author : Ruel
 Problem : Jungol 4013번 소는 왜 길을 건너갔을까? 7
 Problem address : https://jungol.co.kr/problem/4013
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4013_소는왜길을건너갔을까7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 목초지가 주어진다. 각 칸을 넘나들 때는 t만큼의 시간이 소요된다.
        // 각 칸에는 풀들이 자라고 있으며, 매 3번째 움직임마다 해당 칸의 풀들을 전부 먹으며, 해당 풀의 양만큼 시간이 소모된다.
        // (n-1, n-1) 칸에 도달하는 최소 시간은?
        //
        // 최단 경로 문제
        // 기본적으로 한 칸 이동할 때는 t, 3칸마다 자라난 풀 만큼의 시간이 추가적으로 소모된다.
        // 따라서 각 칸에 이르는 경우를
        // dp[row][col][0~3번의 움직임] = 최소 시간으로 정하고 채워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 목초지, 한 칸을 이동할 때 드는 시간 t
        n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 풀의 양
        int[][] grass = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                grass[i][j] = Integer.parseInt(st.nextToken());
        }

        // dp[row][col][0~3번의 움직임] = 최소 시간
        int[][][] dp = new int[n][n][3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 3; k++)
                    dp[i][j][k] = Integer.MAX_VALUE;
            }
        }
        // 처음 상태
        dp[0][0][0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[3]));
        pq.offer(new int[]{0, 0, 0, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (dp[cur[0]][cur[1]][cur[2]] < cur[3])
                continue;

            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = cur[0] + dr[d];
                int nextC = cur[1] + dc[d];

                if (checkArea(nextR, nextC)) {
                    // 다음 칸으로 이동할 때의 소요 시간 누적
                    // 3번째 움직임인 경우, 해당 칸의 풀만큼 시간을 추가 소모
                    int nextCost = cur[3] + (cur[2] == 2 ? grass[nextR][nextC] : 0) + t;
                    // 최소값을 갱신하는 경우에
                    if (dp[nextR][nextC][(cur[2] + 1) % 3] > nextCost) {
                        // 값 갱신 후, 큐에 추가
                        dp[nextR][nextC][(cur[2] + 1) % 3] = nextCost;
                        pq.offer(new int[]{nextR, nextC, (cur[2] + 1) % 3, nextCost});
                    }
                }
            }
        }

        // 목적지에 도달한, 어느 움직임이든 최소 소요 시간을 찾아
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++)
            ans = Math.min(ans, dp[n - 1][n - 1][i]);
        // 출력
        System.out.println(ans);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}