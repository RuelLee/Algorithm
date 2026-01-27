/*
 Author : Ruel
 Problem : Baekjoon 13460번 구슬 탈출 2
 Problem address : https://www.acmicpc.net/problem/13460
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13460_구슬탈출2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {0, -1, 0, 1};
    static int[] dc = {-1, 0, 1, 0};
    static int n, m;
    static char[][] map;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 보드가 주어진다.
        // 가장자리는 #으로 벽으로 막혀있다.
        // 빨간 구슬 R과 파란 구슬 B가 주어진다.
        // 보드를 위쪽, 오른쪽, 아랫쪽, 왼쪽으로 기울일 수 있는데, 빈 칸인 경우 계속하여 굴러간다.
        // 한 칸의 구멍 O가 주어진다.
        // 해당 구멍으로 빨간 구슬만을 꺼내고 싶다.
        // 기울이는 행동을 10회 이내에 최소 행동 수로 꺼내고 싶다.
        // 그 횟수는?
        //
        // BFS, 시뮬레이션 문제
        // dp[빨간구슬의위치][파란구슬의위치] = 최소 행동 횟수
        // 로 정의하고 시뮬레이션을 통해, 현재 보드를 4방향으로 기울이는 경우에 대해 계산해나간다.
        // 두 구슬이 하나의 줄에 있어, 앞의 구슬이 멈추고, 뒤의 구슬이 그 뒤에 멈추는 경우
        // 두 구슬이 동시에 구멍에 빠지는 경우 등의 고려하여 풀어준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 보드의 크기
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 보드 정보
        map = new char[n][m];
        // 두 구슬의 위치
        int[] beads = new int[2];
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 'R') {
                    beads[0] = i * m + j;
                    map[i][j] = '.';
                } else if (map[i][j] == 'B') {
                    beads[1] = i * m + j;
                    map[i][j] = '.';
                }
            }
        }

        // 두 구슬의 위치에 따른 최소 행동 횟수
        int[][] dp = new int[n * m][n * m];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 초기 상태
        dp[beads[0]][beads[1]] = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{beads[0], beads[1]});
        int answer = -1;
        while (!queue.isEmpty()) {
            // 현재 두 구슬의 상태
            int[] current = queue.poll();
            // 행동이 10회 초과가 된다면 불가능하므로 종료
            if (dp[current[0]][current[1]] > 10)
                break;
            else if (map[current[0] / m][current[0] % m] == 'O') {
                // 빨간 구슬이 구멍에 빠진 경우
                // 해당 행동의 횟수를 기록하고 종료
                answer = dp[current[0]][current[1]];
                break;
            }

            // 두 구슬을 동시에 네 방향으로 굴려본다.
            // 방향은 좌 상 우 하로 설정하였다.
            for (int d = 0; d < 4; d++) {
                // 좌, 상 방향이며 빨간 구슬이 더 적은 idx값을 갖는 경우
                // 혹은 우 하 방향이며 빨간 구슬이 더 큰 idx 값을 갖는 경우
                // 빨간 구슬을 먼저 굴린다.
                if ((d < 2 && current[0] < current[1]) || (d >= 2 && current[0] > current[1])) {
                    // 빨간 구슬
                    int nextRed = nextLoc(current[0], d);
                    // 빨간 구슬이 위치가 빈 칸이라면 해당 위치에 구슬을 두어, 파란 구슬과 위치가 겹치지 않도록 한다.
                    if (map[nextRed / m][nextRed % m] == '.')
                        map[nextRed / m][nextRed % m] = 'R';
                    int nextBlue = nextLoc(current[1], d);
                    // 빨간 구슬이 놓였던 칸을 다시 빈 칸으로 복구
                    if (map[nextRed / m][nextRed % m] == 'R')
                        map[nextRed / m][nextRed % m] = '.';

                    // 파란 구슬이 구멍에 떨어지지 않았으며
                    // 현재 두 구슬의 상태가 최소 행동 횟수를 갱신한다면
                    // 횟수 기록 후, 큐에 추가
                    if (map[nextBlue / m][nextBlue % m] != 'O' && dp[nextRed][nextBlue] > dp[current[0]][current[1]] + 1) {
                        dp[nextRed][nextBlue] = dp[current[0]][current[1]] + 1;
                        queue.offer(new int[]{nextRed, nextBlue});
                    }
                } else {
                    // 그 외의 경우는 파란 구슬을 먼저 굴린다.
                    int nextBlue = nextLoc(current[1], d);
                    if (map[nextBlue / m][nextBlue % m] == '.')
                        map[nextBlue / m][nextBlue % m] = 'B';
                    int nextRed = nextLoc(current[0], d);
                    if (map[nextBlue / m][nextBlue % m] == 'B')
                        map[nextBlue / m][nextBlue % m] = '.';

                    if (map[nextBlue / m][nextBlue % m] != 'O' && dp[nextRed][nextBlue] > dp[current[0]][current[1]] + 1) {
                        dp[nextRed][nextBlue] = dp[current[0]][current[1]] + 1;
                        queue.offer(new int[]{nextRed, nextBlue});
                    }
                }
            }
        }
        // 답 출력
        System.out.println(answer);
    }

    // current 위치의 구슬을 direction 방향으로 기울였을 때
    // 멈추는 위치를 반환한다.
    static int nextLoc(int current, int direction) {
        // 현재 row, col
        int r = current / m;
        int c = current % m;

        // 다음 칸이 맵을 벗어나지 않으며
        // 빈 칸 혹은 구멍인 경우, 다음 칸으로 나아간다.
        while (checkArea(r + dr[direction], c + dc[direction]) && (map[r + dr[direction]][c + dc[direction]] == '.' || map[r + dr[direction]][c + dc[direction]] == 'O')) {
            r += dr[direction];
            c += dc[direction];
            // 만약 구멍을 만났다면 해당 위치에서 멈춘다.
            if (map[r][c] == 'O')
                break;
        }
        // 현재 위치 반환
        return r * m + c;
    }

    // 맵의 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}