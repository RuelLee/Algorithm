/*
 Author : Ruel
 Problem : Baekjoon 1938번 통나무 옮기기
 Problem address : https://www.acmicpc.net/problem/1938
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1938_통나무옮기기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int n;
    static char[][] map;
    static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1, -2, 2, 0, 0};
    static int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1, 0, 0, -2, 2};
    static int[] order = {1, 6, 3, 4};
    static int[][] checkIdx = {{0, 1, 2}, {5, 6, 7}, {10}, {11}, {8}, {9}, {0, 3, 5}, {2, 4, 7}};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 맵이 주어진다.
        // BBB 와  EEE가 가로 혹은 세로로 주어진다.
        // BBB의 위치의 통나무를 EEE로 옮기려한다.
        // 이동은 위, 아래, 왼쪽, 오른쪽으로 이동이 가능하고
        // 중심을 기준으로 90도 회전시키는 것이 가능하다.
        // 최소한의 동작으로 이동시키고자 할 때, 그 횟수는?
        //
        // BFS 문제
        // 통나무가 가로이냐, 세로이냐에 따라서, 이동할 때, 체크해야하는 범위가 달라진다.
        // 위 사항만 주의한다면 일반적인 BFS 문제

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 지도
        n = Integer.parseInt(br.readLine());
        map = new char[n][n];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        // BBB의 중심 위치와 가로 세로 여부
        int[] start = new int[3];
        // EEE의 중심 위치와 가로 세로 여부
        int[] end = new int[3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'B' && start[0] == 0) {
                    if (j + 1 < n && map[i][j + 1] == 'B') {
                        start[0] = i;
                        start[1] = j + 1;
                    } else {
                        start[0] = i + 1;
                        start[1] = j;
                        start[2] = 1;
                    }
                } else if (map[i][j] == 'E' && end[0] == 0) {
                    if (j + 1 < n && map[i][j + 1] == 'E') {
                        end[0] = i;
                        end[1] = j + 1;
                    } else {
                        end[0] = i + 1;
                        end[1] = j;
                        end[2] = 1;
                    }
                }

                if (map[i][j] == 'B' || map[i][j] == 'E')
                    map[i][j] = '0';
            }
        }

        // dp[i][j][k] = (i, j)에 중심이 있고, 현재 k(가로 or 세로) 상태
        int[][][] dp = new int[n][n][2];
        for (int[][] d : dp) {
            for (int[] dd : d)
                Arrays.fill(dd, Integer.MAX_VALUE);
        }
        // 초기 상태의 동작은 0
        dp[start[0]][start[1]][start[2]] = 0;

        // BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            // 현재 상태
            int[] current = queue.poll();

            // 90도 회전시키는 경우
            if (canTurn(current[0], current[1]) &&
                    dp[current[0]][current[1]][(current[2] + 1) % 2] > dp[current[0]][current[1]][current[2]] + 1) {
                dp[current[0]][current[1]][(current[2] + 1) % 2] = dp[current[0]][current[1]][current[2]] + 1;
                queue.offer(new int[]{current[0], current[1], (current[2] + 1) % 2});
            }

            // 위, 아래, 왼쪽, 오른쪽으로 이동하는 경우
            for (int m = 0; m < order.length; m++) {
                // 이동이 가능하고
                if (canMove(current[0], current[1], current[2] * 4 + m)) {
                    // 다음 이동의 중심점 위치
                    int nextR = current[0] + dr[order[m]];
                    int nextC = current[1] + dc[order[m]];
                    // 최소 동작 횟수를 갱신한다면
                    // 값 갱신 후 큐에 추가
                    if (dp[nextR][nextC][current[2]] > dp[current[0]][current[1]][current[2]] + 1) {
                        dp[nextR][nextC][current[2]] = dp[current[0]][current[1]][current[2]] + 1;
                        queue.offer(new int[]{nextR, nextC, current[2]});
                    }
                }
            }
        }

        // 답 출력
        int answer = dp[end[0]][end[1]][end[2]];
        System.out.println(answer == Integer.MAX_VALUE ? 0 : answer);
    }

    // (r, c)에서 type으로 이동가능한지 살펴본다.
    // type은 (가로 0, 세로 1) * 4 + (위 0, 아래 1, 왼쪽 2, 오른쪽 3)으로 정의
    static boolean canMove(int r, int c, int type) {
        // checkIdx는 type이 주어질 때, 체크해야하는 범위를 담고 있다.
        for (int d = 0; d < checkIdx[type].length; d++) {
            int nextR = r + dr[checkIdx[type][d]];
            int nextC = c + dc[checkIdx[type][d]];

            // 해당하는 범위가 맵을 벗어나거나, 1로 막힌 경우
            // false 반환
            if (!checkArea(nextR, nextC) || map[nextR][nextC] == '1')
                return false;
        }
        // 모두 통과했다면 true 반환
        return true;
    }

    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }

    // 회전 가능 여부 체크
    static boolean canTurn(int r, int c) {
        if (r - 1 >= 0 && r + 1 < n && c - 1 >= 0 && c + 1 < n) {
            for (int i = r - 1; i <= r + 1; i++) {
                for (int j = c - 1; j <= c + 1; j++) {
                    if (map[i][j] == '1')
                        return false;
                }
            }
            return true;
        }
        return false;
    }
}