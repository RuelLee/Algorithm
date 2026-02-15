/*
 Author : Ruel
 Problem : Baekjoon 19952번 인성 문제 있어??
 Problem address : https://www.acmicpc.net/problem/19952
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19952_인성문제있어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[][] obstacles, dp;
    static int[] dr = new int[]{-1, 0, 1, 0};
    static int[] dc = new int[]{0, 1, 0, -1};
    static int h, w;

    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 미로의 세로 h, 가로 w, 장애물의 수 o, 초기 힘 f, 출발지의 좌표 xs, ys, 도착지의 좌표 xe, ye가 주어진다.
        // 그리고 o개의 장애물에 대해 좌표 (x, y), 높이 l이 주어진다.
        // 장애물이 없는 지역의 높이는 0이다.
        // 격자 간에 이동을 할 때, 높이가 같거나 낮다면, 점프를 하지 않고 이동한다.
        // 높다면 점프를 하게 되고, 남은 힘이 높이 차 이상이 되어야한다.
        // 이동은 상하좌우로 한칸씩 할 수 있고, 할 때마다, 힘이 하나씩 줄어든다.
        // 힘이 0이 된 순간, 더 이상 이동할 수 없다.
        // 출발지에서 도착지로 이동할 수 있는지를 출력하라
        //
        // BFS 문제
        // 장애물을 표시하고, BFS를 통해 각 위치에서 최대 남은 힘을 구한다.
        // (xs, ys)에서 시작하여 (xe, ye)에 도달할 때, 남은 힘이 0이상이라면 가능한 경우
        // 그렇지 않다면 불가능한 경우이다.

        // 장애물의 위치, 각 위치에 최대 잔존 힘
        obstacles = new int[100][100];
        dp = new int[100][100];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 맵의 높이 h, 너비 w
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            // 장애물의 수 o, 초기 힘 f
            int o = Integer.parseInt(st.nextToken());
            int f = Integer.parseInt(st.nextToken());
            // 출발지와 도착지 장소
            int xs = Integer.parseInt(st.nextToken()) - 1;
            int ys = Integer.parseInt(st.nextToken()) - 1;
            int xe = Integer.parseInt(st.nextToken()) - 1;
            int ye = Integer.parseInt(st.nextToken()) - 1;

            // 배열 초기화
            clear();
            // 처음 위치 힘
            dp[xs][ys] = f;
            // 장애물 입력
            for (int i = 0; i < o; i++) {
                st = new StringTokenizer(br.readLine());
                obstacles[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1]
                        = Integer.parseInt(st.nextToken());
            }

            // BFS
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{xs, ys});
            while (!queue.isEmpty()) {
                int[] current = queue.poll();

                // 사방탐색
                for (int d = 0; d < 4; d++) {
                    int nextR = current[0] + dr[d];
                    int nextC = current[1] + dc[d];

                    // (h, w)를 벗어나지 않으며
                    // 잔존 힘이 높이 차보다 많으며, 최대 잔존 힘 값을 갱신하는 경우
                    // 기록하고 다음 탐색을 위해 큐에 추가
                    if (checkArea(nextR, nextC) && dp[current[0]][current[1]] >= Math.max(0, obstacles[nextR][nextC] - obstacles[current[0]][current[1]])
                            && dp[nextR][nextC] < dp[current[0]][current[1]] - 1) {
                        dp[nextR][nextC] = dp[current[0]][current[1]] - 1;
                        queue.offer(new int[]{nextR, nextC});
                    }
                }
            }
            // 도착지에서 힘이 0이상이라면 가능, 음수인 경우는 불가능
            sb.append(dp[xe][ye] >= 0 ? "잘했어!!" : "인성 문제있어??").append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }

    // 배열 초기화
    static void clear() {
        for (int[] o : obstacles)
            Arrays.fill(o, 0);
        for (int[] d : dp)
            Arrays.fill(d, -1);
    }
}