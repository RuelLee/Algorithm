/*
 Author : Ruel
 Problem : Baekjoon 18128번 치삼이의 징검다리 건너기
 Problem address : https://www.acmicpc.net/problem/18128
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18128_치삼이의징검다리건너기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Loc {
    int row;
    int col;

    public Loc(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0, -1, 1, 1, -1};
    static int[] dc = {0, 1, 0, -1, 1, 1, -1, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 정사각형 모양 계곡이 주어진다.
        // 계곡에는 땅과 돌로 이루어져있는데, 땅과 돌이 너무 뜨거워 밟고 지나갈 수 없다.
        // w개의 물 생성지가 주어지는데, 물은 자신과 인접한 상하좌우로 퍼져나간다.
        // 물에 닿은 돌은 식어 밟을 수 있게 된다.
        // 주인공은 상하좌우 대각선으로 이동할 수 있으며, 이동하는데 시간이 걸리지 않는다고 한다.
        // (1, 1)에서 출발하여 (n, n)으로 가는데 걸리는 최소 시간은?
        //
        // BFS 문제
        // 먼저 물이 퍼져나가는 시간을 BFS를 통해 구한다.
        // 그 후, 주인공이 이동을 하는 것 또한 BFS를 통해 구하되
        // 물이 닿아 식은 돌으로만 이동할 수 있다는 것을 유의한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 계곡
        int n = Integer.parseInt(st.nextToken());
        // w개의 물 생성지
        int w = Integer.parseInt(st.nextToken());
        
        // 계곡의 각 칸이 식는데 걸리는 시간
        int[][] coolTime = new int[n][n];
        Queue<Loc> queue = new LinkedList<>();
        for (int[] ct : coolTime)
            Arrays.fill(ct, Integer.MAX_VALUE);
        coolTime[0][0] = coolTime[n - 1][n - 1] = 0;
        
        // 물 생성지 표시
        for (int i = 0; i < w; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            coolTime[row][col] = 0;
            queue.offer(new Loc(row, col));
        }

        // 물 생성지부터 BFS를 통해 계곡을 물로 식히는데 걸리는 시간 계산
        while (!queue.isEmpty()) {
            Loc current = queue.poll();

            for (int d = 0; d < 4; d++) {
                int nextR = current.row + dr[d];
                int nextC = current.col + dc[d];

                if (checkArea(nextR, nextC, n) && coolTime[nextR][nextC] > coolTime[current.row][current.col] + 1) {
                    coolTime[nextR][nextC] = coolTime[current.row][current.col] + 1;
                    queue.offer(new Loc(nextR, nextC));
                }
            }
        }
        
        // 현재 계곡의 땅과 돌 상태
        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // 각 위치에 도달하는 최소 시간
        int[][] minTimes = new int[n][n];
        for (int[] mt : minTimes)
            Arrays.fill(mt, Integer.MAX_VALUE);
        // (0, 0)에서 출발
        minTimes[0][0] = 0;
        
        // BFS
        queue.offer(new Loc(0, 0));
        while (!queue.isEmpty()) {
            Loc current = queue.poll();
            
            // 4방 + 대각선
            for (int d = 0; d < dr.length; d++) {
                int nextR = current.row + dr[d];
                int nextC = current.col + dc[d];

                // 맵을 벗어나지 않고, 돌이며
                // 현재 바로 이동하는 시간과 다음 위치가 물에 식는 시간을 비교하여
                // 더 큰 값이, 이동 가능한 시간.
                if (checkArea(nextR, nextC, n) && map[nextR][nextC] == '1' &&
                        minTimes[nextR][nextC] > Math.max(minTimes[current.row][current.col], coolTime[nextR][nextC])) {
                    // current에서 다음 위치로 이동하는 것이 최소 시간이라면 값 기록
                    minTimes[nextR][nextC] = Math.max(minTimes[current.row][current.col], coolTime[nextR][nextC]);
                    // 큐에 추가
                    queue.offer(new Loc(nextR, nextC));
                }
            }
        }
        // (n-1, n-1)에 도달하는 최소 시간이 초기값 그대로라면 불가능한 경우이므로 -1 출력
        // 그렇지 않다면 해당 시간 출력
        System.out.println(minTimes[n - 1][n - 1] == Integer.MAX_VALUE ? -1 : minTimes[n - 1][n - 1]);
    }

    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}