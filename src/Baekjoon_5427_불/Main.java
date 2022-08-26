/*
 Author : Ruel
 Problem : Baekjoon 5427번 불
 Problem address : https://www.acmicpc.net/problem/5427
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5427_불;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // w, h의 맵이 주어진다
        // 각 칸에는 '.' 빈 공간, '#' 벽, '@' 사람, '*' 불 로 주어진다
        // 불을 1초마다 사방으로 번져나간다고 할 때, 사람이 맵의 맵을 탈출하는 최소 시간을 구하라.
        // 불가능하다면 IMPOSSIBLE을 출력한다.
        //
        // BFS 탐색 문제
        // 먼저 불이 번져나가는 시간을 구해둔다.
        // 그 후에, 사람을 사방 탐색하며, 불보다 이른 시간에 해당 지점에 도착하는 경우만 계산해준다.
        // 최후에 가장 자리에 도달하는 최소 시간을 구해 + 1을 하여 탈출시켜준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());

            // 맵에 대해 주어지는 입력.
            char[][] map = new char[h][];
            for (int i = 0; i < map.length; i++)
                map[i] = br.readLine().toCharArray();

            // 불들의 위치.
            List<Integer> fireLocs = new ArrayList<>();
            // 사람의 위치.
            int humanLoc = -1;
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == '*')
                        fireLocs.add(i * w + j);
                    else if (map[i][j] == '@')
                        humanLoc = i * w + j;
                }
            }

            // 불이 각 지점에 번져나가는 시간을 저장할 배열.
            int[][] fireTimes = new int[h][w];
            for (int[] ft : fireTimes)
                Arrays.fill(ft, Integer.MAX_VALUE);
            for (int fire : fireLocs)
                fireTimes[fire / w][fire % w] = 0;
            Queue<Integer> queue = new LinkedList<>(fireLocs);
            while (!queue.isEmpty()) {
                int current = queue.poll();
                int row = current / w;
                int col = current % w;

                // 불을 사방으로 번져나간다.
                for (int d = 0; d < 4; d++) {
                    int nextR = row + dr[d];
                    int nextC = col + dc[d];

                    // 맵 안에 있으며, 벽이 아니고,
                    // 기록되어있는 도달 시간보다 이른 시간에 불이 도달할 때만.
                    if (checkArea(nextR, nextC, map) && map[nextR][nextC] != '#' &&
                            fireTimes[nextR][nextC] > fireTimes[row][col] + 1) {
                        // 도달 시간 갱신.
                        fireTimes[nextR][nextC] = fireTimes[row][col] + 1;
                        // 큐에 삽입.
                        queue.offer(nextR * w + nextC);
                    }
                }
            }

            // 사람이 각 지점에 도착하는 시간을 저장할 배열.
            int[][] humanTimes = new int[h][w];
            for (int[] ht : humanTimes)
                Arrays.fill(ht, Integer.MAX_VALUE);
            humanTimes[humanLoc / w][humanLoc % w] = 0;
            queue.offer(humanLoc);
            while (!queue.isEmpty()) {
                int current = queue.poll();
                int row = current / w;
                int col = current % w;

                // 사방탐색.
                for (int d = 0; d < 4; d++) {
                    int nextR = row + dr[d];
                    int nextC = col + dc[d];
                    
                    // 맵을 벗어나지 않으며, 벽이 아니고
                    // 불보다 먼저 도착했으며, 이전에 기록된 사람의 도착 시간보다 이를 경우에만
                    if (checkArea(nextR, nextC, map) && map[nextR][nextC] != '#' &&
                            Math.min(fireTimes[nextR][nextC], humanTimes[nextR][nextC]) > humanTimes[row][col] + 1) {
                        // 도착 시간 갱신
                        humanTimes[nextR][nextC] = humanTimes[row][col] + 1;
                        // 큐 삽입
                        queue.offer(nextR * w + nextC);
                    }
                }
            }
            // 사람이 가장자리에 도착하는 최소 시간을 구한다.
            // 먼저 첫 row와 마지막 row에서의 최소값을 구하고
            int minTimeToEdge = Math.min(Arrays.stream(humanTimes[0]).min().getAsInt(),
                    Arrays.stream(humanTimes[h - 1]).min().getAsInt());
            // 나머지 row에 대해서는 첫 col과 마지막 col에 대해서 계산한다.
            for (int i = 1; i < h - 1; i++)
                minTimeToEdge = Math.min(minTimeToEdge, Math.min(humanTimes[i][0], humanTimes[i][w - 1]));

            // 최종적으로 구해진 최소 가장자리 도착 시간이 초기값이라면 불가능한 경우.
            // 그렇지 않고 값이 있다면 도착 가능한 경우이므로, +1을 더해 탈출 시간을 출력한다.
            sb.append(minTimeToEdge == Integer.MAX_VALUE ? "IMPOSSIBLE" : (minTimeToEdge + 1)).append("\n");
        }
        System.out.print(sb);
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}