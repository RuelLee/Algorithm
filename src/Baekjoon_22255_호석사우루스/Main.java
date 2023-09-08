/*
 Author : Ruel
 Problem : Baekjoon 22255번 호석사우루스
 Problem address : https://www.acmicpc.net/problem/22255
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22255_호석사우루스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Probe {
    int x;
    int y;
    int state;

    public Probe(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }
}

public class Main {
    // 사방 탐색할 때의 row, col 변동값
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    // 3k, 3k+1, 3k+2일 때 이동할 수 있는 방향이 다르다.
    static int[][] directions = {{0, 1, 2, 3}, {0, 2}, {1, 3}};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 미궁이 주어지며, 공룡이 갇혀있다.
        // (Sx, Sy) 위치에 존재하고 있으며, (Ex, Ey)로 이동하고자 한다.
        // 각 방에는 정해진 충격량이 있으며, 입장할 때마다 충격을 받는다고 한다.
        // 또한 3k번째 이동에는 상하좌우 모두 이동 가능하며, 3k+1번째는 상하, 3k+2번째는 좌우로만 이동이 가능하다고 한다.
        // 출발지에서 도착지까지 도달하는데 최소 충격량으로 도달하고자 한다면 그 값은?
        //
        // 그래프 탐색 문제
        // 각 방에 도달하는 최소 충격량을 구하되, 해당 이동이 몇 번째 이동인지 관해서도 고려해야한다.
        // 정확히 몇 번이지는 필요없고, 3으로 나눴을 때 나머지가 몇 인지만 중요하므로
        // dp[x][y][이동횟수 %3]으로 dp를 세우고 그래프 탐색을 진행한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 미궁
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 출발지 (sx, sy), 도착지(ex, ey)
        int sx = Integer.parseInt(st.nextToken()) - 1;
        int sy = Integer.parseInt(st.nextToken()) - 1;
        int ex = Integer.parseInt(st.nextToken()) - 1;
        int ey = Integer.parseInt(st.nextToken()) - 1;
        
        // 각 방에 입장할 때 받는 충격량
        int[][] map = new int[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 각 방에 도달하는 최소 충격량
        int[][][] dp = new int[n][m][3];
        // 큰 값으로 초기화
        for (int[][] row : dp) {
            for (int[] col : row)
                Arrays.fill(col, Integer.MAX_VALUE);
        }
        // 출발지에서는 충격량이 0
        dp[sx][sy][0] = 0;

        // BFS로 최소 값을 갱신하는 이동만 한다.
        Queue<Probe> queue = new LinkedList<>();
        // 출발지
        queue.offer(new Probe(sx, sy, 0));
        while (!queue.isEmpty()) {
            Probe current = queue.poll();
            // 이번에 할 이동이 몇번째 이동인지 계산.
            int nextState = (current.state + 1) % 3;
            
            // 현재 상황에 맞는 이동만 가능하다.
            // 3k라면 상하좌우, 3k+1이라면 상하, 3k+2라면 좌우
            for (int d = 0; d < directions[nextState].length; d++) {
                // 다음 이동 위치
                int nextR = current.x + dr[directions[nextState][d]];
                int nextC = current.y + dc[directions[nextState][d]];
                
                // 다음 이동위치가 맵을 벗어나지 않으며, 벽이 아니고
                // 최소 값을 갱신한다면
                if (checkArea(nextR, nextC, map) && map[nextR][nextC] != -1 &&
                        dp[nextR][nextC][nextState] > dp[current.x][current.y][current.state] + map[nextR][nextC]) {
                    // 값을 갱신하고, 큐에 추가
                    dp[nextR][nextC][nextState] = dp[current.x][current.y][current.state] + map[nextR][nextC];
                    queue.offer(new Probe(nextR, nextC, nextState));
                }
            }
        }

        // 도착 지점의 모든 상태에 대한 값들 중 최소 값을 가져온다.
        int answer = Arrays.stream(dp[ex][ey]).min().getAsInt();
        // 해당 값이 초기값 그대로라면 도착이 불가능한 경우이므로 -1을 출력
        // 아니라면 해당 값을 출력한다.
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}