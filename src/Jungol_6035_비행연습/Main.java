/*
 Author : Ruel
 Problem : Jungol 6035번 비행 연습
 Problem address : https://jungol.co.kr/problem/6035
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_6035_비행연습;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[] dx = {-1, 0, 1, 0, 0, 0};
    static int[] dy = {0, 1, 0, -1, 0, 0};
    static int[] dz = {0, 0, 0, 0, -1, 1};
    static int mod = 100 * 100;

    public static void main(String[] args) throws IOException {
        // n * n * n 크기의 3차원 공간이 주어진다.
        // 각 공간에는 구름이 있거나 비어있다. 구름이 있는 곳은 지나갈 수 없다.
        // 시작 위치와 도착 위치 주어진다.
        // 인접한 상하좌우위아래, 인접한 격자로 이동 가능하다.
        // 이동하는데 최단 거리를 구하라
        //
        // BFS 문제
        // 2차원이 아니라 3차원이 됐을 뿐, 똑같다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n * n 크기의 공간
        n = Integer.parseInt(br.readLine());

        // 각 격자에 도달하는 최단 거리
        int[][][] minDistances = new int[n][n][n];
        for (int[][] minDistance : minDistances) {
            for (int[] md : minDistance)
                Arrays.fill(md, Integer.MAX_VALUE);
        }

        // 시작 위치와 도착 위치
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] start = new int[]{Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1};
        st = new StringTokenizer(br.readLine());
        int[] end = new int[]{Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1};

        // 각 칸의 구름 유무
        char[][][] map = new char[n][n][n];
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                String input = br.readLine();
                for (int j = 0; j < n; j++)
                    map[i][j][k] = input.charAt(j);
            }
        }

        // 시작 위치
        minDistances[start[0]][start[1]][start[2]] = 0;
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start[0] * mod + start[1] * 100 + start[2]);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 현재 위치
            int x = current / mod;
            int y = (current % mod) / 100;
            int z = current % 100;

            // 이동 가능한 인접 칸
            for (int d = 0; d < 6; d++) {
                int nextX = x + dx[d];
                int nextY = y + dy[d];
                int nextZ = z + dz[d];

                // 범위 체크, 구름 유무 체크, 최단 거리 체크
                if (checkArea(nextX, nextY, nextZ) && map[nextX][nextY][nextZ] == '0' &&
                        minDistances[nextX][nextY][nextZ] > minDistances[x][y][z] + 1) {
                    minDistances[nextX][nextY][nextZ] = minDistances[x][y][z] + 1;
                    queue.offer(nextX * mod + nextY * 100 + nextZ);
                }
            }
        }

        // 답 출력
        int answer = minDistances[end[0]][end[1]][end[2]] == Integer.MAX_VALUE ? -1 : minDistances[end[0]][end[1]][end[2]];
        System.out.println(answer);
    }

    // 범위 체크
    static boolean checkArea(int x, int y, int z) {
        return x >= 0 && x < n && y >= 0 && y < n && z >= 0 && z < n;
    }
}