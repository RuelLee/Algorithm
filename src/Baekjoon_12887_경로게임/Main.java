/*
 Author : Ruel
 Problem : Baekjoon 12887번 경로 게임
 Problem address : https://www.acmicpc.net/problem/12887
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12887_경로게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 2행 m열의 지도가 주어진다.
        // 지도의 각 칸은 . 하얀 칸과, # 검정칸이 있다.
        // 가장 왼쪽에서 오른쪽으로 이동하고자 한다.
        // 왼쪽 -> 오른쪽 경로가 존재하면서, 하얀 칸을 검정칸으로 바꿀 수 있는 최대 칸 수는?
        //
        // BFS 문제
        // 가장 왼쪽 칸에서 출발하여 오른쪽 칸에 도달하는 최소 경로를 찾아
        // 밟은 칸을 제외한 남은 하얀 칸들을 검정칸으로 바꾸면 되는 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // m열
        int m = Integer.parseInt(br.readLine());
        
        // 지도
        char[][] map = new char[2][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 전체 하얀 칸의 수
        int totalWhites = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '.')
                    totalWhites++;
            }
        }
        
        // 각 칸에 도달하는데 밟는 최소한의 하얀 칸의 수
        int[][] minWhites = new int[2][m];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < minWhites.length; i++) {
            // 큰 값으로 초기화
            Arrays.fill(minWhites[i], Integer.MAX_VALUE);
            // 가장 왼쪽 칸이라면 큐에 추가
            if (map[i][0] == '.') {
                minWhites[i][0] = 1;
                queue.offer(i * m);
            }
        }

        while (!queue.isEmpty()) {
            // 현재 칸
            int current = queue.poll();
            int row = current / m;
            int col = current % m;
            
            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 지도를 벗어나지 않고, 하얀칸이며,
                // (row, col) -> (nextR, nextC)로 진행하는 것이
                // 밟는 최소 하얀 칸의 수를 갱신할 경우
               if (checkArea(nextR, nextC, map) && map[nextR][nextC] == '.' &&
                        minWhites[nextR][nextC] > minWhites[row][col] + 1) {
                    minWhites[nextR][nextC] = minWhites[row][col] + 1;
                    queue.offer(nextR * m + nextC);
                }
            }
        }

        // 가장 오른쪽의 두 칸 중 더 하얀 칸을 적게 밟은 것의 값
        int minWhitesToRight = Math.min(minWhites[0][m - 1], minWhites[1][m - 1]);
        // 전체 하얀 칸의 수에서 minWhitesToRight를 빼준 값이
        // 하얀 칸을 검정칸으로 바꾸더라도 경로가 존재하는
        // 최대 하얀 칸의 수
        System.out.println(totalWhites - minWhitesToRight);
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}