/*
 Author : Ruel
 Problem : Baekjoon 16920번 확장 게임
 Problem address : https://www.acmicpc.net/problem/16920
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16920_확장게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Seek {
    int player;
    int r;
    int c;
    int canMove;

    public Seek(int player, int r, int c, int canMove) {
        this.player = player;
        this.r = r;
        this.c = c;
        this.canMove = canMove;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int n, m;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자가 주어지고, 이를 p명의 플레이어들이 최소 1개씩의 성을 가지고 게임을 진행한다.
        // 플레이어 번호가 작은 플레이어부터 순서대로 게임을 진행한다.
        // 각 플레이어는 s[i]만큼을 상하좌우로 벽과 다른 플레이어의 성이 아닌 지역으로 이동할 수 있으며,
        // 해당 이동한 지역에 자신의 성을 세운다.
        // 모든 플레이어가 더 이상 자신의 영역을 확장할 수 없을 때 게임이 끝난다.
        // 게임이 끝난 후, 각 플레이어의 영역의 크기를 출력하라
        //
        // BFS 문제
        // 움직일 수 있는 거리를 꼭 한방향으로 상하좌우로 이동할 필요는 없었다.
        // 두 칸을 이동할 수 있다면, 상우, 상좌 등의 조합으로 이동도 가능.
        // BFS로 탐색을 하되, 플레이어가 작은 번호를 우선, 번호가 같다면, 아직 많이 이동할 수 있는 탐색을 우선한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        // 플레이어의 수
        int p = Integer.parseInt(st.nextToken());
        
        // 각 플레이어가 턴 마다 움직일 수 있는 칸 수
        int[] s = new int[p + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < s.length; i++)
            s[i] = Integer.parseInt(st.nextToken());

        // 이번 턴의 탐색과 다음 턴의 탐색을 분리한다.
        PriorityQueue<Seek>[] priorityQueues = new PriorityQueue[2];
        // 우선순위큐로 플레이어 번호가 작은 것을 우선,
        // 번호가 같다면, 아직 움직일 수 있는 칸 수가 많은 탐색을 우선
        for (int i = 0; i < priorityQueues.length; i++) {
            priorityQueues[i] = new PriorityQueue<>((o1, o2) -> {
                if (o1.player == o2.player)
                    return Integer.compare(o2.canMove, o1.canMove);
                return Integer.compare(o1.player, o2.player);
            });
        }
        
        // 각 맵의 주인을 표시
        int[][] map = new int[n][m];
        for (int i = 0; i < map.length; i++) {
            String row = br.readLine();
            for (int j = 0; j < map[i].length; j++) {
                // 벽인 경우
                if (row.charAt(j) == '#')
                    map[i][j] = 10;
                else if (row.charAt(j) != '.') {        // 플레이어의 성인 경우.
                    map[i][j] = row.charAt(j) - '0';
                    // 우선순위큐에 추가
                    priorityQueues[0].offer(new Seek(map[i][j], i, j, s[map[i][j]]));
                }
            }
        }
        
        // 턴
        int turn = 0;
        // 이번 턴의 우선순위큐가 비지 않은 동안 반복
        while (!priorityQueues[turn % 2].isEmpty()) {
            // 이번 턴의 우선순위큐가 비지 않았다면
            while (!priorityQueues[turn % 2].isEmpty()) {
                // 하나 꺼내
                Seek current = priorityQueues[turn % 2].poll();
                // 남은 잔여 움직임 수가 0이라면
                // 잔여 움직임 수를 충전하여, 다음 턴으로 넘긴다.
                if (current.canMove == 0) {
                    current.canMove = s[current.player];
                    priorityQueues[(turn + 1) % 2].offer(current);
                    continue;
                }

                // 4방향으로 움직일 수 있다.
                for (int d = 0; d < 4; d++) {
                    int nextR = current.r + dr[d];
                    int nextC = current.c + dc[d];

                    // 맵의 범위를 벗어나지 않고
                    // 빈 공간인 경우.
                    if (checkArea(nextR, nextC) && map[nextR][nextC] == 0) {
                        // 해당 위치에 성을 세우고,
                        // 다음 탐색을 위해 우선순위큐에 잔여 움직임 수를 하나 차감하여 추가.
                        map[nextR][nextC] = current.player;
                        priorityQueues[turn % 2].offer(new Seek(current.player, nextR, nextC, current.canMove - 1));
                    }
                }
            }
            // 이번 턴의 우선순위큐가 비었다면
            // 다음 턴으로 넘긴다.
            turn++;
        }

        // 각 플레이어의 성을 센다.
        int[] counts = new int[11];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++)
                counts[map[i][j]]++;
        }

        StringBuilder sb = new StringBuilder();
        // 1번 플레이어부터
        sb.append(counts[1]);
        // p번 플레이어의 성의 개수 기록
        for (int i = 2; i <= p; i++)
            sb.append(" ").append(counts[i]);
        // 답 출력
        System.out.println(sb);
    }

    // 맵의 범위를 체크한다.
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}