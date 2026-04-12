/*
 Author : Ruel
 Problem : Baekjoon 1175번 배달
 Problem address : https://www.acmicpc.net/problem/1175
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1175_배달;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자가 주어진다.
        // 각 격자는
        // S : 처음 위치, C : 선물을 배달해야하는 곳. 2곳이 주어진다, # : 막힌 곳, . : 자유롭게 드나들 수 있는 곳
        // 으로 주어진다
        // 각 상하좌우 이동을 할 때, 1분이 소요되고, 이전에 이동한 방향으로는 연속해서 이동하지 못한다할 때
        // 두 곳 모두 선물을 배달하는데 걸리는 시간은?
        //
        // BFS 그래프 탐색 문제
        // 그래프 탐색 문제인데, 주의해야할 점은
        // 각 위치마다, 이전에 이동한 방향과 현재까지 배달을 마친 곳의 정보가 필요하다.
        // 따라서
        // dp[row][col][bit를 통한 선물 배달 체크][이전 이동 방향] = 최소 이동 시간으로 세우고 값을 채운다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        char[][] map = new char[n][];
        // 시작 위치
        int start = -1;
        // 두 선물 위치
        int[] presents = new int[2];
        Arrays.fill(presents, -1);
        // 맵 정보 입력
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < m; j++) {
                // 시작 위치 혹은 선물을 배달할 위치인 경우
                if (map[i][j] == 'S')
                    start = i * m + j;
                else if (map[i][j] == 'C') {
                    if (presents[0] == -1)
                        presents[0] = i * m + j;
                    else
                        presents[1] = i * m + j;
                }
            }

        }

        // 값 초기화
        int[][][][] dp = new int[n][m][4][4];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++)
                        dp[i][j][k][l] = Integer.MAX_VALUE;
                }
            }
        }

        // 시작 위치에서부터 값 추가
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            dp[start / m][start % m][0][i] = 0;
            queue.offer(new int[]{start / m, start % m, 0, i});
        }

        // BFS
        while (!queue.isEmpty()) {
            // 현재 위치
            int[] cur = queue.poll();

            // 4방 탐색
            for (int d = 0; d < 4; d++) {
                // 같은 방향일 경우 건너뛴다.
                if (d == cur[3])
                    continue;

                // 다음 위치
                int nextR = cur[0] + dr[d];
                int nextC = cur[1] + dc[d];

                // 맵 범위를 벗어나지 않고, 벽이 아니며
                if (checkArea(nextR, nextC) && map[nextR][nextC] != '#') {
                    // 선물의 배달지인지 확인
                    boolean pre = false;
                    for (int i = 0; i < 2; i++) {
                        if (presents[i] / m == nextR && presents[i] % m == nextC) {
                            pre = true;
                            // 선물이며, 최소 방문 시간을 갱신하는 경우
                            if (dp[nextR][nextC][cur[2] | (1 << i)][d] > dp[cur[0]][cur[1]][cur[2]][cur[3]] + 1) {
                                dp[nextR][nextC][cur[2] | (1 << i)][d] = dp[cur[0]][cur[1]][cur[2]][cur[3]] + 1;
                                queue.offer(new int[]{nextR, nextC, (cur[2] | (1 << i)), d});
                            }
                        }
                    }

                    // 선물의 배달지가 아니며, 벽이 아닌 경우
                    if (!pre && map[nextR][nextC] != '#' && dp[nextR][nextC][cur[2]][d] > dp[cur[0]][cur[1]][cur[2]][cur[3]] + 1) {
                        dp[nextR][nextC][cur[2]][d] = dp[cur[0]][cur[1]][cur[2]][cur[3]] + 1;
                        queue.offer(new int[]{nextR, nextC, cur[2], d});
                    }
                }
            }
        }

        // 두 선물의 위치에서
        // 모든 선물을 배달한 경우들을 모두 살펴보고 최소 시간을 찾는다.
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                min = Math.min(min, dp[presents[i] / m][presents[i] % m][3][j]);
            }
        }
        // min이 초기값인 경우, 불가능한 경우이므로 -1을 출력
        // 그 외의 경우 min 값을 출력
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}