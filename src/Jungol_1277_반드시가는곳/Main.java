/*
 Author : Ruel
 Problem : Jungol 1277번 반드시 가는 곳
 Problem address : https://jungol.co.kr/problem/1277
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1277_반드시가는곳;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static char[][] map;
    static boolean[][] visited;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어진다.
        // 각 격자 칸의 의미는 S 시작, E 도착, # 장애물, . 빈칸이다.
        // S -> E로 가는 모든 경로에 반드시 지나는 빈 칸에 o로 값을 바꾸어 출력하라
        //
        // BFS, 브루트 포스 문제
        // 각 빈 칸을 # 장애물로 바꾸어보고, S -> E로 도달이 가능한가를 BFS를 통해 검사하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        int n = Integer.parseInt(br.readLine());
        // 지도
        map = new char[n][];
        // 방문 체크
        visited = new boolean[n][n];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        // 시작 위치와 도착 위치
        int[] points = new int[2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'S')
                    points[0] = i * n + j;
                else if (map[i][j] == 'E')
                    points[1] = i * n + j;
            }
        }

        // 각 빈 칸을 장애물로 바꿔서 S -> E로 갈 수 있는지 확인.
        // 갈 수 없는 경우, 해당 칸은 필수로 지나야하는 칸
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == '.') {
                    map[i][j] = '#';
                    if (!isValid(points[0], points[1]))
                        map[i][j] = 'o';
                    else
                        map[i][j] = '.';
                }
            }
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                sb.append(map[i][j]);
            sb.append("\n");
        }
        // 출력
        System.out.print(sb);
    }

    // 현재 상태에서 start -> end로 가는 경로가 있는지 확인한다.
    static boolean isValid(int start, int end) {
        // 방문 체크 배열 초기화
        for (boolean[] r : visited)
            Arrays.fill(r, false);

        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start / map.length][start % map.length] = true;
        while (!queue.isEmpty()) {
            int row = queue.peek() / map.length;
            int col = queue.poll() % map.length;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC) && !visited[nextR][nextC] && map[nextR][nextC] != '#') {
                    queue.offer(nextR * map.length + nextC);
                    visited[nextR][nextC] = true;
                }
            }
        }
        // 도착 여부 반환
        return visited[end / map.length][end % map.length];
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map.length;
    }
}