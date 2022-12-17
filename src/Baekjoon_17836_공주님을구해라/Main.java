/*
 Author : Ruel
 Problem : Baekjoon 17836번 공주님을 구해라!
 Problem address : https://www.acmicpc.net/problem/17836
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17836_공주님을구해라;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n, m 크기의 성에 공주님이 갇혀있다.
        // 용사는 (1, 1) 위치에서 시작하며, (n, m) 위치에 공주님이 있다.
        // 성에는 용사가 뚫지 못하는 벽(1)과, 통과할 수 있는 빈 공간(0) 그리고 명검 그림(2)가 주어진다.
        // 그람을 획득할 경우, 벽을 뚫고 진행할 수 있다고 한다.
        // 용사가 t 시간 이내에 공주님을 구출할 수 있다면 그 최소 시간과
        // 그렇지 않다면 Fail을 출력한다.
        //
        // 그래프 탐색 문제
        // 다만 그람 유무에 따라서 벽을 통과할 수 있는지 여부가 바뀌므로
        // 해당 상태를 구분해서 계산할 수 있어야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 입력으로 주어지는 값들
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 성의 상태
        int[][] castle = new int[n][];
        for (int i = 0; i < n; i++)
            castle[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 지점에 도달하는 최소 시간.
        // [i][j][k] = (i, j)에 도달하는데 드는 최소 시간
        // k = 0 그람을 미획득 상태, k = 1 그람을 획득 상태.
        int[][][] minTimes = new int[n][m][2];
        for (int[][] row : minTimes) {
            for (int[] state : row)
                Arrays.fill(state, Integer.MAX_VALUE);
        }
        // 명검 그람이 없는 상태에서
        // (0, 0)에서 시작한다.
        minTimes[0][0][0] = 0;

        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            // 현재 위치에서
            int current = queue.poll();
            int r = current / m;
            int c = current % m;
            
            // 사방탐색으로 다음 위치 탐색
            for (int d = 0; d < 4; d++) {
                int nextR = r + dr[d];
                int nextC = c + dc[d];
                
                // 다음 위치가 성의 범위를 벗어나지 않으며
                if (checkArea(nextR, nextC, castle)) {
                    // 현재 current에 명검 그람이 없는 상태로 도착했고, 다음 위치가 벽이 아니며
                    // 다음 위치로 도달하는 최소 시간을 갱신한다면
                    if (minTimes[r][c][0] != Integer.MAX_VALUE && castle[nextR][nextC] != 1 && minTimes[nextR][nextC][0] > minTimes[r][c][0] + 1) {
                        // 값 갱신
                        minTimes[nextR][nextC][0] = minTimes[r][c][0] + 1;
                        // 큐 추가
                        queue.offer(nextR * m + nextC);
                        // 혹시 다음 위치가 명검 그람이라면, 해당 위치로부터 그람이 있는 상태에도 동일 시간을 기록한다.
                        if (castle[nextR][nextC] == 2)
                            minTimes[nextR][nextC][1] = minTimes[nextR][nextC][0];
                    }

                    // 명검 그람이 있는 상태로 현 위치에 도달했고,
                    // 다음 위치로 도달하는 최소 시간을 갱신한다면
                    if (minTimes[r][c][1] != Integer.MAX_VALUE && minTimes[nextR][nextC][1] > minTimes[r][c][1] + 1) {
                        // 값 갱신
                        minTimes[nextR][nextC][1] = minTimes[r][c][1] + 1;
                        // 큐 추가
                        queue.offer(nextR * m + nextC);
                    }
                }
            }
        }

        // 그람이 있든 없든, 공주님께 도달하는 최소 시간
        int minTime = Arrays.stream(minTimes[n - 1][m - 1]).min().getAsInt();
        // 해당 시간이 시간 제한을 넘을 경우 Fail, 시간 내에 도달했다면 해당 시간 출력.
        System.out.println(minTime <= t ? minTime : "Fail");
    }

    // 성의 범위 체크
    static boolean checkArea(int r, int c, int[][] castle) {
        return r >= 0 && r < castle.length && c >= 0 && c < castle[r].length;
    }
}