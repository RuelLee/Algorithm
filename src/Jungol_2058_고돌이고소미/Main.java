/*
 Author : Ruel
 Problem : Jungol 2058번 고돌이 고소미
 Problem address : https://jungol.co.kr/problem/2058
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2058_고돌이고소미;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[] dr = {0, -1, -1, -1, 0, 1, 1, 1, 0};
    static int[] dc = {0, -1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) throws IOException {
        // 두 마리의 고슴도치의 현재 위치와 집 위치가 주어진다.
        // 고슴도치는 인근 8방향으로 이동할 수 있다.
        // 고슴도치끼리는 가시 때문에 인근 8방향 위치에 나란히 있을 수 없다.
        // 두 고슴도치가 각각의 집에 가는 최소 시간은?
        //
        // BFS 문제
        // dp[고슴도치1의위치][고슴도치2의위치] = 최소 시간
        // 으로 두고 탐색하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 지도
        n = Integer.parseInt(br.readLine());

        // 각 고슴도치의 현 위치와 집 위치
        int[][] hedgehogs = new int[2][4];
        StringTokenizer st;
        for (int i = 0; i < 2; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++)
                hedgehogs[i][j] = Integer.parseInt(st.nextToken()) - 1;
        }

        // 맵 상태
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 두 고슴도치 위치에 따른 최소 시간
        int[][] minDistances = new int[n * n][n * n];
        for (int[] md : minDistances)
            Arrays.fill(md, Integer.MAX_VALUE);
        // 초기값
        minDistances[hedgehogs[0][0] * n + hedgehogs[0][1]][hedgehogs[1][0] * n + hedgehogs[1][1]] = 0;
        // BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{hedgehogs[0][0], hedgehogs[0][1], hedgehogs[1][0], hedgehogs[1][1]});
        while (!queue.isEmpty()) {
            // 현재 위치
            int[] cur = queue.poll();
            // 현재 소요 시간
            int curDistance = minDistances[cur[0] * n + cur[1]][cur[2] * n + cur[3]];

            // 첫번째 고슴도치의 다음 위치
            for (int d1 = 0; d1 < 9; d1++) {
                int h1nextR = cur[0] + dr[d1];
                int h1nextC = cur[1] + dc[d1];
                int idx1 = h1nextR * n + h1nextC;

                // 맵을 안 벗어나고 막힌 공간이 아니어야 함
                if (!checkArea(h1nextR, h1nextC) || map[h1nextR][h1nextC] == 1)
                    continue;

                // 두번째 고슴도치의 다음 위치
                for (int d2 = 0; d2 < 9; d2++) {
                    int h2nextR = cur[2] + dr[d2];
                    int h2nextC = cur[3] + dc[d2];
                    int idx2 = h2nextR * n + h2nextC;

                    // 맵을 벗어나지 않고, 막히지 않고, 두 고슴도치가 서로 인근 위치에 있어서는 안되고
                    // 해당 상태에 도달하는 시간이 이전보다 더 적은 값인 경우
                    if (checkArea(h2nextR, h2nextC) && map[h2nextR][h2nextC] == 0 &&
                            Math.max(Math.abs(h1nextR - h2nextR), Math.abs(h1nextC - h2nextC)) > 1 &&
                            minDistances[idx1][idx2] > curDistance + 1) {
                        // 값 갱신 및 큐 추가
                        minDistances[idx1][idx2] = curDistance + 1;
                        queue.offer(new int[]{h1nextR, h1nextC, h2nextR, h2nextC});
                    }
                }
            }
        }
        // 최종 상태의 최소 시간 출력
        System.out.println(minDistances[hedgehogs[0][2] * n + hedgehogs[0][3]][hedgehogs[1][2] * n + hedgehogs[1][3]]);
    }

    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}