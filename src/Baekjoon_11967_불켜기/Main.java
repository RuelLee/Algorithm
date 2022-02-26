/*
 Author : Ruel
 Problem : Baekjoon 11967번 불켜기
 Problem address : https://www.acmicpc.net/problem/11967
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11967_불켜기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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

    public static void main(String[] args) throws IOException {
        // n, n방이 주어진다
        // 각 방에는 전구와 다른 방의 스위치(들)이 있다
        // 1, 1방은 항상 불이 밝혀져 있고, 주인공은 밝은 방으로만 이동할 수 있다
        // 최대한 많은 밝히고자 할 때, 밝은 방의 개수는?
        // 구현 문제.
        // 밝은 방을 돌면서 다른 방의 스위치를 켜고, 다시 밝은 방을 방문하는 과정을 반복한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<List<Pos>>> switches = new ArrayList<>();
        // switches.get(r).get(c)는 r, c 방에 있는 스위치들.
        for (int i = 0; i < n + 1; i++) {
            switches.add(new ArrayList<>());
            for (int j = 0; j < n + 1; j++)
                switches.get(i).add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {   // 스위치 정보 저장.
            st = new StringTokenizer(br.readLine());
            switches.get(Integer.parseInt(st.nextToken()) - 1).get(Integer.parseInt(st.nextToken()) - 1)
                    .add(new Pos(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1));
        }

        int[][] map = new int[n][n];        // 각 방의 전구 상태 및 방문 상태 표시.
        map[0][0] |= (1 << 1) | (1 << 2);
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(0, 0));
        int count = 1;      // 첫번째 방은 이미 밝혀져있으므로
        while (!queue.isEmpty()) {
            Pos current = queue.poll();      // 현재 위치

            // 현재 방의 스위치들 확인
            for (Pos p : switches.get(current.r).get(current.c)) {
                // 스위치에 해당하는 방이 아직 밝혀져있지 않다면
                if ((map[p.r][p.c] & 1 << 1) == 0) {
                    // 비트마스킹으로 밝은 방임을 표시
                    map[p.r][p.c] |= 1 << 1;
                    // 밝은 방 증가
                    count++;
                    // 해당 방으로부터 4방을 탐색한다.
                    for (int d = 0; d < 4; d++) {
                        int preR = p.r + dr[d];
                        int preC = p.c + dc[d];

                        // p.r, p.c에 도달하기 위해서는 해당 방으로부터 네 방향에 있는 방 중 최소 하나에 방문한 적이 있어야한다.
                        if (checkArea(preR, preC, map) && (map[preR][preC] & 1 << 2) != 0) {
                            // 그렇다면 p.r, p.c에서 탐색을 시작한다.
                            queue.offer(new Pos(p.r, p.c));
                            // 방문 체크
                            map[p.r][p.c] |= 1 << 2;
                        }
                    }
                }
            }

            // 스위치가 켜진 방에서의 네 방향 뿐만 아니라, 현재 위치(current)에서 갈 수 있는 네 방향도 탐색한다.
            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 네 방향의 방에서 방이 밝혀져있으면서 아직 방문한 적이 없다면
                if (checkArea(nextR, nextC, map) && (map[nextR][nextC] & 1 << 1) != 0 &&
                        (map[nextR][nextC] & 1 << 2) == 0) {
                    // 큐에 넣어 다음 번에 탐색
                    queue.offer(new Pos(nextR, nextC));
                    // 방문 체크.
                    map[nextR][nextC] |= 1 << 2;
                }
            }
        }
        // 최종적으로 세어진 count가 밝힌 방의 최대 개수
        System.out.println(count);
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}