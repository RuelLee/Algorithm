/*
 Author : Ruel
 Problem : Baekjoon 2573번 빙산
 Problem address : https://www.acmicpc.net/problem/2573
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2573_빙산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 세로n, 가로m 개의 칸에 빙산의 빙산의 높이가 주어진다.
        // 최외곽은 항상 0인 바닷물이다
        // 1년마다 인접한 바닷물의 개수만큼 빙산의 높이가 낮아진다고 할 때
        // 빙산이 여러 덩어리로 분리되는 최초 년을 출력하라
        // 분리되지 않는다면 0을 출력한다
        //
        // 시뮬레이션 문제.
        // 인접한 바닷물의 개수만큼 빙산의 높이를 녹이는 코드와
        // 분리되어있는 빙산의 개수를 세는 코드를 짜 한 턴마다 시행시켜주자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // map[i][j][0]에는 이번 턴에 바뀐 높이
        // map[i][j][1]에는 이전 턴의 높이를 기록해둘 것이다.
        map = new int[n][m][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                map[i][j][0] = map[i][j][1] = Integer.parseInt(st.nextToken());
        }

        int turn = 0;
        int group;
        // 그룹이 1개 이상이 되거나 0이 될 때까지.
        while ((group = countIceberg()) == 1) {
            // 방문체크
            boolean[][] visited = new boolean[map.length][map[0].length];
            for (int i = 0; i < map.length - 1; i++) {
                for (int j = 0; j < map[i].length - 1; j++)
                    // 방문하지 않았고, 이전 빙산의 높이가 0이라면(=바닷물이라면)
                    // 해당 바닷물에 인접한 빙산을 녹이고, 바닷물이라면 재귀적으로 melt 메소드를 호출한다.
                    if (!visited[i][j] && map[i][j][1] == 0)
                        melt(i, j, visited);
            }
            // 턴 증가.
            turn++;

            // 이전 빙산의 높이를 이번 빙산의 높이로 바꿔준다.
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++)
                    map[i][j][1] = map[i][j][0];
            }
        }
        System.out.println(group == 0 ? 0 : turn);
    }

    // 빙산의 개수를 센다.
    static int countIceberg() {
        int[][] groupCheck = new int[map.length][map[0].length];
        int groupCounter = 0;
        for (int i = 1; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {
                // i, j가 빙산이며, 아직 그룹으로 묶이지 않았다면
                // filGroup 메소드를 불러 인접한 빙산들까지 모두 한 그룹으로 묶는다.
                if (map[i][j][0] > 0 && groupCheck[i][j] == 0)
                    fillGroup(i, j, groupCheck, ++groupCounter);
            }
        }
        return groupCounter;
    }

    // 인접한 빙산들을 하나의 그룹으로 묶어준다.
    static void fillGroup(int r, int c, int[][] groupCheck, int counter) {
        groupCheck[r][c] = counter;
        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            if (map[nextR][nextC][0] > 0 && groupCheck[nextR][nextC] == 0)
                fillGroup(nextR, nextC, groupCheck, counter);
        }
    }

    // 빙산을 녹인다.(=1년을 지나가게 한다)
    static void melt(int r, int c, boolean[][] visited) {
        // 방문 체크.
        visited[r][c] = true;
        for (int d = 0; d < 4; d++) {
            // 4방 탐색을 한다.
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            // nextR, nextC가 맵 안의 구역이며
            if (checkArea(nextR, nextC)) {
                // 작년에 빙산이었다면,
                // nextR, nextC는 i, j와 인접하므로 높이를 하나 줄인다
                // 만약 이미 바닷물이 되었다면 0을 유지한다.
                if (map[nextR][nextC][1] > 0)
                    map[nextR][nextC][0] = Math.max(map[nextR][nextC][0] - 1, 0);

                // nextR, nextC가 작년에 바닷물이었으며, 아직 방문하지 않았다면
                // 재귀적으로 함수를 호출해 다음 위치에서 인접한 빙산들을 녹인다.
                if (!visited[nextR][nextC] && map[nextR][nextC][1] == 0)
                    melt(nextR, nextC, visited);
            }
        }
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}