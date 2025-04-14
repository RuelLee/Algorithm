/*
 Author : Ruel
 Problem : Baekjoon 16137번 견우와 직녀
 Problem address : https://www.acmicpc.net/problem/16137
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16137_견우와직녀;

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
    static int n;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 맵이 주어진다.
        // 값에 따라
        // 0인 경우는 절벽, 1인 경우는 평범한 땅, 2 이상의 값이라면 해당 주기에만 지나갈 수 있는 다리이다.
        // 한 칸을 움직이는데 시간 1이 소모된다.
        // 다리는 시간 1 동안만 유지되므로 연속하여 다리를 건너지는 않도록 한다.
        // 절벽 중 하나만 골라 해당 절벽을 m 주기일 때마다 건널 수 있는 다리로 만든다.
        // 이 때의 고르는 절벽은 가로 절벽과, 세로 절벽이 교차하는 지점에는 만들 수 없다.
        // (0, 0)에서 (n-1, n-1)로 이동하고자 한다.
        // 걸리는 최소 시간은?
        //
        // BFS 문제
        // 조건에 따라 충실히 구현해주면 되는 문제
        // 대신 조건이 꽤나 예외사항을 만들므로 고민을 좀 해야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 맵, 지을 수 있는 한 칸의 다리의 주기
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 맵
        int[][] map = new int[n][n];
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 각 칸에 이르는 최소 시간
        // minTimes[row][col][다리를 건설했는지 여부] = 최소 시간
        int[][][] minTimes = new int[n][n][2];
        for (int[][] row : minTimes) {
            for (int[] col : row)
                Arrays.fill(col, Integer.MAX_VALUE);
        }
        minTimes[0][0][0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int r = queue.peek() / (n * 2);
            int c = (queue.peek() % (n * 2)) / 2;
            int birdBridge = queue.poll() % 2;

            for (int d = 0; d < 4; d++) {
                int nextR = r + dr[d];
                int nextC = c + dc[d];
                
                // 다음 칸이 맵의 범위를 벗어나지 않고
                if (checkArea(nextR, nextC)) {
                    // 다음 칸이 땅이라면
                    // 최소 시간을 갱신하는지만 확인
                    if (map[nextR][nextC] == 1 && minTimes[nextR][nextC][birdBridge] > minTimes[r][c][birdBridge] + 1) {
                        minTimes[nextR][nextC][birdBridge] = minTimes[r][c][birdBridge] + 1;
                        queue.offer(nextR * (n * 2) + nextC * 2 + birdBridge);
                    } else if (map[nextR][nextC] > 1 && map[r][c] == 1 && minTimes[nextR][nextC][birdBridge] > (minTimes[r][c][birdBridge] + map[nextR][nextC]) / map[nextR][nextC] * map[nextR][nextC]) {
                        // 다음 칸이 다리라면, 현재 칸이 땅이어야하고, 해당 칸으로 이동할 수 있는 최소 시간은
                        // 주기가 돌아오는 가장 가까운 시간이다.
                        minTimes[nextR][nextC][birdBridge] = (minTimes[r][c][birdBridge] + map[nextR][nextC]) / map[nextR][nextC] * map[nextR][nextC];
                        queue.offer(nextR * (n * 2) + nextC * 2 + birdBridge);
                    } else if (map[nextR][nextC] == 0 && map[r][c] == 1 && birdBridge == 0 && minTimes[nextR][nextC][1] > (minTimes[r][c][0] + m) / m * m) {
                        // 다음 칸이 절벽이고, 현재 칸이 땅이고, 아직 다리를 건설하지 않았다면
                        // 다음 칸이 혹시 세로 절벽과 가로 절벽이 교차하는 지점인지 확인한다.
                        boolean[] cliff = new boolean[2];
                        for (int e = 0; e < 4; e++) {
                            int nearR = nextR + dr[e];
                            int nearC = nextC + dc[e];

                            if (checkArea(nearR, nearC) && map[nearR][nearC] == 0)
                                cliff[e % 2] = true;
                        }

                        // 두 절벽이 교차하는 지점이 아닐 경우 다리를 건설할 수 있다.
                        if (!(cliff[0] && cliff[1])) {
                            minTimes[nextR][nextC][1] = (minTimes[r][c][0] + m) / m * m;
                            queue.offer(nextR * (n * 2) + nextC * 2 + 1);
                        }
                    }
                }
            }
        }
        // 다리를 건설했건, 안했건, 가장 빨리 (n-1, n-1)에 도달한 시간을 출력한다.
        System.out.println(Math.min(minTimes[n - 1][n - 1][0], minTimes[n - 1][n - 1][1]));
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}