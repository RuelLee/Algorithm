/*
 Author : Ruel
 Problem : Baekjoon 2638번 치즈
 Problem address : https://www.acmicpc.net/problem/2638
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 치즈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pos {
    int r;
    int c;

    public Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] map;

    public static void main(String[] args) throws IOException {
        // 1로 치즈의 위치가 주어진다
        // 공기 중(치즈가 없는 곳, 0)에 치즈가 두 방면 이상 노출되어있다면(내부의 공기는 노출되어있지 않다고 생각한다), 1턴 뒤에 녹아 사라진다고 한다
        // 모든 치즈가 녹아 사라지는 시간을 구하라.
        // 같은 0이더라도 외부와 내부가 다르다. 따라서 입력을 가로 세로 2칸 더 늘려 받아, 최외곽을 무조건 노출된 공기로 만들고 완전탐색을 해주도록 하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        map = new int[n + 2][m + 2];        // 가로 세로를 2줄 늘려 입력 받는다.
        for (int i = 1; i < map.length - 1; i++) {      // 가로는 0번이 아닌 1번줄부터,
            st = new StringTokenizer(br.readLine());        // 세로도 마찬가지.
            for (int j = 1; j < map[i].length - 1; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        int turn = 0;       // 사라지는데 필요한 턴 수.
        boolean isMelted = true;
        while (isMelted) {
            int[][] airExposure = new int[n + 2][m + 2];        // 치즈일 경우, 외부 공기에 노출된 방면을 센다.
            boolean[][] visited = new boolean[n + 2][m + 2];        // 방문 칸인지 확인.
            Queue<Pos> queue = new LinkedList<>();
            queue.offer(new Pos(0, 0));     // (0, 0)부터 시작.

            while (!queue.isEmpty()) {
                Pos current = queue.poll();     // 0의 위치를 꺼낸다.
                for (int d = 0; d < 4; d++) {       // 4방탐색
                    int nextR = current.r + dr[d];
                    int nextC = current.c + dc[d];

                    if (checkArea(nextR, nextC)) {      // 범위를 벗어나지 않고
                        if (map[nextR][nextC] == 1)     // 치즈라면
                            airExposure[nextR][nextC]++;        // 외부 공기에 노출된 방면을 하나 늘린다.
                        else if (!visited[nextR][nextC]) {      // 빈 곳이라면,
                            queue.offer(new Pos(nextR, nextC));     // 큐에 넣어 다음에 탐색한다.
                            visited[nextR][nextC] = true;       // 방문 체크
                        }
                    }
                }
            }

            isMelted = false;       // 녹은 치즈가 있는지 체크.
            for (int i = 0; i < airExposure.length; i++) {
                for (int j = 0; j < airExposure[i].length; j++) {
                    if (airExposure[i][j] > 1) {        // 공기중에 노출된 방면이 2개 이상이라면
                        map[i][j] = 0;      // 해당 치즈를 녹여 0으로 바꾼다.
                        isMelted = true;        // 녹은 치즈가 있다고 체크.
                    }
                }
            }
            if (isMelted)       // 녹은 치즈가 있다면 턴을 하나 늘려 반복한다.
                turn++;
        }
        // 소요된 턴 수 출력.
        System.out.println(turn);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}