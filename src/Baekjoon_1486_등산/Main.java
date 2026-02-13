/*
 Author : Ruel
 Problem : Baekjoon 1486번 등산
 Problem address : https://www.acmicpc.net/problem/1486
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1486_등산;

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
        // n * m 크기의 칸이 주어지고 각 칸에는 알파벳 대문자 혹은 소문자가 들어있다.
        // 알파벳 대문자는 A ~ Z까지 0 ~ 25의 높이를 나타내고 소문자는 a ~ z까지 26 ~ 51까지의 높이를 나타낸다.
        // 각 칸을 이동할 때, 높이가 같거나 낮다면 1의 시간을 소모하고, 높이가 더 높다면 높이 차의 제곱만큼 시간을 소모한다.
        // 칸을 이동할 때는 높이 차가 t이하여야 한다.
        // 제한 시간 d 동안 가장 높은 곳을 다녀오려고 한다.
        // 가능한 가장 높은 높이는?
        //
        // BFS 문제
        // 어떤 지점을 가는데 가는 경로와 오는 경로가 다를 수 있다.
        // 갈 때는 높이 차를 적게해서 올라가는 것이 유리하지만, 내려올 때는 높이 차가 t이하이기만 하면
        // 가장 짧은 경로로 가는 것이 유리하기 때문

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 맵
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        // 이동에 허용되는 높이 차 t, 제한 시간 d
        int t = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        char[][] map = new char[n][m];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        // minTimes[0] -> 올라갈 때의 최소 소요 시간
        // minTimes[1] -> 돌아올 때의 최소 소요 시간.
        int[][][] minTimes = new int[2][n][m];
        for (int[][] mt : minTimes) {
            for (int[] row : mt)
                Arrays.fill(row, Integer.MAX_VALUE);
        }
        minTimes[0][0][0] = minTimes[1][0][0] = 0;

        // BFS
        Queue<int[]> queue = new LinkedList<>();
        // 가는 경로
        queue.offer(new int[]{0, 0, 0});
        // 돌아오는 경로
        queue.offer(new int[]{1, 0, 0});
        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            // 사방 탐색
            for (int i = 0; i < 4; i++) {
                int nextR = current[1] + dr[i];
                int nextC = current[2] + dc[i];

                // 범위 체크
                if (checkArea(nextR, nextC)) {
                    // 높이 차
                    int heightDiff = getHeight(map[nextR][nextC]) - getHeight(map[current[1]][current[2]]);
                    // 높이 차가 t이하인 경우
                    if (Math.abs(heightDiff) <= t) {
                        // 가는 경로일 때는 다음 위치가 더 낮은 경우
                        // 오는 경로일 때는 다음 위치가 더 높은 경우, 소요 시간이 1
                        // 그 외의 경우 높이 차의 제곱이 소요 시간
                        int costTime = (current[0] == 0 && heightDiff <= 0) || (current[0] == 1 && heightDiff >= 0) ? 1 : heightDiff * heightDiff;
                        // 최소 소요 시간을 갱신하는 경우 값 갱신 후, 큐에 추가
                        if (minTimes[current[0]][nextR][nextC] > minTimes[current[0]][current[1]][current[2]] + costTime) {
                            minTimes[current[0]][nextR][nextC] = minTimes[current[0]][current[1]][current[2]] + costTime;
                            queue.offer(new int[]{current[0], nextR, nextC});
                        }
                    }
                }
            }
        }

        // 두 경로의 합이 d 이하인 최대 높이를 찾는다.
        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (minTimes[0][i][j] != Integer.MAX_VALUE && minTimes[1][i][j] != Integer.MAX_VALUE &&
                        minTimes[0][i][j] + minTimes[1][i][j] <= d) {
                    answer = Math.max(answer, getHeight(map[i][j]));
                }
            }
        }
        // 답 출력
        System.out.println(answer);
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }

    // 높이 계산
    static int getHeight(char c) {
        return c - (c >= 'a' ? 'a' - 26 : 'A');
    }
}