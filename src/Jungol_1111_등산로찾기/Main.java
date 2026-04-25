/*
 Author : Ruel
 Problem : Jungol 1111번 등산로 찾기
 Problem address : https://jungol.co.kr/problem/1111
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1111_등산로찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 지형이 주이지고 각 칸의 높이가 주어진다.
        // 범위 밖에는 해발 높이 0이다.
        // 같은 높이를 이동할 때는 0의 힘이, 낮은 곳을 이동할 땐 높이 차만큼
        // 높은 곳으로 이동할 땐 높이차의 제곱만큼 힘이 든다.
        // 정상의 위치가 주어질 때, 정상까지 이동하는 최소 힘은?
        //
        // 최단 거리 문제
        // (n + 2 ) * (n + 2) 의 배열을 선언한 뒤, 가장자리는 0으로 내버려두고
        // 안쪽에 지형을 채운다.
        // 그 후, 각 위치에 이르는 최소 힘을 구해나간다.
        // 중복 연산을 피하기 위해, 우선순위큐로 낮은 힘 우선으로 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 맵의 크기
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 정상의 위치
        int[] summit = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};

        // 지형
        int[][] map = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 각 위치에 이르는 최소 힘
        int[][] dp = new int[n + 2][n + 2];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[0][0] = 0;

        // 우선순위큐로 낮은 힘 위치 먼저 계산
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        // 초기값
        priorityQueue.offer(new int[]{0, 0, 0});
        while (!priorityQueue.isEmpty()) {
            // 현재 위치의 좌표와 힘
            int[] current = priorityQueue.poll();
            // 현재 기록된 힘보다 dp에 기록된 값이 더 작다면
            // 이미 이전에 해당 위치를 탐색했으므로 건너뜀
            if (dp[current[0]][current[1]] < current[2])
                continue;

            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = current[0] + dr[d];
                int nextC = current[1] + dc[d];

                if (checkArea(nextR, nextC)) {
                    // 높이 차
                    int diff = Math.abs(map[nextR][nextC] - map[current[0]][current[1]]);
                    // 추가로 드는 힘
                    int cost = (map[nextR][nextC] >= map[current[0]][current[1]]) ? diff * diff : diff;
                    // 누적 힘
                    cost += dp[current[0]][current[1]];

                    // 해당 위치에 이르는 최소 힘인지 비교 후,
                    // 맞다면 값 갱신 후, 우선순위큐에 추가
                    if (dp[nextR][nextC] > cost) {
                        priorityQueue.offer(new int[]{nextR, nextC, cost});
                        dp[nextR][nextC] = cost;
                    }
                }
            }
        }
        // 정상에 이르는 최소 힘 출력
        System.out.println(dp[summit[0]][summit[1]]);
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n + 2 && c >= 0 && c < n + 2;
    }
}