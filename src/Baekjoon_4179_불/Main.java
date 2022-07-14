/*
 Author : Ruel
 Problem : Baekjoon 4179번 불!
 Problem address : https://www.acmicpc.net/problem/4179
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4179_불;

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
        // r, c의 미로와 지훈의 위치, 그리고 불의 위치가 주어진다
        // 지훈은 세로나 가로로 이동할 수 있고, 불은 세로, 가로 네방향으로 동시에 퍼져나간다한다.
        // 지훈이 이동하는 시간과 불이 퍼져나가는 시간은 1로 같다
        // 지훈이 미로에서 탈출할 수 있는지 있다면 그 시간, 불가능하다면 "IMPOSSIBLE" 을 출력하자.
        //
        // BFS 시뮬레이션 문제
        // 먼저, 불의 위치로부터 4방 탐색을 하며 불이 각 위치에 도달하는 시간을 기록해두자
        // 그 후, 지훈의 위치부터 4방 탐색을 하되, 지훈이 움직인 시간이
        // 불이 해당 위치에 도착한 시간보다 이를 때만 탐색하도록 하자.
        // 마지막으로 미로의 가장자리에 도달한 시간 중 최소값을 찾고,
        // 초기값이라면 불가능 한 경우, 아니라면 그 최소값 + 1을 출력해주자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        char[][] map = new char[r][c];
        int start = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < map.length; i++) {
            String row = br.readLine();
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = row.charAt(j);
                switch (map[i][j]) {
                    // 지훈의 위치 기록
                    case 'J' -> start = c * i + j;
                    // 불은 바로 큐에 넣어주자.
                    case 'F' -> queue.offer(c * i + j);
                }
            }
        }

        // 불이 퍼져나가는 시간.
        int[][] fireTurn = new int[r][c];
        // 지훈이 탈출하며 각 지점에 도달하는 시간.
        int[][] escapeTurn = new int[r][c];
        // 큰 값으로 초기화.
        for (int i = 0; i < fireTurn.length; i++) {
            Arrays.fill(fireTurn[i], Integer.MAX_VALUE);
            Arrays.fill(escapeTurn[i], Integer.MAX_VALUE);
        }
        // 탈출 지점의 시간 0.
        escapeTurn[start / c][start % c] = 0;
        // 불 또한 시간 0.
        for (int f : queue)
            fireTurn[f / c][f % c] = 0;

        // 먼저 불을 퍼뜨리자.
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / c;
            int col = current % c;
            
            // 4방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];
                
                // 맵을 벗어나지 않고, 벽이 아니며, 해당 지점에 도달하는 최소 시간을 갱신한다면
                if (checkArea(nextR, nextC, fireTurn) &&
                        map[nextR][nextC] != '#' &&
                        fireTurn[nextR][nextC] > fireTurn[row][col] + 1) {
                    // 최소 시간 기록
                    fireTurn[nextR][nextC] = fireTurn[row][col] + 1;
                    // 큐 삽입.
                    queue.offer(nextR * c + nextC);
                }
            }
        }

        // 지훈의 탈출
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / c;
            int col = current % c;

            // 4방 탐색.
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 맵의 범위 안에 있고, 벽이 아니며, 다음 지점에 도달하는 최소 시간을 갱신하며
                // 그 시간이 불이 해당 지점에 이르는 시간보다 이를 때만.
                if (checkArea(nextR, nextC, escapeTurn) &&
                        map[nextR][nextC] != '#' &&
                        escapeTurn[nextR][nextC] > escapeTurn[row][col] + 1 &&
                        escapeTurn[row][col] + 1 < fireTurn[nextR][nextC]) {
                    // 해당 지점에 도달 시간 저장.
                    escapeTurn[nextR][nextC] = escapeTurn[row][col] + 1;
                    // 큐에 삽입.
                    queue.offer(nextR * c + nextC);
                }
            }
        }

        // 가장자리에 도달하는 최소 시간을 찾는다.
        int minEscapeTime = Integer.MAX_VALUE;
        // 먼저 좌측과, 우측 가장자리에서 탐색.
        for (int i = 0; i < escapeTurn.length; i++)
            minEscapeTime = Math.min(minEscapeTime,
                    Math.min(escapeTurn[i][0], escapeTurn[i][escapeTurn[i].length - 1]));
        // 상측과 하측 가장 자리 탐색.
        for (int i = 0; i < escapeTurn[0].length; i++)
            minEscapeTime = Math.min(minEscapeTime,
                    Math.min(escapeTurn[0][i], escapeTurn[escapeTurn.length - 1][i]));

        // minEscapeTime이 초기값이라면 탈출이 불가능한 경우.
        // 그렇지 않다면 minEscapeTime + 1(=맵 밖으로 나가야하므로)를 출력해주자.
        System.out.println(minEscapeTime == Integer.MAX_VALUE ? "IMPOSSIBLE" : (minEscapeTime + 1));
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}